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
    private Label cardTitle;
    @FXML
    private VBox rootPane; 

    private List<Question> questions;
    private Question currentQuestion;
    private Card card;
    private static String selectedTheme = "Education";
    private static boolean used = false;
    private boolean correctAnswer = false;
    private Stage stage;

    public static boolean isUsed() {
		return used;
	}

	public static void setUsed(boolean used) {
		TirageCarte.used = used;
	}

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
            questionLabel.prefWidthProperty().bind(rootPane.widthProperty());
            
            cardTitle.setText(selectedTheme);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
    public void applyTheme(String theme) {
        String cssPath = "/views/" + theme.toLowerCase() + ".css";  // Charger le CSS depuis views/ plutôt que styles/
		Scene scene = rootPane.getScene();  // Récupère la scène de la vue
        if (scene != null) {
            scene.getStylesheets().clear();  // Supprimer tous les styles CSS actuels
            scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());  // Ajouter le CSS pour le thème actuel
        }
    }
    
    public void start() {
        try {
            FXMLLoader loader = new FXMLLoader(TirageCarte.class.getResource("/views/card.fxml"));
            Parent root = loader.load();

            stage = new Stage();
            stage.setTitle("Carte - Thème : " + selectedTheme);
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Appliquer le thème CSS
            TirageCarte controller = loader.getController(); 
            controller.applyTheme(selectedTheme); 
            stage.setResizable(false);

            setUsed(true);
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
        difficultySelector.setDisable(true);
        drawButton.setDisable(true);
    }

    @FXML
    private void handleValidateButton() {
        if (currentQuestion != null) {
            String selectedChoice = choicesList.getSelectionModel().getSelectedItem(); // ← valeur sélectionnée

            if (selectedChoice != null && selectedChoice.equals(currentQuestion.getAnswer())) {
                correctAnswer = true;
                answerLabel.setText("✔️ Bonne réponse !");
            } else {
                correctAnswer = false;
                answerLabel.setText("❌ Mauvaise réponse. La bonne réponse était : " + currentQuestion.getAnswer());
            }

            answerLabel.setVisible(true);
            validerButton.setDisable(true);
            drawButton.setDisable(true);
            setUsed(false);
        }
    }


    public static void setThemeSelected(String theme) {
        selectedTheme = theme;
    }
    
    public Stage getStage() {
    	return stage;
    }
    
    public void setStage(Stage stage) {
    	this.stage = stage;
    }
    
    public Question getCurrentQuestion() {
    	return currentQuestion;
    }
    
    public boolean getCorrectAnswer() {
    	return correctAnswer;
    }
}
