package com.it_uatech.json_app.strategy;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.math.BigDecimal;
import java.math.BigInteger;

class NumberStrategy implements AcceptStrategy{

    @Override
    public boolean strategyAccepted(Object number) {
        return number instanceof Number;
    }

    @Override
    public JsonValue createJsonObject(Object number) {

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

        if (number instanceof BigInteger) {
            return jsonObjectBuilder.add("Number", (BigInteger) number).build().get("Number");
        }
        if (number instanceof Integer) {
            return jsonObjectBuilder.add("Number", (int) number).build().get("Number");
        }
        if (number instanceof Long) {
            return jsonObjectBuilder.add("Number", (long) number).build().get("Number");
        }
        if (number instanceof Double) {
            return jsonObjectBuilder.add("Number", (double) number).build().get("Number");
        }
        if (number instanceof Float) {
            return jsonObjectBuilder.add("Number", (float) number).build().get("Number");
        }
        if (number instanceof BigDecimal) {
            return jsonObjectBuilder.add("Number", (BigDecimal) number).build().get("Number");
        }
        if (number instanceof Byte) {
            return jsonObjectBuilder.add("Number", (byte) number).build().get("Number");
        }

        throw new RuntimeException("unexpected number");
    }
}
