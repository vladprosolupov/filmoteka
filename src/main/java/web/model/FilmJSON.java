package web.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Created by Rostyk on 15.06.2017.
 */
public class FilmJSON {

    private int id;

    @NotNull
    @Size(min = 2)
    private String title;

    @NotNull
    private String releaseDate;

    private String language;

    @Min(10)
    @Max(500)
    private int lenght;

    @NotNull
    @Size(min = 10)
    private String description;

    private String slogan;

    private double rating;
//
//    @Min(0)
//    @Max(200)
    private int age;

    private String budget;

    private String cover;

    private List<String> categories;

    private Map<String, Integer> actors;

    private List<String> directors;

    private List<String> studios;

    private List<String> countries;

    private Map<String, Integer> networks;

    private List<String> screenshots;

    private Map<String, Integer> awards;

    private List<String> trailers;

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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Map<String, Integer> getActors() {
        return actors;
    }

    public void setActors(Map<String, Integer> actors) {
        this.actors = actors;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getStudios() {
        return studios;
    }

    public void setStudios(List<String> studios) {
        this.studios = studios;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public Map<String, Integer> getNetworks() {
        return networks;
    }

    public void setNetworks(Map<String, Integer> networks) {
        this.networks = networks;
    }

    public List<String> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<String> screenshots) {
        this.screenshots = screenshots;
    }

    public Map<String, Integer> getAwards() {
        return awards;
    }

    public void setAwards(Map<String, Integer> awards) {
        this.awards = awards;
    }

    public List<String> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<String> trailers) {
        this.trailers = trailers;
    }

    @Override
    public String toString() {
        return "FilmJSON{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", language='" + language + '\'' +
                ", lenght=" + lenght +
                ", description='" + description + '\'' +
                ", slogan='" + slogan + '\'' +
                ", rating=" + rating +
                ", age=" + age +
                ", budget='" + budget + '\'' +
                ", cover='" + cover + '\'' +
                ", categories=" + categories +
                ", actors=" + actors +
                ", directors=" + directors +
                ", studios=" + studios +
                ", countries=" + countries +
                ", networks=" + networks +
                ", screenshots=" + screenshots +
                ", awards=" + awards +
                ", trailers=" + trailers +
                '}';
    }
}
