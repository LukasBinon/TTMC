package models;

import java.util.List;

public abstract class Card {

	private String theme;
	private List<Question> questions;

    public Card(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }
    
    public String getTheme() {
    	return theme;
    }
    
    public void setTheme(String theme) {
    	this.theme = theme;
    }
    
	@Override
	public String toString() {
		return "Card [theme=" + theme + ", questions=" + questions + "]";
	}
	
}