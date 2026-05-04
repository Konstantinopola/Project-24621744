package bg.tu_varna.sit.f24621744.task.jsonWork;

import java.util.ArrayList;
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



}