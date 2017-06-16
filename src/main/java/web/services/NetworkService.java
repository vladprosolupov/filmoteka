package web.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.NetworkDb;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Service("NetworkService")
@Transactional
public class NetworkService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public NetworkDb getNetworkWithId(String id){
        Session session = sessionFactory.getCurrentSession();
        NetworkDb networkDb =
                (NetworkDb) session.createQuery("from NetworkDb n where n.id=" + id).list().get(0);
        return networkDb;
    }

}
