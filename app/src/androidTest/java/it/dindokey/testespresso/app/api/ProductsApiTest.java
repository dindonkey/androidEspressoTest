package it.dindokey.testespresso.app.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by simone on 2/8/16.
 */
public class ProductsApiTest
{
    @Test
    public void getProducts() throws Exception
    {
        ProductsApi productsApi = new ProductsApi();
        assertEquals("First product", productsApi.getProducts()[0]);
    }
}
