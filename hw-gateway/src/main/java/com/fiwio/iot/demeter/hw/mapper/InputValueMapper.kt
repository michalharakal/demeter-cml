package com.fiwio.iot.demeter.hw.mapper

import com.fiwio.iot.demeter.domain.model.io.InputValue
import com.fiwio.iot.demeter.hw.model.DigitalValue

class InputValueMapper {
    fun map(digitalValue: DigitalValue): InputValue {
        return if (digitalValue == DigitalValue.ON) InputValue.ON else InputValue.OFF
    }
}