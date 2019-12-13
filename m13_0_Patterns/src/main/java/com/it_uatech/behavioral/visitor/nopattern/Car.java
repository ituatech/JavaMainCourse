package com.it_uatech.behavioral.visitor.nopattern;


import com.it_uatech.behavioral.visitor.nopattern.elements.Body;
import com.it_uatech.behavioral.visitor.nopattern.elements.Engine;
import com.it_uatech.behavioral.visitor.nopattern.elements.Wheel;

import java.util.Arrays;

/**
 * Client class in the Visitor pattern.
 * Contains elements for service.
 * <p>
 * Created by tully.
 */
@SuppressWarnings("WeakerAccess")
public class Car {
    private final Body body;
    private final Engine engine;
    private final Wheel[] wheels;

    public Car() {
        engine = new Engine();
        body = new Body();
        wheels = new Wheel[4];
        Arrays.fill(wheels, new Wheel());
    }

    public void doRepair() {
        body.doRepair();
        engine.doRepair();
        for (Wheel wheel : wheels) {
            wheel.doRepair();
        }
    }

    public void doWash() {
        body.doWash();
        engine.doWash();
        for (Wheel wheel : wheels) {
            wheel.doWash();
        }
    }

    public void doTO() {
        body.doWash();
        engine.doWash();
        for (Wheel wheel : wheels) {
            wheel.doWash();
        }
    }
}
