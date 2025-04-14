package models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Question {
    private String themeName;
	private int priority;
    private String question;
    private String answer;
    private List<String> multipleChoice;

    public Question(String themeName, int priority, String question, String answer, List<String> multipleChoice) {
        this.themeName = themeName;
        this.priority = priority;
        this.question = question;
        this.answer = answer;
        this.multipleChoice = multipleChoice;
    }

    public String getThemeName() { return themeName; }
    public int getPriority() { return priority; }
    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }
    public List<String> getMultipleChoice() { return multipleChoice; }
    @Override
	public String toString() {
		return "Question [themeName=" + themeName + ", priority=" + priority + ", question=" + question + ", answer="
				+ answer + ", multipleChoice=" + multipleChoice + "]\n";
	}
}
