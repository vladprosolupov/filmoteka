package web.model;

import org.springframework.beans.factory.annotation.Autowired;
import web.dao.*;
import web.services.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Rostyk on 15.06.2017.
 */
public class FilmJSON {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private DirectorService directorService;

    @Autowired
    private LinkToNetworkService linkToNetworkService;

    @Autowired
    private StudioService studioService;

    @Autowired
    private FilmActorService filmActorService;


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

    private List<String> actors;

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

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
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
        filmDb.setId(this.getId());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = new Date();
        try {
            parsed = format.parse(this.getReleaseDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        filmDb.setReleaseDate(new java.sql.Date(parsed.getTime()));
        filmDb.setSlogan(this.getSlogan());
        Set<CategoryDb> setOfCategories = new HashSet<>();
        for (String c: this.getCategories()) {
            setOfCategories.add(categoryService.getCategoryWithId(c));
        }
        filmDb.setFilmCategories(setOfCategories);
        Set<CountryDb> setOfCountries = new HashSet<>();
        for (String s: this.getCountries()) {
            setOfCountries.add(countryService.getCountryWithId(s));
        }
        filmDb.setFilmCountries(setOfCountries);
        Set<DirectorDb> setOfDirectors = new HashSet<>();
        for (String s: this.getDirectors()) {
            setOfDirectors.add(directorService.getDirectorWithId(s));
        }
        filmDb.setFilmDirectors(setOfDirectors);
        Set<LinkToNetworkDb> setOfLinkToNetworks = new HashSet<>();
        for (String s: this.getNetworks()) {
            setOfLinkToNetworks.add(linkToNetworkService.getLinkWithId(s));
        }
        filmDb.setFilmNetworks(setOfLinkToNetworks);
        Set<StudioDb> setOfStudios = new HashSet<>();
        for (String s: this.getStudios()) {
            setOfStudios.add(studioService.getStudioWithId(s));
        }
        filmDb.setFilmStudios(setOfStudios);
        Set<FilmActorDb> setOfFilmActors = new HashSet<>();
//        for (Map.Entry<String, String> entry : this.getActors().entrySet()) {
//            setOfFilmActors.add(filmActorService.getFilmActorWithId(
//                    entry.getValue(), entry.getKey(), this.getId()));
//
//        }
        filmDb.setFilmActorsById(setOfFilmActors);

        return filmDb;
    }
}
