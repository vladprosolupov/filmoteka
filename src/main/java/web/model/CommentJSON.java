package web.model;

public class CommentJSON {

    private int id;
    private int idFilm;
    private int idClient;
    private String commentText;
    private double rating;
    private int referencedComment;
    private String commentDate;
    private String clientLogin;

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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getReferencedComment() {
        return referencedComment;
    }

    public void setReferencedComment(int referencedComment) {
        this.referencedComment = referencedComment;
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

    @Override
    public String toString() {
        return "CommentJSON{" +
                "id=" + id +
                ", idFilm=" + idFilm +
                ", idClient=" + idClient +
                ", commentText='" + commentText + '\'' +
                ", rating=" + rating +
                ", referencedComment=" + referencedComment +
                ", commentDate='" + commentDate + '\'' +
                '}';
    }
}
