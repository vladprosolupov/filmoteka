package web.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import web.dao.LinkToNetworkDb;

/**
 * Created by Rostyk on 16.06.2017.
 */
public class LinkToNetworkService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public LinkToNetworkDb getLinkWithId(String id){
        Session session = sessionFactory.getCurrentSession();
        LinkToNetworkDb linkToNetworkDb =
                (LinkToNetworkDb) session.createQuery("from LinkToNetworkDb l where l.id=" + id).list().get(0);
        return linkToNetworkDb;
    }
}
