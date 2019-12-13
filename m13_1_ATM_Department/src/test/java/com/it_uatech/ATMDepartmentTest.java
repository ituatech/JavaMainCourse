package com.it_uatech;

import com.it_uatech.atm_storage.ATMStorage;
import com.it_uatech.model.Banknote;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class ATMDepartmentTest {

    private ATMDepartment atmDepartment;

    @Before
    public void setUp() {
        atmDepartment = new ATMDepartment();
    }

    @Test
    public void shouldRegisterAndUnregisterSingleATM() {
        ATMStorage atm1 = ATMStorage.atmStorageBuilder();
        ATMStorage atm2 = ATMStorage.atmStorageBuilder();
        atmDepartment.register(atm1);
        atmDepartment.register(atm2);
        assertTrue(atmDepartment.contains(atm1));
        assertTrue(atmDepartment.contains(atm2));

        atmDepartment.unregister(atm1);
        assertFalse(atmDepartment.contains(atm1));
    }

    @Test
    public void canCollectSumOfBalancesFromAllATM(){
        Banknote[] banknotes = Banknote.values();
        ATMStorage atm1 = ATMStorage.atmStorageBuilder(banknotes);
        HashMap<Banknote,Integer> banknoteMap = new HashMap<>();
        banknoteMap.put(Banknote.ONE_DOLLAR,15);
        banknoteMap.put(Banknote.FIVE_DOLLARS,54);
        banknoteMap.put(Banknote.TEN_DOLLARS,4);
        banknoteMap.put(Banknote.FIFTY_DOLLARS,42);
        atm1.loadBanknotesMapGivenNominal(banknoteMap);

        ATMStorage atm2 = ATMStorage.atmStorageBuilder(banknotes);
        HashMap<Banknote,Integer> banknoteMap1 = new HashMap<>();
        banknoteMap1.put(Banknote.ONE_DOLLAR,45);
        banknoteMap1.put(Banknote.FIVE_DOLLARS,56);
        banknoteMap1.put(Banknote.TEN_DOLLARS,789);
        banknoteMap1.put(Banknote.FIFTY_DOLLARS,43);
        atm2.loadBanknotesMapGivenNominal(banknoteMap1);

        atmDepartment.register(atm1);
        atmDepartment.register(atm2);
        assertEquals(atm1.getTotalAmount() + atm2.getTotalAmount(),atmDepartment.getATMBalances());
    }


}