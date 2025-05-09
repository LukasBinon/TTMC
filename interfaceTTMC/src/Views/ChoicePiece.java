package Views;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import Controllers.ChoicePieceController;

public class ChoicePiece extends Application {
    private final int playerCount;

    public StackPane root;
    public Text titleLabel;
    public TextField nameField;
    public Label shapeLabel;
    public VBox content;
    public HBox shapeControls;
    public Shape currentShape;
    public Button actionButton;
    public Pane transparentLayer;
    public ImageView background;

    public final static String FONT_PATH = "/fonts/PressStart2P-Regular.ttf";

    // Constructor with default 2 players
    public ChoicePiece() {
        this(2); // Default to 2 players
    }

    // Constructor with a custom number of players (1 to 4)
    public ChoicePiece(int playerCount) {
        this.playerCount = Math.min(Math.max(playerCount, 1), 4); // Ensure playerCount is between 1 and 4
    }

    @Override
    public void start(Stage primaryStage) {
        buildUI(); // Build the user interface
        ChoicePieceController controller = new ChoicePieceController(this, playerCount, primaryStage);
        controller.start(); // Start the controller
    }

    // Build the UI components and layout
    private void buildUI() {
        // Load the custom font
        Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 12);

        // Create background and transparent overlay
        background = createBackground();
        transparentLayer = createTransparentLayer();

        // Create UI components
        titleLabel = createTitleLabel();
        nameField = createNameField();
        shapeLabel = createShapeLabel();
        shapeControls = new HBox(20);
        shapeControls.setAlignment(Pos.CENTER); // Align the shape controls to the center

        currentShape = null; // Will be injected by the controller
        actionButton = createArcadeButton("NEXT"); // Button to move to the next screen

        // Set up the content layout with a vertical box (VBox)
        content = new VBox(30, titleLabel, nameField, shapeControls, actionButton);
        content.setAlignment(Pos.CENTER); // Center the content
        content.setPadding(new Insets(50)); // Add padding around the content

        // Create the root StackPane containing the background, transparent layer, and content
        root = new StackPane(background, transparentLayer, content);
    }

    // Create and return the main scene for the application
    public Scene createScene() {
        return new Scene(root, 800, 600); // Set scene dimensions
    }

    // Create the background image view
    private ImageView createBackground() {
        ImageView bg = new ImageView(new Image("/images/menuTTMC.png"));
        bg.setFitWidth(Screen.getPrimary().getBounds().getWidth()); // Set background to fit the screen width
        bg.setFitHeight(Screen.getPrimary().getBounds().getHeight()); // Set background to fit the screen height
        bg.setPreserveRatio(false); // Don't preserve the aspect ratio
        return bg;
    }

    // Create a transparent overlay layer for visual effect
    private Pane createTransparentLayer() {
        Pane layer = new Pane();
        layer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);"); // Semi-transparent black layer
        return layer;
    }

    // Create the title label with custom font, color, and shadow effect
    private Text createTitleLabel() {
        Text title = new Text();
        title.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 30)); // Set the font size
        title.setFill(Color.YELLOW); // Set text color to yellow
        title.setStroke(Color.RED); // Set text stroke color to red
        title.setStrokeWidth(2); // Set stroke width
        title.setEffect(new DropShadow(20, Color.RED)); // Add a red shadow effect
        return title;
    }

    // Create a text field for entering the player's name
    private TextField createNameField() {
        TextField field = new TextField();
        field.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 20)); // Set font size
        field.setPromptText("ENTER YOUR NAME"); // Set the prompt text
        field.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: red;"); // Style the field
        field.setMaxWidth(300); // Set the max width of the text field
        return field;
    }

    // Create a label for selecting a shape (could be injected by the controller later)
    private Label createShapeLabel() {
        Label label = new Label();
        label.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 20)); // Set font size
        label.setTextFill(Color.WHITE); // Set label text color to white
        return label;
    }

    // Create and return an arcade-style button
    public Button createArcadeButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 20)); // Set font size
        button.setTextFill(Color.WHITE); // Set text color to white
        button.setBackground(new Background(new BackgroundFill(
            Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY))); // Make the button background transparent

        button.setPadding(new Insets(10, 20, 10, 20)); // Set button padding
        button.setOnMouseEntered(e -> {
            button.setEffect(new DropShadow(15, Color.YELLOW)); // Add yellow shadow on hover
            button.setTextFill(Color.RED); // Change text color on hover
        });
        button.setOnMouseExited(e -> {
            button.setTextFill(Color.WHITE); // Reset text color when not hovered
            button.setEffect(null); // Remove the shadow effect
        });

        return button;
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
