package com.fiwio.iot.demeter.domain.features.configuration

import com.fiwio.iot.demeter.domain.model.fsm.BranchValveParameters

interface ConfigurationProvider {
    fun getBranchParameters(branch: String): BranchValveParameters
}
