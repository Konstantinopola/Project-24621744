package bg.tu_varna.sit.f24621744.task.jsonWork;

public interface JsonType {

    String toJsonString(); // From objects to primitive text

    String toPrettyString(int indent); // From primitive text to classic look json
}