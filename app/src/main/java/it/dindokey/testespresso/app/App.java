package it.dindokey.testespresso.app;

import android.app.Application;


/**
 * Created by simone on 2/9/16.
 */
public class App extends Application
{
    private AppComponent component;

    @Override public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();
    }

    public AppComponent getComponent() {
        return component;
    }

}
