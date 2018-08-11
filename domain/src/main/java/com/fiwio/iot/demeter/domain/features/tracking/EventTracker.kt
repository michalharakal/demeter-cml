package com.fiwio.iot.demeter.domain.features.tracking

interface EventTracker {
    fun track(event: String)
    fun setValue(pinName: String, on: Boolean)
    fun trackAction(name: String, branch: String)
}