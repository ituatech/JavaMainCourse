package com.it_uatech.json_app.strategy;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;
import java.lang.reflect.Array;

class ArrayStrategy implements AcceptStrategy{

    private StrategyFactory strategyFactory;

    ArrayStrategy(StrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    @Override
    public boolean strategyAccepted(Object someObject) {
        return someObject.getClass().isArray();
    }

    @Override
    public JsonValue createJsonObject(Object someArray) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (int i = 0; i < Array.getLength(someArray); i++ ){
            jsonArrayBuilder.add(strategyFactory.createJsonObject(Array.get(someArray,i)));
        }
        return jsonArrayBuilder.build();
    }
}
