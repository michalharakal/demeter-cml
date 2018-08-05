package com.fiwio.iot.demeter.remote.http.mapper

import com.fiwio.iot.demeter.domain.model.io.*
import com.fiwo.iot.demeter.api.model.Input
import com.fiwo.iot.demeter.api.model.Relay
import javax.inject.Inject


class DemeterMapper @Inject constructor() {
    fun map(device: Demeter, version: Versions, network: Network): com.fiwo.iot.demeter.api.model.Demeter {
        val result = com.fiwo.iot.demeter.api.model.Demeter()
        result.relays = device.actuators.map { output ->
            mapRelay(output)
        }
        result.inputs = device.sensors.map { input ->
            mapInputs(input)
        }
        result.versions = com.fiwo.iot.demeter.api.model.Versions()
        with(result.versions) {
            build = version.build
            hw = version.hw
            sha1 = version.git
        }
        result.network = com.fiwo.iot.demeter.api.model.Network()
        with(result.network) {
            ipv4 = network.ipv4
            ipv6 = network.ipv6
            mac = network.mac
        }
        return result
    }

    private fun mapInputs(input: Sensor): Input {
        val result = Input()
        with(result) {
            name = input.name
            value = when (input.value) {
                InputValue.ON -> "ON"
                InputValue.OFF -> "OFF"
                InputValue.UNKNOWN -> "UNKNOWN"
                else -> "OFF"
            }
        }
        return result

    }

    private fun mapRelay(output: Actuator): Relay {
        val result = Relay()
        with(result) {
            name = output.name
            value = if (output.isOn) "ON" else "OFF"
        }
        return result
    }
}
