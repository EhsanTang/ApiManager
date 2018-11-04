package cn.crap.dto;

import cn.crap.enu.DataType;
import cn.crap.utils.Tools;

import java.io.Serializable;

/**
 * 前端下拉选着框DTO
 * @author Ehsan
 *
 */

public class PickDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String value;
	private String name;

	public PickDto(){}
	public PickDto(String id,String value,String name){
        id = handleId(id);
		this.id=id;
		this.value=value;
		this.name=name;
	}

    public PickDto(String id,String name){
        id = handleId(id);
		this.id=id;
		this.value=id;
		this.name=name;
	}
	public PickDto(DataType dateType){
		this.id=dateType.name();
		this.value=dateType.name();
		this.name=dateType.getName();
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

    private String handleId(String id) {
        if (id == null){
            id = System.currentTimeMillis() + Tools.getChar(10);
        }
        id.replace("\\.", "_");
        return id;
    }
}