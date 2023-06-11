package es.progcipfpbatoi.modelo.dto;

public class Categories {

    private int id;
    private String description;

    public Categories(int id) {
        this.id = id;
    }

    public Categories(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
