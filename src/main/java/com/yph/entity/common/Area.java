package com.yph.entity.common;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * 地域实体
 * @author Administrator
 *
 */
@Entity
@Table(name="area")
@EntityListeners(AuditingEntityListener.class)
public class Area extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="province_id")
	private Long provinceId;//省份id
	
	@Column(name="city_id")
	private Long cityId;//城市id
	
	@Column(name="name",nullable=false,length=32)
	private String name;//地域名称
	
	@Column(name="is_show")
	private Boolean isShow = true;//是否显示

	@Column(name = "level")
	private int level;//区域等级

	public Boolean getShow() {
		return isShow;
	}

	public void setShow(Boolean show) {
		isShow = show;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	@Override
	public String toString() {
		return "Area [provinceId=" + provinceId + ", cityId=" + cityId
				+ ", name=" + name + ", isShow=" + isShow + "]";
	}

}
