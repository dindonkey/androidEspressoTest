package it.dindokey.testespresso.app;

import dagger.Component;

import javax.inject.Singleton;

/**
 * Created by simone on 2/19/16.
 */
@Singleton
@Component(modules={AppModule.class, ApiModule.class})
public interface ApiComponent
{
    void inject(MainActivity activity);
}
