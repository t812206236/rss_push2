package onem.cjq.rss.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onem.cjq.rss.db.JDBCUtils;
import onem.cjq.rss.web.ConnectionContext;




public class TranactionFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		Connection connection = null;
		
		try {
			
			//1. 获取连接
			connection = JDBCUtils.getConnection();
			
			//2. 开启事务
			connection.setAutoCommit(false);
			
			//3. 利用 ThreadLocal 把连接和当前线程绑定
			ConnectionContext.getInstance().bind(connection);
			
			//4. 把请求转给目标 Servlet
			arg2.doFilter(arg0, arg1);
			
			//5. 提交事务
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			
			//6. 回滚事务
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			HttpServletResponse resp = (HttpServletResponse) arg0;
			HttpServletRequest req = (HttpServletRequest) arg1;
			resp.sendRedirect(req.getContextPath() + "/error-1.jsp");
			
		} finally{
			//7. 解除绑定
			ConnectionContext.getInstance().remove();
			
			//8. 关闭连接
			JDBCUtils.release(connection);
			
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
