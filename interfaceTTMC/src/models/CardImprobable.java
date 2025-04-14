package models;

import java.util.List;

public class CardImprobable extends Card {

	public CardImprobable(List<Question> questions) {
		super(questions);
		setTheme("Improbable");
	}

}