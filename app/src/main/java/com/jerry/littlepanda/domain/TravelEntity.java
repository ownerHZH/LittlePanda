package com.jerry.littlepanda.domain;

public class TravelEntity {
	int travel_id;
	String travel_title;
	String travel_info;
	String travel_images;
	String travel_detail;
	int travel_read;
	public int getTravel_id() {
		return travel_id;
	}
	public void setTravel_id(int travel_id) {
		this.travel_id = travel_id;
	}
	public String getTravel_title() {
		return travel_title;
	}
	public void setTravel_title(String travel_title) {
		this.travel_title = travel_title;
	}
	public String getTravel_info() {
		return travel_info;
	}
	public void setTravel_info(String travel_info) {
		this.travel_info = travel_info;
	}
	public String getTravel_images() {
		return travel_images;
	}
	public void setTravel_images(String travel_images) {
		this.travel_images = travel_images;
	}
	public String getTravel_detail() {
		return travel_detail;
	}
	public void setTravel_detail(String travel_detail) {
		this.travel_detail = travel_detail;
	}
	public int getTravel_read() {
		return travel_read;
	}
	public void setTravel_read(int travel_read) {
		this.travel_read = travel_read;
	}
	@Override
	public String toString() {
		return "TravelEntity [travel_id=" + travel_id + ", travel_title="
				+ travel_title + ", travel_info=" + travel_info
				+ ", travel_images=" + travel_images + ", travel_detail="
				+ travel_detail + ", travel_read=" + travel_read + "]";
	}
	

}
