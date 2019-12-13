package com.it_uatech.storage;

import com.it_uatech.model.Banknote;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ATMStorage {

    private volatile Map<Banknote, Integer> banknotesStorage;
    private List<Banknote> banknoteSortedList;

    private ATMStorage() {
    }

    public static ATMStorage atmStorageBuilder(Banknote... banknotes) {
        ATMStorage atmStorage = new ATMStorage();
        atmStorage.banknotesStorage = new HashMap();
        for (Banknote banknoteNominal : banknotes) {
            atmStorage.banknotesStorage.put(banknoteNominal, 0);
        }
        atmStorage.banknoteSortedList = atmStorage.banknotesStorage.keySet().stream().sorted(Comparator.comparingInt(Banknote::getValue).reversed()).collect(Collectors.toList());
        return atmStorage;
    }

    public int getTotalAmount() {
        final int[] totalAmount = {0};
        banknotesStorage.forEach((nominal, banknotesNumber) ->
                totalAmount[0] += banknotesNumber * nominal.getValue()
        );
        return totalAmount[0];
    }

    void loadBanknotesGivenNominal(Banknote nominal, int number) {
        if (number < 0) {
            throw new RuntimeException("Check number of banknotes > 0");
        }
        int oldValue = getNumberOfBanknotesGivenNominal(nominal);
        banknotesStorage.put(nominal,oldValue + number);
    }

    public void loadBanknotesMapGivenNominal(Map<Banknote, Integer> banknoteMap) {
        banknoteMap.forEach(this::loadBanknotesGivenNominal);
    }

    int getNumberOfBanknotesGivenNominal(Banknote nominal) {
        Integer banknotesNumber = banknotesStorage.get(nominal);
        if (banknotesNumber == null) {
            throw new RuntimeException("Banknotes of this nominal don't exist");
        }
        return banknotesNumber;
    }

    void withDrawBanknotesGivenNominal(Banknote nominal, int number) {
        if (number < 0) {
            throw new RuntimeException("Check number of banknotes > 0");
        }
        int currentNumber = getNumberOfBanknotesGivenNominal(nominal);
        if (currentNumber < number) {
            throw new RuntimeException("Not enough banknotes: " + nominal.name());
        }
        banknotesStorage.put(nominal, currentNumber - number);
    }

    void withDrawBanknotesMapGivenNominal(Map<Banknote, Integer> banknoteMapWithDraw) {
        banknoteMapWithDraw.forEach(this::withDrawBanknotesGivenNominal);
    }

    public Map<Banknote, Integer> withDrawGivenAmount(final int amountToWithDraw) {
        if(amountToWithDraw > getTotalAmount()){
            throw new RuntimeException("Not enough money on your account");
        }
        int currentAmountWithdraw = amountToWithDraw;
        HashMap<Banknote, Integer> mapOfBanknotesToWithDraw = new HashMap<>();
        for (Banknote banknote:banknoteSortedList){
            int existBanknotesNumber = getNumberOfBanknotesGivenNominal(banknote);
            if (existBanknotesNumber > 0) {
                int banknotesNeeded = currentAmountWithdraw / banknote.getValue();
                if(banknotesNeeded>0){
                    banknotesNeeded = Math.min(existBanknotesNumber,banknotesNeeded);
                    mapOfBanknotesToWithDraw.put(banknote,banknotesNeeded);
                    currentAmountWithdraw -= banknotesNeeded*banknote.getValue();
                }
            }
        }
        if(currentAmountWithdraw > 0){
            throw new RuntimeException("It is not possible to withdraw the indicated amount, choose a different amount");
        }
        withDrawBanknotesMapGivenNominal(mapOfBanknotesToWithDraw);
        return mapOfBanknotesToWithDraw;
    }
}

