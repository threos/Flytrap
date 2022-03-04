package com.teambutterflyeffect.flytrap.system.lifecycle

import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.error.UnknownLifecycleObjectConstructorException
import com.teambutterflyeffect.flytrap.system.execution.engine.FlytrapExecutor
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.collections.HashSet

object LifecycleContext {
    private val objects: IdentityHashMap<ObjectReference<out LifecycleObject>, ObjectParent<out LifecycleObject>> =
        IdentityHashMap()
    private val classes: MutableMap<Class<out LifecycleObject>, MutableSet<ObjectReference<out LifecycleObject>>> =
        HashMap()
    private val subscribers: MutableMap<Class<out ObjectMessage>, MutableSet<ObjectReference<out LifecycleObject>>> =
        HashMap()
    val messageQueue: Queue<ObjectMessage> = LinkedList()
    val messageLock = ReentrantLock()

    internal fun getObjects(): Collection<ObjectParent<out LifecycleObject>> {
        return objects.values
    }

    fun <T : ObjectMessage> post(message: T) = runBlocking {
        launch {
            synchronized(messageLock) {
                messageQueue.add(message)
            }
        }
    }

    fun dispatch(message: ObjectMessage) {
        if (message.intent.target == null) {
            broadcast(message)
        } else sendMessage(message)
    }

    fun count() : Int {
        return objects.size
    }

    private fun <T : ObjectMessage> broadcast(message: T) {
        subscribers[message.javaClass]?.forEach { objects[it]?.message(message) }
    }

    private fun <T : ObjectMessage> sendMessage(message: T) {
        message.intent.target?.let { classes[it]?.forEach { reference -> objects[reference]?.message(message) } }
    }

    @Synchronized
    internal fun shutdown() {
        objects.forEach {
            runBlocking {
                it.value.destroy()
            }
        }
        objects.clear()
        classes.clear()
        subscribers.clear()
    }

    @Synchronized
    fun subscribe(context: ObjectContext<out LifecycleObject>, messageClass: Class<out ObjectMessage>) {
        if (subscribers[messageClass] == null) subscribers[messageClass] = HashSet()
        (subscribers[messageClass]
            ?: throw IllegalStateException("Subscriber set cannot be null.")).add(context.reference)
    }

    fun subscribe(context: ObjectContext<out LifecycleObject>, vararg messageClass: Class<out ObjectMessage>) {
        messageClass.forEach {
            subscribe(context, it)
        }
    }

    @Synchronized
    fun unsubscribe(context: ObjectContext<out LifecycleObject>, messageClass: Class<out ObjectMessage>) {
        subscribers.remove(messageClass)
    }

    @Synchronized
    fun <T : LifecycleObject> attach(objectReference: ObjectReference<T>) {
        try {
            val parent = ObjectParent.create(
                objectReference.objectClass.getConstructor(LifecycleContext::class.java).newInstance(this),
                objectReference,
            )

            objects[objectReference] = parent
            if (classes[objectReference.objectClass] == null) classes[objectReference.objectClass] = HashSet()
            (classes[objectReference.objectClass] ?: throw IllegalStateException("Class set cannot be null.")).add(objectReference)

            parent.create()

        } catch (e: NoSuchMethodException) {
            throw UnknownLifecycleObjectConstructorException(objectReference.objectClass, e)
        }
    }

    @Synchronized
    fun attach(vararg objectReference: ObjectReference<*>) {
        objectReference.forEach {
            attach(it)
        }
    }

    internal fun periodTick() {
        /*objects.values.forEach {
            it.tick()
        }*/
        FlytrapExecutor.tickerProvider.hit()
    }

    fun <T : LifecycleObject> detach(objectReference: ObjectReference<T>) {
        classes[objectReference.objectClass]?.remove(objectReference)
        objects.remove(objectReference)?.destroy()
    }
}

data class ObjectParent<T : LifecycleObject>(
    val context: ObjectContext<T>,
    val obj: T,
) {
    companion object {
        fun <T : LifecycleObject> create(obj: T, reference: ObjectReference<T>): ObjectParent<T> {
            return ObjectParent(ObjectContext(obj.javaClass, reference), obj)
        }
    }

    fun start() {
        obj.onStart(context)
    }

    fun stop() {
        obj.onStop(context)
    }

    fun create() {
        obj.onCreate(context)
    }

    fun destroy() {
        obj.onDestroy(context)
    }

    fun message(message: ObjectMessage) {
        obj.onMessage(context, message)
    }

    fun tick() {
        obj.onTick(context)
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is ObjectParent<*> -> {
                other.context == context
            }
            is Class<*> -> {
                other == context.objectClass
            }
            else -> {
                false
            }
        }
    }

    override fun hashCode(): Int {
        return context.hashCode()
    }
}

data class ObjectContext<T : LifecycleObject>(
    val objectClass: Class<T>,
    val reference: ObjectReference<T>,
) {
    override fun equals(other: Any?): Boolean {
        return if (other is ObjectContext<*>) {
            other.objectClass == objectClass && other.reference == reference
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return objectClass.hashCode()
    }
}