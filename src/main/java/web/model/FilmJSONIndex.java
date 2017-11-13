package web.model;

import java.sql.Date;
import java.util.List;

public class FilmJSONIndex {

    private String releaseDate;
    private List<String> categories;
    private List<String> screenshots;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getScreenshots() {

        return screenshots;
    }

    public void setScreenshots(List<String> screenshots) {
        this.screenshots = screenshots;
    }

    public List<String> getCategories() {

        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getReleaseDate() {

        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
