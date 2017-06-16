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

}
