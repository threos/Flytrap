package com.teambutterflyeffect.flytrap.component.driverassist.targetgravity

import kotlin.math.hypot
import kotlin.math.sqrt

object GravityComposer {
    fun compose(
        points: List<GravityObject>,
        observer: Point = Point(0.5f, 0f)
    ): GravityForce {
        return points.map {
            observer.relativePositionOf(it)
        }.reduce { p, p1 ->
            p.sum(p1)
        }.let {
            GravityForce(it.x, it.y, it.mass)
        }
    }
}

open class Point(
    val x: Float,
    val y: Float,
) {
    open fun relativePositionOf(point: Point): Point = Point(point.x - x, point.y - y)

    open fun relativePositionOf(point: GravityObject): GravityObject = GravityObject(point.x - x, point.y - y, point.mass)

    fun distance(): Float {
        return hypot(x, y)
    }
}

open class GravityObject(
    x: Float,
    y: Float,
    val mass: Float,
): Point(x, y) {

    fun force(): Float = (mass / squareF(distance()))

    private fun squareF(a: Float): Float = a * a

    fun sum(other: GravityObject): GravityObject {
        val force = force()
        val otherForce = other.force()

        val x = (1.0f - x) * force
        val y = (1.0f - y) * force

        val otherX = (1.0f - other.x) * otherForce
        val otherY = (1.0f - other.y) * otherForce

        return GravityObject(x + otherX, y + otherY, 1f)
    }
}

class GravityForce(
    x: Float,
    y: Float,
    val force: Float,
): Point(x, y)