package bg.tu_varna.sit.f24621744.task.jsonWork;

import bg.tu_varna.sit.f24621744.task.Exception.JsonNavigationException;
import bg.tu_varna.sit.f24621744.task.Exception.JsonTypeException;

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
        throw new JsonTypeException("Object can`t be added without key!");
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
        if (key == null || key.trim().isEmpty()) {
            throw new JsonNavigationException("Property key in JsonObject cannot be null or empty.");
        }
        properties.put(key, value); // create or override
    }

    /**
     * Creates a node structure along the given path.
     * <p>
     * <b>Algorithm:</b>
     * Extracts the key from the current position {@code index}. If this step is final
     * ({@code index == path.length - 1}), the new value {@code newValue} is written directly to the object.
     * If the path continues, the method checks for an intermediate node based on the current key.
     * If there is no intermediate node (equal to {@code null}), the method performs a <b>look-ahead</b>
     * on the token {@code path[index + 1]}. If the next token is a number, an {@link JsonArray} is created,
     * otherwise, an {@link JsonObject}. Control is then recursively transferred to the created or found node.
     * </p>
     *
     * @param path is an array of string tokens specifying the full branching path.
     * @param index is the current index in the path array being processed.
     * @param newValue is the JSON value placed at the endpoint of the path.
     * @return {@code true} if the entire chain was successfully created and the value was committed; otherwise {@code false}.
     */
    @Override
    public boolean createPath(String[] path, int index, JsonType newValue) {
        if (path == null || index >= path.length) {
            throw new JsonNavigationException("Invalid structural path or index bounds in create operation.");
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

    /**
     * Performs a recursive search and extracts a JSON node located at the given path.
     * <p>
     * <b>Algorithm:</b>
     * The method takes a string token from the {@code path} array at the current index {@code index}
     * and searches for it among the keys of this object. If the key is not found, {@code null} is returned.
     * If this was the last element in the paths array, the found node is returned. Otherwise, control is passed down the chain to the child node, incrementing the index by 1.
     * </p>
     *
     * @param path is an array of string tokens representing the full path to the target node.
     * @param index is the current index of the recursion step (points to the key being processed in the paths array).
     * @return the found node of type {@link JsonType}, or {@code null} if the key is missing or the path is interrupted.
     */
    @Override
    public JsonType getByPath(String[] path, int index) {
        if (path == null || index >= path.length) {
            return this;
        }
        JsonType child = properties.get(path[index]);
        if (child == null) {
            throw new JsonNavigationException("Key '" + path[index] + "' does not exist in the current JsonObject.");
        }
        return child.getByPath(path, index + 1);
    }

    /**
     * Recursively finds an existing element along the path and updates its value.
     * <p>
     * <b>Algorithm:</b>
     * The method navigates through the object's keys. If a key is found at the current step and this step
     * is the last in the {@code path} array, the old node is overwritten with a new one, {@code newValue}.
     * If the path continues, the method checks for an intermediate node, and if one exists, passes the call on
     * . The method {@code doesn't} create new tree branches if the path is specified incorrectly.
     * </p>
     *
     * @param path is an array of keys leading to the element to be modified.
     * @param index is the current position of the pointer in the path array.
     * @param newValue is a new JSON value that will replace the old one.
     * @return {@code true} if the element at the specified path existed and was successfully updated;
     * @throws JsonNavigationException if the path does not exist
     * @throws JsonTypeException       if the path element is incompatible
     */
    @Override
    public boolean setByPath(String[] path, int index, JsonType newValue) {
        if (path == null || index >= path.length) {
            throw new JsonNavigationException("null path reference or out-of-bounds index during set operation.");
        }
        String currentKey = path[index];

        // If this is the last key in the path, we replace it (only if the key exists)
        if (index == path.length - 1) {
            replaceChild(currentKey, newValue);
            return true;
        }
        // Going down deeper into the tree if not
        JsonType child = properties.get(currentKey);
        if (child == null) {
            throw new JsonNavigationException("path element '" + currentKey + "' is missing from the object branch.");
        }
        return child.setByPath(path, index + 1, newValue);
    }

    /**
     * Recursively removes the tree node located at the endpoint of the specified path.
     * <p>
     * <b>Algorithm:</b>
     * Searches for the key {@code path[index]} within the current object. If this is the last key
     * in the paths array, the method completely removes the key-value pair from the object's internal map using
     * {@link Map#remove(Object)}. If the path requires deeper exploration, the request is delegated to
     * a child element.
     * </p>
     *
     * @param path is an array of strings that form the exact path to the object to be removed.
     * @param index is the current recursive exploration level.
     * @return {@code true} if the element was found and removed from the structure and
     * {@code false} if the element did not exist.
     */
    @Override
    public boolean deleteByPath(String[] path, int index) {
        if (path == null || index >= path.length) {
            throw new JsonNavigationException("Invalid path block tracking for removal execution.");
        }
        String currentKey = path[index];

        if (index == path.length - 1) {
             properties.remove(currentKey);
             return true;
        }

        JsonType child = properties.get(currentKey);
        if (child == null) {
            throw new JsonNavigationException("Nested branch key '" + currentKey + "' is absent from structure.");
        }
        return child.deleteByPath(path, index + 1);
    }

    /**
     * Performs a deep search for all values with the given key within the current object.
     * <p>
     * <b>Algorithm:</b>
     * First, the method checks whether the current object contains a direct property named {@code key}.
     * If such a property is found, the node associated with it is added to the resulting list {@code results}.
     * After this, the method starts a loop that traverses all nested child structures (objects and arrays)
     * of this object, calling this method recursively on them to find matches at deeper levels.
     * </p>
     *
     * @param key is the string key of the property to search for (e.g., "id" or "name").
     * @param results is a list collection that dynamically accumulates all found matches.
     */
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