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
    private List<PlayerConfig> playerConfigs; // List of players' configurations
  

    public Board(Stage primaryStage, List<PlayerConfig> playerConfigs) {
        this.playerConfigs = playerConfigs; // Store the player configurations

        // Set up the background image
        ImageView background = new ImageView(new Image("images/board.png"));
        background.setPreserveRatio(false);
        background.setSmooth(true);
        background.fitWidthProperty().bind(primaryStage.widthProperty());
        background.fitHeightProperty().bind(primaryStage.heightProperty());

        // Dark overlay that covers the entire screen
        Pane darkOverlay = new Pane();
        darkOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        // Bind overlay size to the window dimensions
        darkOverlay.prefWidthProperty().bind(primaryStage.widthProperty());
        darkOverlay.prefHeightProperty().bind(primaryStage.heightProperty());

        // Main container for the background and overlay
        StackPane backgroundStack = new StackPane();
        backgroundStack.getChildren().addAll(background, darkOverlay);

        // Initialize the menu
        menu = new Menu(primaryStage);

        // Add elements to the main pane
        getChildren().add(backgroundStack);

        // Load the game board
        createSnakeBoard();
    }
    
    // Method to start the game scene
    public void start(Stage primaryStage) {
        // Create the scene
        Scene scene = new Scene(this, 1100, 800);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();

        // Handle keyboard interactions
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.M) {
                menu.showMenu(); // Show the menu when 'M' is pressed
            }
        });

        scene.getRoot().requestFocus(); // Request focus for keyboard input
    }

    // Method to create the snake board
    private void createSnakeBoard() {
        loadRectanglesFromFXML(); // Load rectangles from FXML file for the board layout
    }

    // Load rectangles from FXML
    private void loadRectanglesFromFXML() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/view.fxml"));
            AnchorPane root = loader.load(); // Load the FXML and return the root node

            // Check if the file was loaded correctly
            if (root == null) {
                throw new FXMLLoadingException(); // Throw a custom exception if the file is not found
            }

            // Access the controller of the FXML file
            BoardController boardController = loader.getController();

            // Initialize players
            boardController.setPlayers(playerConfigs);
            boardController.initializePion(); // Initialize the game pieces

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
