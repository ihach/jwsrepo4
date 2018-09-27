package com.foo;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.foo2.StringResponseWrapper;

/**
 * Filter that replaces all occurrences of the target string with the
 * replacement string. The target and replacement strings are provided as init
 * parameters to the filter in the web.xml file.
 */
public class ReplaceSiteName2Filter implements Filter {
	
	protected FilterConfig config;
	
	private HttpServletRequest httpRequest;
	private HttpServletResponse httpResponse;
	
	private boolean isCaseInsensitive = false;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		httpRequest = (HttpServletRequest) request;
		httpResponse = (HttpServletResponse) response;
		StringResponseWrapper stringResponseWrapper = new StringResponseWrapper(httpResponse);
		// Invoke resource, accumulating output in the wrapper.
		// -------------------------------------------------------------
		chain.doFilter(httpRequest, stringResponseWrapper);
		// -------------------------------------------------------------
		// Turn entire output into one big String.
		String modifiedResponse = doModification(stringResponseWrapper.toString());
		// Send modified response to the client
		PrintWriter out = httpResponse.getWriter();
		out.write(modifiedResponse);
	}

	/**
	 * Saving off the request object for potential use by the child class.
	 */
	public HttpServletRequest getRequest() {
		return (httpRequest);
	}

	/**
	 * Saving off the response object for potential use by the child class.
	 */
	public HttpServletResponse getResponse() {
		return (httpResponse);
	}

	public void init(FilterConfig config) {
		// Save FilterConfig object for later use by subclasses
		this.config = config;
		
//		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(cfg.getServletContext());
//		this.bean = ctx.getBean(YourBeanType.class);
//		or
//		ServletContext servletContext = filterConfig.getServletContext();
//		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//		AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
//		autowireCapableBeanFactory.configureBean(this, BEAN_NAME);
	}

	public void destroy() {
	}
	
	//#############################################################################3
	
	/**
	 * Classes extending from ModificationFilter must override this method.
	 */
	//////////////////////////////////////public abstract String doModification(String origResponse) throws IOException;
	/**
	 * Replaces all strings matching the target string with the replacement
	 * string.
	 */
	public String doModification(String orig) {
		if ((getTarget() == null) || (getReplacement() == null)) {
			return (orig);
		} else {
			String target = getTarget();
			if (isCaseInsensitive()) {
				target = "(?i)" + target;
			}
			String replacement = getReplacement();
			return (orig.replaceAll(target, replacement));
		}
	}
	
	/** The string that needs replacement. */
	public String getTarget() {
		return getInitParameter("target");
	}

	/** The string that replaces the target. */
	public String getReplacement() {
		return getInitParameter("replacement");
	}
	
	/**
	 * Returns the init parameter value specified by 'param' or null if it is
	 * not present or an empty string
	 */
	private String getInitParameter(String param) {
		String value = config.getInitParameter(param);
		if ((value == null) || (value.trim().equals(""))) {
			value = null;
		}
		return value;
	}

	/**
	 * Sets whether the search for the target string will be case sensitive.
	 */
	public void setCaseInsensitive(boolean flag) {
		isCaseInsensitive = flag;
	}

	/**
	 * Returns true or false, indicating if the search for the target string is
	 * case sensitive.
	 */
	public boolean isCaseInsensitive() {
		return (isCaseInsensitive);
	}
	
	
	
}
