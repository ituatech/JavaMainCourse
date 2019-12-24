package com.it_uatech.my_gson_object;

import java.util.*;

public class ObjectForWriting {
    private String name = "Bircha";
    private int age = 45;
    private transient String favouriteBook = "London";
    private int[] apples = {43, 89, 42, 12, 23};
    private WriteJsonObjectInFile someObject = null;
    private WriteJsonObjectInFile someObjectSecond = new WriteJsonObjectInFile();
    private PhoneBook someObjectThird;
    private List<Integer> arrayList = new ArrayList<>();
    private Map<String, Integer> hashMap = new HashMap<>();
    private PhoneBook[] phoneBook = new PhoneBook[2];

    ObjectForWriting() {
        arrayList.add(89);
        arrayList.add(90);
        arrayList.add(91);

        hashMap.put("first", 22);
        hashMap.put("second", 23);
        hashMap.put("third", 24);

        phoneBook[0] = new PhoneBook("home", "567-67-89", true);
        phoneBook[1] = new PhoneBook("mobil", "432-53-21", false);

        someObjectThird = new PhoneBook("bar", "123-77-78", true);
    }
}
