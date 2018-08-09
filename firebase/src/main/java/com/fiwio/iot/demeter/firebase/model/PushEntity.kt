package com.fiwio.iot.demeter.firebase.model

import com.fiwio.iot.demeter.domain.model.push.PushData

data class PushEntity(val to:String, val data: PushDataEntity)