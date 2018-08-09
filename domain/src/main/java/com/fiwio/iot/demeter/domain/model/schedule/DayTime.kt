package com.fiwio.iot.demeter.domain.model.schedule

data class DayTime(val hour: Int, val minute: Int, val second: Int) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as DayTime
        return other.hour == hour && other.minute == minute && other.second == second
    }
}