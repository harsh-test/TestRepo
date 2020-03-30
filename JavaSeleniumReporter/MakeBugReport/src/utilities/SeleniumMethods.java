package utilities;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SeleniumMethods {
	private static WebDriver driver;

	public static void initChromeBrowser() {
		System.setProperty("webdriver.chrome.driver", "..\\MakeBugReport\\Executable\\chromedriver.exe");
		Map<String, Object> preferences = new Hashtable<String, Object>();
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", preferences);
		options.addArguments("headless");
		options.addArguments("window-size=1920x1080");
		// options.addArguments("disable-infobars");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(capabilities);
	}

	public static void SetText(String xpath, String text) {
		driver.findElement(By.xpath(xpath)).sendKeys(text);
	}

	public static void Click(String xpath) {
		driver.findElement(By.xpath(xpath)).click();
	}

	public static String GetText(String xpath) {
		return driver.findElement(By.xpath(xpath)).getText();
	}

	public static void setURL(String URL) {
		driver.manage().window().maximize();
		driver.get(URL);
	}

	public static void keysPut(String keyname) {
		Actions act = new Actions(driver);
		act.sendKeys(Keys.valueOf(keyname)).build().perform();
	}

	public static By getContinuousLocator(String locator) {
		List<WebElement> elem = new ArrayList<>();
		elem = driver.findElements(By.xpath(locator));
		return By.xpath("(" + locator + ")[" + (elem.size()) + "]");
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static void closeDriver() {
		driver.close();
		driver.quit();
	}
}