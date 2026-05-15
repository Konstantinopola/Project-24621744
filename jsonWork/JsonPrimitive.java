package bg.tu_varna.sit.f24621744.task.jsonWork;

import java.util.Collection;
import java.util.Collections;

public abstract class JsonPrimitive implements JsonType {

    @Override
    public abstract String toJsonString();

    @Override
    public String toPrettyString(int indent) {
        return toJsonString();
    }

    @Override
    public JsonType getChild(String token) {
        return null;
    } // primitive - no kids

    @Override
    public boolean removeChild(String token) {
        System.err.println("Error: Primitive don`t have a child to remove!");
        return false;
    } // primitive - no kids

    @Override
    public boolean replaceChild(String key, JsonType newValue) { // primitive - no kids
        System.err.println("Error: Primitive don`t have a child to replace!");
        return false;
    }

    @Override
    public Collection<JsonType> getValues() {
        return Collections.emptyList(); // send empty list (primitive = no kids)
    }

    @Override
    public boolean append(JsonType value) { // primitive - no objects inside
        System.err.println("Error: Primitive don`t have a child to append!");
        return false;
    }

    @Override
    public void addChild(String key, JsonType value) {
        System.err.println("Error: Primitive don`t have a child to add key " + key + "!");
    } //the same
}

