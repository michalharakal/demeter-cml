package com.fiwio.iot.demeter.events

import com.fiwio.iot.demeter.domain.features.fsm.FsmGateway
import com.fiwio.iot.demeter.domain.features.schedule.EventsExecutionLoop
import com.fiwio.iot.demeter.domain.features.schedule.TimeProvider
import com.fiwio.iot.demeter.domain.gateway.EventsGateway

class SchedulerRunnable private constructor(val timeProvider: TimeProvider, val eventsGateway: EventsGateway,
                                            val fsmGateway: FsmGateway) : Runnable {

    override fun run() {
        val loop = EventsExecutionLoop(timeProvider, eventsGateway, fsmGateway)
        loop.loop()
    }

    companion object {
        private  var _instance: Thread? = null

        @Synchronized
        fun getInstance(
                timeProvider: TimeProvider, eventsGateway: EventsGateway, fsmGateway: FsmGateway): Thread {
            if (_instance == null) {
                _instance = Thread(SchedulerRunnable(timeProvider, eventsGateway, fsmGateway))
            }
            return _instance!!
        }
    }
}
