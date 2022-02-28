package com.teambutterflyeffect.flytrap.component.debugserver

import com.teambutterflyeffect.flytrap.component.driverassist.Alliance

val ROBOT_CONFIGURATION = RobotCongiuration()

data class RobotCongiuration(
    var driverAssistEnabled: Boolean = true,
    var driverAssistInitialDirection: Direction = Direction.LEFT,
    var alliance: Alliance = Alliance.RED,
)

enum class Direction {
    LEFT,
    RIGHT,
}