package com.it_uatech.json_app.strategy;

import javax.json.Json;
import javax.json.JsonValue;

class StringStrategy implements AcceptStrategy{

    @Override
    public boolean strategyAccepted(Object str) {
        return str instanceof String;
    }

    @Override
    public JsonValue createJsonObject(Object str) {
        JsonValue jsonValueBuilder = Json.createObjectBuilder().add("String", (String) str).build().getJsonString("String");
        return jsonValueBuilder;
    }
}
