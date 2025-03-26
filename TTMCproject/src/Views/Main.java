package Views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    private static final String FONT_PATH = "/fonts/PressStart2P-Regular.ttf";
    private static final String BACKGROUND_IMAGE_PATH = "/images/menuTTMC.png";
    private static final String AUDIO_PATH = "/sounds/01PressPlay.mp3"; 

    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) throws IOException {

        //Lance la musique en arrière-plan
        playBackgroundMusic();
		AnchorPane root2 = (AnchorPane)FXMLLoader.load(getClass().getResource("view.fxml"));


        Image backgroundImage = loadBackgroundImage();

        StackPane backgroundStack = createBackgroundStack(backgroundImage);
        VBox titleContainer = createTitleContainer();
        VBox menuBox = createMenu(primaryStage);

        BorderPane root = new BorderPane();
        root.setTop(titleContainer);
        root.setCenter(menuBox);

        //Ajoute le label et le bouton ON/OFF en bas à droite
        HBox musicControlBox = createMusicControlBox();
        BorderPane.setAlignment(musicControlBox, Pos.BOTTOM_RIGHT);
        BorderPane.setMargin(musicControlBox, new Insets(0, 20, 20, 0));
        root.setBottom(musicControlBox);

        StackPane mainLayout = new StackPane();
        mainLayout.getChildren().addAll(backgroundStack, root);

       Scene scene = new Scene(mainLayout, 800, 600);
        //Scene scene = new Scene(root2, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("How much do you spend ?");
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }


    private void playBackgroundMusic() {
        URL audioUrl = getClass().getResource(AUDIO_PATH);
        if (audioUrl != null) {
            Media media = new Media(audioUrl.toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // ✅ Joue en boucle
            mediaPlayer.setVolume(0.05);
            mediaPlayer.play();
        } else {
            System.err.println("Error: Audio file not found. Check the file path.");
        }
    }

    /**
     * Charge l'image de fond
     */
    private Image loadBackgroundImage() {
        Image backgroundImage = new Image(getClass().getResourceAsStream(BACKGROUND_IMAGE_PATH));
        if (backgroundImage.isError()) {
            System.err.println("Error: Background image not loaded. Check the file path.");
            return null;
        }
        return backgroundImage;
    }

    /**
     *Crée le fond d'écran avec une couche transparente
     */
    private StackPane createBackgroundStack(Image backgroundImage) {
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true)
        );

        Pane transparentLayer = new Pane();
        transparentLayer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");

        StackPane backgroundStack = new StackPane();
        backgroundStack.setBackground(new Background(bgImage));
        backgroundStack.getChildren().add(transparentLayer);

        return backgroundStack;
    }

    /**
     * Crée le titre et le sous-titre
     */
    private VBox createTitleContainer() {
        Text title = new Text("How much do you spend ?");
        title.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 40));
        title.setFill(Color.YELLOW);
        title.setStroke(Color.RED);
        title.setStrokeWidth(2);
        title.setEffect(new DropShadow(20, Color.RED));

        Text subtitle = new Text("Ready to take on the challenge?");
        subtitle.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 25));
        subtitle.setFill(Color.WHITE);
        subtitle.setEffect(new DropShadow(10, Color.YELLOW));

        VBox titleContainer = new VBox(22);
        titleContainer.setAlignment(Pos.CENTER);
        titleContainer.setPadding(new Insets(200, 0, 0, 0));
        titleContainer.getChildren().addAll(title, subtitle);

        return titleContainer;
    }

    /**
     * Crée le menu avec les boutons et l'entrée joueur
     */
    private VBox createMenu(Stage primaryStage) {
        Label playerLabel = new Label("Number of players:");
        playerLabel.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 25));
        playerLabel.setTextFill(Color.WHITE);

        TextField playerInput = new TextField();
        playerInput.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 25));
        playerInput.setMaxWidth(200);
        playerInput.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: red;");

        Button startButton = createArcadeButton("Start Game");
        Button rulesButton = createArcadeButton("See Rules");
        Button quitButton = createArcadeButton("Quit");

        // Gestion des actions des boutons
        startButton.setOnAction(e -> {
            String players = playerInput.getText();
            launchGame();
            
        });

        rulesButton.setOnAction(e -> System.out.println("Displaying game rules..."));
        quitButton.setOnAction(e -> primaryStage.close());

        VBox menuBox = new VBox(15);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPadding(new Insets(-30, 0, 0, 0));
        menuBox.getChildren().addAll(playerLabel, playerInput, startButton, rulesButton, quitButton);

        return menuBox;
    }

    /**
     * Crée un bouton arcade stylisé
     */
    private Button createArcadeButton(String text) {
        Button button = new Button(text);

        Font arcadeFont = Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 25);
        if (arcadeFont != null) {
            button.setFont(arcadeFont);
        } else {
            System.err.println("Error: Font not loaded for button.");
        }

        button.setTextFill(Color.WHITE);
        button.setBackground(new Background(new BackgroundFill(
                Color.TRANSPARENT, new CornerRadii(0), Insets.EMPTY
        )));
        button.setPadding(new Insets(10, 20, 8, 20));

        button.setOnMouseEntered(e -> {
            button.setEffect(new DropShadow(15, Color.YELLOW));
            button.setTextFill(Color.RED);
        });
        button.setOnMouseExited(e -> {
            button.setEffect(null);
            button.setTextFill(Color.WHITE);
        });

        return button;
    }

    /**
     * Crée un HBox contenant un label, un bouton ON/OFF
     */
    private HBox createMusicControlBox() {

        Button toggleButton = new Button("Music: ON");

        // Applique le même style que les autres boutons
        Font arcadeFont = Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 20);
        if (arcadeFont != null) {
            toggleButton.setFont(arcadeFont);
        } else {
            System.err.println("Error: Font not loaded for button.");
        }

        toggleButton.setTextFill(Color.WHITE);
        toggleButton.setBackground(new Background(new BackgroundFill(
                Color.TRANSPARENT, new CornerRadii(0), Insets.EMPTY
        )));
        toggleButton.setPadding(new Insets(5, 10, 5, 10));

        // Effet d'ombre au survol
        toggleButton.setOnMouseEntered(e -> {
            toggleButton.setEffect(new DropShadow(10, Color.YELLOW));
            toggleButton.setTextFill(Color.RED);
        });
        toggleButton.setOnMouseExited(e -> {
            toggleButton.setEffect(null);
            toggleButton.setTextFill(Color.WHITE);
        });

        
        toggleButton.setOnAction(e -> {
            if (mediaPlayer != null) {
                if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.pause();
                    toggleButton.setText("Music: OFF");
                    toggleButton.setTextFill(Color.RED);
                } else {
                    mediaPlayer.play();
                    toggleButton.setText("Music: ON");
                    toggleButton.setTextFill(Color.GREEN);
                }
            }
        });

        HBox musicControlBox = new HBox(15);
        musicControlBox.setAlignment(Pos.BOTTOM_RIGHT);
        musicControlBox.getChildren().addAll( toggleButton);
        return musicControlBox;
    }

    @Override
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
    
    private void launchGame() {
        // Créer une nouvelle instance de ChoicePiece
        ChoicePiece choicePiece = new ChoicePiece();
        
        // Créer un nouveau stage (fenêtre) pour lancer la page "ChoicePiece"
        Stage stage = new Stage();
        
        // Appliquer le mode plein écran sur ce stage
        stage.setFullScreen(true);
        
        // Lancer la page ChoicePiece
        choicePiece.start(stage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
