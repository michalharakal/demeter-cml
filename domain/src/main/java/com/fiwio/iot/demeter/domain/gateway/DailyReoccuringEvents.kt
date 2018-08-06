package com.fiwio.iot.demeter.domain.gateway

import com.fiwio.iot.demeter.domain.model.schedule.ActionEvent
import com.fiwio.iot.demeter.domain.model.schedule.DayTime
import com.fiwio.iot.demeter.domain.model.schedule.EitherEvent
import com.fiwio.iot.demeter.domain.repository.SchedulesRepository

class DailyReoccuringEvents(actionsRepository: SchedulesRepository) : EventsGateway {
    override fun getEvent(currentTime: DayTime): EitherEvent {
        return EitherEvent(false, ActionEvent("dummy", "", "", DayTime(0, 0, 0)))
    }
}