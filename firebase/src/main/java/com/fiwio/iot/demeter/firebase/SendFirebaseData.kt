package com.fiwio.iot.demeter.firebase

import android.util.Base64
import com.fiwio.iot.demeter.domain.gateway.IdProvider
import com.fiwio.iot.demeter.domain.gateway.PushNotificationsGateway
import com.fiwio.iot.demeter.domain.model.push.Push
import com.fiwio.iot.demeter.domain.model.push.PushData
import com.google.gson.Gson
import mu.KotlinLogging
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import java.io.UnsupportedEncodingException

private val logger = KotlinLogging.logger {}

class SendFirebaseData(val okhttpClient: OkHttpClient, val gson: Gson, val idProvider: IdProvider) : PushNotificationsGateway {

    val JSON = MediaType.parse("application/json; charset=utf-8")
    val FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send"

    override fun sendPush(topic: String, data: String) {
        val body = RequestBody.create(JSON, gson.toJson(pushJsonFor(data)))
        val request = Request.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "key=AIzaSyDZj36UinaIACLjnTIMpZDG0fQY_qyrDes")
                .url(FCM_MESSAGE_URL)
                .post(body)
                .build()

        try {
            val response = okhttpClient.newCall(request).execute()
            logger.debug { "send push, response code = ${response.code()}" }
        } catch (e: IOException) {
        }

    }

    private fun pushJsonFor(data: String): Push {
        try {
            val charset = Charsets.UTF_8
            val byteArray = data.toByteArray(charset)
            return Push("/topics/events", PushData(idProvider.getDeviceId(), Base64.encodeToString(byteArray, Base64.DEFAULT)))

        } catch (e: UnsupportedEncodingException) {
            return Push("/topics/events", PushData(idProvider.getDeviceId(), ""))
        }
    }

}