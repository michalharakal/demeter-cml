package com.fiwio.iot.demeter.fsm;


import com.fiwio.iot.demeter.domain.features.configuration.ConfigurationProvider;
import com.fiwio.iot.demeter.domain.features.fsm.GardenFiniteStateMachine;
import com.fiwio.iot.demeter.domain.features.io.BranchesInteractorsProvider;

public class FsmRunnable implements Runnable {
    private static Thread _instance;

    private final GardenFiniteStateMachine fsm;
    private final ConfigurationProvider provider;
    private final BranchesInteractorsProvider branchInteractorProvider;

    private FsmRunnable(
            GardenFiniteStateMachine fsm,
            ConfigurationProvider provider,
            BranchesInteractorsProvider branchInteractorProvider) {
        this.fsm = fsm;
        this.provider = provider;
        this.branchInteractorProvider = branchInteractorProvider;
    }

    public static synchronized Thread getInstance(
            GardenFiniteStateMachine fsm,
            ConfigurationProvider provider,
            BranchesInteractorsProvider interactorProvider) {
        if (_instance == null) {
            _instance = new Thread(new FsmRunnable(fsm, provider, interactorProvider));
        }
        return _instance;
    }

    @Override
    public void run() {
        FsmBrachnesFactory.startBranches(provider, fsm, branchInteractorProvider);
    }
}
