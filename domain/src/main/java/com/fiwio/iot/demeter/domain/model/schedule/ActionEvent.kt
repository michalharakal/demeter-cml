package com.fiwio.iot.demeter.domain.model.schedule

data class ActionEvent(val id: Long, val branch: String, val command: String, val dayTime: DayTime)