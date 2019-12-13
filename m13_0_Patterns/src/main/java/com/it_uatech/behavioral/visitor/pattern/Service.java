package com.it_uatech.behavioral.visitor.pattern;

import com.it_uatech.behavioral.visitor.pattern.elements.Body;
import com.it_uatech.behavioral.visitor.pattern.elements.Engine;
import com.it_uatech.behavioral.visitor.pattern.elements.Wheel;

/**
 * Created by tully.
 * <p>
 * Visitor in the Visitor pattern.
 * An abstract service for elements of a car.
 */
public interface Service {

    void visit(Wheel element);

    void visit(Engine element);

    void visit(Body element);
}
