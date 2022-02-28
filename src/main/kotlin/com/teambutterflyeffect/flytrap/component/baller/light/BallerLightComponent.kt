package com.teambutterflyeffect.flytrap.component.baller.light

import com.teambutterflyeffect.flytrap.component.baller.light.animation.FadeAnimation
import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intents
import edu.wpi.first.wpilibj.AddressableLED
import edu.wpi.first.wpilibj.AddressableLEDBuffer

class BallerLightComponent(context: LifecycleContext) : LifecycleObject(context) {
    val tag = "BallerLightComponent"
    val led = AddressableLED(0)
    val buf = AddressableLEDBuffer(52)
    @Volatile
    var step = 0

    private val defaultFadeAnimation = FadeAnimation(0, 255, 0)

    var message: BallerLightMessage? = null

    override fun onTick(context: ObjectContext<*>) {
        if(message?.isValid() == false) message = null
        (message?.content ?: defaultFadeAnimation).let {
            it(step, buf)
            led.setData(buf)
            step++
        }
    }

    /*fun animate() {
        val x = (abs((step.mod(240) - 120)))
        val r = ((x / 120.0).let { if(it != 0.0) { it * 0.0 } else 0.0 }).roundToInt()
        val g = ((x / 120.0).let { if(it != 0.0) { it * 180.0 } else 0.0 }).roundToInt()
        val b = ((x / 120.0).let { if(it != 0.0) { it * 0.0 } else 0.0 }).roundToInt()
        for(i in 0 until buf.length) {
            buf.setRGB(i, r, g, b)
        }
        led.setData(buf)
        step++
    }*/

    override fun subscriptions(): Array<Class<out ObjectMessage>> = arrayOf(BallerLightMessage::class.java)

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        super.onMessage(context, message)
        if(message is BallerLightMessage && message.isValid()) {
            this@BallerLightComponent.message = message
        }
    }

    override fun onCreate(context: ObjectContext<*>) {
        super.onCreate(context)
        log(tag, "create!")
        led.setLength(buf.length)

        for(i in 0 until buf.length) {
            buf.setRGB(i, 0, 200, 0)
        }

        led.setData(buf)

        led.start()

        this@BallerLightComponent.context.post(BallerLightMessage(Intents.create(context, null), defaultFadeAnimation))
    }
}