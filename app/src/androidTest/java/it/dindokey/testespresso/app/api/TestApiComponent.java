package it.dindokey.testespresso.app.api;

import dagger.Component;
import it.dindokey.testespresso.app.espresso.MainActivityTest;

import javax.inject.Singleton;

/**
 * Created by simone on 2/23/16.
 */
@Singleton
@Component(modules = TestApiModule.class)
public interface TestApiComponent extends ApiComponent
{
    void inject(MainActivityTest mainActivityTest);
}
