package it.dindokey.testespresso.app.presenter;

import java.util.List;

import javax.inject.Inject;

import it.dindokey.testespresso.app.ModelViewHolder;
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
    private SchedulerManager schedulerManager;

    private ProductsApiService productsApiService;

    @Inject
    public MainPresenter(ProductsApiService productsApiService, SchedulerManager schedulerManager)
    {
        this.productsApiService = productsApiService;
        this.schedulerManager = schedulerManager;
    }

    public void resume(final ModelViewHolder modelViewHolder)
    {
        final ProductsModel productsModel = modelViewHolder.getModel();
        final MainView view = modelViewHolder.getView();

        if(null != productsModel)
        {
            view.refreshProductList(productsModel.getItems());
        }
        else
        {

            Observable<List<String>> productsObservable = Observable.create(new Observable.OnSubscribe<List<String>>()
            {
                @Override
                public void call(Subscriber<? super List<String>> subscriber)
                {
                    try
                    {
                        subscriber.onNext(productsApiService.getProducts());
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
                    .subscribe(new Action1<List<String>>()
                    {
                        @Override
                        public void call(List<String> strings)  //success
                        {
                            ProductsModel productsModel = new ProductsModel();
                            productsModel.setItems(strings);
                            modelViewHolder.setModel(productsModel);
                            view.refreshProductList(strings);
                        }
                    }, new Action1<Throwable>()
                    {
                        @Override
                        public void call(Throwable throwable)   //error
                        {
                            view.showError();
                        }
                    });

            view.showLoading();
        }
    }
}
