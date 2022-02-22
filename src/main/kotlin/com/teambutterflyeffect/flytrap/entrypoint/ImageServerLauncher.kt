package com.teambutterflyeffect.flytrap.entrypoint

import com.teambutterflyeffect.flytrap.component.aircontrol.gui.AirControlPane
import com.teambutterflyeffect.flytrap.component.aircontrol.gui.ImagePane
import com.teambutterflyeffect.flytrap.component.aircontrol.gui.RadarPane
import com.teambutterflyeffect.flytrap.robot.component.ImageServer
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference
import java.awt.Color
import javax.swing.JFrame
import javax.swing.WindowConstants

fun main() {
    LifecycleContext.attach(ObjectReference(ImageServer::class.java))
    val frame = JFrame()
    frame.contentPane = ImagePane()
    frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    frame.setSize(1200, 1200)
    frame.background = Color.GRAY
    frame.isVisible = true
}