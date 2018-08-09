package com.fiwio.iot.demeter.app.injection

import android.app.Application
import android.content.Context
import com.fatboyindustrial.gsonjodatime.Converters
import com.fiwio.iot.demeter.BuildConfig
import com.fiwio.iot.demeter.android.cache.repository.GsonSchedulesCache
import com.fiwio.iot.demeter.app.JobExecutor
import com.fiwio.iot.demeter.data.mapper.ActionEventsMapper
import com.fiwio.iot.demeter.data.repository.SchedulesCache
import com.fiwio.iot.demeter.data.repository.SchedulesDataRepository
import com.fiwio.iot.demeter.data.repository.SchedulesLocalDataStore
import com.fiwio.iot.demeter.discovery.NdsService
import com.fiwio.iot.demeter.domain.core.executor.ThreadExecutor
import com.fiwio.iot.demeter.domain.features.configuration.ConfigurationProvider
import com.fiwio.iot.demeter.domain.features.fsm.FsmGateway
import com.fiwio.iot.demeter.domain.features.fsm.GardenFiniteStateMachine
import com.fiwio.iot.demeter.domain.features.io.BranchesInteractorsProvider
import com.fiwio.iot.demeter.domain.features.schedule.TimeProvider
import com.fiwio.iot.demeter.domain.features.tracking.EventTracker
import com.fiwio.iot.demeter.domain.gateway.*
import com.fiwio.iot.demeter.domain.model.io.Versions
import com.fiwio.iot.demeter.domain.model.schedule.DayTime
import com.fiwio.iot.demeter.domain.repository.SchedulesRepository
import com.fiwio.iot.demeter.firebase.SendFirebaseData
import com.fiwio.iot.demeter.fsm.DemeterConfigurationProvider
import com.fiwio.iot.demeter.fsm.DemeterFsmGateway
import com.fiwio.iot.demeter.hw.features.io.DemeterBranchesInteractorProvider
import com.fiwio.iot.demeter.hw.gateway.DemeterDeviceGateway
import com.fiwio.iot.demeter.hw.gateway.MacAddressIdProvider
import com.fiwio.iot.demeter.hw.mapper.InputValueMapper
import com.fiwio.iot.demeter.hw.mapper.OutputValueMapper
import com.fiwio.iot.demeter.hw.mock.MockDigitalPins
import com.fiwio.iot.demeter.hw.model.DigitalPins
import com.fiwio.iot.demeter.hw.pifacedigital2.DemeterDigitalPins
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import mu.KotlinLogging
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.joda.time.LocalTime
import java.io.IOException
import javax.inject.Singleton


private val logger = KotlinLogging.logger {}

/**
 * Module used to provide dependencies at an application-level.
 */
@Module
open class ApplicationModule {


    @Provides
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
        return jobExecutor
    }

    @Singleton
    @Provides
    fun providOkHttpClient(application: Application): OkHttpClient {

        val cacheSize = 10 * 1024 * 1024L // 10 MB
        val cache = Cache(application.getCacheDir(), cacheSize)

        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { it -> logger.info { it } })
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .cache(cache)
                .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return Converters.registerDateTime(gsonBuilder).create()
    }

    @Provides
    @Singleton
    fun provideNdsService(application: Application): NdsService {
        return NdsService(application)
    }

    @Provides
    @Singleton
    fun providePushNotificationsGateway(okhttpClient: OkHttpClient, gson: Gson, idProvider: IdProvider): PushNotificationsGateway {
        return SendFirebaseData(okhttpClient, gson, idProvider)
    }

    @Provides
    @Singleton
    fun provideEventTracker(pushNotificationsGateway: PushNotificationsGateway): EventTracker {
        return object : EventTracker {
            override fun track(event: String) {
                logger.debug { event }
                pushNotificationsGateway.sendPush("events", event)
            }
        }
    }

    @Provides
    @Singleton
    fun provideGardenFiniteStateMachine(eventTracker: EventTracker): GardenFiniteStateMachine {
        return GardenFiniteStateMachine(eventTracker)
    }

    @Provides
    @Singleton
    fun provideDeviceGateway(device: DigitalPins): DeviceGateway {
        return DemeterDeviceGateway(device, InputValueMapper(), OutputValueMapper(),
                Versions(hw = BuildConfig.HW_VERSION, build = BuildConfig.BUILD_TIME, git = BuildConfig.GIT_SHA))
    }

    @Provides
    @Singleton
    fun provideDigitalPins(): DigitalPins {
        if (BuildConfig.MOCK_MODE) {
            return MockDigitalPins()
        } else {
            try {
                return DemeterDigitalPins()
            } catch (e: IOException) {
                return MockDigitalPins()
            }
        }
    }

    @Provides
    @Singleton
    fun provideFsmGateway(fsm: GardenFiniteStateMachine, branchesInteractorsProvider: BranchesInteractorsProvider): FsmGateway {
        return DemeterFsmGateway(fsm, branchesInteractorsProvider.get(GardenFiniteStateMachine.BRANCH_GARDEN))
    }

    @Provides
    @Singleton
    fun provideBranchesInteractorsProvider(pins: DigitalPins): BranchesInteractorsProvider {
        return DemeterBranchesInteractorProvider(pins)
    }

    @Provides
    @Singleton
    fun provideConfigurationProvider(): ConfigurationProvider {
        return DemeterConfigurationProvider()
    }

    @Provides
    @Singleton
    fun provideTimeProvider(): TimeProvider {
        return object : TimeProvider {
            override fun getCurrentTime(): DayTime {
                val date = LocalTime()
                return DayTime(date.hourOfDay, date.minuteOfHour, date.secondOfMinute)
            }
        }
    }

    @Provides
    @Singleton
    fun provideSchedulesRepository(localDataStore: SchedulesLocalDataStore,
                                   actionMapper: ActionEventsMapper): SchedulesRepository {
        return SchedulesDataRepository(localDataStore, actionMapper)

    }


    @Provides
    @Singleton
    fun provideEventsGateway(schedulesRepository: SchedulesRepository): EventsGateway {
        return DailyReoccuringEvents(schedulesRepository)
    }

    @Provides
    @Singleton
    fun provideSchedulesDataStore(demeterCache: SchedulesCache): SchedulesLocalDataStore {
        return SchedulesLocalDataStore(demeterCache)
    }

    @Provides
    @Singleton
    fun provideSchedulesCache(gson: Gson, context: Context): SchedulesCache {
        val baseCacheFolder = context.filesDir.absolutePath

        return GsonSchedulesCache(baseCacheFolder, gson)
    }

    @Provides
    @Singleton
    fun provideIdProvider(deviceGateway: DeviceGateway): IdProvider {
        return MacAddressIdProvider(deviceGateway)

    }

}
