package it.dindokey.testespresso.app;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.dindokey.testespresso.app.api.ProductsApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by simone on 3/10/16.
 */
@Module
public class AppModule
{
    //TODO: in case of future needs of context, add a constructor which takes Application reference
    @Provides @Singleton ProductsApi providesProductsApi()
    {
        return new ProductsApi();
    }

    @Provides @Singleton SchedulerManager providesSchedulerManager()
    {
        return new SchedulerManager(Schedulers.io(), AndroidSchedulers.mainThread());
    }
}
