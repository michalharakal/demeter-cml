package com.fiwio.iot.demeter.hw.mock;

import com.fiwio.iot.demeter.hw.model.DigitalIO;
import com.fiwio.iot.demeter.domain.features.io.DigitalIoCallback;
import com.fiwio.iot.demeter.hw.model.DigitalIoType;
import com.fiwio.iot.demeter.hw.model.DigitalPins;
import com.fiwio.iot.demeter.hw.model.DigitalValue;

import java.util.ArrayList;
import java.util.List;

public class MockDigitalPins implements DigitalPins {

    private static final String TAG = MockDigitalPins.class.getSimpleName();

    private List<DigitalIO> inputs = new ArrayList<>();
    private List<DigitalIO> relays = new ArrayList<>();

    public MockDigitalPins() {
        relays.add(new MockDigitalIO(DigitalValue.ON, "BCM23", DigitalIoType.OUTPUT));
        relays.add(new MockDigitalIO(DigitalValue.ON, "BCM24", DigitalIoType.OUTPUT));
        relays.add(new MockDigitalIO(DigitalValue.ON, "BCM22", DigitalIoType.OUTPUT));

        inputs.add(new MockDigitalIO(DigitalValue.ON, "BCM26", DigitalIoType.INPUT));
        inputs.add(new MockDigitalIO(DigitalValue.ON, "BCM16", DigitalIoType.INPUT));
    }

    @Override
    public List<DigitalIO> getInputs() {
        return inputs;
    }

    @Override
    public List<DigitalIO> getOutputs() {
        return relays;
    }

    @Override
    public DigitalIO getInput(String name) {
        for (DigitalIO input : inputs) {
            if (input.getName().equals(name)) {
                return input;
            }
        }
        return null;
    }

    @Override
    public DigitalIO getOutput(String name) {
        for (DigitalIO output : relays) {
            if (output.getName().equals(name)) {
                return output;
            }
        }
        return null;
    }

    @Override
    public void registerInputCallback(DigitalIoCallback callback) {

    }
}
