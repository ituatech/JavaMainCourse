package com.it_uatech.dto;

import java.io.Serializable;
import java.util.Objects;

public class AccountDTO implements Serializable {

    private String login;
    private String password;

    public AccountDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDTO that = (AccountDTO) o;
        return login.equals(that.login) &&
                password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}
