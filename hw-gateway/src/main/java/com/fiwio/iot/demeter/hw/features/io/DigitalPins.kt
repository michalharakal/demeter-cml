package com.fiwio.iot.demeter.hw.features.io

import com.fiwio.iot.demeter.hw.model.DigitalIO


interface DigitalPins {

    val inputs: List<DigitalIO>

    val outputs: List<DigitalIO>

    fun getInput(name: String): DigitalIO

    fun getOutput(name: String): DigitalIO

    fun registerInputCallback(callback: DigitalIoCallback)

}