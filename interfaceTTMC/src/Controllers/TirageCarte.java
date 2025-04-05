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
import factories.CardFactory;

public class TirageCarte extends Application {

    private List<Card> cards;
    private Card currentCard;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 🔧 Lecture JSON brut + Factory Method
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> rawCards = mapper.readValue(
            new File("resources/question.json"),
            new TypeReference<List<Map<String, Object>>>() {}
        );

        cards = rawCards.stream().map(data -> {
            String themeName = (String) data.get("theme_name");
            int priority = ((Number) data.get("priority")).intValue();
            String question = (String) data.get("question");
            String answer = (String) data.get("answer");
            List<String> multipleChoice = (List<String>) data.get("multiple_choice");

            return CardFactory.createCard(themeName, priority, question, answer, multipleChoice);
        }).collect(Collectors.toList());

        // 🎛️ Interface
        ComboBox<String> themeSelector = new ComboBox<>();
        themeSelector.getItems().addAll("Improbable", "Entertainment", "Education", "Informatics");

        ComboBox<String> difficultySelector = new ComboBox<>();
        difficultySelector.getItems().addAll("Toutes", "1", "2", "3", "4");
        difficultySelector.setValue("Toutes");

        Label questionLabel = new Label();
        Label answerLabel = new Label("✔️ Réponse : ???");
        answerLabel.setVisible(false);

        ListView<String> choicesList = new ListView<>();

        Button drawButton = new Button("Tirer une carte");
        Button validerButton = new Button("Valider");
        validerButton.setDisable(true);

        drawButton.setOnAction(e -> {
            String selectedTheme = themeSelector.getValue();
            String selectedPriority = difficultySelector.getValue();

            if (selectedTheme != null) {
                List<Card> filtered = cards.stream()
                        .filter(card -> card.getTheme_name().equalsIgnoreCase(selectedTheme))
                        .filter(card -> selectedPriority.equals("Toutes") ||
                                String.valueOf(card.getPriority()).equals(selectedPriority))
                        .collect(Collectors.toList());

                if (!filtered.isEmpty()) {
                    Collections.shuffle(filtered);
                    currentCard = filtered.get(0);

                    questionLabel.setText("❓ " + currentCard.getQuestion());
                    choicesList.getItems().setAll(currentCard.getMultiple_choice());
                    answerLabel.setText("✔️ Réponse : ???");
                    answerLabel.setVisible(false);
                    validerButton.setDisable(false);
                } else {
                    questionLabel.setText("Aucune carte trouvée pour ce thème et cette difficulté.");
                    choicesList.getItems().clear();
                    answerLabel.setVisible(false);
                    validerButton.setDisable(true);
                }
            }
        });

        validerButton.setOnAction(e -> {
            if (currentCard != null) {
                answerLabel.setText("✔️ Réponse : " + currentCard.getAnswer());
                answerLabel.setVisible(true);
                validerButton.setDisable(true);
            }
        });

        VBox layout = new VBox(10, themeSelector, difficultySelector, drawButton, questionLabel, choicesList, validerButton, answerLabel);
        layout.setStyle("-fx-padding: 20; -fx-font-size: 14px;");

        primaryStage.setScene(new Scene(layout, 600, 450));
        primaryStage.setTitle("Sélecteur de Cartes par Thème et Difficulté");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
