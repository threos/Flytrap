package com.teambutterflyeffect.flytrap.component.driverassist.targetgravity

import com.teambutterflyeffect.flytrap.component.flylogger.log
import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.max

object GravityComposer {
    val tag = "GravityComposer"
    private val nullForce = GravityForce(0f,0f,0f)
    fun compose(
        points: List<GravityObject>,
        observer: Point = Point(1f, 0f),
    ): GravityForce {
        return points.map {
            log(tag, "Point: ${it.x} , ${it.y} , ${it.mass}")
            observer.relativePositionOf(it)
        }.let { gravityObjects ->
            if(gravityObjects.isNotEmpty()) {
                gravityObjects.reduce { p, p1 ->
                    p.sum(p1)
                }.let {
                    GravityForce(it.x, it.y, it.mass)
                }
            } else nullForce
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
        return y
    }
}

open class GravityObject(
    x: Float,
    y: Float,
    val mass: Float,
): Point(x, y) {

    fun force(): Float = (mass / abs(distance()))

    private fun squareF(a: Float): Float = a * a

    fun sum(other: GravityObject): GravityObject {
        val force = force()
        val otherForce = other.force()

        val x = (1.0f - x) * unit(force)
        val y = max((1.0f - y), 0f)

        val otherX = (1.0f - other.x) * unit(otherForce)
        val otherY = max((1.0f - other.y), 0f)

        return GravityObject(x + otherX, y + otherY, max(force, 0.0f) + max(otherForce, 0.0f))
    }

    fun unit(value: Float): Float {
        return if(value < 0) (
            -1.0f
        ) else if(value > 0) {
            1.0f
        } else 0.0f
    }

    fun map(min: Float, max: Float, xMin: Float, xMax: Float, x: Float) {

    }
}

class GravityForce(
    x: Float,
    y: Float,
    val force: Float,
): Point(x, y)