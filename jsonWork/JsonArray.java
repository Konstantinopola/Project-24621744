package bg.tu_varna.sit.f24621744.task.jsonWork;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a JSON array—an ordered list of JSON values.
 * <p>
 * Corresponds to the {@code [ value1, value2, ... ]} construct in JSON format.
 * Elements are indexed starting with {@code 0}. Internally, they are stored in {@link ArrayList},
 * which ensures that the order of the elements is preserved.
 * </p>
 * <p>
 * Example of a JSON array:
 * <pre>{@code
 * [1, "hello", true, null, {"key" : "value"}]
 * }</pre>
 * </p>
 */
public class JsonArray implements JsonType {

    /** Internal list of array elements. */
    private final List<JsonType> elements = new ArrayList<>();

    /**
     * Adds a new element to the end of the array.
     *
     * @param value the JSON element to add
     */
    public void add(JsonType value) {
        elements.add(value);
    }

    /**
     * Serializes an array into a compact, single-line JSON string.
     * <p>
     * Example: {@code [1, "hello", true]}
     * </p>
     *
     * @return the compact JSON string of the array
     */
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

    /**
     * Serializes an array into a readable (pretty-printed) JSON string with indentation.
     * <p>
     * An empty array is serialized as {@code []}. A non-empty array is serialized with line breaks
     * and indentation proportional to the nesting level.
     * </p>
     *
     * @param indent is the current nesting level (each level = 2 spaces)
     * @return the formatted JSON string of the array
     */
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

    /**
     * Adds a new element to the end of the array.
     *
     * @param value is the JSON element to add.
     * @return is always {@code true}, since the addition is always successful.
     */
    @Override
    public boolean append(JsonType value) {
        elements.add(value);
        return true;
    }

    /**
     * This operation is not supported for JSON arrays.
     * <p>
     * Array elements do not have string keys—only numeric indices.
     * To add an element, use {@link #add(JsonType)} or {@link #append(JsonType)}.
     * </p>
     *
     * @param key is the key (not used)
     * @param value is the value (not used)
     * @throws UnsupportedOperationException always, since arrays do not support keys.
     */
    @Override
    public void addChild(String key, JsonType value) {
        System.out.println("Arrays do not support keys");
    }

    /**
     * Implementation of intelligent recursive path creation within an array.
     * <p>
     * Extracts the index from the current path token. If the index exceeds the array size,
     * the element is automatically initialized based on a lookahead for the next step in the path.
     * (creates a {@link JsonArray} for numbers or a {@link JsonObject} for string keys).
     * </p>
     */
    public boolean createPath(String[] path, int index, JsonType newValue) {
        try {
            int idx = Integer.parseInt(path[index]);
            if (idx < 0) return false;

            if (index == path.length - 1) {
                if (idx >= elements.size()) {
                    elements.add(newValue); // Adding to the end
                } else {
                    elements.add(idx, newValue); // Insert with offset
                }
                return true;
            } JsonType child;

            if (idx >= elements.size()) {
                // Looking ahead: what needs to be created inside the array?
                if (isNumeric(path[index + 1])) {
                    child = new JsonArray();
                } else {
                    child = new JsonObject();
                }
                // Add the created intermediate node to the array
                elements.add(child);
            } else {
                // If the knot is already there, just take it
                child = elements.get(idx);
            }

            // We forward the call further into the created or found node
            return child.createPath(path, index + 1, newValue);

        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks whether the next path token is a numeric array index.
     *
     * @param str string to validate
     * @return true if it is a number, otherwise false
     */
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public JsonType getByPath(String[] path, int index) {
        if (path == null || index >= path.length) {
            return this;
        }
        try {
            int idx = Integer.parseInt(path[index]);
            if (idx < 0 || idx >= elements.size()) return null;

            return elements.get(idx).getByPath(path, index + 1);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public boolean setByPath(String[] path, int index, JsonType newValue) {
        if (path == null || index >= path.length) return false;
        try {
            int idx = Integer.parseInt(path[index]);
            if (idx < 0 || idx >= elements.size()) return false;

            if (index == path.length - 1) {
                elements.set(idx, newValue);
                return true;
            }
            return elements.get(idx).setByPath(path, index + 1, newValue);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean deleteByPath(String[] path, int index) {
        if (path == null || index >= path.length) return false;
        try {
            int idx = Integer.parseInt(path[index]);
            if (idx < 0 || idx >= elements.size()) return false;

            if (index == path.length - 1) {
                elements.remove(idx);
                return true;
            }
            return elements.get(idx).deleteByPath(path, index + 1);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void searchByKey(String key, List<JsonType> results) {
        for (JsonType child : elements) {
            child.searchByKey(key, results);
        }
    }

}