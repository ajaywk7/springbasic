package com.socialmedia.socialmedia;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Image {

    @Id @GeneratedValue
    private long id;

    private String name ;

    public Image(){}

    public Image(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
