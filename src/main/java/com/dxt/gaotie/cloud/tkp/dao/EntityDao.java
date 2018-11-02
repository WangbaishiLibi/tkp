package com.dxt.gaotie.cloud.tkp.dao;

import com.dxt.gaotie.cloud.tkp.util.QueryParameter;
import com.dxt.gaotie.cloud.tkp.util.StringUtil;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/6/24.
 */
@Repository
public class EntityDao {

    @PersistenceContext
    private EntityManager em;


    public EntityDao() {

    }


    public EntityManager getEntityManager() {
        // TODO Auto-generated method stub
        return em;
    }

    public Query createQuery(String hql){
        return em.createQuery(hql);
    }

    public Query createNativeQuery(String sql){
        Query query = em.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query;
    }


    public Query createNativeQuery(String sql, Class cls){
        return em.createNativeQuery(sql, cls);
    }



    public Map findByPage(QueryParameter parameter) {
        return findByPage(parameter, false);
    }

    /*
     * 分页查询
     */
    public Map findByPage(QueryParameter parameter, boolean isLike) {
        Map result = new HashMap();
        String cls = parameter.getCls();
        if(StringUtils.isEmpty(cls))    return result;

        String qryStr = "";

        if(StringUtil.hasField(parameter.getCls(), "able")){
            qryStr = " where able=1 ";
        }else{
            qryStr = " where 1=1 ";
        }

        if(parameter.isHasQuery()){
            qryStr += " and " + parameter.getQueryCol() + " like '%" + parameter.getQueryVal() + "%' ";
        }

        if(parameter.isMultiQuery()){
            for(int i = 0; i<parameter.getQueryCols().size(); i++){
                if(i < parameter.getQueryVals().size()){
                    if(isLike){
                        qryStr += " and " + parameter.getQueryCols().get(i) + " like '%" + parameter.getQueryVals().get(i) + "%' ";
                    }else{
                        qryStr += " and " + parameter.getQueryCols().get(i) + "='" + parameter.getQueryVals().get(i) + "' ";
                    }
                }
            }
        }


        String ordStr = "";

        if(parameter.isHasOrder()){
            ordStr = " order by " + parameter.getOrderCol() + " " + parameter.getOrderVal() + " ";
        }

        Query query = em.createQuery("from " + cls + "  " + qryStr + ordStr);
        if(parameter.isHasPage()){
            query.setMaxResults(parameter.getLimit()).setFirstResult(parameter.getOffset());
        }
        result.put("rows", query.getResultList());
        result.put("total", (Long)em.createQuery("select count(*) from " + cls + qryStr).getSingleResult());
        return result;
    }


    /*
     * 多条件查询
     */
    public List findByMultiQuery( QueryParameter parameter) {
        String qryStr = "";

        if(StringUtil.hasField(parameter.getCls(), "able")){
            qryStr = " where able=1 ";
        }else{
            qryStr = " where 1=1 ";
        }

        if(parameter.isHasQuery()){
            qryStr += " and " + parameter.getQueryCol() + " like '%" + parameter.getQueryVal() + "%' ";
        }

        if(parameter.isMultiQuery()){
            for(int i = 0; i<parameter.getQueryCols().size(); i++){
                if(i < parameter.getQueryVals().size()){
                    qryStr += " and " + parameter.getQueryCols().get(i) + "='" + parameter.getQueryVals().get(i) + "' ";
                }
            }
        }

        String ordStr = "";
        boolean ut = StringUtil.hasField(parameter.getCls(), "createTime");
        if(parameter.isHasOrder()){
            ordStr = " order by " + parameter.getOrderCol() + " " + parameter.getOrderVal() + " ";
            if(ut) ordStr += ", updateTime desc ";
        }else if(ut){
            ordStr = " order by updateTime desc ";
        }

        Query query = em.createQuery("from " + parameter.getCls() + "  " + qryStr + ordStr);
        if(parameter.isHasPage()){
            query.setMaxResults(parameter.getLimit()).setFirstResult(parameter.getOffset());
        }
        return query.getResultList();
    }



    public Object findOne(Class cls, Integer id){
        return em.find(cls, id);
    }

    public List findAll(Class cls){
        return createQuery("from " + cls.getSimpleName()).getResultList();
    }

    public List findList(String hql){
        return createQuery(hql).getResultList();
    }

    public List findList(String hql, Integer offset, Integer limit){
        return createQuery(hql).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    public Object saveOrUpdate(Object entity){
        entity = em.merge(entity);
        em.flush();
        return entity;
    }

    public void delete(Class cls, Integer id){
        em.remove(findOne(cls, id));
        em.flush();
    }
}
