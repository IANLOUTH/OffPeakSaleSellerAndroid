package com.appsauthority.appwiz.models;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class OrderDetailResponseObject implements Serializable {

	@SerializedName("errorCode")
	public String errorCode;
	
	@SerializedName("errorMessage")
	public String errorMessage;
	
	@SerializedName("data")
	public OrderObject data;
}
