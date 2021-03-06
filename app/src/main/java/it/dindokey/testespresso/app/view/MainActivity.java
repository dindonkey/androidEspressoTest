package it.dindokey.testespresso.app.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import it.dindokey.testespresso.app.App;
import it.dindokey.testespresso.app.R;
import it.dindokey.testespresso.app.api.ProductsApiService;
import it.dindokey.testespresso.app.cache.InstanceStateCache;
import it.dindokey.testespresso.app.presenter.MainPresenter;
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

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((App) getApplication()).getComponent().inject(this);

        productListViewAdapter = new ProductListViewAdapter(this
        );

        ListView listView = (ListView) findViewById(R.id.list_view);
        if (null != listView)
        {
            listView.setAdapter(productListViewAdapter);
        }
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
        productListViewAdapter.setValues(Collections.singletonList(getString(R.string.loading_message)));
        productListViewAdapter.reload();
    }

    @Override
    public void showError()
    {
        productListViewAdapter.setValues(Collections.singletonList(getString(R.string.error_message)));
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
