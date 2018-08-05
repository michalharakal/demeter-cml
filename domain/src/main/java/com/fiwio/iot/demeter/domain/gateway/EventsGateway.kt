package com.fiwio.iot.demeter.domain.gateway

import com.fiwio.iot.demeter.domain.model.schedule.ActionEvent
import com.fiwio.iot.demeter.domain.model.schedule.DayTime

interface EventsGateway {
    fun getEvent(currentTime: DayTime): ActionEvent
}