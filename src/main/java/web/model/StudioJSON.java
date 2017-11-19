package web.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Rostyk on 17.06.2017.
 */
public class StudioJSON {

    private int id;
    @NotNull
    @Size(min = 2, max = 64, message = "First name should at least have 2 symbols and it should be not bigger than 64")
    private String studioName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudioName() {
        return studioName;
    }

    public void setStudioName(String studioName) {
        this.studioName = studioName;
    }

    @Override
    public String toString() {
        return "StudioJSON{" +
                "id=" + id +
                ", studioName='" + studioName + '\'' +
                '}';
    }
}
