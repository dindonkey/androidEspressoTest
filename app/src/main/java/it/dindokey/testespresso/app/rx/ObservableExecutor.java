package it.dindokey.testespresso.app.rx;

import rx.Observable;
import rx.Observer;

public interface ObservableExecutor
{
    void execute(Observable observable, Observer observer);
    void unsubscribe();
}
