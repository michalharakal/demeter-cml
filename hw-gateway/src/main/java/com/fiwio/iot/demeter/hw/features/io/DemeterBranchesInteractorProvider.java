package com.fiwio.iot.demeter.hw.features.io;

import com.fiwio.iot.demeter.domain.features.fsm.GardenFiniteStateMachine;
import com.fiwio.iot.demeter.domain.features.io.BranchesInteractorsProvider;
import com.fiwio.iot.demeter.domain.features.io.IOInteractor;
import com.fiwio.iot.demeter.hw.model.DigitalPins;

import java.util.HashMap;
import java.util.Map;

/**
 * BCM23 "Napouštení" BCM24 "Sklenik" BCM25 "Kytky" BCM26 "Zahrada"
 */
public class DemeterBranchesInteractorProvider implements BranchesInteractorsProvider {

    private static final String GREENHOUSE_PIN = "BCM26";
    private static final String GARDEN_PIN = "BCM24";
    private static final String FLOWERS_PIN = "BCM25";

    private Map<String, IOInteractor> branches = new HashMap<>();

    public DemeterBranchesInteractorProvider(DigitalPins demeter) {

        DemeterIoInteractor gardenBranch = new DemeterIoInteractor(demeter, GARDEN_PIN);
        branches.put(GardenFiniteStateMachine.BRANCH_GARDEN, gardenBranch);

        DemeterIoInteractor greenhouseBranch = new DemeterIoInteractor(demeter, GREENHOUSE_PIN);
        branches.put(GardenFiniteStateMachine.BRANCH_GREENHOUSE, greenhouseBranch);

        DemeterIoInteractor flowersBranch = new DemeterIoInteractor(demeter, FLOWERS_PIN);
        branches.put(GardenFiniteStateMachine.BRANCH_FLOWERS, flowersBranch);
    }

    @Override
    public IOInteractor get(String branch) {
        return branches.get(branch);
    }
}
