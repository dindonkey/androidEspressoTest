package it.dindokey.testespresso.app;

import dagger.Module;
import dagger.Provides;
import it.dindokey.testespresso.app.api.ProductsApi;

import javax.inject.Singleton;

/**
 * Created by simone on 2/19/16.
 */
@Module
public class ApiModule
{
    @Provides
    @Singleton
    public ProductsApi provideProductsApi()
    {
        return new ProductsApi();
    }
}
