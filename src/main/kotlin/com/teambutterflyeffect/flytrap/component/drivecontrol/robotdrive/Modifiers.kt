package com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive

import kotlin.math.sign

class ReverseModifier: ModifierBuilder {
    override fun invoke(previous: Double, default: Double): Double {
        return (-(previous.sign) * default)
    }
}

class TurboModifier: ModifierBuilder {
    override fun invoke(previous: Double, default: Double): Double {
        return previous.sign * 0.95
    }
}

class PrecisionModifier: ModifierBuilder {
    override fun invoke(previous: Double, default: Double): Double {
        return  -0.35
    }
}