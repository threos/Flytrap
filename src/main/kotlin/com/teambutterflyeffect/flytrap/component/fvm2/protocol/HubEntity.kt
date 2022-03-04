package com.teambutterflyeffect.flytrap.component.fvm2.protocol

import com.fasterxml.jackson.annotation.JsonProperty

data class HubEntity(
    val id: String = "hub",
    val x: Double,
    val y: Double,
    @JsonProperty("tarea")
    val tArea: Int,
)