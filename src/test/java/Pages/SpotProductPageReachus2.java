package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SpotProductPageReachus2 {

	WebDriver driver = null;
	WebDriverWait wait = null;
	
	By regNow;
	By fname;
	By lname;
	By mobile;
	By email;
	By country;
	By state;
	By profession;
	By specialty;
	By seniority;
	By registrationType;
	By file;
	By terms;
	By subscribe;
	By submit;
	By success;
	
	
	public SpotProductPageReachus2(WebDriver webdriver,WebDriverWait wait) {
		this.driver = webdriver;
		this.wait = wait;
		
		initElements();
		
	}
	
	private void initElements() {
		regNow = By.cssSelector("button.locum-gold-btn");
		fname = By.id("firstName");
		lname = By.id("lastName");
		mobile = By.id("phoneNumber");
		email = By.id("email");
		country = By.xpath("//body/div[@id='root']/section[5]/div[1]/div[1]/div[1]/form[1]/div[1]/div[5]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]");
		state = By.xpath("//body/div[@id='root']/section[5]/div[1]/div[1]/div[1]/form[1]/div[1]/div[5]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]");
		profession = By.xpath("//body/div[@id='root']/section[5]/div[1]/div[1]/div[1]/form[1]/div[1]/div[6]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]");
		specialty = By.xpath("//body/div[@id='root']/section[5]/div[1]/div[1]/div[1]/form[1]/div[1]/div[6]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]");
		seniority = By.xpath("//body/div[@id='root']/section[5]/div[1]/div[1]/div[1]/form[1]/div[1]/div[7]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]");
		registrationType = By.xpath("//body/div[@id='root']/section[5]/div[1]/div[1]/div[1]/form[1]/div[1]/div[7]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]");
		file = By.id("fileUpload");
		terms = By.id("agree");
		subscribe = By.id("subscribe");
		submit = By.className("form-register-gold-btn");
		success = By.cssSelector("div.relative.bg-white.p-7.lg\\:p-10.rounded-lg.flex.flex-col.items-center.text-center");
	}
	
	public void setValueInsertBox(String firstName,String lastName, String mobileInput, String emailInput) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(fname)).sendKeys(firstName);
		wait.until(ExpectedConditions.visibilityOfElementLocated(lname)).sendKeys(lastName);
		wait.until(ExpectedConditions.visibilityOfElementLocated(mobile)).sendKeys(mobileInput);
		wait.until(ExpectedConditions.visibilityOfElementLocated(email)).sendKeys(emailInput);
	}
	
	/**------ Set drop down values ------**/
	
	//Country
	public void setValueCountry(int option) {
		driver.findElement(country).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-2-option-"+option+"']"))).click();
	}
	
	//State
	public void setValueState(int option) {
		driver.findElement(state).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-3-option-"+option+"']"))).click();
	}
	
	//Profession
	public void setValueProfession(int option) {
		driver.findElement(profession).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-4-option-"+option+"']"))).click();
	}
	
	//Specialty
	public void setValueSpecialty(int option) {
		driver.findElement(specialty).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-5-option-"+option+"']"))).click();
	}
	
	//Seniority
	public void setValueSeniiority(int option) {
		driver.findElement(seniority).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-6-option-"+option+"']"))).click();
	}
	
	//Registration Type
	public void setValueRegType(int option) {
		driver.findElement(registrationType).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-7-option-"+option+"']"))).click();
	}
	
	/**----- End -----**/
	
	/**----- File -----**/
	public void setValueFile(String filePath) {
		driver.findElement(file).sendKeys(filePath);
	}
	
	/**----- Radio buttons -----**/
	
	//Terms
	public void setValueTerms(String action) {
		if(!driver.findElement(terms).isSelected() && action == "click"  ) {
			driver.findElement(terms).click();
		}
	}
	
	//Subscribe
	public void setValuesSubscribe(String action) {
		if(!driver.findElement(subscribe).isSelected() && action == "click" ) {
			driver.findElement(subscribe).click();
		}
	}
	
	/**----- End -----**/
		
	/**----- submit button ------**/
	public void setSubmit() {
		driver.findElement(submit).sendKeys(Keys.RETURN);
	}
	
	/**----- Successful popup -----**/	
	public boolean isSuccessPopupDisplay() {
		try {
			
			WebElement primaryMesssage = driver.findElement(success).findElement(By.cssSelector("p.text-blue-500"));
			String expectedMessage = "You have successfully registered with medfuture.";
			System.out.println("Hello World");
			System.out.println(primaryMesssage.getText());
			
			return primaryMesssage.getText().equals(expectedMessage);
			
		}catch(Exception e) {
			return false;
		}
	}
	
	/**------------------------------------------------------------------------------------**/
	
	/**----- Empty form -----**/
	
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
	
	/**-------------------------**/
	
	/**----- Invalid Email -----**/
	
	public boolean isValidMail() {
		try {
			WebElement errorMessageElement = driver.findElement(By.xpath("//div[contains(@class, 'form-error-msg') and text()='Please enter a valid email address']"));
	        System.out.println("Invalid Email...");
	        return errorMessageElement.isDisplayed();
		}catch(NoSuchElementException e) {
			System.out.println("Valid Email...");
			return false;
		}
	}
	
	/**---------------------------**/
	
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
	/**---------------------------------**/
	
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
	
	/**-----------------------------------**/
	
	
}
