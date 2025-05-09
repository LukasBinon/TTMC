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

    // ✅ Added for Singleton pattern
    private static GameMenuController instance;

    public GameMenuController(GameMenu view, Stage stage) {
        this.view = view;
        this.stage = stage;
        instance = this; // ✅ Initializes the unique instance
        initController();
    }

    // ✅ Method to get the controller instance
    public static GameMenuController getInstance() {
        return instance;
    }

    private void initController() {
        // Re-initializing the music toggle button
        view.getMusicToggleButton().setOnAction(e -> {
			try {
				toggleMusic();
			} catch (AudioFileNotFoundException e1) {
				// TODO: Handle the exception properly
				e1.printStackTrace();
			}
		}); // Make sure this event is re-registered

        // Start the game when the player count is entered
        view.getStartButton().setOnAction(e -> {
            if (!view.getPlayerInput().getText().isEmpty()) {
                int playerCount = Integer.parseInt(view.getPlayerInput().getText());
                startGame(playerCount);
            } else {
                // Alert if the number of players is not entered
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Please enter the number of players.");
                alert.initOwner(view.getMainLayout().getScene().getWindow());
                alert.showAndWait();
            }
        });
        
        // Register other events as usual
        view.getRulesButton().setOnAction(e -> showRules());
        view.getDifficultyButton().setOnAction(e -> configDifficulty());
        view.getQuitButton().setOnAction(e -> leave());
    }

    // Method to toggle background music on and off
    public void toggleMusic() throws AudioFileNotFoundException {
        // If the MediaPlayer is null, create a new one
        if (mediaPlayer == null) {
            playBackgroundMusic();
        }

        // If the music is playing, pause it
        if (isMusicPlaying) {
            mediaPlayer.pause();
            view.getMusicToggleButton().setText("Music: OFF");
        } else {
            mediaPlayer.play();
            view.getMusicToggleButton().setText("Music: ON");
        }

        isMusicPlaying = !isMusicPlaying;
    }

    // Method to play background music
    public void playBackgroundMusic() throws AudioFileNotFoundException {
        URL audioUrl = getClass().getResource(AUDIO_PATH);

        if (audioUrl != null) {
            // Create a new MediaPlayer each time
            Media media = new Media(audioUrl.toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);  // Loop the music indefinitely
            mediaPlayer.setVolume(0.05);  // Set the volume to a low value
            mediaPlayer.play();
        } else {
            throw new AudioFileNotFoundException();
        }
    }

    // ✅ Allows other classes to check the music state
    public boolean isMusicPlaying() {
        return isMusicPlaying;
    }

    // Method to handle quitting the game
    public void leave() {
        view.close();
        ConfirmationWindow confirmationWindow = new ConfirmationWindow();
        Scene confirmationScene = new Scene(confirmationWindow.getMainLayout(), 400, 200);
        Stage confirmationStage = new Stage();
        confirmationStage.setTitle("Are you sure?");
        confirmationStage.setScene(confirmationScene);
        confirmationStage.setFullScreen(true);
        confirmationStage.show();

        // Handle 'Yes' button press to close the application
        confirmationWindow.getYesButton().setOnAction(e -> {
            confirmationStage.close();
            stage.close();
        });

        // Handle 'No' button press to cancel and show the main stage
        confirmationWindow.getNoButton().setOnAction(e -> {
            confirmationStage.close();
            stage.show();
        });
    }

    // Method to configure the game's difficulty
    public void configDifficulty() {
        Button button = view.getDifficultyButton();
        if (Game.getDifficulty() == EDifficulty.NORMAL) {
            button.setText("Hard");
            Game.setDifficulty(EDifficulty.HARD);
        } else {
            button.setText("Normal");
            Game.setDifficulty(EDifficulty.NORMAL);
        }
    }

    // Method to start the game with the given number of players
    public void startGame(int playerCount) {
        view.close();
        ChoicePiece choicePiece = new ChoicePiece(playerCount);
        Stage gameStage = new Stage();
        choicePiece.start(gameStage);
    }

    // Method to display the game rules
    private void showRules() {
        RulesMenu rulesMenu = new RulesMenu();
        Scene rulesScene = new Scene(rulesMenu.getMainLayout());
        new RulesMenuController(rulesMenu, stage, view.getMainLayout().getScene());
        stage.setScene(rulesScene);
        stage.setFullScreen(true);
    }
}
