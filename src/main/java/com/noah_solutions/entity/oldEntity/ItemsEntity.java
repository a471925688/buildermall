//package com.noah_solutions.entity.oldEntity;
//
//import org.hibernate.annotations.DynamicUpdate;
//import org.hibernate.annotations.SelectBeforeUpdate;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Entity(name="Items")
//@Table(name="Items")
//@DynamicUpdate
//@SelectBeforeUpdate
//public class ItemsEntity implements Serializable{
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
//	@Transient
//	private Integer cattype_id;
//	@Transient
//	private String cattype_Name;
//	@Transient
//	private Integer cattype_lv;
//
//	@Column(name="short_desc_tc")
//	private String short_desc_tc;
//
//	@Column(name="short_desc_eng")
//	private String short_desc_eng;
//
//	@Column(name="unit_tc")
//	private String unit_tc;
//
//	@Column(name="unit_eng")
//	private String unit_eng;
//
//	@Column(name="brands_id")
//	private Integer brands_id;
//
//	@Column(name="raw_material_tc")
//	private String raw_material_tc;
//
//	@Column(name="raw_material_eng")
//	private String raw_material_eng;
//
//	@Column(name="features_tc")
//	private String features_tc;
//
//	@Column(name="features_eng")
//	private String features_eng;
//
//	@Column(name="content")
//	private String content;
//
//	@Column(name="status")
//	private Integer status;
//
//	@Column(name="sold_no", updatable=false)
//	private Integer sold_no;
//
//	@Column(name="sold_order", updatable=false)
//	private Integer sold_order;
//
//	@Column(name="price", precision = 17, scale=2)
//	private BigDecimal price;
//
//	@Column(name="discount_price", precision = 17, scale=2)
//	private BigDecimal discount_price;
//
//	@Column(name="stock")
//	private Integer stock;
//
//	@Column(name="min_order")
//	private Integer min_order;
//
////	@Column(name="cattypelv1_id")
////	private Integer cattypelv1_id;
//
////	@Column(name="cattypelv2_id")
////	private Integer cattypelv2_id;
//
////	@Column(name="cattypelv3_id")
////	private Integer cattypelv3_id;
//
//	@OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
//	@JoinColumn(name="cattypelv1_id")
//	private  Cattype cattype1;
//
//	@OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
//	@JoinColumn(name="cattypelv2_id")
//	private  Cattype cattype2;
//
//	@OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
//	@JoinColumn(name="cattypelv3_id")
//	private  Cattype cattype3;
//
//	@Column(name="s_id")
//	private Integer s_id;
//
//	@Column(name="dt_id")
//	private Integer dt_id;
//
//
//
//	/*
//	@Column(name="photo360Lst")
//	private List<ImageBean> photo360Lst;*/
//
//	//@Column(name="updatedDate", columnDefinition="DATETIME default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP" , insertable=true, updatable=false)
//
//
////	@Column(name="updatedDate", columnDefinition="timestamp default CURRENT_TIMESTAMP" , nullable = false, insertable=true, updatable=true)
////	private String  updatedDate;
////
////	//@Column(name="createdDate", columnDefinition="DATETIME default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP" ,insertable=true, updatable=true)
////
////	@Column(name="createdDate", columnDefinition="timestamp default CURRENT_TIMESTAMP" , nullable = false,insertable=true, updatable=false)
////	private String createdDate;
//
////	@OneToMany(cascade=CascadeType.ALL)
////	@JoinColumn(name="item_id",referencedColumnName="item_id", insertable=false, updatable=false)
//
///*	@OneToMany(mappedBy="item" , cascade=CascadeType.ALL,fetch = FetchType.LAZY)
//	private Set<ItemCSPEntity> cspSet;*/
//
//	@OneToMany(mappedBy="item" , cascade=CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval=true)
//	private List<ItemColorEntity> colorSet;
//
//	@OneToMany(mappedBy="item" , cascade=CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval=true)
//	private List<ItemSizeEntity> sizeSet;
//
//	@OneToMany(mappedBy="item" , cascade=CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval=true)
//	private List<ItemSpec1Entity> spec1Set;
//
//	@OneToMany(mappedBy="item" , cascade=CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval=true)
//	private List<ItemSpec2Entity> spec2Set;
//
//	@OneToMany(mappedBy="item" , cascade=CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval=true)
//	private List<ItemSpec3Entity> spec3Set;
//
//	@OneToMany(mappedBy="item" , cascade=CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval=true)
//	private List<ItemRelationsEntity> relationsSet;
//
//
//	@OneToMany(mappedBy="item" , cascade=CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval=true)
//	private Set<ItemDelOptsEntity> delOptsSet;
//
//	@OneToMany(mappedBy="item" , cascade=CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval=true)
//	@OrderBy("sort_order ASC ")
//	private Set<ItemPhotosEntity> photosSet;
//
//	@OneToMany(mappedBy="item" , cascade=CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval=true)
//	private Set<ItemFilesEntity> resumeSet;
//
//	@OneToMany(mappedBy="item" , cascade=CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval=true)
//	private Set<ItemVideoEntity> videoSet;
//
///*	@OneToMany(mappedBy="item" , cascade=CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval=true)
//	private List<ItemDelOptsEntity> delOptsLst;*/
///*	@OneToMany(mappedBy="item" , cascade=CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval=true)
//	private Set<ItemPhotosEntity> photo360Set;*/
//
//
//
//
//	public ItemsEntity(){
//		this.colorSet =  new ArrayList<ItemColorEntity>();
//		this.sizeSet  =  new ArrayList<ItemSizeEntity>();
//		this.spec1Set  =  new ArrayList<ItemSpec1Entity>();
//		this.spec2Set  =  new ArrayList<ItemSpec2Entity>();
//		this.spec3Set  =  new ArrayList<ItemSpec3Entity>();
//		this.relationsSet  =  new ArrayList<ItemRelationsEntity>();
//		//this.cspSet = new HashSet<ItemCSPEntity>();
//		//this.delOptsLst = new ArrayList<ItemDelOptsEntity>();
//		this.delOptsSet = new HashSet<ItemDelOptsEntity>();
//		this.photosSet = new HashSet<ItemPhotosEntity>();
//		this.resumeSet = new HashSet<ItemFilesEntity>();
//		this.videoSet = new HashSet<ItemVideoEntity>();
//
//	}
//
//	public ItemsEntity(ItemsEntity itemsEntity,String catType_Name,Integer catType_lv){
//
//		this.id=itemsEntity.getId();
//		this.name_tc=itemsEntity.getName_tc();
//		this.name_eng=itemsEntity.getName_eng();
//		this.price=itemsEntity.getPrice();
//		this.discount_price=itemsEntity.getDiscount_price();
//		this.sold_no=itemsEntity.getSold_no();
//		this.sold_order=itemsEntity.getSold_order();
//		this.status=itemsEntity.getStatus();
////		this.catTypeLv1_id=itemsEntity.getCatTypeLv1_id();
////		this.catTypeLv2_id=itemsEntity.getCatTypeLv2_id();
////		this.catTypeLv3_id=itemsEntity.getCatTypeLv3_id();
//		this.s_id=itemsEntity.getS_id();
//		this.brands_id=itemsEntity.getBrands_id();
//		this.dt_id=itemsEntity.getDt_id();
////		this.updatedDate=itemsEntity.getUpdatedDate();
////		this.createdDate=itemsEntity.getCreatedDate();
//		this.colorSet =  itemsEntity.getColorSet();
//		this.sizeSet  =  itemsEntity.getSizeSet();
//		this.spec1Set  =  itemsEntity.getSpec1Set();
//		this.spec2Set  =  itemsEntity.getSpec2Set();
//		this.spec3Set  =  itemsEntity.getSpec3Set();
//		this.relationsSet  =  itemsEntity.getRelationsSet();
//		//this.cspSet = new HashSet<ItemCSPEntity>();
//		//this.delOptsLst = new ArrayList<ItemDelOptsEntity>();
//		this.delOptsSet = itemsEntity.getDelOptsSet();
//		this.photosSet = itemsEntity.getPhotosSet();
//		this.resumeSet = itemsEntity.getResumeSet();
//		this.videoSet = itemsEntity.getVideoSet();
////		this.catType_Name = catType_Name;
////		this.catType_lv=catType_lv;
//	}
//
//
//	public Integer getCattype_id() {
//		return cattype_id;
//	}
//
//	public void setCattype_id(Integer cattype_id) {
//		this.cattype_id = cattype_id;
//	}
//
//	public String getCattype_Name() {
//		return cattype_Name;
//	}
//
//	public void setCattype_Name(String cattype_Name) {
//		this.cattype_Name = cattype_Name;
//	}
//
//	public Integer getCattype_lv() {
//		return cattype_lv;
//	}
//
//	public void setCattype_lv(Integer cattype_lv) {
//		this.cattype_lv = cattype_lv;
//	}
//
//	public Set<ItemFilesEntity> getResumeSet() {
//		return resumeSet;
//	}
//
//	public void setResumeSet(Set<ItemFilesEntity> resumeSet) {
//		this.resumeSet = resumeSet;
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
//	public String getShort_desc_tc() {
//		return short_desc_tc;
//	}
//
//	public void setShort_desc_tc(String short_desc_tc) {
//		this.short_desc_tc = short_desc_tc;
//	}
//
//	public String getShort_desc_eng() {
//		return short_desc_eng;
//	}
//
//	public void setShort_desc_eng(String short_desc_eng) {
//		this.short_desc_eng = short_desc_eng;
//	}
//
//	public String getUnit_tc() {
//		return unit_tc;
//	}
//
//	public void setUnit_tc(String unit_tc) {
//		this.unit_tc = unit_tc;
//	}
//
//	public String getUnit_eng() {
//		return unit_eng;
//	}
//
//	public void setUnit_eng(String unit_eng) {
//		this.unit_eng = unit_eng;
//	}
//
//
//	public String getRaw_material_tc() {
//		return raw_material_tc;
//	}
//
//	public void setRaw_material_tc(String raw_material_tc) {
//		this.raw_material_tc = raw_material_tc;
//	}
//
//	public String getRaw_material_eng() {
//		return raw_material_eng;
//	}
//
//	public void setRaw_material_eng(String raw_material_eng) {
//		this.raw_material_eng = raw_material_eng;
//	}
//
//	public String getFeatures_tc() {
//		return features_tc;
//	}
//
//	public void setFeatures_tc(String features_tc) {
//		this.features_tc = features_tc;
//	}
//
//	public String getFeatures_eng() {
//		return features_eng;
//	}
//
//	public void setFeatures_eng(String features_eng) {
//		this.features_eng = features_eng;
//	}
//
//	public String getContent() {
//		return content;
//	}
//
//	public void setContent(String content) {
//		this.content = content;
//	}
//
//	public Integer getStatus() {
//		return status;
//	}
//
//	public void setStatus(Integer status) {
//		this.status = status;
//	}
//
//	public Integer getSold_no() {
//		return sold_no;
//	}
//
//	public void setSold_no(Integer sold_no) {
//		this.sold_no = sold_no;
//	}
//
//	public Integer getSold_order() {
//		return sold_order;
//	}
//
//	public void setSold_order(Integer sold_order) {
//		this.sold_order = sold_order;
//	}
//
//	public BigDecimal getPrice() {
//		return price;
//	}
//
//	public void setPrice(BigDecimal price) {
//		this.price = price;
//	}
//
//	public BigDecimal getDiscount_price() {
//		return discount_price;
//	}
//
//	public void setDiscount_price(BigDecimal discount_price) {
//		this.discount_price = discount_price;
//	}
//
////	public Integer getCatTypeLv1_id() {
////		return catTypeLv1_id;
////	}
////
////	public void setCatTypeLv1_id(Integer catTypeLv1_id) {
////		this.catTypeLv1_id = catTypeLv1_id;
////	}
////
////	public Integer getCatTypeLv2_id() {
////		return catTypeLv2_id;
////	}
////
////	public void setCatTypeLv2_id(Integer catTypeLv2_id) {
////		this.catTypeLv2_id = catTypeLv2_id;
////	}
////
////	public Integer getCatTypeLv3_id() {
////		return catTypeLv3_id;
////	}
////
////	public void setCatTypeLv3_id(Integer catTypeLv3_id) {
////		this.catTypeLv3_id = catTypeLv3_id;
////	}
//
//	public Integer getS_id() {
//		return s_id;
//	}
//
//	public void setS_id(Integer s_id) {
//		this.s_id = s_id;
//	}
//
//	public Integer getDt_id() {
//		return dt_id;
//	}
//
//	public void setDt_id(Integer dt_id) {
//		this.dt_id = dt_id;
//	}
//
//	public Cattype getCattype1() {
//		return cattype1;
//	}
//
//	public void setCattype1(Cattype cattype1) {
//		this.cattype1 = cattype1;
//	}
//
//	public Cattype getCattype2() {
//		return cattype2;
//	}
//
//	public void setCattype2(Cattype cattype2) {
//		this.cattype2 = cattype2;
//	}
//
//	public Cattype getCattype3() {
//		return cattype3;
//	}
//
//	public void setCattype3(Cattype cattype3) {
//		this.cattype3 = cattype3;
//	}
////	public Integer getCattypelv1_id() {
////		return cattypelv1_id;
////	}
////
////	public void setCattypelv1_id(Integer cattypelv1_id) {
////		this.cattypelv1_id = cattypelv1_id;
////	}
////
////	public Integer getCattypelv2_id() {
////		return cattypelv2_id;
////	}
////
////	public void setCattypelv2_id(Integer cattypelv2_id) {
////		this.cattypelv2_id = cattypelv2_id;
////	}
////
////	public Integer getCattypelv3_id() {
////		return cattypelv3_id;
////	}
////
////	public void setCattypelv3_id(Integer cattypelv3_id) {
////		this.cattypelv3_id = cattypelv3_id;
////	}
////	public Integer getCatTypelv1_id() {
////		return catTypelv1_id;
////	}
////
////	public void setCatTypelv1_id(Integer catTypelv1_id) {
////		this.catTypelv1_id = catTypelv1_id;
////	}
////
////	public Integer getCatTypelv2_id() {
////		return catTypelv2_id;
////	}
////
////	public void setCatTypelv2_id(Integer catTypelv2_id) {
////		this.catTypelv2_id = catTypelv2_id;
////	}
////
////	public Integer getCatTypelv3_id() {
////		return catTypelv3_id;
////	}
////
////	public void setCatTypelv3_id(Integer catTypelv3_id) {
////		this.catTypelv3_id = catTypelv3_id;
////	}
////	public String getUpdatedDate() {
////		return updatedDate;
////	}
////
////	public void setUpdatedDate(String updatedDate) {
////		this.updatedDate = updatedDate;
////	}
////
////	public String getCreatedDate() {
////		return createdDate;
////	}
////
////	public void setCreatedDate(String createdDate) {
////		this.createdDate = createdDate;
////	}
//
///*	public Set<ItemCSPEntity> getCspSet() {
//		return cspSet;
//	}
//
//	public void setCspSet(Set<ItemCSPEntity> cspSet) {
//		this.cspSet = cspSet;
//	}
//
//	public void addCsp(ItemCSPEntity obj){
//		this.cspSet.add(obj);
//	}*/
//
//	public List<ItemColorEntity> getColorSet() {
//		return colorSet;
//	}
//
//	public void setColorSet(List<ItemColorEntity> colorSet) {
//		this.colorSet = colorSet;
//	}
//
//	public void addColor(ItemColorEntity obj){
//		this.colorSet.add(obj);
//	}
//
//	public List<ItemSizeEntity> getSizeSet() {
//		return sizeSet;
//	}
//
//	public void setSizeSet(List<ItemSizeEntity> sizeSet) {
//		this.sizeSet = sizeSet;
//	}
//
//	public void addSize(ItemSizeEntity obj){
//		this.sizeSet.add(obj);
//	}
//
//	public List<ItemSpec1Entity> getSpec1Set() {
//		return spec1Set;
//	}
//
//	public void setSpec1Set(List<ItemSpec1Entity> spec1Set) {
//		this.spec1Set = spec1Set;
//	}
//
//	public void addSpec1(ItemSpec1Entity obj){
//		this.spec1Set.add(obj);
//	}
//
//	public List<ItemSpec2Entity> getSpec2Set() {
//		return spec2Set;
//	}
//
//	public void setSpec2Set(List<ItemSpec2Entity> spec2Set) {
//		this.spec2Set = spec2Set;
//	}
//
//	public void addSpec2(ItemSpec2Entity obj){
//		this.spec2Set.add(obj);
//	}
//
//	public List<ItemSpec3Entity> getSpec3Set() {
//		return spec3Set;
//	}
//
//	public void setSpec3Set(List<ItemSpec3Entity> spec3Set) {
//		this.spec3Set = spec3Set;
//	}
//
//	public void addSpec3(ItemSpec3Entity obj){
//		this.spec3Set.add(obj);
//	}
//
//	public List<ItemRelationsEntity> getRelationsSet() {
//		return relationsSet;
//	}
//
//	public void setRelationsSet(List<ItemRelationsEntity> relationsSet) {
//		this.relationsSet = relationsSet;
//	}
//
//
//	public void addRelations(ItemRelationsEntity obj){
//		this.relationsSet.add(obj);
//	}
//
//	public Set<ItemDelOptsEntity> getDelOptsSet() {
//		return delOptsSet;
//	}
//
//	public void setDelOptsSet(Set<ItemDelOptsEntity> delOptsSet) {
//		this.delOptsSet = delOptsSet;
//	}
//
//
//	public void addDelOpts(ItemDelOptsEntity obj){
//		this.delOptsSet.add(obj);
//	}
//
//	public Set<ItemPhotosEntity> getPhotosSet() {
//		return photosSet;
//	}
//
//
//	public Integer getBrands_id() {
//		return brands_id;
//	}
//
//	public void setBrands_id(Integer brands_id) {
//		this.brands_id = brands_id;
//	}
//
//	public void setPhotosSet(Set<ItemPhotosEntity> photosSet) {
//		this.photosSet.clear();
//		this.photosSet.addAll(photosSet);
//	}
//
//	public Set<ItemFilesEntity> getFileSet() {
//		return resumeSet;
//	}
//
//	public void setFileSet(Set<ItemFilesEntity> resumeSet) {
//		this.resumeSet.clear();
//		this.resumeSet.addAll(resumeSet);
//	}
//
//	public Set<ItemVideoEntity> getVideoSet() {
//		return videoSet;
//	}
//
///*	public List<ItemDelOptsEntity> getDelOptsLst() {
//		return delOptsLst;
//	}
//
//	public void setDelOptsLst(List<ItemDelOptsEntity> delOptsLst) {
//		this.delOptsLst = delOptsLst;
//	}*/
//
//
//	public void setVideoSet(Set<ItemVideoEntity> videoSet) {
//		this.videoSet.clear();
//		this.videoSet.addAll(videoSet);
//	}
//
//
//	public Set<ItemFilesEntity> getSpecSet() {
//		return resumeSet;
//	}
//
//	public void setSpecSet(Set<ItemFilesEntity> resumeSet) {
//		this.resumeSet.clear();
//		this.resumeSet.addAll(resumeSet);
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
//	public Integer getMin_order() {
//		return min_order;
//	}
//
//	public void setMin_order(Integer min_order) {
//		this.min_order = min_order;
//	}
//
///*	public int getStock_temp() {
//		return stock_temp;
//	}
//
//	public void setStock_temp(int stock_temp) {
//		this.stock_temp = stock_temp;
//	}*/
//
//
//
//
//
//}
