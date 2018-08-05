package com.fiwio.iot.demeter.events

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import com.fiwio.iot.demeter.android.ui.ext.getAppComponent
import com.fiwio.iot.demeter.domain.features.fsm.FsmGateway
import com.fiwio.iot.demeter.domain.features.schedule.EventsExecutionLoop
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

    lateinit var handler : Handler


    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private val runnableCode = object : Runnable {
        override fun run() {

            //logger.debug { "Refreshing demeter status" }

            evntLoop.loop();

            //demeterRepository.refresh()
            // Repeat this the same runnable code block again another 2 seconds
            // 'this' is referencing the Runnable object
            handler.postDelayed(this, 1000)
        }
    }

    private lateinit var evntLoop: EventsExecutionLoop

    override fun onCreate() {
        super.onCreate()

        component = getAppComponent().plus(SchedulerServiceModule())
        component.inject(this)

        evntLoop =  EventsExecutionLoop(timeProvider, eventsGateway, fsmGateway)

    //    backgroundJob = SchedulerRunnable.getInstance(timeProvider, eventsGateway, fsmGateway)

    }



    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_START_SCHEDULER -> {
                startScheduler()
            }
        }
    }

    private fun startScheduler() {
        val thread = HandlerThread("RefreshHandlerThread")
        thread.start()
        handler = Handler(thread.looper)
        handler.post(runnableCode)

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
