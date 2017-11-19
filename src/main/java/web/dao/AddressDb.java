package web.dao;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by vladyslavprosolupov on 11.06.17.
 */
@Entity
@Table(name = "Address", schema = "dbo", catalog = "filmotekaDb")
public class AddressDb {
    private int id;
    private String address;
    private String address2;
    private String district;
    private String postalCode;
    private CityDb cityByIdCity;
    private Collection<ClientDb> clientsById;

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
    @Column(name = "address", nullable = true, length = -1)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "address2", nullable = true, length = -1)
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Basic
    @Column(name = "district", nullable = true, length = 64)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Basic
    @Column(name = "postal_code", nullable = true, length = 32)
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressDb addressDb = (AddressDb) o;

        if (id != addressDb.id) return false;
        if (address != null ? !address.equals(addressDb.address) : addressDb.address != null) return false;
        if (address2 != null ? !address2.equals(addressDb.address2) : addressDb.address2 != null) return false;
        if (district != null ? !district.equals(addressDb.district) : addressDb.district != null) return false;
        if (postalCode != null ? !postalCode.equals(addressDb.postalCode) : addressDb.postalCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (address2 != null ? address2.hashCode() : 0);
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_city", referencedColumnName = "id", nullable = false)
    public CityDb getCityByIdCity() {
        return cityByIdCity;
    }

    public void setCityByIdCity(CityDb cityByIdCity) {
        this.cityByIdCity = cityByIdCity;
    }

    @OneToMany(mappedBy = "addressByIdAddress")
    public Collection<ClientDb> getClientsById() {
        return clientsById;
    }

    public void setClientsById(Collection<ClientDb> clientsById) {
        this.clientsById = clientsById;
    }


}
