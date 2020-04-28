package Testcase;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import BrowserUtils.Browserlaunch;
import BrowserUtils.TestBase;
import BusinessUtils.ControlProperties;

public class GoogleTestcase extends TestBase {

	@Test(priority=1)
	public void LaunchChromeDriver(){
		try {
			ControlProperties.googleText.waitForElementReady(60);
			ControlProperties.googleText.SetText(data.get("Name"));
			ControlProperties.googleText.EnterKey(Keys.ENTER);
			Browserlaunch.driver.quit();
			Log("Browser Closed");
		}catch(Exception ex) {
			Log("Test is not working"+ex.getMessage());
		}
	}

	@Test(priority=2)
	public void ChromeDriverLaunch(){
		try {
			ControlProperties.googleText.waitForElementReady(60);
			ControlProperties.googleText.SetText(data.get("Name"));
			ControlProperties.googleText.EnterKey(Keys.ENTER);
			Browserlaunch.driver.quit();
			Log("Browser Closed");
		}catch(Exception ex) {
			Log("Test is not working"+ex.getMessage());
		}
	}
}