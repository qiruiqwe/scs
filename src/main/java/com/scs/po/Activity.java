package com.scs.po;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_activity")
public class Activity {
    @Id
    @GeneratedValue
    private Long id;

    private  String title;

    private String description;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;

    private String picture;

    private String local;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date activitytime;

    private Integer views;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<User> users = new ArrayList<User>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Club club;

    public Activity() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Date getActivitytime() {
        return activitytime;
    }

    public void setActivitytime(Date activitytime) {
        this.activitytime = activitytime;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
