package it.dindokey.testespresso.app;

import javax.inject.Inject;

import it.dindokey.testespresso.app.api.ProductsApi;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by simone on 2/24/16.
 */
public class MainPresenter
{
    private MainView view;
    private SchedulerManager schedulerManager;

    @Inject
    public ProductsApi productsApi;

    public MainPresenter(App app, MainView view, SchedulerManager schedulerManager)
    {
        this.view = view;
        this.schedulerManager = schedulerManager;
        app.apiComponent().inject(this);
    }

    public void onStart()
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
