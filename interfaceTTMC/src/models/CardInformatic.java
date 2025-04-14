package models;

import java.util.List;

public class CardInformatic extends Card {

	public CardInformatic(List<Question> questions) {
		super(questions);
		setTheme("Informatic");
	}

}
