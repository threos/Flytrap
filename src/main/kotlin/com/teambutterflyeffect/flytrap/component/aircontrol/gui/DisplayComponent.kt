package com.teambutterflyeffect.flytrap.component.aircontrol.gui

import com.teambutterflyeffect.flytrap.component.fvm2.protocol.MapDataMessage
import com.teambutterflyeffect.flytrap.component.fvm2.protocol.VisionMapEntity
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import java.awt.Color
import javax.swing.JFrame
import javax.swing.WindowConstants

class DisplayComponent(context: LifecycleContext) : LifecycleObject(context) {
    lateinit var radar: RadarPane

    var entities: List<VisionMapEntity> = emptyList()

    val frame = JFrame()

    override fun onTick(context: ObjectContext<*>) {}

    override fun subscriptions(): Array<Class<out ObjectMessage>> = arrayOf(MapDataMessage::class.java)

    override fun onCreate(context: ObjectContext<*>) {
        super.onCreate(context)

        radar = RadarPane()
        frame.contentPane = AirControlPane(radar)
        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        frame.setSize(1200, 1200)
        frame.background = Color.GRAY
        frame.isVisible = true
    }

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        super.onMessage(context, message)
        if(message is MapDataMessage) {
            entities = message.entities
            radar.entities = entities
        }
    }

    override fun onDestroy(context: ObjectContext<*>) {
        super.onDestroy(context)
        frame.dispose()
    }
}