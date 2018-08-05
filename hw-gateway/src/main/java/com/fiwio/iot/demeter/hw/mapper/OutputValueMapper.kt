package com.fiwio.iot.demeter.hw.mapper

import com.fiwio.iot.demeter.hw.model.DigitalValue

class OutputValueMapper {
    fun map(digitalValue: DigitalValue): Boolean {
        return (digitalValue == DigitalValue.ON)
    }
}