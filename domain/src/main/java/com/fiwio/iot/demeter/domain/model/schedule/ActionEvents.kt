package com.fiwio.iot.demeter.domain.model.schedule

data class ActionEvents(val actionEvents: List<ActionEvent>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as ActionEvents
        return other.actionEvents.equals(actionEvents)
    }
}