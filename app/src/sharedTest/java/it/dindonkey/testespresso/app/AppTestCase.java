package it.dindonkey.testespresso.app;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.schedulers.TestScheduler;
import rx.Subscriber;

import static rx.Observable.just;

/**
 * Created by simone on 5/2/16.
 */
public class AppTestCase
{
    protected List<String> sampleProducts = Arrays.asList("test product");
    protected TestScheduler _God_scheduler = new TestScheduler();

    protected Observable<List<String>> delayedProductsObservable()
    {
        return just(sampleProducts).delay(4,
                TimeUnit.SECONDS);
    }

    protected Observable<List<String>> timeControlledProductsObservable(int secondsToComplete)
    {
        return just(sampleProducts).delay(secondsToComplete,
                TimeUnit.SECONDS, _God_scheduler);
    }

    protected Observable<List<String>> testProductsObservable()
    {
        return just(sampleProducts);
    }

    protected Observable<List<String>> brokenProductsObservable()
    {
        return Observable.create(new Observable.OnSubscribe<List<String>>()
        {
            @Override
            public void call(Subscriber<? super List<String>> subscriber)
            {
                subscriber.onError(new RuntimeException());
            }
        });
    }

    protected Observable<List<String>> observableWithSubscription(final Subscription subscription)
    {
        return Observable.create(new Observable.OnSubscribe<List<String>>()
        {
            @Override
            public void call(Subscriber<? super List<String>> subscriber)
            {
                subscriber.add(subscription);
            }
        });
    }

}
