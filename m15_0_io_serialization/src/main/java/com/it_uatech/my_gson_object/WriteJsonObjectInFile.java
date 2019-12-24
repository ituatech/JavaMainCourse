package com.it_uatech.my_gson_object;

import com.google.gson.Gson;

import java.io.IOException;

public class WriteJsonObjectInFile {
    public static void main(String[] args) throws IOException {
        ObjectForWriting objectForWriting = new ObjectForWriting();
        Gson gson = new Gson();
        String json = gson.toJson(objectForWriting);


        JavaIOHelper.writeToFile(json.getBytes("UTF-8"),"jsondata1.txt");
    }
}
