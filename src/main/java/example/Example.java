package example;

import applitools.ApplitoolsSDKModule;
import org.openqa.selenium.By;
import webdriver.Driver;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 * Created by georgi.yordanov on 4/16/2018.
 */
public class Example {

    public static void main(String [] args) throws MalformedURLException, URISyntaxException {
        // Start Browser and open site
        Driver driver = new Driver("http://localhost:4444/wd/hub");

        driver.getUrl("http://qachallengeaccepted.com/");

        ApplitoolsSDKModule applitoolsSDKModule = new ApplitoolsSDKModule(driver.getDriver(),"QACH4.0","TestBatch","Test","strict",false);

        // Visual check for specific WebElement
        applitoolsSDKModule.checkElement(By.xpath("//img[@src='images/event/GeorgiYordanov.png']/../.."),"Visual Test Automation in Adidas - Strict Example");

        // Visual check for specific WebElement with LAYOUT comparison level
        applitoolsSDKModule.checkElementWithLayoutComparison(By.xpath("//img[@src='images/event/GeorgiYordanov.png']/../.."),"Visual Test Automation in Adidas");

        // Visual check for full page
        applitoolsSDKModule.checkWindow("QA Challenge page - Strict Example");

        // Visual check for full page with LAYOUT comparison level
        applitoolsSDKModule.checkWindowWithLayout("QA Challenge page");

        driver.close();

        // Close applitools session
        applitoolsSDKModule.close();

    }

}
