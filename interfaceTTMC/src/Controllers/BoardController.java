package Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Game;
import models.PlayerConfig;
import models.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import Views.RankingMenu;

public class BoardController {

    private List<PlayerConfig> players;
    @FXML
    private Pane pion1b, pion2b, pion3b, pion4b;
    @FXML private Pane pionContainer1, pionContainer2, pionContainer3, pionContainer4;
    private Game game;
    private int currentPlayer;
    
    @FXML private Button moveButton;
    @FXML private Rectangle tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8, tile9, tile10;
    @FXML private Rectangle tile11, tile12, tile13, tile14, tile15, tile16, tile17, tile18, tile19, tile20;
    @FXML private Rectangle tile21, tile22, tile23, tile24, tile25, tile26, tile27, tile28, tile29, tile30;
    @FXML private Rectangle tile31, tile32, tile33, tile34, tile35;
    @FXML private Text progressionPion1, progressionPion2, progressionPion3, progressionPion4;

    private Map<Integer, Node> tileMap = new HashMap<>();
  

    public void setPlayers(List<PlayerConfig> players) {
        this.players = players;
        this.game = new Game(players);
        this.currentPlayer = 0;
        moveButton.setText("Play : " + players.get(0).getPlayerName());
    }
    
    
    private List<List<Double>> path1 = new ArrayList<>();
    private List<List<Double>> path2 = new ArrayList<>();
    private List<List<Double>> path3 = new ArrayList<>();
    private List<List<Double>> path4 = new ArrayList<>();

    @FXML
    public void initialize() {
        // Init tile path
        path1 = createPath1();
        path2 = createPath2();
        path3 = createPath3();
        path4 = createPath4();
        
        initializeTileMap();
        
    }
    
    
    
    private String getThemeForPosition(int position) {
        // The start tiles (position 0 and last position) provide a random theme
        if (position == 0 || position == path1.size() - 1) {
            String[] randomThemes = {"Education", "Entertainment", "Improbable", "Informatics"};
            return randomThemes[new Random().nextInt(randomThemes.length)];
        }
        
        // For other positions, get the corresponding tile
        Node tile = tileMap.get(position + 1); // +1 because tiles start at index 1
        if (tile != null) {
            // Iterate through all CSS classes of the tile
            for (String styleClass : tile.getStyleClass()) {
                // Check if the class matches a known theme
                if (styleClass.equals("Education") || 
                    styleClass.equals("Entertainment") ||
                    styleClass.equals("Improbable") || 
                    styleClass.equals("Informatics")) {
                    return styleClass;
                }
            }
        }
        return "Education"; // Default theme
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
    
    public void initializePawnView(Pane pane, PlayerConfig player) {
        Shape shape = null;

        switch (player.getShapeIndex()) {
            case 0:
              
                Circle circle = new Circle(14);
                circle.setStroke(Color.WHITE);
                circle.setStrokeWidth(2);
                circle.setCenterX(30);  
                circle.setCenterY(30);  
                shape = circle;
                break;

            case 1:
               
                Rectangle rectangle = new Rectangle(25, 25);
                rectangle.setStroke(Color.WHITE);
                rectangle.setStrokeWidth(2);
                rectangle.setX(17.5);  
                rectangle.setY(17.5);  
                shape = rectangle;
                break;

            case 2:
                
                Polygon triangle = new Polygon();
                triangle.getPoints().addAll(
                    30.0, 10.0,   
                    45.0, 40.0,   
                    15.0, 40.0    
                );
                triangle.setStroke(Color.WHITE);
                triangle.setStrokeWidth(2);
                shape = triangle;
                break;
        }

      
        switch(player.getColorIndex()) {
            case 0: shape.setFill(Color.RED); break;
            case 1: shape.setFill(Color.GREEN); break;
            case 2: shape.setFill(Color.BLUE); break;
            default: shape.setFill(Color.YELLOW); break;
        }

       
        if (shape != null) {
            pane.getChildren().clear();
            pane.getChildren().add(shape);
        }
    }

    
    public void initializePion() {	
    	int nbPlayers = players.size();
    	if(nbPlayers == 4) {
        	initializePawnView(pionContainer4, players.get(3));
        	initializePawnView(pion4b, players.get(3));
        }
        if(nbPlayers >= 3) {
        	initializePawnView(pionContainer3, players.get(2));
        	initializePawnView(pion3b, players.get(2));
        }
        if(nbPlayers >= 2) {
        	initializePawnView(pionContainer2, players.get(1));
        	initializePawnView(pion2b, players.get(1));
        }
        initializePawnView(pionContainer1, players.get(0));
        initializePawnView(pion1b, players.get(0));
        
        positionPion(pionContainer1, path1.get(0));
        positionPion(pionContainer2, path2.get(0));
        positionPion(pionContainer3, path3.get(0));
        positionPion(pionContainer4, path4.get(0));
        
    	if(nbPlayers < 4) {
    		pionContainer4.setVisible(false);
    		pion4b.setVisible(false);
    		progressionPion4.setVisible(false);
    		
    	}
    	if(nbPlayers < 3) {
    		pionContainer3.setVisible(false);
    		pion3b.setVisible(false);
    		progressionPion3.setVisible(false);
    	}
    	if(nbPlayers < 2) {
    		pionContainer2.setVisible(false);
    		pion2b.setVisible(false);
    		progressionPion2.setVisible(false);
    	}
    }

   
    private List<List<Double>> createPath1() {
        List<List<Double>> path = new ArrayList<>();
        path.add(List.of(-14.0, 160.0));
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
        path.add(List.of(-55.0, 200.0));  // point 1
        path.add(List.of(70.0, 199.0));   // point 2
        path.add(List.of(160.0, 199.0));  // point 3
        path.add(List.of(250.0, 199.0));
        path.add(List.of(340.0, 199.0));
        path.add(List.of(430.0, 199.0));
        path.add(List.of(520.0, 199.0));
        path.add(List.of(610.0, 199.0));
        path.add(List.of(610.0, 294.0));
        path.add(List.of(610.0, 389.0));
        path.add(List.of(520.0, 389.0));
        path.add(List.of(430.0, 389.0));
        path.add(List.of(340.0, 389.0));
        path.add(List.of(250.0, 389.0));
        path.add(List.of(160.0, 389.0));
        path.add(List.of(70.0, 389.0));
        path.add(List.of(-20.0, 389.0));
        path.add(List.of(-20.0, 479.0));
        path.add(List.of(-20.0, 569.0));
        path.add(List.of(70.0, 569.0));
        path.add(List.of(160.0, 569.0));
        path.add(List.of(250.0, 569.0));
        path.add(List.of(340.0, 569.0));
        path.add(List.of(430.0, 569.0));
        path.add(List.of(520.0, 569.0));
        path.add(List.of(610.0, 569.0));
        path.add(List.of(610.0, 659.0));
        path.add(List.of(610.0, 749.0));
        path.add(List.of(520.0, 749.0));
        path.add(List.of(430.0, 749.0));
        path.add(List.of(340.0, 749.0));
        path.add(List.of(250.0, 749.0));
        path.add(List.of(160.0, 749.0));
        path.add(List.of(70.0, 749.0));
        path.add(List.of(-10.0, 735.0));
        return path;
    }

    private List<List<Double>> createPath3() {
        List<List<Double>> path = new ArrayList<>();
        path.add(List.of(-14.0, 235.0));  // point 1
        path.add(List.of(94.0, 225.0));   // point 2
        path.add(List.of(184.0, 225.0));  // point 3
        path.add(List.of(274.0, 225.0));
        path.add(List.of(364.0, 225.0));
        path.add(List.of(454.0, 225.0));
        path.add(List.of(544.0, 225.0));
        path.add(List.of(634.0, 225.0));
        path.add(List.of(634.0, 320.0));
        path.add(List.of(634.0, 415.0));
        path.add(List.of(544.0, 415.0));
        path.add(List.of(454.0, 415.0));
        path.add(List.of(364.0, 415.0));
        path.add(List.of(274.0, 415.0));
        path.add(List.of(184.0, 415.0));
        path.add(List.of(94.0, 415.0));
        path.add(List.of(4.0, 415.0));
        path.add(List.of(4.0, 505.0));
        path.add(List.of(4.0, 595.0));
        path.add(List.of(94.0, 595.0));
        path.add(List.of(184.0, 595.0));
        path.add(List.of(274.0, 595.0));
        path.add(List.of(364.0, 595.0));
        path.add(List.of(454.0, 595.0));
        path.add(List.of(544.0, 595.0));
        path.add(List.of(634.0, 595.0));
        path.add(List.of(634.0, 685.0));
        path.add(List.of(634.0, 775.0));
        path.add(List.of(544.0, 775.0));
        path.add(List.of(454.0, 775.0));
        path.add(List.of(364.0, 775.0));
        path.add(List.of(274.0, 775.0));
        path.add(List.of(184.0, 775.0));
        path.add(List.of(94.0, 775.0));
        path.add(List.of(-14.0, 761.0));
        return path;
    }



    private List<List<Double>> createPath4() {
        List<List<Double>> path = new ArrayList<>();
        path.add(List.of(20.0, 199.0));   // point 1
        path.add(List.of(119.0, 199.0));  // point 2
        path.add(List.of(209.0, 199.0));  // point 3
        path.add(List.of(299.0, 199.0));
        path.add(List.of(389.0, 199.0));
        path.add(List.of(479.0, 199.0));
        path.add(List.of(569.0, 199.0));
        path.add(List.of(659.0, 199.0));
        path.add(List.of(659.0, 294.0));
        path.add(List.of(659.0, 389.0));
        path.add(List.of(569.0, 389.0));
        path.add(List.of(479.0, 389.0));
        path.add(List.of(389.0, 389.0));
        path.add(List.of(299.0, 389.0));
        path.add(List.of(209.0, 389.0));
        path.add(List.of(119.0, 389.0));
        path.add(List.of(29.0, 389.0));
        path.add(List.of(29.0, 479.0));
        path.add(List.of(29.0, 569.0));
        path.add(List.of(119.0, 569.0));
        path.add(List.of(209.0, 569.0));
        path.add(List.of(299.0, 569.0));
        path.add(List.of(389.0, 569.0));
        path.add(List.of(479.0, 569.0));
        path.add(List.of(569.0, 569.0));
        path.add(List.of(659.0, 569.0));
        path.add(List.of(659.0, 659.0));
        path.add(List.of(659.0, 749.0));
        path.add(List.of(569.0, 749.0));
        path.add(List.of(479.0, 749.0));
        path.add(List.of(389.0, 749.0));
        path.add(List.of(299.0, 749.0));
        path.add(List.of(209.0, 749.0));
        path.add(List.of(119.0, 749.0));
        path.add(List.of(20.0, 735.0));
        return path;
    }

    // Pos tile
    private void positionPion(Pane pion, List<Double> position) {
        pion.setLayoutX(position.get(0));
        pion.setLayoutY(position.get(1));
    }


    // Method to deplace tile
    public void movePions(int pionIndex, int steps) {
      

        PlayerConfig player = players.get(pionIndex);
       
        int pos = player.getPosition() + steps;
        Pane pion = getPionCircle(pionIndex);
        
        List<List<Double>> path = getPathForPion(pionIndex);
        if (pos < path.size() && pos >= 0) {
            player.move(steps);
            positionPion(pion, path.get(player.getPosition()));
        } else if(pos < 0) {
        	player.setPosition(0);
        	positionPion(pion, path.get(player.getPosition()));
        } else if(pos >= path.size()) {
        	player.setPosition(game.MAX_POSITION-1);
        	positionPion(pion, path.get(player.getPosition()));
        }
        
        

        // update progression text
        updateProgressionText(pionIndex, pos);
    }

 
    private List<List<Double>> getPathForPion(int pionIndex) {
        switch (pionIndex) {
            case 0: return path1;
            case 1: return path2;
            case 2: return path3;
            case 3: return path4;
            default: throw new IllegalArgumentException("Invalid pion index");
        }
    }

   
    private Pane getPionCircle(int pionIndex) {
        switch (pionIndex) {
            case 0: return pionContainer1;
            case 1: return pionContainer2;
            case 2: return pionContainer3;
            case 3: return pionContainer4;
            default: throw new IllegalArgumentException("Invalid pion index");
        }
    }

 

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
                   
                    FXMLLoader loader = new FXMLLoader(TirageCarte.class.getResource("/views/card.fxml"));
                    Parent root = loader.load();
                    TirageCarte controller = loader.getController();

                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.UNDECORATED);
                    
                    Stage mainStage = (Stage) pionContainer1.getScene().getWindow();
                    stage.initOwner(mainStage);
                    stage.setAlwaysOnTop(true); 
                    
                    stage.setTitle("Carte - Thème : " + theme);
                    Scene scene = new Scene(root);
                    stage.setScene(scene);

                    controller.setStage(stage); 
                    controller.applyTheme(theme);
                    stage.setResizable(false);
                    stage.show();
                    stage.toFront();

                   
                    stage.setOnHidden(e -> {
                        Platform.runLater(() -> {
                            Question q = controller.getCurrentQuestion();
                            if (q != null) {
                            	if(controller.isCorrectAnswer())
                            		movePions(playerIndex, q.getPriority());
                            	else {
                            		movePions(playerIndex, - q.getPriority());
                            	}
                            }

                            updateProgressionText(playerIndex, players.get(playerIndex).getPosition());
                            do {
                            	currentPlayer = (currentPlayer + 1) % players.size();
                            }while(players.get(currentPlayer).isFinished());
                            
                            game.verifyEndGame();
                            TirageCarte.setUsed(false);
                            moveButton.setText("Play : " + players.get(currentPlayer).getPlayerName());
                            if (game.isFinished()) {
                                ((Stage) pionContainer1.getScene().getWindow()).close();
                                RankingMenu rankingMenu = new RankingMenu();
                                rankingMenu.setPlayers(players);
                                rankingMenu.start(new Stage());
                            }

                        });
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}