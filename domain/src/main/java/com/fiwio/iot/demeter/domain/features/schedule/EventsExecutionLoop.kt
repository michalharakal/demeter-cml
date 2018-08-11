package com.fiwio.iot.demeter.domain.features.schedule

import com.fiwio.iot.demeter.domain.features.fsm.FsmGateway
import com.fiwio.iot.demeter.domain.features.tracking.EventTracker
import com.fiwio.iot.demeter.domain.gateway.EventsGateway
import com.fiwio.iot.demeter.domain.model.schedule.DayTime
import mu.KotlinLogging


private val logger = KotlinLogging.logger {}

class EventsExecutionLoop(val eventsGateway: EventsGateway, val fsmGateway: FsmGateway, val eventTracker: EventTracker) {
    fun handleSchedulerTick(currentTime: DayTime) {
        val event = eventsGateway.getEvent(currentTime)
        event.actionEvents.map { action ->
            logger.debug { "executing $event" }
            eventTracker.track("executing ${action.name}")
            eventTracker.trackAction(action.name, action.branch)
            fsmGateway.fireEvent(action.branch, action.command)
        }
    }
}