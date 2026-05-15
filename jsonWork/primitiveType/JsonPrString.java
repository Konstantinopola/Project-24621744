package bg.tu_varna.sit.f24621744.task.jsonWork.primitiveType;

import bg.tu_varna.sit.f24621744.task.jsonWork.JsonPrimitive;

public class JsonPrString extends JsonPrimitive {
    private final String value;

    public JsonPrString(String value) {
        this.value = value;
    }

    @Override
    public String toJsonString() {
        return "\"" + value + "\"";
    }

}
