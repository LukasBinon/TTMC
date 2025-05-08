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

    // ✅ Ajout pour le singleton
    private static GameMenuController instance;

    public GameMenuController(GameMenu view, Stage stage) {
        this.view = view;
        this.stage = stage;
        instance = this; // ✅ initialise l'instance unique
        initController();
    }

    // ✅ Méthode pour obtenir l'instance du contrôleur
    public static GameMenuController getInstance() {
        return instance;
    }

    private void initController() {
        // Réinitialisation du bouton de musique
        view.getMusicToggleButton().setOnAction(e -> {
			try {
				toggleMusic();
			} catch (AudioFileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}); // Assurez-vous que cet événement est réenregistré

        view.getStartButton().setOnAction(e -> {
            if (!view.getPlayerInput().getText().isEmpty()) {
                int playerCount = Integer.parseInt(view.getPlayerInput().getText());
                startGame(playerCount);
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Please enter the number of players.");
                alert.initOwner(view.getMainLayout().getScene().getWindow());
                alert.showAndWait();
            }
        });
        
        // Enregistrez les autres événements comme d'habitude
        view.getRulesButton().setOnAction(e -> showRules());
        view.getDifficultyButton().setOnAction(e -> configDifficulty());
        view.getQuitButton().setOnAction(e -> leave());
    }


    public void toggleMusic() throws AudioFileNotFoundException {
        // Si le MediaPlayer est nul, on le recrée
        if (mediaPlayer == null) {
            playBackgroundMusic();
        }

        // Si la musique est en cours de lecture, on la met en pause
        if (isMusicPlaying) {
            mediaPlayer.pause();
            view.getMusicToggleButton().setText("Music: OFF");
        } else {
            mediaPlayer.play();
            view.getMusicToggleButton().setText("Music: ON");
        }

        isMusicPlaying = !isMusicPlaying;
    }

    public void playBackgroundMusic() throws AudioFileNotFoundException {
        URL audioUrl = getClass().getResource(AUDIO_PATH);

        if (audioUrl != null) {
            // Créer un nouveau MediaPlayer chaque fois
            Media media = new Media(audioUrl.toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.05);
            mediaPlayer.play();
        } else {
            throw new AudioFileNotFoundException();
        }
    }


    // ✅ Permet aux autres classes de vérifier l’état de la musique
    public boolean isMusicPlaying() {
        return isMusicPlaying;
    }

    public void leave() {
        view.close();
        ConfirmationWindow confirmationWindow = new ConfirmationWindow();
        Scene confirmationScene = new Scene(confirmationWindow.getMainLayout(), 400, 200);
        Stage confirmationStage = new Stage();
        confirmationStage.setTitle("Are you sure?");
        confirmationStage.setScene(confirmationScene);
        confirmationStage.setFullScreen(true);
        confirmationStage.show();

        confirmationWindow.getYesButton().setOnAction(e -> {
            confirmationStage.close();
            stage.close();
        });

        confirmationWindow.getNoButton().setOnAction(e -> {
            confirmationStage.close();
            stage.show();
        });
    }

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
