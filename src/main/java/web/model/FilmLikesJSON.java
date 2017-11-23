package web.model;

public class FilmLikesJSON {

    private long likes;
    private long dislikes;
    private int filmId;

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getDislikes() {
        return dislikes;
    }

    public void setDislikes(long dislikes) {
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
