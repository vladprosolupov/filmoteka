package web.model.aiModel;

import java.sql.Date;
import java.util.Map;

public class CombinedFilm {
    private Map<Integer, Integer> categories;
    private Map<String, Integer> titles;
    private Map<Integer, Integer> countries;
    private Map<Integer, Integer> actors;
    private Map<Integer, Integer> directors;
    private Map<Integer, Integer> studios;
    private Map<Date, Integer> releaseDates;

    public Map<Integer, Integer> getCategories() {
        return categories;
    }

    public void setCategories(Map<Integer, Integer> categories) {
        this.categories = categories;
    }

    public Map<String, Integer> getTitles() {
        return titles;
    }

    public void setTitles(Map<String, Integer> titles) {
        this.titles = titles;
    }

    public Map<Integer, Integer> getCountries() {
        return countries;
    }

    public void setCountries(Map<Integer, Integer> countries) {
        this.countries = countries;
    }

    public Map<Integer, Integer> getActors() {
        return actors;
    }

    public void setActors(Map<Integer, Integer> actors) {
        this.actors = actors;
    }

    public Map<Integer, Integer> getDirectors() {
        return directors;
    }

    public void setDirectors(Map<Integer, Integer> directors) {
        this.directors = directors;
    }

    public Map<Integer, Integer> getStudios() {
        return studios;
    }

    public void setStudios(Map<Integer, Integer> studios) {
        this.studios = studios;
    }

    public Map<Date, Integer> getReleaseDates() {
        return releaseDates;
    }

    public void setReleaseDates(Map<Date, Integer> releaseDates) {
        this.releaseDates = releaseDates;
    }

    @Override
    public String toString() {
        return "CombinedFilm{" +
                "categories=" + categories +
                ", titles=" + titles +
                ", countries=" + countries +
                ", actors=" + actors +
                ", directors=" + directors +
                ", studios=" + studios +
                ", releaseDates=" + releaseDates +
                '}';
    }
}
