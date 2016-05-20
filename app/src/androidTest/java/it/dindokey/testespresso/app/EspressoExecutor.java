package it.dindokey.testespresso.app;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by simone on 3/14/16.
 */
public class EspressoExecutor extends ThreadPoolExecutor implements IdlingResource
{

    private int runningTasks;
    private ResourceCallback resourceCallback;

    private static EspressoExecutor singleton;

    private EspressoExecutor(BlockingQueue<Runnable> workQueue) {
        super(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, workQueue);
    }

    public static EspressoExecutor getCachedThreadPool() {
        if (singleton == null) {
            singleton = new EspressoExecutor(
                    new SynchronousQueue<Runnable>());
            Espresso.registerIdlingResources(singleton);
        }
        return singleton;
    }

    @Override public void execute(final Runnable command) {
        super.execute(new Runnable() {
            @Override public void run() {
                try {
                    incrementRunningTasks();
                    command.run();
                } finally {
                    decrementRunningTasks();
                }
            }
        });
    }

    private synchronized void decrementRunningTasks() {
        runningTasks--;
        if (runningTasks == 0 && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }
    }

    private synchronized void incrementRunningTasks() {
        runningTasks++;
    }

    @Override public String getName() {
        return "EspressoExecutor";
    }

    @Override public boolean isIdleNow() {
        return runningTasks == 0;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }
}
