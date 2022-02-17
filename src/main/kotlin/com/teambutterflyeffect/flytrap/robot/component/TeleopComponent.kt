package com.teambutterflyeffect.flytrap.robot.component

import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveComponent
import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveData
import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveMessage
import com.teambutterflyeffect.flytrap.component.driverassist.targetgravity.message.GravityForceMessage
import com.teambutterflyeffect.flytrap.component.flylogger.LogLevel
import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.control.input.message.InputChannelMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intents
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference
import edu.wpi.first.wpilibj.XboxController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Math.abs
import java.time.Duration

class TeleopComponent(context: LifecycleContext) : LifecycleObject(context) {
    val controller = XboxController(0)
    val gravityMessage: GravityForceMessage? = null

    val TAG = "TeleopComponent"

    override fun onTick(context: ObjectContext<*>) {
        log(TAG, "tick")
        val x = controller.getRawAxis(XboxController.Axis.kLeftX.value)
        val y = controller.getRawAxis(XboxController.Axis.kLeftY.value)
        this@TeleopComponent.context.post(
            RobotDriveMessage(
                Intents.create(
                    context,
                    RobotDriveComponent::class.java
                ),
                RobotDriveData(y * 0.6, x * (0.6 + (gravityMessage?.let {
                    if(it.isValid()) {
                        it.content.x * 0.2 * kotlin.math.abs(y)
                    } else 0.0
                } ?: 0.0)))
            )
        )
    }

    override fun onCreate(context: ObjectContext<*>) {
        super.onCreate(context)
        log(TAG, "onCreate", LogLevel.VERBOSE)
        //test(context)
    }

    override fun subscriptions(): Array<Class<out ObjectMessage>> = arrayOf(InputChannelMessage::class.java)

    override fun components(): Array<ObjectReference<out LifecycleObject>> = arrayOf()
}