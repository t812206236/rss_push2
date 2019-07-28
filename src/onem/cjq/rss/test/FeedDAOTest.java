package onem.cjq.rss.test;

import static org.junit.Assert.*;

import org.junit.Test;

import onem.cjq.rss.dao.impl.FeedDAOImpl;

public class FeedDAOTest {

	FeedDAOImpl test=new FeedDAOImpl();
	
	@Test
	public void testGetFeed() {
		System.out.println(test.getFeed(2));
	}

	@Test
	public void testGetPageInt() {
		System.out.println(test.getPage(2).getList());
	}

	@Test
	public void testGetPageIntString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetListIntInt() {
		System.out.println(test.getList(2,3));
	}

	@Test
	public void testGetListIntStringInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTotalNum() {
		System.out.println(test.getTotalNum());
	}

	@Test
	public void testGetTotalNumString() {
		fail("Not yet implemented");
	}

}
