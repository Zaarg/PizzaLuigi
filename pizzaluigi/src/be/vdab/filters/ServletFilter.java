package be.vdab.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class ServletFilter
 */
//@WebFilter("*.htm") commented to test filer register in web.xml for servers who don't understand servlets 3.0
public class ServletFilter implements Filter {
	
	private String encoding;
	
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding); //encoding can ook hard coded zijn met gewoon "utf-8" in te vullen, dan geen web.xml param nodig
		chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		encoding = fConfig.getInitParameter("encoding");
	}
	
	public void destroy() {
		
	}

}
