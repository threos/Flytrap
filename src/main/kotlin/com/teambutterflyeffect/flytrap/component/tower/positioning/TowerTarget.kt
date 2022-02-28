package com.teambutterflyeffect.flytrap.component.tower.positioning

data class TowerTarget(
    val position: TowerPosition
)

enum class TowerPosition(val i: Int) {
    SHOOTER(3),
    PRE_SHOOTER(2),
    HOPPER(1),
    POST_INTAKE(-1),
}