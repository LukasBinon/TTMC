package Views;

import Controllers.GameMenuController;
import Controllers.MenuController;
import Exceptions.AudioFileNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Menu {

    private VBox root;
    private Text titleLabel;
    private Button btnResume;
    private Button btnMainMenu;
    private Button btnQuit;
    private Button musicToggleButton;

    private Stage menuStage;
    private static final String FONT_PATH = "/fonts/PressStart2P-Regular.ttf";

    // Constructor to initialize the menu with a reference to the owner stage
    public Menu(Stage ownerStage) {
        // Create the Stage for the menu
        menuStage = new Stage();
        menuStage.initStyle(StageStyle.UNDECORATED); // Remove window borders
        menuStage.initModality(Modality.APPLICATION_MODAL); // Make it modal (blocks interaction with other windows)
        menuStage.initOwner(ownerStage); // Set the owner stage

        // Controller instance
        MenuController controller = new MenuController(menuStage);

        // Build the UI layout
        root = new VBox(20); // VBox layout with 20px spacing between elements
        root.setPadding(new Insets(30, 20, 30, 20)); // Add padding around the root container
        root.setAlignment(Pos.TOP_CENTER); // Align content to the top center
        root.getStyleClass().add("root"); // Add a style class for custom styling

        // Title label for the menu
        titleLabel = new Text("How much do you spend"); // Title text
        titleLabel.getStyleClass().add("title-label"); // Add style class for the title

        // Load custom fonts for the title and buttons
        Font arcadeFontTitle = Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 30); // Font for title
        Font arcadeFontButtons = Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 25); // Font for buttons
        if (arcadeFontTitle != null) titleLabel.setFont(arcadeFontTitle);

        // Buttons for the menu options
        btnResume = createButton("Resume Game", arcadeFontButtons); // Resume button
        btnMainMenu = createButton("Back to Main Menu", arcadeFontButtons); // Main menu button
        btnQuit = createButton("Quit Game", arcadeFontButtons); // Quit button

        // Music toggle button
        musicToggleButton = createButton("Music: ON", arcadeFontButtons); // Initial state "ON"

        // Update music button text based on the current music state
        GameMenuController controllerInstance = GameMenuController.getInstance();
        if (controllerInstance != null) {
            updateMusicButtonText(controllerInstance);
        }

        // Toggle music state when the music button is pressed
        musicToggleButton.setOnAction(e -> {
            GameMenuController musicController = GameMenuController.getInstance();
            if (musicController != null) {
                try {
                    musicController.toggleMusic(); // Toggle music state
                } catch (AudioFileNotFoundException e1) {
                    // Handle exception if the audio file is not found
                    e1.printStackTrace();
                }
                updateMusicButtonText(musicController); // Update the button text after toggling
            }
        });

        // Set up the actions for the buttons
        btnResume.setOnAction(e -> controller.onResume()); // Resume game action
        btnMainMenu.setOnAction(e -> controller.onMainMenu(ownerStage)); // Go back to the main menu
        btnQuit.setOnAction(e -> controller.onQuit(ownerStage)); // Quit game action

        // Add all elements to the root container
        root.getChildren().addAll(titleLabel, btnResume, btnMainMenu, btnQuit, musicToggleButton);

        // Create the scene and add a stylesheet for custom styling
        Scene scene = new Scene(root, 700, 400);
        scene.getStylesheets().add(getClass().getResource("/Views/menu.css").toExternalForm());
        menuStage.setScene(scene);
    }

    // Create a button with a custom text and font
    private Button createButton(String text, Font font) {
        Button button = new Button(text); // Create the button with text
        button.setFont(font); // Set the custom font
        button.getStyleClass().add("arcade-button"); // Add style class for the arcade button
        return button;
    }

    // Update the text on the music toggle button based on whether the music is playing
    private void updateMusicButtonText(GameMenuController controller) {
        boolean isPlaying = controller.isMusicPlaying(); // Check if music is playing
        musicToggleButton.setText(isPlaying ? "Music: ON" : "Music: OFF"); // Update the button text
    }

    // Show the menu window if it is not already visible
    public void showMenu() {
        if (!menuStage.isShowing()) {
            menuStage.show(); // Show the menu
        }
    }
}
