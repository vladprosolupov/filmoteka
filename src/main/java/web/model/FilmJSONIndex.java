package web.model;

import java.sql.Date;
import java.util.Set;

public class FilmJSONIndex {

    private Date releaseDate;
    private String title;
    private String cover;
    private Double rating;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover() {
        return cover;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "FilmJSONIndex{" +
                "releaseDate=" + releaseDate +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                ", rating=" + rating +
                '}';
    }

}
