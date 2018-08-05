package com.fiwio.iot.demeter.hw.pifacedigital2;

import android.util.Log;

import com.fiwio.iot.demeter.hw.model.DigitalIO;
import com.fiwio.iot.demeter.hw.model.DigitalIoType;
import com.fiwio.iot.demeter.hw.model.DigitalValue;
import com.martingregor.PiFaceDigital2;

import org.jetbrains.annotations.NotNull;


public class DemeterRelay implements DigitalIO {
    private static final String TAG = DemeterRelay.class.getSimpleName();

    private final PiFaceDigital2 mPiFaceDigital2;

    private final String name;
    private final int relayIndex;
    private DigitalValue mLastValue;

    public DemeterRelay(final PiFaceDigital2 mPiFaceDigital2, final int relayIndex, String ioName) {
        this.name = ioName;
        this.relayIndex = relayIndex;
        this.mPiFaceDigital2 = mPiFaceDigital2;
        this.mLastValue = DigitalValue.OFF;
    }

    @Override
    public void setValue(DigitalValue value) {
        mLastValue = value;
        mPiFaceDigital2.setLED(relayIndex, value == DigitalValue.ON ? true : false);
        Log.d(TAG, getName() + ((value == DigitalValue.ON) ? "ON" : "OFF"));

    }

    @Override
    public DigitalValue getValue() {
        return mLastValue;
    }

    @Override
    public String getName() {
        return name;
    }

    @NotNull
    @Override
    public DigitalIoType getType() {
        return DigitalIoType.OUTPUT;
    }
}
