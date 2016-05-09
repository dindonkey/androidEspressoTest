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
import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by simone on 2/24/16.
 */
public class MainPresenter
{
    private SchedulerManager schedulerManager;
    private ObservableCache observableCache;
    private SubscriberManager subscribeManager;
    private ProductsApiService productsApiService;

    private Subscription subscription;
    private Observer<List<String>> observer;

    @Inject
    public MainPresenter(ProductsApiService productsApiService,
                         SchedulerManager schedulerManager,
                         ObservableCache observableCache,
                         SubscriberManager subscribeManager)
    {
        this.productsApiService = productsApiService;
        this.schedulerManager = schedulerManager;
        this.observableCache = observableCache;
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
        subscribeManager.subscribe();
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
//        subscription = observableCache.observable().subscribe(observer);
    }

    public void pause()
    {
        if (null != subscription)
        {
            subscription.unsubscribe();
        }
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

    <T> Observable.Transformer<T, T> applySchedulers()
    {
        return new Observable.Transformer<T, T>()
        {
            @Override
            public Observable<T> call(Observable<T> observable)
            {
                return observable.subscribeOn(schedulerManager.computation())
                        .observeOn(schedulerManager.mainThread());
            }
        };
    }
}
