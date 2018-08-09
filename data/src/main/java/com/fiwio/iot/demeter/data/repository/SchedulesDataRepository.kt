package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.data.mapper.ActionEventsMapper
import com.fiwio.iot.demeter.domain.model.schedule.ActionEvents
import com.fiwio.iot.demeter.domain.repository.SchedulesRepository

class SchedulesDataRepository(val localDataStore: SchedulesDataStore, val actionMapper: ActionEventsMapper) : SchedulesRepository {

    override fun getDailyEvents(): ActionEvents {
        return actionMapper.mapFromEntity(localDataStore.getDailyActions())
    }

    override fun writeDailyActions(actions: ActionEvents) {
        localDataStore.writeDailyActions(actionMapper.mapToEntity(actions))
    }
}