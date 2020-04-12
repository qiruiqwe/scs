package com.scs.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_grade")
public class Grade {
    @Id
    @GeneratedValue
    private Long id;

    private String value;

    @OneToMany(mappedBy="grade")
    private List<User> users = new ArrayList<User>();



    public Grade() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
