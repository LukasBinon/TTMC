package Views;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.effect.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.Board;
import models.PlayerConfig;

import java.util.ArrayList;
import java.util.List;

public class ChoicePiece extends Application {
    private static final String[] SHAPES = {"CIRCLE", "SQUARE", "TRIANGLE"};
    public static final Color[] COLORS = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
    private static final String FONT_PATH = "/fonts/PressStart2P-Regular.ttf";
    
    private int currentPlayerIndex = 0;
    private final int totalPlayers;
    private final List<PlayerConfig> playerConfigs = new ArrayList<>();
    private Shape currentShape;
    private Label shapeLabel;
    private TextField nameField;
    private Text titleLabel;

    public ChoicePiece(int playerCount) {
        if (playerCount < 1) {
            this.totalPlayers = 1;
        } else if (playerCount > 4) {
            this.totalPlayers = 4;
        } else {
            this.totalPlayers = playerCount;
        }
    }


    @Override
    public void start(Stage primaryStage) {
        initializePlayers();
        setup(primaryStage); // Configure l'interface
    
    }

    /**
     * Initialise la liste des joueurs avec des valeurs par défaut
     * - Noms: "Player 1", "Player 2"...
     * - Formes et couleurs réparties cycliquement
     */
    public void initializePlayers() {
        for (int i = 0; i < totalPlayers; i++) {
            playerConfigs.add(new PlayerConfig(
                "Player " + (i + 1), 
                i % SHAPES.length,  // Index de forme cyclique
                i % COLORS.length   // Index de couleur cyclique
            ));
        }
    }

    public void setup(Stage stage) {
        // Chargement de la police
        Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 12);

        // Configuration de l'arrière-plan
        ImageView background = createBackground();
        Pane transparentLayer = createTransparentLayer();

        /**
         * Configure l'interface utilisateur principale
         */
        PlayerConfig currentPlayer = playerConfigs.get(currentPlayerIndex);
        titleLabel = createTitleLabel();
        nameField = createNameField(currentPlayer);
        HBox shapeControls = createShapeControls(currentPlayer);
        currentShape = createPlayerShape(currentPlayer);
        Button actionButton = createActionButton(stage);

        // Organisation du layout
        VBox content = new VBox(30, titleLabel, nameField, shapeControls, currentShape, actionButton);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(50));

        StackPane root = new StackPane(background, transparentLayer, content);
        Scene scene = new Scene(root, 800, 600);

        // Configuration de la fenêtre
        stage.setTitle("How much do you spend? - Token Selection");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public ImageView createBackground() {
        ImageView bg = new ImageView(new Image("/images/menuTTMC.png"));
        bg.setFitWidth(Screen.getPrimary().getBounds().getWidth());
        bg.setFitHeight(Screen.getPrimary().getBounds().getHeight());
        bg.setPreserveRatio(false);
        return bg;
    }

    public Pane createTransparentLayer() {
        Pane layer = new Pane();
        layer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        return layer;
    }

    public Text createTitleLabel() {
        Text title = new Text("PLAYER " + (currentPlayerIndex + 1) + " - CHOOSE YOUR TOKEN");
        title.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 30));
        title.setFill(Color.YELLOW);
        title.setStroke(Color.RED);
        title.setStrokeWidth(2);
        title.setEffect(new DropShadow(20, Color.RED));
        return title;
    }

    public TextField createNameField(PlayerConfig player) {
        TextField field = new TextField(player.getPlayerName());
        field.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 20));
        field.setPromptText("ENTER YOUR NAME");
        field.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: red;");
        field.setMaxWidth(300);
        return field;
    }
    /**
     * Crée les contrôles pour changer la forme du pion
     */
    public HBox createShapeControls(PlayerConfig player) {
        shapeLabel = new Label(SHAPES[player.getShapeIndex()]);
        shapeLabel.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 20));
        shapeLabel.setTextFill(Color.WHITE);
        
       
        Button leftBtn = createArrowButton("◄", () -> updateShape(-1));
        Button rightBtn = createArrowButton("►", () -> updateShape(1));
        
   
        HBox container = new HBox(20, leftBtn, shapeLabel, rightBtn);
        container.setAlignment(Pos.CENTER); 
        
 
        
        return container;
    }

    public Shape createPlayerShape(PlayerConfig player) {
        Shape shape;
        switch (SHAPES[player.getShapeIndex()]) {
            case "CIRCLE": shape = new Circle(50); break;
            case "SQUARE": shape = new Rectangle(100, 100); break;
            case "TRIANGLE": 
                Polygon triangle = new Polygon();
                triangle.getPoints().addAll(0.0, 100.0, 50.0, 0.0, 100.0, 100.0);
                shape = triangle;
                break;
            default: shape = new Circle(50);
        }

        shape.setFill(COLORS[player.getColorIndex()]);
        shape.setStroke(Color.WHITE);
        shape.setStrokeWidth(3);
        shape.setEffect(new DropShadow(20, Color.BLACK));
        shape.setOnMouseClicked(e -> updateColor());

        return shape;
    }

    /**
     * Crée le bouton d'action principal
     * avec une gestion claire du texte selon l'état
     */
    public Button createActionButton(Stage stage) {
        String buttonText;
        
        if (currentPlayerIndex < totalPlayers - 1) {
            buttonText = "NEXT PLAYER";  
        } 
       
        else {
            buttonText = "START GAME";   
        }
        
        Button button = createArcadeButton(buttonText);
        
        
        button.setOnAction(e -> {
            // save le nom du joueur actuel
            playerConfigs.get(currentPlayerIndex).setPlayerName(nameField.getText());

            
            if (currentPlayerIndex < totalPlayers - 1) {
                // passe au joueur suivant
                currentPlayerIndex++;
                refreshUI(stage);  // Met à jour l'interface
            } else {
              
                launchGame(stage);
            }
        });

        return button;
    }

    
    //mise a jour forme
    public void updateShape(int direction) {
        PlayerConfig current = playerConfigs.get(currentPlayerIndex);
        int newIndex = (current.getShapeIndex() + direction + SHAPES.length) % SHAPES.length;
        current.setShapeIndex(newIndex);
        
        shapeLabel.setText(SHAPES[newIndex]);
        refreshShape();
    }

    //mise a jour couleur
    public void updateColor() {
        PlayerConfig current = playerConfigs.get(currentPlayerIndex);
        int newIndex = (current.getColorIndex() + 1) % COLORS.length;
        current.setColorIndex(newIndex);
        currentShape.setFill(COLORS[newIndex]);
    }

    public void refreshShape() {
        PlayerConfig current = playerConfigs.get(currentPlayerIndex);
        Shape newShape = createPlayerShape(current);
        
        // Remplace la forme actuelle dans la scène
        VBox parent = (VBox) currentShape.getParent();
        parent.getChildren().set(parent.getChildren().indexOf(currentShape), newShape);
        currentShape = newShape;
    }

    public void refreshUI(Stage stage) {
        PlayerConfig current = playerConfigs.get(currentPlayerIndex);
        
        // Met à jour les éléments UI
        titleLabel.setText("PLAYER " + (currentPlayerIndex + 1) + " - CHOOSE YOUR TOKEN");
        nameField.setText(current.getPlayerName());
        shapeLabel.setText(SHAPES[current.getShapeIndex()]);
        refreshShape();
        
        // Met à jour le bouton d'action
        Button actionButton = (Button) ((VBox) currentShape.getParent()).getChildren().get(4);
        actionButton.setText((currentPlayerIndex < totalPlayers - 1) ? "NEXT PLAYER" : "LAUNCH GAME");
        
        }

    public void launchGame(Stage stage) {
        System.out.println("Launching game with " + totalPlayers + " players:");
        for (PlayerConfig config : playerConfigs) {
            System.out.println(config.getPlayerName() + 
                " - " + SHAPES[config.getShapeIndex()] + 
                " - " + COLORS[config.getColorIndex()]);
        }
        
        stage.close();
        Board board = new Board(new Stage(), playerConfigs);
        board.start(new Stage());
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

    public Button createArrowButton(String text, Runnable action) {
        Button button = createArcadeButton(text);
        button.setOnAction(e -> action.run());
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}