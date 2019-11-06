//package com.noah_solutions.entity.oldEntity;
//
//import org.hibernate.annotations.DynamicUpdate;
//import org.hibernate.annotations.SelectBeforeUpdate;
//
//import javax.persistence.*;
//
//@Entity(name="Itemphotos")
//@Table(name="Itemphotos")
//@DynamicUpdate
//@SelectBeforeUpdate
//public class ItemPhotosEntity {
//
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	@Column(name="id")
//	private int id;
//
//	@Column(name="path")
//	private String path;
//
//	@Column(name="name")
//	private String name;
//
//	@Column(name="thumbnails")
//	private String thumbnails;
//
//	@Column(name="sort_order")
//	private int sort_order;
//
//	@Column(name="type_id")
//	private int type_id;
//
//	@ManyToOne(cascade=CascadeType.REFRESH,fetch = FetchType.LAZY, optional=true)
//	@JoinColumn(name="item_id", nullable=false)
//	private ItemsEntity item;
//
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public String getPath() {
//		return path;
//	}
//
//	public void setPath(String path) {
//		this.path = path;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getThumbnails() {
//		return thumbnails;
//	}
//
//	public void setThumbnails(String thumbnails) {
//		this.thumbnails = thumbnails;
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
//	public int getSort_order() {
//		return sort_order;
//	}
//
//	public void setSort_order(int sort_order) {
//		this.sort_order = sort_order;
//	}
//
//	public int getType_id() {
//		return type_id;
//	}
//
//	public void setType_id(int type_id) {
//		this.type_id = type_id;
//	}
//
//
//
//
//
//
//}
