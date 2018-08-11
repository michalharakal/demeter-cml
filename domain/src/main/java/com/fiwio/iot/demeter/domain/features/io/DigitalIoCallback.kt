package com.fiwio.iot.demeter.domain.features.io


interface DigitalIoCallback {
    fun onFLoatSensorStated(activated:Boolean)
    fun onActuatorSet(pinName: String, on: Boolean)
}
