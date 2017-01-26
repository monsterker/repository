package misc;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class DemoFilter implements Filter {
	@Override
	public void destroy() {

	}
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		//pre-processing
//		if(pre-processing失敗) {
//			request.getRequestDispatcher("....").forward(request, resp);
//			return;
//		}

		chain.doFilter(request, resp);	//呼叫下一個Web元件

		//post-processing
//		if(post-processing失敗) {
//			request.getRequestDispatcher("....").forward(request, resp);
//			return;
//		}
	}
	private FilterConfig filterConfig;
	@Override
	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
	}
}
