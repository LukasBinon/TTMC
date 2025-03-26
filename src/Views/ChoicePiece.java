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
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ChoicePiece extends Application {
    private final String[] shapes = {"Cercle", "Carré", "Triangle"};
    private final String[] colors = {"Rouge", "Vert", "Bleu", "Jaune"};
    private int shapeIndex = 0;
    private int colorIndex = 0;
    private Shape pionShape;
    private final Label shapeLabel = new Label(shapes[shapeIndex]);

    @Override
    public void start(Stage primaryStage) {
        // Image de fond
        ImageView background = new ImageView(new Image("file:C:/Users/lulu1/OneDrive - Haute Ecole Louvain en Hainaut/Documents/ecole/bac2/Projet/interface.png"));
        background.setFitWidth(Screen.getPrimary().getBounds().getWidth());
        background.setFitHeight(Screen.getPrimary().getBounds().getHeight());
        background.setPreserveRatio(false);

        Label titleLabel = new Label("Choisis ton pion");
        titleLabel.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Champ de texte pour le nom
        TextField nameField = new TextField();
        nameField.setPromptText("Entrez un nom");
        styleTextField(nameField);

        // Boutons de changement de forme
        Button leftArrow = new Button("⬅");
        Button rightArrow = new Button("➡");
        styleArrowButton(leftArrow);
        styleArrowButton(rightArrow);

        pionShape = createShape();  // Créer la forme initiale
        pionShape.setOnMouseClicked(e -> updateColor());  // Ajouter la gestion de couleur
        stylePionShape(pionShape);

        leftArrow.setOnAction(e -> updateShape(-1));  // Action pour flèche gauche
        rightArrow.setOnAction(e -> updateShape(1));  // Action pour flèche droite

        HBox shapeSelector = new HBox(10, leftArrow, shapeLabel, rightArrow);
        shapeSelector.setAlignment(Pos.CENTER);

        // Affichage du pion dans une VBox
        VBox pionDisplay = new VBox(10, pionShape);
        pionDisplay.setAlignment(Pos.CENTER);

        // Bouton "Lancer le jeu"
        Button btnLancerJeu = new Button("launch game");
        btnLancerJeu.setStyle("-fx-background-color: linear-gradient(to right, #ffdb3b, #fe53bb, #5a0267, #5a026f); " +
                              "-fx-background-radius: 15px; " +
                              "-fx-font-size: 18px; " +
                              "-fx-text-fill: white; " +
                              "-fx-cursor: hand; " +
                              "-fx-pref-width: 150px; " +
                              "-fx-pref-height: 50px;");

        btnLancerJeu.setOnAction(e -> launchGame());

        // Conteneur principal avec espacement
        VBox content = new VBox(15, nameField, shapeSelector, pionDisplay, btnLancerJeu);
        content.setAlignment(Pos.CENTER);
        styleMainContainer(content);

        // Conteneur principal superposé avec l'image de fond
        StackPane root = new StackPane();
        root.getChildren().addAll(background, content);

        // Création de la scène
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("Sélection du Pion");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode pour mettre à jour la forme
    private void updateShape(int direction) {
        shapeIndex = (shapeIndex + direction + shapes.length) % shapes.length;
        shapeLabel.setText(shapes[shapeIndex]);
        pionShape = createShape();
        pionShape.setOnMouseClicked(e -> updateColor());
        stylePionShape(pionShape);

        // Trouver la VBox qui contient la forme et remplacer l'ancienne forme par la nouvelle
        VBox pionDisplay = (VBox) shapeLabel.getParent().getParent().getChildrenUnmodifiable().get(2);
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
            case "Cercle": shape = new Circle(30); break;
            case "Carré": shape = new Rectangle(60, 60); break;
            case "Triangle":
                Polygon triangle = new Polygon();
                triangle.getPoints().addAll(0.0, 60.0, 30.0, 0.0, 60.0, 60.0);
                shape = triangle;
                break;
            default: shape = new Circle(30);
        }
        shape.setFill(getColor(colors[colorIndex]));
        return shape;
    }

    // Méthode pour obtenir la couleur
    private Color getColor(String colorName) {
        switch (colorName) {
            case "Rouge": return Color.RED;
            case "Vert": return Color.GREEN;
            case "Bleu": return Color.BLUE;
            case "Jaune": return Color.YELLOW;
            default: return Color.BLACK;
        }
    }

    // Méthode pour styliser le conteneur principal
    private void styleMainContainer(VBox container) {
        container.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); " +
                          "-fx-border-radius: 15px; " +
                          "-fx-border-color: #8707ff; " +
                          "-fx-border-width: 2px;");
    }

    // Méthode pour styliser le champ de texte
    private void styleTextField(TextField field) {
        field.setStyle("-fx-font-size: 18px; " +
                       "-fx-background-color: #222; " +
                       "-fx-text-fill: white; " +
                       "-fx-border-color: #8707ff; " +
                       "-fx-border-width: 2px; " +
                       "-fx-border-radius: 8px;");
    }

    // Méthode pour styliser la forme du pion
    private void stylePionShape(Shape shape) {
        shape.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 10, 0, 0, 4); " +
                       "-fx-cursor: hand;");
    }

    // Méthode pour styliser les boutons fléchés
    private void styleArrowButton(Button button) {
        button.setStyle("-fx-background-color: linear-gradient(to right, #ffdb3b, #fe53bb, #5a0267, #5a026f); " +
                        "-fx-background-radius: 15px; " +
                        "-fx-font-size: 18px; " +
                        "-fx-text-fill: white; " +
                        "-fx-cursor: hand; " +
                        "-fx-pref-width: 60px; " +
                        "-fx-pref-height: 40px;");
    }
    
    private void launchGame() {
        
    	// Créer un nouveau stage (fenêtre) pour lancer la page "ChoicePiece"
        Stage stage = new Stage();
        
    	Board bd = new Board(stage);
        
        // Appliquer le mode plein écran sur ce stage
        stage.setFullScreen(true);
        
        // Lancer la page ChoicePiece
        bd.start(stage);
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
