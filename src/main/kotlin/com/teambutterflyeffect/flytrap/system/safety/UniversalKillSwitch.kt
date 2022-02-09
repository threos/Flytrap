package com.teambutterflyeffect.flytrap.system.safety

import com.teambutterflyeffect.flytrap.system.data.VolatileLateVal

val KILL_SWITCH_STATE = VolatileLateVal(KillSwitchState.NORMAL)

object UniversalKillSwitch {
    fun activate() {
        KILL_SWITCH_STATE.data = KillSwitchState.ACTIVE
    }
}

enum class KillSwitchState {
    ACTIVE,
    NORMAL,
}