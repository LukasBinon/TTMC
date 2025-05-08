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

import Exceptions.AudioFileNotFoundException;
import Views.ChoicePiece;
import Views.ConfirmationWindow;
import Views.GameMenu;
import Views.RulesMenu;

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
            	    alert.setHeaderText(null); //no secondary title
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

    public void playBackgroundMusic() throws AudioFileNotFoundException {
        // Get the audio file URL from resources
        URL audioUrl = getClass().getResource(AUDIO_PATH);

        // Check if the audio file was found
        if (audioUrl != null) {
            // Create a Media object from the audio file URL
            Media media = new Media(audioUrl.toString());

            // Create a MediaPlayer to play the media
            mediaPlayer = new MediaPlayer(media);

            // Set the media player to loop indefinitely
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

            // Set the volume level (5% of the maximum volume)
            mediaPlayer.setVolume(0.05);

            // Start playing the background music
            mediaPlayer.play();
        } else {
            // Throw custom exception if the audio file is missing
            throw new AudioFileNotFoundException();
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
        RulesMenu rulesMenu = new RulesMenu();
        Scene rulesScene = new Scene(rulesMenu.getMainLayout());

        
        new RulesMenuController(rulesMenu, stage, view.getMainLayout().getScene());

        stage.setScene(rulesScene);
        stage.setFullScreen(true);
    }
}
