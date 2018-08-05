package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.data.model.ActionEventsEntity

class SchedulesLocalDataStore(private val demeterCache: SchedulesCache) : SchedulesDataStore {
    override fun writeDailyActions(actions: ActionEventsEntity) {
        demeterCache.writeDailyActions(actions)
    }

    override fun getDailyActions(): ActionEventsEntity {
        return demeterCache.getDailyActions()
    }
}
