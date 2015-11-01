package page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PageUI<T> implements Page<T>{

	@SuppressWarnings("unchecked")
	private List<T> data = Collections.EMPTY_LIST; //褰撳墠椤电殑鏁版嵁
	private int rowNum = 10; //姣忛〉鏄剧ず鏉℃暟
	private int pageSize; //椤垫暟
	private int resultCount; //鎬昏褰曟暟
	private int currentPage = 1; //褰撳墠椤�
	
	private int start; // 璧峰鏉℃暟锛岀敤浜巗ql
	private int limit; // 鍙栧灏戞潯鏁版嵁锛岀敤浜巗ql
	
	private String sortField;
	private String sortOrder;
	
	private List<Integer> pageSizeByList = new ArrayList<Integer>();
	
	private PageOperator<T> operator; 
	
	public PageUI(PageOperator<T> operator) {
		this.operator = operator;
		loadData(currentPage);
	}
	
	public PageUI(PageOperator<T> operator, int rowNum) {
		this.operator = operator;
		this.rowNum = rowNum;
		loadData(currentPage);
	}
	
	public void loadData(int currentPage) {
		this.limit = this.rowNum;
		if (currentPage <= 1 ) {
			this.start = 0;
		} else {
			this.start = (currentPage-1)*limit;
		}
		
		operator.loadPageData(this); //锟斤拷锟斤拷锟斤拷锟�
		
		//锟斤拷锟斤拷页锟斤拷
		int x = resultCount % rowNum;
		int pageNum = resultCount/rowNum;
		if (x == 0) {
			if (pageNum == 0) {
				pageNum = 1;
			}
		} else {
			pageNum = pageNum + 1;
		}
		this.pageSize = pageNum;
		
		//锟斤拷取页 锟斤拷锟斤拷
		pageSizeByList.clear();
		int i = 0;
		if (this.currentPage > 10) {
			i = currentPage - 5;
		}
		for (; i < pageSize; i++) {
			pageSizeByList.add(new Integer(i + 1));
			if (pageSizeByList.size() > 10) {
				break;
			}
		}
		
		
		for (int j = 0; j < 10; j++) {
			if (j > i) {
				pageSizeByList.add(null);
			}
		}
		
	}
	
	
	/**
	 * 涓嬩竴椤�
	 * @param name
	 * @return
	 */
	public void nextPage() {
		this.currentPage = this.currentPage + 1;
		loadData(currentPage);
	}
	
	/**
	 * 涓婁竴椤�
	 * @param name
	 * @return
	 */
	public void prevPage() {
		// TODO Auto-generated method stub
		this.currentPage = this.currentPage - 1;
		loadData(currentPage);
	}
	
	/**
	 * 棣栭〉
	 * @param name
	 * @return
	 */
	public void firstPage() {
		// TODO Auto-generated method stub
		this.currentPage = 1;
		loadData(currentPage);
	}
	
	/**
	 * 灏鹃〉
	 * @param name
	 * @return
	 */
	public void lastPage() {
		// TODO Auto-generated method stub
		this.currentPage = pageSize;
		loadData(pageSize);
	}
	
	/**
	 * 璁剧疆褰撳墠椤�
	 * @param name
	 * @param currentPage
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		loadData(this.currentPage);
	}
	
	/**
	 * 鍒ゆ柇鏄惁鏄椤�
	 * @param name
	 * @return
	 */
	public boolean isFirstPage() {
		return this.currentPage == 1;
	}
	
	/**
	 * 鍒ゆ柇鏄惁鏄渶鍚庝竴椤�
	 * @param name
	 * @return
	 */
	public boolean isLastPage() {
		return this.currentPage == this.pageSize;
	}
	
	/**
	 * 鍒ゆ柇鏄惁鏄綋鍓嶉〉
	 * @param pageNum
	 * @return
	 */
	public boolean isCurrentPage(int pageNum) {
		return this.currentPage == pageNum;
	}
	
	///////////////////////////////get and set ///////////////////////

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> datas) {
		this.data = datas;
	}

	public int getPageSize() {
		return pageSize;
	}
	
	public List<Integer> getPageSizeByList() {
		return pageSizeByList;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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

	public int getResultCount() {
		return resultCount;
	}

	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
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
	
	
}
