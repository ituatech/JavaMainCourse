package com.it_uatech.serializable;

import java.util.Objects;

public class SimpleObject {
    private String telephoneType;
    private String TelephoneNumber;
    private boolean numberOld;
    private int money;

    public SimpleObject(String telephoneType, String TelephoneNumber, boolean numberOld, int money) {
        this.telephoneType = telephoneType;
        this.TelephoneNumber = TelephoneNumber;
        this.numberOld = numberOld;
        this.money = money;
    }

    public SimpleObject(){
        this.telephoneType = "home";
        this.TelephoneNumber = "324-76-88";
        this.numberOld = true;
        money = 123;
    }

    public String getTelephoneType() {
        return telephoneType;
    }

    public String getTelephoneNumber() {
        return TelephoneNumber;
    }

    public boolean isNumberOld() {
        return numberOld;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleObject simpleObject = (SimpleObject) o;
        return numberOld == simpleObject.numberOld &&
                money == simpleObject.money &&
                telephoneType.equals(simpleObject.telephoneType) &&
                TelephoneNumber.equals(simpleObject.TelephoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(telephoneType, TelephoneNumber, numberOld, money);
    }

    public int getMoney() {
        return money;
    }

}
