package com.contact.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFilter;


@Entity
@Table(name = "contact")
//@JsonIgnoreProperties(value = {"id","add1"})
@JsonFilter("someBean")
public class Contact implements Serializable {




	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	

	@Size(min = 4,message = "Address should have atleast 4 characters")
	@Column(name="add_1")
	private String add1;
	
	@Column(name="add_2")
	private String add2;
	


	@Column(name="pin_Code")
	private int pinCode;
	
	@Column(name="country")
	private String country;
	
//	@JsonIgnore
	@Column(name="mobile_No")
	private String mobileNo;
	
	@Column(name="land_Mark")
	private String landMark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdd1() {
		return add1;
	}

	public void setAdd1(String add1) {
		this.add1 = add1;
	}

	public String getAdd2() {
		return add2;
	}

	public void setAdd2(String add2) {
		this.add2 = add2;
	}

	public int getPinCode() {
		return pinCode;
	}

	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}
	
	

}
