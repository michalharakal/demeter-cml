package com.fiwio.iot.demeter;

import android.app.Application;
import android.util.Log;

import com.fiwio.iot.demeter.domain.features.configuration.ConfigurationProvider;
import com.fiwio.iot.demeter.domain.features.fsm.GardenFiniteStateMachine;
import com.fiwio.iot.demeter.domain.features.io.BranchesInteractorsProvider;
import com.fiwio.iot.demeter.domain.features.tracking.EventTracker;
import com.fiwio.iot.demeter.events.DemeterEventBus;
import com.fiwio.iot.demeter.events.IEventBus;
import com.fiwio.iot.demeter.hw.features.io.DemeterBranchesInteractorProvider;
import com.fiwio.iot.demeter.hw.features.io.DigitalPins;
import com.fiwio.iot.demeter.hw.mock.MockDigitalPins;
import com.fiwio.iot.demeter.hw.rpi3.DemeterDigitalPins;
import com.google.android.things.device.TimeManager;

import net.danlew.android.joda.JodaTimeAndroid;

//import com.evernote.android.job.JobManager;

public class DemeterApplication extends Application {

    private static final String LOG_TAG = DemeterApplication.class.getSimpleName();
    private DigitalPins demeter;
    private GardenFiniteStateMachine fsm;
    private IEventBus eventBus;
    private ConfigurationProvider configurationProvider;
    //private ReminderEngine reminderEngine;
    private EventTracker evenTracker;
    private BranchesInteractorsProvider branchInteractorsProvider;

    @Override
    public void onCreate() {
        super.onCreate();

        evenTracker =
                new EventTracker() {
                    @Override
                    public void track(String message) {
                        Log.d(LOG_TAG, message);
                    }
                };

        TimeManager tm = TimeManager.getInstance();
        tm.setTimeZone("Europe/Berlin");

        JodaTimeAndroid.init(this);

    /*
    configuration = new Configuration(this);
    configurationProvider = new DemeterConfigurationProvider(configuration);
    */

        demeter = createDeviceImageInstance();
        branchInteractorsProvider = new DemeterBranchesInteractorProvider(demeter);

        fsm = createFlowersFsm();
        eventBus = new DemeterEventBus();

        // reminderEngine = new ReminderEngine(this, eventBus);
        // JobManager.create(this).addJobCreator(new ReminderJobCreator(reminderEngine));
    }

    private DigitalPins createDeviceImageInstance() {
        if (BuildConfig.MOCK_MODE) {
            return new MockDigitalPins();
        } else {
            return new DemeterDigitalPins();
        }
    }

    private GardenFiniteStateMachine createFlowersFsm() {
        return new GardenFiniteStateMachine(evenTracker);
    }

    public DigitalPins getDemeter() {
        return demeter;
    }

    public GardenFiniteStateMachine getFsm() {
        return fsm;
    }

    public IEventBus getEventBus() {
        return eventBus;
    }

    //public ReminderEngine getRemainderEngine() {        return reminderEngine;    }

    public BranchesInteractorsProvider getBranchesInteractorProvider() {
        return branchInteractorsProvider;
    }

    public ConfigurationProvider getConfigurationProvider() {
        return configurationProvider;
    }
}
