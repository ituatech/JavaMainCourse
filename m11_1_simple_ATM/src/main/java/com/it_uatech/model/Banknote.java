package com.it_uatech.model;

public enum Banknote {
    ONE_DOLLAR(1),
    TWO_DOLLARS(2),
    FIVE_DOLLARS(5),
    TEN_DOLLARS(10),
    TWENTY_DOLLARS(20),
    FIFTY_DOLLARS(50),
    HUNDRED_DOLLARS(100);

    private int value;

    Banknote(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
