package com.fiwio.iot.demeter.domain.model.schedule

data class ActionEvent(val name: String, val branch: String, val command: String, val dayTime: DayTime)