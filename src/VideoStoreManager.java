import core.Application;

public class VideoStoreManager {
    public static void main(String[] args) {
        // * Application entry point
        // Create a new instance of the Application class
        Application app = Application.getInstance();

        // Start the application
        app.start();

        // Display the main menu screen
        // app.displayMainMenu();

        // Exit the application
        System.exit(0);
    }
}
