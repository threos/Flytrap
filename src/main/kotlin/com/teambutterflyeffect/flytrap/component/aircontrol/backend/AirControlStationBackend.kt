package com.teambutterflyeffect.flytrap.component.aircontrol.backend

import com.fasterxml.jackson.core.type.TypeReference
import com.fazecast.jSerialComm.SerialPort
import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.component.fvm2.protocol.HubEntity
import com.teambutterflyeffect.flytrap.system.data.mapper
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import java.io.InputStream

class AirControlStationBackend(context: LifecycleContext) : LifecycleObject(context) {
    val tag = "AirControlStationBackend"
    var port: SerialPort? = null
        set(value) {
            field?.inputStream?.let {
                stream = it
            }
            field = value
        }
    lateinit var stream: InputStream
    val reader = mapper.readerFor(object : TypeReference<HubEntity>() {})

    override fun onTick(context: ObjectContext<*>) {
        //mapper.readValue(stream)
    }

    override fun onCreate(context: ObjectContext<*>) {
        super.onCreate(context)
        log(tag, SerialPort.getCommPorts().joinToString {
            it.portDescription
        })

        port = SerialPort.getCommPorts().first {
            it.portDescription.contains("wch", ignoreCase = true)
        }.also {
            it.openPort()
        }
    }
}