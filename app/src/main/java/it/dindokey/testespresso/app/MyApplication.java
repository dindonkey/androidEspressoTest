package it.dindokey.testespresso.app;

import android.app.Application;
import android.util.Log;
import it.dindokey.testespresso.app.api.ProductsApi;

/**
 * Created by simone on 2/9/16.
 */
public class MyApplication extends Application
{
    private ProductsApi productsApi;

    public MyApplication()
    {
        productsApi = new ProductsApi();
    }

    public ProductsApi getProductsApi()
    {
        return productsApi;
    }

    public void setProductsApi(ProductsApi productsApi)
    {
        this.productsApi = productsApi;
    }
}
