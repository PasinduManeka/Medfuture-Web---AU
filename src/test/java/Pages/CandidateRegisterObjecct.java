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

public class CandidateRegisterObjecct {
	
	WebDriver driver = null;
	WebDriverWait wait = null;
	
	By fName;
	By lName;
	By profession;	
	By specialty;
	By country;
	By state;
	By email;
	By mobile;
	By password;
	By cPassword;
	By hearus;
	By terms;
	By subscribe;
	By file;
	By submit;
	By success;
//	By errorMSG;
	
	public CandidateRegisterObjecct(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		
		initElements();
//		setValueProfession();
	}
	
	private void initElements() {
		fName = By.id("firstName");
		lName = By.id("lastName");
		profession = By.id("react-select-2-input");
		specialty = By.id("react-select-3-input");
		country = By.id("react-select-4-input");
		state = By.id("react-select-5-input");
		email = By.id("email");
		mobile = By.id("phoneNumber");
		password = By.id("password");
		cPassword = By.id("confirmPassword");
		hearus = By.id("react-select-6-input");
		terms = By.id("agree");
		subscribe = By.id("subscribe");
		file= By.id("fileUpload");
		submit = By.className("find-jobs-btn-candidate");	
		success =  By.cssSelector("div.relative.bg-white.p-7.lg\\:p-10");
//		errorMSG = By.className("form-error-msg");
	}
	
	public void setValuesInserBox(String fname, String lname, String emailInput, String phone, String pwd, String cpwd) {
//		driver.findElement(fName).sendKeys(fname);
//		driver.findElement(lName).sendKeys(lname);
//		driver.findElement(email).sendKeys(emailInput);
//		driver.findElement(mobile).sendKeys(phone);
//		driver.findElement(password).sendKeys(pwd);
//		driver.findElement(cPassword).sendKeys(pwd);
		wait.until(ExpectedConditions.visibilityOfElementLocated(fName)).sendKeys(fname);
		wait.until(ExpectedConditions.visibilityOfElementLocated(lName)).sendKeys(lname);
		wait.until(ExpectedConditions.visibilityOfElementLocated(email)).sendKeys(emailInput);
		wait.until(ExpectedConditions.visibilityOfElementLocated(mobile)).sendKeys(phone);
		wait.until(ExpectedConditions.visibilityOfElementLocated(password)).sendKeys(pwd);
		wait.until(ExpectedConditions.visibilityOfElementLocated(cPassword)).sendKeys(cpwd);
		
		
//		if(passwordMatch(pwd, cpwd)) {
//			wait.until(ExpectedConditions.visibilityOfElementLocated(password)).sendKeys(pwd);
//			wait.until(ExpectedConditions.visibilityOfElementLocated(cPassword)).sendKeys(cpwd);
//		}else {
//			System.out.println("Password and Confirm Password do not match.");
//			throw new IllegalArgumentException("Passwords do  not match");
//		}
		
		
		
	}
	
	public void setValueProfession(int option) {
		driver.findElement(profession).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-2-option-"+option+"']"))).click();
//		driver.findElement(By.xpath("//div[@id='react-select-3-option-"+option+"']"));
	}
	
	public void setValueSpecialty(int option){
		driver.findElement(specialty).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-3-option-"+option+"']"))).click();
	}
	
	public void setValueCountry(int option){
		driver.findElement(country).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-4-option-"+option+"']"))).click();
	}
	
	public void setValueState(int option){
		driver.findElement(state).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-5-option-"+option+"']"))).click();
	}
	
	public void setValuehearUs(int option){
		driver.findElement(hearus).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-6-option-"+option+"']"))).click();
	}

	public void setValueTerms(String action){
		if(!driver.findElement(terms).isSelected() && action == "click"  ) {
			driver.findElement(terms).click();
		}
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-5-option-"+option+"']")));
	}
	
	public void setValueSubscribe(String action){
		if(!driver.findElement(subscribe).isSelected() && action == "click" ) {
			driver.findElement(subscribe).click();
		}
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-5-option-"+option+"']")));
	}
	
	public void setValueFile(String filePath) {
		driver.findElement(file).sendKeys(filePath);;
	}
	
	public void setSubmit() {
		driver.findElement(submit).sendKeys(Keys.RETURN);
	}
	
	//Successful pop up
	public boolean isSuccessPopupDisplayed(BrowserMobProxy proxy) {
		
		boolean serverCode = false;
		boolean uiSuccessMessage = false;
				
		Har har = proxy.getHar();
		List<HarEntry> entries = har.getLog().getEntries();
		
		for(HarEntry entry:entries) {
			HarResponse response = entry.getResponse();
			int statusCode = response.getStatus();
			
			if(statusCode == 200 || statusCode == 201) {
				serverCode = true;
				System.out.println("Server response detected.");
				System.out.println("Server Code:"+statusCode);
			}
		}
		
		try {
			WebElement primaryMessage = driver.findElement(success).findElement(By.cssSelector("p.text-black"));
			String expectedMessage = "Registered Successfully";
			uiSuccessMessage = primaryMessage.getText().equals(expectedMessage);
			System.out.println("UI Success Message status:"+uiSuccessMessage);
			
		}catch(Exception e) {
			uiSuccessMessage =  false;
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
	
	public boolean captureEmailDuplicate(BrowserMobProxy proxy) {
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
	    if (serverErrorDetected && uiErrorDitected) {
	        System.out.println("Both server and UI indicate 'email already taken'.");
	        return true;
	    }else if(serverErrorDetected) {
	    	System.out.println("Server error isdicated. UI validation did not display.");
	    	return true;
	    }else if(uiErrorDitected){
	    	System.out.println("UI validation message detected. Server error did not display.");
	    	return true;
	    }else {
	        System.out.println("Either server or UI did not indicate 'email already taken'.");
	        return false;
	    }
	}
	
//	public void captureServerErrorMessages(BrowserMobProxy proxy) {
//		Har har = proxy.getHar();
//		List <HarEntry> entries = har.getLog().getEntries();
//		
//		System.out.println("Analyzing Server responses for errors....");
//		boolean errorFound  = false;
//		
//		for(HarEntry entry : entries) {
//			HarResponse response = entry.getResponse();
//			int statusCode = response.getStatus();
//			
//			if (statusCode >= 400) {
//                errorFound = true;
//                System.out.println("Error Response Detected:");
//                System.out.println("Status Code: " + statusCode);
//                System.out.println("URL: " + entry.getRequest().getUrl());
//                System.out.println("Response Body: " + response.getContent().getText());
//            }
//		}
//		
//		if (!errorFound) {
//            System.out.println("No server errors detected in responses.");
//		}
//	}
	
	//password length
	public boolean isPasswordLengthErrorDisplayed(WebDriver driver) {
	    try {
	        WebElement errorMessageElement = driver.findElement(By.xpath("//div[contains(@class, 'form-error-msg') and text()='Password must be between 8 and 12 characters.']"));
	        return errorMessageElement.isDisplayed();
	    } catch (NoSuchElementException e) {
	        return false; // Return false if the element is not found
	    }
	}
	
	//password mismatch
	public boolean isPasswordMismatchErrorDisplayed(WebDriver driver) {
	    try {
	        WebElement errorMessageElement = driver.findElement(By.xpath("//div[contains(@class, 'form-error-msg') and text()='Password does not match with original']"));
	        return errorMessageElement.isDisplayed();
	    } catch (NoSuchElementException e) {
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
	
	//Validate File size
	public boolean isValidFileSize() {
		try {
			WebElement errorMessageElement = driver.findElement(By.xpath("//div[contains(text(),'File size exceeds the 2 MB limit')]"));
			System.out.println("File size is exeed");
			return errorMessageElement.isDisplayed();
		}catch(NoSuchElementException e){
			return false;
		}
	}
	
	
	
	
	
}
