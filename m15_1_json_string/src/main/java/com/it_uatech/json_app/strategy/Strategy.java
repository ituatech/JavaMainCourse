package com.it_uatech.json_app.strategy;

import javax.json.JsonValue;

public interface Strategy {
    public JsonValue createJsonObject(Object someObject);
}
