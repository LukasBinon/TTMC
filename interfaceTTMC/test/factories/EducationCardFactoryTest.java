package factories;
import factories.EducationCardFactory;
import models.Question;
import models.Card;
import models.CardEducation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

public class EducationCardFactoryTest {

    private List<Question> questions;

    @BeforeEach
    public void setUp() {
        questions = new ArrayList<>();
        questions.add(new Question("Education", 1, "Q1", "A1", List.of("A1", "B1")));
        questions.add(new Question("Education", 2, "Q2", "A2", List.of("A2", "B2")));
        questions.add(new Question("Education", 3, "Q3", "A3", List.of("A3", "B3")));
        questions.add(new Question("Education", 4, "Q4", "A4", List.of("A4", "B4")));
        questions.add(new Question("Entertainment", 1, "Q5", "A5", List.of("A5", "B5"))); // should be ignored
    }
    
    @Test
    public void testCreateCardReturnsCardEducation() {
        EducationCardFactory factory = new EducationCardFactory();
        Card card = factory.createCard(questions);
        assertTrue(card instanceof CardEducation, "The generated card should be a CardEducation");
    }
    
    @Test
    public void testSelectsOneQuestionPerDifficulty() {
        EducationCardFactory factory = new EducationCardFactory();
        Card card = factory.createCard(questions);
        assertEquals(4, card.getQuestions().size(), "There should be 4 selected questions (one per difficulty level)");
    }
    
    @Test
    public void testFiltersOnlyEducationTheme() {
        EducationCardFactory factory = new EducationCardFactory();
        Card card = factory.createCard(questions);
        boolean allEducation = card.getQuestions().stream()
                .allMatch(q -> q.getThemeName().equalsIgnoreCase("Education"));
        assertTrue(allEducation, "All questions should belong to the 'Education' theme");
    }
    
    @Test
    public void testHandlesMissingDifficultyLevel() {
        // Remove the question with difficulty level 4
        questions.removeIf(q -> q.getPriority() == 4);

        EducationCardFactory factory = new EducationCardFactory();
        Card card = factory.createCard(questions);
        assertEquals(3, card.getQuestions().size(), "There should be 3 selected questions if a difficulty level is missing");
    }
}
