package com.it_uatech.structural.decorator;

/**
 * Created by tully.
 * <p>
 * CoreFunctionality in the Decorator pattern.
 */
public class PrinterImpl implements Printer {

    @Override
    public void print(String str) {
        System.out.println(str);
    }
}
