package it.dindokey.testespresso.app;

import android.app.Application;


/**
 * Created by simone on 2/9/16.
 */
public class MyApplication extends Application
{
    private final ApiComponent apiComponent = createApiComponent();

    protected ApiComponent createApiComponent()
    {
        return DaggerApiComponent.builder()
                .apiModule(new ApiModule())
                .build();
    }

    public ApiComponent apiComponent()
    {
        return apiComponent;
    }

}
