//package com.noah_solutions.entity.oldEntity;
//
//import org.hibernate.annotations.DynamicUpdate;
//import org.hibernate.annotations.SelectBeforeUpdate;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity(name = "Suppliers")
//@Table(name = "Suppliers")
//@DynamicUpdate
//@SelectBeforeUpdate
//public class SupplierUserEntity implements Serializable {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id")
//	private int id;
//
//	@Column(name = "name_tc")
//	private String name_tc;
//
//	@Column(name = "name_eng")
//	private String name_eng;
//
//	@Column(name = "addr")
//	private String addr;
//
////	@Column(name = "factoryAddr")
////	private String factoryAddr;
//
//	@Column(name = "contact_no")
//	private String contact_no;
//
//	@Column(name = "fax_no")
//	private String fax_no;
//
//	@Column(name = "email")
//	private String email;
//
//	@Column(name = "website")
//	private String website;
//
//	@Column(name = "nature")
//	private String nature;
//
//	@Column(name = "business_year")
//	private String business_year;
//
//	@Column(name = "m_director")
//	private String m_director;
//
//	@Column(name = "directors")
//	private String directors;
//
//	@Column(name = "owners")
//	private String owners;
//
//	@Column(name = "partners")
//	private String partners;
//
//	@Column(name = "employed_managerial")
//	private int employed_managerial;
//
//	@Column(name = "employed_clerical")
//	private int employed_clerical;
//
//	@Column(name = "employed_technical")
//	private int employed_technical;
//
//	@Column(name = "employed_workers")
//	private int employed_workers;
//
//	@Column(name = "details_good")
//	private String details_good;
//
//	@Column(name = "auth_agent_name")
//	private String auth_agent_name;
//
////	@Column(name = "isManufacturer")
////	private Boolean isManufacturer;
//
//	@Column(name = "bank_no")
//	private String bank_no;
//
//	@Column(name = "pwd")
//	private String pwd;
//
////	@Column(name = "updatedDate")
////	private String updatedDate;
////
////	@Column(name = "createdD	ate")
////	private String createdDate;
//
//	//规格
//	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
//	@JoinColumn(name="s_id")
//	private Set<ItemsEntity> itemsEntities = new HashSet<>();
//
//	public Set<ItemsEntity> getItemsEntities() {
//		return itemsEntities;
//	}
//
//	public void setItemsEntities(Set<ItemsEntity> itemsEntities) {
//		this.itemsEntities = itemsEntities;
//	}
//
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
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
//	public String getAddr() {
//		return addr;
//	}
//
//	public void setAddr(String addr) {
//		this.addr = addr;
//	}
//
////	public String getFactoryAddr() {
////		return factoryAddr;
////	}
////
////	public void setFactoryAddr(String factoryAddr) {
////		this.factoryAddr = factoryAddr;
////	}
//
//	public String getContact_no() {
//		return contact_no;
//	}
//
//	public void setContact_no(String contact_no) {
//		this.contact_no = contact_no;
//	}
//
//	public String getFax_no() {
//		return fax_no;
//	}
//
//	public void setFax_no(String fax_no) {
//		this.fax_no = fax_no;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getWebsite() {
//		return website;
//	}
//
//	public void setWebsite(String website) {
//		this.website = website;
//	}
//
//	public String getNature() {
//		return nature;
//	}
//
//	public void setNature(String nature) {
//		this.nature = nature;
//	}
//
//	public String getBusiness_year() {
//		return business_year;
//	}
//
//	public void setBusiness_year(String business_year) {
//		this.business_year = business_year;
//	}
//
//	public String getM_director() {
//		return m_director;
//	}
//
//	public void setM_director(String m_director) {
//		this.m_director = m_director;
//	}
//
//	public String getDirector() {
//		return directors;
//	}
//
//	public void setDirector(String directors) {
//		this.directors = directors;
//	}
//
//	public String getOwners() {
//		return owners;
//	}
//
//	public void setOwners(String owners) {
//		this.owners = owners;
//	}
//
//	public String getPartners() {
//		return partners;
//	}
//
//	public void setPartners(String partners) {
//		this.partners = partners;
//	}
//
//	public int getEmployed_managerail() {
//		return employed_managerial;
//	}
//
//	public void setEmployed_managerail(int employed_managerail) {
//		this.employed_managerial = employed_managerail;
//	}
//
//	public int getEmployed_clerical() {
//		return employed_clerical;
//	}
//
//	public void setEmployed_clerical(int employed_clerical) {
//		this.employed_clerical = employed_clerical;
//	}
//
//	public int getEmployed_technical() {
//		return employed_technical;
//	}
//
//	public void setEmployed_technical(int employed_technical) {
//		this.employed_technical = employed_technical;
//	}
//
//	public int getEmployed_workers() {
//		return employed_workers;
//	}
//
//	public void setEmployed_workers(int employed_workers) {
//		this.employed_workers = employed_workers;
//	}
//
//	public String getDetails_good() {
//		return details_good;
//	}
//
//	public void setDetails_good(String details_good) {
//		this.details_good = details_good;
//	}
//
//	public String getAuth_agent_name() {
//		return auth_agent_name;
//	}
//
//	public void setAuth_agent_name(String auth_agent_name) {
//		this.auth_agent_name = auth_agent_name;
//	}
//
////	public Boolean getIsManufacturer() {
////		return isManufacturer;
////	}
////
////	public void setIsManufacturer(Boolean isManufacturer) {
////		this.isManufacturer = isManufacturer;
////	}
//
//	public String getBank_no() {
//		return bank_no;
//	}
//
//	public void setBank_no(String bank_no) {
//		this.bank_no = bank_no;
//	}
//
//	public String getPwd() {
//		return pwd;
//	}
//
//	public void setPwd(String pwd) {
//		this.pwd = pwd;
//	}
//
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
//}
