package models;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import Views.Menu;

import java.util.ArrayList;
import java.util.List;

public class Board extends Pane {
    private List<Case> cases = new ArrayList<>();
    private int tileSize = 80;
    private Menu menu;

    public Board(Stage primaryStage) {
        ImageView background = new ImageView(new Image("file:C:/Users/lulu1/OneDrive - Haute Ecole Louvain en Hainaut/Documents/ecole/bac2/Projet/board.png"));
        background.setPreserveRatio(false);
        background.setSmooth(true);
        background.fitWidthProperty().bind(primaryStage.widthProperty());
        background.fitHeightProperty().bind(primaryStage.heightProperty());

        Pane darkOverlay = new Pane();
        darkOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        darkOverlay.setPrefSize(1100, 800);

        StackPane backgroundStack = new StackPane();
        backgroundStack.getChildren().addAll(background, darkOverlay);

        menu = new Menu(primaryStage);

        getChildren().add(backgroundStack);
        createSnakeBoard();
    }

    public void start(Stage primaryStage) {
        Scene scene = new Scene(this, 1100, 800);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.M) {
                menu.showMenu();
            }
        });

        scene.getRoot().requestFocus();
    }

    private void createSnakeBoard() {
        int width = 8;
        int totalCases = 40;
        int currentX = 0, currentY = 0;
        boolean leftToRight = true;

        double centerOffsetX = 150;
        double centerOffsetY = 150;
        double horizontalSpacing = 10;
        double verticalSpacing = 15;

        for (int i = 0; i < totalCases; i++) {
            Rectangle rect = new Rectangle(tileSize, tileSize);
            rect.getStyleClass().add("rectangle");

            double posX = centerOffsetX + currentX * (tileSize + horizontalSpacing);
            double posY = centerOffsetY + currentY * (tileSize + verticalSpacing);

            rect.setX(posX);
            rect.setY(posY);

            if (i == 0) {
                rect.setWidth(tileSize + 10);
                rect.setHeight(tileSize + 10);
                rect.getStyleClass().add("start");
            } else if (i == totalCases - 1) {
                rect.setWidth(tileSize + 10);
                rect.setHeight(tileSize + 10);
                rect.getStyleClass().add("end");
            }

            getChildren().add(rect);

            if (leftToRight) {
                currentX++;
                if (currentX >= width) {
                    currentX = width - 1;
                    currentY += 2;
                    leftToRight = false;
                    if (i < totalCases - 2) addConnector(centerOffsetX, centerOffsetY, currentX, currentY, verticalSpacing);
                }
            } else {
                currentX--;
                if (currentX < 0) {
                    currentX = 0;
                    currentY += 2;
                    leftToRight = true;
                    if (i < totalCases - 2) addConnector(centerOffsetX, centerOffsetY, currentX, currentY, verticalSpacing);
                }
            }
        }
    }

    private void addConnector(double centerOffsetX, double centerOffsetY, int currentX, int currentY, double verticalSpacing) {
        Rectangle connector = new Rectangle(tileSize / 2, verticalSpacing);
        connector.setX(centerOffsetX + currentX * (tileSize + 10) + tileSize / 4);
        connector.setY(centerOffsetY + (currentY - 1) * (tileSize + verticalSpacing) + tileSize);
        connector.getStyleClass().add("rectangle");

        getChildren().add(connector);
    }

    public List<Case> getCases() {
        return cases;
    }
}