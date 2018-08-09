package com.fiwio.iot.demeter.domain.gateway

interface PushNotificationsGateway {
    fun sendPush(topic: String, data: String)
}