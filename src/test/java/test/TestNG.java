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
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;

public class TestNG {
	
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
	
	//test success submission
	@Test(priority =1)
	public void successfulFormSubmission() throws InterruptedException{
		CandidateRegisterObjecct object = new CandidateRegisterObjecct(driver, wait);
		driver.get("https://medfuture.com.au/register");
		
		//drop downs
		object.setValuesInserBox("Test666", "QA", "pasinduherath18@gmail.com", "292569333", "Test123//", "Test123//");

		object.setValueProfession(0);
		object.setValueSpecialty(0);
		object.setValueCountry(12);
		object.setValueState(0);
		object.setValuehearUs(0);
		object.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\\\support\\\\1.5MB.pdf");
		
		//radio buttons
		object.setValueSubscribe("click");
		object.setValueTerms("click");
		
		Thread.sleep(2000);
		//Submit
		object.setSubmit();
		Thread.sleep(40000);
		
		boolean isPoupDisplayed = object.isSuccessPopupDisplayed();
		Assert.assertTrue(isPoupDisplayed, "Popup Is not displayed");
				
	}
	
	@Test(priority =2)
	public void testEmptyFormSubmission() throws InterruptedException{
		CandidateRegisterObjecct object = new CandidateRegisterObjecct(driver, wait);
		driver.get("https://medfuture.com.au/register");
		
		object.setValuesInserBox("", "", "", "", "","");
		
		Thread.sleep(2000);
		//Submit
		object.setSubmit();
		
		Thread.sleep(40000);
		
		// Check for form errors (expected in this case)
        boolean isFormErrorPresent = object.isFormErrorPresent();
        Assert.assertTrue(isFormErrorPresent, "Form error message was not displayed for empty form submission.");

	}
	
	//Test existing email validation
	@Test(priority =3)
	public void testAlreadyTakenUserRegistration() throws InterruptedException{
		CandidateRegisterObjecct object = new CandidateRegisterObjecct(driver, wait);
		driver.get("https://medfuture.com.au/register");
		
		//drop downs
		object.setValuesInserBox("Test666", "QA", "pasinduherath18@gmail.com", "292569333", "Test123//", "Test123//");

		object.setValueProfession(0);
		object.setValueSpecialty(0);
		object.setValueCountry(12);
		object.setValueState(0);
		object.setValuehearUs(0);
		object.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\\\support\\\\1.5MB.pdf");
		
		//radio buttons
		object.setValueSubscribe("click");
		object.setValueTerms("click");
		
		Thread.sleep(2000);
		//Submit
		object.setSubmit();
		
		Thread.sleep(40000);
		
		boolean isEmailAlreadyTaken = object.captureEmailDuplicate(proxy);
		System.out.println(isEmailAlreadyTaken);
		Assert.assertTrue(isEmailAlreadyTaken,"New Email.");
//		Thread.sleep(40000);
		
	}
	
	//Test password length
	@Test(priority = 4)	
	public void tesPasswordLenghtError() throws InterruptedException {
		CandidateRegisterObjecct object = new CandidateRegisterObjecct(driver, wait);
		driver.get("https://medfuture.com.au/register");
		
		object.setValuesInserBox("Test666", "QA", "pasinduherath18@gmail.com", "292569333", "Tes", "Tes");

		object.setValueProfession(0);
		object.setValueSpecialty(0);
		object.setValueCountry(12);
		object.setValueState(0);
		object.setValuehearUs(0);
		object.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\\\support\\\\1.5MB.pdf");
		
		//radio buttons
		object.setValueSubscribe("click");
		object.setValueTerms("click");
		
		Thread.sleep(2000);
		//Submit
		object.setSubmit();
		
		Thread.sleep(40000);
		boolean passwordLength = object.isPasswordLengthErrorDisplayed(driver);
		Assert.assertTrue(passwordLength, "Password length error message should be displayed.");
		
	}
	
	//password match
	@Test(priority=5)	
	public void testPasswordMismatchErrorDisplayed() throws InterruptedException {
		CandidateRegisterObjecct object = new CandidateRegisterObjecct(driver, wait);
		driver.get("https://medfuture.com.au/register");
		
		object.setValuesInserBox("Test666", "QA", "pasinduherath18@gmail.com", "292569333", "Test1", "Test123//");

		object.setValueProfession(0);
		object.setValueSpecialty(0);
		object.setValueCountry(12);
		object.setValueState(0);
		object.setValuehearUs(0);
		object.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\\\support\\\\1.5MB.pdf");
		
		//radio buttons
		object.setValueSubscribe("click");
		object.setValueTerms("click");
		
		Thread.sleep(2000);
		//Submit
		object.setSubmit();
		
		Thread.sleep(40000);
		
		boolean missMatchPassword = object.isPasswordMismatchErrorDisplayed(driver);
		Assert.assertTrue(missMatchPassword, "Password and Confirm Password is matching.");
	}
	
	//invalid email
	@Test(priority=6)
	public void testValidEmail() throws InterruptedException {
		CandidateRegisterObjecct object = new CandidateRegisterObjecct(driver, wait);
		driver.get("https://medfuture.com.au/register");
		
		object.setValuesInserBox("Test666", "QA", "pasinduherat", "292569333", "Test1", "Test123//");

		object.setValueProfession(0);
		object.setValueSpecialty(0);
		object.setValueCountry(12);
		object.setValueState(0);
		object.setValuehearUs(0);
		object.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\5-mb-example-file.pdf");
		
		//radio buttons
		object.setValueSubscribe("click");
		object.setValueTerms("click");
		
		Thread.sleep(2000);
		//Submit
		object.setSubmit();
		
		Thread.sleep(40000);
		
		boolean missMatchPassword = object.isValidMail();
		Assert.assertTrue(missMatchPassword, "Password and Confirm Password is matching.");
	}
	
	@Test(priority=7)
	public void testValidFileSize() throws InterruptedException {
		CandidateRegisterObjecct object = new CandidateRegisterObjecct(driver, wait);
		driver.get("https://medfuture.com.au/register");
		
		object.setValuesInserBox("Test666", "QA", "pasinduherat", "292569333", "Test1", "Test123//");

		object.setValueProfession(0);
		object.setValueSpecialty(0);
		object.setValueCountry(12);
		object.setValueState(0);
		object.setValuehearUs(0);
		object.setValueFile("C:\\Users\\QA CODEDESK\\eclipse-workspace\\MedfutureFramework\\support\\5-mb-example-file.pdf");
		
		//radio buttons
		object.setValueSubscribe("click");
		object.setValueTerms("click");
		
		Thread.sleep(2000);
		
		Assert.assertTrue(object.isValidFileSize(), "Valid File Size");
		
		//Submit
		object.setSubmit();
		
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
