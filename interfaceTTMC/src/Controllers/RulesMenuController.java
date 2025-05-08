package Controllers;

import Views.RulesMenu;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RulesMenuController {

    private final RulesMenu view;
    private final Stage stage;
    private final Scene mainMenuScene;

    public RulesMenuController(RulesMenu view, Stage stage, Scene mainMenuScene) {
        this.view = view;
        this.stage = stage;
        this.mainMenuScene = mainMenuScene;

        initController();
    }

    private void initController() {
        view.getBackButton().setOnAction(e -> returnToMainMenu());
    }

    private void returnToMainMenu() {
        stage.setScene(mainMenuScene);
        stage.setFullScreen(true);
    }

}
