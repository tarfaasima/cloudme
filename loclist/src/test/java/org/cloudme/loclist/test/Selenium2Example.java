package org.cloudme.loclist.test;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Selenium2Example {
    public static void main(String[] args) throws IOException {
        ChromeDriver driver = new ChromeDriver();
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        String url = "http://" + hostAddress + ":8080";

        driver.get(url);

        WebElement element = driver.findElement(By.name("email"));
        element.submit();
        element = driver.findElement(By.name("note.name"));
        element.sendKeys("Einkaufszettel " + System.currentTimeMillis());
        element.submit();

        File screenshot = driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("target/screenshots/selenium.png"));

        System.out.println(driver.getCurrentUrl());

        driver.quit();
    }
}
