package bg.tu_varna.sit.f24621744.task.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonArray implements JsonToString {
    private final List<JsonToString> elements = new ArrayList<>();

    public void add(JsonToString value) {
        elements.add(value);
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


}