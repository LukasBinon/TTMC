package Controllers;

import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import models.PlayerConfig;
import Views.ChoicePiece;
import models.Board;

import java.util.ArrayList;
import java.util.List;

public class ChoicePieceController {
    private static final String[] SHAPES = {"CIRCLE", "SQUARE", "TRIANGLE"};
    public static final Color[] COLORS = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};

    
    private int currentPlayerIndex = 0;
    private final List<PlayerConfig> playerConfigs = new ArrayList<>();
    private final ChoicePiece view;
    private final int playerCount;
    private final Stage stage;

    public ChoicePieceController(ChoicePiece view, int playerCount, Stage stage) {
        this.view = view;
        this.playerCount = playerCount;
        this.stage = stage;

        initializePlayers(); 
        setupView();
    }

    public void start() {
        stage.setScene(view.createScene());
        stage.setFullScreen(true);
        stage.show();
    }

    private void initializePlayers() {
        for (int i = 0; i < playerCount; i++) {
            playerConfigs.add(new PlayerConfig(
                "Player " + (i + 1),
                i % SHAPES.length,
                i % COLORS.length
            ));
        }
    }

    private void setupView() {
        updateUI();

        view.actionButton.setOnAction(e -> {
            playerConfigs.get(currentPlayerIndex).setPlayerName(view.nameField.getText());
            if (currentPlayerIndex < playerCount - 1) {
                currentPlayerIndex++;
                updateUI();
            } else {
                launchGame();
            }
        });

        view.shapeControls.getChildren().clear();
        Button leftBtn = view.createArcadeButton("◄");
        leftBtn.setOnAction(e -> updateShape(-1));

        Button rightBtn = view.createArcadeButton("►");
        rightBtn.setOnAction(e -> updateShape(1));

        view.shapeControls.getChildren().addAll(leftBtn, view.shapeLabel, rightBtn);
    }

    private void updateUI() {
        PlayerConfig player = playerConfigs.get(currentPlayerIndex);

        view.titleLabel.setText("PLAYER " + (currentPlayerIndex + 1) + " - CHOOSE YOUR TOKEN");
        view.nameField.setText(player.getPlayerName());
        view.shapeLabel.setText(SHAPES[player.getShapeIndex()]);

        view.content.getChildren().remove(view.currentShape);

        view.currentShape = createShape(player);
        view.content.getChildren().add(3, view.currentShape);

        view.actionButton.setText(currentPlayerIndex < playerCount - 1 ? "NEXT PLAYER" : "START GAME");

        view.currentShape.setOnMouseClicked(e -> updateColor());
    }

    private Shape createShape(PlayerConfig player) {
        Shape shape;
        switch (SHAPES[player.getShapeIndex()]) {
            case "CIRCLE": shape = new Circle(50); break;
            case "SQUARE": shape = new Rectangle(100, 100); break;
            case "TRIANGLE":
                Polygon triangle = new Polygon(0.0, 100.0, 50.0, 0.0, 100.0, 100.0);
                shape = triangle;
                break;
            default: shape = new Circle(50);
        }

        shape.setFill(COLORS[player.getColorIndex()]);
        shape.setStroke(Color.WHITE);
        shape.setStrokeWidth(3);
        shape.setEffect(new javafx.scene.effect.DropShadow(20, Color.BLACK));

        return shape;
    }

    private void updateShape(int direction) {
        PlayerConfig current = playerConfigs.get(currentPlayerIndex);
        int newIndex = (current.getShapeIndex() + direction + SHAPES.length) % SHAPES.length;
        current.setShapeIndex(newIndex);
        updateUI();
    }

    private void updateColor() {
        PlayerConfig current = playerConfigs.get(currentPlayerIndex);
        int newIndex = (current.getColorIndex() + 1) % COLORS.length;
        current.setColorIndex(newIndex);
        view.currentShape.setFill(COLORS[newIndex]);
    }

    private void launchGame() {
        stage.close();
        Stage newStage = new Stage();
        Board board = new Board(newStage, playerConfigs);
        newStage.setFullScreen(true);
        board.start(newStage);
    }
}
