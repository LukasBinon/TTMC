package models;

import java.util.List;

public class CardEducation extends Card{

	public CardEducation(List<Question> questions) {
		super(questions);
		setTheme("Education");
	}

}