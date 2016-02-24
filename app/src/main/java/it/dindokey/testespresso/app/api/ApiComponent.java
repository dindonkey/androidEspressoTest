package it.dindokey.testespresso.app.api;

import dagger.Component;
import it.dindokey.testespresso.app.activity.MainActivity;

import javax.inject.Singleton;

/**
 * Created by simone on 2/19/16.
 */
@Singleton
@Component(modules = ApiModule.class)
public interface ApiComponent
{
    void inject(MainActivity activity);
}
