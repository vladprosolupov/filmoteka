package web.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FilmJSONSearch {

    private int id;

    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "FilmJSONSearch{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

}
