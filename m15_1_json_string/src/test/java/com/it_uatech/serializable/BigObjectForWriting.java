package com.it_uatech.serializable;

import java.util.*;

public class BigObjectForWriting {
    private String name = "Bircha";
    private int age = 45;
    private transient String favouriteBook = "London";
    private int[] apples = {43,89,42,12,23};
    private SimpleObject someObject = null;
    private SimpleObject someObjectSecond = new SimpleObject("hometel","234-76-98",false,123);
    private List<Integer> arrayList = new ArrayList<>();
    private Map<String,Integer> hashMap = new HashMap<>();
    private SimpleObject[] simpleObject =  new SimpleObject[3];

    public BigObjectForWriting(){
        arrayList.add(89);
        arrayList.add(90);
        arrayList.add(91);

        hashMap.put("first",22);
        hashMap.put("second",23);
        hashMap.put("third",24);

        simpleObject[0] = new SimpleObject("home","567-67-89", true,123);
        simpleObject[1] = new SimpleObject("mobil","432-53-21", false,456);
        simpleObject[2] = new SimpleObject("mail","zu@or.com", true,789);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int[] getApples() {
        return apples;
    }

    public SimpleObject getSomeObjectSecond() {
        return someObjectSecond;
    }

    public List<Integer> getArrayList() {
        return arrayList;
    }

    public Map<String, Integer> getHashMap() {
        return hashMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BigObjectForWriting that = (BigObjectForWriting) o;
        return age == that.age &&
                name.equals(that.name) &&
                favouriteBook.equals(that.favouriteBook) &&
                Arrays.equals(apples, that.apples) &&
                Objects.equals(someObject, that.someObject) &&
                someObjectSecond.equals(that.someObjectSecond) &&
                arrayList.equals(that.arrayList) &&
                hashMap.equals(that.hashMap) &&
                Arrays.equals(simpleObject, that.simpleObject);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, age, favouriteBook, someObject, someObjectSecond, arrayList, hashMap);
        result = 31 * result + Arrays.hashCode(apples);
        result = 31 * result + Arrays.hashCode(simpleObject);
        return result;
    }
}
