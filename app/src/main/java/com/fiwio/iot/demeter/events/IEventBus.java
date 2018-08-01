package com.fiwio.iot.demeter.events;

public interface IEventBus {

    void register(Object subscriber);

    void unregister(Object subscriber);

    void post(Object event);
}
