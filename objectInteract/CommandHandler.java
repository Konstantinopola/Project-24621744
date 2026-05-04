package bg.tu_varna.sit.f24621744.task.objectInteract;

import bg.tu_varna.sit.f24621744.task.jsonWork.JsonArray;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonObject;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonType;

import java.util.List;

public class CommandHandler {


    public static void create(JsonObject root, String[] path, JsonType newValue) {
        JsonObject current = root;

        for (int i = 0; i < path.length - 1; i++) {
            JsonType next = current.getValue(path[i]);
            if (!(next instanceof JsonObject)) {
                next = new JsonObject();
                current.add(path[i], next);
            }
            current = (JsonObject) next;
        }

        String finalKey = path[path.length - 1];
        JsonType existingNode = current.getValue(finalKey);


        if (existingNode instanceof JsonArray) {
            ((JsonArray) existingNode).add(newValue);
        }

        else {
            current.add(finalKey, newValue);
        }
    }


    public static void search(JsonType node, String key, List<JsonType> results) {
        if (node instanceof JsonObject) {
            JsonObject obj = (JsonObject) node;

            JsonType value = obj.getValue(key);
            if (value != null) {
                results.add(value);
            }

            for (JsonType child : obj.getProperties().values()) {
                search(child, key, results);
            }
        } else if (node instanceof JsonArray) {
            for (JsonType item : ((JsonArray) node).getElements()) {
                search(item, key, results);
            }
        }
    }

    // Навигация по пути (разделенному пробелами) для изменения или создания
    public static void set(JsonObject root, String[] path, JsonType newValue) {

    }

    // Удаление элемента по пути
    public static void delete(JsonObject root, String[] path) {
        JsonObject current = root;
        for (int i = 0; i < path.length - 1; i++) {
            JsonType next = current.getValue(path[i]);
            if (!(next instanceof JsonObject)) return; // Путь не найден
            current = (JsonObject) next;
        }
        current.remove(path[path.length - 1]);
    }
}