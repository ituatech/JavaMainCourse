package com.it_uatech;

import com.it_uatech.atm_storage.ATMStorage;

import java.util.ArrayList;
import java.util.List;

public class ATMDepartment {

    private List<ATMStorage> atmList = new ArrayList<>();

    public void register(ATMStorage atm) {
        atmList.add(atm);
    }

    public void unregister(ATMStorage atm) {
        atmList.remove(atm);
    }

    boolean contains (ATMStorage atm) {
        return atmList.contains(atm);
    }


    public int getATMBalances() {
        int totalAmount = atmList.stream().mapToInt(ATMStorage::getTotalAmount).sum();
        return totalAmount;
    }
}
