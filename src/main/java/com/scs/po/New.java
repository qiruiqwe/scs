package com.scs.po;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_new")
public class New {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String picture;
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date publishtime;

    private Integer views;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Club club;


    public New() {
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

}
