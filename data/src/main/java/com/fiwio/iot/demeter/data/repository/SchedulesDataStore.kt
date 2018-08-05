package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.data.model.ActionEventsEntity

interface SchedulesDataStore {
    fun getDailyActions(): ActionEventsEntity
    fun writeDailyActions(actions:ActionEventsEntity)

}