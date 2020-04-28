package BrowserUtils;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GenericFunctions extends Browserlaunch {

	public WebElement element=null;
	private WebElement parentElement=null;
	private By _by=null;
	public String control_name=null;
	private boolean isExistingElement = false;
	String Xpath = null;
	GenericReport report=new GenericReport();
	Actions action=new Actions(Browserlaunch.driver);
	Select select= null;


	public String getControl_name() {
		return control_name;
	}

	public void setControl_name(String control_name) {
		this.control_name = control_name;
	}

	public By get_by() {
		return _by;
	}

	public GenericFunctions(String by,String name) {
		this.control_name = name;
		this.Xpath = by;
		try
		{
			if (by.contains("/"))
				this._by = By.xpath(by);
			else
				this._by = By.xpath("//*[@*='" + by + "']");
		}
		catch (Exception ex)
		{
			report.Log("Exception:"+ex.getMessage());
		}
	}

	public WebElement GetElement()
	{
		try
		{
			if (isExistingElement)
				return this.element;
			if (this.parentElement == null) {
				element = Browserlaunch.driver.findElement(this._by);   
			}
			else
				element = (WebElement) this.parentElement.findElement(this._by);
		}
		catch (Exception ex)
		{
			report.Log(this.control_name + " not found!");
			throw new ElementNotVisibleException(ex.getMessage());
		}
		return element;
	}

	public void ClickOnElement() {
		element = GetElement();
		boolean IsSuccessfull = false;
		int i = 0;
		while (!IsSuccessfull && i < 300)
		{
			try
			{
				if (!element.isEnabled())
				{
					i++;
					continue;
				}
				element.click();
				IsSuccessfull = true;
				report.Log("Clicked on " + this.control_name);
				break;
			}
			catch (StaleElementReferenceException st)
			{
				element = GetElement();
				i++;
			}
			catch (ElementNotVisibleException En)
			{
				this.ScrollIntoView();
				element.click();
				break;
			}
			catch (Exception ex)
			{
				i++;
			}
		}
		if (i == 200)
			report.Log("Not able to click on"+this.control_name+"element.\n\n");
	}

	public void SetText(String text) {
		element = GetElement();
		try
		{
			element.clear();
			element.sendKeys(text);
			report.Log("Setting text '" + text + "' in " + this.control_name);
		}
		catch (InvalidElementStateException ex)
		{
			throw new InvalidElementStateException("Element " + this.control_name + " not ready!");
		}
		catch (Exception ex)
		{
			try {
				throw new Exception(String.format("Not able to set text is"+ this.control_name+ "control!\""));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void ScrollIntoView() {

	}

	public void DoubleClick(){
		element = GetElement();
		try {
			action.doubleClick(element).perform();
			report.Log("Double Click on" + this.control_name);
		}
		catch(Exception ex) {
			throw new ElementNotVisibleException(String.format("Not able to Double click on"+ this.control_name+ "control!\""));
		}
	}

	public void Hover() {
		element = GetElement();
		try {
			action.moveToElement(element).perform();
			report.Log("Mouse hover on"+this.control_name);
		}
		catch(Exception ex) {
			throw new NoSuchElementException("Mouse hover element not found"+ex.getMessage());
		}
	}

	public void SelectByText(String textName) {
		element = GetElement();
		try {
			select=new Select(element);
			select.selectByVisibleText(textName);
			report.Log("Selected value is"+textName+ "From"+ this.control_name);
		}
		catch(Exception ex) {
			throw new NoSuchElementException("value is not Selected"+ex.getMessage()+" "+this.control_name);
		}
	}

	public void SelectValueByIndex(String index) {
		element = GetElement();
		try {
			select=new Select(element);
			select.selectByVisibleText(index);
			report.Log("Selected value is"+index+ "From"+ this.control_name);
		}
		catch(Exception ex) {
			throw new NoSuchElementException("value is not Selected"+ex.getMessage()+" "+this.control_name);
		}
	}

	public Alert AlertBox() {
		Alert alert =null;
		try {
			alert = (Alert) driver.switchTo().alert(); 
		}
		catch(Exception ex) {
			throw new NoAlertPresentException("Alert is not displayed"+ex.getMessage());
		}
		return alert;
	}

	public List<WebElement> GetMatchingElement(){
		List<WebElement> matchingElement = null;
		try {
			matchingElement = Browserlaunch.driver.findElements(this._by);
			report.Log("Selected Element is"+this.control_name);
		}
		catch(Exception ex) {
			throw new ElementNotVisibleException(this.control_name + "Element not found");
		}
		return matchingElement;
	}

	public void IsDisplayed() {
		element = GetElement();
		try {
			element.isDisplayed();
			report.Log(this.control_name + "Element is displayed");
		}
		catch(Exception ex) {
			throw new ElementNotVisibleException(this.control_name +"No found");
		}
	}

	public void EnterKey(Keys key) {
		element = GetElement();
		try {
			element.sendKeys(key);
			report.Log(this.control_name + " key entered");
		}
		catch(Exception ex) {
			throw new ElementNotVisibleException(this.control_name + "Key not found");
		}
	}

	public void waitForElementReady(int time) {
		element = GetElement();
		try {
			WebDriverWait wait = new WebDriverWait(Browserlaunch.driver,time);
			wait.until(ExpectedConditions.elementToBeClickable(element)); 
		}
		catch(Exception ex) {
			throw new ElementNotVisibleException(ex.getMessage());
		}
	}	
}