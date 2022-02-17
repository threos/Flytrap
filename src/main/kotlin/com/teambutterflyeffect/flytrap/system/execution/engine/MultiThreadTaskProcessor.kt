package com.teambutterflyeffect.flytrap.system.execution.engine

import com.teambutterflyeffect.flytrap.component.flylogger.log
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MultiThreadTaskProcessor(
    NUM_THREADS: Int = Utils.getIdealThreadCount()
) {
    val tag = "MultiThreadTaskProcessor"

    private val executor: ExecutorService

    init {
        log(tag, "Create work stealing thread pool with $NUM_THREADS threads.")
        executor = Executors.newWorkStealingPool(NUM_THREADS)
    }

    fun execute(task: EngineTask) {
        log(tag, "Execute engine task: $task")
        executor.execute(task)
    }

    fun addTask(task: EngineTask) {
        log(tag, "Submit engine task: $task")
        executor.submit(task)
    }

    fun shutdown() {
        log(tag, "Shutdown executor")
        executor.shutdown()
    }
}