package Controllers;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import models.EDifficulty;
import models.Game;

import java.net.URL;

import Views.ChoicePiece;
import Views.ConfirmationWindow;
import Views.GameMenu;

public class GameMenuController {

    private static final String AUDIO_PATH = "/sounds/01PressPlay.mp3";

    private final GameMenu view;
    private final Stage stage;
    private MediaPlayer mediaPlayer;
    private boolean isMusicPlaying = true;

    public GameMenuController(GameMenu view, Stage stage) {
        this.view = view;
        this.stage = stage;
        initController();
    }

    private void initController() {
    	view.getStartButton().setOnAction(e -> {
            if (!view.getPlayerInput().getText().isEmpty()) {
                int playerCount = Integer.parseInt(view.getPlayerInput().getText());
                startGame(playerCount);
            }
            else
            {
            	 Alert alert = new Alert(AlertType.INFORMATION);
            	    alert.setTitle("Information");
            	    alert.setHeaderText(null); // Pas de titre secondaire
            	    alert.setContentText("Please enter the number of players.");
            	    alert.initOwner(view.getMainLayout().getScene().getWindow());
            	    alert.showAndWait();
            }
        });
        view.getRulesButton().setOnAction(e -> showRules());
        view.getDifficultyButton().setOnAction(e -> configDifficulty());
        view.getQuitButton().setOnAction(e -> leave());
        view.getMusicToggleButton().setOnAction(e -> toggleMusic());
    }

    public void playBackgroundMusic() {
        URL audioUrl = getClass().getResource(AUDIO_PATH);
        if (audioUrl != null) {
            Media media = new Media(audioUrl.toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.05);
            mediaPlayer.play();
        } else {
            System.err.println("Error: Audio file not found. Check the file path.");
        }
    }

    private void toggleMusic() {
        if (mediaPlayer == null) return;

        if (isMusicPlaying) {
            mediaPlayer.pause();
            view.getMusicToggleButton().setText("Music: OFF");
        } else {
            mediaPlayer.play();
            view.getMusicToggleButton().setText("Music: ON");
        }

        isMusicPlaying = !isMusicPlaying;
    }
    public void leave() {
        // Close the current window
        view.close();

        // Create the confirmation window
        ConfirmationWindow confirmationWindow = new ConfirmationWindow();

        // Create the scene and stage for the confirmation window
        Scene confirmationScene = new Scene(confirmationWindow.getMainLayout(), 400, 200);
        Stage confirmationStage = new Stage();
        confirmationStage.setTitle("Are you sure?");
        confirmationStage.setScene(confirmationScene);

        // Set the confirmation window to fullscreen
        confirmationStage.setFullScreen(true);

        // Display the confirmation window
        confirmationStage.show();

        // Handle the user's response when "Yes" is clicked
        confirmationWindow.getYesButton().setOnAction(e -> {
            // If "Yes", close the application
            confirmationStage.close();
            stage.close();  // Close the main window
        });

        // Handle the user's response when "No" is clicked
        confirmationWindow.getNoButton().setOnAction(e -> {
            // If "No", close the confirmation window and return to the main window
            confirmationStage.close();
            stage.show();  // Show the main window again
        });
    }

    public void configDifficulty() {
    	Button button = view.getDifficultyButton();
    	if(Game.getDifficulty() == EDifficulty.NORMAL) {
    		button.setText("Hard");
    		Game.setDifficulty(EDifficulty.HARD);
    	}
    	else {
    		button.setText("Normal");
    		Game.setDifficulty(EDifficulty.NORMAL);
    	}
    	
    }


    public void startGame(int playerCount) {
        
    	view.close();
        ChoicePiece choicePiece = new ChoicePiece(playerCount);
        Stage gameStage = new Stage();
        choicePiece.start(gameStage);

}

    private void showRules() {
        System.out.println("Displaying game rules...");
        // TODO: Implement rules display logic
    }
}
