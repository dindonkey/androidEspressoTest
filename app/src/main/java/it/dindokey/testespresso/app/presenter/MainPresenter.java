package it.dindokey.testespresso.app.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import it.dindokey.testespresso.app.SchedulerManager;
import it.dindokey.testespresso.app.api.ProductsApiService;
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

    private ProductsApiService productsApiService;
    private String[] productsModel;

    @Inject
    public MainPresenter(ProductsApiService productsApiService, SchedulerManager schedulerManager)
    {
        this.productsApiService = productsApiService;
        this.schedulerManager = schedulerManager;
    }

    public void resume(final MainView view)
    {
        if(null == productsModel)
        {
            getProductsApiServiceObservable()
                    .subscribe(new Action1<String[]>()
                    {
                        @Override
                        public void call(String[] strings)  //success
                        {
                            productsModel = strings;
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

    @NonNull
    private Observable<String[]> getProductsApiServiceObservable()
    {
        Observable<String[]> productsObservable = Observable.create(new Observable.OnSubscribe<String[]>()
        {
            @Override
            public void call(Subscriber<? super String[]> subscriber)
            {
                try
                {
                    subscriber.onNext(productsApiService.getProducts());
                } catch (Exception e)
                {
                    subscriber.onError(e);
                }
            }
        });

        productsObservable
                .subscribeOn(schedulerManager.io())
                .observeOn(schedulerManager.mainThread());
        return productsObservable;
    }
}
