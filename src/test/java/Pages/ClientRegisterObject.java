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

public class ClientRegisterObject {
	
	WebDriver driver = null;
	WebDriverWait wait = null;
	
	By companyName;
	By tradeName;
	By industryType;
	By websiteURL;
	By contactNum;
	By designation;
	By email;
	By telephone;
	By password;
	By cPassword;
	By file;
	By hearUs;
	By terms;
	By subscribe;
	By submit;
	By success;
	
	
	public ClientRegisterObject(WebDriver driver, WebDriverWait wait) {
		this.driver=driver;
		this.wait=wait;
		
		initElements();
		
	}
	
	private void initElements() {
		companyName = By.id("companyName");
		tradeName = By.id("tradeName");	 	
		industryType = By.id("industryType");
		websiteURL = By.id("website");
		contactNum = By.id("contactPerson");
		designation = By.id("designation");
		email = By.id("email");
		telephone = By.id("phoneNumber");
		password = By.id("password");
		cPassword = By.id("confirmPassword");
		file = By.id("vacancyProfile");
		hearUs = By.id("social-media");
		terms = By.id("agree");
		subscribe = By.id("subscribe");
		submit = By.cssSelector("button.find-jobs-btn-candidate");
//		success = By.xpath("//div[@class='relative bg-white p-7 lg:p-10 rounded-lg flex flex-col items-center text-center']");
		success = By.cssSelector("div.relative.bg-white.p-7.lg\\:p-10.rounded-lg.flex.flex-col.items-center.text-center");
	}
	
	public void setValues(String company, String trade, String URL, String officialCont, String designationInput, String emailInput, String phone, String pwd, String cpwd) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(companyName)).sendKeys(company);
		wait.until(ExpectedConditions.visibilityOfElementLocated(tradeName)).sendKeys(trade);
		wait.until(ExpectedConditions.visibilityOfElementLocated(websiteURL)).sendKeys(URL);
		wait.until(ExpectedConditions.visibilityOfElementLocated(contactNum)).sendKeys(officialCont);
		wait.until(ExpectedConditions.visibilityOfElementLocated(designation)).sendKeys(designationInput);
		wait.until(ExpectedConditions.visibilityOfElementLocated(email)).sendKeys(emailInput);
		wait.until(ExpectedConditions.visibilityOfElementLocated(telephone)).sendKeys(phone);
		wait.until(ExpectedConditions.visibilityOfElementLocated(password)).sendKeys(pwd);
		wait.until(ExpectedConditions.visibilityOfElementLocated(cPassword)).sendKeys(cpwd);
	}
	
	//Set Industry type
	public void setValueIndustryType(int option) {
		driver.findElement(industryType).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-2-option-"+option+"']"))).click();
	}
	
	//Set hear us
	public void setValuehearUs(int option){
		driver.findElement(hearUs).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-3-option-"+option+"']"))).click();
	}
	
	//Set Terms
	public void setValueTerm(String action) {
		if(!driver.findElement(terms).isSelected() && action == "click"  ) {
			driver.findElement(terms).click();
		}
	}
	
	//Set Subscribe
	public void setValueSubscribe(String action) {
		if(!driver.findElement(subscribe).isSelected() && action == "click" ) {
			driver.findElement(subscribe).click();
		}
	}
	
	//set file
	public void setValueFile(String filePath) {
		driver.findElement(file).sendKeys(filePath);;
	}
	
	//Submit button
	public void setSubmit() {
		driver.findElement(submit).sendKeys(Keys.RETURN);
	}
	
	//successful pop-up
	public boolean isSuccessPopupDisplayed() {
		try {
			WebElement primaryMessage = driver.findElement(success).findElement(By.cssSelector("p.text-black"));
			String expectedMessage = "Registered Successful";
//			System.out.println(primaryMessage.getText());
			return primaryMessage.getText().equals(expectedMessage);
			
		}catch(Exception e) {
			return false;
		}
		
	}
	
	//empty form
	public boolean isFormErrorPresent() {
		try {
			List <WebElement> errorMessages = driver.findElements(By.className("form-error-msg"));
			
			for (WebElement errorMessage : errorMessages) {
				if(errorMessage.isDisplayed() && errorMessage.isEnabled()) {
					System.out.println("An enabled error message is present.");
					return true;
				}
			}
			
			
			System.out.println("No enabled error messages found.");
			return false;
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//existing email check
	public boolean captureEmailduplicate(BrowserMobProxy proxy) {
		boolean serverErrorDetected = false;
		boolean uiErrorDitected = false;
		
	    Har har = proxy.getHar();
	    List<HarEntry> entries = har.getLog().getEntries();


	    for (HarEntry entry : entries) {
	        HarResponse response = entry.getResponse();
	        int statusCode = response.getStatus();

	        if (statusCode == 422) {
	        	serverErrorDetected = true;
	            System.out.println("Error Response Detected:");
	            System.out.println("Status Code: " + statusCode);
	            System.out.println("URL: " + entry.getRequest().getUrl());
	            System.out.println("Response Body: " + response.getContent().getText());
	            break;
	        }
	    }
	    
	    //Check UI error detected
	    try {
	    	WebElement errorMessageElement = driver.findElement(By.xpath("//div[contains(@class, 'form-error-msg') and text()='The email has already been taken.']"));
	    	uiErrorDitected = errorMessageElement.isDisplayed();	    	
	    }catch(NoSuchElementException  e) {
	    	uiErrorDitected = false;
	    }
	    
	    //return true only if both conditions are true
	  //return true only if both conditions are true
	    if (serverErrorDetected && uiErrorDitected) {
	        System.out.println("Both server and UI indicate 'email already taken'.");
	        return true;
	    }else if(serverErrorDetected) {
	    	System.out.println("Server error is indicated.");
	    	return true;
	    }else if(uiErrorDitected){
	    	System.out.println("UI validation message detected.");
	    	return true;
	    }else {
	        System.out.println("Either server or UI did not indicate 'email already taken'.");
	        return false;
	    }

	    

	}
	
	//password length
	public boolean isPasswordLengthErrorDisplayed() {
	    try {
	        WebElement errorMessageElement = driver.findElement(By.xpath("//div[contains(@class, 'form-error-msg') and text()='Password must be between 8 and 12 characters.']"));
	        return errorMessageElement.isDisplayed();
	    } catch (NoSuchElementException e) {
	        return false; // Return false if the element is not found
	    }
	}
	
	//mismatch password
	public boolean isPasswordMismatchErrorDisplayed() {
	    try {
	        WebElement errorMessageElement = driver.findElement(By.xpath("//div[contains(@class, 'form-error-msg') and text()='Password does not match with original']"));
	        System.out.println("Mismatch Password.");
	        return errorMessageElement.isDisplayed();
	    } catch (NoSuchElementException e) {
	    	System.out.println("Passwords are matching.");
	        return false; // Return false if the element is not found
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
	
	//Invalid URL
	public boolean isValidWebURL() {
	    try {
	        WebElement errorMessageElement = driver.findElement(By.xpath("//div[contains(@class, 'form-error-msg') and text()='Please enter a valid website URL (e.g., https://www.example.com)']"));
	        System.out.println("Invalid Web URL...");
//	        System.out.println(errorMessageElement);
	        return errorMessageElement.isDisplayed();
	    } catch (NoSuchElementException e) {
	    	System.out.println("Valid Web URL.");
	        return false; // Return false if the element is not found
	    }
	}

	
	
	
	

}
