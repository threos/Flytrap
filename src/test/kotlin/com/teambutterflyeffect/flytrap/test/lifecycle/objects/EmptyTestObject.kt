package com.teambutterflyeffect.flytrap.test.lifecycle.objects

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage

class EmptyTestObject(context: LifecycleContext) : LifecycleObject(context) {

    override fun onTick(context: ObjectContext<*>) { }
}