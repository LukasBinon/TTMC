package Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameMenu {

    private static final String FONT_PATH = "/fonts/PressStart2P-Regular.ttf";
    private static final String BACKGROUND_IMAGE_PATH = "/images/menuTTMC.png";

    private StackPane mainLayout;
    private TextField playerInput;
    private Button startButton;
    private Button rulesButton;
    private Button quitButton;
    private Button musicToggleButton;

    public GameMenu() {
        Image backgroundImage = loadBackgroundImage();
        StackPane backgroundStack = createBackgroundStack(backgroundImage);
        VBox titleContainer = createTitleContainer();
        VBox menuBox = createMenu();
        HBox musicControlBox = createMusicControlBox();

        BorderPane root = new BorderPane();
        root.setTop(titleContainer);
        root.setCenter(menuBox);
        BorderPane.setAlignment(musicControlBox, Pos.BOTTOM_RIGHT);
        BorderPane.setMargin(musicControlBox, new Insets(0, 20, 20, 0));
        root.setBottom(musicControlBox);

        mainLayout = new StackPane();
        mainLayout.getChildren().addAll(backgroundStack, root);
    }

    public StackPane getMainLayout() {
        return mainLayout;
    }

    public TextField getPlayerInput() {
        return playerInput;
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getRulesButton() {
        return rulesButton;
    }

    public Button getQuitButton() {
        return quitButton;
    }

    public Button getMusicToggleButton() {
        return musicToggleButton;
    }

    private Image loadBackgroundImage() {
        return new Image(getClass().getResourceAsStream(BACKGROUND_IMAGE_PATH));
    }
    
    public void close() {
        mainLayout.getScene().getWindow().hide();
    }
    
 

    private StackPane createBackgroundStack(Image backgroundImage) {
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true)
        );

        Pane transparentLayer = new Pane();
        transparentLayer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");

        StackPane backgroundStack = new StackPane();
        backgroundStack.setBackground(new Background(bgImage));
        backgroundStack.getChildren().add(transparentLayer);

        return backgroundStack;
    }

    private VBox createTitleContainer() {
        Text title = new Text("How much do you spend ?");
        title.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 40));
        title.setFill(Color.YELLOW);
        title.setStroke(Color.RED);
        title.setStrokeWidth(2);
        title.setEffect(new DropShadow(20, Color.RED));

        Text subtitle = new Text("Ready to take on the challenge?");
        subtitle.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 25));
        subtitle.setFill(Color.WHITE);
        subtitle.setEffect(new DropShadow(10, Color.YELLOW));

        VBox titleContainer = new VBox(22);
        titleContainer.setAlignment(Pos.CENTER);
        titleContainer.setPadding(new Insets(200, 0, 0, 0));
        titleContainer.getChildren().addAll(title, subtitle);

        return titleContainer;
    }

    private VBox createMenu() {
        Label playerLabel = new Label("Number of players:");
        playerLabel.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 25));
        playerLabel.setTextFill(Color.WHITE);

        playerInput = new TextField();
        playerInput.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 25));
        playerInput.setMaxWidth(200);
        playerInput.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: red;");

        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("[1-4]?") || change.getControlNewText().isEmpty()) {
                return change;
            }
            return null;
        });
        playerInput.setTextFormatter(formatter);

        startButton = createArcadeButton("Start Game");
        rulesButton = createArcadeButton("See Rules");
        quitButton = createArcadeButton("Quit");

        VBox menuBox = new VBox(15);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPadding(new Insets(-30, 0, 0, 0));
        menuBox.getChildren().addAll(playerLabel, playerInput, startButton, rulesButton, quitButton);

        return menuBox;
    }

    private Button createArcadeButton(String text) {
        Button button = new Button(text);
        Font arcadeFont = Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 25);

        button.setFont(arcadeFont);
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

    private HBox createMusicControlBox() {
        musicToggleButton = new Button("Music: ON");
        Font arcadeFont = Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 20);

        musicToggleButton.setFont(arcadeFont);
        musicToggleButton.setTextFill(Color.WHITE);
        musicToggleButton.setBackground(new Background(new BackgroundFill(
                Color.TRANSPARENT, new CornerRadii(0), Insets.EMPTY
        )));
        musicToggleButton.setPadding(new Insets(5, 10, 5, 10));

        return new HBox(musicToggleButton);
    }
    
   

    public void start(Stage stage) {
        stage.setFullScreen(true); 
        stage.setScene(mainLayout.getScene()); 
        stage.show(); 
    }

}
