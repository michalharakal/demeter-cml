package com.fiwio.iot.demeter.data.mapper

import com.fiwio.iot.demeter.data.model.ActionEventEntity
import com.fiwio.iot.demeter.data.model.ActionEventsEntity
import com.fiwio.iot.demeter.data.model.DayTimeEntity
import com.fiwio.iot.demeter.domain.model.schedule.ActionEvent
import com.fiwio.iot.demeter.domain.model.schedule.ActionEvents
import com.fiwio.iot.demeter.domain.model.schedule.DayTime
import org.dukecon.data.mapper.Mapper
import javax.inject.Inject

class ActionEventsMapper @Inject constructor() : Mapper<ActionEventsEntity, ActionEvents> {
    override fun mapFromEntity(type: ActionEventsEntity): ActionEvents {
        return ActionEvents(type.actions.map { it ->
            mapFromActionEntity(it)
        })
    }

    private fun mapFromActionEntity(actionEventEntity: ActionEventEntity): ActionEvent {
        with(actionEventEntity) {
            return ActionEvent(
                    name = this.name,
                    branch = this.branch,
                    command = this.command,
                    dayTime = mapFromdayTimeEntitty(this.daytime))
        }
    }

    private fun mapFromdayTimeEntitty(daytime: DayTimeEntity): DayTime {
        return DayTime(daytime.hour, daytime.minute, daytime.second)
    }

    override fun mapToEntity(type: ActionEvents): ActionEventsEntity {
        return ActionEventsEntity(
                type.actionEvents.map { it ->
                    mapToActionENtity(it)
                }
        )
    }

    private fun mapToActionENtity(actionEvent: ActionEvent): ActionEventEntity {
        with(actionEvent) {
            return ActionEventEntity(
                    name = this.name,
                    branch = this.branch,
                    command = this.command,
                    daytime = mapToDayTimeEntity(this.dayTime)
            )
        }
    }

    private fun mapToDayTimeEntity(dayTime: DayTime): DayTimeEntity {
        return DayTimeEntity(dayTime.hour, dayTime.minute, dayTime.second)
    }

}