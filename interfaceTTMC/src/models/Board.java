package models;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import Views.ChoicePiece;
import Views.Menu;

import java.io.IOException;
import java.util.List;

import Controllers.BoardController;

public class Board extends Pane {
    
    private Menu menu;
    private List<PlayerConfig> playerConfigs; // Liste des joueurs
    private Game game;

    public Board(Stage primaryStage, List<PlayerConfig> playerConfigs) {
        this.playerConfigs = playerConfigs; // Stocke la configuration des joueurs

        // Configuration de l'arrière-plan
        ImageView background = new ImageView(new Image("images/board.png"));
        background.setPreserveRatio(false);
        background.setSmooth(true);
        background.fitWidthProperty().bind(primaryStage.widthProperty());
        background.fitHeightProperty().bind(primaryStage.heightProperty());

        // Overlay sombre
        Pane darkOverlay = new Pane();
        darkOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        darkOverlay.setPrefSize(1100, 800);

        // Conteneur principal
        StackPane backgroundStack = new StackPane();
        backgroundStack.getChildren().addAll(background, darkOverlay);

        // Menu
        menu = new Menu(primaryStage);

        // Ajouter les éléments au panneau principal
        getChildren().add(backgroundStack);

        // Charger le plateau de jeu
        createSnakeBoard();

        // Placer les pions des joueurs
        placePlayerTokens();
    }

    public void start(Stage primaryStage) {
        // Création de la scène
        Scene scene = new Scene(this, 1100, 800);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();

        // Gestion des interactions clavier
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.M) {
                menu.showMenu();
            }
        });

        scene.getRoot().requestFocus();
    }

    private void createSnakeBoard() {
        loadRectanglesFromFXML();
    }

    private void loadRectanglesFromFXML() {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/view.fxml"));
            AnchorPane root = loader.load(); // Charge le FXML et retourne le root

            // Accéder au contrôleur de ce fichier FXML
            BoardController boardController = loader.getController();

            // Initialiser les joueurs 
            boardController.setPlayers(playerConfigs);
            boardController.initializePion();

            // Ajouter le root à la scène
            getChildren().add(root); 

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier FXML !");
            e.printStackTrace();
        }
    }


    private void placePlayerTokens() {
        if (playerConfigs == null || playerConfigs.isEmpty()) {
            return;
        }

        // Position de départ (à ajuster selon la position de la première case)
        double startX = 100; // Remplace par la vraie position de la case
        double startY = 100; 

        for (int i = 0; i < playerConfigs.size(); i++) {
            PlayerConfig player = playerConfigs.get(i);

            Circle token = new Circle(15); // Rayon du pion
            token.setFill(ChoicePiece.COLORS[player.getColorIndex()]); // Appliquer la couleur choisie
            token.setLayoutX(startX + (i * 20)); // Décalage pour éviter la superposition
            token.setLayoutY(startY);

            getChildren().add(token);
        }
    }
}
