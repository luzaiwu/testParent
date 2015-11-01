package page;
public interface LazyDataLoader<T> {
	public void loadData(PageDto<T> page);
}
