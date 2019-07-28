package onem.cjq.rss.dao.impl;

import java.util.List;

import onem.cjq.rss.dao.FeedDAO;
import onem.cjq.rss.domain.Feed;
import onem.cjq.rss.web.Page;

public class FeedDAOImpl extends BaseDAO<Feed> implements FeedDAO{

	@Override
	public List<Feed> getAll() {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM feeds";
		return queryForList(sql);
	}
	
	@Override
	public Feed getFeed(int id) {
		String sql = "SELECT * FROM feeds WHERE id = ?";
		return query(sql,id);
	}
	
	@Override
	public long newFeed() {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO feeds (webRawTitleName) VALUES ('无效的Feed，等待编辑')";
		return insert(sql);
	}
	
	@Override
	public void delFeed(int id) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM feeds WHERE ID =?";
		update(sql,id);
	}


	@Override
	public Page<Feed> getPage(int pageNum) {
		// TODO Auto-generated method stub
		Page<Feed> page=new Page<>(pageNum);
		int verifyNum=pageNum;
		page.setTotalItemNumber(getTotalNum());
		//校验页数是否正确
		verifyNum=page.getPageNo();
		page.setList(getList(verifyNum, page.getPageSize()));
		return page;
	}

	@Override
	public Page<Feed> getPage(int pageNum, String keyWord) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Feed> getList(int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM feeds LIMIT ?,?";
		return queryForList(sql,(pageNum-1)*pageSize,pageSize);
	}

	@Override
	public List<Feed> getList(int pageNum, String keyWord, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getTotalNum() {
		// TODO Auto-generated method stub
		String sql = "SELECT count(*) FROM feeds";
		return getSingleVal(sql);
	}

	@Override
	public long getTotalNum(String keyWord) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateFeed(Feed feed) {
		// TODO Auto-generated method stub
		String sql = "UPDATE feeds SET webRawTitleName=? ,webRawDesc=?,webPath=?,"
				+ "webEncode=?,webGlobalReg=?,webItemReg=?,composeTitleReg=?,composeLinkReg=?,composeContentReg=?"
				+ "WHERE id=?";
		update(sql, feed.getWebRawTitleName(),feed.getWebRawDesc(),feed.getWebPath(),
				feed.getWebEncode(),feed.getWebGlobalReg(),feed.getWebItemReg(),feed.getComposeTitleReg(),feed.getComposeLinkReg(),feed.getComposeContentReg(),
				feed.getId());
	}

}
