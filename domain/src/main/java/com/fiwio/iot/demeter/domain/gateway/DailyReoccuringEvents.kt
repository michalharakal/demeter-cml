package com.fiwio.iot.demeter.domain.gateway

import com.fiwio.iot.demeter.domain.model.schedule.ActionEvents
import com.fiwio.iot.demeter.domain.model.schedule.DayTime
import com.fiwio.iot.demeter.domain.repository.SchedulesRepository

class DailyReoccuringEvents(val actionsRepository: SchedulesRepository) : EventsGateway {
    override fun getEvent(currentTime: DayTime): ActionEvents {
        return ActionEvents(
                actionsRepository.getDailyEvents().actionEvents.filter { event ->
                    event.dayTime.equals(currentTime)
                }
        )
    }
}