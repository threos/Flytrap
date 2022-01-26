package com.teambutterflyeffect.flytrap.system.safety

fun safeRun(func: () -> Unit) = safeRunUniversal(func)

private fun safeRunUniversal(func: () -> Unit) = isKillSwitchActive().let { if(!it) func() }

private fun isKillSwitchActive(): Boolean {
    return KILL_SWITCH_STATE.data == KillSwitchState.NORMAL
}