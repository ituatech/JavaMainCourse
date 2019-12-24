package com.it_uatech.json_app.strategy;


import javax.json.JsonValue;
import java.util.ArrayList;
import java.util.List;

public class StrategyFactory implements Strategy {

    private List<AcceptStrategy> strategyList;
    private ObjectStrategy objectStrategy;

    public StrategyFactory(){
        strategyList = new ArrayList<>();
        strategyList.add(new BooleanStrategy());
        strategyList.add(new NumberStrategy());
        strategyList.add(new StringStrategy());
        strategyList.add(new CharacterStrategy());
        strategyList.add(new ArrayStrategy(this));
        strategyList.add(new IterableStrategy(this));
        strategyList.add(new MapStrategy(this));

        objectStrategy = new ObjectStrategy(this);
    }

    @Override
    public JsonValue createJsonObject(Object fieldObject) {
        JsonValue jsonValue = null;
        for(AcceptStrategy acceptStrategy : strategyList) {
            if (acceptStrategy.strategyAccepted(fieldObject)) {
                jsonValue = acceptStrategy.createJsonObject(fieldObject);
                return jsonValue;
            }
        }
        return objectStrategy.createJsonObject(fieldObject);
    }


}
