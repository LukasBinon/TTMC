package models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Card {

    private String theme_name; // Thème de la carte
    private int priority; // Priorité (difficulté)
    private String question; // Question posée
    private String answer; // Réponse correcte
    private List<String> multiple_choice; // Choix multiples

    // Constructeur par défaut
    public Card() {
        // Laissez ce constructeur vide ou initialisez les valeurs par défaut si nécessaire
    }

    // Constructeur avec paramètres
    @JsonCreator
    public Card(@JsonProperty("theme_name") String theme_name,
                @JsonProperty("priority") int priority,
                @JsonProperty("question") String question,
                @JsonProperty("answer") String answer,
                @JsonProperty("multiple_choice") List<String> multiple_choice) {
        this.theme_name = theme_name;
        this.priority = priority;
        this.question = question;
        this.answer = answer;
        this.multiple_choice = multiple_choice;
    }

    // Getters et setters
    public String getTheme_name() {
        return theme_name;
    }

    public void setTheme_name(String theme_name) {
        this.theme_name = theme_name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getMultiple_choice() {
        return multiple_choice;
    }

    public void setMultiple_choice(List<String> multiple_choice) {
        this.multiple_choice = multiple_choice;
    }

    // ToString pour une représentation facile
    @Override
    public String toString() {
        return "Card{" +
                "theme_name='" + theme_name + '\'' +
                ", priority=" + priority +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", multiple_choice=" + multiple_choice +
                '}';
    }
}
