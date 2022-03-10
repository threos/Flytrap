package com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX
import com.teambutterflyeffect.flytrap.component.flylogger.LogLevel
import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.component.flylogger.storage.LogStorage.data
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import edu.wpi.first.wpilibj.SpeedController
import edu.wpi.first.wpilibj.SpeedControllerGroup
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup
import kotlin.math.abs

val defaultModifier = 0.9

const val TAG = "RobotDriveComponent"
class RobotDriveComponent(context: LifecycleContext) : LifecycleObject(context) {
    var driveMessage: RobotDriveMessage? = null
    var modifier: Double = 1.0
    var modifierExpiration: Long? = 0

    val leftMotors = arrayOf(
        WPI_VictorSPX(0),
        WPI_VictorSPX(1),
    )

    val rightMotors = arrayOf(
        WPI_VictorSPX(2),
        WPI_VictorSPX(3),
    )

    val motorGroups = arrayOf(
        MotorControllerGroup(leftMotors),
        MotorControllerGroup(rightMotors),
    )

    val drive: DifferentialDrive = DifferentialDrive(
        motorGroups[0],
        motorGroups[1],
    )

    override fun onCreate(context: ObjectContext<*>) {
        super.onCreate(context)
        motorGroups[1].inverted = true
    }

    var locked = false

    override fun subscriptions(): Array<Class<out ObjectMessage>> = arrayOf(
        RobotDriveMessage::class.java,
        DriveModifierMessage::class.java
    )

    override fun onTick(context: ObjectContext<*>) {
        var speed = 0.0
        var rotation = 0.0
        driveMessage?.takeIf {
            it.isValid()
        }?.also {
            modifierExpiration?.takeUnless { expiration ->
                expiration > System.currentTimeMillis()
            }.let {
                modifier = defaultModifier
            }
            speed = it.content.speed * if(it.allowModifier) modifier else defaultModifier
            rotation = it.content.rotation * if(it.allowModifier) abs(modifier) else defaultModifier
        }

        drive.arcadeDrive(speed, rotation)
    }

    @Synchronized fun use(): Boolean {
        return !locked
    }

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        //log(TAG, "Message: $message", LogLevel.VERBOSE)
        super.onMessage(context, message)
        if(message is RobotDriveMessage && message.isValid()) {
            if(driveMessage != null && message.content.let { abs(it.speed) + abs(it.rotation) < 0.05 }) return
            driveMessage = message
            //drive.arcadeDrive(message.content.speed, message.content.rotation)
        } else if(message is DriveModifierMessage && message.isValid()) {
            modifier = message.content(modifier, defaultModifier)
            modifierExpiration = message.validUntil
            log(TAG, "MODIFIER: $modifier EXP: $modifierExpiration")
        } else {
            log(TAG, "INVALID MESSAGE: $message", LogLevel.WARNING)
        }
    }

    override fun onDestroy(context: ObjectContext<*>) {
        super.onDestroy(context)
        leftMotors.forEach {
            it.close()
        }
        rightMotors.forEach {
            it.close()
        }
        motorGroups.forEach {
            it.close()
        }
        drive.close()
    }
}