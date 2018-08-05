package com.fiwio.iot.demeter.hw.features.io

import com.fiwio.iot.demeter.domain.features.io.IOInteractor
import com.fiwio.iot.demeter.hw.model.DigitalIO
import com.fiwio.iot.demeter.hw.model.DigitalPins
import com.fiwio.iot.demeter.hw.model.DigitalValue


internal class DemeterIoInteractor(demeter: DigitalPins, branchVentilName: String) : IOInteractor {
    override fun barrelPumpOff() {
        barrel_pump.value = DigitalValue.OFF
    }

    private val barrel_input: DigitalIO
    private val barrel_pump: DigitalIO
    private val branch_valve: DigitalIO

    init {
        barrel_input = demeter.getInput("INP0")
        barrel_pump = demeter.getOutput("BCM23")
        branch_valve = demeter.getOutput(branchVentilName)
    }

    override fun swimmerIsInactive(): Boolean {
        return (barrel_input.value == DigitalValue.ON)
    }

    override fun barrelPumpOn() {
        barrel_pump.value = DigitalValue.ON
    }

    override fun openBarrel() {
        branch_valve.value = DigitalValue.ON
    }

    override fun closeAllVentils() {
        barrel_pump.value = DigitalValue.OFF
        branch_valve.value = DigitalValue.OFF
    }
}
