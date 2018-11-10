package com.dxt.gaotie.cloud.tkp.entity;/**
 * Created by admin on 2018/11/10.
 */

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @ ClassName: UUser
 * @ Description: TODO
 * @ author：黎国敏，邵玉祥，丁志翔，吴坤，李碧，陈阳
 * @ date： 2018/11/10 21:23
 * @ version： V1.0.0
 * @ CopyRight：武汉点线通软件有限责任公司
 */
@Entity
@Table(name = "u_user", schema = "", catalog = "tkp")
public class UUser {
    private int id;
    private Integer empno;
    private String password;
    private Timestamp createTime;
    private Timestamp updateTime;

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
    @Column(name = "empno")
    public Integer getEmpno() {
        return empno;
    }

    public void setEmpno(Integer empno) {
        this.empno = empno;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UUser uUser = (UUser) o;

        if (id != uUser.id) return false;
        if (createTime != null ? !createTime.equals(uUser.createTime) : uUser.createTime != null) return false;
        if (empno != null ? !empno.equals(uUser.empno) : uUser.empno != null) return false;
        if (password != null ? !password.equals(uUser.password) : uUser.password != null) return false;
        if (updateTime != null ? !updateTime.equals(uUser.updateTime) : uUser.updateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (empno != null ? empno.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }
}
