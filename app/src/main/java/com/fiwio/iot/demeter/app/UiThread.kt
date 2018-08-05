package com.fiwio.iot.demeter.app

import com.fiwio.iot.demeter.domain.core.executor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * MainThread (UI Thread) implementation based on rxNdsDiscovery [Scheduler]
 * which will execute actions on the Android UI thread
 */
class UiThread @Inject internal constructor() : PostExecutionThread {

    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()

}