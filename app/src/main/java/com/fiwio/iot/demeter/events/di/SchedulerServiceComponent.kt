package com.fiwio.iot.demeter.events.di

import com.fiwio.iot.demeter.events.SchedulerService
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(SchedulerServiceModule::class))
interface SchedulerServiceComponent {
    fun inject(schedulerService: SchedulerService)
}


