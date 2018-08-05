package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.data.model.ActionEventsEntity

interface SchedulesCache {
    fun getDailyActions(): ActionEventsEntity
    fun writeDailyActions(actions:ActionEventsEntity)
}