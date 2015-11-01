/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;



/**
 *
 * @author Sikandar A.A
 */
public interface IService {
    public void closeSession(Session session);
    public boolean save(Object obj) throws Exception;
    public boolean update(Object obj);   
    public boolean merge(Object obj);    
    public boolean delete(Object obj);
    public boolean saveOrUpdate(Object bean);
    public Session getCurrentSession();
	public boolean delete(List<?> beans) throws ConstraintViolationException;
	public boolean save(List<?> beanList);
	public boolean update(List<?> beanList);
	public boolean saveOrUpdate(List<?> beanList);
	public boolean merge(List<?> beanList);
}
