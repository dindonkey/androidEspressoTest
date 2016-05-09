package it.dindokey.testespresso.app;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by simone on 5/6/16.
 */
public class BaseSubscriberManager implements SubscriberManager
{
    private ObservableCache observableCache;
    private SchedulerManager schedulerManager;
    private Subscription subscription;

    public BaseSubscriberManager(ObservableCache observableCache, SchedulerManager schedulerManager)
    {
        this.observableCache = observableCache;
        this.schedulerManager = schedulerManager;
    }

    @Override
    public void doSubscriptionWith(Observable observable, Observer observer)
    {
        if (null == observableCache.observable())
        {
            observableCache.store(observable
                    .compose(this.<List<String>>applySchedulers())
                    .replay());

            observableCache.observable().connect();
        }

        subscription = observableCache.observable().subscribe(observer);
    }

    @Override
    public void doUnsubscribe()
    {
        if (null != subscription)
        {
            subscription.unsubscribe();
        }
    }

    <T> Observable.Transformer<T, T> applySchedulers()
    {
        return new Observable.Transformer<T, T>()
        {
            @Override
            public Observable<T> call(Observable<T> observable)
            {
                return observable.subscribeOn(schedulerManager.computation())
                        .observeOn(schedulerManager.mainThread());
            }
        };
    }


}
