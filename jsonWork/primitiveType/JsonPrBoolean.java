package bg.tu_varna.sit.f24621744.task.jsonWork.primitiveType;

import bg.tu_varna.sit.f24621744.task.jsonWork.JsonPrimitive;

public class JsonPrBoolean extends JsonPrimitive {
    private final boolean value;

    public JsonPrBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public String toJsonString() {
        return String.valueOf(value);
    }

}
