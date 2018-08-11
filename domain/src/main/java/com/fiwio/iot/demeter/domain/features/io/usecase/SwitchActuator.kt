package com.fiwio.iot.demeter.domain.features.manual

import com.fiwio.iot.demeter.domain.core.executor.PostExecutionThread
import com.fiwio.iot.demeter.domain.core.executor.ThreadExecutor
import com.fiwio.iot.demeter.domain.core.interactor.SingleUseCase
import com.fiwio.iot.demeter.domain.gateway.DeviceGateway
import com.fiwio.iot.demeter.domain.model.io.Actuator
import com.fiwio.iot.demeter.domain.model.io.Demeter
import io.reactivex.Single
import javax.inject.Inject

class SwitchActuator @Inject constructor(val demeterRepository: DeviceGateway,
                                         threadExecutor: ThreadExecutor,
                                         postExecutionThread: PostExecutionThread) :
        SingleUseCase<Demeter, Actuator>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Actuator?): Single<Demeter> {
        return Single.create { singleEmitter ->
            singleEmitter.onSuccess(
                    demeterRepository.switchActuator(params as Actuator)
            )
        }
    }
}