package com.it_uatech.json_app.strategy;

import javax.json.Json;
import javax.json.JsonValue;

class CharacterStrategy implements AcceptStrategy {

    @Override
    public boolean strategyAccepted(Object someCharacter) {
        return someCharacter instanceof Character;
    }

    @Override
    public JsonValue createJsonObject(Object someCharacter) {
        JsonValue jsonValueBuilder = Json.createObjectBuilder().add("Character", ((Character) someCharacter).toString()).build().getJsonString("Character");
        return jsonValueBuilder;
    }
}
