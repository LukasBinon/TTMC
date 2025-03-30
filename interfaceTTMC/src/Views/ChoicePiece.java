package Views;

import javafx.application.Application;
import models.Board;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.effect.DropShadow;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ChoicePiece extends Application {
    private final String[] shapes = {"CIRCLE", "SQUARE", "TRIANGLE"};
    private final String[] colors = {"RED", "GREEN", "BLUE", "YELLOW"};
    private int shapeIndex = 0;
    private int colorIndex = 0;
    private Shape pionShape;
    private final Label shapeLabel = new Label(shapes[shapeIndex]);
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) {
        // Charger la police
        Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 12);
        
        // Image de fond avec couche transparente
        ImageView background = new ImageView(new Image("/images/menuTTMC.png"));
        background.setFitWidth(Screen.getPrimary().getBounds().getWidth());
        background.setFitHeight(Screen.getPrimary().getBounds().getHeight());
        background.setPreserveRatio(false);
        
        Pane transparentLayer = new Pane();
        transparentLayer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");

        // Titre
        Text titleLabel = new Text("CHOOSE YOUR TOKEN");
        titleLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 30));
        titleLabel.setFill(Color.YELLOW);
        titleLabel.setStroke(Color.RED);
        titleLabel.setStrokeWidth(2);
        titleLabel.setEffect(new DropShadow(20, Color.RED));

        // Champ de texte pour le nom
        TextField nameField = new TextField();
        nameField.setPromptText("ENTER YOUR NAME");
        nameField.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 12));
        nameField.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: red;");
        nameField.setMaxWidth(300);

        // Boutons de changement de forme
        Button leftArrow = createArcadeButton("◄");
        Button rightArrow = createArcadeButton("►");

        pionShape = createShape();  // Créer la forme initiale
        pionShape.setOnMouseClicked(e -> updateColor());  // Ajouter la gestion de couleur
        stylePionShape(pionShape);

        leftArrow.setOnAction(e -> updateShape(-1));  // Action pour flèche gauche
        rightArrow.setOnAction(e -> updateShape(1));  // Action pour flèche droite

        shapeLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 20));
        shapeLabel.setTextFill(Color.WHITE);

        HBox shapeSelector = new HBox(20, leftArrow, shapeLabel, rightArrow);
        shapeSelector.setAlignment(Pos.CENTER);

        // Affichage du pion dans une VBox
        VBox pionDisplay = new VBox(20, pionShape);
        pionDisplay.setAlignment(Pos.CENTER);

        // Bouton "Lancer le jeu"
        Button btnLancerJeu = createArcadeButton("LAUNCH GAME");
        btnLancerJeu.setOnAction(e -> launchGame());

        // Conteneur principal avec espacement
        VBox content = new VBox(30, titleLabel, nameField, shapeSelector, pionDisplay, btnLancerJeu);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(50));

        // Conteneur principal superposé avec l'image de fond
        StackPane root = new StackPane();
        root.getChildren().addAll(background, transparentLayer, content);

        // Création de la scène
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("How much do you spend? - Token Selection");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    // Crée un bouton avec le style arcade
    private Button createArcadeButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 15));
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

    // Méthode pour mettre à jour la forme
    private void updateShape(int direction) {
        shapeIndex = (shapeIndex + direction + shapes.length) % shapes.length;
        shapeLabel.setText(shapes[shapeIndex]);
        pionShape = createShape();
        pionShape.setOnMouseClicked(e -> updateColor());
        stylePionShape(pionShape);

        // Trouver la VBox qui contient la forme et remplacer l'ancienne forme par la nouvelle
        VBox pionDisplay = (VBox) shapeLabel.getParent().getParent().getChildrenUnmodifiable().get(3);
        pionDisplay.getChildren().set(0, pionShape);
    }

    // Méthode pour changer la couleur de la forme
    private void updateColor() {
        colorIndex = (colorIndex + 1) % colors.length;
        pionShape.setFill(getColor(colors[colorIndex]));
    }

    // Méthode pour créer une forme
    private Shape createShape() {
        Shape shape;
        switch (shapes[shapeIndex]) {
            case "CIRCLE": 
                shape = new Circle(50); 
                break;
            case "SQUARE": 
                shape = new Rectangle(100, 100); 
                break;
            case "TRIANGLE":
                Polygon triangle = new Polygon();
                triangle.getPoints().addAll(0.0, 100.0, 50.0, 0.0, 100.0, 100.0);
                shape = triangle;
                break;
            default: 
                shape = new Circle(50);
        }
        shape.setFill(getColor(colors[colorIndex]));
        return shape;
    }

    // Méthode pour obtenir la couleur
    private Color getColor(String colorName) {
        switch (colorName) {
            case "RED": return Color.RED;
            case "GREEN": return Color.GREEN;
            case "BLUE": return Color.BLUE;
            case "YELLOW": return Color.YELLOW;
            default: return Color.WHITE;
        }
    }

    // Méthode pour styliser la forme du pion
    private void stylePionShape(Shape shape) {
        shape.setStroke(Color.WHITE);
        shape.setStrokeWidth(3);
        shape.setEffect(new DropShadow(20, Color.BLACK));
    }
    
    private void launchGame() {
        Stage stage = new Stage();
        Board bd = new Board(stage);
        stage.setFullScreen(true);
        bd.start(stage);
    }

    @Override
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}