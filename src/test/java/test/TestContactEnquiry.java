package test;

import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Pages.EnquiryNowObject;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;

public class TestContactEnquiry {
	
	private static WebDriver driver;
	private static WebDriverWait wait;
	private static BrowserMobProxy proxy;
	
	@BeforeTest
	public void setUp() {
		String projectPath = System.getProperty("user.dir");
//		System.out.println(projectPath);
		
		System.setProperty("webdriver.chrome.driver", projectPath+"\\drivers\\chromedriver\\chromedriver.exe");
		
		proxy = new BrowserMobProxyServer();
		proxy.setTrustAllServers(true);
		proxy.setHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
		proxy.start(0);
		
		Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
		
		FirefoxOptions options = new FirefoxOptions();
//		ChromeOptions options = new ChromeOptions();
		options.setProxy(seleniumProxy);
		
		proxy.newHar("forSubmission");
			
		driver = new FirefoxDriver(options);
		
		wait=new WebDriverWait(driver,Duration.ofSeconds(20));
	}
	
	//check success pop up 
	@Test(priority=1)
	public void successfulIsFormSubmission() throws InterruptedException{
		EnquiryNowObject enq = new EnquiryNowObject(driver, wait);
		driver.get("https://medfuture.com.au/contact-us");
		
		enq.setValuesInsertBox("Test QA", "pasinduherath18@gmail.com", "292569333", "This is a test record");
		
		Thread.sleep(2000);
		
		enq.setSubmit();
		
		Thread.sleep(40000);
		
		Assert.assertTrue("Successful Poup is not displayed", enq.isSuccessPopupDisplayed());
	}
	
	//Check required fields
	@Test(priority=2)
	public void testEmptyForm() throws InterruptedException{
		EnquiryNowObject enq = new EnquiryNowObject(driver, wait);
		driver.get("https://medfuture.com.au/contact-us");
		
		Thread.sleep(10000);
		
		enq.setSubmit();
		
		Assert.assertTrue("Required fields are filled.", enq.isEmptyForm());
	}
	
	//Check valid mobile
	@Test(priority=3)
	public void testValidMobileNum() throws InterruptedException{
		EnquiryNowObject enq = new EnquiryNowObject(driver, wait);
		driver.get("https://medfuture.com.au/contact-us");
		
		enq.setValuesInsertBox("Test QA", "pasinduherath18@gmail.com", "2925", "This is a test record");
		
		Thread.sleep(2000);
		
		enq.setSubmit();
		
		Thread.sleep(40000);
		
		Assert.assertTrue("Valid Mobile Number.", enq.isValidPhoneNum());		
		
	}
	
	//Test invalid email
	@Test(priority = 4)
	public void testValidEmail() throws InterruptedException{
		EnquiryNowObject enq = new EnquiryNowObject(driver, wait);
		driver.get("https://medfuture.com.au/contact-us");
		
		enq.setValuesInsertBox("Test QA", "pasinduherath18", "2925", "This is a test record");
		
		Thread.sleep(2000);
		
		enq.setSubmit();
		
		Thread.sleep(40000);
		
		Assert.assertTrue("Valid Mobile Number.", enq.isValidMail());
	}
	
	@AfterMethod
	public void AfterMethod() {
		System.out.println("Call the after method.");
	}
	
	@AfterClass
	public void tearDown() {
		if(driver != null) {
			driver.quit();
		}
		if(proxy != null) {
			proxy.stop();
		}
	}

}
