package com.dxt.gaotie.cloud.tkp.entity;

import javax.persistence.*;

/**
 * Created by admin on 2018/10/21.
 */
@Entity
@Table(name = "edu_question_group", schema = "", catalog = "tkp")
public class EduQuestionGroup {
    private int id;
    private String groupName;
    private int type;
    private Integer paperId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "group_name")
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Basic
    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Basic
    @Column(name = "paper_id")
    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EduQuestionGroup that = (EduQuestionGroup) o;

        if (id != that.id) return false;
        if (type != that.type) return false;
        if (groupName != null ? !groupName.equals(that.groupName) : that.groupName != null) return false;
        if (paperId != null ? !paperId.equals(that.paperId) : that.paperId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        result = 31 * result + type;
        result = 31 * result + (paperId != null ? paperId.hashCode() : 0);
        return result;
    }
}
