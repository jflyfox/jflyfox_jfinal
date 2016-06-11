package com.flyfox.jfinal.base;

import com.flyfox.util.Constants;

public class Paginator {

	private int pageNo;
	private int pageSize;
	private int totalRecords;

	public Paginator() {
		this.pageNo = 1;
		this.pageSize = Constants.DEFAULT_PAGE_SIZE;
	}

	public Paginator(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getFirstResult() {
		if (pageNo <= 0) {
			return 0;
		}
		return (pageNo - 1) * pageSize;
	}

	public int getMaxResults() {
		return pageSize;
	}
}
