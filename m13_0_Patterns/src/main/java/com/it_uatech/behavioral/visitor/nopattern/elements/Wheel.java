package com.it_uatech.behavioral.visitor.nopattern.elements;


import com.it_uatech.behavioral.visitor.nopattern.CarElement;

/**
 * Created by tully.
 */
public class Wheel implements CarElement {
    @Override
    public String getName() {
        return "wheel";
    }

    @Override
    public void doWash() {
        System.out.println("Washing: " + this.getName());
    }

    @Override
    public void doRepair() {
        System.out.println("Repairing: " + this.getName());
    }

}
