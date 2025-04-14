package models;

import java.util.List;

public class CardEntertainment extends Card {

	public CardEntertainment(List<Question> questions) {
		super(questions);
		setTheme("Entertainment");
	}

	
}
