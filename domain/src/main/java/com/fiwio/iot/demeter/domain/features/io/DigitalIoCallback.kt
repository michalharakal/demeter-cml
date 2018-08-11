package com.fiwio.iot.demeter.domain.features.io


interface DigitalIoCallback {
    fun onFLoatSensorActivated()
    fun onActuatorSet(pinName: String, on: Boolean)
}
