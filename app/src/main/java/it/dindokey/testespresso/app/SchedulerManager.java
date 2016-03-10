package it.dindokey.testespresso.app;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by simone on 3/9/16.
 */
public class SchedulerManager
{

    private Scheduler io;
    private Scheduler mainThread;

    public SchedulerManager(Scheduler io, Scheduler mainThread)
    {

        this.io = io;
        this.mainThread = mainThread;
    }


    public Scheduler io()
    {
        return io;
    }

    public Scheduler mainThread()
    {
        return mainThread;
    }
}
