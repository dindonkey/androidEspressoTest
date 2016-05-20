package it.dindokey.testespresso.app;

import javax.inject.Singleton;

import dagger.Component;
import it.dindokey.testespresso.app.view.MainActivity;

@Singleton
@Component (modules = AppModule.class)
public interface AppComponent
{
    void inject(MainActivity activity);
}
