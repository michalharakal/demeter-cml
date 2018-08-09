package com.fiwio.iot.demeter.remote.http.mapper

import com.fiwio.iot.demeter.domain.model.schedule.ActionEvent
import com.fiwio.iot.demeter.domain.model.schedule.ActionEvents
import com.fiwio.iot.demeter.domain.model.schedule.DayTime
import com.fiwo.iot.demeter.api.model.ScheduledEvent
import com.fiwo.iot.demeter.api.model.ScheduledEvents
import org.joda.time.DateTime
import javax.inject.Inject

class SchedulesMapper @Inject constructor() {
    fun mapFromActions(excludedEvents: ActionEvents): ScheduledEvents {
        val result = ScheduledEvents()
        excludedEvents.actionEvents.map { event ->
            result.add(mapScheduledEvent(event))
        }
        return result
    }

    fun mapToActions(excludedEvents: ScheduledEvents): ActionEvents {
        return ActionEvents(
                excludedEvents.map { event ->
                    ActionEvent(
                            event.name,
                            event.fsm,
                            event.command,
                            DayTime(event.time.hour, event.time.minute, event.time.second))
                }
        )
    }

    private fun mapScheduledEvent(event: ActionEvent): ScheduledEvent {
        val result = ScheduledEvent()
        val now = DateTime()
        with(result) {
            name = event.name
            command = event.command
            fsm = event.branch
            time = mapToDayTime(event.dayTime)
        }
        return result
    }

    private fun mapToDayTime(dayTime: DayTime): com.fiwo.iot.demeter.api.model.DayTime? {
        val result = com.fiwo.iot.demeter.api.model.DayTime()
        with(result) {
            hour = dayTime.hour
            minute = dayTime.minute
            second = dayTime.second
        }
        return result
    }

}