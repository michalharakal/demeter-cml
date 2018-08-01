package com.fiwio.iot.demeter.fsm;

import com.fiwio.iot.demeter.domain.features.configuration.ConfigurationProvider;
import com.fiwio.iot.demeter.domain.features.fsm.GardenFiniteStateMachine;
import com.fiwio.iot.demeter.domain.features.io.BranchesInteractorsProvider;

public class FsmBrachnesFactory {
    public static void startBranches(
            ConfigurationProvider configurationProvider,
            GardenFiniteStateMachine fsm,
            BranchesInteractorsProvider interactorProvider) {
        runBranch(
                GardenFiniteStateMachine.BRANCH_GARDEN, configurationProvider, fsm, interactorProvider);
        runBranch(
                GardenFiniteStateMachine.BRANCH_FLOWERS, configurationProvider, fsm, interactorProvider);
        runBranch(
                GardenFiniteStateMachine.BRANCH_GREENHOUSE, configurationProvider, fsm, interactorProvider);
    }

    private static void runBranch(
            String branch,
            ConfigurationProvider configurationProvider,
            GardenFiniteStateMachine fsm,
            BranchesInteractorsProvider interactorProvider) {
        fsm.run(
                branch, configurationProvider.getBranchParameters(branch), interactorProvider.get(branch));
    }
}
