package com.it_uatech.json_app.strategy;

import javax.json.Json;
import javax.json.JsonValue;

class BooleanStrategy implements AcceptStrategy {

    @Override
    public boolean strategyAccepted(Object aBoolean) {
        return aBoolean instanceof Boolean;
    }

    @Override
    public JsonValue createJsonObject(Object aBoolean) {
        JsonValue jsonValueBuilder = Json.createObjectBuilder().add("Boolean", (Boolean) aBoolean).build().get("Boolean");
        return jsonValueBuilder;
    }
}
