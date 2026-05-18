package bg.tu_varna.sit.f24621744.task.jsonWork.primitiveType;

import bg.tu_varna.sit.f24621744.task.jsonWork.JsonPrimitive;

/**
 * Represents a primitive JSON value of type <b>string</b>.
 * <p>
 * Stores a string value and serializes it to JSON format.
 * </p>
 * <p>
 * Example JSON: {@code "Hello, World!"}
 * </p>
 */
public class JsonPrString extends JsonPrimitive {

    /** The string value of this JSON primitive. */
    private final String value;

    /**
     * Creates a new instance with the given string value.
     *
     * @param value string value (must not be {@code null})
     */
    public JsonPrString(String value) {
        this.value = value;
    }

    /**
     * Serializes a string to JSON format, enclosing it in double quotes.
     * <p>
     * Example: value {@code Hello} → {@code "Hello"}
     * </p>
     *
     * @return a string in the format {@code "value"}
     */
    @Override
    public String toJsonString() {
        return "\"" + value + "\"";
    }

}
