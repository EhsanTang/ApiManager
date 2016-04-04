package cn.crap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import cn.crap.framework.base.BaseModel;
import cn.crap.utils.MyString;
import cn.crap.utils.WebPageType;

@Entity
@Table(name="webpage")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class WebPage extends BaseModel{
	private String id;
	private String name;
	private String brief;
	private String content;
	private int click;
	private String type;
	private String moduleId;
	
	@Transient
	public String getTypeName(){
		if(!MyString.isEmpty(type))
			return WebPageType.valueOf(type).getName();
		return "";
	}
	@Id
	@GeneratedValue(generator="Generator")
	@Column(name="id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="brief")
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	
	@Column(name="content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name="click")
	public int getClick() {
		return click;
	}
	public void setClick(int click) {
		this.click = click;
	}
	
	@Column(name="type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name="moduleId")
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
}