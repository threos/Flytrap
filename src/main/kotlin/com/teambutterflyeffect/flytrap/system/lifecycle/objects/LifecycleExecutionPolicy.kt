package com.teambutterflyeffect.flytrap.system.lifecycle.objects

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectParent

data class LifecycleExecutionPolicy(val dependsOn: List<Class<LifecycleObject>>, val priority: Int) {
    fun run(objects: List<ObjectParent<LifecycleObject>>): Int? {
        var dependencyIndex: Int? = null

        val objectClasses = objects.map {
            it.context.objectClass
        }

        dependsOn.forEachIndexed { index, it ->
            if (objectClasses.contains(it)) {
                dependencyIndex = index
            }
        }

        return dependencyIndex
    }
}