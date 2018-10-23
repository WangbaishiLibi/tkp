package com.dxt.gaotie.cloud.tkp.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 2018/10/21.
 */
@Entity
@Table(name = "tkp_article", schema = "", catalog = "tkp")
public class TkpArticle {
    private int id;
    private String title;
    private String abstr;
    private String content;
    private String tag;
    private Integer categoryId;
    private Integer catalogId;
    private Integer views;
    private Integer likes;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Boolean able;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "abstr")
    public String getAbstr() {
        return abstr;
    }

    public void setAbstr(String abstr) {
        this.abstr = abstr;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "tag")
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Basic
    @Column(name = "category_id")
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "catalog_id")
    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }

    @Basic
    @Column(name = "views")
    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    @Basic
    @Column(name = "likes")
    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "able")
    public Boolean getAble() {
        return able;
    }

    public void setAble(Boolean able) {
        this.able = able;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TkpArticle that = (TkpArticle) o;

        if (id != that.id) return false;
        if (able != null ? !able.equals(that.able) : that.able != null) return false;
        if (abstr != null ? !abstr.equals(that.abstr) : that.abstr != null) return false;
        if (catalogId != null ? !catalogId.equals(that.catalogId) : that.catalogId != null) return false;
        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (likes != null ? !likes.equals(that.likes) : that.likes != null) return false;
        if (tag != null ? !tag.equals(that.tag) : that.tag != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (views != null ? !views.equals(that.views) : that.views != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (abstr != null ? abstr.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (catalogId != null ? catalogId.hashCode() : 0);
        result = 31 * result + (views != null ? views.hashCode() : 0);
        result = 31 * result + (likes != null ? likes.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (able != null ? able.hashCode() : 0);
        return result;
    }
}
