package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.domain.model.fsm.BranchValveParameters
import com.fiwio.iot.demeter.domain.repository.ConfigurationRespository
import javax.inject.Inject


class DemeterConfigurationRepository @Inject constructor() : ConfigurationRespository {

    private val tenMinutesInSeconds = 10 * 60L
    private val twentySeconds = 20L

    private var params: MutableMap<String, BranchValveParameters> = HashMap()

    private var defaultValveParameters: BranchValveParameters =
            BranchValveParameters(twentySeconds, tenMinutesInSeconds, tenMinutesInSeconds)

    override fun getValveParameters(branchId: String): BranchValveParameters {
        return params[branchId] ?: defaultValveParameters
    }

    override fun setValveParameters(branchId: String, newValues: BranchValveParameters) {
        params[branchId] = newValues
    }
}
