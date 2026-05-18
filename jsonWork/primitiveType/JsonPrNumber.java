package bg.tu_varna.sit.f24621744.task.jsonWork.primitiveType;

import bg.tu_varna.sit.f24621744.task.jsonWork.JsonPrimitive;

/**
 * Represents a primitive JSON value of type <b>number</b>.
 * <p>
 * Stores a numeric value through the {@link Number} base type, allowing
 * to work with integers ({@link Integer}, {@link Long}) and floating-point numbers
 * ({@link Double}, {@link Float}).
 * </p>
 * <p>
 * JSON examples: {@code 42}, {@code 3.14}, {@code -100}
 * </p>
 */
public class JsonPrNumber  extends JsonPrimitive {

    /** The numeric value of this JSON primitive. */
    private final Number value;

    /**
     * Creates a new instance with the specified numeric value.
     *
     * @param value is a numeric value (must not be {@code null});
     * can be {@link Integer}, {@link Double}, {@link Long}, etc.
     */
    public JsonPrNumber(Number value) {
        this.value = value;
    }

    /**
     * Serializes a number to JSON format using the standard string
     * representation of the number.
     * <p>
     * Examples: {@code 42}, {@code 3.14}, {@code -7}
     * </p>
     *
     * @return the JSON string representation of the number without quotes
     */
    @Override
    public String toJsonString() {
        return value.toString();
    }

}
