package com.it_uatech.api.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "phones")
public class PhoneDataSet extends DataSet{

    @Column(name = "phone_number")
    private String number;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDataSet user;

    public PhoneDataSet() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public UserDataSet getUser() {
        return user;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "number='" + number + '\'' +
                ", user=" + user +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneDataSet)) return false;
        PhoneDataSet that = (PhoneDataSet) o;
        return number.equals(that.number) &&
                user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, user);
    }
}
