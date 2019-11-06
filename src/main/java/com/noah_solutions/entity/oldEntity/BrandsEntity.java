//package com.noah_solutions.entity.oldEntity;
//
//import org.hibernate.annotations.DynamicUpdate;
//import org.hibernate.annotations.SelectBeforeUpdate;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity(name="brands")
//@Table(name="brands")
//@DynamicUpdate
//@SelectBeforeUpdate
//public class BrandsEntity {
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
//	@Column(name="logoPath")
//	private String logoPath;
//
//	@Column(name="logoFileName")
//	private String logoFileName;
//
//	@Column(name="status")
//	private int status;
//
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="updatedDate", columnDefinition="timestamp default CURRENT_TIMESTAMP" , nullable = false, insertable=true, updatable=true)
//	private Date updatedDate;
//
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="createdDate", columnDefinition="timestamp default CURRENT_TIMESTAMP" , nullable = false,insertable=true, updatable=false)
//	private Date createdDate;
//
//	@Column(name="rank_plan_id")
//	private Integer rank_plan_id;
//
//	@Column(name="s_id")
//	private int s_id;
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
//	public String getLogoPath() {
//		return logoPath;
//	}
//
//	public void setLogoPath(String logoPath) {
//		this.logoPath = logoPath;
//	}
//
//	public String getLogoFileName() {
//		return logoFileName;
//	}
//
//	public void setLogoFileName(String logoFileName) {
//		this.logoFileName = logoFileName;
//	}
//
//	public int getStatus() {
//		return status;
//	}
//
//	public void setStatus(int status) {
//		this.status = status;
//	}
//
//	public Date getUpdatedDate() {
//		return updatedDate;
//	}
//
//	public void setUpdatedDate(Date updatedDate) {
//		this.updatedDate = updatedDate;
//	}
//
//	public Date getCreatedDate() {
//		return createdDate;
//	}
//
//	public void setCreatedDate(Date createdDate) {
//		this.createdDate = createdDate;
//	}
//
//	public Integer getRank_plan_id() {
//		return rank_plan_id;
//	}
//
//	public void setRank_plan_id(Integer rank_plan_id) {
//		this.rank_plan_id = rank_plan_id;
//	}
//
//	public int getS_id() {
//		return s_id;
//	}
//
//	public void setS_id(int s_id) {
//		this.s_id = s_id;
//	}
//
//}
