package com.fiwio.iot.demeter.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.fiwio.iot.demeter.android.ui.ext.getAppComponent
import com.fiwio.iot.demeter.android.ui.feature.main.di.MainComponent
import com.fiwio.iot.demeter.android.ui.feature.main.di.MainModule
import com.fiwio.iot.demeter.discovery.NdsService
import com.fiwio.iot.demeter.domain.features.fsm.FsmGateway
import com.fiwio.iot.demeter.events.SchedulerService
import com.fiwio.iot.demeter.fsm.DemeterFsmGateway
import com.fiwio.iot.demeter.fsm.FsmBackgroundService
import com.fiwio.iot.demeter.hw.model.DigitalIO
import com.fiwio.iot.demeter.hw.model.DigitalPins
import com.fiwio.iot.demeter.remote.http.DemeterHttpServer
import com.google.android.things.pio.PeripheralManager
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var service: NdsService

    lateinit var component: MainComponent

    @Inject
    lateinit var httpServer: DemeterHttpServer

    @Inject
    lateinit var fsmGateway: FsmGateway

    @Inject
    lateinit var digitalPins: DigitalPins

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = getAppComponent().plus(MainModule())
        component.inject(this)

        service.startServer("cml", null)

        Log.d(TAG, "Available GPIO: " + PeripheralManager.getInstance().gpioList)

        httpServer.start()


        val intent = Intent(Intent.ACTION_SYNC, null, this, FsmBackgroundService::class.java)
        startService(intent)

        SchedulerService.startScheduler(this)

        digitalPins.registerInputCallback(fsmGateway as DemeterFsmGateway);


    }

    public override fun onDestroy() {
        super.onDestroy()
        service.stopServer()
    }

    override fun getSystemService(name: String?): Any {
        return when (name) {
            "component" -> component
            else -> super.getSystemService(name)
        }
    }

    companion object {

        private val TAG = MainActivity::class.java.simpleName
    }


}
