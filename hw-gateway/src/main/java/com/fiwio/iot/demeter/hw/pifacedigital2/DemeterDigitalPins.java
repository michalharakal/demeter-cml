package com.fiwio.iot.demeter.hw.pifacedigital2;

import android.util.Log;

import com.fiwio.iot.demeter.hw.model.DigitalIO;
import com.fiwio.iot.demeter.domain.features.io.DigitalIoCallback;
import com.fiwio.iot.demeter.hw.model.DigitalPins;
import com.fiwio.iot.demeter.hw.model.DigitalValue;
import com.fiwio.iot.demeter.hw.rpi3.BoardDefaults;
import com.martingregor.InputEdgeCallback;
import com.martingregor.PiFaceDigital2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DemeterDigitalPins implements DigitalPins, InputEdgeCallback {

    private static final String LOG_TAG = DemeterDigitalPins.class.getSimpleName();
    private final PiFaceDigital2 piFaceDigital2;
    private final DemeterInput floatBarrel;
    private List<DigitalIO> inputs = new ArrayList<>();
    private List<DigitalIO> relays = new ArrayList<>();

    private DigitalIoCallback callback = null;

    public DemeterDigitalPins() throws IOException {
        piFaceDigital2 = PiFaceDigital2.create(BoardDefaults.getSPIPort(), this);


        relays.add(new DemeterRelay(piFaceDigital2, 0, "BCM23")); // Barrel filling
        relays.add(new DemeterRelay(piFaceDigital2, 1, "BCM24")); // garden
        relays.add(new DemeterRelay(piFaceDigital2, 2, "BCM25")); // flowers
        relays.add(new DemeterRelay(piFaceDigital2, 4, "BCM26")); // greenhouse

        floatBarrel = new DemeterInput(piFaceDigital2, 0, "INP0");
        inputs.add(floatBarrel);  // mechanical float - barrel
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
        this.callback = callback;
    }


    @Override
    public boolean onGpioEdge(byte[] values) {
        if (floatBarrel.getValue() == DigitalValue.OFF) {
            Log.d(LOG_TAG, "triggered on value =" + ((floatBarrel.getValue() == DigitalValue.OFF) ? "OFF" : "ON"));
            if (callback != null) {
                callback.onFLoatSensorActivated();
            }
        }
        return true;
    }
}
