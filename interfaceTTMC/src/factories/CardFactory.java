package factories;

import models.Card;

import java.util.List;

public class CardFactory {

    public static Card createCard(String theme_name, int priority, String question, String answer, List<String> multiple_choice) {
        return new Card(theme_name, priority, question, answer, multiple_choice);
    }
}
