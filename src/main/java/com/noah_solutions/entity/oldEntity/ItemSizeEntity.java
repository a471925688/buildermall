//package com.noah_solutions.entity.oldEntity;
//
//import org.hibernate.annotations.DynamicUpdate;
//import org.hibernate.annotations.SelectBeforeUpdate;
//
//import javax.persistence.*;
//
//@Entity(name="Itemssize")
//@Table(name="Itemssize")
//@DynamicUpdate
//@SelectBeforeUpdate
//public class ItemSizeEntity {
//
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	@Column(name="id")
//	private Integer id;
//
//	@Column(name="name_tc")
//	private String name_tc;
//
//	@Column(name="name_eng")
//	private String name_eng;
//
//	@ManyToOne(cascade=CascadeType.REFRESH,fetch = FetchType.LAZY, optional=true)
//	@JoinColumn(name="items_id", nullable=false)
//	private ItemsEntity item;
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public String getName_tc() {
//		return name_tc;
//	}
//
//	public void setName_tc(String name_tc) {
//		this.name_tc = name_tc;
//	}
//
//	public String getName_eng() {
//		return name_eng;
//	}
//
//	public void setName_eng(String name_eng) {
//		this.name_eng = name_eng;
//	}
//
//	public ItemsEntity getItem() {
//		return item;
//	}
//
//	public void setItem(ItemsEntity item) {
//		this.item = item;
//	}
//
//
//
//
//
//
//}
