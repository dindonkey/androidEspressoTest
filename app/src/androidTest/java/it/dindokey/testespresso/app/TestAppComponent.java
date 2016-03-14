package it.dindokey.testespresso.app;

import javax.inject.Singleton;

import dagger.Component;
import it.dindokey.testespresso.app.view.MainActivityTest;

/**
 * Created by simone on 3/11/16.
 */
@Singleton
@Component(modules = TestAppModule.class)
public interface TestAppComponent extends AppComponent
{
    void inject(MainActivityTest test);
}
