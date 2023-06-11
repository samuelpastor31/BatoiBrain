package es.progcipfpbatoi.modelo.dto;

public class Answer {

    private int id;
    private Question question;
    private String description;
    private int correct;

    public Answer(int id, Question question, String description, int correct) {
        this.id = id;
        this.question = question;
        this.description = description;
        this.correct = correct;
    }

    public Answer(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return description + '\'' +
                ", correct=" + correct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }
}
