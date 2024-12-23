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

import Pages.ProductPageReachus;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;

public class TestProductPage {
	
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
		
		wait=new WebDriverWait(driver,Duration.ofSeconds(60));
	}
	
	@Test(priority=1)
	public void testSuccessfulFormSubmission() throws InterruptedException{
		ProductPageReachus product = new ProductPageReachus(driver, wait);
		driver.get("https://medfuture.com.au/premier-locum-general-practitioner");
		
		//set values
		product.setValueInsertBox("Test", "QA", "292569333", "pasinduherath18@gmail.com");
		
		
		Thread.sleep(10000);
		
		//drop downs
		product.setValueCountry(0);
		product.setValueState(0);
		product.setValueProfession(0);
		product.setValueSpecialty(0);
		product.setValueSeniiority(0);
		product.setValueRegType(0);
		
		//file value set
		product.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\1.5MB.pdf");
		
		//radio buttons
		product.setValueTerms("click");
		product.setValuesSubscribe("click");
		
		Thread.sleep(10000);
		
		//submit
		product.setSubmit();		
		Thread.sleep(40000);
		
		Assert.assertTrue(product.isSuccessPopupDisplay(), "Popup Is not displayed.");
		
	}
	
	
	@Test(priority=2)
	public void testemptyFormSubmission() throws InterruptedException{
		ProductPageReachus product = new ProductPageReachus(driver, wait);
		driver.get("https://medfuture.com.au/premier-locum-general-practitioner");
		
		Thread.sleep(20000);
		
		//submit
		product.setSubmit();		
		Thread.sleep(10000);
		
		Assert.assertTrue(product.isFormErrorPresent(), "Required Validations are not displayed.");
		
	}
	
	@Test(priority=3)
	public void testValidEmail() throws InterruptedException{
		ProductPageReachus product = new ProductPageReachus(driver, wait);
		driver.get("https://medfuture.com.au/premier-locum-general-practitioner");
		
		//set values
		product.setValueInsertBox("Test", "QA", "292569333", "pasinduhera");
		
		Thread.sleep(20000);
		
		//submit
		product.setSubmit();		
		Thread.sleep(10000);
		
		Assert.assertTrue(product.isValidMail(), "It's a valid email");
		
	}
	
	@Test(priority=4)
	public void testValidMobile() throws InterruptedException{
		ProductPageReachus product = new ProductPageReachus(driver, wait);
		driver.get("https://medfuture.com.au/premier-locum-general-practitioner");
		
		//set values
		product.setValueInsertBox("Test", "QA", "29256", "pasinduherath18@gmail.com");
		
		Thread.sleep(20000);
		
		//submit
		product.setSubmit();		
		Thread.sleep(10000);
		
		Assert.assertTrue(product.isValidMobile(), "It's a valid mobile");
		
	}
	
	@Test(priority=5)
	public void testValidFileSize() throws InterruptedException{
		
		ProductPageReachus product = new ProductPageReachus(driver, wait);
		driver.get("https://medfuture.com.au/premier-locum-general-practitioner");
		
		//set values
		product.setValueInsertBox("Test", "QA", "29256", "pasinduherath18@gmail.com");
		
		product.setValueCountry(0);
		product.setValueState(0);
		product.setValueProfession(0);
		product.setValueSpecialty(0);
		product.setValueSeniiority(0);
		product.setValueRegType(0);
		product.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\5-mb-example-file.pdf");
		
		Assert.assertTrue(product.isValidFileSize(), "Invalid file size does not detect.");
		
		product.setSubmit();
		
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
