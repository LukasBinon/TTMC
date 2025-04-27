package Controllers;




import Views.GameMenu;
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
	    // Ferme la scène actuelle (le menu)
	    stage.close();
	    
	    
	  
	    
	    // Crée une nouvelle instance de GameMenu
	    GameMenu gmenu = new GameMenu();
	    
	    // Créer un nouveau stage pour le GameMenu
	    Stage gameStage = new Stage();
	    
	    gmenu.start(gameStage);
	}



    public void onQuit() {
        System.exit(0);
    }
}
