package Main;

import Controllers.GameMenuController;
import Views.GameMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Play extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameMenu view = new GameMenu();
        GameMenuController controller = new GameMenuController(view, primaryStage);

        Scene scene = new Scene(view.getMainLayout(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("How much do you spend ?");
        primaryStage.setFullScreen(true);
        primaryStage.show();

        controller.playBackgroundMusic();
    }

    public static void main(String[] args) {
        launch(args);
    }
}