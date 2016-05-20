package it.dindokey.testespresso.app;

import android.app.Application;


public class App extends Application
{
    private AppComponent component;

    @Override
    public void onCreate()
    {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();
    }

    public AppComponent getComponent()
    {
        return component;
    }

    public void setComponent(AppComponent component)
    {
        this.component = component;
    }

}
