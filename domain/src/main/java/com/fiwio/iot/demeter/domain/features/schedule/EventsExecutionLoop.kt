package com.fiwio.iot.demeter.domain.features.schedule

import com.fiwio.iot.demeter.domain.features.fsm.FsmGateway
import com.fiwio.iot.demeter.domain.gateway.EventsGateway
import mu.KotlinLogging


private val logger = KotlinLogging.logger {}

class EventsExecutionLoop(val timeProvider: TimeProvider, val eventsGateway: EventsGateway, val fsmGateway: FsmGateway) {
    fun loop() {
        val currentTime = timeProvider.getCurrentTime()
        val event = eventsGateway.getEvent(currentTime)
        if (event.branch.length > 0) {
            fsmGateway.fireEvent(event.branch, event.command)
        }
        Thread.sleep(1000)
    }
}