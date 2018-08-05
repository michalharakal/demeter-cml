package com.fiwio.iot.demeter.data.source

import com.fiwio.iot.demeter.data.model.ActionEventsEntity

interface SchedulesDataSource {
    fun getDailyActions(): ActionEventsEntity
}
