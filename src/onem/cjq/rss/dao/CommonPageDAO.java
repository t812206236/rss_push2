package onem.cjq.rss.dao;

import java.util.List;

import onem.cjq.rss.web.Page;

/**
 * 只封装了带关键字和不带关键字两种形式，如果有详细复杂的分页方式，建议单独封装一个Dao接口，类似于BookDAO
 * @author cjq
 *
 * @param <T>
 */
public interface CommonPageDAO <T>{
	/**
	 * 根据页数和关键字获取Page分页，每页有多少个是固定参数，暂时先不从前端去配置这个每页数量，之后可能要去加
	 * @param pageNum 请求的页数
	 * @return
	 */
	public abstract Page<T> getPage(int pageNum);
	/**
	 * 根据页数和关键字获取Page分页，每页有多少个是固定参数，暂时先不从前端去配置这个每页数量，之后可能要去加
	 * @param pageNum 请求的页数
	 * @param keyWord 搜索的关键字，关键字不具体指哪一个部分，根据不同的需求来针对不同的部分
	 * @return
	 */
	public abstract Page<T> getPage(int pageNum,String keyWord);
	/**
	 * 为page提供所需要的列表，是一个辅助方法
	 * @param pageNum 请求的页数
	 * @param pageSize 每页多少个
	 * @return
	 */
	public abstract List<T> getList(int pageNum,int pageSize);
	/**
	 * 为page提供所需要的列表，是一个辅助方法
	 * @param pageNum 请求的页数
	 * @param keyWord 搜索的关键字，关键字不具体指哪一个部分，根据不同的需求来针对不同的部分
	 * @param pageSize 每页多少个
	 * @return
	 */
	public abstract List<T> getList(int pageNum,String keyWord,int pageSize);
	/**
	 * 查询所有item的数量
	 * @return
	 */
	public abstract long getTotalNum();
	/**
	 * 查询带关键字item的数量
	 * @param keyWord 搜索的关键字，关键字不具体指哪一个部分，根据不同的需求来针对不同的部分
	 * @return
	 */
	public abstract long getTotalNum(String keyWord);
	
}
