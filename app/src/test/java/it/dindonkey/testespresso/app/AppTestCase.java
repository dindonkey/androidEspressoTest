package it.dindonkey.testespresso.app;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.schedulers.TestScheduler;

import static rx.Observable.just;

/**
 * Created by simone on 5/2/16.
 */
public class AppTestCase
{
    protected List<String> sampleProducts = Arrays.asList("test product");
    protected TestScheduler _God_scheduler = new TestScheduler();

    @NonNull
    protected Observable<List<String>> delayedProductsObservable()
    {
        return just(sampleProducts).delay(4,
                TimeUnit.SECONDS);
    }

    @NonNull
    protected Observable<List<String>> timeControlledProductsObservable(int secondsToComplete)
    {
        return just(sampleProducts).delay(secondsToComplete,
                TimeUnit.SECONDS, _God_scheduler);
    }

}
