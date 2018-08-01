package com.fiwio.iot.demeter.hw.pifacedigital2;

import android.util.Log;

import com.fiwio.iot.demeter.hw.model.DigitalIO;
import com.fiwio.iot.demeter.hw.model.DigitalIoType;
import com.fiwio.iot.demeter.hw.model.DigitalValue;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;


public class DemeterRelay implements DigitalIO {

    private static final String TAG = DemeterRelay.class.getSimpleName();

    private Gpio mLedGpio;
    private final String name;

    public DemeterRelay(PeripheralManager gpio, String ioName) {
        this.name = ioName;
        try {
            mLedGpio = gpio.openGpio(ioName);
            mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        }
    }

    @Override
    public void setValue(DigitalValue value) {
        try {
            mLedGpio.setValue(value == DigitalValue.ON ? true : false);
            Log.d(TAG, getName() + ((value == DigitalValue.ON) ? "ON" : "OFF"));
        } catch (IOException e) {
            Log.e(TAG, "error setting Relay " + name);
        }
    }

    @Override
    public DigitalValue getValue() {
        try {
            return mLedGpio.getValue() ? DigitalValue.ON : DigitalValue.OFF;
        } catch (IOException e) {
            Log.e(TAG, "error getting Relay " + name);
        }
        return DigitalValue.OFF;
    }

    @Override
    public DigitalIoType getType() {
        return DigitalIoType.OUTPUT;
    }

    @Override
    public String getName() {
        return name;
    }
}
