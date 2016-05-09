package it.dindokey.testespresso.app;

import rx.Observable;
import rx.Observer;

/**
 * Created by simone on 5/6/16.
 */
public interface SubscriberManager
{
    void doSubscriptionWith(Observable observable, Observer observer);
    void doUnsubscribe();
}
