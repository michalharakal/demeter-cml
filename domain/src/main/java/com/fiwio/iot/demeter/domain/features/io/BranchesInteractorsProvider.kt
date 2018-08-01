package com.fiwio.iot.demeter.domain.features.io

interface BranchesInteractorsProvider {
    operator fun get(branch: String): IOInteractor
}
