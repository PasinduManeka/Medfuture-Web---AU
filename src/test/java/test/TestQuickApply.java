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

import Pages.CandidateRegisterObjecct;
import Pages.QuickApplyObject;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;

public class TestQuickApply {
	
	private static WebDriver driver;
	private static WebDriverWait wait;
	private static BrowserMobProxy proxy;
	private String url="https://medfuture.com.au/apply-now/permanent/general-practitioner-aged-care-aud-180-per-hour-dpa-mmm-2-ballarat/24395";
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
	
	//test success form submission
	@Test(priority =1)
	public void successfulFormSubmission() throws InterruptedException{
		QuickApplyObject apply = new QuickApplyObject(driver, wait);
		driver.get(url);
		
		//set values on input box
		apply.SetValues("Test", "QA", "292569333", "pasinduherath18@gmail.com", "This is a test message.");
		
		Thread.sleep(10000);
		
		//set values on drop downs
		apply.setValueState(0);
		apply.setValueSeniority(0);
		apply.setValueSpecilaty(0);
		apply.setValueSubscribe("click");
		apply.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\1.5MB.pdf");
		apply.setValuehearUs(0);
		
		apply.setValueSubscribe("click");
		
		Thread.sleep(1000);
		
		apply.setSubmit();
		
		Thread.sleep(20000);
		
		Assert.assertTrue(apply.isSuccessfulPopupDisplayed(proxy), "Quick apply is not submitted.");
				
	}
	
	//test empty form submission
	@Test(priority=2)
	public void testEmptyFormSubmission() throws InterruptedException{
		QuickApplyObject apply = new QuickApplyObject(driver, wait);
		driver.get(url);
		
		Thread.sleep(20000);
		
		apply.setSubmit();
		
		Thread.sleep(2000);
		
		Assert.assertTrue(apply.isFormErrorPresent(), "Validation messages are not displayed.");

	}
	
	//test valid email validation
	@Test(priority=3)
	public void testEmailValidations() throws InterruptedException {
		QuickApplyObject apply = new QuickApplyObject(driver, wait);
		driver.get(url);
		
		//set values on input box
		apply.SetValues("Test", "QA", "292569333", "pasinduhe", "This is a test message.");
		
		Thread.sleep(10000);
		
		//set values on drop downs
		apply.setValueState(0);
		apply.setValueSeniority(0);
		apply.setValueSpecilaty(0);
		apply.setValueSubscribe("click");
		apply.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\1.5MB.pdf");
		apply.setValuehearUs(0);
		
		apply.setValueSubscribe("click");
		
		Thread.sleep(1000);
		
		apply.setSubmit();
		
		Thread.sleep(10000);
		
		Assert.assertTrue(apply.isValidMail(), "It's a valid email.");
	}
	
	//test valid mobile validation
	@Test(priority=4)
	public void testMobileValidation() throws InterruptedException{
		QuickApplyObject apply = new QuickApplyObject(driver, wait);
		driver.get(url);
		
		//set values on input box
		apply.SetValues("Test", "QA", "2925", "pasinduherath18@gmail.com", "This is a test message.");
		
		Thread.sleep(10000);
		
		//set values on drop downs
		apply.setValueState(0);
		apply.setValueSeniority(0);
		apply.setValueSpecilaty(0);
		apply.setValueSubscribe("click");
		apply.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\1.5MB.pdf");
		apply.setValuehearUs(0);
		
		apply.setValueSubscribe("click");
		
		Thread.sleep(1000);
		
		apply.setSubmit();
		
		Thread.sleep(20000);
		
		Assert.assertTrue(apply.isValidMobile(), "It's valid mobile number.");
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
