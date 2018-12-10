package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.HibernateUtil;

import java.io.Serializable;

public class DaoImpl<T> {

    public T saveOrUpdate(T t){
        Session session = HibernateUtil.getInstance().getSession();
        Transaction transaction = null;

        try {
            session.beginTransaction();
            session.saveOrUpdate(t);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction!=null) {
                transaction.rollback();
            }
        }

        return t;
    }

    @NotNull
    public T load(Serializable id){
        Session session = HibernateUtil.getInstance().getSession();
        Transaction transaction = session.getTransaction();
        T t = null;
        try {
            t = (T) session.load(t.getClass(),id);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
        return t;
    }

    @Nullable
    public T find(Serializable id){
        Session session = HibernateUtil.getInstance().getSession();
        Transaction transaction = session.getTransaction();
        T t = null;
        try {
            t = (T) session.get(t.getClass(),id);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
        return t;
    }
}
