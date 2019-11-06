//package com.noah_solutions.entity.oldEntity;
//
//import org.hibernate.annotations.DynamicUpdate;
//import org.hibernate.annotations.NotFound;
//import org.hibernate.annotations.NotFoundAction;
//import org.hibernate.annotations.SelectBeforeUpdate;
//
//import javax.persistence.*;
//import java.math.BigDecimal;
//
//@Entity(name="Itemsrelations")
//@Table(name="Itemsrelations")
//@DynamicUpdate
//@SelectBeforeUpdate
//public class ItemRelationsEntity {
//
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	@Column(name="id")
//	private Integer id;
//
////	@Column(name="Itemscolorid")
////	private Integer itemsColorId;
//
//	@OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
//	@JoinColumn(name="Itemscolorid")
//	@NotFound(action=NotFoundAction.IGNORE)
//	private  ItemColorEntity itemColorEntity;
//
//	@Column(name="Itemssizeid")
//	private Integer itemsSizeId;
//
////	@Column(name="Itemsspec1id")
////	private Integer itemsSpec1Id;
//
////	@Column(name="Itemsspec2id")
////	private Integer itemsSpec2Id;
////
////	@Column(name="Itemsspec3id")
////	private Integer itemsSpec3Id;
//
//	@OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
//	@JoinColumn(name="Itemsspec1id")
//	@NotFound(action=NotFoundAction.IGNORE)
//	private  ItemSpec1Entity itemSpec1Entity;
//
//	@OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
//	@JoinColumn(name="Itemsspec2id")
//	@NotFound(action= NotFoundAction.IGNORE)
//	private  ItemSpec2Entity itemSpec2Entity;
//
//	@OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
//	@NotFound(action=NotFoundAction.IGNORE)
//	@JoinColumn(name="Itemsspec3id")
//	private  ItemSpec3Entity itemSpec3Entity;
//
//	@Column(name="price", precision = 17, scale=2)
//	private BigDecimal price;
//
//	@Column(name="stock")
//	private Integer stock;
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
////	public Integer getItemsColorId() {
////		return itemsColorId;
////	}
////
////	public void setItemsColorId(Integer itemsColorId) {
////		this.itemsColorId = itemsColorId;
////	}
//
//
//	public ItemColorEntity getItemColorEntity() {
//		return itemColorEntity;
//	}
//
//	public void setItemColorEntity(ItemColorEntity itemColorEntity) {
//		this.itemColorEntity = itemColorEntity;
//	}
//
//	public Integer getItemsSizeId() {
//		return itemsSizeId;
//	}
//
//	public void setItemsSizeId(Integer itemsSizeId) {
//		this.itemsSizeId = itemsSizeId;
//	}
//
//	public ItemSpec1Entity getItemSpec1Entity() {
//		return itemSpec1Entity;
//	}
//
//	public void setItemSpec1Entity(ItemSpec1Entity itemSpec1Entity) {
//		this.itemSpec1Entity = itemSpec1Entity;
//	}
//
//	public ItemSpec2Entity getItemSpec2Entity() {
//		return itemSpec2Entity;
//	}
//
//	public void setItemSpec2Entity(ItemSpec2Entity itemSpec2Entity) {
//		this.itemSpec2Entity = itemSpec2Entity;
//	}
//
//	public ItemSpec3Entity getItemSpec3Entity() {
//		return itemSpec3Entity;
//	}
//
//	public void setItemSpec3Entity(ItemSpec3Entity itemSpec3Entity) {
//		this.itemSpec3Entity = itemSpec3Entity;
//	}
//
////	public Integer getItemsSpec1Id() {
////		return itemsSpec1Id;
////	}
////
////	public void setItemsSpec1Id(Integer itemsSpec1Id) {
////		this.itemsSpec1Id = itemsSpec1Id;
////	}
////
////	public Integer getItemsSpec2Id() {
////		return itemsSpec2Id;
////	}
////
////	public void setItemsSpec2Id(Integer itemsSpec2Id) {
////		this.itemsSpec2Id = itemsSpec2Id;
////	}
////
////	public Integer getItemsSpec3Id() {
////		return itemsSpec3Id;
////	}
////
////	public void setItemsSpec3Id(Integer itemsSpec3Id) {
////		this.itemsSpec3Id = itemsSpec3Id;
////	}
//
//	public BigDecimal getPrice() {
//		return price;
//	}
//
//	public void setPrice(BigDecimal price) {
//		this.price = price;
//	}
//
//	public Integer getStock() {
//		return stock;
//	}
//
//	public void setStock(Integer stock) {
//		this.stock = stock;
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
