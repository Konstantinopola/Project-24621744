package bg.tu_varna.sit.f24621744.task.jsonWork;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JsonArray implements JsonType {
    private final List<JsonType> elements = new ArrayList<>();

    public void add(JsonType value) {
        elements.add(value);
    }

    public List<JsonType> getElements() {
        return elements;
    }

    @Override
    public String toJsonString() {
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < elements.size(); i++) {
            sb.append(elements.get(i).toJsonString());

            if (i < elements.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public String toPrettyString(int indent) {
        if (elements.isEmpty()) return "[]";

        String space = "  ".repeat(indent);
        String innerSpace = "  ".repeat(indent + 1);

        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        for (int i = 0; i < elements.size(); i++) {
            sb.append(innerSpace).append(elements.get(i).toPrettyString(indent + 1));

            if (i < elements.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }

        sb.append(space).append("]");
        return sb.toString();
    }

    @Override
    public JsonType getChild(String token) {
        try {
            int index = Integer.parseInt(token);
            if (index >= 0 && index < elements.size()) {
                return elements.get(index);
            }
        } catch (NumberFormatException ignored) {}
        return null;
    }

    @Override
    public boolean removeChild(String token) {
        try {
            int index = Integer.parseInt(token);
            if (index >= 0 && index < elements.size()) {
                elements.remove(index);
                return true;
            }
        } catch (NumberFormatException ignored) {}
        return false;
    }

    @Override
    public boolean replaceChild(String key, JsonType newValue) {
        try {
            int index = Integer.parseInt(key);
            if (index >= 0 && index < elements.size()) {
                elements.set(index, newValue); // changing element on index
                return true;
            }
        } catch (NumberFormatException ignored) {}
        return false;
    }

    @Override
    public Collection<JsonType> getValues() {
        return elements; // all elements
    }

    @Override
    public boolean append(JsonType value) {
        elements.add(value); // array will add element to the end
        return true;
    }

    @Override
    public void addChild(String key, JsonType value) {
        throw new UnsupportedOperationException("Arrays do not support keys"); // key in Array is not allowed!
    }


}