package com.it_uatech.creational.singleton;

/**
 * Created by tully.
 */
public class Main {
    public static void main(String[] args) {
        Singleton.instance().setMessage("Hello");

        System.out.println(Singleton.instance().getMessage());
    }
}
