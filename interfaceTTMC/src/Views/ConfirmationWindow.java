package Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ConfirmationWindow {

    private static final String FONT_PATH = "/fonts/PressStart2P-Regular.ttf";  // Use the same font
    private static final String BACKGROUND_IMAGE_PATH = "/images/menuTTMC.png"; // Use the same background image

    private StackPane mainLayout;
    private Button yesButton;
    private Button noButton;

    public ConfirmationWindow() {
        // Create the confirmation message
        Text message = new Text("Are you sure you want to quit?");
        message.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 30));
        message.setFill(Color.YELLOW);
        message.setStroke(Color.RED);
        message.setStrokeWidth(2);
        message.setEffect(new DropShadow(20, Color.RED));  // Apply shadow effect

        // Create the buttons for Yes and No
        yesButton = createStyledButton("Yes");
        noButton = createStyledButton("No");

        // Create layout and apply styling
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50, 20, 50, 20));  // Adjust the padding as needed
        layout.getChildren().addAll(message, yesButton, noButton);

        // Create a background and overlay effect
        StackPane backgroundStack = createBackgroundStack();

        // Set the final layout with background and buttons
        this.mainLayout = new StackPane();
        this.mainLayout.getChildren().addAll(backgroundStack, layout);
        // Set the content to resize based on its size
        this.mainLayout.setMinWidth(Region.USE_COMPUTED_SIZE);
        this.mainLayout.setMinHeight(Region.USE_COMPUTED_SIZE);
    }

    // Method to get the main layout
    public StackPane getMainLayout() {
        return mainLayout;
    }

    // Method to get the Yes button
    public Button getYesButton() {
        return yesButton;
    }

    // Method to get the No button
    public Button getNoButton() {
        return noButton;
    }

    // Create a styled button with arcade effects
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 25));
        button.setTextFill(Color.WHITE);
        button.setBackground(new Background(new BackgroundFill(
                Color.TRANSPARENT, new CornerRadii(0), Insets.EMPTY
        )));
        button.setPadding(new Insets(10, 20, 8, 20));

        button.setOnMouseEntered(e -> {
            button.setEffect(new DropShadow(15, Color.YELLOW));
            button.setTextFill(Color.RED);
        });
        button.setOnMouseExited(e -> {
            button.setEffect(null);
            button.setTextFill(Color.WHITE);
        });

        return button;
    }

    // Method to create background stack with overlay effect
    private StackPane createBackgroundStack() {
        // Background image
        Image backgroundImage = new Image(getClass().getResourceAsStream(BACKGROUND_IMAGE_PATH));
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true)
        );

        // Transparent overlay layer
        Pane transparentLayer = new Pane();
        transparentLayer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");

        // Stack pane for the background image and overlay
        StackPane backgroundStack = new StackPane();
        backgroundStack.setBackground(new Background(bgImage));
        backgroundStack.getChildren().add(transparentLayer);

        return backgroundStack;
    }
}
