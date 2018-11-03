package com.dxt.gaotie.cloud.tkp.entity;/**
 * Created by admin on 2018/11/2.
 */

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @ ClassName: SearchModel
 * @ Description: TODO
 * @ author：黎国敏，邵玉祥，丁志翔，吴坤，李碧，陈阳
 * @ date： 2018/11/2 16:22
 * @ version： V1.0.0
 * @ CopyRight：武汉点线通软件有限责任公司
 */
@MappedSuperclass
public abstract class SearchModel implements Serializable{

    protected int id;
    protected String title;
    protected String abstr;

    protected String link;
    protected String origin;

    protected Timestamp createTime;
    protected Timestamp updateTime;


    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract String getTitle() ;

    public abstract void setTitle(String title);

    public abstract String getAbstr() ;

    public abstract void setAbstr(String abstr) ;

    public abstract String getLink() ;

    public abstract void setLink(String link) ;

    public abstract String getOrigin() ;

    public abstract void setOrigin(String origin) ;

    @Basic
    @Column(name = "create_time", insertable = false, updatable = false)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time", insertable = false, updatable = false)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
