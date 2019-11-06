//package com.noah_solutions.entity.oldEntity;
//
//import org.hibernate.annotations.DynamicUpdate;
//import org.hibernate.annotations.SelectBeforeUpdate;
//
//import javax.persistence.*;
//
//@Entity(name="Itemvideo")
//@Table(name="Itemvideo")
//@DynamicUpdate
//@SelectBeforeUpdate
//public class ItemVideoEntity {
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
//
//
//}
