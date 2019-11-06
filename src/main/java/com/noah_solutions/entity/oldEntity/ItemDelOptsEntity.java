//package com.noah_solutions.entity.oldEntity;
//
//import org.hibernate.annotations.DynamicUpdate;
//import org.hibernate.annotations.SelectBeforeUpdate;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.math.BigDecimal;
//
//@Entity(name="Deliveryopts")
//@Table(name="Deliveryopts")
//@DynamicUpdate
//@SelectBeforeUpdate
//public class ItemDelOptsEntity implements Serializable{
//
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	@Column(name="id")
//	private Integer id;
//
//	@Column(name="source")
//	private String source;
//
//	@Column(name="destination")
//	private String destination;
//
//	@Column(name="charge" , precision = 17, scale=2)
//	private BigDecimal charge;
//
//	@Column(name="est_days")
//	private int est_days;
//
//	@Column(name="unit_tc")
//	private String unit_tc;
//
//	@Column(name="unit_eng")
//	private String unit_eng;
//
///*	@Column(name="item_id")
//	private int item_id;*/
//
//	/*@ManyToOne(cascade=CascadeType.ALL)
//	@JoinColumn(name="id")*/
//
//	@ManyToOne(cascade=CascadeType.REFRESH,fetch = FetchType.LAZY, optional=true)
//	@JoinColumn(name="item_id", nullable=false)
//	private ItemsEntity item;
//
//	public Integer getId() {
//		return id;
//	}
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public String getSource() {
//		return source;
//	}
//	public void setSource(String source) {
//		this.source = source;
//	}
//
//	public String getDestination() {
//		return destination;
//	}
//	public void setDestination(String destination) {
//		this.destination = destination;
//	}
//	public BigDecimal getCharge() {
//		return charge;
//	}
//	public void setCharge(BigDecimal charge) {
//		this.charge = charge;
//	}
//	public int getEst_days() {
//		return est_days;
//	}
//	public void setEst_days(int est_days) {
//		this.est_days = est_days;
//	}
//	public String getUnit_tc() {
//		return unit_tc;
//	}
//	public void setUnit_tc(String unit_tc) {
//		this.unit_tc = unit_tc;
//	}
//	public String getUnit_eng() {
//		return unit_eng;
//	}
//	public void setUnit_eng(String unit_eng) {
//		this.unit_eng = unit_eng;
//	}
//
//	public ItemsEntity getItem() {
//		return item;
//	}
//	public void setItem(ItemsEntity item) {
//		this.item = item;
//	}
//
//
//
//
//
//}
