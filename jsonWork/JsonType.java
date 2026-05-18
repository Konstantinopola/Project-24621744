package bg.tu_varna.sit.f24621744.task.jsonWork;

import java.util.List;

/**
 * The base interface for all JSON tree nodes.
 * <p>
 * Implements the Composite design pattern. Allows for consistent
 * treatment of both individual atomic values (primitives) and composite data structures
 * (objects and arrays).
 * </p>
 * <p>
 * Tree navigation and modification are accomplished by recursively traversing an array of strings,
 * representing the path to the target element, without the need for third-party libraries.
 * </p>
 */
public interface JsonType {

    /**
     * Serializes this node into a compact JSON string.
     * <p>
     * Example for an object: {@code {"key": "value", "num": 42}}
     * </p>
     *
     * @return a JSON string representation without extra spaces and line breaks
     */
    String toJsonString();

    /**
     * Serializes the given node into a readable (pretty-printed) JSON string
     * with the specified indentation level.
     *
     * @param indent is the current nesting level; each level adds two spaces
     * @return a formatted JSON string with indentations and line breaks
     */
    String toPrettyString(int indent);


    /**
     * Appends a child element to the end of the current node.
     * Only supported for collections (arrays).
     *
     * @param value : The JSON component to add.
     * @return true if the addition was successful; otherwise, false.
     */
    boolean append(JsonType value);

    /**
     * Adds or overwrites a property with a unique text key.
     * Supported only for objects.
     *
     * @param key is the string key of the property
     * @param value is the JSON node to match
     */
    void addChild(String key, JsonType value);

    /**
     * Recursively traverses the specified path and creates all missing intermediate structures,
     * placing the passed value at the final point of the path.
     * <p>
     * The algorithm analyzes subsequent path elements to automatically determine the type of
     * the container being created (object or array).
     * </p>
     *
     * @param path : an array of string tokens representing the full path to the target element
     * @param index : the current index being processed in the path array
     * @param newValue : the JSON value to be captured at the end of the path
     * @return true if the fork and create operation completed successfully, otherwise false
     */
    boolean createPath(String[] path, int index, JsonType newValue);

    /**
     * Retrieves the JSON node located at the given path.
     * Traversal succeeds if all keys or indices are sequentially found in the tree.
     *
     * @param path is an array of strings describing the route to the target element.
     * @param index is the current index of the recursion step.
     * @return the found node of type {@link JsonType}, or null if the path does not exist.
     */
    JsonType getByPath(String[] path, int index);

    /**
     * Updates the value of an existing element at the specified path.
     * The method only works on existing tree nodes and does not create new structures.
     *
     * @param path is an array of keys/indices leading to the element to be modified.
     * @param index is the current position of the pointer in the path array.
     * @param newValue is the new JSON value to substitute.
     * @return true if the element was successfully found and updated; otherwise, false.
     */
    boolean setByPath(String[] path, int index, JsonType newValue);

    /**
     * Recursively deletes the tree node located at the endpoint of the specified path.
     * When deleting an element from an object, the key is completely erased; when deleting an element from an array,
     * subsequent elements are shifted left.
     *
     * @param path : An array of strings forming the path to the object being deleted.
     * @param index : The current recursive level.
     * @return true if the element existed and was deleted; otherwise, false.
     */
    boolean deleteByPath(String[] path, int index);

    /**
     * Performs a deep, end-to-end search of all values for the given key.
     * Scans the current node and all nested data structures, accumulating matches into a list.
     *
     * @param key is the string property key to search for
     * @param results is the list to which all found matches are added
     */
    void searchByKey(String key, List<JsonType> results);

}