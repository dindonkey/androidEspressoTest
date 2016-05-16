package it.dindokey.testespresso.app.presenter;

import it.dindokey.testespresso.app.api.ProductsApiService;
import it.dindokey.testespresso.app.cache.ModelCache;
import it.dindokey.testespresso.app.rx.ObservableExecutor;
import it.dindokey.testespresso.app.view.MainView;

/**
 * Created by simone on 2/24/16.
 */
public class MainPresenter
{
    private ObservableExecutor observableExecutor;
    private ModelCache modelCache;
    private ProductsApiService productsApiService;

    public MainPresenter(ProductsApiService productsApiService,
                         ObservableExecutor observableExecutor,
                         ModelCache modelCache)
    {
        this.productsApiService = productsApiService;
        this.observableExecutor = observableExecutor;
        this.modelCache = modelCache;
    }

    public void resume(MainView view)
    {
        if (null != modelCache.model())
        {
            view.refreshProductList(modelCache.model()
                    .getItems()); //TODO: refereshProductList should work with model
        } else
        {
            loadData(view);
            view.showLoading();
        }
    }

    public void loadData(MainView view)
    {
        ProductsListSubscriber productsListSubscriber = new ProductsListSubscriber(view,
                modelCache);
        observableExecutor.execute(productsApiService.getProducts(), productsListSubscriber);
    }

    public void pause()
    {
        observableExecutor.unsubscribe();
    }

}
