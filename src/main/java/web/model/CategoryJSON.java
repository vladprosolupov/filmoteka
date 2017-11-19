package web.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Rostyk on 17.06.2017.
 */
public class CategoryJSON {

    private int id;
    @NotNull
    @Size(min = 10, max = 128, message = "Name of category should be between 10 and 128")
    @Pattern(regexp = "[a-zA-Z]+", message = "Name of category should be only with letters")
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
