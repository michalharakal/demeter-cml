package com.fiwio.iot.demeter.hw.features.io

import com.fiwio.iot.demeter.hw.model.DigitalIO

interface DigitalIoCallback {

    fun onGpioEdge(digitalIO: DigitalIO): Boolean

    fun onGpioError(gpio: DigitalIO, error: Int)
}
