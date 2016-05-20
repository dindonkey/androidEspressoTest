package it.dindokey.testespresso.app.cache;

import rx.observables.ConnectableObservable;

/**
 * Created by simone on 5/4/16.
 */
public class ObservableCache
{
    private ConnectableObservable observable;

    public ConnectableObservable observable()
    {
        return observable;
    }

    public void store(ConnectableObservable observable)
    {
        this.observable = observable;
    }

    public void clear()
    {
        observable = null;
    }
}
