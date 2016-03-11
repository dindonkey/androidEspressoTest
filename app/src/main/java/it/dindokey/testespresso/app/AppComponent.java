package it.dindokey.testespresso.app;

import javax.inject.Singleton;

import dagger.Component;
import it.dindokey.testespresso.app.ui.MainActivity;

/**
 * Created by simone on 3/10/16.
 */
@Singleton
@Component (modules = AppModule.class)
public interface AppComponent
{
    void inject(MainActivity activity);
}
