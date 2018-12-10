package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static ThreadLocal<Session> threadLocal = new ThreadLocal<>();
    private SessionFactory sessionFactory;

    private static HibernateUtil hibernateUtil;

    /*  static {
        hibernateUtil = new HibernateUtil();
    }*/

    private HibernateUtil(){
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public static HibernateUtil getInstance(){
        if(hibernateUtil==null){
            hibernateUtil = new HibernateUtil();
        }
        return hibernateUtil;
    }

    public Session getSession(){
        Session session = threadLocal.get();
        if(session==null){
            session = sessionFactory.openSession();
            threadLocal.set(session);
        }
        return session;
    }

    public Session getSession(String database){
       return getSession();
    }
}
