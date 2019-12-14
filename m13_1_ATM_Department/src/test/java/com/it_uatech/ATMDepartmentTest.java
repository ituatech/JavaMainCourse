package com.it_uatech;

import com.it_uatech.model.Banknote;
import com.it_uatech.storage.ATMStorage;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ATMDepartmentTest {

    private ATMDepartment atmDepartment;
    private ATMStorage atm1;
    private ATMStorage atm2;

    @Before
    public void setUp() {
        atmDepartment = new ATMDepartment();
        atm1 = ATMStorage.atmStorageBuilder(Banknote.values());
        atm2 = ATMStorage.atmStorageBuilder(Banknote.values());
        atmDepartment.register("#111", atm1);
        atmDepartment.register("#222", atm2);
    }

    @Test
    public void shouldRegisterAndUnregisterSingleATM() {
        atmDepartment.register("#333", atm1);
        atmDepartment.register("#444", atm2);
        assertSame(atm1, atmDepartment.getAtm("#333"));
        assertSame(atm2, atmDepartment.getAtm("#444"));

        atmDepartment.unregister("#333");
        assertNull(atmDepartment.getAtm("#333"));
    }

    @Test
    public void canCollectSumOfBalancesFromAllATM() {
        HashMap<Banknote, Integer> banknoteMapFirstATM = new HashMap<>();
        banknoteMapFirstATM.put(Banknote.ONE_DOLLAR, 15);
        banknoteMapFirstATM.put(Banknote.FIVE_DOLLARS, 54);
        banknoteMapFirstATM.put(Banknote.FIFTY_DOLLARS, 42);
        atm1.loadBanknotesMapGivenNominal(banknoteMapFirstATM);

        HashMap<Banknote, Integer> banknoteMapSecondATM = new HashMap<>();
        banknoteMapSecondATM.put(Banknote.ONE_DOLLAR, 45);
        banknoteMapSecondATM.put(Banknote.FIVE_DOLLARS, 56);
        banknoteMapSecondATM.put(Banknote.TEN_DOLLARS, 789);
        banknoteMapSecondATM.put(Banknote.FIFTY_DOLLARS, 43);
        atm2.loadBanknotesMapGivenNominal(banknoteMapSecondATM);

        assertEquals(atm1.getTotalAmount() + atm2.getTotalAmount(), atmDepartment.getATMBalances());
    }

    @Test
    public void shouldRestoreAllATMToThereInitialState() {
        HashMap<Banknote, Integer> initStateFirstATM = new HashMap<>();
        Arrays.asList(Banknote.values()).forEach(banknote -> initStateFirstATM.put(banknote, (int) (Math.random() * 100 + 20)));
        atm1.setInitialState(initStateFirstATM);
        atm1.loadBanknotesMapGivenNominal(initStateFirstATM);
        int initTotalAmountFirstATM = atm1.getTotalAmount();

        HashMap<Banknote, Integer> initStateSecondATM = new HashMap<>();
        Arrays.asList(Banknote.values()).forEach(banknote -> initStateSecondATM.put(banknote, (int) (Math.random() * 100 + 20)));
        atm2.setInitialState(initStateSecondATM);
        atm2.loadBanknotesMapGivenNominal(initStateSecondATM);
        int initTotalAmountSecondATM = atm2.getTotalAmount();

        atm1.withDrawGivenAmount(initTotalAmountFirstATM - initTotalAmountFirstATM/4);
        assertNotEquals(initTotalAmountFirstATM, atm1.getTotalAmount());

        atm2.withDrawGivenAmount(initTotalAmountSecondATM - initTotalAmountSecondATM/5);
        assertNotEquals(initTotalAmountSecondATM, atm2.getTotalAmount());

        atmDepartment.restoreInitState();
        assertEquals(initTotalAmountFirstATM, atm1.getTotalAmount());
        assertEquals(initTotalAmountSecondATM, atm2.getTotalAmount());
    }


}