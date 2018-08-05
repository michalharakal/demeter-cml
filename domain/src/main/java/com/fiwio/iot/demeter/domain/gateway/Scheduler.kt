package com.fiwio.iot.demeter.domain.gateway

import com.fiwio.iot.demeter.domain.model.schedule.ActionEvents

interface Scheduler {
    fun excludedEvents(): ActionEvents
}