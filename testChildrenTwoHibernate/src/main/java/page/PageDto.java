package page;

import java.util.Collections;
import java.util.List;

import org.primefaces.model.SortOrder;


@SuppressWarnings("unchecked")
public class PageDto<T> implements Page<T> {
	private int pageSize;
	private int start;
	private int limit;
	private int resultCount;
	private List<T> data;
	private String sortField;
	private String sortOrder;
	
	public PageDto(int pageSize, int start, int limit) {
		super();
		this.pageSize = pageSize;
		this.start = start;
		this.limit = limit;
		this.data = Collections.EMPTY_LIST;
	}

	public PageDto(int pageSize, int start, int limit, String sortField, String sortOrder) {
		super();
		this.pageSize = pageSize;
		this.start = start;
		this.limit = limit;
		this.data = Collections.EMPTY_LIST;
		this.sortField = sortField;
		this.sortOrder = sortOrder;
	}

	public PageDto(int pageSize, int start, int limit, String sortField, SortOrder sortOrder) {
		super();
		this.pageSize = pageSize;
		this.start = start;
		this.limit = limit;
		this.data = Collections.EMPTY_LIST;
		this.sortField = sortField;
		this.sortOrder = (sortOrder == SortOrder.ASCENDING ? "ASC" : "DESC");
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getResultCount() {
		return resultCount;
	}

	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
}
