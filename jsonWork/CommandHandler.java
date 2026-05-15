package bg.tu_varna.sit.f24621744.task.jsonWork;

import java.util.List;

public class CommandHandler {

 public static void create(JsonType root, String[] path, JsonType newValue) {
        JsonType current = root;

        // building path
        for (int i = 0; i < path.length - 1; i++) {
            JsonType next = current.getChild(path[i]);
            if (!(next instanceof JsonObject)) {
                next = new JsonObject();
                current.addChild(path[i], next);
            }
            current = next;
        }

        String finalKey = path[path.length - 1];
        JsonType existingNode = current.getChild(finalKey);

        if (existingNode == null || !existingNode.append(newValue)) {
            current.addChild(finalKey, newValue);
        }
    }


    public static void search(JsonType node, String key, List<JsonType> results) {
        if (node == null) return;
        // Search on the same lvl
        JsonType match = node.getChild(key);
        if (match != null) {
            results.add(match);
        }
        // Go in the deep if not found
        for (JsonType child : node.getValues()) {
            search(child, key, results);
        }
    }

    // Навигация по пути (разделенному пробелами) для изменения или создания
    public static boolean set(JsonType rootNode, String[] path, JsonType newValue) {
        if (path == null || path.length == 0) return false;

        String targetKey = path[path.length - 1];
        String[] parentPath = new String[path.length - 1];
        System.arraycopy(path, 0, parentPath, 0, path.length - 1);

        JsonType parent = get(rootNode, parentPath);
        if (parent == null) return false;

        return parent.replaceChild(targetKey, newValue);
        // Parent will know what element need to change
    }

    public static boolean delete(JsonType rootNode, String[] path) {
        if (path == null || path.length == 0) return false;

        String targetKey = path[path.length - 1];
        if (path.length == 1) return rootNode.removeChild(targetKey);

        // Get path to the parent
        String[] parentPath = new String[path.length - 1];
        System.arraycopy(path, 0, parentPath, 0, path.length - 1);

        // Asking to delete child
        JsonType parent = get(rootNode, parentPath);
        if (parent == null) return false;

        return parent.removeChild(targetKey);
    }

        public static JsonType get(JsonType rootNode, String[] path) {
        JsonType current = rootNode;
        for (String key : path) {
            if (current == null) return null;
            current = current.getChild(key);
        }
        return current;
    }
}