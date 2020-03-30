package runner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import utilities.SeleniumMethods;

public class MainClass2 {
	private static WritableWorkbook wwb;
	private static WritableSheet wsh;
	private static Label Title, Weight, Price;
	private static File wfile;
	private static int count = 1, switcher, put = 1;

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		// Create excel
		wfile = new File(System.getProperty("user.dir") + "\\Output\\grocery.xls");
		try {
			wwb = Workbook.createWorkbook(wfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wsh = wwb.createSheet("item", 0);

		// Open Browser
		SeleniumMethods.initChromeBrowser();
		/*
		 * SeleniumMethods.setURL(
		 * "https://www.bigbasket.com/pc/bakery-cakes-dairy/cakes-pastries/" +
		 * "?nc=ct-fa&sid=5-F4oY2ibWQBoWMBqHNrdV9saXN0kKJuZsOiY2OjMzY3qWJhdGNoX2lkeACiYW_ConVywqJhcMOibHTNARuhb6pwb3B1bGFyaXR5pXNyX2lkAaNtcmnNDi4%3D#!page=1"
		 * );
		 */

		SeleniumMethods.setURL("https://www.bigbasket.com/");
		Thread.sleep(3000);

		// Select state and pin
		SeleniumMethods.Click("//*[@class='hvc']");
		Thread.sleep(2000);
		SeleniumMethods.Click(
				"/html/body/div[1]/div[1]/div[3]/header/div/div/div/div/ul/li[2]/div/div/div[2]/form/div[1]/div/div/span");
		Thread.sleep(2000);
		SeleniumMethods.SetText(
				"/html/body/div[1]/div[1]/div[3]/header/div/div/div/div/ul/li[2]/div/div/div[2]/form/div[1]/div/input[1]",
				"delhi");
		SeleniumMethods.keysPut("ENTER");
		Thread.sleep(1000);
		SeleniumMethods.SetText("//*[@id='areaselect']", "110092");
		Thread.sleep(2000);
		SeleniumMethods.keysPut("ENTER");
		Thread.sleep(1000);
		SeleniumMethods.keysPut("ENTER");

		Thread.sleep(2000);
		SeleniumMethods.getDriver().get(
				"https://www.bigbasket.com/pc/gourmet-world-food/chocolates-biscuits/luxury-chocolates-gifts/?nc=nb#!page=1");
		Thread.sleep(5000);
		for (int j = 1; j <= 7; j++) {
			if (j == 2 || j == 6)
				continue;
			else {
				// Select filter
				SeleniumMethods.Click("//*[@id='filterbar']/div[4]/div[2]/section/div[" + j + "]/label/span[1]");
				Thread.sleep(3000);

				// start loop
				List<WebElement> Cards = SeleniumMethods.getDriver()
						.findElements(By.xpath("//*[@class='col-sm-12 col-xs-12 add-bskt']"));
				System.out.println(Cards.size());
				for (int i = 1; i <= Cards.size(); i++) {
					Thread.sleep(7000);
					// Select title

					if (i > 4 && (i % 4) == 1)
						++count;
					System.out.println("CARD:::" + count);

					Title = new Label(0, put, SeleniumMethods.GetText(
							"/html/body/div[1]/div[3]/product-deck/section/div[2]/div[4]/div[1]/div/div/div[2]/div/div[1]/product-template/div/div[4]/div[1]/h6")
							+ SeleniumMethods.GetText(
									"/html/body/div[1]/div[3]/product-deck/section/div[2]/div[4]/div[1]/div/div/div[2]/div/div["
											+ count + "]/product-template/div/div[4]/div[1]/a"));

					String str = SeleniumMethods.GetText(
							"/html/body/div[1]/div[3]/product-deck/section/div[2]/div[4]/div[1]/div/div/div[2]/div/div["
									+ count + "]/product-template/div/div[4]/div[2]/div[1]/span/span[1]");
					if (str.contains("Combo"))
						Weight = new Label(1, put, "Combo");
					else
						Weight = new Label(1, put, str.substring(0, str.indexOf(' ')));

					if ((j % 2) == 0)
						switcher = 1;
					else
						switcher = 1;

					String str1 = SeleniumMethods.GetText(
							"/html/body/div[1]/div[3]/product-deck/section/div[2]/div[4]/div[1]/div/div/div[2]/div/div["
									+ (count) + "]/product-template/div/div[4]/div[3]/div/div[1]/h4/span/span["
									+ switcher + "]");
					Price = new Label(2, put, str1);

					// Write to excel
					try {
						wsh.addCell(Weight);
						wsh.addCell(Title);
						wsh.addCell(Price);
					} catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Thread.sleep(1000);
					put++;
					count++;
					// End loop
				}
			}

			Thread.sleep(3000);
			// Deselect filter
			SeleniumMethods.Click("//*[@id='filterbar']/div[4]/div[2]/section/div[" + j + "]/label/span[1]");
			Thread.sleep(3000);
			count = 1;

		}

		// Flush data into excel
		try {
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Close excel and driver
		SeleniumMethods.closeDriver();
	}
}
