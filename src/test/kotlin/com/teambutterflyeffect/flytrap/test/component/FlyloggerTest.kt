package com.teambutterflyeffect.flytrap.test.component

import com.teambutterflyeffect.flytrap.base.FlytrapInstance
import com.teambutterflyeffect.flytrap.component.flylogger.api.LogServer
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference
import kotlin.test.BeforeTest

class FlyloggerTest {
    @BeforeTest
    fun initializeTestServer() {
        FlytrapInstance()
        LifecycleContext.attach(
            ObjectReference(LogServer::class.java)
        )
    }
}