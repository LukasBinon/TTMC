package Controllers;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;


import java.util.List;
import java.util.ArrayList;

public class BoardController {

    @FXML
    private Circle pion1;
    @FXML
    private Circle pion2;
    @FXML
    private Circle pion3;
    @FXML
    private Circle pion4;


    // Vecteurs des positions pour chaque pion
    private List<List<Double>> path1 = new ArrayList<>();
    private List<List<Double>> path2 = new ArrayList<>();
    private List<List<Double>> path3 = new ArrayList<>();
    private List<List<Double>> path4 = new ArrayList<>();

    // Indices des positions actuelles des pions
    private int indexPion1 = 0;
    private int indexPion2 = 0;
    private int indexPion3 = 0;
    private int indexPion4 = 0;

    @FXML
    public void initialize() {
        // Initialisation des chemins de chaque pion
        path1 = createPath1();
        path2 = createPath2();
        path3 = createPath3();
        path4 = createPath4();

        // Initialiser les positions de départ des pions
        positionPion(pion1, path1.get(0));
        positionPion(pion2, path2.get(0));
        positionPion(pion3, path3.get(0));
        positionPion(pion4, path4.get(0));
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
    @FXML
    public void movePion1() {
        if (indexPion1 < path1.size() - 1) {
            indexPion1++;
            positionPion(pion1, path1.get(indexPion1));
        }
    }

    @FXML
    public void movePion2() {
        if (indexPion2 < path2.size() - 1) {
            indexPion2++;
            positionPion(pion2, path2.get(indexPion2));
        }
    }

    @FXML
    public void movePion3() {
        if (indexPion3 < path3.size() - 1) {
            indexPion3++;
            positionPion(pion3, path3.get(indexPion3));
        }
    }

    @FXML
    public void movePion4() {
        if (indexPion4 < path4.size() - 1) {
            indexPion4++;
            positionPion(pion4, path4.get(indexPion4));
        }
    }
    
    @FXML
    public void movePion() {
        // Déplace chaque pion vers sa position suivante, en fonction de son chemin
        movePion1();
        movePion2();
        movePion3();
        movePion4();
     
    }

}

