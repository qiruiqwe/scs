package com.scs.po;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String nickname;

    @NotNull
    private String phone;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registertime;

    @NotNull
    private Boolean sex;

    @NotNull
    private String email;

    @ManyToMany(mappedBy = "users")
    private List<Club> joinclubs = new ArrayList<Club>();

    @ManyToMany(mappedBy = "users")
    private List<Activity> joinactivities = new ArrayList<Activity>();

    @ManyToOne(cascade =CascadeType.PERSIST )
    private Academy academy;

    @ManyToOne(cascade =CascadeType.PERSIST )
    private Grade grade;

    public User() {
    }

    public List<Activity> getJoinactivities() {
        return joinactivities;
    }

    public void setJoinactivities(List<Activity> joinactivities) {
        this.joinactivities = joinactivities;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Grade getGrade() {
        return grade;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Club> getJoinclubs() {
        return joinclubs;
    }

    public void setJoinclubs(List<Club> joinclubs) {
        this.joinclubs = joinclubs;
    }

    public Academy getAcademy() {
        return academy;
    }

    public void setAcademy(Academy academy) {
        this.academy = academy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getRegistertime() {
        return registertime;
    }

    public void setRegistertime(Date registertime) {
        this.registertime = registertime;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", registertime=" + registertime +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", joinclubs=" + joinclubs +
                ", academy=" + academy +
                '}';
    }
}
