package it.dindokey.testespresso.app.presenter;

import java.util.List;

import javax.inject.Inject;

import it.dindokey.testespresso.app.ModelCache;
import it.dindokey.testespresso.app.ModelViewHolder;
import it.dindokey.testespresso.app.ObservableCache;
import it.dindokey.testespresso.app.SchedulerManager;
import it.dindokey.testespresso.app.SubscriberManager;
import it.dindokey.testespresso.app.api.ProductsApiService;
import it.dindokey.testespresso.app.model.ProductsModel;
import it.dindokey.testespresso.app.view.MainView;
import rx.Observer;
import rx.Subscription;

/**
 * Created by simone on 2/24/16.
 */
public class MainPresenter
{
    private SubscriberManager subscribeManager;
    private ProductsApiService productsApiService;

    private Subscription subscription;
    private Observer<List<String>> observer;

    @Inject
    public MainPresenter(ProductsApiService productsApiService,
                         SubscriberManager subscribeManager)
    {
        this.productsApiService = productsApiService;
        this.subscribeManager = subscribeManager;
    }

    public void resume(MainView view, ModelCache modelCache)
    {
        observer = createObserver(modelViewHolder);
        if (null != modelCache.model())
        {
            view.refreshProductList(modelCache.model().getItems());
        } else
        {
            loadData();
            view.showLoading();
        }
    }

    public void loadData()
    {
        subscribeManager.doSubscriptionWith(productsApiService.getProducts());



//        if (null == observableCache.observable())
//        {
//            observableCache.store(productsApiService
//                    .getProducts()
//                    .compose(this.<List<String>>applySchedulers())
//                    .replay());
//
//            observableCache.observable().connect();
//        }
//
//        subscription = observableCache.observable().doSubscriptionWith(observer);
    }

    public void pause()
    {
        subscribeManager.doUnsubscribe();
    }

    private Observer<List<String>> createObserver(final ModelViewHolder modelViewHolder)
    {
        return new Observer<List<String>>()
        {
            @Override
            public void onCompleted()
            {
                observableCache.clear();
            }

            @Override
            public void onError(Throwable e)
            {
                modelViewHolder.getView().showError();
            }

            @Override
            public void onNext(List<String> strings)
            {
                ProductsModel productsModel = new ProductsModel();
                productsModel.setItems(strings);
                modelViewHolder.setModel(productsModel);
                modelViewHolder.getView().refreshProductList(strings);
            }
        };
    }


}
