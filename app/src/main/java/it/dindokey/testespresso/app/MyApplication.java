package it.dindokey.testespresso.app;

import android.app.Application;
import it.dindokey.testespresso.app.api.DaggerApiComponent;


/**
 * Created by simone on 2/9/16.
 */
public class MyApplication extends Application
{
    private ApiComponent apiComponent;

    @Override
    public void onCreate()
    {
        super.onCreate();
        apiComponent = DaggerApiComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule())
                .build();
    }

    public ApiComponent getApiComponent()
    {
        return apiComponent;
    }

}
