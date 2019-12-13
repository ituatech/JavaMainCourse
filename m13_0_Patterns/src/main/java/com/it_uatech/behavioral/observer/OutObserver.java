package com.it_uatech.behavioral.observer;

/**
 * Created by tully.
 */
public class OutObserver implements Observer {
    @Override
    public void notify(Event event) {
        System.out.println(event.getClass());
    }
}
