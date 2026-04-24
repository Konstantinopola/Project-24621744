package bg.tu_varna.sit.f24621744.task.parser;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonObject implements JsonToString {
    private final Map<String, JsonToString> properties = new LinkedHashMap<>();

    public void add(String key, JsonToString value) {
        properties.put(key, value);
    }

    public JsonToString getValue(String key) {
        return properties.get(key);
    }

    @Override
    public String toJsonString() {
        StringBuilder sb = new StringBuilder("{");
        int count = 0;

        for (Map.Entry<String, JsonToString> entry : properties.entrySet()) {
            sb.append("\"").append(entry.getKey()).append("\": ").append(entry.getValue().toJsonString());

            if (count < properties.size() - 1) {
                sb.append(", ");
            }
            count++;
        }

        sb.append("}");
        return sb.toString();
    }
}