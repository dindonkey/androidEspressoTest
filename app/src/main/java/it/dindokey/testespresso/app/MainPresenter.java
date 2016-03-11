package it.dindokey.testespresso.app;

import javax.inject.Inject;

import it.dindokey.testespresso.app.api.ProductsApi;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by simone on 2/24/16.
 */
public class MainPresenter
{
    private SchedulerManager schedulerManager;

    public ProductsApi productsApi;

    @Inject public MainPresenter(ProductsApi productsApi, SchedulerManager schedulerManager)
    {
        this.productsApi = productsApi;
        this.schedulerManager = schedulerManager;
    }

    public void resume(final MainView view)
    {
//        new AsyncTask<Void, Void, String[]>()
//        {
//            @Override
//            protected void onPostExecute(String[] result)
//            {
//                view.refreshProductList(result);
//            }
//
//            @Override
//            protected String[] doInBackground(Void... params)
//            {
//                return productsApi.getProducts();
//            }
//        }.execute();


        Observable<String[]> productsObservable = Observable.create(new Observable.OnSubscribe<String[]>()
        {
            @Override
            public void call(Subscriber<? super String[]> subscriber)
            {
                try
                {
                    String[] products = productsApi.getProducts();
                    subscriber.onNext(products);
                    subscriber.onCompleted();
                }
                catch (Exception e)
                {
                    subscriber.onError(e);
                }
            }
        });

        productsObservable
                .subscribeOn(schedulerManager.io())
                .observeOn(schedulerManager.mainThread())
                .subscribe(new Observer<String[]>()
                {
                    @Override
                    public void onCompleted()
                    {

                    }

                    @Override
                    public void onError(Throwable e)
                    {

                    }

                    @Override
                    public void onNext(String[] products)
                    {
                        view.refreshProductList(products);
                    }
                });
    }
}
