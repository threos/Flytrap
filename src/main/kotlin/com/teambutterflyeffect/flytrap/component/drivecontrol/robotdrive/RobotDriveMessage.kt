package com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive

import com.teambutterflyeffect.flytrap.system.lifecycle.data.DataMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent

class RobotDriveMessage(intent: Intent, data: RobotDriveData, timeoutInMillis: Long = 100) : DataMessage<RobotDriveData>(intent, data, timeoutInMillis = timeoutInMillis)

data class RobotDriveData(
    val speed: Double,
    val rotation: Double,
)