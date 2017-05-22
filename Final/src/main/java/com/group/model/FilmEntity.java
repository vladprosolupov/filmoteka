package com.group.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by 1rost on 5/22/2017.
 */
@Entity
@Table(name = "Film", schema = "dbo", catalog = "filmotekaDb")
public class FilmEntity {
    private int id;
    private String title;
    private Timestamp releaseDate;
    private Integer lenght;
    private String description;
    private Collection<AwardEntity> awardsById;
    private Collection<ClientDataEntity> clientDataById;
    private Collection<CommentRatingEntity> commentRatingsById;
    private LanguageEntity languageByIdLanguage;
    private CountryEntity countryByCountryId;
    private Collection<FilmActorEntity> filmActorsById;
    private Collection<FilmCategoryEntity> filmCategoriesById;
    private Collection<FilmDirectorEntity> filmDirectorsById;
    private Collection<FilmNetworkEntity> filmNetworksById;
    private Collection<FilmStudioEntity> filmStudiosById;
    private Collection<ScreenshotEntity> screenshotsById;
    private Collection<TrailerEntity> trailersById;

    @Id
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
    public Timestamp getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Timestamp releaseDate) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilmEntity that = (FilmEntity) o;

        if (id != that.id) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (releaseDate != null ? !releaseDate.equals(that.releaseDate) : that.releaseDate != null) return false;
        if (lenght != null ? !lenght.equals(that.lenght) : that.lenght != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (lenght != null ? lenght.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "filmByIdFilm")
    public Collection<AwardEntity> getAwardsById() {
        return awardsById;
    }

    public void setAwardsById(Collection<AwardEntity> awardsById) {
        this.awardsById = awardsById;
    }

    @OneToMany(mappedBy = "filmByIdFilm")
    public Collection<ClientDataEntity> getClientDataById() {
        return clientDataById;
    }

    public void setClientDataById(Collection<ClientDataEntity> clientDataById) {
        this.clientDataById = clientDataById;
    }

    @OneToMany(mappedBy = "filmByIdFilm")
    public Collection<CommentRatingEntity> getCommentRatingsById() {
        return commentRatingsById;
    }

    public void setCommentRatingsById(Collection<CommentRatingEntity> commentRatingsById) {
        this.commentRatingsById = commentRatingsById;
    }

    @ManyToOne
    @JoinColumn(name = "id_language", referencedColumnName = "id", nullable = false)
    public LanguageEntity getLanguageByIdLanguage() {
        return languageByIdLanguage;
    }

    public void setLanguageByIdLanguage(LanguageEntity languageByIdLanguage) {
        this.languageByIdLanguage = languageByIdLanguage;
    }

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    public CountryEntity getCountryByCountryId() {
        return countryByCountryId;
    }

    public void setCountryByCountryId(CountryEntity countryByCountryId) {
        this.countryByCountryId = countryByCountryId;
    }

    @OneToMany(mappedBy = "filmByIdFilm")
    public Collection<FilmActorEntity> getFilmActorsById() {
        return filmActorsById;
    }

    public void setFilmActorsById(Collection<FilmActorEntity> filmActorsById) {
        this.filmActorsById = filmActorsById;
    }

    @OneToMany(mappedBy = "filmByIdFilm")
    public Collection<FilmCategoryEntity> getFilmCategoriesById() {
        return filmCategoriesById;
    }

    public void setFilmCategoriesById(Collection<FilmCategoryEntity> filmCategoriesById) {
        this.filmCategoriesById = filmCategoriesById;
    }

    @OneToMany(mappedBy = "filmByIdFilm")
    public Collection<FilmDirectorEntity> getFilmDirectorsById() {
        return filmDirectorsById;
    }

    public void setFilmDirectorsById(Collection<FilmDirectorEntity> filmDirectorsById) {
        this.filmDirectorsById = filmDirectorsById;
    }

    @OneToMany(mappedBy = "filmByIdFilm")
    public Collection<FilmNetworkEntity> getFilmNetworksById() {
        return filmNetworksById;
    }

    public void setFilmNetworksById(Collection<FilmNetworkEntity> filmNetworksById) {
        this.filmNetworksById = filmNetworksById;
    }

    @OneToMany(mappedBy = "filmByIdFilm")
    public Collection<FilmStudioEntity> getFilmStudiosById() {
        return filmStudiosById;
    }

    public void setFilmStudiosById(Collection<FilmStudioEntity> filmStudiosById) {
        this.filmStudiosById = filmStudiosById;
    }

    @OneToMany(mappedBy = "filmByIdFilm")
    public Collection<ScreenshotEntity> getScreenshotsById() {
        return screenshotsById;
    }

    public void setScreenshotsById(Collection<ScreenshotEntity> screenshotsById) {
        this.screenshotsById = screenshotsById;
    }

    @OneToMany(mappedBy = "filmByIdFilm")
    public Collection<TrailerEntity> getTrailersById() {
        return trailersById;
    }

    public void setTrailersById(Collection<TrailerEntity> trailersById) {
        this.trailersById = trailersById;
    }
}
