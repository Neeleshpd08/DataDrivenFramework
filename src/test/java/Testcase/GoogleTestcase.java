package Testcase;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import BrowserUtils.TestBase;
import BrowserUtils.status;
import BusinessUtils.ControlProperties;

public class GoogleTestcase extends TestBase {

	@Test(priority=1)
	public void LaunchChromeDriver(){
		try {
			ControlProperties.googleText.waitForElementReady(60);
			ControlProperties.googleText.SetText(data.get("Name"));
			ControlProperties.googleText.EnterKey(Keys.ENTER);
			//Assert.assertTrue(false,"Test case failed due to assert fail");
		}catch(Exception ex) {
			Log(status.Fail,"Test is not working"+ex.getMessage());
		}
	}

	@Test(priority=2)
	public void ChromeDriverLaunch(){
		try {
			ControlProperties.googleText.waitForElementReady(60);
			ControlProperties.googleText.SetText(data.get("Name"));
			ControlProperties.googleText.EnterKey(Keys.ENTER);
		}catch(Exception ex) {
			Log(status.Fail,"Test is not working"+ex.getMessage());
		}
	}
}