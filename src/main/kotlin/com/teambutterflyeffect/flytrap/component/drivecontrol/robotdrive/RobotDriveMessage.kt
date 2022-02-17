package com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive

import com.teambutterflyeffect.flytrap.system.lifecycle.data.DataMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent

class RobotDriveMessage(intent: Intent, data: RobotDriveData) : DataMessage<RobotDriveData>(intent, data, timeoutInMillis = 100)

data class RobotDriveData(
    val speed: Double,
    val rotation: Double,
)