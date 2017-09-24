package cn.crap.utils;

import java.io.Serializable;

public class Page implements Serializable{
	private static final long serialVersionUID = 1L;
	private int allRow = 0; // allRow will always big than -1
	private int currentPage = 1; // currentPage will always big than 0
	private int size = 20; // size will always big than 0
	private int totalPage = 0; // totalPage will always big than -1

	public Page(){}

	public Page(Integer size){
		if (size == null || size <= 0){
			return;
		}
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
	 * if allRow equals null, then allRow will be default 0
	 * if allRow bit than 0, then will calculate the totalPage
	 * @param allRow
	 */
	public void setAllRow(Integer allRow) {
		if (allRow == null || allRow < 0){
			return;
		}
		this.totalPage = (allRow+size-1)/size;
		this.allRow = allRow;
	}


	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * * if currentPage equals null, then currentPage will be default 1
	 * @param currentPage
	 */
	public void setCurrentPage(Integer currentPage) {
		if(currentPage == null || currentPage <= 0) {
			return;
		}
		this.currentPage = currentPage;
	}


	public void setStrCurrentPage(String currentPage) {
		if (MyString.isEmpty(currentPage)){
			return;
		}

		try{
			this.currentPage = Integer.parseInt(currentPage);
		}catch(Exception e){
			this.currentPage =1;
		}

		if(this.currentPage < 1) {
			this.currentPage = 1;
		}
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

	/**
	 * if size is null or less then 1, then size will be default 20
	 * @param size
	 */
	public void setSize(Integer size) {
		if (size == null || size <= 0){
			return;
		}
		this.size = size;
	}

	public int getTotalPage() {
		return totalPage;
	}
	
}
