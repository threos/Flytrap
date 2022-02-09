package com.teambutterflyeffect.flytrap.test.component

import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.component.fvm2.CAMERA_NEGATIVE_MOUNTING_ANGLE_IN_DEGREES
import com.teambutterflyeffect.flytrap.component.fvm2.FlytrapVisionModule
import com.teambutterflyeffect.flytrap.component.fvm2.VERTICAL_FOV_IN_DEGREES
import com.teambutterflyeffect.flytrap.component.fvm2.transformation.MidpointTransformation
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference
import com.teambutterflyeffect.flytrap.test.lifecycle.objects.TransparentMessageReceiverTestObject
import org.junit.jupiter.api.Test
import java.awt.Color
import java.awt.Graphics
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants
import kotlin.test.AfterTest
import kotlin.test.BeforeTest


const val TAG = "VisionTest"
class VisionTest {
    @BeforeTest
    fun attach() {
        LifecycleContext.attach(ObjectReference(FlytrapVisionModule::class.java))
        LifecycleContext.attach(ObjectReference(TransparentMessageReceiverTestObject::class.java))
    }

    @AfterTest
    fun reset() {
        LifecycleContext.shutdown()
    }

    @Test
    fun visionServerMustBroadcastMessage() {
    }

    @Test
    fun midpointTransformationPassive() {
        val transformation = MidpointTransformation(VERTICAL_FOV_IN_DEGREES, CAMERA_NEGATIVE_MOUNTING_ANGLE_IN_DEGREES)

        val val1 = transformation.transformHorizontalDistance(0.2f)
        val val2 = transformation.transformHorizontalDistance(0.4f)
        val val3 = transformation.transformHorizontalDistance(0.5f)
        val val4 = transformation.transformHorizontalDistance(0.55f)

        log(TAG, "Transform midpoint 0.2: $val1")
        log(TAG, "Transform midpoint 0.4: $val2")
        log(TAG, "Transform midpoint 0.5: $val3")
        log(TAG, "Transform midpoint 0.55: $val4")

        assert(val2 > val1)

        Thread.sleep(60000)
    }

}