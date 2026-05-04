package bg.tu_varna.sit.f24621744.task.jsonWork;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonObject implements JsonType {
    private final Map<String, JsonType> properties = new LinkedHashMap<>();

    public void add(String key, JsonType value) {
        properties.put(key, value);
    }

    public JsonType getValue(String key) {
        return properties.get(key);
    }

    public Map<String, JsonType> getProperties() {
        return properties;
    }

    public void remove(String key) {
        properties.remove(key);
    }

    @Override
    public String toJsonString() {
        StringBuilder sb = new StringBuilder("{");
        int count = 0;

        for (Map.Entry<String, JsonType> entry : properties.entrySet()) {
            sb.append("\"").append(entry.getKey()).append("\": ").append(entry.getValue().toJsonString());

            if (count < properties.size() - 1) {
                sb.append(", ");
            }
            count++;
        }

        sb.append("}");
        return sb.toString();
    }

    @Override
    public String toPrettyString(int indent) {
        if (properties.isEmpty()) return "{}";

        String space = "  ".repeat(indent);
        String innerSpace = "  ".repeat(indent + 1);

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        int count = 0;
        for (Map.Entry<String, JsonType> entry : properties.entrySet()) {
            sb.append(innerSpace)
                    .append("\"").append(entry.getKey()).append("\": ")
                    .append(entry.getValue().toPrettyString(indent + 1));

            if (++count < properties.size()) {
                sb.append(",");
            }
            sb.append("\n");
        }

        sb.append(space).append("}");
        return sb.toString();
    }
}