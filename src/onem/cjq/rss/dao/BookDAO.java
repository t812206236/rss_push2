package onem.cjq.rss.dao;

import java.util.List;

import onem.cjq.rss.domain.Book;
import onem.cjq.rss.web.CriteriaBook;
import onem.cjq.rss.web.Page;

@Deprecated
public interface BookDAO {

	/**
	 * 根据 id 获取指定 Book 对象
	 * @param id
	 * @return
	 */
	public abstract Book getBook(int id);

	/**
	 * 根据传入的 CriteriaBook 对象返回对应的 Page 对象
	 * @param cb
	 * @return
	 */
	public abstract Page<Book> getPage(CriteriaBook cb);

	/**
	 * 根据传入的 CriteriaBook 对象返回其对应的记录数
	 * @param cb
	 * @return
	 */
	public abstract long getTotalBookNumber(CriteriaBook cb);

	/**
	 * 根据传入的 CriteriaBook 和 pageSize 返回当前页对应的 List 
	 * @param cb
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public abstract List<Book> getPageList(CriteriaBook cb,int pageSize);

	/**
	 * 返回指定 id 的 book 的 storeNumber 字段的值
	 * @param id
	 * @return
	 */
	public abstract int getStoreNumber(Integer id);


}