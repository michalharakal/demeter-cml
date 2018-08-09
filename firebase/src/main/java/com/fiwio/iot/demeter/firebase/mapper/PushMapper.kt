package com.fiwio.iot.demeter.firebase.mapper

import com.fiwio.iot.demeter.domain.model.push.Push
import com.fiwio.iot.demeter.firebase.model.PushDataEntity
import com.fiwio.iot.demeter.firebase.model.PushEntity

class PushMapper {
    fun mapToEntity(push: Push): PushEntity {
        return PushEntity(push.to, PushDataEntity(title = push.data.title, message = push.data.message))
    }
}