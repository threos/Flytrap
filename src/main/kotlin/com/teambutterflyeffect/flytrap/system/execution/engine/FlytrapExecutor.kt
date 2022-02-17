package com.teambutterflyeffect.flytrap.system.execution.engine

import com.teambutterflyeffect.flytrap.component.flylogger.log

object FlytrapExecutor {
    const val TAG = "FlytrapExecutor"

    lateinit var processor: MultiThreadTaskProcessor
    val tickerProvider = TickerProvider()

    fun init() {
        log(TAG, "Initialize")
        processor = MultiThreadTaskProcessor()
        addTickerTask()
    }

    fun addTickerTask() {
        log(TAG, "Add ticker task")
        processor.addTask(tickerProvider.task)
    }
}