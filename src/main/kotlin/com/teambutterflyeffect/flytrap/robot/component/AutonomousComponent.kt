package com.teambutterflyeffect.flytrap.robot.component

import com.teambutterflyeffect.flytrap.component.autopilot.AutopilotComponent
import com.teambutterflyeffect.flytrap.component.autopilot.AutopilotMessage
import com.teambutterflyeffect.flytrap.component.baller.aligner.BallerAlignerComponent
import com.teambutterflyeffect.flytrap.component.baller.finder.BallerFinderComponent
import com.teambutterflyeffect.flytrap.component.baller.finder.BallerFinderResultMessage
import com.teambutterflyeffect.flytrap.component.baller.light.OperationLightComponent
import com.teambutterflyeffect.flytrap.component.baller.shooter.ShooterComponent
import com.teambutterflyeffect.flytrap.component.baller.shooter.ShooterMessage
import com.teambutterflyeffect.flytrap.component.debugserver.ROBOT_CONFIGURATION
import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveComponent
import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveData
import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveMessage
import com.teambutterflyeffect.flytrap.component.driverassist.targetgravity.message.GravityForceMessage
import com.teambutterflyeffect.flytrap.component.flylogger.LogLevel
import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.component.intake.IntakeDirection
import com.teambutterflyeffect.flytrap.component.tower.component.TowerMotorComponent
import com.teambutterflyeffect.flytrap.component.tower.message.TowerMotorMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intents
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference

const val TAG = "AutonomousComponent"
class AutonomousComponent(context: LifecycleContext) : LifecycleObject(context) {
    /*var step = 0
    set(value) {
        field = value
        when(value) {
            1 -> {
                context.detach(ballerFinderReference)
                context.attach(alignerReference)
            }
        }
    }*/

    var begin = 0L

    /*private val ballerFinderReference = ObjectReference(BallerFinderComponent::class.java)
    private val alignerReference = ObjectReference(BallerAlignerComponent::class.java)*/

    override fun onTick(context: ObjectContext<*>) {
        /*if(System.currentTimeMillis() - begin < 100) {
            this@AutonomousComponent.context.post(
                TowerMotorMessage(
                    Intents.create(context, TowerMotorComponent::class.java),
                    IntakeDirection.OUT,
                    timeoutInMillis = 50,
                    )
            )
        }*/

        (System.currentTimeMillis() - begin).let {
            if(it in 500..6000) {
                this@AutonomousComponent.context.post(
                    ShooterMessage(
                        Intents.create(context, ShooterComponent::class.java),
                        ROBOT_CONFIGURATION.BALLER_RPM,
                        timeoutInMillis = 200
                    )
                )
            }
            if(it in 4000..6000) {
                this@AutonomousComponent.context.post(
                    TowerMotorMessage(
                        Intents.create(context, TowerMotorComponent::class.java),
                        IntakeDirection.IN,
                        timeoutInMillis = 200
                    )
                )
            }
                if(it in 7000..9500) {
                this@AutonomousComponent.context.post(
                    RobotDriveMessage(
                        Intents.create(context, RobotDriveComponent::class.java),
                        RobotDriveData(0.8, 0.0),
                        timeoutInMillis = 100
                    )
                )
            }
            /*if(it < 100) {
                this@AutonomousComponent.context.post(
                    TowerMotorMessage(
                        Intents.create(context, TowerMotorComponent::class.java),
                        IntakeDirection.OUT,
                        timeoutInMillis = 50,
                    )
                )
            }
            if(it > 3000) {
                this@AutonomousComponent.context.post(
                    ShooterMessage(
                        Intents.create(context, ShooterComponent::class.java),
                        ROBOT_CONFIGURATION.BALLER_RPM,
                        timeoutInMillis = 100
                    )
                )
            }
            if(it in 7001..8999) {
                this@AutonomousComponent.context.post(
                    TowerMotorMessage(
                        Intents.create(context, TowerMotorComponent::class.java),
                        IntakeDirection.IN,
                        timeoutInMillis = 100
                    )
                )
            }
            if(it > 9000) {
                this@AutonomousComponent.context.post(
                    AutopilotMessage(
                        Intents.create(context, AutopilotComponent::class.java),
                        timeoutInMillis = 100
                    )
                )
            }*/
        }


    }

    override fun onCreate(context: ObjectContext<*>) {
        super.onCreate(context)
        //this@AutonomousComponent.context.attach(ballerFinderReference)
        log(TAG, "onCreate", LogLevel.VERBOSE)
        begin = System.currentTimeMillis()
    }

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        super.onMessage(context, message)
        log(TAG, "Message", LogLevel.VERBOSE)
        if(message is GravityForceMessage && message.isValid()) {
            //log(TAG, "Valid gravity message!", LogLevel.VERBOSE)
        } else if(message is BallerFinderResultMessage && message.isValid()) {
            log(TAG, "BallerFinderResultMessage", LogLevel.VERBOSE)
            //step = 1
        }
    }

    override fun onDestroy(context: ObjectContext<*>) {
        super.onDestroy(context)
        //this@AutonomousComponent.context.detach(ballerFinderReference)
        //this@AutonomousComponent.context.detach(alignerReference)
    }

    override fun subscriptions(): Array<Class<out ObjectMessage>> = arrayOf(
        GravityForceMessage::class.java,
        BallerFinderResultMessage::class.java,
    )

    override fun components(): Array<ObjectReference<out LifecycleObject>> = arrayOf(
        //ObjectReference(AutopilotComponent::class.java)
        //ObjectReference(OperationLightComponent::class.java)
    )

}