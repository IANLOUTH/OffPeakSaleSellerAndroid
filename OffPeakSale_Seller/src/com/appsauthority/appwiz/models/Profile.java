package com.appsauthority.appwiz.models;

/* Copyright (C)
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Kevin Irish Antonio <irish.antonio@yahoo.com>, February 2014
 */
import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Profile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("fname")
	private String firstName;

	@SerializedName("phone_num")
	private String phone_num;
	
	@SerializedName("email")
	private String email;

	@SerializedName("country")
	private String country;
	
	@SerializedName("id")
	public String id;
	
	@SerializedName("company_name")
	public String company_name;
	
	@SerializedName("password")
	public String password;
	
	@SerializedName("address")
	public String address;
	
	@SerializedName("phone")
	public String phone;
	
	@SerializedName("retailer_id")
	public String retailer_id;
	
	@SerializedName("status")
	public String status;
	
	
	@SerializedName("orders_sold")
	public String orders_sold;
	
	@SerializedName("orders_redeemed")
	public String orders_redeemed;
	

	public Profile() {

	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getPhone_num() {
		return phone_num;
	}


	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getCompany_name() {
		return company_name;
	}


	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getRetailer_id() {
		return retailer_id;
	}


	public void setRetailer_id(String retailer_id) {
		this.retailer_id = retailer_id;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getOrders_sold() {
		return orders_sold;
	}


	public void setOrders_sold(String orders_sold) {
		this.orders_sold = orders_sold;
	}


	public String getOrders_redeemed() {
		return orders_redeemed;
	}


	public void setOrders_redeemed(String orders_redeemed) {
		this.orders_redeemed = orders_redeemed;
	}

	
	
	


}
