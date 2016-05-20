package it.dindonkey.testespresso.app;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.TestScheduler;

import static rx.Observable.just;

@SuppressWarnings("unused")
public class AppTestCase
{
    protected final List<String> sampleProducts = Collections.singletonList("test product");
    private final TestScheduler _God_scheduler = new TestScheduler();

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
