package com.it_uatech.hibernate_impl.dao;

import com.it_uatech.api.dao.UserDao;
import com.it_uatech.api.dao.UserDaoException;
import com.it_uatech.api.model.UserDataSet;
import com.it_uatech.api.session.SessionManager;
import com.it_uatech.hibernate_impl.sessionmanager.DataBaseSessionHibernate;
import com.it_uatech.hibernate_impl.sessionmanager.SessionManagerHibernate;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private final SessionManagerHibernate sessionManager;

    public UserDaoImpl(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public long save(UserDataSet user) {
        DataBaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            if (user.getId() > 0) {
                currentSession.getSession().merge(user);
            } else {
                currentSession.getSession().persist(user);
            }
            return user.getId();
        } catch (Exception ex) {
            throw new UserDaoException(ex);
        }
    }

    @Override
    public void save(List<UserDataSet> user) {
        user.forEach(this::save);
    }

    @Override
    public Optional<UserDataSet> findById(long id) {
        DataBaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getSession().find(UserDataSet.class, id));
        } catch (Exception ex) {
            throw new UserDaoException(ex);
        }
    }

    @Override
    public Optional<UserDataSet> findByName(String name) {
        DataBaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            CriteriaBuilder builder = currentSession.getSession().getCriteriaBuilder();
            CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
            Root<UserDataSet> from = criteria.from(UserDataSet.class);
            criteria.where(builder.equal(from.get("name"), name));
            Query<UserDataSet> query = currentSession.getSession().createQuery(criteria);
            return Optional.ofNullable(query.uniqueResult());
        } catch (Exception ex) {
            throw new UserDaoException(ex);
        }
    }

    @Override
    public String getLocalStatus() {
        DataBaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return currentSession.getTransaction().getStatus().name();
        } catch (Exception ex) {
            throw new UserDaoException(ex);
        }
    }

    @Override
    public List<UserDataSet> findAll() {
        DataBaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            CriteriaBuilder builder = currentSession.getSession().getCriteriaBuilder();
            CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
            criteria.from(UserDataSet.class);
            return currentSession.getSession().createQuery(criteria).list();
        } catch (Exception ex) {
            throw new UserDaoException(ex);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
