package com.it_uatech;

import com.it_uatech.storage.ATMStorage;

import java.util.HashMap;
import java.util.Map;

public class ATMDepartment {

    private Map<String, ATMStorage> atmList = new HashMap<>();

    public void register(String atmKey, ATMStorage atm) {
        atmList.putIfAbsent(atmKey, atm);
    }

    public void unregister(String atmKey) {
        atmList.remove(atmKey);
    }

    public ATMStorage getAtm(String atmKey) {
        return atmList.get(atmKey);
    }


    public int getATMBalances() {
        int totalAmount = atmList.entrySet().stream().mapToInt(entry -> entry.getValue().getTotalAmount()).sum();
        return totalAmount;
    }

    public void restoreInitState() {
        atmList.entrySet().forEach(entry -> entry.getValue().restoreInitState());
    }
}
