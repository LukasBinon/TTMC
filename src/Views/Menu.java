package Views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Menu {
    private Stage menuStage; // Independent window for the menu
    private static final String FONT_PATH = "/fonts/PressStart2P-Regular.ttf"; // Path to Arcade font

    public Menu(Stage ownerStage) {
        // Create a new stage for the menu
        menuStage = new Stage();
        menuStage.initStyle(StageStyle.UNDECORATED); // Removes title bar
        menuStage.initModality(Modality.APPLICATION_MODAL); // Blocks interaction with the main window
        menuStage.initOwner(ownerStage); // Makes the board window the owner

        // Title at the top
        Text titleLabel = new Text("How much do you spend");
        titleLabel.setFill(Color.YELLOW);
        titleLabel.setStroke(Color.RED);
        titleLabel.setStrokeWidth(2);
        titleLabel.setEffect(new DropShadow(20, Color.RED));

        // Load Arcade font
        Font arcadeFont = Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 30);
        if (arcadeFont != null) {
            titleLabel.setFont(arcadeFont);
        } else {
            System.err.println("Error: Font not loaded for title label.");
        }

        // Menu container
        VBox menuBox = new VBox(20);
        menuBox.setPadding(new Insets(30, 20, 30, 20));
        menuBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.9); -fx-border-color: yellow; -fx-border-width: 5;");
        menuBox.setAlignment(Pos.TOP_CENTER);

        // Add buttons to the menu
        Button btnResume = createArcadeButton("Resume Game");
        Button btnMainMenu = createArcadeButton("Back to Main Menu");
        Button btnQuit = createArcadeButton("Quit Game");

        // Button actions
        btnResume.setOnAction(e -> menuStage.close()); // Closes the menu window
        btnMainMenu.setOnAction(e -> System.out.println("Returning to main menu..."));
        btnQuit.setOnAction(e -> System.exit(0)); // Exits the application

        menuBox.getChildren().addAll(titleLabel, btnResume, btnMainMenu, btnQuit);

        // Add the menu to the scene
        Scene scene = new Scene(menuBox, 700, 400); // Dimensions for the menu window
        menuStage.setScene(scene);
    }

    public void showMenu() {
        // Show the menu window if it's not already showing
        if (!menuStage.isShowing()) {
            menuStage.show();
        }
    }

    private Button createArcadeButton(String text) {
        Button button = new Button(text);

        Font arcadeFont = Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 25);
        if (arcadeFont != null) {
            button.setFont(arcadeFont);
        } else {
            System.err.println("Error: Font not loaded for button.");
        }

        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: transparent;");
        button.setPadding(new Insets(10, 20, 10, 20));

        // Hover effects
        button.setOnMouseEntered(e -> {
            button.setEffect(new DropShadow(10, Color.YELLOW));
            button.setTextFill(Color.RED);
        });
        button.setOnMouseExited(e -> {
            button.setEffect(null);
            button.setTextFill(Color.WHITE);
        });

        return button;
    }
}
