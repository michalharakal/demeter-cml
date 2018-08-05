package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.data.mapper.ActionEventsMapper
import com.fiwio.iot.demeter.domain.model.schedule.ActionEvents
import com.fiwio.iot.demeter.domain.repository.SchedulesRepository

class SchedulesDataRepository : SchedulesRepository {
    override fun writeDailyActions(actions: ActionEvents) {
        localDataStore.writeDailyActions(actionMapper.mapToEntity(actions))
    }

    val localDataStore: SchedulesLocalDataStore
    val actionMapper: ActionEventsMapper

    constructor(localDataStore: SchedulesLocalDataStore, actionMapper: ActionEventsMapper) {
        this.localDataStore = localDataStore
        this.actionMapper = actionMapper
    }

    override fun getDailyEvents(): ActionEvents {
        return actionMapper.mapFromEntity(localDataStore.getDailyActions())
    }
}