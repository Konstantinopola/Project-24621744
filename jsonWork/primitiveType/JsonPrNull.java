package bg.tu_varna.sit.f24621744.task.jsonWork.primitiveType;

import bg.tu_varna.sit.f24621744.task.jsonWork.JsonPrimitive;

/**
 * Represents the primitive JSON value {@code null}.
 * <p>
 * Example JSON: {@code null}
 * </p>
 */
public class JsonPrNull extends JsonPrimitive {

    /**
     * Serializes the value {@code null} to JSON format.
     *
     * @return the string {@code "null"} without quotes, according to the JSON specification
     */
    @Override
    public String toJsonString() {
        return "null";
    }

}
