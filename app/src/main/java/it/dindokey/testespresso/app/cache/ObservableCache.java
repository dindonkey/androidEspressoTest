package it.dindokey.testespresso.app.cache;

import java.util.List;

import rx.observables.ConnectableObservable;

/**
 * Created by simone on 5/4/16.
 */
public class ObservableCache
{
    private ConnectableObservable<List<String>> observable;

    public ConnectableObservable<List<String>> observable()
    {
        return observable;
    }

    public void store(ConnectableObservable<List<String>> observable)
    {
        this.observable = observable;
    }

    public void clear()
    {
        observable = null;
    }
}
