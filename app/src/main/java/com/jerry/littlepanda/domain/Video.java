package com.jerry.littlepanda.domain;

public class Video {
	Integer video_id;
	String video_title;
	String video_url;
	String video_source;
	String video_image;
	String video_category;
	Integer video_length;
	String video_type;
	String video_summary;
	String video_date;

	public YiDianNewsItem convertToVideoItem()
	{
		YiDianNewsItem videoItem=new YiDianNewsItem();
		videoItem.setImage(video_image);
		videoItem.setTitle(video_title);
		videoItem.setVideo_url(video_url);
		videoItem.setSource(video_source);

		return videoItem;
	}
	
	public String getVideo_date() {
		return video_date;
	}
	public void setVideo_date(String video_date) {
		this.video_date = video_date;
	}
	public Integer getVideo_id() {
		return video_id;
	}
	public void setVideo_id(Integer video_id) {
		this.video_id = video_id;
	}
	public String getVideo_title() {
		return video_title;
	}
	public void setVideo_title(String video_title) {
		this.video_title = video_title;
	}
	public String getVideo_url() {
		return video_url;
	}
	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}
	public String getVideo_source() {
		return video_source;
	}
	public void setVideo_source(String video_source) {
		this.video_source = video_source;
	}
	public String getVideo_image() {
		return video_image;
	}
	public void setVideo_image(String video_image) {
		this.video_image = video_image;
	}
	public String getVideo_category() {
		return video_category;
	}
	public void setVideo_category(String video_category) {
		this.video_category = video_category;
	}
	public Integer getVideo_length() {
		return video_length;
	}
	public void setVideo_length(Integer video_length) {
		this.video_length = video_length;
	}
	public String getVideo_type() {
		return video_type;
	}
	public void setVideo_type(String video_type) {
		this.video_type = video_type;
	}
	public String getVideo_summary() {
		return video_summary;
	}
	public void setVideo_summary(String video_summary) {
		this.video_summary = video_summary;
	}
	@Override
	public String toString() {
		return "Video [video_id=" + video_id + ", video_title=" + video_title
				+ ", video_url=" + video_url + ", video_source=" + video_source
				+ ", video_image=" + video_image + ", video_category="
				+ video_category + ", video_length=" + video_length
				+ ", video_type=" + video_type + ", video_summary="
				+ video_summary + "]";
	}
	
}
