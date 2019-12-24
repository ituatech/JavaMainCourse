package com.it_uatech.json_app;

import com.it_uatech.json_app.strategy.StrategyFactory;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import java.io.StringWriter;

public class JsonApp {
    private StrategyFactory strategyFactory;

    public JsonApp() {
        this.strategyFactory = new StrategyFactory();
    }

    public String writeJson(Object someObject) {
        if (someObject != null) {
            return writeToString((JsonObject) strategyFactory.createJsonObject(someObject));
        }
        return null;
    }

    String writeToString(JsonObject inputObject) {
        StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
            jsonWriter.write(inputObject);
        }
        return stringWriter.toString();
    }
}
