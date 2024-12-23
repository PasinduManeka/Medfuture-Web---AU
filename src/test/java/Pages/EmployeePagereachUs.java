package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmployeePagereachUs {
	
	WebDriver driver = null;
	WebDriverWait wait = null;
	
	By regNow;
	By cname;
	By tname;
	By abn;
	By websiteURL;
	By name;
	By designation;
	By email;
	By mobile;
	By industryType;
	By profession;
	By placementType;
	By message;
	By file;
	By terms;
	By subscribe;
	By submit;
	By success;
	
	public EmployeePagereachUs(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait=wait;
		
		initElements();
		
	}
	
	public void initElements() {
		cname = By.id("clientName");
		tname = By.id("tradeName");
		abn = By.id("businessNumber");
		websiteURL = By.id("website");
		name = By.id("name");
		designation = By.id("designation");
		email = By.id("email");
		mobile = By.id("phoneNumber");
		industryType = By.xpath("//body/div[@id='root']/section[4]/div[1]/div[2]/form[1]/div[6]/div[1]/div[1]/div[1]/div[1]/div[2]");
		profession = By.xpath("//body/div[@id='root']/section[4]/div[1]/div[2]/form[1]/div[7]/div[1]/div[1]/div[1]/div[1]/div[2]");
		placementType = By.xpath("//body/div[@id='root']/section[4]/div[1]/div[2]/form[1]/div[8]/div[1]/div[1]/div[1]/div[1]/div[2]");
		message = By.id("message");
		file = By.id("fileUpload");
		terms = By.id("agree");
		subscribe = By.id("subscribe");
		submit = By.className("find-jobs-btn-candidate");
		success = By.cssSelector("div.relative.bg-white.p-7.lg\\:p-10.rounded-lg.flex.flex-col.items-center.text-center");
		
	}
	
	/**----- Set Input boxes values -----**/
	public void setInputBoxValues(String clientInput, String tradeNameInput, String abnInput, String websiteUrl, String nameInput, String designationInput, String emailInput, String mobileInput, String messageInput) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(cname)).sendKeys(clientInput);
		wait.until(ExpectedConditions.visibilityOfElementLocated(tname)).sendKeys(tradeNameInput);
		wait.until(ExpectedConditions.visibilityOfElementLocated(abn)).sendKeys(abnInput);
		wait.until(ExpectedConditions.visibilityOfElementLocated(websiteURL)).sendKeys(websiteUrl);
		wait.until(ExpectedConditions.visibilityOfElementLocated(name)).sendKeys(nameInput);
		wait.until(ExpectedConditions.visibilityOfElementLocated(designation)).sendKeys(designationInput);
		wait.until(ExpectedConditions.visibilityOfElementLocated(email)).sendKeys(emailInput);
		wait.until(ExpectedConditions.visibilityOfElementLocated(mobile)).sendKeys(mobileInput);
		wait.until(ExpectedConditions.visibilityOfElementLocated(message)).sendKeys(messageInput);
	};
	
	/**--------------------------------------**/
	
	/**----- Drop downs -----**/
	
	//Industry Type
	public void setIndustryType(int option) {
		driver.findElement(industryType).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-2-option-"+option+"']"))).click();
	}
	
	//Profession
	public void setProfession(int option) {
		driver.findElement(profession).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-3-option-"+option+"']"))).click();
	}
	
	//Placement
	public void setPlacementType(int option) {
		driver.findElement(placementType).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-4-option-"+option+"']"))).click();
	}
	
	/**------------------------------------**/
	
	/**----- File -----**/
	public void setValueFile(String filePath) {
		driver.findElement(file).sendKeys(filePath);
	}
	
	/**------------------------------------**/
	
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
	
	/**------------------------------------------**/
	
	//Submit
	public void setSubmit() {
		driver.findElement(submit).click();
	}
	
	/**-------------------------------------------**/
	
	//successful pop up
	public boolean findSuccessfulPopup() {
		WebElement primaryMessage = driver.findElement(success).findElement(By.cssSelector("p.text-black"));
		String expectedElement = "Registered Successful";		
		return primaryMessage.getText().equals(expectedElement);
	}
	
	/**--------------------------------------------**/
	
	//capture error message
	public boolean findErrorMsgElement() {
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
		}catch (Exception e){
			System.out.println("Error:"+e);
			return false;
		}
	}
	
	/**--------------------------------------------**/
	
	//Invalid Email
	
	public boolean isValidEmail() {
		try {
			WebElement errorMessageElement = driver.findElement(By.xpath("//div[contains(text(),'Please enter a valid email address')]"));
	        System.out.println("Invalid Email...");
	        return errorMessageElement.isDisplayed();
		}catch(Exception e) {
			System.out.println("Error:"+e);
			return false;
		}
	}
	
	/**---------------------------------------------**/
	
	//Invalid mobile number	
	public boolean isValidmobileNumber() {
		try {
			WebElement errorMessageElement = driver.findElement(By.xpath("//div[contains(text(),'Please enter a valid Phone Number')]"));
	        System.out.println("Invalid Mobile...");
			return true;
		}catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	/**----------------------------------------------**/
	
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
