package com.teambutterflyeffect.flytrap.component.fvm2.transformation

import kotlin.math.PI
import kotlin.math.sin

/**
 * Transform midpoint value to distance. This method is currently inaccurate as the area of view
 * will increase with distance and transformation discards that.
 * @param fov camera field of view in degrees
 * @param nAngle negative angle (counterclockwise) between bottom ray of camera and y axis
 */
class MidpointTransformation (private val fov: Float, private val nAngle: Float) {
    private val sinA: Float = sin(toRadians(nAngle - 90)).toFloat()
    private val sinB: Float = sin(toRadians((180 - fov) / 2)).toFloat()

    fun transformHorizontalDistance(midPointToBottom: Float): Float {
        return (midPointToBottom * sinB) / sinA
    }
}

fun toRadians(degree: Float): Double {
    return degree * PI / 180f
}