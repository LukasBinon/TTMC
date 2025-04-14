package factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import models.Card;
import models.CardEducation;
import models.Question;

public class EducationCardFactory extends CardFactory {
	@Override
    protected List<Question> selectQuestions(List<Question> allQuestions) {
        List<Question> filtered = allQuestions.stream()
                .filter(q -> q.getThemeName().equalsIgnoreCase("Education"))
                .collect(Collectors.toList());

        List<Question> selected = new ArrayList<>();
        for (int difficulty = 1; difficulty <= 4; difficulty++) {
            int finalDifficulty = difficulty;
            List<Question> byDiff = filtered.stream()
                    .filter(q -> q.getPriority() == finalDifficulty)
                    .collect(Collectors.toList());
            if (!byDiff.isEmpty()) {
                Collections.shuffle(byDiff);
                selected.add(byDiff.get(0));
            }
        }
        return selected;
    }

	@Override
	public Card createCard(List<Question> allQuestions) {
		return new CardEducation(selectQuestions(allQuestions));
	}
}