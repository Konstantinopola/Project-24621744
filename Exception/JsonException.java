package bg.tu_varna.sit.f24621744.task.Exception;


import bg.tu_varna.sit.f24621744.task.jsonWork.JsonType;

/**
 * An exception that describes any internal logical or syntax errors
 * when working with a hierarchical JSON tree.
 * <p>
 * Inherits from {@link RuntimeException} to integrate with existing method
 * of the {@link JsonType} interface.
 * </p>
 */
public class JsonException extends RuntimeException {

    /**
     * Creates a new JSON exception with a detailed text description of the cause of the failure.
     *
     * @param message - a detailed error message
     */
    public JsonException(String message) {
        super(message);
    }
}