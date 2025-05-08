package factories;

import java.util.List;

import models.Card;
import models.Question;

public abstract class CardFactory {
	
	//Defines the interface for creating a Card object.
	public abstract Card createCard(List<Question> allQuestions);
	
	//Used by the factory method to select questions.
	protected abstract List<Question> selectQuestions(List<Question> allQuestions);
}
