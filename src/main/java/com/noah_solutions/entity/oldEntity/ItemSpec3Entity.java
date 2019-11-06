//package com.noah_solutions.entity.oldEntity;
//
//import org.hibernate.annotations.DynamicUpdate;
//import org.hibernate.annotations.SelectBeforeUpdate;
//
//import javax.persistence.*;
//
//@Entity(name="Itemsspec3")
//@Table(name="Itemsspec3")
//@DynamicUpdate
//@SelectBeforeUpdate
//public class ItemSpec3Entity {
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
//	@Column(name="items_id",updatable = false,insertable=false)
//	private String items_id;
//	@ManyToOne(cascade=CascadeType.REFRESH,fetch = FetchType.LAZY, optional=true)
//	@JoinColumn(name="items_id", nullable=false)
//	private ItemsEntity item;
//
//	public String getItems_id() {
//		return items_id;
//	}
//
//	public void setItems_id(String items_id) {
//		this.items_id = items_id;
//	}
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
//}
