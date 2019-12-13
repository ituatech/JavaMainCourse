package com.it_uatech.behavioral.visitor.pattern;

import com.it_uatech.behavioral.visitor.pattern.elements.Body;
import com.it_uatech.behavioral.visitor.pattern.elements.Engine;
import com.it_uatech.behavioral.visitor.pattern.elements.Wheel;

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

    public void doService(Service service) {
        body.accept(service);
        engine.accept(service);
        for (Wheel wheel : wheels) {
            wheel.accept(service);
        }
    }
}
