package web.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.NetworkDb;
import web.model.NetworkJSON;

import java.util.List;

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

    public void saveOrUpdate(NetworkDb networkDb){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(networkDb);
    }

    public void delete(String id){
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from NetworkDb n where n.id=" + id).executeUpdate();
    }

    public List<NetworkDb> getAll(){
        Session session = sessionFactory.getCurrentSession();
        List<NetworkDb> result = session.createQuery("from NetworkDb").list();
        return result;
    }

    public NetworkDb convertToNetworkDb(NetworkJSON networkJSON){
        NetworkDb networkDb = new NetworkDb();
        networkDb.setNetworkLogo(networkJSON.getNetworkLogo());
        networkDb.setId(networkJSON.getId());
        networkDb.setNetworkName(networkJSON.getNetworkName());
        return networkDb;
    }

}
