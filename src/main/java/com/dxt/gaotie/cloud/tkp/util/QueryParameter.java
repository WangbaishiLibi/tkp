package com.dxt.gaotie.cloud.tkp.util;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryParameter {

	
	private String cls;
	
	/*
	 * 分页
	 */
	private int offset;	//起始位置(从0开始)
	private int limit;	//每页记录数>0	(主参)
	private boolean hasPage;

	
	/*
	 * 查询条件
	 */
	private String queryCol;	//模糊查询 like
	private String queryVal;
	private List<String> queryCols = new ArrayList();	//匹配查询 =
	private List<String> queryVals = new ArrayList();
	private boolean hasQuery;
	private boolean multiQuery;
	
	/*
	 * 排序
	 */
	private String orderCol;
	private String orderVal;	//排序值只支持ASC和DESC	(主参)
	private boolean hasOrder;
	
	public static final List<String> orderVals = Arrays.asList("ASC", "DESC", "asc", "desc");



	
	public QueryParameter() {
		// TODO Auto-generated constructor stub

	}

	public QueryParameter(String cls, int offset, int limit){
		this.cls = cls;
		this.offset = offset;
		this.limit = limit;
	}

	public QueryParameter(Class cls, int offset, int limit){
		this.cls = cls.getSimpleName();
		this.offset = offset;
		this.limit = limit;
	}

	public QueryParameter(Class cls, int offset, int limit, String queryCol, String queryVal){
		this.cls = cls.getSimpleName();
		this.offset = offset;
		this.limit = limit;
		this.queryCol = queryCol;
		this.queryVal = queryVal;
		this.hasQuery = !StringUtils.isEmpty(queryCol);
	}
	
	

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}


	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getQueryCol() {
		return queryCol;
	}


	public void setQueryCol(String queryCol) {
		this.queryCol = queryCol;
	}


	public String getQueryVal() {
		return queryVal;
	}


	public void setQueryVal(String queryVal) {
		this.queryVal = queryVal;
		if(!StringUtils.isEmpty(queryVal)){
			this.hasQuery = true;
		}
	}


	public List<String> getQueryCols() {
		return queryCols;
	}

	public void setQueryCols(List<String> queryCols) {
		this.queryCols = queryCols;
	}

	public List<String> getQueryVals() {
		return queryVals;
	}

	public void setQueryVals(List<String> queryVals) {
		this.queryVals = queryVals;
//		this.multiQuery = (queryVals != null && queryVals.size()>0);
	}


	public static List<String> getOrderVals() {
		return orderVals;
	}

	public String getOrderCol() {
		return orderCol;
	}


	public void setOrderCol(String orderCol) {
		this.orderCol = orderCol;
	}


	public String getOrderVal() {
		return orderVal;
	}


	public void setOrderVal(String orderVal) {
		if(orderVals.contains(orderVal)){
			this.orderVal = orderVal;
			this.hasOrder = true;
		}
	}


	public boolean isHasPage() {
		return offset >= 0 ? true : hasPage;
	}

	public boolean isHasQuery() {
		return hasQuery;
	}

	public boolean isHasOrder() {
		return hasOrder;
	}

	public boolean isMultiQuery() {
		this.multiQuery = (this.queryCols != null && this.queryCols.size()>0);
		return multiQuery;
	}
	
	
}
