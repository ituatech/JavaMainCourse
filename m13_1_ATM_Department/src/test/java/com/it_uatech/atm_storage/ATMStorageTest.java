package com.it_uatech.atm_storage;

import com.it_uatech.model.Banknote;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;


public class ATMStorageTest {

    ATMStorage atmStorage;

    @Before
    public void setUp() {
        Banknote[] banknotes = Banknote.values();
        atmStorage = ATMStorage.atmStorageBuilder(banknotes);
    }

    @Test
    public void checkBanknotesWereLoadedInStorage(){
        Assert.assertNotNull(atmStorage.getNumberOfBanknotesGivenNominal(Banknote.ONE_DOLLAR));
        Assert.assertNotNull(atmStorage.getNumberOfBanknotesGivenNominal(Banknote.TWO_DOLLARS));
        Assert.assertNotNull(atmStorage.getNumberOfBanknotesGivenNominal(Banknote.TWENTY_DOLLARS));
        Assert.assertNotNull(atmStorage.getNumberOfBanknotesGivenNominal(Banknote.FIFTY_DOLLARS));
    }

    @Test
    public void canLoadAndRetrieveNumberOfBanknotesByGivenNominal(){
        int numberOneDollarBanknotes = 12;
        int numberFiveDollarBanknotes = 14;
        atmStorage.loadBanknotesGivenNominal(Banknote.ONE_DOLLAR,numberOneDollarBanknotes);
        atmStorage.loadBanknotesGivenNominal(Banknote.FIVE_DOLLARS,numberFiveDollarBanknotes);
        int retrieveNumberOneDollarBanknotes = atmStorage.getNumberOfBanknotesGivenNominal(Banknote.ONE_DOLLAR);
        int retrieveNumberFiveDollarBanknotes = atmStorage.getNumberOfBanknotesGivenNominal(Banknote.FIVE_DOLLARS);

        Assert.assertEquals(numberOneDollarBanknotes,retrieveNumberOneDollarBanknotes);
        Assert.assertEquals(numberFiveDollarBanknotes,retrieveNumberFiveDollarBanknotes);
    }

    @Test
    public void checkRetrieveSumOfBanknotesNumberByLoadSameNominal(){
        int addFirstTimeBanknotesNumber = 7;
        int addSecondTimeBanknotesNumber = 15;

        atmStorage.loadBanknotesGivenNominal(Banknote.ONE_DOLLAR,addFirstTimeBanknotesNumber);
        Assert.assertEquals(addFirstTimeBanknotesNumber,atmStorage.getNumberOfBanknotesGivenNominal(Banknote.ONE_DOLLAR));

        atmStorage.loadBanknotesGivenNominal(Banknote.ONE_DOLLAR,addSecondTimeBanknotesNumber);
        Assert.assertEquals(addFirstTimeBanknotesNumber+addSecondTimeBanknotesNumber,atmStorage.getNumberOfBanknotesGivenNominal(Banknote.ONE_DOLLAR));
    }

    @Test
    public void canGetCorrectTotalAmountFromATM(){
        Assert.assertEquals(0,atmStorage.getTotalAmount());
        int oneDollarNumber = 23;
        int twoDollarNumber = 7;
        int fiveDollarNumber = 25;
        int tenDollarNumber = 12;
        int twentyDollarNumber = 47;
        int fiftyDollarNumber = 63;
        int hundredDollarNumber = 145;
        int totalAmount = oneDollarNumber+twoDollarNumber*2+fiveDollarNumber*5+tenDollarNumber*10+twentyDollarNumber*20+fiftyDollarNumber*50+hundredDollarNumber*100;
        atmStorage.loadBanknotesGivenNominal(Banknote.ONE_DOLLAR,oneDollarNumber);
        atmStorage.loadBanknotesGivenNominal(Banknote.TWO_DOLLARS,twoDollarNumber);
        atmStorage.loadBanknotesGivenNominal(Banknote.FIVE_DOLLARS,fiveDollarNumber);
        atmStorage.loadBanknotesGivenNominal(Banknote.TEN_DOLLARS,tenDollarNumber);
        atmStorage.loadBanknotesGivenNominal(Banknote.TWENTY_DOLLARS,twentyDollarNumber);
        atmStorage.loadBanknotesGivenNominal(Banknote.FIFTY_DOLLARS,fiftyDollarNumber);
        atmStorage.loadBanknotesGivenNominal(Banknote.HUNDRED_DOLLARS,hundredDollarNumber);
        Assert.assertEquals(totalAmount,atmStorage.getTotalAmount());
    }

    @Test
    public void canLoadMapOfBanknotesGivenNominal(){
        int oneDollarNumber = 23;
        int fiveDollarNumber = 25;
        HashMap<Banknote,Integer> banknoteMap = new HashMap<>();
        banknoteMap.put(Banknote.ONE_DOLLAR,oneDollarNumber);
        banknoteMap.put(Banknote.FIVE_DOLLARS,fiveDollarNumber);
        atmStorage.loadBanknotesMapGivenNominal(banknoteMap);
        Assert.assertEquals(oneDollarNumber,atmStorage.getNumberOfBanknotesGivenNominal(Banknote.ONE_DOLLAR));
        Assert.assertEquals(fiveDollarNumber,atmStorage.getNumberOfBanknotesGivenNominal(Banknote.FIVE_DOLLARS));
    }

    @Test
    public void canWithDrawNumberOfBanknotesGivenNominal(){
        int fiveDollarBanknotesNumber = 100;
        int fiveDollarBanknotesWithDraw = 45;        ;
        atmStorage.loadBanknotesGivenNominal(Banknote.FIVE_DOLLARS,fiveDollarBanknotesNumber);
        atmStorage.withDrawBanknotesGivenNominal(Banknote.FIVE_DOLLARS,fiveDollarBanknotesWithDraw);
        Assert.assertEquals(fiveDollarBanknotesNumber-fiveDollarBanknotesWithDraw,atmStorage.getNumberOfBanknotesGivenNominal(Banknote.FIVE_DOLLARS));
    }

    @Test
    public void canWithDrawMapOfBanknotesGivenNominal(){
        int oneDollarNumber = 15;
        int fiveDollarNumber = 20;
        int oneDollarWithDraw = 10;
        int fiveDollarWithDraw =11;
        HashMap<Banknote,Integer> banknoteMapToLoad = new HashMap<>();
        banknoteMapToLoad.put(Banknote.ONE_DOLLAR,oneDollarNumber);
        banknoteMapToLoad.put(Banknote.FIVE_DOLLARS,fiveDollarNumber);
        atmStorage.loadBanknotesMapGivenNominal(banknoteMapToLoad);

        HashMap<Banknote,Integer> banknoteMapWithDraw = new HashMap<>();
        banknoteMapWithDraw.put(Banknote.ONE_DOLLAR,oneDollarWithDraw);
        banknoteMapWithDraw.put(Banknote.FIVE_DOLLARS,fiveDollarWithDraw);
        atmStorage.withDrawBanknotesMapGivenNominal(banknoteMapWithDraw);
        Assert.assertEquals(oneDollarNumber-oneDollarWithDraw,atmStorage.getNumberOfBanknotesGivenNominal(Banknote.ONE_DOLLAR));
        Assert.assertEquals(fiveDollarNumber-fiveDollarWithDraw,atmStorage.getNumberOfBanknotesGivenNominal(Banknote.FIVE_DOLLARS));
    }

    @Test
    public void canWithDrawGivenAmountFromStorage(){
        Banknote[] banknotes = Banknote.values();
        HashMap<Banknote,Integer> BanknotesToLoad = new HashMap<>();
        for(Banknote banknote : banknotes){
            BanknotesToLoad.put(banknote,30);
        }
        atmStorage.loadBanknotesMapGivenNominal(BanknotesToLoad);
        int amountToWithDraw = 1343;
        int totalAmountInStorage = atmStorage.getTotalAmount();
        atmStorage.withDrawGivenAmount(amountToWithDraw);
        Assert.assertEquals(totalAmountInStorage-amountToWithDraw,atmStorage.getTotalAmount());
    }

    @Test
    public void canWithDrawGivenAmountFromStorageWithLessBanknotes(){
        Banknote[] banknotes = Banknote.values();
        HashMap<Banknote,Integer> BanknotesToLoad = new HashMap<>();
        for(Banknote banknote : banknotes){
            BanknotesToLoad.put(banknote,10);
        }
        atmStorage.loadBanknotesMapGivenNominal(BanknotesToLoad);
        int amountToWithDraw = 957;
        int totalAmountInStorage = atmStorage.getTotalAmount();
        atmStorage.withDrawGivenAmount(amountToWithDraw);
        Assert.assertEquals(totalAmountInStorage-amountToWithDraw,atmStorage.getTotalAmount());
        Assert.assertEquals(1,atmStorage.getNumberOfBanknotesGivenNominal(Banknote.HUNDRED_DOLLARS));
        Assert.assertEquals(9,atmStorage.getNumberOfBanknotesGivenNominal(Banknote.FIFTY_DOLLARS));
        Assert.assertEquals(9,atmStorage.getNumberOfBanknotesGivenNominal(Banknote.TWO_DOLLARS));
    }

    @Test
    public void canWriteOfBalance(){
        Banknote[] banknotes = Banknote.values();
        HashMap<Banknote,Integer> BanknotesToLoad = new HashMap<>();
        for(Banknote banknote : banknotes){
            BanknotesToLoad.put(banknote,20);
        }
        atmStorage.loadBanknotesMapGivenNominal(BanknotesToLoad);
        int totalAmountInStorage = atmStorage.getTotalAmount();
        int amountToWithDraw = totalAmountInStorage;
        atmStorage.withDrawGivenAmount(amountToWithDraw);
        Assert.assertEquals(0,atmStorage.getTotalAmount());
    }

    @Test(expected = RuntimeException.class)
    public void checkIfBanknoteNominalExistInStorageByGettingBanknotesNumber(){
        ATMStorage storage = ATMStorage.atmStorageBuilder(Banknote.TWO_DOLLARS, Banknote.FIVE_DOLLARS);
        storage.getNumberOfBanknotesGivenNominal(Banknote.ONE_DOLLAR);
    }

    @Test(expected = RuntimeException.class)
    public void checkIfBanknoteNominalExistInStorageByLoadingBanknotesNumber(){
        ATMStorage storage = ATMStorage.atmStorageBuilder(Banknote.TWO_DOLLARS, Banknote.FIVE_DOLLARS);
        storage.loadBanknotesGivenNominal(Banknote.ONE_DOLLAR,10);
    }

    @Test(expected = RuntimeException.class)
    public void checkIfBanknoteNominalExistInEmptyStorage(){
        ATMStorage storage = ATMStorage.atmStorageBuilder();
        storage.getNumberOfBanknotesGivenNominal(Banknote.ONE_DOLLAR);
    }

    @Test(expected = RuntimeException.class)
    public void exceptionIfNotEnoughBanknotesGivenNominal(){
        int fiveDollarBanknotesNumber = 100;
        int fiveDollarBanknotesWithDraw = 145;        ;
        atmStorage.loadBanknotesGivenNominal(Banknote.FIVE_DOLLARS,fiveDollarBanknotesNumber);
        atmStorage.withDrawBanknotesGivenNominal(Banknote.FIVE_DOLLARS,fiveDollarBanknotesWithDraw);
    }

    @Test(expected = RuntimeException.class)
    public void exceptionByLoadNegativeBanknotesNumber(){
        atmStorage.loadBanknotesGivenNominal(Banknote.FIVE_DOLLARS,-10);
    }

    @Test(expected = RuntimeException.class)
    public void exceptionByWithDrawNegativeBanknotesNumber(){
        atmStorage.loadBanknotesGivenNominal(Banknote.FIVE_DOLLARS,10);
        atmStorage.withDrawBanknotesGivenNominal(Banknote.FIVE_DOLLARS,-10);
    }

    @Test(expected = RuntimeException.class)
    public void exceptionByNullAsBanknoteNominalByGettingBanknotesGivenNominal(){
        atmStorage.getNumberOfBanknotesGivenNominal(null);
    }

    @Test(expected = RuntimeException.class)
    public void exceptionByNullAsBanknoteNominalByLoadingBanknotesGivenNominal(){
        atmStorage.loadBanknotesGivenNominal(null,10);
    }

    @Test(expected = RuntimeException.class)
    public void exceptionIfWithdrawAmountBiggerThenTotalAmount(){
        atmStorage.loadBanknotesGivenNominal(Banknote.TEN_DOLLARS,10);
        atmStorage.loadBanknotesGivenNominal(Banknote.FIVE_DOLLARS,10);
        atmStorage.withDrawGivenAmount(160);
    }

    @Test(expected = RuntimeException.class)
    public void exceptionIfNotPossibleToWithdrawAmount(){
        atmStorage.loadBanknotesGivenNominal(Banknote.TEN_DOLLARS,10);
        atmStorage.loadBanknotesGivenNominal(Banknote.FIVE_DOLLARS,10);
        atmStorage.withDrawGivenAmount(56);
    }
}