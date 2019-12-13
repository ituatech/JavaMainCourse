package com.it_uatech.model;

import org.junit.Assert;
import org.junit.Test;

public class BanknoteTest {

    @Test
    public void getValueAndValueOf() {
        Banknote one = Banknote.valueOf("ONE_DOLLAR");
        Assert.assertEquals(1, one.getValue());
        Assert.assertSame(Banknote.ONE_DOLLAR, one);
        Banknote one1 = Banknote.valueOf(Banknote.ONE_DOLLAR.name());
        Assert.assertEquals(1, one1.getValue());
        Assert.assertSame(one, one1);

        Banknote two = Banknote.valueOf(Banknote.TWO_DOLLARS.name());
        Assert.assertNotSame(one1, two);
    }

    @Test
    public void getArrayOfBanknotes() {
        Banknote[] banknotes = {Banknote.ONE_DOLLAR, Banknote.TWO_DOLLARS, Banknote.FIVE_DOLLARS, Banknote.TEN_DOLLARS, Banknote.TWENTY_DOLLARS, Banknote.FIFTY_DOLLARS, Banknote.HUNDRED_DOLLARS};
        Banknote[] banknoteRetrived = Banknote.values();
        Assert.assertArrayEquals(banknotes, banknoteRetrived);
    }
}