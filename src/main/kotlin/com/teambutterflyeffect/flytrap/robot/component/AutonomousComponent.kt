package com.teambutterflyeffect.flytrap.robot.component

import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveComponent
import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveData
import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveMessage
import com.teambutterflyeffect.flytrap.component.driverassist.targetgravity.message.GravityForceMessage
import com.teambutterflyeffect.flytrap.component.flylogger.LogLevel
import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intents
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference
import jdk.incubator.vector.VectorOperators.POW
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Math.pow
import java.time.Duration
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

const val TAG = "AutonomousComponent"
class AutonomousComponent(context: LifecycleContext) : LifecycleObject(context) {
    var i = 0
    override fun onTick(context: ObjectContext<*>) {

    }

    override fun onCreate(context: ObjectContext<*>) {
        super.onCreate(context)
        log(TAG, "onCreate", LogLevel.VERBOSE)
        //test(context)
    }

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        super.onMessage(context, message)
        log(TAG, "Message", LogLevel.VERBOSE)
        if(message is GravityForceMessage && message.isValid()) {
            log(TAG, "Valid gravity message!", LogLevel.VERBOSE)
            this@AutonomousComponent.context.post(
                RobotDriveMessage(
                    Intents.create(context, RobotDriveComponent::class.java),
                    RobotDriveData(
                        (min(message.content.force * 0.9f, 0.9f).toDouble().pow(2.0) * 0.8),
                        min(message.content.x, 1f) * -0.45
                    )
                )
            )
        }
    }

    override fun subscriptions(): Array<Class<out ObjectMessage>> = arrayOf(GravityForceMessage::class.java)

    override fun components(): Array<ObjectReference<out LifecycleObject>> = arrayOf()

    fun test(context: ObjectContext<*>) = runBlocking {
        log(TAG, "runBlocking", LogLevel.VERBOSE)
        launch {
            log(TAG, "Launch coroutine scope", LogLevel.VERBOSE)
            listOf(
                RobotDriveData(
                    0.5,
                    0.0
                ),
                RobotDriveData(
                    0.5,
                    1.0
                ),
                RobotDriveData(
                    0.5,
                    -1.0
                ),
            ).forEach {
                log(TAG, "Iterate RobotDriveData", LogLevel.VERBOSE)
                this@AutonomousComponent.context.post(
                    RobotDriveMessage(
                        Intents.create(context, RobotDriveComponent::class.java),
                        it
                    )
                )
                delay(20000)

                this@AutonomousComponent.context.post(
                    RobotDriveMessage(
                        Intents.create(context, RobotDriveComponent::class.java),
                        RobotDriveData(
                            0.0,
                            0.0
                        ),
                    )
                )
                delay(1000)
            }
        }
    }
}