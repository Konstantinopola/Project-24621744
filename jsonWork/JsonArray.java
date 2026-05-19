package bg.tu_varna.sit.f24621744.task.jsonWork;

import bg.tu_varna.sit.f24621744.task.Exception.JsonNavigationException;
import bg.tu_varna.sit.f24621744.task.Exception.JsonTypeException;

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
        if (value == null) {
            throw new JsonTypeException("Cannot append a null instance object directly to JsonArray.");
        }
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
        throw new JsonTypeException("Arrays do not support keys");
    }

    /**
     * Implements path creation and automatic array expansion.
     * <p>
     * <b>Algorithm:</b>
     * The path token {@code path[index]} is parsed into a numeric index of an array cell. If an invalid
     * key is received, execution is aborted. If the index exceeds the current array size, or the cell is empty,
     * the look-ahead mechanism is activated on the element {@code path[index + 1]}.
     * Based on this analysis, either an {@link JsonArray} or {@link JsonObject} is created and inserted into the array.
     * At the final step of the path, the value {@code newValue} is placed into the array at the specified index.
     * </p>
     *
     * @param path is an array of string components of the path (indices and keys).
     * @param index is the current path processing depth. * @param newValue is the new JSON value to insert.
     * @return {@code true} if the recursive construction and insertion operation completed successfully; otherwise {@code false}.
     */
    public boolean createPath(String[] path, int index, JsonType newValue) {
        if (path == null || index >= path.length) {
            throw new JsonNavigationException("Incomplete arguments passed to array branch creator.");
        }

        try {
            int idx = Integer.parseInt(path[index]);
            if (idx < 0) {
                throw new JsonNavigationException("JSON arrays cannot allocate negative cell pointers: " + idx);
            }

            if (index == path.length - 1) {
                if (idx >= elements.size()) {
                    elements.add(newValue); // Adding to the end
                } else {
                    elements.add(idx, newValue); // Insert with offset
                }
                return true;
            }

            JsonType child;
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
            throw new JsonTypeException("Expected an array index digit but received text symbol key: '" + path[index] + "'.");
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

    /**
     * Performs a recursive search and extracts a JSON node by its index within an array.
     * <p>
     * <b>Algorithm:</b>
     * The method parses the current string path token {@code path[index]} into an integer. If the token is not
     * a number, or the index is beyond the current array size (negative or too large),
     * {@code null} is returned. If this is the final step of the path, the array element is returned.
     * Otherwise, the recursive call goes deeper into the found element.
     * </p>
     *
     * @param path is an array of string representations of indices/keys.
     * @param index is the current position in the path being processed.
     * @return the found element at the specified index, or {@code null} if the index or data type is invalid.
     */
    @Override
    public JsonType getByPath(String[] path, int index) {
        if (path == null || index >= path.length) {
            return this;
        }
        try {
            int idx = Integer.parseInt(path[index]);
            if (idx < 0 || idx >= elements.size()) {
                throw new JsonNavigationException("Requested target cell index " + idx + " is invalid for current array size of " + elements.size());
            }

            return elements.get(idx).getByPath(path, index + 1);
        } catch (NumberFormatException e) {
            throw new JsonTypeException("Direct string property access '" + path[index] + "' is invalid for arrays.");
        }
    }

    /**
     * Finds an existing array cell at the specified path and replaces its value.
     * <p>
     * <b>Algorithm:</b>
     * The path token is converted to a numeric cell index. The index is checked to be valid for the current
     * list size. If this is the final destination, the {@link List#set(int, Object)} method is called,
     * replacing the old object with {@code newValue}. Otherwise, the request is passed further down the chain.
     * </p>
     *
     * @param path is an array of paths, where the current token is expected as a valid index.
     * @param index is the current path processing depth.
     * @param newValue is the new value to overwrite the cell.
     * @return {@code true} if the element at the specified numeric index existed and was modified;
     * @throws JsonNavigationException if the path does not exist
     * @throws JsonTypeException       if the path element is incompatible
     */
    @Override
    public boolean setByPath(String[] path, int index, JsonType newValue) {
        if (path == null || index >= path.length) {
            throw new JsonNavigationException("Array token routing missing execution configuration.");
        }
        try {
            int idx = Integer.parseInt(path[index]);
            if (idx < 0 || idx >= elements.size()) {
                throw new JsonNavigationException("Target array index " + idx + " does not map to any element allocation.");
            }

            if (index == path.length - 1) {
                elements.set(idx, newValue);
                return true;
            }
            return elements.get(idx).setByPath(path, index + 1, newValue);
        } catch (NumberFormatException e) {
            throw new JsonTypeException("Cannot look up string key path '" + path[index] + "' inside an array.");
        }
    }

    /**
     * Removes the element at the specified numeric index from the array.
     * <p>
     * <b>Algorithm:</b>
     * Converts a string step in the path to a number. If this is the last step, the element is removed from the list
     * using {@link List#remove(int)}. All subsequent elements in the array are automatically
     * shifted left, reducing the list size by one. If the path goes deeper, the task
     * is delegated to a child element of the cell.
     * </p>
     *
     * @param path is the path containing the index of the element to remove at the current position.
     * @param index is the current recursion step.
     * @return {@code true} if the element was successfully removed from the list;
     * {@code false} if the index does not exist or an invalid text key was passed instead of the index.
     */
    @Override
    public boolean deleteByPath(String[] path, int index) {
        if (path == null || index >= path.length) {
            throw new JsonNavigationException("Missing navigation array references for deletion.");
        }
        try {
            int idx = Integer.parseInt(path[index]);
            if (idx < 0 || idx >= elements.size()) {
                throw new JsonNavigationException("Extraction index " + idx + " is unavailable for deletion tracking.");
            }

            if (index == path.length - 1) {
                elements.remove(idx);
                return true;
            }
            return elements.get(idx).deleteByPath(path, index + 1);
        } catch (NumberFormatException e) {
            throw new JsonTypeException("Cannot process removal string key '" + path[index] + "' from sequence collection.");
        }
    }

    /**
     * Performs a deep, forward-looking search for a key in all nested structures within the current array's elements.
     * <p>
     * <b>Algorithm:</b>
     * Since the array itself contains only ordinal indices and does not store string field keys,
     * it cannot directly return a match. Instead, the method sequentially loops through
     * each element of the {@code elements} array and redirects the {@code searchByKey} search command within them,
     * allowing objects within the array to find and store matches.
     * </p>
     *
     * @param key is the string key to search for.
     * @param results is a collection list where all found objects will be added.
     */
    @Override
    public void searchByKey(String key, List<JsonType> results) {
        for (JsonType child : elements) {
            child.searchByKey(key, results);
        }
    }

}