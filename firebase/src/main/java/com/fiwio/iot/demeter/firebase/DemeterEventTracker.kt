package com.fiwio.iot.demeter.firebase

import com.fiwio.iot.demeter.domain.features.push.SendNotification
import com.fiwio.iot.demeter.domain.features.tracking.EventTracker
import com.fiwio.iot.demeter.domain.gateway.PushNotificationsGateway
import io.reactivex.observers.DisposableSingleObserver
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class DemeterEventTracker(val pushNotificationsGateway: PushNotificationsGateway, val sendNotification: SendNotification) : EventTracker {
    override fun swimmerStatus(activated: Boolean) {
        sendNotification.execute(ResponseHandler(), "SWIMM:" + if (activated) "ACTIVE" else "INACTIVE")
    }

    inner class ResponseHandler : DisposableSingleObserver<Unit>() {
        override fun onSuccess(t: Unit) {

        }

        override fun onError(e: Throwable) {

        }
    }

    override fun trackAction(name: String, branch: String) {
        sendNotification.execute(ResponseHandler(), "ACTION:$name:$branch")
    }

    override fun setValue(pinName: String, on: Boolean) {
        sendNotification.execute(ResponseHandler(), "PIN:" + pinName + ":" + if (on) "ON" else "OFF")
    }

    override fun track(event: String) {
        logger.debug { event }
        sendNotification.execute(ResponseHandler(), event)
    }
}


