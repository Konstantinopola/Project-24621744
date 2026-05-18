package bg.tu_varna.sit.f24621744.task.jsonWork.primitiveType;

import bg.tu_varna.sit.f24621744.task.jsonWork.JsonPrimitive;

/**
 * Represents a primitive JSON value of type <b>boolean</b> ({@code true} / {@code false}).
 * <p>
 * JSON examples: {@code true}, {@code false}
 * </p>
 */
public class JsonPrBoolean extends JsonPrimitive {

    /** The Boolean value of this JSON primitive. */
    private final boolean value;

    /**
     * Creates a new instance with the specified Boolean value.
     *
     * @param value is a Boolean value ({@code true} or {@code false})
     */
    public JsonPrBoolean(boolean value) {
        this.value = value;
    }

    /**
     * Serializes a Boolean value to JSON format.
     * <p>
     * Returns {@code "true"} or {@code "false"} without quotes,
     * according to the JSON specification.
     * </p>
     *
     * @return the string {@code "true"} or {@code "false"}
     */
    @Override
    public String toJsonString() {
        return String.valueOf(value);
    }

}
