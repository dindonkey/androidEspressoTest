package it.dindokey.testespresso.app.presenter;

import java.util.List;

import javax.inject.Inject;

import it.dindokey.testespresso.app.ModelViewHolder;
import it.dindokey.testespresso.app.ObservableCache;
import it.dindokey.testespresso.app.SchedulerManager;
import it.dindokey.testespresso.app.api.ProductsApiService;
import it.dindokey.testespresso.app.model.ProductsModel;
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
    private ProductsApiService productsApiService;

    private Subscription subscription;
    private Observer<List<String>> observer;

    @Inject
    public MainPresenter(ProductsApiService productsApiService,
                         SchedulerManager schedulerManager,
                         ObservableCache observableCache)
    {
        this.productsApiService = productsApiService;
        this.schedulerManager = schedulerManager;
        this.observableCache = observableCache;
    }

    public void resume(ModelViewHolder modelViewHolder)
    {
        observer = createObserver(modelViewHolder);
        if (null != modelViewHolder.getModel())
        {
            modelViewHolder.getView().refreshProductList(modelViewHolder.getModel().getItems());
        } else
        {
            loadData();
            modelViewHolder.getView().showLoading();
        }
    }

    public void loadData()
    {
        if (null == observableCache.observable())
        {
            observableCache.store(productsApiService
                    .getProducts()
                    .compose(this.<List<String>>applySchedulers())
                    .replay());

            observableCache.observable().connect();
        }

        subscription = observableCache.observable().subscribe(observer);
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
