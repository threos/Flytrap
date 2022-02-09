package com.teambutterflyeffect.flytrap.control.input

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ExternalReference
import com.teambutterflyeffect.flytrap.system.state.cubit.MultiStateCubit

class JoystickInput(port: Int, context: LifecycleContext) : BaseInput<InputChannel>(context) {
    override fun onTick(context: ObjectContext<*>) {

    }
}

class JoystickInputState(): MultiStateCubit<InputChannel>() {
    override fun update(reference: ExternalReference<InputChannel>, value: InputChannel) {
        TODO("Not yet implemented")
    }
}