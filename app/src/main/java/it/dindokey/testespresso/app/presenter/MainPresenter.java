package it.dindokey.testespresso.app.presenter;

import javax.inject.Inject;

import it.dindokey.testespresso.app.SchedulerManager;
import it.dindokey.testespresso.app.api.ProductsApi;
import it.dindokey.testespresso.app.view.MainView;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

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
                .subscribe(new Action1<String[]>()
                {
                    @Override
                    public void call(String[] strings)  //success
                    {
                        view.refreshProductList(strings);
                    }
                }, new Action1<Throwable>()
                {
                    @Override
                    public void call(Throwable throwable)   //error
                    {
                        //TODO: show error
                    }
                });

    }
}
