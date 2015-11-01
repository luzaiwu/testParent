package com.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;

import com.entity.RelaxAmount;

import page.PageDto;

public class RelaxAmountBeanServiceImpl extends ServiceAdaptorImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	public List<RelaxAmount> findAllRelaxAmount(PageDto<RelaxAmount> page) {
		SessionWrapper swrapper = null;
		swrapper = begin();
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("SELECT * FROM relax_amount where 1=1 ");
			
			page.setResultCount(getSqlCount(hql.toString()));
			if (page.getSortField() != null) {
				String str = "";
				hql.append(" ORDER BY " + str + " " + page.getSortOrder());
			} else
				hql.append(" ORDER BY id desc");
			Query query = swrapper.getSession().createSQLQuery(hql.toString()).addEntity("", RelaxAmount.class);
			query.setFirstResult(page.getStart());
			query.setMaxResults(page.getLimit());
			page.setData(query.list());
			return (List<RelaxAmount>) page.getData();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			end(swrapper);
		}
		return null;
	}
}
