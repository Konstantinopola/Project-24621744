package bg.tu_varna.sit.f24621744.task.jsonWork;

import java.util.List;

/**
 * Abstract base class for all primitive JSON values.
 * <p>
 * Primitives are leaf nodes of the JSON tree that do not contain
 * child elements. These include:
 * <ul>
 * <li>strings ({@code JsonPrString})</li>
 * <li>numbers ({@code JsonPrNumber})</li>
 * <li>booleans ({@code JsonPrBoolean})</li>
 * <li>null ({@code JsonPrNull})</li>
 * </ul>
 * </p>
 * <p>
 * All methods related to child elements return default values
 * or display an error message, since primitives cannot have children.
 * </p>
 */
public abstract class JsonPrimitive implements JsonType {

    /**
     * Serializes a primitive value into a JSON string.
     * <p>
     * Implemented in each specific subclass:
     * Strings are enclosed in quotes, numbers and Booleans are unquoted,
     * null is serialized as {@code null}.
     * </p>
     *
     * @return JSON representation of the primitive value
     */
    @Override
    public abstract String toJsonString();

    /**
     * Serializes a primitive into a readable JSON string.
     * <p>
     * No formatting is applied to primitives—the result is identical to
     * {@link #toJsonString()}, since primitives do not contain nested structures.
     * </p>
     *
     * @param indent nesting level (ignored for primitives)
     * @return same as {@link #toJsonString()}
     */
    @Override
    public String toPrettyString(int indent) {
        return toJsonString();
    }

    /**
     * Adding an element is not supported for primitives.
     * <p>
     * Prints an error message to {@code System.err} and returns {@code false}.
     * </p>
     *
     * @param value - the element to add (ignored)
     * @return is always {@code false}
     */
    @Override
    public boolean append(JsonType value) {
        System.err.println("Error: Primitive don`t have a child to append!");
        return false;
    }

    /**
     * Adding a child element with a key is not supported for primitives.
     * <p>
     * Outputs an error message to {@code System.err}.
     * </p>
     *
     * @param key - key (ignored)
     * @param value - value to add (ignored)
     */
    @Override
    public void addChild(String key, JsonType value) {
        System.err.println("Error: Primitive don`t have a child to add key " + key + "!");
    }

    /**
     * The primitive is a leaf and cannot contain nested paths.
     * Outputs an error message because the path attempts to go deeper than a terminal node.
     */
    @Override
    public boolean createPath(String[] path, int index, JsonType newValue) {
        System.err.println("Error: Primitives cannot contain a nested child path.");
        return false;
    }

    /**
     * Returns the primitive itself if the path ends exactly at it; otherwise, returns null.
     */
    @Override
    public JsonType getByPath(String[] path, int index) {
        if (path == null || index >= path.length) {
            return this;
        }
        return null;
    }

    /**
     * Модификация вложенных элементов невозможна, так как примитив не имеет дочерней структуры.
     * @return всегда false
     */
    @Override
    public boolean setByPath(String[] path, int index, JsonType newValue) {
        return false;
    }

    /**
     * Deleting nested elements is not possible within a terminal node.
     * @return is always false
     */
    @Override
    public boolean deleteByPath(String[] path, int index) {
        return false;
    }

    /**
     * Primitives do not contain key-value pairs, so the search returns no results.
     * Terminates the recursive scan branch without adding elements to the results.
     */
    @Override
    public void searchByKey(String key, List<JsonType> results) {
        // The primitives don't contain keys, so we don't do anything.
    }


}
