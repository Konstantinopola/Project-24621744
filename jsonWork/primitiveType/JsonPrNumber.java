package bg.tu_varna.sit.f24621744.task.jsonWork.primitiveType;

import bg.tu_varna.sit.f24621744.task.jsonWork.JsonPrimitive;

public class JsonPrNumber  extends JsonPrimitive {
    private final Number value;

    public JsonPrNumber(Number value) {
        this.value = value;
    }

    @Override
    public String toJsonString() {
        return value.toString();
    }

}
