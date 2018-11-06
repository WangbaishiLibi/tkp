package com.dxt.gaotie.cloud.tkp.service;/**
 * Created by admin on 2018/11/2.
 */

import com.dxt.gaotie.cloud.tkp.entity.TkpBook;
import com.dxt.gaotie.cloud.tkp.entity.TkpCatalog;
import com.dxt.gaotie.cloud.tkp.repository.BookRepository;
import com.dxt.gaotie.cloud.tkp.repository.CatalogRepository;
import com.dxt.gaotie.cloud.tkp.util.Node;
import com.dxt.gaotie.cloud.tkp.view.CatalogView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @ ClassName: CatalogService
 * @ Description: TODO
 * @ author：黎国敏，邵玉祥，丁志翔，吴坤，李碧，陈阳
 * @ date： 2018/11/2 17:01
 * @ version： V1.0.0
 * @ CopyRight：武汉点线通软件有限责任公司
 */
@Service
@Transactional
public class CatalogService {

    @Autowired
    CatalogRepository catalogRepository;

    @Autowired
    BookRepository bookRepository;

    public CatalogView catalogView(Integer id){
        CatalogView catalogView = new CatalogView();
        catalogView.catalog = catalogRepository.findOne(id);
        catalogView.parents = new ArrayList<>();
        int parentId = catalogView.catalog.getParentId();
        int i = 6;
        while(--i > 0 && parentId > 0){
            TkpCatalog parent = catalogRepository.findOne(parentId);
            if(parent == null)  break;
            catalogView.parents.add(parent);
            parentId = parent.getParentId();
        }
        return catalogView;
    }

    public List<Node> allCatalogTree(){
        List<Node> rootList = new ArrayList<>();
        List<TkpBook> books = bookRepository.findAll();
        for(TkpBook book : books){
            rootList.add(getBookTreeNode(book));
        }
        return rootList;
    }

    public List<Node> bookCatalogTree(Integer bookId){
        List<Node> rootList = new ArrayList<>();
        TkpBook book = bookRepository.findOne(bookId);
        if(book == null)    return rootList;
        rootList.add(getBookTreeNode(book));
        return rootList;
    }

    private Node getBookTreeNode(TkpBook book){
        List<TkpCatalog> catalogs = catalogRepository.findByParentAndBook(0, book.getId());
        Node bookNode = new Node(String.valueOf(book.getId()), book.getTitle(), book.getUrl(), new ArrayList<Node>());
        for(TkpCatalog catalog : catalogs){
            bookNode.getChildren().add(new Node(String.valueOf(catalog.getId()), catalog.getTitle(), catalog.getType(), childrenList(catalog.getId())));
        }
        return bookNode;
    }

    public List<Node> catalogTree(Integer parentId){
        List<Node> root = new ArrayList<>();
        TkpCatalog catalog = catalogRepository.findOne(parentId);
        if(catalog == null)    return root;

        root.add(new Node(String.valueOf(catalog.getId()), catalog.getTitle(), catalog.getType(), childrenList(catalog.getId())));

        return root;
    }


    public List<Node> childrenList(Integer parentId){
        List<Node> children = new ArrayList<>();
        List<TkpCatalog> catalogs = catalogRepository.findByParent(parentId);
        for(TkpCatalog catalog : catalogs){
            children.add(new Node(String.valueOf(catalog.getId()), catalog.getTitle(), catalog.getType(), childrenList(catalog.getId())));
        }
        return children;
    }




}
