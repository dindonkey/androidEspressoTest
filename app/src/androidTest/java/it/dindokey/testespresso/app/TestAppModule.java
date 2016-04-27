package it.dindokey.testespresso.app;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.dindokey.testespresso.app.api.ProductsApiService;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by simone on 3/11/16.
 */
@Module
public class TestAppModule
{
    @Provides @Singleton
    ProductsApiService providesProductsApi()
    {
        return Mockito.mock(ProductsApiService.class);
    }

    @Provides @Singleton SchedulerManager providesSchedulerManager()
    {
        return new SchedulerManager(Schedulers.from(EspressoExecutor.getCachedThreadPool()), AndroidSchedulers.mainThread());
    }
}
