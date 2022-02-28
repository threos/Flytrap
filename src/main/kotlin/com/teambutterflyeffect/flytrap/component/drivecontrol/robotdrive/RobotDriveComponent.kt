package com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX
import com.teambutterflyeffect.flytrap.component.flylogger.LogLevel
import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import edu.wpi.first.wpilibj.SpeedController
import edu.wpi.first.wpilibj.SpeedControllerGroup
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup
import kotlin.math.abs

const val TAG = "RobotDriveComponent"
class RobotDriveComponent(context: LifecycleContext) : LifecycleObject(context) {
    var data: RobotDriveData = RobotDriveData(0.0, 0.0)
    var dataExpiration: Long = 0
    var modifier: Double = 1.0
    var modifierExpiration: Long? = 0
    val defaultModifier = 0.8

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
        if(dataExpiration > System.currentTimeMillis()) {
            modifierExpiration?.takeUnless {
                it > System.currentTimeMillis()
            }.let {
                modifier = defaultModifier
            }
            drive.arcadeDrive(data.speed * modifier, data.rotation * abs(modifier))
        } /*else {
            log(TAG, "Ignore expired data!")
        }*/
    }

    @Synchronized fun use(): Boolean {
        return !locked
    }

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        //log(TAG, "Message: $message", LogLevel.VERBOSE)
        super.onMessage(context, message)
        if(message is RobotDriveMessage && message.isValid()) {
            data = message.content
            dataExpiration = message.validUntil!!
            //drive.arcadeDrive(message.content.speed, message.content.rotation)
        } else if(message is DriveModifierMessage && message.isValid()) {
            modifier = message.content(modifier, defaultModifier)
            modifierExpiration = message.validUntil
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