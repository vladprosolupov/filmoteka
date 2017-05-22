package com.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by 1rost on 5/22/2017.
 */
public class HibernateSession {

    private static SessionFactory sessionFactory = buildSessionFactory();

    protected static SessionFactory buildSessionFactory() {
        // A SessionFactory is set up once for an application!
//        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .configure() // configures settings from hibernate.cfg.xml
//                .build();
//        try {
//            sessionFactory = new MetadataSources( registry )
//                    .buildMetadata()
//                    .buildSessionFactory();
//        }
//        catch (Exception e) {
//            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
//            // so destroy it manually.
//            StandardServiceRegistryBuilder.destroy( registry );
//
//            throw new ExceptionInInitializerError("Initial SessionFactory failed" + e);
//        }
//        Configuration configuration = new Configuration();
//        configuration.configure();
//        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
//                configuration.getProperties()). build();
//        sessionFactory = configuration.buildSessionFactory(serviceRegistry)
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml").buildSessionFactory();
        return sessionFactory;

    }


    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

}
