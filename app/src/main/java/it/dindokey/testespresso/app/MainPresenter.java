package it.dindokey.testespresso.app;

import android.os.AsyncTask;
import it.dindokey.testespresso.app.api.ProductsApi;

import javax.inject.Inject;

/**
 * Created by simone on 2/24/16.
 */
public class MainPresenter
{
    private MainView view;

    @Inject
    public ProductsApi productsApi;

    public MainPresenter(App app, MainView view)
    {
        this.view = view;
        app.apiComponent().inject(this);
    }

    public void onStart()
    {
        new AsyncTask<Void, Void, String[]>()
        {
            @Override
            protected void onPostExecute(String[] result)
            {
                view.refreshProductList(result);
            }

            @Override
            protected String[] doInBackground(Void... params)
            {
                return productsApi.getProducts();
            }
        }.execute();
    }
}
