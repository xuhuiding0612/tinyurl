package com.myprojects.tinyurl.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "longurl", uniqueConstraints = { @UniqueConstraint(columnNames = {"id"})})
public class LongURL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @SequenceGenerator(name = "auto_gen", sequenceName = "A")
    private Long id;
    private String longURL;

    protected LongURL() {}

    public LongURL(String longURL) {
        this.longURL = longURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLongURL() {
        return longURL;
    }

    public void setLongURL(String longURL) {
        this.longURL = longURL;
    }
}
