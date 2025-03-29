package Main;
import javafx.application.Application;
import javafx.stage.Stage;
import Views.GameMenu;

public class Play extends Application {
    @Override
    public void start(Stage primaryStage) {
        GameMenu gameMenu = new GameMenu();
        try {
            gameMenu.start(primaryStage); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args); 
    }
}
