package web.dao;

import web.embeddable.Bookmark;

import javax.persistence.*;

@Entity
@Table(name = "Bookmark", schema = "dbo", catalog = "filmotekaDb")
public class BookmarkDb {
    @EmbeddedId
    private Bookmark bookmark;

    public Bookmark getBookmark() {
        return bookmark;
    }

    public void setBookmark(Bookmark bookmark) {
        this.bookmark = bookmark;
    }

    @Override
    public String toString() {
        return "BookmarkDb{" +
                "bookmark=" + bookmark +
                '}';
    }
}
