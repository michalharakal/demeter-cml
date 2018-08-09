package com.fiwio.iot.demeter.app

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.fiwio.iot.demeter.app.injection.ApplicationComponent
import com.fiwio.iot.demeter.app.injection.DaggerApplicationComponent


class DemeterApplication : MultiDexApplication() {
    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent
                .builder()
                .application(this)
                .build()

        component.inject(this)

        AndroidLoggingHandler.reset(AndroidLoggingHandler())
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun getSystemService(name: String?): Any {
        return when (name) {
            "component" -> component
            else -> super.getSystemService(name)
        }
    }

}
