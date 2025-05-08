package Controllers;




import Views.ConfirmationWindow;
import Views.GameMenu;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MenuController {
    private Stage menuStage;

    public MenuController(Stage menuStage) {
        this.menuStage = menuStage;
    }

    

	public void onResume() {
        menuStage.close();
    }

	public void onMainMenu(Stage stage) {
	    
	    menuStage.close();

	    
	    Stage currentStage = (Stage) stage.getScene().getWindow();
	    currentStage.close();

	   
	    GameMenu gmenu = new GameMenu();
	    Stage newStage = new Stage();
	    
	    GameMenuController controller = new GameMenuController(gmenu, newStage);
	    gmenu.start(newStage);
	}




	public void onQuit(Stage mainStage) {
	    // Masque la fenêtre principale au lieu de la fermer directement
	    mainStage.hide();

	    // Crée la fenêtre de confirmation
	    ConfirmationWindow confirmationWindow = new ConfirmationWindow();

	    // Crée la scène et la stage pour la confirmation
	    Scene confirmationScene = new Scene(confirmationWindow.getMainLayout(), 400, 200);
	    Stage confirmationStage = new Stage();
	    confirmationStage.setTitle("Are you sure?");
	    confirmationStage.setScene(confirmationScene);
	    confirmationStage.setFullScreen(true); // facultatif

	    confirmationStage.show();

	    // Si l'utilisateur clique sur "Yes"
	    confirmationWindow.getYesButton().setOnAction(e -> {
	        confirmationStage.close();
	        mainStage.close(); // ferme l'application proprement
	    });

	    // Si l'utilisateur clique sur "No"
	    confirmationWindow.getNoButton().setOnAction(e -> {
	        confirmationStage.close();
	        mainStage.show(); // revient au menu
	    });
	}

}
