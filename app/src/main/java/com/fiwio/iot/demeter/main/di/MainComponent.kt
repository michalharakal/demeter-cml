package com.fiwio.iot.demeter.android.ui.feature.main.di

import com.fiwio.iot.demeter.main.MainActivity
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(MainModule::class))
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}