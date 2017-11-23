package web.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CommentJSON {

    private int id;
    @NotNull
    private int idFilm;
    private int idClient;
    @NotNull
    private String commentText;
    private String commentDate;
    private String clientLogin;
    private String clientFirstName;
    private String clientLastName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getClientLogin() {
        return clientLogin;
    }

    public void setClientLogin(String clientLogin) {
        this.clientLogin = clientLogin;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    @Override
    public String toString() {
        return "CommentJSON{" +
                "id=" + id +
                ", idFilm=" + idFilm +
                ", idClient=" + idClient +
                ", commentText='" + commentText + '\'' +
                ", commentDate='" + commentDate + '\'' +
                ", clientLogin='" + clientLogin + '\'' +
                ", clientFirstName='" + clientFirstName + '\'' +
                ", clientLastName='" + clientLastName + '\'' +
                '}';
    }
}
