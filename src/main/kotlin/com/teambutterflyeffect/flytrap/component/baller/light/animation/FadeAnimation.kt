package com.teambutterflyeffect.flytrap.component.baller.light.animation

import com.teambutterflyeffect.flytrap.component.baller.light.BallerLightAnimator
import edu.wpi.first.wpilibj.AddressableLEDBuffer
import kotlin.math.abs
import kotlin.math.roundToInt

class FadeAnimation(val red: Int, val green: Int, val blue: Int, val period: Int = 240): BallerLightAnimator {
    override fun invoke(counter: Int, buffer: AddressableLEDBuffer) {
        val x = (abs((counter.mod(period) - (period / 2))))
        val r = ((x * 2.0 / period).let { if(it != 0.0) { it * red } else 0.0 }).roundToInt()
        val g = ((x * 2.0 / period).let { if(it != 0.0) { it * green } else 0.0 }).roundToInt()
        val b = ((x * 2.0 / period).let { if(it != 0.0) { it * blue } else 0.0 }).roundToInt()
        for(i in 0 until buffer.length) {
            buffer.setRGB(i, r, g, b)
        }
    }
}