package it.dindokey.testespresso.app.rx;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import it.dindokey.testespresso.app.cache.ObservableCache;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;
import rx.observables.ConnectableObservable;

/**
 * Created by simone on 5/6/16.
 */
public class CacheObservableExecutor implements ObservableExecutor
{
    private SchedulerManager schedulerManager;
    private Subscription subscription;
    private ObservableCache observableCache;

    @Inject
    public CacheObservableExecutor(ObservableCache observableCache, SchedulerManager schedulerManager)
    {
        this.observableCache = observableCache;
        this.schedulerManager = schedulerManager;
    }

    @Override
    public void execute(Observable observable, Observer observer)
    {
        ConnectableObservable connectableObservable = observableCache.observable();

        if (null == connectableObservable)
        {
            connectableObservable =  observable
                    .doOnCompleted(clearObservableCache())
                    .subscribeOn(schedulerManager.computation())
                    .observeOn(schedulerManager.mainThread())
                    .replay();

            observableCache.store(connectableObservable);
            connectableObservable.connect();
        }

        subscription = connectableObservable.subscribe(observer);
    }

    @NonNull
    private Action0 clearObservableCache()
    {
        return new Action0()
        {
            @Override
            public void call()
            {
                observableCache.clear();
            }
        };
    }

    @Override
    public void unsubscribe()
    {
        if (null != subscription)
        {
            subscription.unsubscribe();
        }
    }
}
