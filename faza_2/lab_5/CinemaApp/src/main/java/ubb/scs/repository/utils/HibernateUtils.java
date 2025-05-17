package ubb.scs.repository.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ubb.scs.domain.Client;
import ubb.scs.domain.Seat;

public class HibernateUtils {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            sessionFactory = createSessionFactory();
        }
        return sessionFactory;
    }

    private static SessionFactory createSessionFactory() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(Client.class)
                .addAnnotatedClass(Seat.class)
                .buildSessionFactory();
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
