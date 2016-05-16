package it.dindokey.testespresso.app.rx;

import rx.Observable;
import rx.Observer;

/**
 * Created by simone on 5/6/16.
 */
public interface ObservableExecutor
{
    void execute(Observable observable, Observer observer);
    void unsubscribe();
}
