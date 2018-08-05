package com.fiwio.iot.demeter.domain.repository

import com.fiwio.iot.demeter.domain.model.schedule.ActionEvents


interface SchedulesRepository {
    val excludedEvents: ActionEvents
}