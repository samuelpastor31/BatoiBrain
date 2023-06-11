package es.progcipfpbatoi.modelo.dto;

public class Levels {

    private int id;
    private String description;

    public Levels(String description) {
        this.description = description;
    }

    public Levels(int id) {
        this.id = id;
    }

    public Levels(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Levels{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
