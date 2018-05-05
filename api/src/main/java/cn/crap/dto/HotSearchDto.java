package cn.crap.dto;

/**
 * Automatic generation by tools
 * dto: exchange data with view
 */
public class HotSearchDto{
	private String id;
	private Integer times;
	private String keyword;
	private String createTimeStr;

	public void setId(String id){
		this.id=id;
	}
	public String getId(){
		return id;
	}

	public void setTimes(Integer times){
		this.times=times;
	}
	public Integer getTimes(){
		return times;
	}

	public void setKeyword(String keyword){
		this.keyword=keyword;
	}
	public String getKeyword(){
		return keyword;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
}
