package com.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name="relax_amount")
public class RelaxAmount implements Serializable{
	private static final long serialVersionUID = 1L;
	/*public  static final String STATUS_ACTIVE ="active";
	public  static final String STATUS_DISABLE ="disable";*/
	private Long id;
	private Long basicDateGroupId;
	private Long categoryId;
	private Long elasticityId;
	private Long pattenId;
	
	@Id
	@SequenceGenerator(name = "ID", sequenceName = "RELAX_AMOUNT_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ID")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "BASIC_DATE_GROUP_ID")
	public Long getBasicDateGroupId() {
		return basicDateGroupId;
	}
	public void setBasicDateGroupId(Long basicDateGroupId) {
		this.basicDateGroupId = basicDateGroupId;
	}
	@Column(name = "CATEGORY_ID")
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	@Column(name = "ELASTICITY_ID")
	public Long getElasticityId() {
		return elasticityId;
	}
	public void setElasticityId(Long elasticityId) {
		this.elasticityId = elasticityId;
	}
	@Column(name = "PATTEN_ID")
	public Long getPattenId() {
		return pattenId;
	}
	public void setPattenId(Long pattenId) {
		this.pattenId = pattenId;
	}
}
