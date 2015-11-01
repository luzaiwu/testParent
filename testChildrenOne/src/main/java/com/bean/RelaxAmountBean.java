package com.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.component.datatable.DataTable;

import com.entity.RelaxAmount;
import com.service.Service;

import page.LazyDataLoader;
import page.PageDto;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "relaxAmountBean")
@ViewScoped
public class RelaxAmountBean extends Service implements Serializable, LazyDataLoader<RelaxAmount> {

	private static final long serialVersionUID = 1L;
	private RelaxAmountBeanPagenator relaxAmountBeanPagenator;
	private boolean isFirst = true;

	@PostConstruct
	public void init() {
		relaxAmountBeanPagenator = new RelaxAmountBeanPagenator(this);
	}

	public void search() {
		isFirst = true;
	}

	public RelaxAmountBeanPagenator getRelaxAmountBeanPagenator() {
		return relaxAmountBeanPagenator;
	}

	public void setRelaxAmountBeanPagenator(RelaxAmountBeanPagenator relaxAmountBeanPagenator) {
		this.relaxAmountBeanPagenator = relaxAmountBeanPagenator;
	}

	public void loadData(PageDto<RelaxAmount> page) {
		List<RelaxAmount> list = new ArrayList<RelaxAmount>();
		if (isFirst) {
			page.setStart(0);
			DataTable d = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
					.findComponent("relaxAmountFromId:relaxAmountTableId");
			d.setFirst(0);
			isFirst = false;
		}
		list = relaxAmountBeanService.findAllRelaxAmount(page);
	}

}
