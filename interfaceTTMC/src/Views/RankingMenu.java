package Views;

import java.util.List;

import Controllers.RankingMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.PlayerConfig;

public class RankingMenu extends Application {

    private List<PlayerConfig> players;

    // Setter to pass the list of players to the RankingMenu
    public void setPlayers(List<PlayerConfig> players) {
        this.players = players;
    }

    @Override
    public void start(Stage stage) {
        try {
            // Load the FXML file for the ranking menu
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ranking.fxml"));
            Parent root = loader.load();

            // Get the controller for the ranking menu
            RankingMenuController controller = loader.getController();

            // Pass the list of players to the controller
            controller.setPlayers(players);

            // Create the scene for the ranking menu
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Classement"); // Set the title of the window (Ranking)
            stage.setResizable(false); // Disable resizing of the window
            stage.setFullScreen(true); // Set the window to full screen
            stage.show(); // Display the stage
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions that occur during loading
        }
    }
}


