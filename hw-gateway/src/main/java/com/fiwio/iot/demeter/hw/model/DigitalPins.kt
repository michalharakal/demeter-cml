package com.fiwio.iot.demeter.hw.model

import com.fiwio.iot.demeter.domain.features.io.DigitalIoCallback


interface DigitalPins {

    val inputs: List<DigitalIO>

    val outputs: List<DigitalIO>

    fun getInput(name: String): DigitalIO

    fun getOutput(name: String): DigitalIO

    fun registerInputCallback(callback: DigitalIoCallback)

}