package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarResponse;

public class QuickApplyObject {

	WebDriver driver = null;
	WebDriverWait wait = null;
	
	By firstName;
	By lastName;
	By phone;
	By email;
	By state;
	By seniority;
	By specialty;
	By file;
	By hearUs;
	By note;
	By subscribe;
	By submit;
	By success;
	
	public QuickApplyObject(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		
		initElements();
	}
	
	public void initElements() {
		firstName = By.id("firstName");
		lastName = By.id("lastName"); 
		phone = By.id("phoneNumber");
		email = By.id("email");
		state = By.id("react-select-3-input");   
		seniority = By.id("react-select-4-input");
		specialty = By.id("react-select-5-input");
		note = By.id("note");
		file= By.id("fileUpload");
		subscribe = By.id("subscribe");
		hearUs = By.id("react-select-6-input");
		submit = By.className("form-register-gold-quick-btn");
		success = By.cssSelector("div.relative.bg-white.p-7.lg\\:p-10.rounded-lg.flex.flex-col.items-center.text-center");
	}
	
	//Set values on text fields 
	public void SetValues(String fname, String lname,String phoneInput, String emailInput, String noteInput) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).sendKeys(fname);
		wait.until(ExpectedConditions.visibilityOfElementLocated(lastName)).sendKeys(lname);
		wait.until(ExpectedConditions.visibilityOfElementLocated(phone)).sendKeys(phoneInput);
		wait.until(ExpectedConditions.visibilityOfElementLocated(email)).sendKeys(emailInput);
		wait.until(ExpectedConditions.visibilityOfElementLocated(note)).sendKeys(noteInput);
	}
	
	//set values on state
	public void setValueState(int option){
		driver.findElement(state).click();
		System.out.println("Sysout Option:"+option);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-3-option-"+option+"']"))).click();
	}
	
	//set value on seniority
	public void setValueSeniority(int option){
		driver.findElement(seniority).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-4-option-"+option+"']"))).click();
	}
	
	//set value on specialty
	public void setValueSpecilaty(int option){
		driver.findElement(specialty).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-5-option-"+option+"']"))).click();
	}
	
	//set value on radio button
	public void setValueSubscribe(String action){
		if(!driver.findElement(subscribe).isSelected() && action == "click" ) {
			driver.findElement(subscribe).click();
		}
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-5-option-"+option+"']")));
	}
	
	//set document
	public void setValueFile(String filePath) {
		driver.findElement(file).sendKeys(filePath);;
	}
	
	//set value hear us
	public void setValuehearUs(int option){
		driver.findElement(hearUs).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-6-option-"+option+"']"))).click();
	}
	
	//submit button
	public void setSubmit() {
		driver.findElement(submit).sendKeys(Keys.RETURN);
	}
	
	//empty form
	public boolean isFormErrorPresent() {
		try {
			
			List <WebElement> errorMessages = driver.findElements(By.className("form-error-msg"));
			
			for(WebElement errorMessage : errorMessages) {
				if(errorMessage.isDisplayed() && errorMessage.isEnabled()) {
					System.out.println("An enabled error message is present.");
					return true;
				}
			}
			
			System.out.println("No eneble form-error-msg found");
			return false;
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("No form-error-msg found");
			return false;
		}
	}
	
	
	//successful popup
	public boolean isSuccessfulPopupDisplayed(BrowserMobProxy proxy) {
		
		boolean serverCode = false;
		boolean uiSuccessMessage = false;
		
		Har har = proxy.getHar();
		List<HarEntry> entries = har.getLog().getEntries();
		
		for(HarEntry entry:entries) {
			HarResponse response = entry.getResponse();
			int statusCode =  response.getStatus();
			
			if(statusCode == 200 || statusCode == 201) {
				serverCode = true;
				System.out.println("Server response detected.");
				System.out.println("Server Code:"+statusCode);
				break;
			}
		}
		
		try {
			WebElement primaryMessage = driver.findElement(success).findElement(By.cssSelector("p.text-black"));
			String expectedMesage = "Application Successfully Submitted"; 
			System.out.println(primaryMessage.getText());			
			uiSuccessMessage = primaryMessage.getText().equals(expectedMesage);
			System.out.println("uiSuccessMessage"+uiSuccessMessage);
		}catch(Exception e) {
			uiSuccessMessage = false;
		}
		
		if(serverCode && uiSuccessMessage) {
			System.out.println("Both server and UI popup displayed.");
			return true;
		}else if(serverCode) {
			System.out.println("Did not capture the success popup.");
			return true;
		}else {
			System.out.println("Either server or UI did not indicate.");
			return false;
		}
	}
	
	//Invalid Email	
	public boolean isValidMail() {
	    try {
	        WebElement errorMessageElement = driver.findElement(By.xpath("//div[contains(@class, 'form-error-msg') and text()='Please enter a valid email address']"));
	        System.out.println("Invalid Email...");
	        return errorMessageElement.isDisplayed();
	    } catch (NoSuchElementException e) {
	    	System.out.println("Valid Email.");
	        return false; // Return false if the element is not found
	    }
	}	
	
	/**----- Invalid Mobile -----**/
	public boolean isValidMobile() {
		try {
			WebElement errorMessageElement = driver.findElement(By.xpath("//div[contains(text(),'Please enter a valid Phone Number')]"));
	        System.out.println("Invalid Mobile...");
	        return errorMessageElement.isDisplayed();
		}catch(Exception e) {
			return false;
		}
	}
	
	/**----- File Size -----**/
	
	public boolean isValidFileSize() {
		try {
			WebElement errorMessageElement = driver.findElement(By.xpath("//div[contains(text(),'File size exceeds the 2 MB limit')]"));
			System.out.println("File size is exeed");
			return errorMessageElement.isDisplayed();
		}catch(NoSuchElementException e) {
			return false;
		}
	}
	
	
	
	
	
	
	
}
