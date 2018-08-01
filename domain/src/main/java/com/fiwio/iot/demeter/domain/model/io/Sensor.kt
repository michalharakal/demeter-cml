package com.fiwio.iot.demeter.domain.model.io

import com.fiwio.iot.demeter.domain.model.io.InputValue

data class Sensor(
        val name: String,
        val value: InputValue
)