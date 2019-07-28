package onem.cjq.rss.test;

import org.junit.Test;

import onem.cjq.rss.generator.WebGetter;

public class WgetTest {
	@Test
	public void fun() {
		try {
			new WebGetter().get("https://blog.reimu.net/", "utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
