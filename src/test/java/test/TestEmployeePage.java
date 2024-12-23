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

import Pages.EmployeePagereachUs;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;

public class TestEmployeePage {
	
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
	
	@Test(priority=1)
	public void testSuccessfulFormSubmission() throws InterruptedException{
		EmployeePagereachUs employee = new EmployeePagereachUs(driver, wait);
		driver.get("https://medfuture.com.au/employer-service");
		
		employee.setInputBoxValues("Company123", "Company Trade", "456321", "https://www.test.com", "Test", "QA", "manekaherat815@gmail.com", "+61292569333", "This is a test messsage");
		
		employee.setIndustryType(0);
		employee.setProfession(0);
		employee.setIndustryType(0);
		
		employee.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\1.5MB.pdf");
		
		employee.setValueTerms("click");
		employee.setValuesSubscribe("click");
		
		Thread.sleep(5000);
		
		employee.setSubmit();
		
		Thread.sleep(2000);
		
		Assert.assertTrue("Popup is not displayed.", employee.findSuccessfulPopup());
	
		Thread.sleep(10000);
	}
	
	@Test(priority=2)
	public void testEmptyForm() throws InterruptedException{
		EmployeePagereachUs employee = new EmployeePagereachUs(driver, wait);
		driver.get("https://medfuture.com.au/employer-service");
		
		Thread.sleep(100000);
		
		employee.setSubmit();
		
		Thread.sleep(2000);
		Assert.assertTrue("Required fields validation messages are not displayed.", employee.findErrorMsgElement());
		
		Thread.sleep(1000);		
	}
	
	@Test(priority=3)
	public void testValidEmail() throws InterruptedException{
		EmployeePagereachUs employee = new EmployeePagereachUs(driver,wait);
		driver.get("https://medfuture.com.au/employer-service");
		
		employee.setInputBoxValues("Company123", "Company Trade", "456321", "https://www.test.com", "Test", "QA", "manekaher", "+61292569333", "This is a test messsage");
		
		employee.setIndustryType(0);
		employee.setProfession(0);
		employee.setIndustryType(0);
		
		Thread.sleep(1000);
		
		employee.setSubmit();
		
		Thread.sleep(1000);
		
		Assert.assertTrue("Invaid email validation does not display.", employee.isValidEmail());		
	}
	
	@Test(priority = 4)
	public void testValidmobileNumber() throws InterruptedException{
		EmployeePagereachUs employee = new EmployeePagereachUs(driver, wait);
		driver.get("https://medfuture.com.au/employer-service");
		
		employee.setInputBoxValues("Company123", "Company Trade", "456321", "https://www.test.com", "Test", "QA", "manekaherat815@gmail.com", "+6129", "This is a test messsage");
		
		employee.setIndustryType(0);
		employee.setProfession(0);
		employee.setIndustryType(0);
		
		Thread.sleep(1000);
		
		employee.setSubmit();
		
		Thread.sleep(1000);
		
		Assert.assertTrue("Invalie mobile validation message does not display.", employee.isValidEmail());
	}
	
	@Test(priority=1)
	public void testFilSizeValidation() throws InterruptedException{
		EmployeePagereachUs employee = new EmployeePagereachUs(driver, wait);
		driver.get("https://medfuture.com.au/employer-service");
		
		employee.setInputBoxValues("Company123", "Company Trade", "456321", "https://www.test.com", "Test", "QA", "manekaherat815@gmail.com", "+61292569333", "This is a test messsage");
		
		employee.setIndustryType(0);
		employee.setProfession(0);
		employee.setPlacementType(0);
		
		employee.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\5-mb-example-file.pdf");
		
		//call the file size validation
		Assert.assertTrue("File size has not been validated.", employee.isValidFileSize());
		
		
	}
	
	
	@AfterMethod
	public void AfterMethod() {
		//Test
		System.out.println("Call the Ater method");
		
	}
	
	@AfterClass
	public void tearDown() {
		if(driver != null) {
			driver.quit();
		}
		if(proxy != null ) {
			proxy.stop();
		}
	}
	
	
}
