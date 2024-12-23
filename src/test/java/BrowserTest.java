
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserTest {
	public static void main(String[] args) throws InterruptedException {
		
		String projectPath = System.getProperty("user.dir");
		System.out.println(projectPath);
		
		System.setProperty("webdriver.gecko.driver", projectPath+"\\drivers\\geckodriver\\geckodriver.exe");
		
		FirefoxOptions options = new FirefoxOptions();
		options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36");

		WebDriver driver = new FirefoxDriver(options);
		
//		System.setProperty("webdriver.chrome.driver", projectPath+"\\drivers\\chromedriver\\chromedriver.exe");
//		WebDriver driver = new ChromeDriver();
		
//		WebDriver driver = new  InternetExplorerDriver();
		
//		WebDriver driver = new ChromeDriver();
		
//		driver.get("https://medfuture.com.au/register");
//		
//		WebElement fName = driver.findElement(By.id("firstName"));
//		fName.sendKeys("Test");
//		
//		Thread.sleep(2000);
//		
//		driver.close();
		
		try {
						
			driver.get("https://medfuture.com.au/register");
			
			Thread.sleep(5000);
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//			WebElement fname = 	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName")));
//			fname.sendKeys("Test");
			
			WebElement lname = 	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastName")));
			lname.sendKeys("QA");
//			System.out.println(driver.getPageSource());
			
			//profession drop down
			WebElement profession = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-select-2-input")));
			profession.click();
			Thread.sleep(1000);
			WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-2-option-0']")));
	        option.click();
//			profession.sendKeys("Medical Practitioner");
			
			
			
//			Actions professionActions = new Actions(driver);
//			professionActions.moveToElement(profession).click().perform();
//			profession.sendKeys("Medical Practitioner");
			
//			
			//specialty drop down
			WebElement specialty = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-select-3-input")));
			specialty.click();
			Thread.sleep(1000);
			WebElement option2 = driver.findElement(By.xpath("//div[@id='react-select-3-option-0']"));
			option2.click();
//			specialty.sendKeys("Psychiatry");
//			
//			//country drop down
			WebElement country = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-select-4-input")));
			country.click();
			Thread.sleep(1000);
			WebElement option3 = driver.findElement(By.xpath("//div[@id='react-select-4-option-12']"));
			option3.click();
//			
//			//state drop down
			WebElement state = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-select-5-input")));
			state.click();
			Thread.sleep(1000);
			WebElement option4 = driver.findElement(By.xpath("//div[@id='react-select-5-option-0']"));
			option4.click();
//			
			//email
			WebElement email = 	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
			email.sendKeys("pasinduherath18@gmail.com");
//			
//			//phone number
			WebElement phone = 	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("phoneNumber")));
			phone.sendKeys("292569333");
//			
//			//password
			WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
			password.sendKeys("Test123//");
//			
//			//Confirm Password
			WebElement confirmPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmPassword")));
			confirmPassword.sendKeys("Test123//");
//			
//			//Source
			WebElement hearUs = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-select-6-input")));
			hearUs.click();
			Thread.sleep(1000);
			WebElement option5 = driver.findElement(By.xpath("//div[@id='react-select-6-option-0']"));
			option5.click();
//			
			//CV upload
//			WebElement cvUpload = wait.until(ExpectedConditions.elementToBeClickable(By.id("fileUpload")));
//			cvUpload.sendKeys(projectPath+"\\support\\1.5MB.pdf");
//			
			//Terms and conditions
			WebElement terms = wait.until(ExpectedConditions.elementToBeClickable(By.id("agree")));
			if(!terms.isSelected()) {
				terms.click();
			}
			
			//Subscribe
			WebElement subscribe = wait.until(ExpectedConditions.elementToBeClickable(By.id("subscribe")));
			if(!subscribe.isSelected()) {
				subscribe.click();
			}
	
			
			Thread.sleep(4000);
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			driver.close();
		}
	}

}
