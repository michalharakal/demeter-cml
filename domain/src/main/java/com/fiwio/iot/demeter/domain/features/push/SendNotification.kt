package com.fiwio.iot.demeter.domain.features.push

import com.fiwio.iot.demeter.domain.core.executor.PostExecutionThread
import com.fiwio.iot.demeter.domain.core.executor.ThreadExecutor
import com.fiwio.iot.demeter.domain.core.interactor.SingleUseCase
import com.fiwio.iot.demeter.domain.gateway.PushNotificationsGateway
import io.reactivex.Single
import javax.inject.Inject


class SendNotification @Inject constructor(val pushNotificationsGateway: PushNotificationsGateway,
                                           threadExecutor: ThreadExecutor,
                                           postExecutionThread: PostExecutionThread) :
        SingleUseCase<Unit, String>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: String?): Single<Unit> {
        return Single.create { singleEmitter ->
            singleEmitter.onSuccess(pushNotificationsGateway.sendPush("events", params!!))
        }
    }
}