package com.fiwio.iot.demeter.hw.rpi3;

import com.fiwio.iot.demeter.hw.features.io.DigitalIoCallback;
import com.fiwio.iot.demeter.hw.features.io.DigitalPins;
import com.fiwio.iot.demeter.hw.model.DigitalIO;
import com.fiwio.iot.demeter.hw.model.DigitalValue;
import com.fiwio.iot.demeter.hw.pifacedigital2.DemeterInput;
import com.fiwio.iot.demeter.hw.pifacedigital2.DemeterRelay;
import com.google.android.things.pio.PeripheralManager;

import java.util.ArrayList;
import java.util.List;


public class DemeterDigitalPins implements DigitalPins, DigitalIoCallback {

    private static final String TAG = DemeterDigitalPins.class.getSimpleName();
    private List<DigitalIO> inputs = new ArrayList<>();
    private List<DigitalIO> relays = new ArrayList<>();

    public DemeterDigitalPins() {

        PeripheralManager service = PeripheralManager.getInstance();

        relays.add(new DemeterRelay(service, "BCM23")); // X3.8 barrel filling
        relays.add(new DemeterRelay(service, "BCM24")); // X3.7 irrigating
        relays.add(new DemeterRelay(service, "BCM22")); // X3.6

        inputs.add(new DemeterInput(service, "BCM26", this)); // X4.5
        inputs.add(new DemeterInput(service, "BCM16", this));  // X4.6

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

    @Override
    public boolean onGpioEdge(DigitalIO digitalIO) {
        if (digitalIO.getValue() == DigitalValue.ON) {
            // TODO floating sensor stops always filling and closes pump ventil
            /*
            EventBus.getDefault().post(new FireFsmEvent("garden", "stop"));
            */
        }
        return true;
    }

    @Override
    public void onGpioError(DigitalIO gpio, int error) {

    }
}
