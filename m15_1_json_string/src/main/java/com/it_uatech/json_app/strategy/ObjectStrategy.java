package com.it_uatech.json_app.strategy;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class ObjectStrategy implements Strategy{

    private StrategyFactory strategyFactory;

    ObjectStrategy(StrategyFactory strategyFactory){
        this.strategyFactory = strategyFactory;
    }

    @Override
    public JsonValue createJsonObject(Object someObject) {
        Field[] fields = someObject.getClass().getDeclaredFields();
        List<Field> listFields = Arrays.asList(fields).stream().filter(field -> !Modifier.isTransient(field.getModifiers()))
                                                      .collect(Collectors.toList());

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (Field field : listFields){
            boolean accessible = field.canAccess(someObject);
            field.setAccessible(true);
            try {
                Object fieldObject = field.get(someObject);
                if (fieldObject != null){
                    jsonObjectBuilder.add(field.getName(),strategyFactory.createJsonObject(fieldObject));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                field.setAccessible(accessible);
            }
        }
        return jsonObjectBuilder.build();
    }
}
