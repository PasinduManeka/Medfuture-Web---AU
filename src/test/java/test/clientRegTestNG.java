package test;

import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import Pages.ClientRegisterObject;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;

public class clientRegTestNG {
	
	private static WebDriver driver;
	private static WebDriverWait wait;
	private static BrowserMobProxy proxy;
	private String url = "https://medfuture.com.au/employer-register";
	
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
	
	//test success submission
	@Test(priority = 1)
	public void successfulFormSubmission() throws InterruptedException{
		
		System.out.println("Test the client successful popup .");
		
		ClientRegisterObject client = new ClientRegisterObject(driver, wait);
		driver.get(url);
		
		client.setValues("Test", "QA", "https://test.com", "Test", "QA", "manekaherat815@gmail.com", "292569333", "Test123//", "Test123//");
		
		Thread.sleep(2000);
		client.setValueIndustryType(0);
		client.setValuehearUs(0);
		client.setValueTerm("click");
		client.setValueSubscribe("click");
		client.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\1.5MB.pdf");
		
		//submit the form
		client.setSubmit();
		Thread.sleep(40000);
		
//		boolean isSuccPopupDispalyed = client.isSuccessPopupDisplayed();
		Assert.assertTrue("Successful Poup is not displayed", client.isSuccessPopupDisplayed());
		
	}
	
	//test empty form submission
	@Test(priority=2)
	public void testEmptyFormSubmission() throws InterruptedException{
		ClientRegisterObject client = new ClientRegisterObject(driver, wait);
		driver.get(url);
		
		Thread.sleep(2000);
		
		client.setSubmit();
		
		Thread.sleep(1000);
		
		Assert.assertTrue("Validation messages are not displayed.", client.isFormErrorPresent());
		
	}
	
	//test existing mail validation.
	@Test(priority=3)
	public void testExistEmailValidation() throws InterruptedException{
		
		System.out.println("Test the client email duplication .");
		
		ClientRegisterObject client = new ClientRegisterObject(driver, wait);
		driver.get(url);
		
		client.setValues("Test", "QA", "https://test.com", "Test", "QA", "manekaherat815@gmail.com", "292569333", "Test123//", "Test123//");
		
		Thread.sleep(2000);
		client.setValueIndustryType(0);
		client.setValuehearUs(0);
		client.setValueTerm("click");
		client.setValueSubscribe("click");
		client.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\1.5MB.pdf");
		
		//submit the form
		client.setSubmit();
		
		Thread.sleep(40000);
		
		Assert.assertTrue("New Email", client.captureEmailduplicate(proxy));
		
	}
	
	//Test password length
	@Test(priority=4)
	public void testPasswordLength() throws InterruptedException{
		ClientRegisterObject client = new ClientRegisterObject(driver, wait);
		driver.get(url);
		
		client.setValues("Test", "QA", "https://test.com", "Test", "QA", "manekaherat815@gmail.com", "292569333", "Tes", "Test123//");
		
		Thread.sleep(2000);
		client.setValueIndustryType(0);
		client.setValuehearUs(0);
		client.setValueTerm("click");
		client.setValueSubscribe("click");
		client.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\1.5MB.pdf");
		
		//submit the form
		client.setSubmit();
		
		Thread.sleep(40000);
		
		Assert.assertTrue("The password has the correct length.", client.isPasswordLengthErrorDisplayed());
		
	}
	
	//test confirm password and password values are match validation.
	@Test(priority=5)
	public void testPasswordMatch() throws InterruptedException{
		ClientRegisterObject client = new ClientRegisterObject(driver, wait);
		driver.get(url);
		
		client.setValues("Test", "QA", "https://test.com", "Test", "QA", "manekaherat815@gmail.com", "292569333", "Test", "Test123//");
		
		Thread.sleep(2000);
		client.setValueIndustryType(0);
		client.setValuehearUs(0);
		client.setValueTerm("click");
		client.setValueSubscribe("click");
		client.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\1.5MB.pdf");
		
		//submit the form 
		client.setSubmit();
		
		Thread.sleep(40000);
		
		Assert.assertTrue("The same passwords have been entered.", client.isPasswordMismatchErrorDisplayed());
		
	}
	
	//test invalid email validation
	@Test(priority=6)
	public void testValidEmail() throws InterruptedException{
		ClientRegisterObject client = new ClientRegisterObject(driver, wait);
		driver.get(url);
		
		client.setValues("Test", "QA", "test.com", "Test", "QA", "manekaherat815", "292569333", "Test123//", "Test123//");
		
		Thread.sleep(2000);
		client.setValueIndustryType(0);
		client.setValuehearUs(0);
		client.setValueTerm("click");
		client.setValueSubscribe("click");
		client.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\1.5MB.pdf");
		
		//submit the form 
		client.setSubmit();
		
		Thread.sleep(40000);
		
		Assert.assertTrue("It's a valid email.", client.isValidMail());
		
	}
	
	//test valid web site URL validation
	@Test(priority=7)
	public void testValidWebURL() throws InterruptedException{
		ClientRegisterObject client = new ClientRegisterObject(driver, wait);
		driver.get(url);
		
		client.setValues("Test", "QA", "test", "Test", "QA", "manekaherat815", "292569333", "Test123//", "Test123//");
		
		Thread.sleep(2000);
		client.setValueIndustryType(0);
		client.setValuehearUs(0);
		client.setValueTerm("click");
		client.setValueSubscribe("click");
		client.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\1.5MB.pdf");
		
		//submit the form 
		client.setSubmit();
		
		Thread.sleep(40000);
		
		Assert.assertTrue("It's a valid Web URL.", client.isValidWebURL());
		
	}	
	
	//test required fields validation
	@Test(priority=8)
	public void testRequiredFields() throws InterruptedException{
		ClientRegisterObject client = new ClientRegisterObject(driver, wait);
		driver.get(url);
		
		client.setValues("", "", "", "Test", "QA", "", "", "Test123//", "Test123//");
		
		Thread.sleep(2000);
		client.setValueIndustryType(0);
		client.setValuehearUs(0);
		client.setValueTerm("click");
		client.setValueSubscribe("click");
		client.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\1.5MB.pdf");
		
		//submit the form 
		client.setSubmit();
				
		Thread.sleep(20000);
		
		Assert.assertTrue("Required fields are not validated", client.isFormErrorPresent());
	}
	
	//test vacancy profile not required 
	@Test(priority=9)
	public void  testVacancyFieldRequired() throws InterruptedException{
		ClientRegisterObject client = new ClientRegisterObject(driver, wait);
		driver.get(url);
		
		client.setValues("Test", "QA", "https://test.com", "Test", "QA", "manekaherat8151@gmail.com", "292569333", "Test123//", "Test123//");
		
		Thread.sleep(2000);
		client.setValueIndustryType(0);
		client.setValuehearUs(0);
		client.setValueTerm("click");
		client.setValueSubscribe("click");
//		client.setValueFile("");
		
		//submit the form
		client.setSubmit();
		
		Thread.sleep(40000);
		
		Assert.assertFalse("Vacancy profile is unrecognized required field.", client.isFormErrorPresent());
	}
	
	//test file size should not validate
	@Test(priority=10)
	public void testFileSize() throws InterruptedException{
		ClientRegisterObject client = new ClientRegisterObject(driver, wait);
		driver.get(url);
		
		client.setValues("Test", "QA", "https://test.com", "Test", "QA", "manekaherat815@gmail.com", "292569333", "Tes", "Test123//");
		
		Thread.sleep(2000);
		client.setValueIndustryType(0);
		client.setValuehearUs(0);
		client.setValueTerm("click");
		client.setValueSubscribe("click");
		client.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\5-mb-example-file.pdf");
		
		Assert.assertFalse("Vacancy profile file size has been validated. It cannot be ", client.isValidFileSize());
		
		//submit the form
		client.setSubmit();
				
		Thread.sleep(40000);
	}
	
	//test file type validation
		@Test(priority=11)
		public void testFileTypeValidation() throws InterruptedException{
			ClientRegisterObject client = new ClientRegisterObject(driver, wait);
			driver.get(url);
			
			client.setValues("Test", "QA", "https://test.com", "Test", "QA", "manekaherat815@gmail.com", "292569333", "Tes", "Test123//");
			
			Thread.sleep(2000);
			client.setValueIndustryType(0);
			client.setValuehearUs(0);
			client.setValueTerm("click");
			client.setValueSubscribe("click");
			client.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\annual-enterprise-survey-2023-financial-year-provisional-size-bands.csv");
			
			//submit the form
			client.setSubmit();
					
			Thread.sleep(40000);
			
			Assert.assertTrue("File type is noot validated", client.isFormErrorPresent());
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
