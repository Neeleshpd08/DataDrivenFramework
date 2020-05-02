package BrowserUtils;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.aventstack.extentreports.Status;

public class TestBase extends GenericReport {

	Properties p=new Properties();
	public HashMap<String,String> data;
	private String Excelpath = "\\Resources\\TestData.xlsx";
	GetData ExcelData=new GetData(Excelpath);
	public Browserlaunch launch=new Browserlaunch();
			
	@BeforeSuite
	public void BeforeSuite() throws IOException {
		GenerateReport();
	}

	@BeforeMethod
	public void BeforeTest(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		data = ExcelData.GetDataByTestCase("Data", testName);
		CreateTest(result.getMethod().getMethodName());
		launch.browserLaunch("chrome","https://www.google.com");
	}

	@AfterMethod
	public void AfterTest(ITestResult result) throws IOException, InterruptedException {
		if(result.getStatus() == ITestResult.FAILURE)
		{
			Log(status.Fail,"Test Case failed is"+result.getThrowable());
			String screenshotPath = getScreenshot(Browserlaunch.driver, result.getName());
			GenericReport.test.addScreenCaptureFromPath(screenshotPath);
		}else if(result.getStatus() == ITestResult.SKIP) {
			Log(status.Skip, "TestCase Skipped is:"+result.getName());
		}
		else if(result.getStatus() == ITestResult.SUCCESS) {
			Log(status.Pass, "TestCase Passed:"+result.getName());
		}
		Browserlaunch.driver.quit();
		Log(status.Pass,"Browser Closed");
	}

	public String getScreenshot(WebDriver driver,String screenshotName) {
		String destination = null;
		try {
			String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			TakesScreenshot ts=(TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);

			destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName +".png";
			File finalDestination = new File(destination);
			FileUtils.copyFile(source,finalDestination);
		}
		catch(Exception ex) {
			Log(status.Fail,"Screenshot is not generated "+ex.getMessage());
		}
		return destination;
	}

	@AfterSuite()
	public void AfterSuite() {
		FlushReport();
	}
}