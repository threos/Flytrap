package com.teambutterflyeffect.flytrap.robot.component

import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveComponent
import com.teambutterflyeffect.flytrap.component.driverassist.DriverAssistModule
import com.teambutterflyeffect.flytrap.component.flylogger.api.LogServer
import com.teambutterflyeffect.flytrap.component.fvm2.FlytrapVisionModule
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference
import edu.wpi.first.cameraserver.CameraServer

class RobotComponent(context: LifecycleContext) : LifecycleObject(context) {
    override fun onTick(context: ObjectContext<*>) {}

    override fun components(): Array<ObjectReference<out LifecycleObject>> = arrayOf(
        ObjectReference(LogServer::class.java),
        ObjectReference(FlytrapVisionModule::class.java),
        ObjectReference(RobotDriveComponent::class.java),
        ObjectReference(DriverAssistModule::class.java)
    )
}