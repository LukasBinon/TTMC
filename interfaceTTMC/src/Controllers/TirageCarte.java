package Controllers;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import Exceptions.CardNotFoundException;
import factories.*;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Card;
import models.Question;

public class TirageCarte {

    @FXML
    private ComboBox<String> difficultySelector; // ComboBox for selecting difficulty
    @FXML
    private Button drawButton; // Button to draw a card
    @FXML
    private Button validerButton; // Button to validate the answer
    @FXML
    private Label questionLabel; // Label to display the question
    @FXML
    private Label answerLabel; // Label to display the answer
    @FXML
    private ListView<String> choicesList; // ListView to display multiple choices
    @FXML
    private Label cardTitle; // Label to display the card title
    @FXML
    private VBox rootPane; // Root pane for the scene layout

    private List<Question> questions; // List of questions
    private Question currentQuestion; // The current question
    private Card card; // The card object containing questions
    private static String selectedTheme = "Education"; // Default selected theme
    private static boolean used = false; // Flag to indicate if the card is used
    private boolean correctAnswer = false; // Flag to track if the answer is correct
    private Stage stage; // Stage to display the card window

    // Getter for the 'used' flag
    public static boolean isUsed() {
        return used;
    }

    // Setter for the 'used' flag
    public static void setUsed(boolean used) {
        TirageCarte.used = used;
    }

    // Initialize method for setting up the view and loading data
    @FXML
    public void initialize() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> rawCards = mapper.readValue(
                new File("resources/question.json"),
                new TypeReference<List<Map<String, Object>>>() {}
            );

            // Map raw card data to a list of Question objects
            questions = rawCards.stream().map(data -> {
                String themeName = (String) data.get("theme_name");
                int priority = ((Number) data.get("priority")).intValue();
                String question = (String) data.get("question");
                String answer = (String) data.get("answer");
                List<String> multipleChoice = (List<String>) data.get("multiple_choice");

                return new Question(themeName, priority, question, answer, multipleChoice);
            }).collect(Collectors.toList());

            // Select the appropriate factory based on the selected theme
            CardFactory factory = switch (selectedTheme.toLowerCase()) {
                case "education" -> new EducationCardFactory();
                case "entertainment" -> new EntertainmentCardFactory();
                case "improbable" -> new ImprobableCardFactory();
                case "informatics" -> new InformaticCardFactory();
                default -> throw new IllegalArgumentException("Unknown theme: " + selectedTheme);
            };

            // Create the card with the selected theme's factory
            card = factory.createCard(questions);

            // Initialize the difficulty selector and UI elements
            difficultySelector.getItems().addAll("1", "2", "3", "4");
            difficultySelector.setValue("1");
            validerButton.setDisable(true);
            answerLabel.setVisible(false);
            questionLabel.prefWidthProperty().bind(rootPane.widthProperty());

            // Set the card title to the selected theme
            cardTitle.setText(selectedTheme);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to apply the selected theme's CSS
    public void applyTheme(String theme) {
        String cssPath = "/views/" + theme.toLowerCase() + ".css";  // Load the CSS from the views folder
        Scene scene = rootPane.getScene();  // Get the scene of the view
        if (scene != null) {
            scene.getStylesheets().clear();  // Remove all current CSS styles
            scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());  // Add the current theme's CSS
        }
    }

    // Method to start the game by loading the card view
    public void start() {
        try {
            FXMLLoader loader = new FXMLLoader(TirageCarte.class.getResource("/views/card.fxml"));
            Parent root = loader.load();

            if (root == null) {
                throw new CardNotFoundException(); // Throw exception if card (file) not found
            }

            stage = new Stage();
            stage.setTitle("Card - Theme: " + selectedTheme);
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Apply the theme's CSS
            TirageCarte controller = loader.getController(); 
            controller.applyTheme(selectedTheme); 
            stage.setResizable(false);

            setUsed(true);
            stage.show();
        } catch (CardNotFoundException e) {
            System.out.println(e.getMessage()); // Display error message if card is not found
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace for IO exceptions
        }
    }

    // Handle the draw button click to display a question based on selected difficulty
    @FXML
    private void handleDrawButton() {
        try {
            String selectedPriority = difficultySelector.getValue();
            int index = Integer.parseInt(selectedPriority) - 1;

            // Validate the index for the question list
            if (index < 0 || index >= card.getQuestions().size()) {
                throw new CardNotFoundException(); // Throw exception if index is out of bounds
            }

            currentQuestion = card.getQuestions().get(index);

            // Display the question and choices
            questionLabel.setText("❓ " + currentQuestion.getQuestion());
            choicesList.getItems().setAll(currentQuestion.getMultipleChoice());
            answerLabel.setText("✔️ Answer: ???");
            answerLabel.setVisible(false);
            validerButton.setDisable(false);
            difficultySelector.setDisable(true);
            drawButton.setDisable(true);
        } catch (CardNotFoundException e) {
            System.out.println(e.getMessage()); // Display "Card not found" message
        } catch (NumberFormatException e) {
            System.out.println("Error: invalid priority!"); // Display error message for invalid priority
        }
    }

    // Handle the validate button click to check the answer
    @FXML
    private void handleValidateButton() {
        if (currentQuestion != null) {
            String selectedChoice = choicesList.getSelectionModel().getSelectedItem(); // Get the selected answer

            // Check if the selected answer is correct
            if (selectedChoice != null && selectedChoice.equals(currentQuestion.getAnswer())) {
                correctAnswer = true;
                answerLabel.setText("✔️ Nice!");
            } else {
                correctAnswer = false;
                answerLabel.setText("❌ Wrong!");
            }

            answerLabel.setVisible(true);
            validerButton.setDisable(true);
            drawButton.setDisable(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> {
                Stage stage = (Stage) validerButton.getScene().getWindow();
                stage.close(); // Close the card window after showing the result
            });
            pause.play();
        }
    }

    // Set the selected theme for the card
    public static void setThemeSelected(String theme) {
        selectedTheme = theme;
    }

    // Getter for the stage of the current card view
    public Stage getStage() {
        return stage;
    }

    // Setter for the stage of the current card view
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Getter for the current question being asked
    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    // Getter to check if the answer was correct
    public boolean isCorrectAnswer() {
        return correctAnswer;
    }
}
