package web.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Comment_rating", schema = "dbo", catalog = "filmotekaDb")
public class CommentRatingDb {
    private int id;
    private String commentText;
    private Double rating;
    private Integer referencedComment;
    private Timestamp commentDate;
    private ClientDb clientByIdClient;
    @JsonIgnore
    private FilmDb filmByIdFilm;

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
    @Column(name = "rating", nullable = true, precision = 0)
    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Basic
    @Column(name = "referenced_comment", nullable = true)
    public Integer getReferencedComment() {
        return referencedComment;
    }

    public void setReferencedComment(Integer referencedComment) {
        this.referencedComment = referencedComment;
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

        CommentRatingDb that = (CommentRatingDb) o;

        if (id != that.id) return false;
        if (commentText != null ? !commentText.equals(that.commentText) : that.commentText != null) return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        if (referencedComment != null ? !referencedComment.equals(that.referencedComment) : that.referencedComment != null)
            return false;
        if (commentDate != null ? !commentDate.equals(that.commentDate) : that.commentDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (commentText != null ? commentText.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (referencedComment != null ? referencedComment.hashCode() : 0);
        result = 31 * result + (commentDate != null ? commentDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_client", referencedColumnName = "id")
    public ClientDb getClientByIdClient() {
        return clientByIdClient;
    }

    public void setClientByIdClient(ClientDb clientByIdClient) {
        this.clientByIdClient = clientByIdClient;
    }

    @ManyToOne
    @JoinColumn(name = "id_film", referencedColumnName = "id")
    public FilmDb getFilmByIdFilm() {
        return filmByIdFilm;
    }

    public void setFilmByIdFilm(FilmDb filmByIdFilm) {
        this.filmByIdFilm = filmByIdFilm;
    }

    @Override
    public String toString() {
        return "CommentRatingDb{" +
                "id=" + id +
                ", commentText='" + commentText + '\'' +
                ", rating=" + rating +
                ", referencedComment=" + referencedComment +
                ", commentDate=" + commentDate +
                ", clientByIdClient=" + clientByIdClient +
                ", filmByIdFilm=" + filmByIdFilm +
                '}';
    }
}
