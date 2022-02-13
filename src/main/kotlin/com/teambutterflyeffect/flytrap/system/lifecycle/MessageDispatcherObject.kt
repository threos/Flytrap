package com.teambutterflyeffect.flytrap.system.lifecycle

import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import java.util.concurrent.locks.ReentrantLock

abstract class MessageDispatcherObject(context: LifecycleContext) : LifecycleObject(context) {
    val messageLock = ReentrantLock()
    val messages: MutableList<ObjectMessage> = ArrayList()

    override fun onTick(context: ObjectContext<*>) {
        onTick(context, messages)
        clear()
    }

    fun clear() {
        messageLock.lock()
        try {
            messages.clear()
        } finally {
            messageLock.unlock()
        }
    }

    abstract override fun subscriptions(): Array<Class<out ObjectMessage>>

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        super.onMessage(context, message)

        messageLock.lock()
        try {
            messages.add(message)
        } finally {
            messageLock.unlock()
        }
    }

    abstract fun onTick(context: ObjectContext<*>, messages: List<ObjectMessage>)
}