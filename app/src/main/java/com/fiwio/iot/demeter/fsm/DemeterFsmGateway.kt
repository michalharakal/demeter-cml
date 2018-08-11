package com.fiwio.iot.demeter.fsm

import com.fiwio.iot.demeter.domain.features.fsm.FsmGateway
import com.fiwio.iot.demeter.domain.features.fsm.GardenFiniteStateMachine
import com.fiwio.iot.demeter.domain.features.io.DigitalIoCallback
import com.fiwio.iot.demeter.domain.features.io.IOInteractor
import com.fiwio.iot.demeter.domain.features.tracking.EventTracker
import com.fiwio.iot.demeter.domain.model.fsm.StateMachine
import com.fiwio.iot.demeter.domain.model.fsm.StateMachines

class DemeterFsmGateway(val fsm: GardenFiniteStateMachine, val ioInteractor: IOInteractor,
                        val eventTracker: EventTracker) : FsmGateway, DigitalIoCallback {
    override fun onFLoatSensorStated(activated: Boolean) {
        eventTracker.swimmerStatus(activated)
        if (activated) {
            if (!fsm.stopFillingBranches()) {
                ioInteractor.barrelPumpOff()
            }
        }
    }

    override fun onActuatorSet(pinName: String, on: Boolean) {
        eventTracker.setValue(pinName, on)
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