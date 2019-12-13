package com.it_uatech.behavioral.visitor.pattern.services;

import com.it_uatech.behavioral.visitor.pattern.Service;
import com.it_uatech.behavioral.visitor.pattern.elements.Body;
import com.it_uatech.behavioral.visitor.pattern.elements.Engine;
import com.it_uatech.behavioral.visitor.pattern.elements.Wheel;

/**
 * Created by tully.
 */
public class CarRepairService implements Service {
    @Override
    public void visit(Wheel element) {
        System.out.println("Repairing: " + element.getName());
    }

    @Override
    public void visit(Engine element) {
        System.out.println("Repairing: " + element.getName());
    }

    @Override
    public void visit(Body element) {
        System.out.println("Repairing: " + element.getName());
    }
}
