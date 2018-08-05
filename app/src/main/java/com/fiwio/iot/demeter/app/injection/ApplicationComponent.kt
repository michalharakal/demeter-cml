package com.fiwio.iot.demeter.android.ui.injection

import android.app.Application
import com.fiwio.iot.demeter.android.ui.feature.main.di.MainComponent
import com.fiwio.iot.demeter.android.ui.feature.main.di.MainModule
import com.fiwio.iot.demeter.android.ui.feature.refresh.di.RefreshServiceComponent
import com.fiwio.iot.demeter.android.ui.feature.refresh.di.RefreshServiceModule
import com.fiwio.iot.demeter.app.DemeterApplication
import com.fiwio.iot.demeter.events.di.SchedulerServiceComponent
import com.fiwio.iot.demeter.events.di.SchedulerServiceModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, DataModule::class))
interface ApplicationComponent {

    fun inject(demeterApplication: DemeterApplication)

    fun plus(mainModule: MainModule): MainComponent
    fun plus(mainModule: RefreshServiceModule): RefreshServiceComponent
    fun plus(schedulerServiceModule: SchedulerServiceModule): SchedulerServiceComponent

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}
