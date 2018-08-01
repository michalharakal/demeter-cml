package com.fiwio.iot.demeter.domain.features.fsm

interface FsmGateway {
    fun fireEvent(branch: String, event: String): Boolean
    fun fireAlwaysEvent(branch: String, event: String): Boolean
}