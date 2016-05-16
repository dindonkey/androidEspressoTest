package it.dindokey.testespresso.app.rx;

import rx.Scheduler;

/**
 * Created by simone on 3/9/16.
 */
public class SchedulerManager
{

    private Scheduler computation;
    private Scheduler mainThread;

    public SchedulerManager(Scheduler computation, Scheduler mainThread)
    {

        this.computation = computation;
        this.mainThread = mainThread;
    }


    public Scheduler computation()
    {
        return computation;
    }

    public Scheduler mainThread()
    {
        return mainThread;
    }
}
