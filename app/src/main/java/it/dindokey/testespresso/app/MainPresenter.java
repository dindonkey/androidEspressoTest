package it.dindokey.testespresso.app;

import android.os.AsyncTask;
import it.dindokey.testespresso.app.api.MainView;
import it.dindokey.testespresso.app.api.ProductsApi;

/**
 * Created by simone on 2/12/16.
 */
public class MainPresenter
{
    private MainView mainView;

    public MainPresenter(MainView mainView)
    {
        this.mainView = mainView;
    }

    public void onTakeView()
    {
        new AsyncTask<Void, Void, String[]>()
        {
            @Override
            protected String[] doInBackground(Void... params)
            {
//                ProductsApi productsApi = ((MyApplication) activity.getApplication()).getProductsApi();
                ProductsApi productsApi = new ProductsApi();
                return productsApi.getProducts();
            }

            @Override
            protected void onPostExecute(String[] result)
            {
              mainView.refreshProductList(result);
            }
        }.execute();
    }
}
