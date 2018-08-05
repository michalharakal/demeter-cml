package com.fiwio.iot.demeter.domain.features.fsm

import com.fiwio.iot.demeter.domain.model.fsm.StateMachines

interface FsmGateway {
    fun fireEvent(branch: String, event: String): Boolean
    fun getStatus(): StateMachines
}