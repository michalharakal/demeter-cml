package com.fiwio.iot.demeter.fsm

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import com.fiwio.iot.demeter.android.ui.ext.getAppComponent
import com.fiwio.iot.demeter.android.ui.feature.refresh.di.RefreshServiceComponent
import com.fiwio.iot.demeter.android.ui.feature.refresh.di.RefreshServiceModule
import com.fiwio.iot.demeter.domain.features.configuration.ConfigurationProvider
import com.fiwio.iot.demeter.domain.features.fsm.GardenFiniteStateMachine
import com.fiwio.iot.demeter.domain.features.io.BranchesInteractorsProvider
import com.fiwio.iot.demeter.remote.http.DemeterHttpServer
import mu.KotlinLogging
import java.io.IOException
import javax.inject.Inject

private val logger = KotlinLogging.logger {}

class FsmBackgroundService : IntentService {

    private lateinit var backgroundJob: Thread


    @Inject
    lateinit var interactorsProvider: BranchesInteractorsProvider

    @Inject
    lateinit var configurationProvider: ConfigurationProvider

    @Inject
    lateinit var fsm: GardenFiniteStateMachine

    lateinit var component: RefreshServiceComponent

    private var httpServerRunning = false

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        component = getAppComponent().plus(RefreshServiceModule())
        component.inject(this)

        backgroundJob = FsmRunnable.getInstance(fsm, configurationProvider, interactorsProvider)
        backgroundJob.start()
    }


    /**
     * Creates an IntentService. Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    constructor(name: String) : super(name) {}

    /**
     * Creates an IntentService. Required by AndroidManifest.xml
     */
    constructor() : super(LOG_TAG) {}

    override fun onHandleIntent(intent: Intent?) {
        if (!httpServerRunning) {
            try {
                //httpServer.start()
                httpServerRunning = true
            } catch (e: IOException) {

            }

        }
    }

    companion object {
        private val LOG_TAG = FsmBackgroundService::class.java.simpleName
    }
}
