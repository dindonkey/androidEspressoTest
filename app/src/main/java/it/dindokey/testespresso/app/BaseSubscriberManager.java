package it.dindokey.testespresso.app;

/**
 * Created by simone on 5/6/16.
 */
public class BaseSubscriberManager implements SubscriberManager
{
    @Override
    public void subscribe()
    {
        if (null == observableCache.observable())
//        {
//            observableCache.store(productsApiService
//                    .getProducts()
//                    .compose(this.<List<String>>applySchedulers())
//                    .replay());
//
//            observableCache.observable().connect();
//        }
//
//        subscription = observableCache.observable().subscribe(observer);
    }

    @Override
    public void unsubscribe()
    {

    }
}
