package web.model;

import web.dao.FilmDb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Rostyk on 15.06.2017.
 */
public class FilmJSON {

    private int id;

    private String title;

    private String releaseDate;

    private String language;

    private int lenght;

    private String description;

    private String slogan;

    private double rating;

    private int age;

    private String budget;

    private String cover;

    private List<String> categories;

    private Map<String,String> actors;

    private List<String> directors;

    private List<String> studios;

    private List<String> countries;

    private List<String> networks;

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

    public Map<String, String> getActors() {
        return actors;
    }

    public void setActors(Map<String, String> actors) {
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

    public List<String> getNetworks() {
        return networks;
    }

    public void setNetworks(List<String> networks) {
        this.networks = networks;
    }


    public FilmDb convert(){
        FilmDb filmDb = new FilmDb();
        filmDb.setAge(this.getAge());
        filmDb.setBudget(this.getBudget());
        filmDb.setCover(this.getCover());
        filmDb.setDescription(this.getDescription());
        filmDb.setTitle(this.getTitle());
        filmDb.setLenght(this.getLenght());
        filmDb.setRating(this.getRating());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date parsed = new Date();
        try {
            parsed = format.parse(this.getReleaseDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        filmDb.setReleaseDate(new java.sql.Date(parsed.getTime()));
        filmDb.setSlogan(this.getSlogan());
//        filmDb.setFilmCategories();
        return filmDb;
    }
}
