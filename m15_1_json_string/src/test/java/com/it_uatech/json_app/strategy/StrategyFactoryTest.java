package com.it_uatech.json_app.strategy;

import com.google.gson.Gson;
import com.it_uatech.serializable.*;
import org.junit.Before;
import org.junit.Test;

import javax.json.JsonValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static org.junit.Assert.*;

public class StrategyFactoryTest {

    StrategyFactory strategyFactory;
    Gson gson;

    @Before
    public void setUp(){
        strategyFactory = new StrategyFactory();
        gson = new Gson();
    }

    @Test
    public void shouldSelectBooleanStrategyAndSerializeBooleanInJsonString() {
        Boolean aBoolean = true;
        JsonValue model = strategyFactory.createJsonObject(aBoolean);
        assertEquals("true", model.toString());
    }

    @Test
    public void shouldSelectNumberStrategyAndSerializeNumberInJsonString(){
        Number number = 123;
        JsonValue model = strategyFactory.createJsonObject(number);
        assertEquals("123", model.toString());

        number = 45.78;
        model = strategyFactory.createJsonObject(number);
        assertEquals("45.78", model.toString());

        number = 2_678_765_879L;
        model = strategyFactory.createJsonObject(number);
        assertEquals("2678765879", model.toString());

        number = BigInteger.valueOf(88);
        model = strategyFactory.createJsonObject(number);
        assertEquals("88", model.toString());

        number = BigDecimal.valueOf(546989887);
        model = strategyFactory.createJsonObject(number);
        assertEquals("546989887", model.toString());

        Byte aByte = 44;
        model = strategyFactory.createJsonObject(aByte);
        assertEquals("44", model.toString());
    }

    @Test
    public void shouldSelectStringStrategyAndSerializeStringInJsonString(){
        String str = "home";
        JsonValue model = strategyFactory.createJsonObject(str);
        assertEquals("\"home\"", model.toString());
    }

    @Test
    public void shouldSelectCharacterStrategyAndSerializeCharacterInJsonString(){
        Character character = 'A';
        JsonValue model = strategyFactory.createJsonObject(character);
        assertEquals("\"A\"", model.toString());
    }

    @Test
    public void shouldSelectObjectStrategyAndSerializeObjectInJsonString(){
        SimpleObject testObject = new SimpleObject("mobil","234-55-65",true,1111);
        JsonValue model = strategyFactory.createJsonObject(testObject);
        assertEquals("{\"telephoneType\":\"mobil\",\"TelephoneNumber\":\"234-55-65\",\"numberOld\":true,\"money\":1111}", model.toString());

        SimpleObject testObjectDeserialize = gson.fromJson(model.toString(), SimpleObject.class);
        assertEquals(testObject,testObjectDeserialize);
    }

    @Test
    public void shouldNotSerializeNullObjectsAndTransientVariables(){
        WithNullAndTransient testObject = new WithNullAndTransient(23,"mobi",false);
        JsonValue model = strategyFactory.createJsonObject(testObject);
        assertEquals("{\"number\":23,\"aBoolean\":false}", model.toString());

        WithNullAndTransient testObjectDeserialize = gson.fromJson(model.toString(),WithNullAndTransient.class);
        assertEquals(testObject,testObjectDeserialize);
    }

    @Test
    public void shouldSerializeSomeObjectWithAnotherObjectInField(){
        SimpleObject testFieldObject = new SimpleObject("mobil","234-55-65",true,1111);
        ObjectWithAnotherObject testObject = new ObjectWithAnotherObject(12,testFieldObject,false);
        JsonValue model = strategyFactory.createJsonObject(testObject);
        assertEquals("{\"number\":12,\"simpleObject\":{\"telephoneType\":\"mobil\",\"TelephoneNumber\":\"234-55-65\",\"numberOld\":true,\"money\":1111},\"aBoolean\":false}", model.toString());

        ObjectWithAnotherObject testObjectDeserialize = gson.fromJson(model.toString(),ObjectWithAnotherObject.class);
        assertEquals(testObject,testObjectDeserialize);
    }

    @Test
    public void shouldSelectArrayStrategyAndSerializeArrayInJsonString(){
        int[] array = {1,23,45};
        JsonValue model = strategyFactory.createJsonObject(array);
        assertEquals("[1,23,45]", model.toString());

        SimpleObject[] simpleObjects = {new SimpleObject("home","566-76-98",false,222),
                                        new SimpleObject("mobil","234-55-65",true,1111)};
        model = strategyFactory.createJsonObject(simpleObjects);
        assertEquals("[{\"telephoneType\":\"home\",\"TelephoneNumber\":\"566-76-98\",\"numberOld\":false,\"money\":222}," +
                              "{\"telephoneType\":\"mobil\",\"TelephoneNumber\":\"234-55-65\",\"numberOld\":true,\"money\":1111}]"
                               ,model.toString());
    }

    @Test
    public void shouldSerializeObjectWithNullValueOfFields(){
        ObjectWithNullValueOfFields testObject = new ObjectWithNullValueOfFields();
        JsonValue model = strategyFactory.createJsonObject(testObject);
        assertEquals("{\"aInt\":0,\"aFloat\":0.0}", model.toString());
    }

    @Test
    public void shouldSelectIterableStrategyAndSerializeIterableInJsonString(){
        Collection<Integer> collection = new ArrayList<>();
        JsonValue model = strategyFactory.createJsonObject(collection);
        assertEquals("[]", model.toString());

        Collection<Integer> collection1 = new ArrayDeque<>();
        collection1.add(12);
        collection1.add(13);
        collection1.add(14);
        model = strategyFactory.createJsonObject(collection1);
        assertEquals("[12,13,14]", model.toString());

        Collection<String> collection2 = new LinkedList<>();
        collection2.add("aaa");
        collection2.add("bbb");
        collection2.add("ccc");
        model = strategyFactory.createJsonObject(collection2);
        assertEquals("[\"aaa\",\"bbb\",\"ccc\"]", model.toString());
    }

    @Test
    public void shouldSelectMapStrategyAndSerializeMapInJsonString(){
        Map<String,Integer> map = new HashMap<>();
        JsonValue model = strategyFactory.createJsonObject(map);
        assertEquals("{}", model.toString());

        Map<String,Boolean> map1 = new Hashtable<>();
        map1.put("one", true);
        map1.put("two", false);
        map1.put("three", true);

        model = strategyFactory.createJsonObject(map1);
        assertEquals("{\"two\":false,\"one\":true,\"three\":true}", model.toString());
    }
}