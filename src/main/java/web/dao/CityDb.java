package web.dao;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by vladyslavprosolupov on 11.06.17.
 */
@Entity
@Table(name = "City", schema = "dbo", catalog = "filmotekaDb")
public class CityDb {
    private int id;
    private String name;
    private Collection<AddressDb> addressesById;
    private CountryDb countryByIdCountry;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityDb cityDb = (CityDb) o;

        if (id != cityDb.id) return false;
        if (name != null ? !name.equals(cityDb.name) : cityDb.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "cityByIdCity")
    public Collection<AddressDb> getAddressesById() {
        return addressesById;
    }

    public void setAddressesById(Collection<AddressDb> addressesById) {
        this.addressesById = addressesById;
    }

    @ManyToOne
    @JoinColumn(name = "id_country", referencedColumnName = "id", nullable = false)
    public CountryDb getCountryByIdCountry() {
        return countryByIdCountry;
    }

    public void setCountryByIdCountry(CountryDb countryByIdCountry) {
        this.countryByIdCountry = countryByIdCountry;
    }

    @Override
    public String toString() {
        return "CityDb{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", addressesById=" + addressesById +
                ", countryByIdCountry=" + countryByIdCountry +
                '}';
    }
}
