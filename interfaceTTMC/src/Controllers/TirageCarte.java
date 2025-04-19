package Controllers;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import factories.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Card;
import models.Question;

public class TirageCarte {

    @FXML
    private ComboBox<String> difficultySelector;
    @FXML
    private Button drawButton;
    @FXML
    private Button validerButton;
    @FXML
    private Label questionLabel;
    @FXML
    private Label answerLabel;
    @FXML
    private ListView<String> choicesList;
    
    @FXML
    private VBox rootPane; 

    private List<Question> questions;
    private Question currentQuestion;
    private Card card;
    private static String selectedTheme = "Education";

    @FXML
    public void initialize() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> rawCards = mapper.readValue(
                new File("resources/question.json"),
                new TypeReference<List<Map<String, Object>>>() {}
            );

            questions = rawCards.stream().map(data -> {
                String themeName = (String) data.get("theme_name");
                int priority = ((Number) data.get("priority")).intValue();
                String question = (String) data.get("question");
                String answer = (String) data.get("answer");
                List<String> multipleChoice = (List<String>) data.get("multiple_choice");

                return new Question(themeName, priority, question, answer, multipleChoice);
            }).collect(Collectors.toList());

            CardFactory factory = switch (selectedTheme.toLowerCase()) {
                case "education" -> new EducationCardFactory();
                case "entertainment" -> new EntertainmentCardFactory();
                case "improbable" -> new ImprobableCardFactory();
                case "informatics" -> new InformaticCardFactory();
                default -> throw new IllegalArgumentException("Thème inconnu : " + selectedTheme);
            };

            card = factory.createCard(questions);

            difficultySelector.getItems().addAll("1", "2", "3", "4");
            difficultySelector.setValue("1");
            validerButton.setDisable(true);
            answerLabel.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
    private void applyTheme(String theme) {
        String cssPath = "/views/" + theme.toLowerCase() + ".css";  // Charger le CSS depuis views/ plutôt que styles/
		Scene scene = rootPane.getScene();  // Récupère la scène de la vue
        if (scene != null) {
            scene.getStylesheets().clear();  // Supprimer tous les styles CSS actuels
            scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());  // Ajouter le CSS pour le thème actuel
        }
    }
    
    public static void start() {
        try {
            FXMLLoader loader = new FXMLLoader(TirageCarte.class.getResource("/views/card.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Carte - Thème : " + selectedTheme);
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Appliquer le thème CSS
            TirageCarte controller = loader.getController(); 
            controller.applyTheme(selectedTheme);  

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleDrawButton() {
        String selectedPriority = difficultySelector.getValue();
        currentQuestion = card.getQuestions().get(Integer.parseInt(selectedPriority) - 1);

        questionLabel.setText("❓ " + currentQuestion.getQuestion());
        choicesList.getItems().setAll(currentQuestion.getMultipleChoice());
        answerLabel.setText("✔️ Réponse : ???");
        answerLabel.setVisible(false);
        validerButton.setDisable(false);
        drawButton.setDisable(true);
    }

    @FXML
    private void handleValidateButton() {
        if (currentQuestion != null) {
            answerLabel.setText("✔️ Réponse : " + currentQuestion.getAnswer());
            answerLabel.setVisible(true);
            validerButton.setDisable(true);
            drawButton.setDisable(false);
        }
    }

    public static void setThemeSelected(String theme) {
        selectedTheme = theme;
    }
}
