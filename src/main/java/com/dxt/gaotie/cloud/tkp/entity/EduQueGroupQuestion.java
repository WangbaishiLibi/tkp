package com.dxt.gaotie.cloud.tkp.entity;

import javax.persistence.*;

/**
 * Created by admin on 2018/10/21.
 */
@Entity
@Table(name = "edu_que_group_question", schema = "", catalog = "tkp")
@IdClass(EduQueGroupQuestionPK.class)
public class EduQueGroupQuestion {
    private int groupId;
    private int questionId;

    @Id
    @Column(name = "group_id")
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Id
    @Column(name = "question_id")
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EduQueGroupQuestion that = (EduQueGroupQuestion) o;

        if (groupId != that.groupId) return false;
        if (questionId != that.questionId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = groupId;
        result = 31 * result + questionId;
        return result;
    }
}
