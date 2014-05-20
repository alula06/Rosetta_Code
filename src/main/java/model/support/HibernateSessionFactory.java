package model.support;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Zi sheng Wu
 */

public class HibernateSessionFactory {
    
    private static String CONFIC_FILE_LOCATION = "hibernate.cfg.xml";
    private static final ThreadLocal<Session>threadLocal = new ThreadLocal<Session>();
    private static Configuration configuration = new Configuration();
    private static SessionFactory sessionFactory;
    
    static {
        try {
            configuration.configure(CONFIC_FILE_LOCATION);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Session getSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        if (session == null || !session.isOpen()) {
            session = (sessionFactory != null) ? sessionFactory.openSession() : null;
            threadLocal.set(session);
        }
        return session;
    }

}
