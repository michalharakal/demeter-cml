package com.fiwio.iot.demeter.remote.http

import com.fatboyindustrial.gsonjodatime.Converters
import com.fiwio.iot.demeter.domain.features.fsm.FsmGateway
import com.fiwio.iot.demeter.domain.features.tracking.EventTracker
import com.fiwio.iot.demeter.domain.gateway.DeviceGateway
import com.fiwio.iot.demeter.domain.model.fsm.StateMachines
import com.fiwio.iot.demeter.domain.model.io.Actuator
import com.fiwio.iot.demeter.domain.repository.SchedulesRepository
import com.fiwio.iot.demeter.remote.http.mapper.DemeterMapper
import com.fiwio.iot.demeter.remote.http.mapper.SchedulesMapper
import com.fiwo.iot.demeter.api.model.ScheduledEvents
import com.fiwo.iot.demeter.api.model.TriggerEvent
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fi.iki.elonen.NanoHTTPD
import java.io.IOException
import java.util.*
import javax.inject.Inject

class DemeterHttpServer @Inject constructor(
        private val deviceGateway: DeviceGateway, private val fsmGateway: FsmGateway,
        private val demeterMapper: DemeterMapper, private val eventTracker: EventTracker,
        private val schedulesRepository: SchedulesRepository,
        val scheduleMapper: SchedulesMapper)
    : NanoHTTPD(8080) {


    private val gson: Gson

    init {
        gson = Converters.registerDateTime(GsonBuilder()).create()
    }


    override fun serve(session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response {
        val path = session.uri

        val method = session.method

        if (method == NanoHTTPD.Method.GET) {
            if (path.contains("demeter")) {
                return createJsonResponseFromObject(getDemeterStatus())
            }
            if (path.contains("fsm")) {
                return createJsonResponseFromObject(getFsmStatus())
            }
            if (path.contains("schedule")) {
                return createJsonResponseFromObject(getScheduleStatus())
            }
        }
        if (NanoHTTPD.Method.DELETE == method) {
            if (path.contains("schedule")) {
                /*
                String[] segments = session.getUri().split("/");

                reminderEngine.removeReminderById(Integer.valueOf(segments[2]));
                return newFixedLengthResponse(
                        Response.Status.OK, "application/javascript", getScheduleStatus());
                        */
            }
        }

        if (NanoHTTPD.Method.PUT == method || NanoHTTPD.Method.POST == method) {
            try {
                val files = HashMap<String, String>()
                session.parseBody(files)

                //  POST body
                val postBody = files["postData"]
                eventTracker.track("processing ${method.name.toUpperCase()} on $path with $postBody ")
                if (path.contains("demeter")) {
                    postDemeterRequest(
                            gson.fromJson<com.fiwo.iot.demeter.api.model.Demeter>(
                                    postBody, com.fiwo.iot.demeter.api.model.Demeter::class.java
                            )
                    )
                }
                if (path.contains("fsm")) {
                    postFsmRequest(gson.fromJson<TriggerEvent>(postBody, TriggerEvent::class.java))
                    return createJsonResponseFromObject(getFsmStatus())
                }
                /*
                if (path.contains("schedule")) {

                    try {
                        processTaskRequest(gson.fromJson<Task>(postBody, Task::class.java!!))
                    } catch (e: JsonSyntaxException) {
                        val task = Task()
                        val dateTime = DateTime()
                        dateTime.plusMinutes(1)
                        task.setTime(dateTime)
                        task.setCommand("close")
                        task.setFsm("garden")
                        processTaskRequest(task)
                    }

                    return NanoHTTPD.newFixedLengthResponse(
                            NanoHTTPD.Response.Status.OK, "application/javascript", getScheduleStatus())
                }

                if (path.contains("config")) {
                    processConfigurationRequest(gson.fromJson<ModelConfiguration>(postBody, ModelConfiguration::class.java!!))
                    return NanoHTTPD.newFixedLengthResponse(
                            NanoHTTPD.Response.Status.OK, "application/javascript", getConfigStatus())
                }
                */

            } catch (ioe: IOException) {
                return NanoHTTPD.newFixedLengthResponse(
                        NanoHTTPD.Response.Status.INTERNAL_ERROR,
                        NanoHTTPD.MIME_PLAINTEXT,
                        "SERVER INTERNAL ERROR: IOException: " + ioe.message)
            } catch (re: NanoHTTPD.ResponseException) {
                return NanoHTTPD.newFixedLengthResponse(re.status, NanoHTTPD.MIME_PLAINTEXT, re.message)
            }

        }

        // demeter status as default response
        return createJsonResponseFromObject(getDemeterStatus())
    }


    private fun getScheduleStatus(): ScheduledEvents {
        return scheduleMapper.map(schedulesRepository.getDailyEvents())
    }

    private fun postFsmRequest(fromJson: TriggerEvent) {
        fsmGateway.fireEvent(branch = fromJson.fsm, event = fromJson.command)
    }

    private fun getDemeterStatus(): com.fiwo.iot.demeter.api.model.Demeter {
        return demeterMapper.map(deviceGateway.getDeviceImage(), deviceGateway.getVersions(), deviceGateway.getNetwork())
    }

    private fun getFsmStatus(): StateMachines {
        return fsmGateway.getStatus()
    }


    private fun createJsonResponseFromObject(obj: Any): Response {
        return newFixedLengthResponse(Response.Status.OK, "application/javascript", gson.toJson(obj))
    }

    private fun postDemeterRequest(demeter: com.fiwo.iot.demeter.api.model.Demeter) {
        for (relay in demeter.relays) {
            deviceGateway.switchActuator(Actuator(relay.name, relay.value != "OFF"))
        }
    }
}


/*
private void processTaskRequest(Task task) {
    Log.d(TAG, "processing" + gson.toJson(task));
    Log.d(TAG, "current date time=" + new DateTime().toString());
    reminderEngine.createNewReminder(task.getTime().getMillis(), task.getCommand(), task.getFsm());
}
*/

/*
private String getScheduleStatus() {
    ScheduledEvents scheduledEvents = new ScheduledEvents();

    for (Reminder reminder : reminderEngine.getReminders()) {
        ScheduledEvent scheduledEvent = new ScheduledEvent();

        DateTime dt = new DateTime(reminder.getTimestamp(), DateTimeZone.UTC);
        scheduledEvent.setTime(dt.toDateTime());
        scheduledEvent.setCommand(reminder.getJobName());
        scheduledEvent.setId(reminder.getId());
        scheduledEvent.setFsm(reminder.getBranchName());

        scheduledEvents.add(scheduledEvent);
    }
    return gson.toJson(scheduledEvents);
}


private String getConfigStatus() {
    return gson.toJson(confgurationEngine.getConfiguration());
}

private void processConfigurationRequest(ModelConfiguration newConfiguration) {
    Log.d(TAG, "processing" + gson.toJson(newConfiguration));
    confgurationEngine.setConfiguration(newConfiguration);
}




*/
