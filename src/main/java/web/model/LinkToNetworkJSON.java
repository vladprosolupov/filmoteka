package web.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Rostyk on 16.06.2017.
 */
public class LinkToNetworkJSON {

    private int id;

    @NotNull
    @Size(min = 1)
    private String link;

    private int idNetwork;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getIdNetwork() {
        return idNetwork;
    }

    public void setIdNetwork(int idNetwork) {
        this.idNetwork = idNetwork;
    }

    @Override
    public String toString() {
        return "LinkToNetworkJSON{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", idNetwork=" + idNetwork +
                '}';
    }
}
