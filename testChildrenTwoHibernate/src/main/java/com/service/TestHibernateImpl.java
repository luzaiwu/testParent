package com.service;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entity.RelaxAmount;
import com.service.ServiceAdaptorImpl;
import com.service.ServiceAdaptorImpl.SessionWrapper;
public class TestHibernateImpl extends ServiceAdaptorImpl
{
//	@Autowired
	public void test()
	{
		SessionWrapper swrapper = null;
		swrapper=begin();
		try {
			String hql="select * from relax_amount";
			Query query=swrapper.getSession().createSQLQuery(hql).addEntity(RelaxAmount.class);
			List<RelaxAmount>list=query.list();
			System.out.println(list.get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		TestHibernateImpl hibernateImpl=new TestHibernateImpl();
		hibernateImpl.test();
	}
}
