package com.dxt.gaotie.cloud.tkp.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 2018/10/21.
 */
@Entity
@Table(name = "v_tkp_article", schema = "", catalog = "tkp")
public class VTkpArticle {
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
    private String lev1Title;
    private int lev1Id;
    private String lev2Title;
    private int lev2Id;
    private String lev3Title;
    private int lev3Id;

    @Id
    @GeneratedValue
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

    @Basic
    @Column(name = "lev1_title")
    public String getLev1Title() {
        return lev1Title;
    }

    public void setLev1Title(String lev1Title) {
        this.lev1Title = lev1Title;
    }

    @Basic
    @Column(name = "lev1_id")
    public int getLev1Id() {
        return lev1Id;
    }

    public void setLev1Id(int lev1Id) {
        this.lev1Id = lev1Id;
    }

    @Basic
    @Column(name = "lev2_title")
    public String getLev2Title() {
        return lev2Title;
    }

    public void setLev2Title(String lev2Title) {
        this.lev2Title = lev2Title;
    }

    @Basic
    @Column(name = "lev2_id")
    public int getLev2Id() {
        return lev2Id;
    }

    public void setLev2Id(int lev2Id) {
        this.lev2Id = lev2Id;
    }

    @Basic
    @Column(name = "lev3_title")
    public String getLev3Title() {
        return lev3Title;
    }

    public void setLev3Title(String lev3Title) {
        this.lev3Title = lev3Title;
    }

    @Basic
    @Column(name = "lev3_id")
    public int getLev3Id() {
        return lev3Id;
    }

    public void setLev3Id(int lev3Id) {
        this.lev3Id = lev3Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VTkpArticle that = (VTkpArticle) o;

        if (id != that.id) return false;
        if (lev1Id != that.lev1Id) return false;
        if (lev2Id != that.lev2Id) return false;
        if (lev3Id != that.lev3Id) return false;
        if (able != null ? !able.equals(that.able) : that.able != null) return false;
        if (abstr != null ? !abstr.equals(that.abstr) : that.abstr != null) return false;
        if (catalogId != null ? !catalogId.equals(that.catalogId) : that.catalogId != null) return false;
        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (lev1Title != null ? !lev1Title.equals(that.lev1Title) : that.lev1Title != null) return false;
        if (lev2Title != null ? !lev2Title.equals(that.lev2Title) : that.lev2Title != null) return false;
        if (lev3Title != null ? !lev3Title.equals(that.lev3Title) : that.lev3Title != null) return false;
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
        result = 31 * result + (lev1Title != null ? lev1Title.hashCode() : 0);
        result = 31 * result + lev1Id;
        result = 31 * result + (lev2Title != null ? lev2Title.hashCode() : 0);
        result = 31 * result + lev2Id;
        result = 31 * result + (lev3Title != null ? lev3Title.hashCode() : 0);
        result = 31 * result + lev3Id;
        return result;
    }
}
