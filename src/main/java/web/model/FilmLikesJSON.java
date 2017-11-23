package web.model;

public class FilmLikesJSON {

    private int likes;
    private int dislikes;
    private int filmId;

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    @Override
    public String toString() {
        return "FilmLikesJSON{" +
                "likes=" + likes +
                ", dislikes=" + dislikes +
                ", filmId=" + filmId +
                '}';
    }
}
