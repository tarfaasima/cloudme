package org.cloudme.notepad.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.SneakyThrows;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {
    private static final int PORT = 8080;
    private static final String ADDRESS = localIpAddress() + ":" + PORT;
    private static final String SNAPSHOT_DIR = "target/selenium/";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private static final File LOCAL_DB = new File(
            "target/notepad-0.17-SNAPSHOT/WEB-INF/appengine-generated/local_db.bin");
    private static final File LOCAL_DB_BAK = new File(
            "target/notepad-0.17-SNAPSHOT/WEB-INF/appengine-generated/local_db.bin.bak");
    private WebDriver driver;

    @Before
    public void initDriver() throws Throwable {
        try {
            driver = new ChromeDriver();
        }
        catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        FileUtils.deleteDirectory(new File(SNAPSHOT_DIR));
        // assertTrue(LOCAL_DB.exists());
        LOCAL_DB.renameTo(LOCAL_DB_BAK);
        assertFalse(LOCAL_DB.exists());
    }

    @After
    public void quitDriver() {
        LOCAL_DB_BAK.renameTo(LOCAL_DB);
        driver.quit();
    }

    @SuppressWarnings( "unused" )
    @Test( )
    public void testWithSelenium() throws Throwable {
        if (true) return;
        if (driver == null) {
            System.err.println("Skipping Selenium test.");
            return;
        }
        doLogin("joe@test.org");
        doSaveWithoutEntry();
        doSaveWithoutEntryAndDate();
        doEnterNote("18.03.2012", "Project Meeting", "This is a test", "Jim", "2d");
        doDeleteAllNotes();
        doEnterNote("15.04.2011", "Database Migration", "Create migration scripts.", "Max", "20");
        doEnterNote("15.04.2011", "Database Migration", "Data consistency checks were successful.", null, null);
        doShowTasks();
        doCheck("Create migration scripts.");
        doLogout();
    }

    private void doCheck(String string) {

    }

    private void doShowTasks() throws IOException {
        driver.findElement(By.linkText("Tasks")).click();
        takeSnapshot("doShowTasks");
    }

    private void doDeleteAllNotes() throws IOException {
        while (true) {
            List<WebElement> elements = driver.findElements(By.cssSelector("a.content"));
            if (elements.isEmpty()) {
                return;
            }
            WebElement element = elements.get(0);
            element.click();
            takeSnapshot("doDeleteAllNotes");
            driver.findElement(By.linkText("Delete")).click();
            Alert alert = driver.switchTo().alert();
            assertEquals("Do you really want to delete the note?", alert.getText());
            alert.accept();
        }
    }

    private void doEnterNote(String date, String topic, String content, String responsible, String dueDate)
            throws IOException {
        enter("date", date);
        enter("topic", topic);
        enter("content", content);
        enter("responsible", responsible);
        enter("dueDate", dueDate);
        takeSnapshot("doEnterNote");
        click(By.name("save"));
        takeSnapshot("doEnterNote");
    }

    private void enter(String id, String text) {
        WebElement element = driver.findElement(By.id(id));
        enter(element, text);
    }

    private void enter(WebElement element, String text) {
        if (text != null) {
            element.clear();
            element.sendKeys(text);
        }
    }

    private void doLogout() throws IOException {
        driver.findElement(By.linkText("Sign out")).click();
        assertEquals("Not logged in", driver.findElement(By.tagName("h3")).getText());
        takeSnapshot("doLogout");
    }

    private void doSaveWithoutEntry() throws IOException {
        assertEquals(DATE_FORMAT.format(new Date()), driver.findElement(By.name("date")).getAttribute("value"));
        click(By.name("save"));
        Alert alert = driver.switchTo().alert();
        assertFalse(alert.getText().toLowerCase().contains("date"));
        assertTrue(alert.getText().toLowerCase().contains("content"));
        assertTrue(alert.getText().toLowerCase().contains("topic"));
        alert.accept();
        takeSnapshot("doSaveWithoutEntry");
    }

    private void doSaveWithoutEntryAndDate() throws IOException {
        assertEquals(DATE_FORMAT.format(new Date()), driver.findElement(By.name("date")).getAttribute("value"));
        driver.findElement(By.name("date")).clear();
        click(By.name("save"));
        Alert alert = driver.switchTo().alert();
        assertTrue(alert.getText().toLowerCase().contains("date"));
        assertTrue(alert.getText().toLowerCase().contains("content"));
        assertTrue(alert.getText().toLowerCase().contains("topic"));
        alert.accept();
        takeSnapshot("doSaveWithoutEntryAndDate");
    }

    private void click(By by) {
        driver.findElement(by).click();
    }

    private void doLogin(String email) throws IOException {
        driver.get(ADDRESS);
        takeSnapshot("doLogin");
        WebElement element = driver.findElement(By.id("email"));
        element.clear();
        element.sendKeys(email);
        element.submit();
        assertEquals("Logged in as " + email + "\nSign out", driver.findElement(By.id("footer")).getText());
        takeSnapshot("doLogin");
    }

    private void takeSnapshot(String method) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        long millis = System.currentTimeMillis();
        String prefix = SNAPSHOT_DIR + millis + "_" + method;
        FileUtils.copyFile(scrFile, new File(prefix + "_screenshot.png"));
        String pageSource = driver.getPageSource();
        FileUtils.write(new File(prefix + "_pageSource.html"), pageSource);
    }

    @SneakyThrows
    private static String localIpAddress() {
        StringBuilder sb = new StringBuilder();
        for (byte b : InetAddress.getLocalHost().getAddress()) {
            if (sb.length() > 0) {
                sb.append('.');
            }
            sb.append(b < 0 ? 256 + b : b);
        }
        String address = sb.toString();
        return address;
    }
}
