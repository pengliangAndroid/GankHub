package com.wstrong.mygank.data.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by pengl on 2016/9/21.
 */
@Entity
public class Collection {

    @Property
    private String url;

    @Id
    private Long id;

    @Property
    private String title;


    @Generated(hash = 1580476555)
    public Collection(String url, Long id, String title) {
        this.url = url;
        this.id = id;
        this.title = title;
    }

    @Generated(hash = 1149123052)
    public Collection() {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
