package com.teambutterflyeffect.flytrap.system.lifecycle

import com.teambutterflyeffect.flytrap.system.execution.Ticker
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference

abstract class LifecycleObject(val context: LifecycleContext): Ticker {
    private var components: Array<ObjectReference<out LifecycleObject>> = emptyArray()
    private var subscriptions: Array<Class<out ObjectMessage>> = emptyArray()

    open fun onCreate(context: ObjectContext<*>) {
        components()?.run {
            components = this
            this@LifecycleObject.context.attach(*this)
        }
        subscriptions()?.run {
            subscriptions = this
            this@LifecycleObject.context.subscribe(context, *this)
        }
    }

    open fun components(): Array<ObjectReference<out LifecycleObject>>? = null

    open fun subscriptions(): Array<Class<out ObjectMessage>>? = null

    open fun onStart(context: ObjectContext<*>) {}

    open fun onStop(context: ObjectContext<*>) {}

    open fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {}

    open fun onDestroy(context: ObjectContext<*>) {
        components.forEach {
            this@LifecycleObject.context.detach(it)
        }
        subscriptions.forEach {
            this@LifecycleObject.context.unsubscribe(context, it)
        }
    }
}