package com.fiwio.iot.demeter.domain.gateway

interface IdProvider {
    fun getDeviceId():String
}