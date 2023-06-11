package es.progcipfpbatoi.modelo.dto;

public class Question {

    private int id;
    private String description;
    private String image_url;
    private Categories category;
    private Levels level;
    private int status;

    public Question(int id, String description, String image_url, Categories category, Levels level, int status) {
        this.id = id;
        this.description = description;
        this.image_url = image_url;
        this.category = category;
        this.level = level;
        this.status = status;
    }

    public Question(int id, String description, Categories category, Levels level, int status) {
        this.id = id;
        this.description = description;
        this.category = category;
        this.level = level;
        this.status = status;
    }

    public Question(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  id +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", level=" + level +
                ", status=" + status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public Levels getLevel() {
        return level;
    }

    public void setLevel(Levels level) {
        this.level = level;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

