package com.fiwio.iot.demeter.hw.mock;

import android.util.Log;

import com.fiwio.iot.demeter.hw.model.DigitalIO;
import com.fiwio.iot.demeter.hw.model.DigitalIoType;
import com.fiwio.iot.demeter.hw.model.DigitalValue;

public class MockDigitalIO implements DigitalIO {

    private static final String TAG = MockDigitalIO.class.getSimpleName();

    private DigitalValue value;
    private final String name;

    public MockDigitalIO(String name) {
        this.name = name;
    }

    @Override
    public void setValue(DigitalValue value) {
        this.value = value;
        Log.d(TAG, getName() + ((value == DigitalValue.ON) ? "ON" : "OFF"));

    }

    @Override
    public DigitalValue getValue() {
        return value;
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
