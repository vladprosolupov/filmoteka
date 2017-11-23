package web.model;

import javax.validation.constraints.NotNull;

public class BookmarkJSON {
    @NotNull
    private int idFilm;

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    @Override
    public String toString() {
        return "BookmarkJSON{" +
                "idFilm=" + idFilm +
                '}';
    }
}
