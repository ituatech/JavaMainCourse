package com.it_uatech.hibernate_impl.sessionmanager;

import com.it_uatech.api.session.SessionManager;
import com.it_uatech.api.session.SessionManagerException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SessionManagerHibernate implements SessionManager {

    private DataBaseSessionHibernate session;
    private final SessionFactory sessionFactory;

    public SessionManagerHibernate(SessionFactory sessionFactory) {
        if (sessionFactory == null) {
            throw new SessionManagerException("SessionFactory is null");
        }
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void beginSession() {
        try {
            session = new DataBaseSessionHibernate(sessionFactory.openSession());
        } catch (Exception ex) {
            throw new SessionManagerException(ex);
        }
    }

    @Override
    public void commitSession() {
        checkSessionAndTransaction();
        try {
            session.getTransaction().commit();
            session.getSession().close();
        } catch (Exception ex) {
            throw new SessionManagerException(ex);
        }
    }

    @Override
    public void rollbackSession() {
        checkSessionAndTransaction();
        try {
            session.getTransaction().rollback();
            session.getSession().close();
        } catch (Exception ex) {
            throw new SessionManagerException(ex);
        }
    }

    @Override
    public DataBaseSessionHibernate getCurrentSession() {
        checkSessionAndTransaction();
        return session;
    }

    DataBaseSessionHibernate getHibernateSession() {
        return session;
    }

    @Override
    public void close() {
        try {
            if (session == null) {
                return;
            }

            Session sess = session.getSession();
            if (sess == null || !sess.isConnected()) {
                return;
            }

            Transaction trans = session.getTransaction();
            if (trans == null || !trans.isActive()) {
                sess.close();
                return;
            }
            session.close();
            session = null;
        } catch (Exception ex) {
            throw new SessionManagerException(ex);
        }
    }

    private void checkSessionAndTransaction() {
        if (session == null) {
            throw new SessionManagerException("DatabaseSession not opened ");
        }
        Session sess = session.getSession();
        if (sess == null || !sess.isConnected()) {
            throw new SessionManagerException("Session not opened ");
        }

        Transaction transaction = session.getTransaction();
        if (transaction == null || !transaction.isActive()) {
            throw new SessionManagerException("Transaction not opened ");
        }
    }

    @Override
    public void shutdown() {
        close();
        sessionFactory.close();
    }
}
