package web.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.AddressDb;

@Service("AddressService")
@Transactional
public class AddressService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public AddressDb getAddressById(int idAddress) throws HibernateException {
        if (idAddress < 0) {
            throw new IllegalArgumentException("IdAddress should not be smaller than 0");
        }
        Session session = sessionFactory.getCurrentSession();
        AddressDb addressDb = (AddressDb) session.createQuery("from AddressDb where id = " + idAddress);
        return addressDb;
    }
}
