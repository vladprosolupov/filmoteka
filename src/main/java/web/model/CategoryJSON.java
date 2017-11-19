package web.model;

/**
 * Created by Rostyk on 17.06.2017.
 */
public class CategoryJSON {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryJSON{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
