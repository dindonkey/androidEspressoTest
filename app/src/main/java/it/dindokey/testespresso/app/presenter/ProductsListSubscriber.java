package it.dindokey.testespresso.app.presenter;

import java.util.List;

import it.dindokey.testespresso.app.cache.ModelCache;
import it.dindokey.testespresso.app.model.ProductsModel;
import it.dindokey.testespresso.app.view.MainView;
import rx.Observer;

public class ProductsListSubscriber implements Observer<List<String>>
{
    private final MainView view;
    private final ModelCache modelCache;

    public ProductsListSubscriber(MainView view, ModelCache modelCache)
    {
        this.view = view;
        this.modelCache = modelCache;
    }

    @Override
    public void onCompleted()
    {
    }

    @Override
    public void onError(Throwable e)
    {
        view.showError();
    }

    @Override
    public void onNext(List<String> strings)
    {
        ProductsModel productsModel = new ProductsModel();
        productsModel.setItems(strings);
        modelCache.setModel(productsModel);
        view.refreshProductList(strings);
    }
}
