package page;

import java.util.List;

public interface Page<T> {
	
	public int getPageSize();

	public void setPageSize(int pageSize);

	public int getStart();

	public void setStart(int start);

	public int getLimit();

	public void setLimit(int limit);

	public int getResultCount();

	public void setResultCount(int resultCount);

	public List<T> getData();

	public void setData(List<T> data);

	public String getSortField();

	public void setSortField(String sortField);

	public String getSortOrder();

	public void setSortOrder(String sortOrder);
	
}
