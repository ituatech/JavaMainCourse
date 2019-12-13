package com.it_uatech.behavioral.visitor.pattern;

import com.it_uatech.behavioral.visitor.pattern.services.CarRepairService;
import com.it_uatech.behavioral.visitor.pattern.services.CarWashService;

/**
 * Created by tully.
 */
public class Main {
    public static void main(String[] args) {
        Car car = new Car();
        car.doService(new CarRepairService());
        car.doService(new CarWashService());
    }
}
