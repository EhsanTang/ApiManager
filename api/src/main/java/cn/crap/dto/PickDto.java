package cn.crap.dto;

/**
 * 前端下拉选着框DTO
 * @author Ehsan
 *
 */

public class PickDto{
	private String id;
	private String value;
	private String name;
	
	public PickDto(){}
	public PickDto(String id,String value,String name){
		this.id=id;
		this.value=value;
		this.name=name;
	}
	public PickDto(String id,String name){
		this.id=id;
		this.value=id;
		this.name=name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}