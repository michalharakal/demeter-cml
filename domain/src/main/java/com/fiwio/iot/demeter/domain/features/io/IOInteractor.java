package com.fiwio.iot.demeter.domain.features.io;

public interface IOInteractor {
    boolean swimmerIsInactive();

    void barrelPumpOn();

    void openBarrel();

    void closeAllVentils();
}
