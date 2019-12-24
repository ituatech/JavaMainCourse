package com.it_uatech.json_app;

import com.google.gson.Gson;
import com.it_uatech.json_app.strategy.StrategyFactory;
import com.it_uatech.serializable.BigObjectForWriting;
import org.junit.Before;
import org.junit.Test;

import javax.json.JsonValue;

import static org.junit.Assert.*;

public class JsonAppTest {

    JsonApp jsonApp;
    Gson gson;

    @Before
    public void setUp(){
        jsonApp = new JsonApp();
        gson = new Gson();
    }

    @Test
    public void canSerializeAndDeserializeBigObject(){
        BigObjectForWriting testObject = new BigObjectForWriting();
        String model = jsonApp.writeJson(testObject);

        BigObjectForWriting testObjectDeserialize = gson.fromJson(model, BigObjectForWriting.class);
        assertEquals(testObject,testObjectDeserialize);

        BigObjectForWriting nullObject = null;
        model = jsonApp.writeJson(nullObject);
        assertEquals(null,nullObject);
    }
}