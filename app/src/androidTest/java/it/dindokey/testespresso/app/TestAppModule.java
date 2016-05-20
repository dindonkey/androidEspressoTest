package it.dindokey.testespresso.app;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.dindokey.testespresso.app.api.ProductsApiService;
import it.dindokey.testespresso.app.cache.InstanceStateCache;
import it.dindokey.testespresso.app.cache.ObservableCache;
import it.dindokey.testespresso.app.rx.SchedulerManager;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class TestAppModule
{
    @Provides
    @Singleton
    ProductsApiService providesProductsApi()
    {
        return Mockito.mock(ProductsApiService.class);
    }

    @Provides
    @Singleton
    SchedulerManager providesSchedulerManager()
    {
        return new SchedulerManager(Schedulers.from(EspressoExecutor.getCachedThreadPool()),
                AndroidSchedulers.mainThread());
    }

    @Provides
    @Singleton
    ObservableCache providesObservableCache()
    {
        return new ObservableCache();
    }

    @Provides
    @Singleton
    InstanceStateCache providesInstanceStateCache()
    {
        return new InstanceStateCache();
    }
}
