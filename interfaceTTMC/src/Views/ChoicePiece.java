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

    public ChoicePiece() {
        this(2); // par défaut 2 joueurs
    }

    public ChoicePiece(int playerCount) {
        this.playerCount = Math.min(Math.max(playerCount, 1), 4);
    }

    @Override
    public void start(Stage primaryStage) {
        buildUI();
        ChoicePieceController controller = new ChoicePieceController(this, playerCount, primaryStage);
        controller.start();
    }

    private void buildUI() {
        Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 12);

        background = createBackground();
        transparentLayer = createTransparentLayer();

        titleLabel = createTitleLabel();
        nameField = createNameField();
        shapeLabel = createShapeLabel();
        shapeControls = new HBox(20);
        shapeControls.setAlignment(Pos.CENTER);

        currentShape = null; // sera injecté par le contrôleur
        actionButton = createArcadeButton("NEXT");

        content = new VBox(30, titleLabel, nameField, shapeControls, actionButton);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(50));

        root = new StackPane(background, transparentLayer, content);
    }

    public Scene createScene() {
        return new Scene(root, 800, 600);
    }

    private ImageView createBackground() {
        ImageView bg = new ImageView(new Image("/images/menuTTMC.png"));
        bg.setFitWidth(Screen.getPrimary().getBounds().getWidth());
        bg.setFitHeight(Screen.getPrimary().getBounds().getHeight());
        bg.setPreserveRatio(false);
        return bg;
    }

    private Pane createTransparentLayer() {
        Pane layer = new Pane();
        layer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        return layer;
    }

    private Text createTitleLabel() {
        Text title = new Text();
        title.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 30));
        title.setFill(Color.YELLOW);
        title.setStroke(Color.RED);
        title.setStrokeWidth(2);
        title.setEffect(new DropShadow(20, Color.RED));
        return title;
    }

    private TextField createNameField() {
        TextField field = new TextField();
        field.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 20));
        field.setPromptText("ENTER YOUR NAME");
        field.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: red;");
        field.setMaxWidth(300);
        return field;
    }

    private Label createShapeLabel() {
        Label label = new Label();
        label.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 20));
        label.setTextFill(Color.WHITE);
        return label;
    }

    public Button createArcadeButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 20));
        button.setTextFill(Color.WHITE);
        button.setBackground(new Background(new BackgroundFill(
            Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        button.setPadding(new Insets(10, 20, 10, 20));
        button.setOnMouseEntered(e -> {
            button.setEffect(new DropShadow(15, Color.YELLOW));
            button.setTextFill(Color.RED);
        });
        button.setOnMouseExited(e -> {
            button.setTextFill(Color.WHITE);
            button.setEffect(null);
        });

        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
