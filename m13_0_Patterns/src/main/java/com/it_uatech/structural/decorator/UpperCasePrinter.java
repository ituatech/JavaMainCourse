package com.it_uatech.structural.decorator;

/**
 * Created by tully.
 */
public class UpperCasePrinter extends PrinterDecorator {
    public UpperCasePrinter(Printer printer) {
        super(printer);
    }

    @Override
    public void print(String str) {
        super.print(str.toUpperCase());
    }
}
