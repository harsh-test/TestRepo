package TestCases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class NewTest {
	private static WebDriver driver;
	private static SoftAssert sa = new SoftAssert();
  @Test (priority=1)
  public void initBrowser() {
	  String chromeDriverPath = System.getProperty("user.dir")+"/Resources/Executables/chromedriver.exe";  
	  System.setProperty("webdriver.chrome.driver", chromeDriverPath);  
	  ChromeOptions options = new ChromeOptions();  
	  options.addArguments("--headless", "--disable-gpu", /*"--window-size=1920,1200",*/"--ignore-certificate-errors");  
	  driver = new ChromeDriver(options);
	  
  }
  @Test (priority=2)
  public void checkTitle() {
	  driver.get("https://phptravels.com/Demo/");
	  System.out.println(driver.getTitle());
	  sa.assertEquals(driver.getTitle(), "PHP TRAVELS");
	  sa.assertAll();
  }
  @Test (priority = 3)
  public void tearDown() {
	  driver.close();
	  driver.quit();
  }
  
}
