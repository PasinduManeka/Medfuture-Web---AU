package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CandidateRegister {
	
	private static WebElement element = null;
	
	
	public static WebElement text_Fname(WebDriver driver, WebDriverWait wait) {
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName")));
		return element;
	}
	
	public static WebElement text_Lname(WebDriver driver, WebDriverWait wait) {
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastName")));
		return element;
	}
	
	//drop downs
	public static WebElement drop_Profession(WebDriver driver, WebDriverWait wait) {
		element = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-select-2-input")));
		return element;
	}
	
	public static WebElement drop_Speciality(WebDriver driver, WebDriverWait wait) {
		element = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-select-3-input")));
		return element;
	}
	
	public static WebElement drop_Country(WebDriver driver, WebDriverWait wait) {
		element = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-select-4-input")));
		return element;
	}
	
	public static WebElement drop_State(WebDriver driver, WebDriverWait wait) {
		element = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-select-5-input")));
		return element;
	}
	
	public static WebElement text_Email(WebDriver driver, WebDriverWait wait) {
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
		return element;
	}
	
	public static WebElement text_Mobile(WebDriver driver, WebDriverWait wait) {
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("phoneNumber")));
		return element;
	}
	
	public static WebElement text_Password(WebDriver driver, WebDriverWait wait) {
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
		return element;
	}
	
	public static WebElement text_CPassword(WebDriver driver, WebDriverWait wait) {
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmPassword")));
		return element;
	}
	
	public static WebElement drop_hearUs(WebDriver driver, WebDriverWait wait) {
		element = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-select-6-input")));
		return element;
	}
	
	public static WebElement text_terms(WebDriver driver, WebDriverWait wait) {
		element = wait.until(ExpectedConditions.elementToBeClickable(By.id("agree")));
		return element;
	}
	
	public static WebElement text_subscribe(WebDriver driver, WebDriverWait wait) {
		element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-2-option-0']")));
		return element;
	}
	
	//Option (drop down element)
	public static WebElement options_Profession(WebDriver driver, WebDriverWait wait) {
		element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-2-option-0']")));
		return element;
	}
	
	public static WebElement options_Specialty(WebDriver driver, WebDriverWait wait) {
		element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-3-option-0']")));
		return element;
	}
	
	public static WebElement options_Country(WebDriver driver, WebDriverWait wait) {
		element = driver.findElement(By.xpath("//div[@id='react-select-4-option-0']"));
		return element;
	}
	
	public static WebElement options_State(WebDriver driver, WebDriverWait wait) {
		element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-5-option-0']")));
		return element;
	}
	
	public static WebElement options_HearUs(WebDriver driver, WebDriverWait wait) {
		element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='react-select-6-option-0']")));
		return element;
	}
	

}
