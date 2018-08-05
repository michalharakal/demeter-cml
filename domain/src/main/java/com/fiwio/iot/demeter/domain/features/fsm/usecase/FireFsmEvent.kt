package com.fiwio.iot.demeter.domain.features.fsm.usecase

import com.fiwio.iot.demeter.domain.core.executor.PostExecutionThread
import com.fiwio.iot.demeter.domain.core.executor.ThreadExecutor
import com.fiwio.iot.demeter.domain.core.interactor.SingleUseCase
import com.fiwio.iot.demeter.domain.features.fsm.FsmGateway
import com.fiwio.iot.demeter.domain.model.fsm.FsmEvent
import io.reactivex.Single
import javax.inject.Inject

open class FireFsmEvent @Inject constructor(private val fsmGateway: FsmGateway,
                                            threadExecutor: ThreadExecutor,
                                            postExecutionThread: PostExecutionThread) :
        SingleUseCase<Unit, FsmEvent>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: FsmEvent?): Single<Unit> {
        return Single.create { _ ->
            fsmGateway.fireEvent((params as FsmEvent).branch, params.name)
        }
    }
}