package bg.tu_varna.sit.f24621744.task.jsonWork;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a JSON object—an ordered set of key-value pairs.
 * <p>
 * Corresponds to the {@code { "key": value, ... }} construct in JSON format.
 * The order of fields is preserved using {@link LinkedHashMap}.
 * </p>
 * <p>
 * Example JSON object:
 * <pre>{@code
 * {
 * "name": "Alice",
 * "age": 30,
 * "active": true
 * }
 * }</pre>
 * </p>
 */
public class JsonObject implements JsonType {

    /** Object property storage: key → value. Insertion order is preserved. */
    private final Map<String, JsonType> properties = new LinkedHashMap<>();

    /**
     * Serializes an object into a compact, single-line JSON string.
     * <p>
     * Example: {@code {"name": "Alice", "age": 30}}
     * </p>
     *
     * @return the object's compact JSON string
     */
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

    /**
     * Serializes an object into a readable (pretty-printed) JSON string with indentation.
     * <p>
     * An empty object is serialized as {@code {}}. A non-empty object is serialized with line breaks
     * and indentation proportional to the nesting level.
     * </p>
     *
     * @param indent is the current nesting level (each level = 2 spaces)
     * @return the formatted JSON string of the object
     */
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

    /**
     * Helper method for replacing an existing child element with a new value.
     *
     * @param key is the unique key of the field
     * @param newValue is the new tree node
     */
    public void replaceChild(String key, JsonType newValue) {
        if (properties.containsKey(key)) {
            properties.put(key, newValue); // only if key exist
        }
    }

    /**
     * This operation is not supported for JSON objects.
     * <p>
     * JSON objects require a key for each value.
     * To add, use {@link #addChild(String, JsonType)}.
     * </p>
     *
     * @param value is the value to add (but not used)
     * @throws UnsupportedOperationException always, since objects do not support adding without a key.
     */
    @Override
    public boolean append(JsonType value) {
        throw new UnsupportedOperationException("Object can`t be added without key!");
        // no objects without key
    }

    /**
     * Adds or overwrites an object property with the specified key.
     * <p>
     * If a property with the same key already exists, its value will be replaced.
     * </p>
     *
     * @param key String key of the new or updated property
     * @param value JSON value to associate with the key
     */
    @Override
    public void addChild(String key, JsonType value) {
        properties.put(key, value); // create or override
    }

    /**
     * Implementation of intelligent recursive path creation.
     * <p>
     * If there is no intermediate node, the method analyzes the next token
     * of the path (index + 1). If the next token is numeric, an instance of {@link JsonArray} is automatically created; otherwise, an instance of {@link JsonObject} is created. This prevents
     * accidentally overwriting arrays with objects when branching the structure.
     * </p>
     */
    @Override
    public boolean createPath(String[] path, int index, JsonType newValue) {
        if (path == null || index >= path.length) {
            return false;
        }

        String key = path[index];

        // 1. Base case: reached the end of the path
        if (index == path.length - 1) {
            properties.put(key, newValue);
            return true;
        }

        // 2. Recursive case: going deeper
        JsonType child = properties.get(key);

        // If there is no node, look at the NEXT step in the path to understand what to create
        if (child == null) {
            if (isNumeric(path[index + 1])) {
                child = new JsonArray(); // The next key is an index, an array is needed
            } else {
                child = new JsonObject(); // The next key is a string, an object is needed
            }
            properties.put(key, child);
        }

        return child.createPath(path, index + 1, newValue);
    }

    /**
     * Checks whether a string consists exclusively of digits, i.e., whether it is an index.
     *
     * @param str is the path string to check.
     * @return true if the string converts to a valid integer; otherwise, false.
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
        JsonType child = properties.get(path[index]);
        if (child == null) return null;
        return child.getByPath(path, index + 1);
    }

    @Override
    public boolean setByPath(String[] path, int index, JsonType newValue) {
        if (path == null || index >= path.length) return false;
        String currentKey = path[index];

        // If this is the last key in the path, we replace it (only if the key exists)
        if (index == path.length - 1) {
            replaceChild(currentKey, newValue);
        }
        // Going down deeper into the tree if not
        JsonType child = properties.get(currentKey);
        if (child == null) return false;
        return child.setByPath(path, index + 1, newValue);
    }

    @Override
    public boolean deleteByPath(String[] path, int index) {
        if (path == null || index >= path.length) return false;
        String currentKey = path[index];

        if (index == path.length - 1) {
            return properties.remove(currentKey) != null;
        }

        JsonType child = properties.get(currentKey);
        if (child == null) return false;
        return child.deleteByPath(path, index + 1);
    }

    @Override
    public void searchByKey(String key, List<JsonType> results) {
        if (properties.containsKey(key)) {
            results.add(properties.get(key));
        }
        for (JsonType child : properties.values()) {
            child.searchByKey(key, results);
        }
    }


}