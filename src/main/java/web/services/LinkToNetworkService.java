package web.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.LinkToNetworkDb;
import web.model.LinkToNetworkJSON;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Service("LinkToNetworkService")
@Transactional
public class LinkToNetworkService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    @Autowired
    private NetworkService networkService;

    public LinkToNetworkDb getLinkWithId(String id){
        Session session = sessionFactory.getCurrentSession();
        LinkToNetworkDb linkToNetworkDb =
                (LinkToNetworkDb) session.createQuery("from LinkToNetworkDb l where l.id=" + id).list().get(0);
        return linkToNetworkDb;
    }

    public int save(LinkToNetworkDb linkToNetworkDb){
        Session session = sessionFactory.getCurrentSession();
        session.save(linkToNetworkDb);
        return linkToNetworkDb.getId();
    }

    public int update(LinkToNetworkDb linkToNetworkDb){
        Session session = sessionFactory.getCurrentSession();
        session.update(linkToNetworkDb);
        return linkToNetworkDb.getId();
    }

    public void delete(String id){
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from LinkToNetworkDb l where l.id=" + id).executeUpdate();
    }

//    public LinkToNetworkDb convert(LinkToNetworkJSON linkToNetworkJSON){
//        LinkToNetworkDb linkToNetworkDb = new LinkToNetworkDb();
//        linkToNetworkDb.setId(linkToNetworkJSON.getId());
//        linkToNetworkDb.setLink(linkToNetworkJSON.getLink());
//        linkToNetworkDb.setNetworkByIdNetwork(
//                networkService.getNetworkWithId(Integer.toString(linkToNetworkJSON.getIdNetwork())));
//        return linkToNetworkDb;
//    }
//
    public Set<LinkToNetworkDb> createSetOfLinkToNetwork(Map<String, Integer> links){
        Set<LinkToNetworkDb> linkToNetworkDbSet = new HashSet<>();
        for(Map.Entry<String, Integer> m : links.entrySet()){
            LinkToNetworkDb linkToNetworkDb = new LinkToNetworkDb();
            linkToNetworkDb.setLink(m.getKey());
            linkToNetworkDb.setNetworkByIdNetwork(
                    networkService.getNetworkWithId(Integer.toString(m.getValue())));
            linkToNetworkDbSet.add(linkToNetworkDb);
        }
        return linkToNetworkDbSet;
    }
}
