package com.teambutterflyeffect.flytrap.component.flylogger.storage

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.teambutterflyeffect.flytrap.component.flylogger.LogItem
import java.util.*

data class LogData constructor(
    @JsonProperty("messages")
    var messages: MutableList<LogItem> = ArrayList(),
    @JsonProperty("created_at")
    @JsonFormat
        (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    var createdAt: Date = Date(),
    @JsonProperty("modified")
    @JsonFormat
        (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    var modifiedAt: Date = Date(),
) {
    fun modified() {
        modifiedAt = Date()
    }
}