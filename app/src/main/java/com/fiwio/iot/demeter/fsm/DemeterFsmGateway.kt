package com.fiwio.iot.demeter.fsm

import com.fiwio.iot.demeter.domain.features.fsm.FsmGateway
import com.fiwio.iot.demeter.domain.features.fsm.GardenFiniteStateMachine
import com.fiwio.iot.demeter.domain.model.fsm.StateMachine
import com.fiwio.iot.demeter.domain.model.fsm.StateMachines
import com.fiwio.iot.demeter.domain.features.io.DigitalIoCallback
import com.fiwio.iot.demeter.domain.features.io.IOInteractor

class DemeterFsmGateway(val fsm: GardenFiniteStateMachine, val ioInteractor: IOInteractor) : FsmGateway, DigitalIoCallback {
    override fun onFLoatSensorActivated() {
        var stopped = false
        if (!fsm.stopFillingBranches()) {
            ioInteractor.barrelPumpOff()
        }
    }

    private fun isFilling(name: String): Boolean {
        if (name == GardenFiniteStateMachine.States.BARREL_FILLING.name) {
            return true
        }
        if (name == GardenFiniteStateMachine.States.BARREL_FILLING_OPENING.name) {
            return true
        }
        return false
    }

    override fun fireEvent(branch: String, event: String): Boolean {
        return fsm.trigger(branch, event)
    }

    override fun getStatus(): StateMachines {
        return StateMachines(
                fsm.branches.map { branchContext ->
                    StateMachine(branchContext.id, fsm.getState(branchContext).text)
                },
                GardenFiniteStateMachine.Events.values().map {
                    it.text
                }
        )
    }
}