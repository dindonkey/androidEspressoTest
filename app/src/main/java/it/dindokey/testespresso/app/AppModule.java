package it.dindokey.testespresso.app;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.dindokey.testespresso.app.api.HttpClient;
import it.dindokey.testespresso.app.api.ProductsApiService;
import it.dindokey.testespresso.app.api.SimpleProductsApiService;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by simone on 3/10/16.
 */
@Module
public class AppModule
{
    @Provides
    @Singleton
    ProductsApiService providesProductsApi()
    {
        return new SimpleProductsApiService(new HttpClient());
    }

    @Provides
    @Singleton
    SchedulerManager providesSchedulerManager()
    {
        return new SchedulerManager(Schedulers.io(), AndroidSchedulers.mainThread());
    }

    @Provides
    @Singleton
    ObservableCache providesObservableCache()
    {
        return new ObservableCache();
    }
}
