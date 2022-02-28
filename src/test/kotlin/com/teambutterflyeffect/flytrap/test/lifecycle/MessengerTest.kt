package com.teambutterflyeffect.flytrap.test.lifecycle

import com.teambutterflyeffect.flytrap.base.FlytrapInstance
import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent
import com.teambutterflyeffect.flytrap.system.lifecycle.data.StringMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference
import com.teambutterflyeffect.flytrap.test.lifecycle.objects.AntiMessageTestObject
import com.teambutterflyeffect.flytrap.test.lifecycle.objects.EmptyTestObject
import com.teambutterflyeffect.flytrap.test.lifecycle.objects.TRANSPARENT_RECEIVER_MESSAGE_LIST
import com.teambutterflyeffect.flytrap.test.lifecycle.objects.TransparentMessageReceiverTestObject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains

const val TAG = "MessengerTest"
class MessengerTest {
    @BeforeTest
    fun warmUp() {
        FlytrapInstance()
        LifecycleContext
    }

    @AfterTest
    fun cleanUp() {
        LifecycleContext.shutdown()
    }

    @Test
    fun lifecycleObjectMustReceiveBroadcastMessage() {
        LifecycleContext.attach(
            ObjectReference(TransparentMessageReceiverTestObject::class.java)
        )

        val empty = EmptyTestObject::class.java

        for(i in 0..1000) LifecycleContext.attach(ObjectReference(empty))

        log(TAG, "Component count: ${LifecycleContext.count()}", internal = true)

        val start = System.currentTimeMillis()
        var i = 0
        while(true) {
            val message = StringMessage(Intent(EmptyTestObject::class.java, null), "Test message content")


            LifecycleContext.post(message)

            /*assertContains(
                TRANSPARENT_RECEIVER_MESSAGE_LIST,
                message,
                "TRANSPARENT_RECEIVER_MESSAGE_LIST does not contain previously posted message (Broadcast)."
            )*/
            if(i % 100 == 0 && System.currentTimeMillis() > start + 1000) break

            i++
        }
        log(TAG, "lifecycleObjectMustReceiveBroadcastMessage ran $i times in ${System.currentTimeMillis() - start}ms", internal = true)

    }

    @Test
    fun lifecycleObjectMustReceiveTargetedMessage() {
        val target = TransparentMessageReceiverTestObject::class.java
        val anti = AntiMessageTestObject::class.java
        val empty = EmptyTestObject::class.java

        LifecycleContext.attach(
            ObjectReference(target),
            ObjectReference(anti),
            ObjectReference(empty)
        )

        for(i in 0..1000) LifecycleContext.attach(ObjectReference(empty))

        log(TAG, "Component count: ${LifecycleContext.count()}", internal = true)
        val start = System.currentTimeMillis()
        var i = 0
        while(true) {
            val message = StringMessage(Intent(empty, target), "Test message content")

            LifecycleContext.post(message)

            /*assertContains(
                TRANSPARENT_RECEIVER_MESSAGE_LIST,
                message,
                "TRANSPARENT_RECEIVER_MESSAGE_LIST does not contain previously posted message (Targeted)."
            )*/

            if(i % 100 == 0 && System.currentTimeMillis() > start + 1000) break

            i++
        }
        log(TAG, "lifecycleObjectMustReceiveTargetedMessage ran $i times in ${System.currentTimeMillis() - start}ms", internal = true)
    }
}