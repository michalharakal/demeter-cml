package com.fiwio.iot.demeter.domain.gateway

import com.fiwio.iot.demeter.domain.model.schedule.DayTime
import com.fiwio.iot.demeter.domain.model.schedule.EitherEvent

interface EventsGateway {
    fun getEvent(currentTime: DayTime): EitherEvent
}