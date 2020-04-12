package com.it_uatech.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "account")
public class AccountDataSet{

    @Id
    @Column(name = "user_login",nullable = false, unique = true)
    private String login;

    @Column(name = "password",nullable = false)
    private String password;

    public AccountDataSet(){}

    public AccountDataSet(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountDataSet)) return false;
        AccountDataSet that = (AccountDataSet) o;
        return login.equals(that.login) &&
                password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }

    @Override
    public String toString() {
        return "AccountDataSet{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
