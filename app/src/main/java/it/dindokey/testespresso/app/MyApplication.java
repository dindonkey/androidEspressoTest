package it.dindokey.testespresso.app;

import android.app.Application;
import it.dindokey.testespresso.app.api.ApiComponent;
import it.dindokey.testespresso.app.api.ApiModule;


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
