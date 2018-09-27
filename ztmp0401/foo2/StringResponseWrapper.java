package com.foo2;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.foo3.StringOutputStream;

//import java.io.*;
//import javax.servlet.*;
//import javax.servlet.http.*;


/**
 * A response wrapper that takes everything the client would normally output and
 * saves it in one large string.
 */
public class StringResponseWrapper extends HttpServletResponseWrapper {
	
	private StringWriter stringWriter;

	/**
	 * Initializes wrapper.
	 * <P>
	 * First, this constructor calls the parent constructor. That call is
	 * crucial so that the response is stored and thus setHeader, setStatus,
	 * addCookie, and so forth work normally.
	 * <P>
	 * Second, this constructor creates a StringWriter that will be used to
	 * accumulate the response.
	 */
	public StringResponseWrapper(HttpServletResponse response) {
		super(response);
		stringWriter = new StringWriter();
	}

	/**
	 * When servlets or JSP pages ask for the Writer, don't give them the real
	 * one. Instead, give them a version that writes into the StringBuffer. The
	 * filter needs to send the contents of the buffer to the client (usually
	 * after modifying it).
	 */
	public PrintWriter getWriter() {
		return (new PrintWriter(stringWriter));
	}

	/**
	 * Similarly, when resources call getOutputStream, give them a phony output
	 * stream that just buffers up the output.
	 */
	public ServletOutputStream getOutputStream() {
		return (new StringOutputStream(stringWriter));
	}

	/**
	 * Get a String representation of the entire buffer.
	 * <P>
	 * Be sure <I>not</I> to call this method multiple times on the same
	 * wrapper. The API for StringWriter does not guarantee that it "remembers"
	 * the previous value, so the call is likely to make a new String every
	 * time.
	 */
	public String toString() {
		return (stringWriter.toString());
	}

	/** Get the underlying StringBuffer. */
	public StringBuffer getBuffer() {
		return (stringWriter.getBuffer());
	}
}