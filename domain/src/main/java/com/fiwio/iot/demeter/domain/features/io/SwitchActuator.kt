package com.fiwio.iot.demeter.domain.features.manual

import com.fiwio.iot.demeter.domain.core.executor.PostExecutionThread
import com.fiwio.iot.demeter.domain.core.executor.ThreadExecutor
import com.fiwio.iot.demeter.domain.core.interactor.SingleUseCase
import com.fiwio.iot.demeter.domain.model.io.Actuator
import com.fiwio.iot.demeter.domain.model.io.Demeter
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import io.reactivex.Single
import javax.inject.Inject

open class SwitchActuator @Inject constructor(val demeterRepository: DemeterRepository,
                                              threadExecutor: ThreadExecutor,
                                              postExecutionThread: PostExecutionThread) :
        SingleUseCase<Demeter, Actuator>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Actuator?): Single<Demeter> {
        return demeterRepository.switchActuator(params as Actuator)
    }
}