package com.fiwio.iot.demeter.hw.model

interface DigitalIO {
    var value: DigitalValue
    val name: String
    val type: DigitalIoType
}
