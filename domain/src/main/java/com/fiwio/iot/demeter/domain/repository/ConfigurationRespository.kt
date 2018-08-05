package com.fiwio.iot.demeter.domain.repository

import com.fiwio.iot.demeter.domain.model.fsm.BranchValveParameters

interface ConfigurationRespository {
    fun getValveParameters(branchId: String): BranchValveParameters
    fun setValveParameters(branchId: String, newValues: BranchValveParameters)
}
