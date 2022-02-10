package com.teambutterflyeffect.flytrap.component.flylogger.storage

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.teambutterflyeffect.flytrap.component.flylogger.LogItem
import com.teambutterflyeffect.flytrap.component.flylogger.LogLevel
import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.system.data.mapper
import kotlinx.coroutines.runBlocking
import java.nio.file.Paths

const val TAG = "LogStorage"

object LogStorage {
    val file = Paths.get("", "flytrap_logger.json").toFile()
    var data: LogData

    init {
        log(TAG, "Initializing log storage", internal = true)
        data = try {
            log(TAG, "Reading from file: $file", internal = true)
            mapper.readValue(file)
        } catch (e: Throwable) {
            if(file.delete()) log(TAG, "Deleted corrupted log file: $file", internal = true)
            log(TAG, "Error reading from file: $file", internal = true)
            log(TAG, e.stackTraceToString(), level = LogLevel.CRITICAL, internal = true)
            LogData()
        }
    }

    fun append(item: LogItem) {
        data.messages.add(item)
        commit()
    }

    private fun commit() = runBlocking {
        mapper.writeValue(file, data)
    }
}