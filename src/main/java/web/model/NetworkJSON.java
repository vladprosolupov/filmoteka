package web.model;

/**
 * Created by Rostyk on 17.06.2017.
 */
public class NetworkJSON {

    private int id;
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
