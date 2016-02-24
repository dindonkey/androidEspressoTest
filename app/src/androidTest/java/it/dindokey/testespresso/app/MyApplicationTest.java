package it.dindokey.testespresso.app;

/**
 * Created by simone on 2/23/16.
 */
public class MyApplicationTest extends MyApplication
{
    @Override
    protected ApiComponent createApiComponent()
    {
        return DaggerTestApiComponent.builder()
                .testApiModule(new TestApiModule())
                .build();
    }
}
