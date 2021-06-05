package BrowserUtils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

public class Browserlaunch {
	
	public static WebDriver driver = null;
	private String destination = System.getProperty("user.dir");
	
	public void browserLaunch(String driverName,String URL) {
		if("chrome" == driverName.trim()) {
			System.out.println("=============="+ driverName + "===============");
			System.setProperty("webdriver.chrome.driver",destination+"\\Resources\\chromedriver.exe");
			driver=new ChromeDriver();
		}
		else if("IE" == driverName.trim()) {
			System.setProperty("webdriver.ie.driver",destination+"\\Resources\\IEDriverServer.exe");
			driver =new InternetExplorerDriver(); 
		}
		else if("Firefox" == driverName.trim()) {
			System.setProperty("webdriver.gecko.driver",destination+"\\Resources\\geckodriver.exe");
			driver =new FirefoxDriver();
		}
		else if("Safari" == driverName.trim()) {
			driver =new SafariDriver();
		}
		else {
			System.out.println("please choose driver");
			System.out.println("=============="+ driverName.trim() + "===============");
		}
		driver.navigate().to(URL);
		driver.manage().window().maximize();
	}
	
}
