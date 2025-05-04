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

    public void setPlayers(List<PlayerConfig> players) {
        this.players = players;
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ranking.fxml"));
            Parent root = loader.load();

            // Récupère le contrôleur
            RankingMenuController controller = loader.getController();

            // On lui transmet la liste des joueurs
            controller.setPlayers(players);

            // Création de la scène
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Classement");
            stage.setResizable(false);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

