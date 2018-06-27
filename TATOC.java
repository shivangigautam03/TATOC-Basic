package selenium;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.Spring;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TATOC {
	public static void main(String args[]) throws InterruptedException {
		WebDriver driver = new ChromeDriver(); // launch the driver
		driver.get("http://10.0.1.86/tatoc"); // load the page
		driver.findElement(By.cssSelector("a[href*=\"basic\"]")).click(); // click on basic
		driver.findElement(By.className("greenbox")).click(); // click greenbox
		driver.switchTo().frame("main"); // frameDungeon
		String actualAnswer = driver.findElement(By.id("answer")).getAttribute("class");            
		Boolean condition = true;
		while (condition) {
			driver.findElement(By.cssSelector("body > center > a:nth-child(7)")).click();
			WebElement childDiv = driver.findElement(By.id("child"));
			driver.switchTo().frame(childDiv);
			String expectedAnswer = driver.findElement(By.id("answer")).getAttribute("class");
			driver.switchTo().parentFrame();
			if (actualAnswer.equals(expectedAnswer)) {
				condition = false;
			}
		}
		driver.findElement(By.cssSelector("body > center > a:nth-child(9)")).click();
	//	driver.switchTo().defaultContent();
		Actions act = new Actions(driver);
		// find element which we need to drag
		WebElement drag = driver.findElement(By.id("dropbox")); // find element which we need to drop
		WebElement drop = driver.findElement(By.className("ui-draggable")); // find element which we need to drag
		act.dragAndDropBy(drop, 10,-68).build().perform();
		
		Thread.sleep(1000);
		driver.findElement(By.linkText("Proceed")).click();     //click on proceed
		driver.findElement(By.cssSelector("a")).click();
	     
		//driver.findElement(By.partialLinkText("Launch")).click();   //click on launch popup window
		Thread.sleep(1000);
		String mainWindow = driver.getWindowHandle();
		Set<String> openedWindow = driver.getWindowHandles();
		Iterator<String> itr = openedWindow.iterator();
		while (itr.hasNext()) {
			String childWindow = itr.next();
			if (!mainWindow.equalsIgnoreCase(childWindow)) {
				driver.switchTo().window(childWindow);
				driver.findElement(By.id("name")).sendKeys("Hiiii");
				driver.findElement(By.id("submit")).click();

			}
		}
		driver.switchTo().window(mainWindow);
		driver.findElement(By.linkText("Proceed")).click();        //click on proceed   
		driver.findElement(By.cssSelector("body > div > div.page > a:nth-child(8)")).click();          //click on generate token
		String token = driver.findElement(By.id("token")).getText();                
		String[] splited = token.split(" ");

		Cookie token1 = new Cookie("Token", splited[1]);
		driver.manage().addCookie(token1);   
		driver.findElement(By.linkText("Proceed")).click();       //click on proceed
		Thread.sleep(2000);           
		driver.close();
	}
}