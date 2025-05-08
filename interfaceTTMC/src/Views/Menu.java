package Views;

import Controllers.GameMenuController;
import Controllers.MenuController;
import Exceptions.AudioFileNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Menu {

    private VBox root;
    private Text titleLabel;
    private Button btnResume;
    private Button btnMainMenu;
    private Button btnQuit;
    private Button musicToggleButton;

    private Stage menuStage;
    private static final String FONT_PATH = "/fonts/PressStart2P-Regular.ttf";

    public Menu(Stage ownerStage) {
        // Création du Stage
        menuStage = new Stage();
        menuStage.initStyle(StageStyle.UNDECORATED);
        menuStage.initModality(Modality.APPLICATION_MODAL);
        menuStage.initOwner(ownerStage);

        // Contrôleur
        MenuController controller = new MenuController(menuStage);

        // Construction UI
        root = new VBox(20);
        root.setPadding(new Insets(30, 20, 30, 20));
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().add("root");

        // Titre
        titleLabel = new Text("How much do you spend");
        titleLabel.getStyleClass().add("title-label");

        Font arcadeFontTitle = Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 30);
        Font arcadeFontButtons = Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 25);
        if (arcadeFontTitle != null) titleLabel.setFont(arcadeFontTitle);

        // Boutons
        btnResume = createButton("Resume Game", arcadeFontButtons);
        btnMainMenu = createButton("Back to Main Menu", arcadeFontButtons);
        btnQuit = createButton("Quit Game", arcadeFontButtons);

        // Bouton musique
        musicToggleButton = createButton("Music: ON", arcadeFontButtons);

        GameMenuController controllerInstance = GameMenuController.getInstance();
        if (controllerInstance != null) {
            updateMusicButtonText(controllerInstance);
        }

        musicToggleButton.setOnAction(e -> {
            GameMenuController musicController = GameMenuController.getInstance();
            if (musicController != null) {
                try {
					musicController.toggleMusic();
				} catch (AudioFileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                updateMusicButtonText(musicController);
            }
        });

        // Actions
        btnResume.setOnAction(e -> controller.onResume());
        btnMainMenu.setOnAction(e -> controller.onMainMenu(ownerStage));
        btnQuit.setOnAction(e -> controller.onQuit(ownerStage));

        root.getChildren().addAll(titleLabel, btnResume, btnMainMenu, btnQuit, musicToggleButton);

        // Scène
        Scene scene = new Scene(root, 700, 400);
        scene.getStylesheets().add(getClass().getResource("/Views/menu.css").toExternalForm());
        menuStage.setScene(scene);
    }

    private Button createButton(String text, Font font) {
        Button button = new Button(text);
        button.setFont(font);
        button.getStyleClass().add("arcade-button");
        return button;
    }

    private void updateMusicButtonText(GameMenuController controller) {
        boolean isPlaying = controller.isMusicPlaying();
        musicToggleButton.setText(isPlaying ? "Music: ON" : "Music: OFF");
    }

    public void showMenu() {
        if (!menuStage.isShowing()) {
            menuStage.show();
        }
    }
}
