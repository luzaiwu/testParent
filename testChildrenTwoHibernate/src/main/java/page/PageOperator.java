package page;

public interface PageOperator<T> {
	
	
	/**
	 * @param pageUi
	 * @return
	 */
	public PageUI<T> loadPageData(PageUI<T> pageUi);

}
