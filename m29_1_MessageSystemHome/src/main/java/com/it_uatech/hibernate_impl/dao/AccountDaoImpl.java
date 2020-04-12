package com.it_uatech.hibernate_impl.dao;

import com.it_uatech.api.dao.AccountDao;
import com.it_uatech.api.dao.AccountDaoException;
import com.it_uatech.api.dao.UserDaoException;
import com.it_uatech.api.model.AccountDataSet;
import com.it_uatech.api.model.UserDataSet;
import com.it_uatech.api.session.SessionManager;
import com.it_uatech.hibernate_impl.sessionmanager.DataBaseSessionHibernate;
import com.it_uatech.hibernate_impl.sessionmanager.SessionManagerHibernate;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class AccountDaoImpl implements AccountDao {

    private final SessionManagerHibernate sessionManager;

    public AccountDaoImpl(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void save(AccountDataSet account) {
        DataBaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            currentSession.getSession().saveOrUpdate(account);
        } catch (Exception ex) {
            throw new AccountDaoException(ex);
        }
    }

    @Override
    public Optional<AccountDataSet> findByLogin(String login) {
        DataBaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            CriteriaBuilder builder = currentSession.getSession().getCriteriaBuilder();
            CriteriaQuery<AccountDataSet> criteria = builder.createQuery(AccountDataSet.class);
            Root<AccountDataSet> from = criteria.from(AccountDataSet.class);
            criteria.where(builder.equal(from.get("login"), login));
            Query<AccountDataSet> query = currentSession.getSession().createQuery(criteria);
            return Optional.ofNullable(query.uniqueResult());
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
