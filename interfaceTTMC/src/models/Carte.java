package models;

import java.util.List;

public class Carte {
	private String theme_name;
	private int priority;
	private String question;
	private String answer;
	
	private List<String> multiple_choice;
	
	public Carte() {}
	
	public Carte(String theme_name, int priority, String question, String answer, List<String> multiple_choice) {
		super();
		this.theme_name = theme_name;
		if(priority>0 && priority <5) {
			this.priority = priority;
		}
		this.question = question;
		this.answer = answer;
		this.multiple_choice = multiple_choice;
	}
	
	public List<String> getMultiple_choice() {
		return multiple_choice;
	}
	public String getTheme_name() {
		return theme_name;
	}
	public int getPriority() {
		return priority;
	}
	public String getQuestion() {
		return question;
	}
	public String getAnswer() {
		return answer;
	}
	
	

}
