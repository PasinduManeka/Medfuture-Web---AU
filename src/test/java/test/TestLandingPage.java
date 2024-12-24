package test;

import java.time.Duration;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Pages.LandingPageReachus;
import Pages.SIMGPageReachus;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;

public class TestLandingPage {
	
	private static WebDriver driver;
	private static WebDriverWait wait;
	private static BrowserMobProxy proxy;
	private String url = "https://medfuture.com.au/profession/general-practitioner-jobs-in-australia";
	
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
		
		wait=new WebDriverWait(driver,Duration.ofSeconds(60));
	}
	
	//test successful form submission
	@Test(priority=1)
	public void testSuccessfulFormSubmission() throws InterruptedException{
		LandingPageReachus landing = new LandingPageReachus(driver, wait);
		driver.get(url);
		
		//set values
		landing.setValueInsertBox("Test22", "QA", "292569333", "pasinduherath18@gmail.com", "This is a test message.");
		
		
		Thread.sleep(10000);
		
		//drop downs
		landing.setValueProfession(0);
		landing.setValueState(0);
		landing.setValueSpecialty(0);
		landing.setValueSeniiority(0);
		
		//file value set
		landing.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\1.5MB.pdf");
		
		//radio buttons
		landing.setValueTerms("click");
		landing.setValuesSubscribe("click");
	
		Thread.sleep(10000);
		
		//submit
		landing.setSubmit();		
		Thread.sleep(40000);
		
		Assert.assertTrue(landing.isSuccessPopupDisplay(), "Popup Is not displayed.");
		
	}
	
	//test empty form validation
	@Test(priority=2)
	public void testEmptyFormSubmission() throws InterruptedException{
		LandingPageReachus landing = new LandingPageReachus(driver, wait);
		driver.get(url);
		
		Thread.sleep(10000);
		
		//submit
		landing.setSubmit();		
		Thread.sleep(20000);
		
		Assert.assertTrue(landing.isFormErrorPresent(), "Required Validations are not displayed.");
		
	}
	
	//test valid email validation
	@Test(priority=3)
	public void testValidEmail() throws InterruptedException{
		LandingPageReachus landing = new LandingPageReachus(driver, wait);
		driver.get(url);
		
		//set values
		landing.setValueInsertBox("Test", "QA", "292569333", "pasinduherath", "This is a test message.");
				
				
		Thread.sleep(10000);
				
		//drop downs
		landing.setValueProfession(0);
		landing.setValueState(0);
		landing.setValueSpecialty(0);
		landing.setValueSeniiority(0);
				
		//file value set
		landing.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\1.5MB.pdf");
		
		//radio buttons
		landing.setValueTerms("click");
		landing.setValuesSubscribe("click");
		
		Thread.sleep(10000);
		
		//submit
		landing.setSubmit();		
		Thread.sleep(40000);
		
		Assert.assertTrue(landing.isValidMail(), "Email validation is not working.");
		
	}
	
	//test valid mobile validation
	@Test(priority=4)
	public void testValidMobile() throws InterruptedException{
		LandingPageReachus landing = new LandingPageReachus(driver, wait);
		driver.get(url);
		
		//set values
		landing.setValueInsertBox("Test", "QA", "29256", "pasinduherath18@gmail.com", "This is a test message.");
						
				
		Thread.sleep(10000);
				
				//drop downs
		landing.setValueProfession(0);
		landing.setValueState(0);
		landing.setValueSpecialty(0);
		landing.setValueSeniiority(0);
				
		//file value set
		landing.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\1.5MB.pdf");
		
		//radio buttons
		landing.setValueTerms("click");
		landing.setValuesSubscribe("click");
		
		Thread.sleep(10000);
		
		//submit
		landing.setSubmit();		
		Thread.sleep(40000);
		
		Assert.assertTrue(landing.isValidMobile(), "Mobile validation is not displaying.");
	}
	
	//test valid file size validation
	@Test(priority=5)
	public void isValidFileSize() throws InterruptedException{
		LandingPageReachus landing = new LandingPageReachus(driver, wait);
		driver.get(url);
		
		//set values
		landing.setValueInsertBox("Test", "QA", "29256", "pasinduherath18@gmail.com", "This is a test message.");
						
				
		Thread.sleep(10000);
				
				//drop downs
		landing.setValueProfession(0);
		landing.setValueState(0);
		landing.setValueSpecialty(0);
		landing.setValueSeniiority(0);
				
		//file value set
		landing.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\5-mb-example-file.pdf");
		
		//radio buttons
		landing.setValueTerms("click");
		landing.setValuesSubscribe("click");
	
		Thread.sleep(10000);
		
		Assert.assertTrue(landing.isValidFileSize(), "File size is not detected.");
		
		//submit
		landing.setSubmit();		
		Thread.sleep(40000);
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
