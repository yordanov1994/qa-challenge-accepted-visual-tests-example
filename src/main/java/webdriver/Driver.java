package webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by georgi.yordanov on 4/16/2018.
 */
public class Driver {

    private WebDriver webDriver;

    public Driver(String url) throws MalformedURLException {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("acceptSslCerts", "true");
        capabilities.setCapability("os", "WINDOWS");
        capabilities.setCapability("browser", "chrome");
        this.webDriver = new RemoteWebDriver(new URL(url), capabilities);
        this.webDriver.manage().timeouts().setScriptTimeout(180, TimeUnit.SECONDS);
        this.webDriver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
    }

    public WebDriver getDriver(){
        return this.webDriver;
    }

    public void close(){
        webDriver.close();
    }

    public void getUrl(String url){
        webDriver.get(url);
    }

}
