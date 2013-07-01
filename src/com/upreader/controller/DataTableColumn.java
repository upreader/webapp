package com.upreader.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DataTableColumn {
	@JsonProperty("mData")
	private String sTitle;
	
	@JsonIgnore
	private String sClass;
	
	public DataTableColumn(String sTitle) {
		this(sTitle, null);
	}
	
	public DataTableColumn(String sTitle, String sClass) {
		this.sTitle = sTitle;
		this.sClass = sClass;
	}

	public String getsTitle() {
		return sTitle;
	}

	public void setsTitle(String sTitle) {
		this.sTitle = sTitle;
	}

	public String getsClass() {
		return sClass;
	}

	public void setsClass(String sClass) {
		this.sClass = sClass;
	}
	
	
}
