package com.fiwio.iot.demeter.fsm

import com.fiwio.iot.demeter.domain.features.configuration.ConfigurationProvider
import com.fiwio.iot.demeter.domain.features.fsm.GardenFiniteStateMachine
import com.fiwio.iot.demeter.domain.model.fsm.BranchValveParameters
import java.util.*

class DemeterConfigurationProvider() : ConfigurationProvider {
    private val branches = HashMap<String, BranchValveParameters>()

    init {
        createGarden()
        createFlowers()
        createGreenhouse()
    }

    private fun createGarden() {
        branches[GardenFiniteStateMachine.BRANCH_GARDEN] = BranchValveParameters(
                openingDurationSec = TWENTY_SECS,
                fillingDurationSec = THIRTY_MINUTES_IN_SECS,
                actionDurationSec = FIVETEEN_MINUTES_IN_SECS)
    }

    private fun createFlowers() {
        branches[GardenFiniteStateMachine.BRANCH_FLOWERS] = BranchValveParameters(
                openingDurationSec = TWENTY_SECS,
                fillingDurationSec = THIRTY_MINUTES_IN_SECS,
                actionDurationSec = TWO_MINUTES_IN_SECS)
    }

    private fun createGreenhouse() {
        branches[GardenFiniteStateMachine.BRANCH_GREENHOUSE] = BranchValveParameters(
                openingDurationSec = TWENTY_SECS,
                fillingDurationSec = THIRTY_MINUTES_IN_SECS,
                actionDurationSec = FIVETEEN_MINUTES_IN_SECS)
    }


    override fun getBranchParameters(branch: String): BranchValveParameters {
        return branches[branch]!!
    }

    companion object {

        private val FIVE_MINUTES_IN_SECS = (5 * 60).toLong()
        private val THIRTY_MINUTES_IN_SECS = (30 * 60).toLong()
        private val FIVETEEN_MINUTES_IN_SECS= (15 * 60).toLong()
        private val TWO_MINUTES_IN_SECS= (15 * 60).toLong()
        private val TWENTY_SECS: Long = 20
    }
}
