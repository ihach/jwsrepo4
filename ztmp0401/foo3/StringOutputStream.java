package com.foo3;

import java.io.StringWriter;
import javax.servlet.ServletOutputStream;

//import java.io.*;
//import javax.servlet.*;
//import javax.servlet.http.*;

/**
 * StringOutputStream is a stub for ServletOutputStream which buffers up the
 * output in a string buffer instead of sending it straight to the client.
 */
public class StringOutputStream extends ServletOutputStream {
	
	private StringWriter stringWriter;

	public StringOutputStream(StringWriter stringWriter) {
		this.stringWriter = stringWriter;
	}

	public void write(int c) {
		stringWriter.write(c);
	}
}
