package it.dindokey.testespresso.app.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import it.dindokey.testespresso.app.App;
import it.dindokey.testespresso.app.api.ProductsApiService;
import it.dindokey.testespresso.app.cache.InstanceStateCache;
import it.dindokey.testespresso.app.presenter.MainPresenter;
import it.dindokey.testespresso.app.R;
import it.dindokey.testespresso.app.rx.CacheObservableExecutor;

public class MainActivity extends AppCompatActivity implements MainView
{
    private ProductListViewAdapter productListViewAdapter;

    @Inject
    ProductsApiService productsApiService;
    @Inject
    CacheObservableExecutor observableExecutor;
    @Inject
    InstanceStateCache instanceStateCache;

    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((App) getApplication()).getComponent().inject(this);

        ListView listView = (ListView) findViewById(R.id.list_view);

        productListViewAdapter = new ProductListViewAdapter(this,
                android.R.layout.simple_list_item_1, android.R.id.text1);
        listView.setAdapter(productListViewAdapter);

        instanceStateCache.initModelFrom(savedInstanceState);
        mainPresenter = new MainPresenter(productsApiService, observableExecutor,
                instanceStateCache);
        //TODO verify leak passing this
        mainPresenter.resume(this);
    }

    @Override
    public void refreshProductList(List<String> products)
    {
        productListViewAdapter.setValues(products);
        productListViewAdapter.reload();
    }

    @Override
    public void showLoading()
    {
        productListViewAdapter.setValues(Arrays.asList(getString(R.string.loading_message)));
        productListViewAdapter.reload();
    }

    @Override
    public void showError()
    {
        productListViewAdapter.setValues(Arrays.asList(getString(R.string.error_message)));
        productListViewAdapter.reload();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        instanceStateCache.saveModelTo(outState);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mainPresenter.pause();
    }
}
