package com.it_uatech.my_example;

public class MainClass {
    public static void main(String[] args) {
        SomeObject someObject = new SomeObject(25,"Vasya");
        SomeAnotherObject someAnotherObject = new SomeAnotherObject(67,"Bircha");
        JavaIOHelper.writeObject("my_file.txt",someObject);
        //JavaIOHelper.writeObject("my_file.txt",someAnotherObject);

        SomeObject someObject1 = JavaIOHelper.readObject("my_file.txt",SomeObject.class);
        System.out.println(someObject.equals(someObject1));
    }
}
