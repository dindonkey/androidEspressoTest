package it.dindokey.testespresso.app.api;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by simone on 2/8/16.
 */
public class ProductsApiService
{
    public Observable<List<String>> getProducts()
    {

        return Observable.create(new Observable.OnSubscribe<List<String>>()
        {
            @Override
            public void call(Subscriber<? super List<String>> subscriber)
            {
                try
                {
                    Thread.sleep(10000); //simulate slow network communication
                    subscriber.onNext(Arrays.asList("First product"));
                    subscriber.onCompleted();
                }
                catch (Exception e)
                {
                    subscriber.onError(e);
                }
            }
        });
    }
}
