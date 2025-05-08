package models;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import javafx.stage.Stage;

import Views.Menu;

import java.io.IOException;
import java.util.List;

import Controllers.BoardController;
import Exceptions.FXMLLoadingException;

public class Board extends Pane {
    
    private Menu menu;
    private List<PlayerConfig> playerConfigs; // Liste des joueurs
  

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
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/view.fxml"));
            AnchorPane root = loader.load(); // Load the FXML and return the root node

            // Check if the file was loaded correctly
            if (root == null) {
                throw new FXMLLoadingException(); // Throw custom exception if the file is not found
            }

            // Access the controller of the FXML file
            BoardController boardController = loader.getController();

            // Initialize players
            boardController.setPlayers(playerConfigs);
            boardController.initializePion();

            // Add the root node to the scene
            getChildren().add(root);

        } catch (FXMLLoadingException e) {
            System.err.println(e.getMessage()); // Prints "Error loading the FXML file."
        } catch (IOException e) {
            System.err.println("Unexpected IO error while loading the FXML file.");
            e.printStackTrace();
        }
    }



    
}
