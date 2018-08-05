package com.fiwio.iot.demeter.domain.gateway

import com.fiwio.iot.demeter.domain.model.io.Actuator
import com.fiwio.iot.demeter.domain.model.io.Demeter
import com.fiwio.iot.demeter.domain.model.io.Network
import com.fiwio.iot.demeter.domain.model.io.Versions

interface DeviceGateway {
    fun getVersions(): Versions
    fun getNetwork(): Network
    fun getDeviceImage(): Demeter
    fun switchActuator(actuator: Actuator): Demeter
}