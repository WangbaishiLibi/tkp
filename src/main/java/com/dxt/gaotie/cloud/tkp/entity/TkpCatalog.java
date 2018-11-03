package com.dxt.gaotie.cloud.tkp.entity;

import javax.persistence.*;

/**
 * Created by admin on 2018/10/21.
 */
@Entity
@Table(name = "tkp_catalog", schema = "", catalog = "tkp")
public class TkpCatalog extends SearchModel{

    private String tag;
    private Integer seq = 0;
    private String type;
    private Integer parentId;
    private TkpBook book;

    public TkpCatalog() {
    }

//    public TkpCatalog(String title, String type, Integer bookId, Integer parentId) {
//        this.title = title;
//        this.type = type;
//        this.bookId = bookId;
//        this.parentId = parentId;
//    }



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

    @Transient
    @Override
    public String getLink() {
        return "/catalog/" + id;
    }

    @Override
    public void setLink(String link) {
        this.link = link;
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
    @Column(name = "seq")
    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "parent_id")
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @ManyToOne
    @JoinColumn(name = "book_id")
    public TkpBook getBook() {
        return book;
    }

    public void setBook(TkpBook book) {
        this.book = book;
    }

    @Transient
    @Override
    public String getOrigin() {
        if(origin != null)  return origin;
        origin = "本地规章目录";
        if(book != null){
            origin = "<a href=\""+book.getUrl()+"\" target=\"_blank\">" + origin +"</a>";
        }
        return origin;
    }

    @Override
    public void setOrigin(String origin) {
        this.origin = origin;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TkpCatalog that = (TkpCatalog) o;

        if (id != that.id) return false;
        if (abstr != null ? !abstr.equals(that.abstr) : that.abstr != null) return false;
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
        if (seq != null ? !seq.equals(that.seq) : that.seq != null) return false;
        if (tag != null ? !tag.equals(that.tag) : that.tag != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (abstr != null ? abstr.hashCode() : 0);
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        result = 31 * result + (seq != null ? seq.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        return result;
    }
}
