package com.teambutterflyeffect.flytrap.robot.component

import com.teambutterflyeffect.flytrap.component.baller.light.BallerLightComponent
import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveComponent
import com.teambutterflyeffect.flytrap.component.baller.shooter.ShooterComponent
import com.teambutterflyeffect.flytrap.component.driverassist.DriverAssistModule
import com.teambutterflyeffect.flytrap.component.flylogger.api.LogServer
import com.teambutterflyeffect.flytrap.component.fvm2.FlytrapVisionModule
import com.teambutterflyeffect.flytrap.component.intake.IntakeComponent
import com.teambutterflyeffect.flytrap.component.tower.TowerModule
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference
import edu.wpi.first.cameraserver.CameraServer

class RobotComponent(context: LifecycleContext) : LifecycleObject(context) {
    override fun onTick(context: ObjectContext<*>) {}

    override fun components(): Array<ObjectReference<out LifecycleObject>> = arrayOf(
        //ObjectReference(LogServer::class.java),
        ObjectReference(FlytrapVisionModule::class.java),
        ObjectReference(RobotDriveComponent::class.java),
        ObjectReference(DriverAssistModule::class.java),
        ObjectReference(ShooterComponent::class.java),
        ObjectReference(BallerLightComponent::class.java),
        ObjectReference(IntakeComponent::class.java),
        ObjectReference(TowerModule::class.java)
    )
}