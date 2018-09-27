package com.foo;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.foo2.StringResponseWrapper;

/**
 * Generic modification filter that buffers the output and lets doModification
 * method change the output string before sending it to the real output, i.e.,
 * the client. This is an abstract class: you <I>must</I> override
 * doModification in a subclass.
 */
public abstract class ModificationFilter implements Filter {
	
	protected FilterConfig config;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
		request = (HttpServletRequest) req;
		response = (HttpServletResponse) resp;
		StringResponseWrapper responseWrapper = new StringResponseWrapper(response);
		// Invoke resource, accumulating output in the wrapper.
		// -------------------------------------------------------------
		chain.doFilter(request, responseWrapper);
		// -------------------------------------------------------------
		// Turn entire output into one big String.
		String modifiedResponse = doModification(responseWrapper.toString());
		// Send modified response to the client
		PrintWriter out = response.getWriter();
		out.write(modifiedResponse);
	}

	/**
	 * Classes extending from ModificationFilter must override this method.
	 */
	public abstract String doModification(String origResponse) throws IOException;

	/**
	 * Saving off the request object for potential use by the child class.
	 */
	public HttpServletRequest getRequest() {
		return (request);
	}

	/**
	 * Saving off the response object for potential use by the child class.
	 */
	public HttpServletResponse getResponse() {
		return (response);
	}

	public void init(FilterConfig config) {
		// Save FilterConfig object for later use by subclasses
		this.config = config;
	}

	public void destroy() {
	}
}
