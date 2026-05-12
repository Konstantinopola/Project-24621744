package bg.tu_varna.sit.f24621744.task.jsonWork;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonObject implements JsonType {
    private final Map<String, JsonType> properties = new LinkedHashMap<>();

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
    
    @Override
    public JsonType getChild(String token) {
        return properties.get(token);
    }

    @Override
    public boolean removeChild(String token) {
        if (properties.containsKey(token)) {
            properties.remove(token);
            return true;
        }
        return false;
    }

    @Override
    public boolean replaceChild(String key, JsonType newValue) {
        if (properties.containsKey(key)) {
            properties.put(key, newValue); // only if key exist
            return true;
        }
        return false;
    }

    @Override
    public Collection<JsonType> getValues() {
        return properties.values();// sent all element and their value
    }

    @Override
    public boolean append(JsonType value) {
        throw new UnsupportedOperationException("Object can`t be added without key!");
        // no objects without key
    }

    @Override
    public void addChild(String key, JsonType value) {
        properties.put(key, value); // create or override
    }
}