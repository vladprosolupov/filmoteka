package com.group.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by 1rost on 5/22/2017.
 */
@Entity
@Table(name = "Comment_rating", schema = "dbo", catalog = "filmotekaDb")
public class CommentRatingEntity {
    private int id;
    private String commentText;
    private Double filmRating;
    private Timestamp commentDate;
    private ClientEntity clientByIdClient;
    private FilmEntity filmByIdFilm;

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
    @Column(name = "comment_text", nullable = true, length = -1)
    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @Basic
    @Column(name = "Film_rating", nullable = true, precision = 0)
    public Double getFilmRating() {
        return filmRating;
    }

    public void setFilmRating(Double filmRating) {
        this.filmRating = filmRating;
    }

    @Basic
    @Column(name = "comment_date", nullable = true)
    public Timestamp getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Timestamp commentDate) {
        this.commentDate = commentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentRatingEntity that = (CommentRatingEntity) o;

        if (id != that.id) return false;
        if (commentText != null ? !commentText.equals(that.commentText) : that.commentText != null) return false;
        if (filmRating != null ? !filmRating.equals(that.filmRating) : that.filmRating != null) return false;
        if (commentDate != null ? !commentDate.equals(that.commentDate) : that.commentDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (commentText != null ? commentText.hashCode() : 0);
        result = 31 * result + (filmRating != null ? filmRating.hashCode() : 0);
        result = 31 * result + (commentDate != null ? commentDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_client", referencedColumnName = "id", nullable = false)
    public ClientEntity getClientByIdClient() {
        return clientByIdClient;
    }

    public void setClientByIdClient(ClientEntity clientByIdClient) {
        this.clientByIdClient = clientByIdClient;
    }

    @ManyToOne
    @JoinColumn(name = "id_film", referencedColumnName = "id", nullable = false)
    public FilmEntity getFilmByIdFilm() {
        return filmByIdFilm;
    }

    public void setFilmByIdFilm(FilmEntity filmByIdFilm) {
        this.filmByIdFilm = filmByIdFilm;
    }
}
