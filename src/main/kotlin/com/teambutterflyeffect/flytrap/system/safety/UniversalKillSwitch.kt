package com.teambutterflyeffect.flytrap.system.safety

import com.teambutterflyeffect.flytrap.system.data.VolatileLateVal

val KILL_SWITCH_STATE = VolatileLateVal(KillSwitchState.NORMAL)

enum class KillSwitchState {
    ACTIVE,
    NORMAL,
}