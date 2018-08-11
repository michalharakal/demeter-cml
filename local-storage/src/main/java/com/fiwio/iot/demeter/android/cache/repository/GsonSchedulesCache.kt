package com.fiwio.iot.demeter.android.cache.repository

import com.fiwio.iot.demeter.data.model.ActionEventEntity
import com.fiwio.iot.demeter.data.model.ActionEventsEntity
import com.fiwio.iot.demeter.data.model.DayTimeEntity
import com.fiwio.iot.demeter.data.repository.SchedulesCache
import com.google.gson.Gson
import java.io.*

class GsonSchedulesCache(private val baseFolder: String, val gson: Gson) : SchedulesCache {
    override fun writeDailyActions(actions: ActionEventsEntity) {
        writeSchedule(actions)
    }

    override fun getDailyActions(): ActionEventsEntity {
        return readActions()
    }

    private fun getScheduleFullName() = baseFolder + "/schedule.json"

    private val lock: Any = Any()

    fun readActions(): ActionEventsEntity {
        synchronized(lock) {
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
        return ActionEventsEntity(listOf(
                ActionEventEntity("zahrada rano", "garden", "irrigate",
                        DayTimeEntity(7, 0, 0)),
                ActionEventEntity("zahrada vecer", "garden", "irrigate",
                        DayTimeEntity(19, 0, 0)),
                ActionEventEntity("sklenik rano", "greenhouse", "irrigate",
                        DayTimeEntity(7, 20, 0)),
                ActionEventEntity("sklenik vecer", "greenhouse", "irrigate",
                        DayTimeEntity(19, 20, 0)),
                ActionEventEntity("napousteni rano", "flowers", "fill",
                        DayTimeEntity(8, 0, 0)),
                ActionEventEntity("napousteni vecer", "flowers", "fill",
                        DayTimeEntity(20, 0, 0))
                /*ActionEventEntity("kytky vecer", "flowers", "irrigate",
                        DayTimeEntity(19, 30, 0))
                        */

        ))
    }


    fun writeSchedule(actions: ActionEventsEntity) {
        synchronized(lock) {
            writeJsonData(getScheduleFullName(), actions)
        }
    }


    fun writeJsonData(fileName: String, events: Any) {
        val sd = File(fileName)
        sd.createNewFile()

        val fOut = FileOutputStream(sd)
        val myOutWriter = OutputStreamWriter(fOut)
        val json = gson.toJson(events)
        myOutWriter.write(json)
        myOutWriter.flush()
    }
}