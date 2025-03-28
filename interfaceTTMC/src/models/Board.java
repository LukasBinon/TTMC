package models;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import Views.Menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Board extends Pane {
    private List<Case> cases = new ArrayList<>();
    private int tileSize = 80;
    private Menu menu;
    

    public Board(Stage primaryStage) {
        // Configuration de l'arrière-plan
        ImageView background = new ImageView(new Image("file:C:/Users/lulu1/OneDrive - Haute Ecole Louvain en Hainaut/Documents/ecole/bac2/Projet/board.png"));
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

        // Ajout des éléments au panneau principal
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
        // Chargement des rectangles depuis le fichier FXML
        loadRectanglesFromFXML();
    }

    private void loadRectanglesFromFXML() {
        try {
            // Charger le fichier FXML
            AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/view.fxml"));
            getChildren().add(root); // Ajout de tous les enfants du fichier FXML au panneau principal

            // Récupérer et traiter les rectangles
            for (javafx.scene.Node node : root.getChildrenUnmodifiable()) {
                if (node instanceof Rectangle) {
                    Rectangle rectFXML = (Rectangle) node;

                    // Création du rectangle avec les mêmes propriétés
                    Rectangle rect = new Rectangle(rectFXML.getWidth(), rectFXML.getHeight());
                    rect.setLayoutX(rectFXML.getLayoutX());
                    rect.setLayoutY(rectFXML.getLayoutY());
                    rect.getStyleClass().addAll(rectFXML.getStyleClass());

                    // Gestion des styles spécifiques pour "start" et "end"
                    if (rectFXML.getStyleClass().contains("start")) {
                        rect.setWidth(tileSize + 10);
                        rect.setHeight(tileSize + 10);
                    } else if (rectFXML.getStyleClass().contains("end")) {
                        rect.setWidth(tileSize + 10);
                        rect.setHeight(tileSize + 10);
                    }

                    // Ajouter le rectangle au panneau principal
                    getChildren().add(rect);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier FXML !");
            e.printStackTrace();
        }
    }

    public List<Case> getCases() {
        return cases;
    }
}
