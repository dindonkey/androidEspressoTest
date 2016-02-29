package it.dindokey.testespresso.app;

import it.dindokey.testespresso.app.api.ApiComponent;
import it.dindokey.testespresso.app.api.DaggerTestApiComponent;
import it.dindokey.testespresso.app.api.TestApiModule;

/**
 * Created by simone on 2/23/16.
 */
public class AppTest extends App
{
    @Override
    protected ApiComponent createApiComponent()
    {
        return DaggerTestApiComponent.builder()
                .testApiModule(new TestApiModule())
                .build();
    }
}
