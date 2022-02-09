package com.teambutterflyeffect.flytrap.component.flylogger.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.teambutterflyeffect.flytrap.component.flylogger.LogLevel

data class LogModel(
    @JsonProperty("tag")
    val tag: String,
    @JsonProperty("message")
    val message: String,
    @JsonProperty("level")
    val level: LogLevel,
)
