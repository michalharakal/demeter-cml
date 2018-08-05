package com.fiwio.iot.demeter.domain.features.fsm.usecase

import com.fiwio.iot.demeter.domain.core.interactor.UseCase
import com.fiwio.iot.demeter.domain.features.fsm.FsmGateway
import com.fiwio.iot.demeter.domain.model.fsm.StateMachines
import com.fiwio.iot.demeter.domain.model.io.Demeter
import javax.inject.Inject

/**
 * Use case used for retreiving a [Demeter] instances from the [DemeterRepository]
 */
open class GetFsm @Inject constructor(private val fsm: FsmGateway) :
        UseCase<StateMachines, Void?>() {
    public override fun buildUseCaseObservable(params: Void?): StateMachines {
        return fsm.getStatus()
    }
}