package it.dindokey.testespresso.app.api;

/**
 * Created by simone on 2/8/16.
 */
public class ProductsApiService
{
    public String[] getProducts()
    {
        try
        {
            Thread.sleep(10000); //simulate slow network communication
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return new String[]{"First product"};
    }
}
