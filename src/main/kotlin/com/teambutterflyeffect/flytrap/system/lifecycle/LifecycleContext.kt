package com.teambutterflyeffect.flytrap.system.lifecycle

import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference
import java.util.*

object LifecycleContext {
    private val objects: IdentityHashMap<ObjectReference<out LifecycleObject>, ObjectParent<LifecycleObject>> = IdentityHashMap()
    //private val subscribers: Map<>

    fun broadcast() {

    }

    fun <T: LifecycleObject> attach(objectReference: ObjectReference<T>) {
        val parent = ObjectParent.create<LifecycleObject>(objectReference.objectClass.getConstructor(LifecycleProvider::class.java).newInstance(this))

        objects[objectReference] = parent
    }

    fun <T: LifecycleObject> detach(objectReference: ObjectReference<T>) {
        objects.remove(objectReference)
    }
}

data class ObjectParent<T : LifecycleObject>(
    val context: ObjectContext<T>,
    val obj: T,
){
    companion object {
        fun <T: LifecycleObject> create(obj: T): ObjectParent<T> {
            return ObjectParent(ObjectContext(obj.javaClass), obj)
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
) {
    override fun equals(other: Any?): Boolean {
        return if(other is ObjectContext<*>) {
            other.objectClass == objectClass
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return objectClass.hashCode()
    }
}