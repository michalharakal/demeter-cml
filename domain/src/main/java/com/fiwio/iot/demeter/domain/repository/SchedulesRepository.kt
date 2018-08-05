package com.fiwio.iot.demeter.domain.repository

import com.fiwio.iot.demeter.domain.model.schedule.ActionEvents


interface SchedulesRepository {
    fun getDailyEvents(): ActionEvents
    fun writeDailyActions(actions:ActionEvents)
}