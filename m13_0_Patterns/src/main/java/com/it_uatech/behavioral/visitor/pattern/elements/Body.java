package com.it_uatech.behavioral.visitor.pattern.elements;

import com.it_uatech.behavioral.visitor.pattern.CarElement;
import com.it_uatech.behavioral.visitor.pattern.Service;

/**
 * Created by tully.
 */
public class Body implements CarElement {
    @Override
    public String getName() {
        return "body";
    }

    public void accept(Service visitor) {
        visitor.visit(this);
    }
}
