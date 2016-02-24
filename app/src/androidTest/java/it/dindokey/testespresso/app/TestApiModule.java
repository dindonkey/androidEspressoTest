package it.dindokey.testespresso.app;

import dagger.Module;
import dagger.Provides;
import it.dindokey.testespresso.app.api.ProductsApi;
import org.mockito.Mockito;

import javax.inject.Singleton;

/**
 * Created by simone on 2/23/16.
 */
@Module
public class TestApiModule
{
    @Provides
    @Singleton
    public ProductsApi provideProductsApi()
    {
        return Mockito.mock(ProductsApi.class);
    }
}
