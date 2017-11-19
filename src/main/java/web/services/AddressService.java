package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger log = LogManager.getLogger(AddressService.class);

    public AddressDb getAddressById(int idAddress) throws HibernateException {
        log.info("getAddressById(idAddress=" + idAddress + ")");

        if (idAddress < 0) {
            log.error("Error : idAddress is incorrect");

            throw new IllegalArgumentException("IdAddress should not be smaller than 0");
        }
        Session session = sessionFactory.getCurrentSession();
        AddressDb addressDb = (AddressDb) session.createQuery("from AddressDb where id = " + idAddress);

        log.info("getAddressById() returns : addressDb=" + addressDb);
        return addressDb;
    }
}
