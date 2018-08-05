package com.fiwio.iot.demeter.app

import android.app.Application
import com.fiwio.iot.demeter.android.ui.injection.ApplicationComponent
import com.fiwio.iot.demeter.android.ui.injection.ApplicationModule
import com.fiwio.iot.demeter.android.ui.injection.DaggerApplicationComponent

class DemeterApplication : Application() {
    lateinit var component: ApplicationComponent

    private object Holder {  lateinit var INSTANCE:Application }

    companion object {
        val instance: Application by lazy { Holder.INSTANCE }
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent
                .builder()
                .application(this)
                .build()

        component.inject(this)
        Holder.INSTANCE = this

        AndroidLoggingHandler.reset(AndroidLoggingHandler())
    }

    override fun getSystemService(name: String?): Any {
        return when (name) {
            "component" -> component
            else -> super.getSystemService(name)
        }
    }

}
