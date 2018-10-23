package com.dxt.gaotie.cloud.tkp.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by admin on 2018/10/21.
 */
public class EduQueGroupQuestionPK implements Serializable {
    private int groupId;
    private int questionId;

    @Column(name = "group_id")
    @Id
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Column(name = "question_id")
    @Id
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

        EduQueGroupQuestionPK that = (EduQueGroupQuestionPK) o;

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
