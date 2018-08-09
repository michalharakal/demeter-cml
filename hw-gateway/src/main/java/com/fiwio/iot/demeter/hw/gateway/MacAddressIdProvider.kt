package com.fiwio.iot.demeter.hw.gateway

import android.util.Base64
import com.fiwio.iot.demeter.domain.gateway.DeviceGateway
import com.fiwio.iot.demeter.domain.gateway.IdProvider

class MacAddressIdProvider(val deviceGateway: DeviceGateway) : IdProvider {
    override fun getDeviceId(): String {
        val charset = Charsets.UTF_8
        val byteArray = deviceGateway.getNetwork().mac.toByteArray(charset)
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}