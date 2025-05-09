package Controllers;

import Views.ConfirmationWindow;
import Views.GameMenu;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {
    private Stage menuStage;

    public MenuController(Stage menuStage) {
        this.menuStage = menuStage;
    }

    // Method to resume the game, closing the menu stage
    public void onResume() {
        menuStage.close();
    }

    // Method to return to the main menu
    public void onMainMenu(Stage stage) {
        menuStage.close();

        // Close the current stage (previous window)
        Stage currentStage = (Stage) stage.getScene().getWindow();
        currentStage.close();

        // Create a new GameMenu and start it
        GameMenu gmenu = new GameMenu();
        Stage newStage = new Stage();

        // Initialize the GameMenuController and start the new menu stage
        GameMenuController controller = new GameMenuController(gmenu, newStage);
        gmenu.start(newStage);
    }

    // Method to handle quitting the game
    public void onQuit(Stage mainStage) {
        // Hide the main window instead of closing it immediately
        mainStage.hide();

        // Create a confirmation window
        ConfirmationWindow confirmationWindow = new ConfirmationWindow();

        // Create the scene and stage for the confirmation window
        Scene confirmationScene = new Scene(confirmationWindow.getMainLayout(), 400, 200);
        Stage confirmationStage = new Stage();
        confirmationStage.setTitle("Are you sure?");
        confirmationStage.setScene(confirmationScene);
        confirmationStage.setFullScreen(true); // Optional: full screen mode

        confirmationStage.show();

        // If the user clicks "Yes", close the application
        confirmationWindow.getYesButton().setOnAction(e -> {
            confirmationStage.close();
            mainStage.close(); // Close the application cleanly
        });

        // If the user clicks "No", return to the menu
        confirmationWindow.getNoButton().setOnAction(e -> {
            confirmationStage.close();
            mainStage.show(); // Show the main menu again
        });
    }
}
