package com.fiwio.iot.demeter.android.cache.repository

import com.fiwio.iot.demeter.data.model.ActionEventsEntity
import com.fiwio.iot.demeter.data.repository.SchedulesCache
import com.google.gson.Gson
import java.io.*

class GsonSchedulesCache(private val baseFolder: String, val gson: Gson) : SchedulesCache {
    override fun writeDailyActions(actions: ActionEventsEntity) {
        writeSchedule(actions)
    }

    override fun getDailyActions(): ActionEventsEntity {
        return ActionEventsEntity(emptyList())
    }

    private fun getScheduleFullName() = baseFolder + "/schedule.json"

    private val LOCK: Any = Any()

    fun readActions(): ActionEventsEntity {
        synchronized(LOCK) {
            val sd = File(getScheduleFullName())
            if (sd.exists()) {
                val inputStream = FileInputStream(sd)
                val reader = InputStreamReader(inputStream)
                return gson.fromJson(reader, ActionEventsEntity::class.java)
            } else {
                return createDefaultSchedule()
            }
        }
    }

    private fun createDefaultSchedule(): ActionEventsEntity {
        return ActionEventsEntity(emptyList())
    }


    fun writeSchedule(actions: ActionEventsEntity) {
        synchronized(LOCK) {
            writeList(getScheduleFullName(), actions)
        }
    }


    fun writeList(fileName: String, events: Any) {
        val sd = File(fileName)
        sd.createNewFile()

        val fOut = FileOutputStream(sd)
        val myOutWriter = OutputStreamWriter(fOut)
        val json = gson.toJson(events)
        myOutWriter.write(json)
        myOutWriter.flush()
    }
}