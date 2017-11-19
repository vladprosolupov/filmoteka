package web.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Rostyk on 17.06.2017.
 */
public class NetworkJSON {

    private int id;
    @NotNull
    @Size(min = 2, max = 64, message = "Name should at least have 2 symbols and it should be not bigger than 64")
    private String networkName;
    private String networkLogo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getNetworkLogo() {
        return networkLogo;
    }

    public void setNetworkLogo(String networkLogo) {
        this.networkLogo = networkLogo;
    }

    @Override
    public String toString() {
        return "NetworkJSON{" +
                "id=" + id +
                ", networkName='" + networkName + '\'' +
                ", networkLogo='" + networkLogo + '\'' +
                '}';
    }
}
