package web.dao;

import javax.persistence.*;

@Entity
@Table(name = "Avatar", schema = "dbo", catalog = "filmotekaDb")
public class AvatarDb {
    private String id;
    private String path;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AvatarDb avatarDb = (AvatarDb) o;

        if (id != null ? !id.equals(avatarDb.id) : avatarDb.id != null) return false;
        if (path != null ? !path.equals(avatarDb.path) : avatarDb.path != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }
}
