package com.teambutterflyeffect.flytrap.component.debugserver

import com.fasterxml.jackson.annotation.JsonProperty
import com.teambutterflyeffect.flytrap.component.driverassist.Alliance

var ROBOT_CONFIGURATION = RobotConfiguration()

data class RobotConfiguration(
    @JsonProperty("driver_assist_enabled")
    var driverAssistEnabled: Boolean = true,
    @JsonProperty("driver_assist_direction")
    var driverAssistInitialDirection: Direction = Direction.LEFT,
    @JsonProperty("alliance")
    var alliance: Alliance = Alliance.RED,
    @JsonProperty("baller_ideal_y")
    var BALLER_IDEAL_Y_POINT: Double = 0.88,
    @JsonProperty("baller_ideal_x")
    var BALLER_IDEAL_X_POINT: Double = 0.52,
    @JsonProperty("baller_min_target")
    var BALLER_MINIMUM_TARGET_AREA: Int = 300,
    @JsonProperty("baller_ideal_target")
    var BALLER_IDEAL_TARGET_AREA: Int = 500,
    @JsonProperty("baller_ideal_target")
    var BALLER_RPM: Int = 7500,
    @JsonProperty("baller_ideal_target")
    var BALLER_LOW_RPM: Int = 3600,
)

enum class Direction(val rotation: Double) {
    LEFT(-1.0),
    RIGHT(1.0),
}