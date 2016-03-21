package it.dindokey.testespresso.app.presenter;

import android.os.Bundle;

import javax.inject.Inject;

import it.dindokey.testespresso.app.SchedulerManager;
import it.dindokey.testespresso.app.api.ProductsApiService;
import it.dindokey.testespresso.app.model.ProductsModel;
import it.dindokey.testespresso.app.view.MainView;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by simone on 2/24/16.
 */
public class MainPresenter
{
    public static final String MODEL = "model";
    private SchedulerManager schedulerManager;

    private ProductsApiService productsApiService;

    private ProductsModel productsModel;

    @Inject
    public MainPresenter(ProductsApiService productsApiService, SchedulerManager schedulerManager)
    {
        this.productsApiService = productsApiService;
        this.schedulerManager = schedulerManager;
    }

    public void resume(final MainView view, Bundle savedInstanceState)
    {
        if(null != savedInstanceState)
        {
            productsModel = savedInstanceState.getParcelable(MODEL);
        }
        if(null == productsModel)
        {
            productsModel = new ProductsModel();
        }
        if(productsModel.getItems().length == 0)
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
                    .observeOn(schedulerManager.mainThread())
                    .subscribe(new Action1<String[]>()
                    {
                        @Override
                        public void call(String[] strings)  //success
                        {
                            productsModel.setItems(strings);
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

        view.refreshProductList(productsModel.getItems());

    }

    public void saveInstanceState(Bundle outState)
    {
        outState.putParcelable(MODEL,productsModel);
    }

    public ProductsModel getProductsModel()
    {
        return productsModel;
    }

    public void setProductsModel(ProductsModel productsModel)
    {
        this.productsModel = productsModel;
    }
}
