package it.dindokey.testespresso.app.api;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by simone on 2/8/16.
 */
public class SimpleProductsApiService implements ProductsApiService
{
    private HttpClient httpClient;

    public SimpleProductsApiService(HttpClient httpClient)
    {

        this.httpClient = httpClient;
    }

    @Override
    public Observable<List<String>> getProducts()
    {
        return Observable.create(new Observable.OnSubscribe<List<String>>()
        {
            @Override
            public void call(Subscriber<? super List<String>> subscriber)
            {
                try
                {

                    subscriber.onNext(httpClient.get());
                    subscriber.onCompleted();
                } catch (Exception e)
                {
                    subscriber.onError(e);
                }
            }
        });
    }
}
