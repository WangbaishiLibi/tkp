package com.dxt.test;/**
 * Created by admin on 2018/10/29.
 */

import com.dxt.gaotie.cloud.tkp.TKPApplication;
import com.dxt.gaotie.cloud.tkp.dao.EntityDao;
import com.dxt.gaotie.cloud.tkp.util.Node;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ ClassName: TestBoot
 * @ Description: TODO
 * @ author：黎国敏，邵玉祥，丁志翔，吴坤，李碧，陈阳
 * @ date： 2018/10/29 20:05
 * @ version： V1.0.0
 * @ CopyRight：武汉点线通软件有限责任公司
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TKPApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestBoot {

    @Autowired
    EntityDao entityDao;




    public void test1(){
        Integer[] docIds = new Integer[]{1, 5, 6, 7, 8, 9, 10};

        for(int i=1; i<=docIds.length; i++){
            Integer bookId = i;
            List<Node> parters = listParter(docIds[i-1]);
            for(Node parter : parters){
            //    TkpCatalog tkpParter = new TkpCatalog(parter.getText(), "编", bookId, 0);

                for(Node chapter : parter.getChildren()){
                //    TkpCatalog tkpChapter = new TkpCatalog(chapter.getText(), "章", bookId, tkpParter.getId());

                    for(Node section : chapter.getChildren()){
                    //    TkpCatalog tkpSection = new TkpCatalog(section.getText(), "节", bookId, tkpChapter.getId());

                    }
                }

            }
        }
    }

    private List<Node> listParter(Integer docId){
        List<Node> nodes = new ArrayList<>();
        List<Map> parters = entityDao.createNativeQuery("select * from regulation.parter where doc_id=" + docId).getResultList();
        for(Map<String, Object> parter : parters){
            String pid = parter.get("id").toString();
            nodes.add(new Node(pid, parter.get("title").toString(), "",
                    listChapter(pid)));
        }
        return nodes;
    }


    private List<Node> listChapter(String pid){
        List<Node> nodes = new ArrayList<>();
        List<Map> parters = entityDao.createNativeQuery("select * from regulation.chapter where parter_id=" + pid).getResultList();
        for(Map<String, Object> parter : parters){
            String cid = parter.get("id").toString();
            nodes.add(new Node(pid, parter.get("title").toString(), "",
                    listSection(cid)));
        }
        return nodes;
    }

    private List<Node> listSection(String cid){
        List<Node> nodes = new ArrayList<>();
        List<Map> parters = entityDao.createNativeQuery("select * from regulation.section where chapter_id=" + cid).getResultList();
        for(Map<String, Object> parter : parters){
            nodes.add(new Node(parter.get("id").toString(), parter.get("title").toString(), "",
                    new ArrayList<Node>()));
        }
        return nodes;
    }





}
