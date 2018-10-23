package com.dxt.gaotie.cloud.tkp.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 2018/10/21.
 */
@Entity
@Table(name = "tkp_book", schema = "", catalog = "tkp")
public class TkpBook {
    private int id;
    private String title;
    private String abstr;
    private String docPath;
    private String url;
    private String tag;
    private Integer categoryId;
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
    @Column(name = "doc_path")
    public String getDocPath() {
        return docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

        TkpBook tkpBook = (TkpBook) o;

        if (id != tkpBook.id) return false;
        if (able != null ? !able.equals(tkpBook.able) : tkpBook.able != null) return false;
        if (abstr != null ? !abstr.equals(tkpBook.abstr) : tkpBook.abstr != null) return false;
        if (categoryId != null ? !categoryId.equals(tkpBook.categoryId) : tkpBook.categoryId != null) return false;
        if (createTime != null ? !createTime.equals(tkpBook.createTime) : tkpBook.createTime != null) return false;
        if (docPath != null ? !docPath.equals(tkpBook.docPath) : tkpBook.docPath != null) return false;
        if (tag != null ? !tag.equals(tkpBook.tag) : tkpBook.tag != null) return false;
        if (title != null ? !title.equals(tkpBook.title) : tkpBook.title != null) return false;
        if (updateTime != null ? !updateTime.equals(tkpBook.updateTime) : tkpBook.updateTime != null) return false;
        if (url != null ? !url.equals(tkpBook.url) : tkpBook.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (abstr != null ? abstr.hashCode() : 0);
        result = 31 * result + (docPath != null ? docPath.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (able != null ? able.hashCode() : 0);
        return result;
    }
}
