package com.fiwio.iot.demeter.domain.model.io

data class Demeter(
        val actuators: List<Actuator>,
        val sensors: List<Sensor>
)

