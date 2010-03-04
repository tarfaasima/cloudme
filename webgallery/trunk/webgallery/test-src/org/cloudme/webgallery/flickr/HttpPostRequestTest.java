package org.cloudme.webgallery.flickr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.StringReader;
import java.net.URLConnection;

import org.junit.Before;
import org.junit.Test;

public class HttpPostRequestTest {
	private URLConnection con;

	@Before
	public void setUp() {
		con = new DummyConnection();
	}

	@Test
	public void testPostRequest() throws Exception {
		final String boundary = "asdfuioasdf";
		final AppendableHttpRequest req = new HttpPostRequest(
				"http://something.com/api/upload", boundary);
		req.writeTo(con);
		String expected = "POST /api/upload HTTP/1.1\r\n"
				+ "Content-Type: multipart/form-data; boundary=asdfuioasdf\r\n"
				+ "Host: something.com\r\n" + "Content-Length: 0\r\n";
		assertEqualLines(expected, con.toString());

		req.append("some", "anything");
		expected += "\r\n" + boundary + "\r\n"
				+ "Content-Disposition: form-data; name=\"some\"\r\n" + "\r\n"
				+ "anything\r\n" + boundary + "\r\n";
		req.writeTo(con);
		assertEqualLines(expected, con.toString());

		req.append("hello", "world");
		expected += "Content-Disposition: form-data; name=\"hello\"\r\n"
				+ "\r\n" + "world\r\n" + boundary + "\r\n";
		req.writeTo(con);
		assertEqualLines(expected, con.toString());

		req.append("photo", "holiday.jpg", "image/jpeg", "Hello World!"
				.getBytes());
		expected = "POST /api/upload HTTP/1.1\r\n"
				+ "Content-Type: multipart/form-data; boundary=asdfuioasdf\r\n"
				+ "Host: something.com\r\n" + "Content-Length: 12\r\n";
		expected += "\r\n" + boundary + "\r\n"
				+ "Content-Disposition: form-data; name=\"some\"\r\n" + "\r\n"
				+ "anything\r\n" + boundary + "\r\n";
		expected += "Content-Disposition: form-data; name=\"hello\"\r\n"
				+ "\r\n" + "world\r\n" + boundary + "\r\n";
		expected += "Content-Disposition: form-data; name=\"photo\"; filename=\"holiday.jpg\"\r\n"
				+ "Content-Type: image/jpeg\r\n"
				+ "\r\n"
				+ "Hello World!\r\n"
				+ boundary + "\r\n";
		req.writeTo(con);
		System.out.println(con.toString());
		assertEqualLines(expected, con.toString());
		assertTrue(con.getDoOutput());
	}

	private void assertEqualLines(String expected, String actual)
			throws Exception {
		BufferedReader exp = new BufferedReader(new StringReader(expected));
		BufferedReader act = new BufferedReader(new StringReader(actual));
		String line;
		int lineNr = 1;
		while ((line = exp.readLine()) != null) {
			assertEquals("Difference in line " + lineNr++, line, act.readLine());
		}
		assertNull("No more lines expected ( " + lineNr + ")", act.readLine());
	}
}
