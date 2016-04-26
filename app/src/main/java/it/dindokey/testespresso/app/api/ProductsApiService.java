package it.dindokey.testespresso.app.api;

import java.util.Arrays;
import java.util.List;

/**
 * Created by simone on 2/8/16.
 */
public class ProductsApiService
{
    public List<String> getProducts()
    {
        try
        {
            Thread.sleep(10000); //simulate slow network communication
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return Arrays.asList("First product");
    }
}
