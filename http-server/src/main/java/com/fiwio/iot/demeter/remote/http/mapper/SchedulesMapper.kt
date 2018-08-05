package com.fiwio.iot.demeter.remote.http.mapper

import com.fiwio.iot.demeter.domain.model.schedule.ActionEvent
import com.fiwio.iot.demeter.domain.model.schedule.ActionEvents
import com.fiwo.iot.demeter.api.model.ScheduledEvent
import com.fiwo.iot.demeter.api.model.ScheduledEvents
import org.joda.time.DateTime
import javax.inject.Inject

class SchedulesMapper @Inject constructor() {
    fun map(excludedEvents: ActionEvents): ScheduledEvents {
        val result = ScheduledEvents()
        excludedEvents.actionEvents.map { event ->
            result.add(mapScheduledEvent(event))
        }
        return result
    }

    private fun mapScheduledEvent(event: ActionEvent): ScheduledEvent {
        val result = ScheduledEvent()
        with(result) {
            id = 0
            command = event.command
            fsm = event.branch
            time = DateTime.now()
        }
        return result
    }
}