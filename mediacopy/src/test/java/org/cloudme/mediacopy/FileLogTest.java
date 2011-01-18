package org.cloudme.mediacopy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class FileLogTest {
	@Test
	public void testContains() throws IOException, InterruptedException {
		File tmp = new File("target/tmp");
		FileUtils.deleteQuietly(tmp);
		File test = new File(tmp, "test.txt");
		FileUtils.writeStringToFile(test, "Hello World");
		FileLog fileLog = new FileLog();
		assertFalse(fileLog.contains(test));
		fileLog.put(test);
		assertTrue(fileLog.contains(test));
		Thread.sleep(1000);
		FileUtils.writeStringToFile(test, "Change file contents - and modification date");
		assertFalse(fileLog.contains(test));
	}
}
