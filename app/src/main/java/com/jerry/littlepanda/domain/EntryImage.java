package com.jerry.littlepanda.domain;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * UserBean实体类，存储数据库中user表中的数据
 * <p>
 * 注解：
 * DatabaseTable：通过其中的tableName属性指定数据库名称
 * DatabaseField：代表数据表中的一个字段
 * ForeignCollectionField：一对多关联，表示一个UserBean关联着多个ArticleBean（必须使用ForeignCollection集合）
 * <p>
 * 属性：
 * id：当前字段是不是id字段（一个实体类中只能设置一个id字段）
 * columnName：表示当前属性在表中代表哪个字段
 * generatedId：设置属性值在数据表中的数据是否自增
 * useGetSet：是否使用Getter/Setter方法来访问这个字段
 * canBeNull：字段是否可以为空，默认值是true
 * unique：是否唯一
 * defaultValue：设置这个字段的默认值
 */
@DatabaseTable(tableName = "t_entryimage")
public class EntryImage {

	public static final String IMAGE_ID="imageId";
	public static final String ENTRY_ID="entryId";
	public static final String IMAGE_LINK="imageLink";

	@DatabaseField(generatedId = true, columnName = IMAGE_ID, useGetSet = true)
	private int imageId;
	@DatabaseField(columnName = ENTRY_ID, foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true,
			foreignColumnName = SyndEntry.ENTRY_ID)
	private SyndEntry entryId;
	@DatabaseField(columnName = IMAGE_LINK, useGetSet = true)
	private String imageLink;
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public SyndEntry getEntryId() {
		return entryId;
	}

	public void setEntryId(SyndEntry entryId) {
		this.entryId = entryId;
	}

	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	@Override
	public String toString() {
		return "EntryImage [imageId=" + imageId
				+ ", imageLink=" + imageLink + "]";
	}
	
	

}
