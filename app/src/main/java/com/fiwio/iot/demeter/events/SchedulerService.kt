package com.fiwio.iot.demeter.events

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.fiwio.iot.demeter.android.ui.ext.getAppComponent
import com.fiwio.iot.demeter.domain.features.fsm.FsmGateway
import com.fiwio.iot.demeter.domain.features.schedule.TimeProvider
import com.fiwio.iot.demeter.domain.gateway.EventsGateway
import com.fiwio.iot.demeter.events.di.SchedulerServiceComponent
import com.fiwio.iot.demeter.events.di.SchedulerServiceModule
import mu.KotlinLogging
import javax.inject.Inject

private const val ACTION_START_SCHEDULER = "com.fiwio.iot.demeter.schedule.action.START_SCHEDULER"

private val logger = KotlinLogging.logger {}

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * helper methods.
 */
class SchedulerService : IntentService("SchedulerService") {

    lateinit var component: SchedulerServiceComponent

    @Inject
    lateinit var timeProvider: TimeProvider

    @Inject
    lateinit var eventsGateway: EventsGateway

    @Inject
    lateinit var fsmGateway: FsmGateway


    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private lateinit var backgroundJob: Thread

    override fun onCreate() {
        super.onCreate()

        component = getAppComponent().plus(SchedulerServiceModule())
        component.inject(this)

        backgroundJob = SchedulerRunnable.getInstance(timeProvider, eventsGateway, fsmGateway)

    }

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_START_SCHEDULER -> {
                startScheduler()
            }
        }
    }

    private fun startScheduler() {
        backgroundJob.start()
    }

    companion object {
        /**
         * Starts this service to perform action Foo with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        // TODO: Customize helper method
        @JvmStatic
        fun startScheduler(context: Context) {
            val intent = Intent(context, SchedulerService::class.java).apply {
                action = ACTION_START_SCHEDULER
            }
            context.startService(intent)
        }
    }
}
