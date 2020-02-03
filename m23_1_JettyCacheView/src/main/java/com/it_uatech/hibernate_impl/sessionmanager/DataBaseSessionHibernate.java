package com.it_uatech.hibernate_impl.sessionmanager;

import com.it_uatech.api.session.DatabaseSession;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DataBaseSessionHibernate implements DatabaseSession {

    private final Session session;
    private final Transaction transaction;

    public DataBaseSessionHibernate(Session session) {
        this.session = session;
        this.transaction = session.beginTransaction();
    }

    public Session getSession() {
        return session;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void close() {
        if (transaction.isActive()){
            transaction.commit();
        }
        session.close();
    }
}
