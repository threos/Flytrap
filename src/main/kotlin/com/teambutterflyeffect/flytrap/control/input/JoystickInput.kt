package com.teambutterflyeffect.flytrap.control.input

import com.teambutterflyeffect.flytrap.control.input.message.InputChannelMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ExternalValueReference
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intents
import com.teambutterflyeffect.flytrap.system.state.cubit.MultiStateCubit
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.XboxController

class JoystickInput(port: Int, context: LifecycleContext) : BaseInput<InputChannel>(context) {
    private val state = JoystickInputState(port, context)
    private val joystick = XboxController(port)

    private val buttons = Joystick.ButtonType.values().map {
        ExternalValueReference(it.value, Int::class.java)
    }
    private val axes = Joystick.AxisType.values().map {
        ExternalValueReference(it.value, Int::class.java)
    }

    override fun onTick(context: ObjectContext<*>) {
        buttons.forEach {
            state.emit(
                context,
                it, if (joystick.getRawButton(it.obj)) {
                    1.0
                } else 0.0
            )
        }
        axes.forEach {
            state.emit(context, it, joystick.getRawAxis(it.obj))
        }
    }
}

class JoystickInputState(private val port: Int, val context: LifecycleContext) : MultiStateCubit<Int, Double>() {
    override fun update(context: ObjectContext<*>, reference: ExternalValueReference<out Int>, value: Double) {
        this@JoystickInputState.context.post(
            InputChannelMessage(
                port,
                StaticInputChannel(
                    reference.obj,
                    value,
                ),
                Intents.create(context, null)
            )
        )
    }
}