package bg.tu_varna.sit.f24621744.task;

import bg.tu_varna.sit.f24621744.task.jsonWork.JsonType;

import java.nio.file.Path;

/**
 * Stores the state of the current application session.
 * <p>
 * Contains information about the open file and its parsed JSON contents.
 * Passed to each command during execution, allowing commands to read
 * and modify the current state.
 * </p>
 */

public class Session {

    /** Path to the currently open file; {@code null} if the file is not open. */
    private Path currentFilePath;

    /** The root node of the current file's JSON tree; {@code null} if the file is not open. */
    private JsonType rootNode;

    /**
     * Opens a file: saves its path and parsed JSON tree in the session.
     *
     * @param path is the path to the file to open.
     * @param rootNode is the root node of the file's JSON structure.
     */
    public void openFile(Path path, JsonType rootNode) {
        this.currentFilePath = path;
        this.rootNode = rootNode;
    }

    /**
     * Closes the current file: resets the path and JSON tree to {@code null}.
     * All unsaved changes will be lost.
     */
    public void closeFile() {
        this.currentFilePath = null;
        this.rootNode = null;
    }

    /**
     * Returns the root node of the JSON structure of the currently open file.
     *
     * @return the root {@link JsonType} node, or {@code null} if the file is not open.
     */
    public JsonType getRootNode() {
        return rootNode;
    }

    /**
     * Sets a new root node of the JSON structure for the current session.
     *
     * @param rootNode - the new root node of the JSON tree
     */
    public void setRootNode(JsonType rootNode) {
        this.rootNode = rootNode;
    }

    /**
     * Checks whether a file is currently open.
     *
     * @return {@code true} if the file is open; {@code false} otherwise
     */
    public boolean isFileOpen() {
        return currentFilePath != null;
    }

    /**
     * Возвращает путь к текущему открытому файлу.
     *
     * @return {@link Path} к открытому файлу или {@code null}, если файл не открыт
     */
    public Path getCurrentFilePath() {
        return currentFilePath;
    }


}