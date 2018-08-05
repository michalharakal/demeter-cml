package com.fiwio.iot.demeter.domain.core.interactor

abstract class UseCase<T, in Params>  {

    protected abstract fun buildUseCaseObservable(params: Params? = null): T

    /**
     * Executes the current use case.
     */
    open fun execute(params: Params? = null):T {
        return buildUseCaseObservable(params)
    }
}