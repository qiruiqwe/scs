package com.scs.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_club")
public class Club {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creattime;

    private Boolean status;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<User> users = new ArrayList<User>();

    @OneToOne
    private User creater;

    @OneToMany(mappedBy = "club")
    private List<New> news = new ArrayList<New>();

    @OneToMany(mappedBy = "club")
    private List<Activity> activities = new ArrayList<Activity>();

    public Club() {
    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getCreater() {
        return creater;
    }

    public void setCreater(User creater) {
        this.creater = creater;
    }

    public List<New> getNews() {
        return news;
    }

    public void setNews(List<New> news) {
        this.news = news;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
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

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Club{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", creattime=" + creattime +
                ", status=" + status +
                '}';
    }
}
