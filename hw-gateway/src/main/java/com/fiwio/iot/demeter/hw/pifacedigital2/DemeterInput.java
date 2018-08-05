package com.fiwio.iot.demeter.hw.pifacedigital2;

import android.util.Log;

import com.fiwio.iot.demeter.hw.model.DigitalIO;
import com.fiwio.iot.demeter.hw.model.DigitalIoType;
import com.fiwio.iot.demeter.hw.model.DigitalValue;
import com.martingregor.PiFaceDigital2;

public class DemeterInput implements DigitalIO {

    private static final String TAG = DemeterInput.class.getSimpleName();

    private final String name;
    private final int relayIndex;
    private final PiFaceDigital2 mPiFaceDigital2;


    public DemeterInput(final PiFaceDigital2 mPiFaceDigital2, final int relayIndex, String ioName) {
        this.name = ioName;
        this.relayIndex = relayIndex;
        this.mPiFaceDigital2 = mPiFaceDigital2;

    }

    @Override
    public void setValue(DigitalValue value) {
        // input is read only
    }

    @Override
    public DigitalValue getValue() {
       // Log.d(TAG, "Input " + name + "=" + (mPiFaceDigital2.getInput(relayIndex) ? "ON" : "OFF"));
        return mPiFaceDigital2.getInput(relayIndex) ? DigitalValue.ON : DigitalValue.OFF;
    }

    @Override
    public DigitalIoType getType() {
        return DigitalIoType.INPUT;
    }

    @Override
    public String getName() {
        return name;
    }
}