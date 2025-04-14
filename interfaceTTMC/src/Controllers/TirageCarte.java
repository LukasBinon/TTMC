package Controllers;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Card;
import models.CardEducation;
import models.CardEntertainment;
import models.Question;
import factories.*;

public class TirageCarte extends Application {

    private List<Question> questions;
    private Question currentQuestion;
    private Card card;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 🔧 Lecture JSON brut
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

        // Création de la carte
        String selectedThemeTest = "Entertainment";
        
        CardFactory factory = switch (selectedThemeTest.toLowerCase()) {
            case "education" -> new EducationCardFactory();
            case "entertainment" -> new EntertainmentCardFactory();
            case "improbable" -> new ImprobableCardFactory();
            case "informatics" -> new InformaticCardFactory();
            default -> throw new IllegalArgumentException("Thème inconnu : " + selectedThemeTest);
        };

        card = factory.createCard(questions);
        
        System.out.println(card.toString());
        
        // 🎛️ Interface

        ComboBox<String> difficultySelector = new ComboBox<>();
        difficultySelector.getItems().addAll("1", "2", "3", "4");
        difficultySelector.setValue("1");

        Label questionLabel = new Label();
        Label answerLabel = new Label("✔️ Réponse : ???");
        answerLabel.setVisible(false);

        ListView<String> choicesList = new ListView<>();

        Button drawButton = new Button("Tirer une question");
        Button validerButton = new Button("Valider");
        validerButton.setDisable(true);

        drawButton.setOnAction(e -> {
            String selectedPriority = difficultySelector.getValue();

            currentQuestion = card.getQuestions().get(Integer.parseInt(selectedPriority)-1);
            System.out.println(currentQuestion);

            questionLabel.setText("❓ " + currentQuestion.getQuestion());
            choicesList.getItems().setAll(currentQuestion.getMultipleChoice());
            answerLabel.setText("✔️ Réponse : ???");
            answerLabel.setVisible(false);
            validerButton.setDisable(false);
            drawButton.setDisable(true);
            drawButton.setVisible(true);

        });

        validerButton.setOnAction(e -> {
            if (currentQuestion != null) {
                answerLabel.setText("✔️ Réponse : " + currentQuestion.getAnswer());
                answerLabel.setVisible(true);
                validerButton.setDisable(true);
            }
        });

        VBox layout = new VBox(10, difficultySelector, drawButton, questionLabel, choicesList, validerButton, answerLabel);
        layout.setStyle("-fx-padding: 20; -fx-font-size: 14px;");

        primaryStage.setScene(new Scene(layout, 600, 450));
        primaryStage.setTitle("Sélecteur de Cartes par Thème et Difficulté");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
