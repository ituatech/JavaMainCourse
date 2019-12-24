package com.it_uatech.json_app.strategy;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.util.Map;
import java.util.Set;

class MapStrategy implements AcceptStrategy {

    private StrategyFactory strategyFactory;

    MapStrategy(StrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    @Override
    public boolean strategyAccepted(Object someMap) {
        return someMap instanceof Map;
    }

    @Override
    public JsonValue createJsonObject(Object someMap) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        Map map = (Map) someMap;
        Set keySet = map.keySet();
        for (Object obj : keySet) {
            jsonObjectBuilder.add(obj.toString(), strategyFactory.createJsonObject(map.get(obj)));
        }
        return jsonObjectBuilder.build();
    }
}
