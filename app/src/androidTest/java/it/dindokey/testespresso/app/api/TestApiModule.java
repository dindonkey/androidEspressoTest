package it.dindokey.testespresso.app.api;

import dagger.Module;
import dagger.Provides;
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
