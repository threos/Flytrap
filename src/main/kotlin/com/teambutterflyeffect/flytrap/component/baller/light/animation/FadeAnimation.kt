package com.teambutterflyeffect.flytrap.component.baller.light.animation

import com.teambutterflyeffect.flytrap.component.baller.light.BallerLightAnimator
import edu.wpi.first.wpilibj.AddressableLEDBuffer
import kotlin.math.abs
import kotlin.math.roundToInt

class FadeAnimation(val red: Int, val green: Int, val blue: Int): BallerLightAnimator {
    override fun invoke(counter: Int, buffer: AddressableLEDBuffer) {
        val x = (abs((counter.mod(240) - 120)))
        val r = ((x / 120.0).let { if(it != 0.0) { it * red } else 0.0 }).roundToInt()
        val g = ((x / 120.0).let { if(it != 0.0) { it * green } else 0.0 }).roundToInt()
        val b = ((x / 120.0).let { if(it != 0.0) { it * blue } else 0.0 }).roundToInt()
        for(i in 0 until buffer.length) {
            buffer.setRGB(i, r, g, b)
        }
    }
}