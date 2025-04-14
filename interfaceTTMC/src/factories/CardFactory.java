package factories;

import java.util.List;

import models.Card;
import models.Question;

public abstract class CardFactory {
	
	public abstract Card createCard(List<Question> allQuestions);
	
	protected abstract List<Question> selectQuestions(List<Question> allQuestions);
}
