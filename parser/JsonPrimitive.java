package bg.tu_varna.sit.f24621744.task.parser;

public class JsonPrimitive implements JsonToString {
    private final Object value;

    public JsonPrimitive(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toJsonString() {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return "\"" + value + "\"";
        }

        return value.toString();
    }
}