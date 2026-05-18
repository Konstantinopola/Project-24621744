package bg.tu_varna.sit.f24621744.task;

/**
 * The main entry point to the JSON editor application.
 * <p>
 * This class contains the {@code main} method, which launches the application,
 * delegating control to the {@link Main} class.
 * </p>
 */
public class Application {

    /**
     * Application entry point.
     * <p>
     * Creates an instance of {@link Main} and starts the interactive loop
     * for processing user commands.
     * </p>
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        new Main().start();
    }
}
