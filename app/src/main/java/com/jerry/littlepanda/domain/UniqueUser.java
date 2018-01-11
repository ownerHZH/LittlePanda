package com.jerry.littlepanda.domain;

public class UniqueUser {
	Integer uniqueuser_id;
	String android_id;
	String imei;
	String useragent;
	String android_version;
	String device_model;
	String bundle_id;
	String app_version;
	String device_ip;
	String device_w;
	String device_h;
	String carrier;
	public Integer getUniqueuser_id() {
		return uniqueuser_id;
	}
	public void setUniqueuser_id(Integer uniqueuser_id) {
		this.uniqueuser_id = uniqueuser_id;
	}
	public String getAndroid_id() {
		return android_id;
	}
	public void setAndroid_id(String android_id) {
		this.android_id = android_id;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getUseragent() {
		return useragent;
	}
	public void setUseragent(String useragent) {
		this.useragent = useragent;
	}
	public String getAndroid_version() {
		return android_version;
	}
	public void setAndroid_version(String android_version) {
		this.android_version = android_version;
	}
	public String getDevice_model() {
		return device_model;
	}
	public void setDevice_model(String device_model) {
		this.device_model = device_model;
	}
	public String getBundle_id() {
		return bundle_id;
	}
	public void setBundle_id(String bundle_id) {
		this.bundle_id = bundle_id;
	}
	public String getApp_version() {
		return app_version;
	}
	public void setApp_version(String app_version) {
		this.app_version = app_version;
	}
	public String getDevice_ip() {
		return device_ip;
	}
	public void setDevice_ip(String device_ip) {
		this.device_ip = device_ip;
	}
	public String getDevice_w() {
		return device_w;
	}
	public void setDevice_w(String device_w) {
		this.device_w = device_w;
	}
	public String getDevice_h() {
		return device_h;
	}
	public void setDevice_h(String device_h) {
		this.device_h = device_h;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	@Override
	public String toString() {
		return "UniqueUser [uniqueuser_id=" + uniqueuser_id + ", android_id="
				+ android_id + ", imei=" + imei + ", useragent=" + useragent
				+ ", android_version=" + android_version + ", device_model="
				+ device_model + ", bundle_id=" + bundle_id + ", app_version="
				+ app_version + ", device_ip=" + device_ip + ", device_w="
				+ device_w + ", device_h=" + device_h + ", carrier=" + carrier
				+ "]";
	}
	
}
