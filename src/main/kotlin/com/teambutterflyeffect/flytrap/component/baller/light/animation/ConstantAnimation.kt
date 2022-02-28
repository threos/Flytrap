package com.teambutterflyeffect.flytrap.component.baller.light.animation

import com.teambutterflyeffect.flytrap.component.baller.light.BallerLightAnimator
import edu.wpi.first.wpilibj.AddressableLEDBuffer

class ConstantAnimation(val red: Int, val green: Int, val blue: Int): BallerLightAnimator {
    override fun invoke(counter: Int, buffer: AddressableLEDBuffer) {
        for(i in 0 until buffer.length) buffer.setRGB(i, red, green, blue)
    }
}