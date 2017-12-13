package web.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import web.embeddable.FilmDislike;
import web.embeddable.FilmLike;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Set;

/**
 * Created by vladyslavprosolupov on 11.06.17.
 */
@Entity
@Table(name = "Film", schema = "dbo", catalog = "inzS13009")
public class FilmDb {
    private int id;
    private String title;
    private Date releaseDate;
    private Integer lenght;
    private String description;
    private String slogan;
    private Double rating;
    private Integer age;
    private String budget;
    private String cover;
    private Set<CategoryDb> filmCategories;
    private Set<StudioDb> filmStudios;
    private Set<LinkToNetworkDb> filmNetworks;
    private Set<DirectorDb> filmDirectors;
    private Set<AwardDb> awardsById;
    private LanguageDb languageByIdLanguage;
    private Set<FilmActorDb> filmActorsById;
    private Set<ScreenshotDb> screenshotsById;
    private Set<TrailerDb> trailersById;
    private Set<CountryDb> filmCountries;
//    private Set<FilmLike> filmLikes;
//    private Set<FilmDislike> filmDislikes;
    //TODO Check the right query
    @JsonIgnore
    private Collection<CommentDb> commentsById;
    @JsonIgnore
    private String titleSearch;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = true, length = -1)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "release_date", nullable = true)
    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Basic
    @Column(name = "lenght", nullable = true)
    public Integer getLenght() {
        return lenght;
    }

    public void setLenght(Integer lenght) {
        this.lenght = lenght;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "slogan", nullable = true, length = -1)
    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    @Basic
    @Column(name = "rating", nullable = true, precision = 0)
    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Basic
    @Column(name = "age", nullable = true)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Basic
    @Column(name = "budget", nullable = true, length = 255)
    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    @Basic
    @Column(name = "cover", nullable = true, length = -1)
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Basic
    @Column(name = "title_search", nullable = true, length = -1)
    public String getTitleSearch() {
        return titleSearch;
    }

    public void setTitleSearch(String titleSearch) {
        this.titleSearch = titleSearch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilmDb filmDb = (FilmDb) o;

        if (id != filmDb.id) return false;
        if (title != null ? !title.equals(filmDb.title) : filmDb.title != null) return false;
        if (releaseDate != null ? !releaseDate.equals(filmDb.releaseDate) : filmDb.releaseDate != null) return false;
        if (lenght != null ? !lenght.equals(filmDb.lenght) : filmDb.lenght != null) return false;
        if (description != null ? !description.equals(filmDb.description) : filmDb.description != null) return false;
        if (slogan != null ? !slogan.equals(filmDb.slogan) : filmDb.slogan != null) return false;
        if (rating != null ? !rating.equals(filmDb.rating) : filmDb.rating != null) return false;
        if (age != null ? !age.equals(filmDb.age) : filmDb.age != null) return false;
        if (budget != null ? !budget.equals(filmDb.budget) : filmDb.budget != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (lenght != null ? lenght.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (slogan != null ? slogan.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (budget != null ? budget.hashCode() : 0);
        return result;
    }

    @ManyToMany
    @JoinTable(name = "Film_Category", catalog = "inzS13009", schema = "dbo", joinColumns = @JoinColumn(name = "id_film", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_category", referencedColumnName = "id", nullable = false))
    @Fetch(FetchMode.JOIN)
    public Set<CategoryDb> getFilmCategories() {
        return filmCategories;
    }

    public void setFilmCategories(Set<CategoryDb> filmCategories) {
        this.filmCategories = filmCategories;
    }

    @ManyToMany
    @JoinTable(name = "Film_studio", catalog = "inzS13009", schema = "dbo", joinColumns = @JoinColumn(name = "id_film", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_studio", referencedColumnName = "id", nullable = false))
    @Fetch(FetchMode.JOIN)
    public Set<StudioDb> getFilmStudios() {
        return filmStudios;
    }

    public void setFilmStudios(Set<StudioDb> filmStudios) {
        this.filmStudios = filmStudios;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Film_network", catalog = "inzS13009", schema = "dbo", joinColumns = @JoinColumn(name = "id_film", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_link_to_network", referencedColumnName = "id", nullable = false))
    @Fetch(FetchMode.JOIN)
    public Set<LinkToNetworkDb> getFilmNetworks() {
        return filmNetworks;
    }

    public void setFilmNetworks(Set<LinkToNetworkDb> filmNetworks) {
        this.filmNetworks = filmNetworks;
    }

    @ManyToMany
    @JoinTable(name = "Film_director", catalog = "inzS13009", schema = "dbo", joinColumns = @JoinColumn(name = "id_film", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_director", referencedColumnName = "id", nullable = false))
    @Fetch(FetchMode.JOIN)
    public Set<DirectorDb> getFilmDirectors() {
        return filmDirectors;
    }

    public void setFilmDirectors(Set<DirectorDb> filmDirectors) {
        this.filmDirectors = filmDirectors;
    }

    @OneToMany(mappedBy = "filmByIdFilm")
    @Fetch(FetchMode.JOIN)
    public Set<AwardDb> getAwardsById() {
        return awardsById;
    }

    public void setAwardsById(Set<AwardDb> awardsById) {
        this.awardsById = awardsById;
    }

    @ManyToOne
    @JoinColumn(name = "id_language", referencedColumnName = "id", nullable = false)
    @Fetch(FetchMode.JOIN)
    public LanguageDb getLanguageByIdLanguage() {
        return languageByIdLanguage;
    }

    public void setLanguageByIdLanguage(LanguageDb languageByIdLanguage) {
        this.languageByIdLanguage = languageByIdLanguage;
    }

    @OneToMany(mappedBy = "filmByIdFilm")
    @Fetch(FetchMode.JOIN)
    public Set<FilmActorDb> getFilmActorsById() {
        return filmActorsById;
    }

    public void setFilmActorsById(Set<FilmActorDb> filmActorsById) {
        this.filmActorsById = filmActorsById;
    }

    @OneToMany(mappedBy = "filmByIdFilm")
    @Fetch(FetchMode.JOIN)
    public Set<ScreenshotDb> getScreenshotsById() {
        return screenshotsById;
    }

    public void setScreenshotsById(Set<ScreenshotDb> screenshotsById) {
        this.screenshotsById = screenshotsById;
    }

    @OneToMany(mappedBy = "filmByIdFilm")
    @Fetch(FetchMode.JOIN)
    public Set<TrailerDb> getTrailersById() {
        return trailersById;
    }

    public void setTrailersById(Set<TrailerDb> trailersById) {
        this.trailersById = trailersById;
    }

    @ManyToMany
    @JoinTable(name = "Film_country", catalog = "inzS13009", schema = "dbo", joinColumns = @JoinColumn(name = "id_film", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_country", referencedColumnName = "id", nullable = false))
    @Fetch(FetchMode.JOIN)
    public Set<CountryDb> getFilmCountries() {
        return filmCountries;
    }

    public void setFilmCountries(Set<CountryDb> filmCountries) {
        this.filmCountries = filmCountries;
    }

    @OneToMany(mappedBy = "filmByIdFilm")
    @Fetch(FetchMode.JOIN)
    public Collection<CommentDb> getCommentsById() {
        return commentsById;
    }

    public void setCommentsById(Collection<CommentDb> commentRatingsById) {
        this.commentsById = commentRatingsById;
    }

    @Override
    public String toString() {
        return "FilmDb{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", lenght=" + lenght +
                ", description='" + description + '\'' +
                ", slogan='" + slogan + '\'' +
                ", rating=" + rating +
                ", age=" + age +
                ", budget='" + budget + '\'' +
                ", cover='" + cover + '\'' +
                ", titleSearch='" + titleSearch + '\'' +
                '}';
    }
}
