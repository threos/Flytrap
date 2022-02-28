package com.teambutterflyeffect.flytrap.component.flylogger.storage

import com.fasterxml.jackson.module.kotlin.readValue
import com.teambutterflyeffect.flytrap.component.flylogger.LogItem
import com.teambutterflyeffect.flytrap.component.flylogger.LogLevel
import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.component.flylogger.logo
import com.teambutterflyeffect.flytrap.system.data.mapper
import kotlinx.coroutines.runBlocking
import java.io.File
import java.nio.file.Paths

const val TAG = "LogStorage"

const val ENABLE_LOG_STORE = false

object LogStorage {
    private val file: File = Paths.get("", "flytrap_logger.json").toFile()
    var data: LogData
    var readOnly = true

    init {
        if(ENABLE_LOG_STORE) {
            log(TAG, "Log storage is disabled!", internal = true, level = LogLevel.WARNING)
            log(TAG, "Using empty log data.", internal = true)
            data = LogData()
        } else {
            log(TAG, "Initializing log storage", internal = true)
            data = try {
                log(TAG, "Reading from file: $file", internal = true)
                mapper.readValue(file)
            } catch (e: Throwable) {
                if (!readOnly) {
                    if (file.delete()) log(TAG, "Deleted corrupted log file: $file", internal = true)
                    log(TAG, "Error reading from file: $file", internal = true)
                    log(TAG, e.stackTraceToString(), level = LogLevel.CRITICAL, internal = true)
                }
                LogData()
            }
            logo.lines().forEach {
                log("FlytrapKit", it, level = LogLevel.INFO)
            }
        }
    }

    fun append(item: LogItem) {
        data.messages.add(item)
        commit()
    }

    private fun commit() = runBlocking {
        if(readOnly) return@runBlocking
        mapper.writeValue(file, data)
    }
}