package com.it_uatech.json_app.strategy;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;
import java.util.Iterator;

class IterableStrategy implements AcceptStrategy{

    private StrategyFactory strategyFactory;

    IterableStrategy(StrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    @Override
    public boolean strategyAccepted(Object someIterable) {
        return someIterable instanceof Iterable;
    }

    @Override
    public JsonValue createJsonObject(Object someIterable) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        Iterable iterable = (Iterable) someIterable;
        Iterator iterator = iterable.iterator();
        while (iterator.hasNext()){
            jsonArrayBuilder.add(strategyFactory.createJsonObject(iterator.next()));
        }
        return jsonArrayBuilder.build();
    }
}
