package org.ibs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

public class AddFood {
    public static void addFoodForm(){
        WebDriver driver= new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", "\\src\\test\\resources\\chromedriver.exe");
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://localhost:8080/food");

        WebElement addBtn = driver.findElement(By.xpath ("//button[text()=\"Добавить\"]"));
        WebElement addTypeExotic = driver.findElement(By.xpath ("//*[@id=\"exotic\"]"));
        WebElement saveBtn = driver.findElement(By.xpath ("//button[@id=\"save\"]"));
        Select addType = new Select(driver.findElement(By.xpath("//select[@id=\"type\"]")));
        WebElement addName = driver.findElement(By.xpath ("//*[@id=\"name\"]"));
        addBtn.click();

        String exotic="0";
        addName.sendKeys("Citrus limon(☼) _Azerbaijan 5HPH+PPC, Unnamed Road, Pərvanlı");
        addType.selectByVisibleText("Фрукт");

        if (exotic=="true"){
            addTypeExotic.click();
        }
        saveBtn.click();
        driver.quit();

    }

}
