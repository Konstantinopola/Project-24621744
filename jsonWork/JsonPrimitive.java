package bg.tu_varna.sit.f24621744.task.jsonWork;

public class JsonPrimitive implements JsonType {
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

    @Override
    public String toPrettyString(int indent) {
        return toJsonString();
    }
}