package cn.crap.utils;

import cn.crap.query.BaseQuery;
import org.springframework.util.Assert;

import java.io.Serializable;

public class Page implements Serializable{
	private static final long serialVersionUID = 1L;
	private int allRow = 0; // allRow will always big than -1
	private int currentPage; // currentPage will always big than 0
	private int size; // size will always big than 0
	private int totalPage = 0; // totalPage will always big than -1

	public Page(Integer currentPage){
		this(15, currentPage == null ? 1 : currentPage);
	}

	public Page(BaseQuery baseQuery){
		this(baseQuery.getPageSize(), baseQuery.getCurrentPage());
	}

	public Page(Integer size, Integer currentPage){
		currentPage = (currentPage == null ? 1 : currentPage);
        size = (size == null ? 15 : size);
		Assert.isTrue(size <= 1000, "pageSzie 不能大于1000");
		Assert.notNull(currentPage > 0);

		this.currentPage = currentPage;
		this.size = size;
	}

	/**
	 * the total number of result
	 * @return
	 */
	public int getAllRow() {
		return allRow;
	}

	/**
	 * allRow must big or equal 0
	 * @param allRow
	 */
	public void setAllRow(Integer allRow) {
		Assert.notNull(allRow);
		Assert.notNull(allRow >= 0);

		this.totalPage = (allRow+size-1)/size;
		this.allRow = allRow;
	}


	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * according to size and currentPage calculate the start row num for databases
	 * @return
	 */
	public int getStart(){
		return  this.getSize() * (this.getCurrentPage() - 1);
	}

	public int getSize() {
		return size;
	}

	public int getTotalPage() {
		return totalPage;
	}
}
