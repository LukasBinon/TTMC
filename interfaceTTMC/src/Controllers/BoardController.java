package Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Game;
import models.PlayerConfig;
import models.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Views.GameMenu;

public class BoardController {

    private List<PlayerConfig> players;
    @FXML
    private Circle pion1, pion2, pion3, pion4, pion1b, pion2b, pion3b, pion4b;
    private Game game;
    private int currentPlayer;
    
    @FXML private Rectangle tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8, tile9, tile10;
    @FXML private Rectangle tile11, tile12, tile13, tile14, tile15, tile16, tile17, tile18, tile19, tile20;
    @FXML private Rectangle tile21, tile22, tile23, tile24, tile25, tile26, tile27, tile28, tile29, tile30;
    @FXML private Rectangle tile31, tile32, tile33, tile34, tile35;
    @FXML private Text progressionPion1, progressionPion2, progressionPion3, progressionPion4;

    private Map<Integer, Node> tileMap = new HashMap<>();
    private Glow glowEffect = new Glow(0.8);

    public void setPlayers(List<PlayerConfig> players) {
        this.players = players;
        this.game = new Game(players);
        this.currentPlayer = 0;
    }
    
    // Vecteurs des positions pour chaque pion
    private List<List<Double>> path1 = new ArrayList<>();
    private List<List<Double>> path2 = new ArrayList<>();
    private List<List<Double>> path3 = new ArrayList<>();
    private List<List<Double>> path4 = new ArrayList<>();

    @FXML
    public void initialize() {
        // Initialisation des chemins de chaque pion
        path1 = createPath1();
        path2 = createPath2();
        path3 = createPath3();
        path4 = createPath4();
        
        initializeTileMap();

        // Initialiser les positions de départ des pions
        positionPion(pion1, path1.get(0));
        positionPion(pion2, path2.get(0));
        positionPion(pion3, path3.get(0));
        positionPion(pion4, path4.get(0));
    }
    
    
    private void highlightPion(Circle pion) {
        pion.setEffect(glowEffect);  
    }

    
    private void removeGlowEffect(Circle pion) {
        pion.setEffect(null);  
    }
    
    private String getThemeForPosition(int position) {
        // Les cases start (position 0 et dernière position) donnent un thème aléatoire
        if (position == 0 || position == path1.size() - 1) {
            String[] randomThemes = {"Education", "Entertainment", "Improbable", "Informatics"};
            return randomThemes[new Random().nextInt(randomThemes.length)];
        }
        
        // Pour les autres positions, on récupère la tile correspondante
        Node tile = tileMap.get(position + 1); // +1 car les tiles commencent à 1
        if (tile != null) {
            // Parcourir toutes les classes CSS de la tile
            for (String styleClass : tile.getStyleClass()) {
                // Vérifier si la classe correspond à un thème connu
                if (styleClass.equals("Education") || 
                    styleClass.equals("Entertainment") ||
                    styleClass.equals("Improbable") || 
                    styleClass.equals("Informatics")) {
                    return styleClass;
                }
            }
        }
        return "Education"; // Thème par défaut
    }
    
    private void initializeTileMap() {
        tileMap.put(1, tile1);
        tileMap.put(2, tile2);
        tileMap.put(3, tile3);
        tileMap.put(4, tile4);
        tileMap.put(5, tile5);
        tileMap.put(6, tile6);
        tileMap.put(7, tile7);
        tileMap.put(8, tile8);
        tileMap.put(9, tile9);
        tileMap.put(10, tile10);
        tileMap.put(11, tile11);
        tileMap.put(12, tile12);
        tileMap.put(13, tile13);
        tileMap.put(14, tile14);
        tileMap.put(15, tile15);
        tileMap.put(16, tile16);
        tileMap.put(17, tile17);
        tileMap.put(18, tile18);
        tileMap.put(19, tile19);
        tileMap.put(20, tile20);
        tileMap.put(21, tile21);
        tileMap.put(22, tile22);
        tileMap.put(23, tile23);
        tileMap.put(24, tile24);
        tileMap.put(25, tile25);
        tileMap.put(26, tile26);
        tileMap.put(27, tile27);
        tileMap.put(28, tile28);
        tileMap.put(29, tile29);
        tileMap.put(30, tile30);
        tileMap.put(31, tile31);
        tileMap.put(32, tile32);
        tileMap.put(33, tile33);
        tileMap.put(34, tile34);
        tileMap.put(35, tile35);
    }
    
    public void initializePion() {
    	int nbPlayers = players.size();
    	if(nbPlayers < 4) pion4.setVisible(false);
    	if(nbPlayers < 3) pion3.setVisible(false);
    	if(nbPlayers < 2) pion2.setVisible(false);
    }

    // Créer les chemins pour chaque pion (chemin 1 à 4)
    private List<List<Double>> createPath1() {
        List<List<Double>> path = new ArrayList<>();
        path.add(List.of(-16.0, 160.0));
        path.add(List.of(94.0, 176.0));
        path.add(List.of(184.0, 176.0));
        path.add(List.of(274.0, 176.0));
        path.add(List.of(364.0, 176.0));
        path.add(List.of(454.0, 176.0));
        path.add(List.of(544.0, 176.0));
        path.add(List.of(634.0, 176.0));
        path.add(List.of(634.0, 271.0));
        path.add(List.of(634.0, 366.0));
        path.add(List.of(544.0, 366.0));
        path.add(List.of(454.0, 366.0));
        path.add(List.of(364.0, 366.0));
        path.add(List.of(274.0, 366.0));
        path.add(List.of(184.0, 366.0));
        path.add(List.of(94.0, 366.0));
        path.add(List.of(4.0, 366.0));
        path.add(List.of(4.0, 456.0));
        path.add(List.of(4.0, 546.0));
        path.add(List.of(94.0, 546.0));
        path.add(List.of(184.0, 546.0));
        path.add(List.of(274.0, 546.0));
        path.add(List.of(364.0, 546.0));
        path.add(List.of(454.0, 546.0));
        path.add(List.of(544.0, 546.0));
        path.add(List.of(634.0, 546.0));
        path.add(List.of(634.0, 636.0));
        path.add(List.of(634.0, 726.0));
        path.add(List.of(544.0, 726.0));
        path.add(List.of(454.0, 726.0));
        path.add(List.of(364.0, 726.0));
        path.add(List.of(274.0, 726.0));
        path.add(List.of(184.0, 726.0));
        path.add(List.of(94.0, 726.0));
        path.add(List.of(14.0, 712.0));
        return path;
    }

    private List<List<Double>> createPath2() {
        List<List<Double>> path = new ArrayList<>();
        path.add(List.of(-105.0, 199.0));
        path.add(List.of(20.0, 199.0));
        path.add(List.of(110.0, 199.0));
        path.add(List.of(200.0, 199.0));
        path.add(List.of(290.0, 199.0));
        path.add(List.of(380.0, 199.0));
        path.add(List.of(470.0, 199.0));
        path.add(List.of(560.0, 199.0));
        path.add(List.of(560.0, 295.0));
        path.add(List.of(560.0, 390.0));
        path.add(List.of(470.0, 390.0));
        path.add(List.of(380.0, 390.0));
        path.add(List.of(290.0, 390.0));
        path.add(List.of(200.0, 390.0));
        path.add(List.of(110.0, 390.0));
        path.add(List.of(20.0, 390.0));
        path.add(List.of(-70.0, 390.0));
        path.add(List.of(-70.0, 480.0));
        path.add(List.of(-70.0, 570.0));
        path.add(List.of(20.0, 570.0));
        path.add(List.of(110.0, 570.0));
        path.add(List.of(200.0, 570.0));
        path.add(List.of(290.0, 570.0));
        path.add(List.of(380.0, 570.0));
        path.add(List.of(470.0, 570.0));
        path.add(List.of(560.0, 570.0));
        path.add(List.of(560.0, 660.0));
        path.add(List.of(560.0, 750.0));
        path.add(List.of(470.0, 750.0));
        path.add(List.of(380.0, 750.0));
        path.add(List.of(290.0, 750.0));
        path.add(List.of(200.0, 750.0));
        path.add(List.of(110.0, 750.0));
        path.add(List.of(20.0, 750.0));
        path.add(List.of(-105.0, 750.0));
        return path;
    }


    private List<List<Double>> createPath3() {
        List<List<Double>> path = new ArrayList<>();
        path.add(List.of(-16.0, 190.0));
        path.add(List.of(94.0, 174.0));
        path.add(List.of(184.0, 174.0));
        path.add(List.of(274.0, 174.0));
        path.add(List.of(364.0, 174.0));
        path.add(List.of(454.0, 174.0));
        path.add(List.of(544.0, 174.0));
        path.add(List.of(634.0, 174.0));
        path.add(List.of(634.0, 269.0));
        path.add(List.of(634.0, 364.0));
        path.add(List.of(544.0, 364.0));
        path.add(List.of(454.0, 364.0));
        path.add(List.of(364.0, 364.0));
        path.add(List.of(274.0, 364.0));
        path.add(List.of(184.0, 364.0));
        path.add(List.of(94.0, 364.0));
        path.add(List.of(4.0, 364.0));
        path.add(List.of(4.0, 454.0));
        path.add(List.of(4.0, 544.0));
        path.add(List.of(94.0, 544.0));
        path.add(List.of(184.0, 544.0));
        path.add(List.of(274.0, 544.0));
        path.add(List.of(364.0, 544.0));
        path.add(List.of(454.0, 544.0));
        path.add(List.of(544.0, 544.0));
        path.add(List.of(634.0, 544.0));
        path.add(List.of(634.0, 634.0));  
        path.add(List.of(634.0, 724.0));   
        path.add(List.of(544.0, 724.0));  
        path.add(List.of(454.0, 724.0));  
        path.add(List.of(364.0, 724.0));  
        path.add(List.of(274.0, 724.0));  
        path.add(List.of(184.0, 724.0));   
        path.add(List.of(94.0, 724.0));   
        path.add(List.of(-16.0, 742.0));
        return path;
    }

    private List<List<Double>> createPath4() {
        List<List<Double>> path = new ArrayList<>();
        path.add(List.of(-26.0, 150.0));
        path.add(List.of(68.0, 150.0));
        path.add(List.of(158.0, 150.0));
        path.add(List.of(248.0, 150.0));
        path.add(List.of(338.0, 150.0));
        path.add(List.of(428.0, 150.0));
        path.add(List.of(518.0, 150.0));
        path.add(List.of(608.0, 150.0));
        path.add(List.of(608.0, 245.0));
        path.add(List.of(608.0, 340.0));
        path.add(List.of(518.0, 340.0));
        path.add(List.of(428.0, 340.0));
        path.add(List.of(338.0, 340.0));
        path.add(List.of(248.0, 340.0));
        path.add(List.of(158.0, 340.0));
        path.add(List.of(68.0, 340.0));
        path.add(List.of(-22.0, 340.0));
        path.add(List.of(-22.0, 430.0));
        path.add(List.of(-22.0, 520.0));
        path.add(List.of(68.0, 520.0));
        path.add(List.of(158.0, 520.0));
        path.add(List.of(248.0, 520.0));
        path.add(List.of(338.0, 520.0));
        path.add(List.of(428.0, 520.0));
        path.add(List.of(518.0, 520.0));
        path.add(List.of(608.0, 520.0));
        path.add(List.of(608.0, 610.0));
        path.add(List.of(608.0, 700.0));
        path.add(List.of(518.0, 700.0));
        path.add(List.of(428.0, 700.0));
        path.add(List.of(338.0, 700.0));
        path.add(List.of(248.0, 700.0));
        path.add(List.of(158.0, 700.0));
        path.add(List.of(58.0, 700.0));
        path.add(List.of(-30.0, 700.0));
        return path;
    }
    // Positionner les pions
    private void positionPion(Circle pion, List<Double> position) {
        pion.setLayoutX(position.get(0));
        pion.setLayoutY(position.get(1));
    }


    // Méthode pour déplacer un pion d'une case à la suivante
    public void movePions(int pionIndex, int steps) {
        // Enlève l'effet lumineux sur tous les pions
        removeGlowEffect(pion1b);
        removeGlowEffect(pion2b);
        removeGlowEffect(pion3b);
        removeGlowEffect(pion4b);

        PlayerConfig player = players.get(pionIndex);
        // Avance d'une case
        int pos = player.getPosition() + steps;
        Circle pion = getPionCircle(pionIndex);
        
        List<List<Double>> path = getPathForPion(pionIndex);
        if (pos < path.size() && pos > 0) {
            player.move(steps);
            positionPion(pion, path.get(player.getPosition()));
        } else if(pos >= path.size()) {
        	player.setPosition(game.MAX_POSITION-1);
        	positionPion(pion, path.get(player.getPosition()));
        } else if(pos <= 0) {
        	player.setPosition(0);
        	positionPion(pion, path.get(player.getPosition()));
        }
        
        // Remet l'effet lumineux sur le bon bouton
        highlightPion(getPionHighlight(pionIndex));

        // Met à jour le texte de progression
        updateProgressionText(pionIndex, pos);
    }

    // Petite méthode utilitaire pour récupérer le bon chemin
    private List<List<Double>> getPathForPion(int pionIndex) {
        switch (pionIndex) {
            case 0: return path1;
            case 1: return path2;
            case 2: return path3;
            case 3: return path4;
            default: throw new IllegalArgumentException("Invalid pion index");
        }
    }

    // Petite méthode utilitaire pour récupérer l'ImageView du pion
    private Circle getPionCircle(int pionIndex) {
        switch (pionIndex) {
            case 0: return pion1;
            case 1: return pion2;
            case 2: return pion3;
            case 3: return pion4;
            default: throw new IllegalArgumentException("Invalid pion index");
        }
    }

    // Petite méthode utilitaire pour récupérer le bouton du pion
    private Circle getPionHighlight(int pionIndex) {
        switch (pionIndex) {
            case 0: return pion1b;
            case 1: return pion2b;
            case 2: return pion3b;
            case 3: return pion4b;
            default: throw new IllegalArgumentException("Invalid pion index");
        }
    }

    // Petite méthode utilitaire pour mettre à jour le texte de progression
    private void updateProgressionText(int pionIndex, int pos) {
        switch (pionIndex) {
            case 0: progressionPion1.setText(String.valueOf(pos)); break;
            case 1: progressionPion2.setText(String.valueOf(pos)); break;
            case 2: progressionPion3.setText(String.valueOf(pos)); break;
            case 3: progressionPion4.setText(String.valueOf(pos)); break;
            default: throw new IllegalArgumentException("Invalid pion index");
        }
    }

    
    @FXML
    public void movePion() {
        if (!TirageCarte.isUsed()) {
            if (!game.isFinished()) {
                int playerIndex = currentPlayer;
                int currentPosition = players.get(playerIndex).getPosition();

                String theme = getThemeForPosition(currentPosition);
                TirageCarte.setThemeSelected(theme);
                TirageCarte.setUsed(true);

                try {
                    // Chargement du FXML et récupération du vrai contrôleur
                    FXMLLoader loader = new FXMLLoader(TirageCarte.class.getResource("/views/card.fxml"));
                    Parent root = loader.load();
                    TirageCarte controller = loader.getController();

                    Stage stage = new Stage();
                    stage.setTitle("Carte - Thème : " + theme);
                    Scene scene = new Scene(root);
                    stage.setScene(scene);

                    controller.setStage(stage); // Important pour que getStage() fonctionne
                    controller.applyTheme(theme);
                    stage.setResizable(false);
                    stage.show();

                    // Quand la fenêtre se ferme, on agit
                    stage.setOnHidden(e -> {
                        Platform.runLater(() -> {
                            Question q = controller.getCurrentQuestion();
                            if (q != null) {
                            	if(controller.getCorrectAnswer())
                            		movePions(playerIndex, q.getPriority());
                            	else {
                            		movePions(playerIndex, - q.getPriority());
                            	}
                            }

                            updateProgressionText(playerIndex, players.get(playerIndex).getPosition());
                            currentPlayer = (currentPlayer + 1) % players.size();
                            game.verifyEndGame();
                            TirageCarte.setUsed(false);
                        });
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                GameMenu gameMenu = new GameMenu();
                gameMenu.start(new Stage());
            }
        }
    }




}

