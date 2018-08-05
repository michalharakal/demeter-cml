package com.fiwio.iot.demeter.hw.gateway

import com.fiwio.iot.demeter.domain.gateway.DeviceGateway
import com.fiwio.iot.demeter.domain.model.io.*
import com.fiwio.iot.demeter.hw.mapper.InputValueMapper
import com.fiwio.iot.demeter.hw.mapper.OutputValueMapper
import com.fiwio.iot.demeter.hw.model.DigitalPins
import com.fiwio.iot.demeter.hw.model.DigitalValue
import com.fiwio.iot.demeter.hw.utils.Utils

class DemeterDeviceGateway(val device: DigitalPins,
                           val valuesMapper: InputValueMapper,
                           val outputValueMapper: OutputValueMapper, private val versions: Versions) : DeviceGateway {
    override fun getVersions(): Versions {
        return versions
    }

    override fun getNetwork(): Network {
        return Network(ipv4 = Utils.getLocalIpAddress(true), ipv6 = Utils
                .getLocalIpAddress(false), mac = Utils.getLocalMacAddress())
    }

    override fun getDeviceImage(): Demeter {
        return Demeter(
                device.outputs.map { output ->
                    Actuator(output.name, outputValueMapper.map(output.value))
                },
                device.inputs.map { input ->
                    Sensor(input.name, valuesMapper.map(input.value))
                }
        )
    }

    override fun switchActuator(actuator: Actuator): Demeter {
        // check floating sensors
        if (actuator.name.equals("BCM23")) {
            for (input in device.inputs) {
                if (input.name.equals("INP0")) {
                    // ignore command, floating sensor is active
                    if ((input.value == DigitalValue.OFF) && (actuator.isOn)) {
                        return getDeviceImage()
                    }
                }
            }
        }
        // switch
        val relays = device.outputs
        for (relay in relays) {
            if (relay.name.equals(actuator.name)) {
                relay.value = if (actuator.isOn) DigitalValue.ON else DigitalValue.OFF
                break
            }
        }

        return getDeviceImage()
    }
}