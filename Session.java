package bg.tu_varna.sit.f24621744.task;

import bg.tu_varna.sit.f24621744.task.jsonWork.JsonType;

import java.nio.file.Path;

public class Session {
    private Path currentFilePath;
    private JsonType rootNode;

    public void openFile(Path path, JsonType rootNode) {
        this.currentFilePath = path;
        this.rootNode = rootNode;
    }

    public void closeFile() {
        this.currentFilePath = null;
        this.rootNode = null;
    }

    public JsonType getRootNode() {
        return rootNode;
    }

    public void setRootNode(JsonType rootNode) {
        this.rootNode = rootNode;
    }

    public boolean isFileOpen() {
        return currentFilePath != null;
    }

    public Path getCurrentFilePath() {
        return currentFilePath;
    }


}