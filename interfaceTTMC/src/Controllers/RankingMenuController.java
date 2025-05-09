package Controllers;

import java.util.List;

import Main.Play;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.PlayerConfig;

public class RankingMenuController {

    private List<PlayerConfig> players;
    
    @FXML
    private BorderPane borderPane;
    @FXML
    private HBox playerOneHBox;
    @FXML
    private HBox playerTwoHbox;
    @FXML
    private HBox playerThreeHBox;
    @FXML
    private HBox playerFourHBox;

    @FXML
    private Label playerRankOneLabel;
    @FXML
    private Label playerRankTwoLabel;
    @FXML
    private Label playerRankThreeLabel;
    @FXML
    private Label playerRankFourLabel;

    @FXML
    private Button gameMenuButton;

    @FXML
    public void initialize() {
        gameMenuButton.setOnAction(e -> goBackToMenu());
    }

    public void setPlayers(List<PlayerConfig> players) {
        this.players = players;
        initializePlayerView();
        setPlayerRanks();
    }

    
    private void initializePlayerView() {
        if (players.size() < 4) {
            playerFourHBox.setVisible(false);
        }
        if (players.size() < 3) {
            playerThreeHBox.setVisible(false);
        }
        if (players.size() < 2) {
            playerTwoHbox.setVisible(false);
        }
    }

    
    private void setPlayerRanks() {
        for (PlayerConfig p : players) {
            switch (p.getRank()) {
                case 1: playerRankOneLabel.setText(p.getPlayerName()); break;
                case 2: playerRankTwoLabel.setText(p.getPlayerName()); break;
                case 3: playerRankThreeLabel.setText(p.getPlayerName()); break;
                case 4: playerRankFourLabel.setText(p.getPlayerName()); break;
            }
        }
    }

    
    private void goBackToMenu() {
        // close current window
    	((Stage) gameMenuButton.getScene().getWindow()).close();

        // launch main menu
        Play gameMenu = new Play();
        gameMenu.start(new Stage());
    }
}
