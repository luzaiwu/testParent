package com.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.entity.RelaxAmount;

import page.LazyDataLoader;
import page.PageDto;


public class RelaxAmountBeanPagenator extends LazyDataModel<RelaxAmount> implements Serializable{
	private static final long serialVersionUID = 1L;
	private LazyDataLoader<RelaxAmount>loader;
	public RelaxAmountBeanPagenator(LazyDataLoader<RelaxAmount>loader)
	{
		this.loader=loader;
	}
	public List<RelaxAmount> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
		PageDto<RelaxAmount> page = new PageDto<RelaxAmount>(pageSize, first, pageSize, sortField, sortOrder);
		loader.loadData(page);
		setRowCount(page.getResultCount());
		return page.getData();
	}
}
