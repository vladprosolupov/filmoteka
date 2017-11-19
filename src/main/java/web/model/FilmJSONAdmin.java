package web.model;

import java.sql.Date;

public class FilmJSONAdmin {
    private String title;
    private Date releaseDate;
    private Double rating;
    private Integer lenght;

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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getLenght() {
        return lenght;
    }

    public void setLenght(Integer lenght) {
        this.lenght = lenght;
    }

    @Override
    public String toString() {
        return "FilmJSONAdmin{" +
                "title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", rating=" + rating +
                ", lenght=" + lenght +
                '}';
    }
}
