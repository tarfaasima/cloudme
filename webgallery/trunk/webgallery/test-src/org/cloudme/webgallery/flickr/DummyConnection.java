/**
 * 
 */
package org.cloudme.webgallery.flickr;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;

import org.apache.commons.io.output.ByteArrayOutputStream;

class DummyConnection extends URLConnection {
	private ByteArrayOutputStream out;
	private boolean doOutput;
	
	public DummyConnection() {
		super(null);
	}
	
	@Override
	public void setDoOutput(boolean doOutput) {
		this.doOutput = doOutput;
	}
	
	@Override
	public void connect() throws IOException {
	}
	
	@Override
	public OutputStream getOutputStream() throws IOException {
		out = new ByteArrayOutputStream();
		return out;
	}
	
	@Override
	public String toString() {
		return new String(out.toByteArray());
	}
	
	@Override
	public boolean getDoOutput() {
		return doOutput;
	}
}