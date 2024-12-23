package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EnquiryNowObject {
	
	WebDriver driver = null;
	WebDriverWait wait = null;
	
	By name;
	By email;
	By phone;
	By message;
	By submit;
	By success;
	
	public EnquiryNowObject(WebDriver driver, WebDriverWait wait) {
		this.driver=driver;
		this.wait=wait;
		
		initElements();
	}
	
	private void initElements() {
		name = By.id("name");
		email = By.id("email");
		phone = By.id("phoneNumber");
		message = By.id("note");
		submit =  By.className("find-jobs-btn-candidate");
		success =  By.cssSelector("div.relative.bg-white.p-7.lg\\:p-10");
	}
	
	public void setValuesInsertBox(String nameInput, String emailInput, String phoneInput, String messageInput) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(name)).sendKeys(nameInput);
		wait.until(ExpectedConditions.visibilityOfElementLocated(email)).sendKeys(emailInput);
		wait.until(ExpectedConditions.visibilityOfElementLocated(phone)).sendKeys(phoneInput);
		wait.until(ExpectedConditions.visibilityOfElementLocated(message)).sendKeys(messageInput);
	}
	
	//submit function
	public void setSubmit() {
		driver.findElement(submit).sendKeys(Keys.RETURN);		
	}
	
	//Successful pop up
	public boolean isSuccessPopupDisplayed() {
		try {
			WebElement primaryMessage = driver.findElement(success).findElement(By.cssSelector("p.text-black"));
			String expectedMessage = "Message Submitted";
			return primaryMessage.getText().equals(expectedMessage);
			
		}catch(Exception e) {
			return false;
		}
		
	}
	
	//Empty form
	public boolean isEmptyForm() {
		try {
			List <WebElement> errorMessages = driver.findElements(By.className("form-error-msg-enq"));
			
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
	
	//Invalid Email	
	public boolean isValidMail() {
	    try {
	        WebElement errorMessageElement = driver.findElement(By.xpath("//div[contains(@class, 'form-error-msg-enq') and text()='Please enter a valid email address']"));
	        System.out.println("Invalid Email...");
	        return errorMessageElement.isDisplayed();
	    } catch (NoSuchElementException e) {
	    	System.out.println("Valid Email.");
	        return false; // Return false if the element is not found
	    }
	}
	
	//Invalid Email	
	public boolean isValidPhoneNum() {
	    try {
	        WebElement errorMessageElement = driver.findElement(By.xpath("//div[contains(@class, 'form-error-msg-enq') and text()='Please enter valid phone number']"));
	        System.out.println("Invalid Phone Number...");
	        return errorMessageElement.isDisplayed();
	    } catch (NoSuchElementException e) {
	    	System.out.println("Valid Phone Number.");
	        return false; // Return false if the element is not found
	    }
	}	
	
}
