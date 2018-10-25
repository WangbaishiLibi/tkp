package com.dxt.gaotie.cloud.tkp.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by admin on 2018/10/21.
 */
@Entity
@Table(name = "edu_test_paper", schema = "", catalog = "tkp")
public class EduTestPaper {
    private int id;
    private String paperName;
    private int duration;
    private String intro;
    private Boolean able;
    private EduPaperGroup paperGroup;
    private List<EduQuestionGroup>  questionGroups;
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
    @Column(name = "paper_name")
    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    @Basic
    @Column(name = "duration")
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "intro")
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }


    @Basic
    @Column(name = "able")
    public Boolean getAble() {
        return able;
    }

    public void setAble(Boolean able) {
        this.able = able;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    public EduPaperGroup getPaperGroup() {
        return paperGroup;
    }

    public void setPaperGroup(EduPaperGroup paperGroup) {
        this.paperGroup = paperGroup;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "paper_id")
    public List<EduQuestionGroup> getQuestionGroups() {
        return questionGroups;
    }

    public void setQuestionGroups(List<EduQuestionGroup> questionGroups) {
        this.questionGroups = questionGroups;
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

        EduTestPaper that = (EduTestPaper) o;

        if (duration != that.duration) return false;
        if (id != that.id) return false;
        if (able != null ? !able.equals(that.able) : that.able != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (intro != null ? !intro.equals(that.intro) : that.intro != null) return false;
        if (paperName != null ? !paperName.equals(that.paperName) : that.paperName != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (paperName != null ? paperName.hashCode() : 0);
        result = 31 * result + duration;
        result = 31 * result + (intro != null ? intro.hashCode() : 0);
        result = 31 * result + (able != null ? able.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }
}
