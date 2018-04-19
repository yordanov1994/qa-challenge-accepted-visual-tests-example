package applitools;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.selenium.fluent.Target;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by georgi.yordanov on 4/16/2018.
 */
public class ApplitoolsSDKModule {

    private Eyes eyes;

    // Set applitools key
    private String apiKey = "TYPE_APPLITOOLS_KEY_HERE";

    // Set applitools instance if you use private setup
    private static final String APPLITOOLS_URI = "https://eyes.applitools.com";

    public  ApplitoolsSDKModule(WebDriver webDriver, String appName, String batchName, String testName, String matchLevel, boolean isFloatingHeader) throws URISyntaxException {
        this.eyes = new Eyes();
        eyes.setServerUrl(new URI(APPLITOOLS_URI));
        eyes.setStitchMode(StitchMode.CSS);
        this.eyes.setApiKey(apiKey);
        setTestComparisonLevel(matchLevel);

        if (isFloatingHeader) {
            eyes.setStitchMode(StitchMode.CSS);
        }

        BatchInfo batchInfo = new BatchInfo(batchName);
        batchInfo.setId(batchName);
        eyes.setBatch(batchInfo);

        eyes.setForceFullPageScreenshot(true);
        eyes.open(webDriver, appName, testName);
    }


    /**
     * Mapping for comparison levels
     *
     * @param matchLevel - set comparison level, LAYOUT is by default
     */
    public void setTestComparisonLevel(String matchLevel) {
        MatchLevel level = null;
        switch (matchLevel.toLowerCase()) {
            case "exact":
                level = MatchLevel.EXACT;
                break;
            case "strict":
                level = MatchLevel.STRICT;
                break;
            case "content":
                level = MatchLevel.CONTENT;
                break;
            default:
                level = MatchLevel.LAYOUT;
        }
        eyes.setMatchLevel(level);
    }

    /**
     * Take a full page screenshot and compare it against the baseline with given name.
     * This method uses comparison level set in constructor.
     *
     * @param name  - The name which will be set for the screenshot
     */
    public void checkWindow(String name) {
        eyes.checkWindow(name);
    }

    /**
     * Take a full page screenshot with LAYOUT comparison and compare it against the baseline with given name.
     * This method must be used when comparison level set in constructor is different from LAYOUT.
     *
     * @param name - The name which will be set for the screenshot
     */
    public void checkWindowWithLayout(String name) {
        eyes.check(name, Target.window().layout());
    }

    /**
     * Take a WebElement screenshot and compare it against the baseline with given name.
     * This method uses comparison level set in constructor.
     *
     * @param locator  - WebElement locator
     * @param name     - The name which will be set for the screenshot
     */
    public void checkElement(By locator, String name) {
        eyes.check(name, Target.region(locator));
    }


    /**
     * Take a WebElement screenshot with LAYOUT comparison and compare it against beseline with given name.
     * This method must be used when comparison level set in constructor is different from LAYOUT.
     *
     * @param locator  - WebElement locator
     * @param name     - The name which will be set for the screenshot
     */
    public void checkElementWithLayoutComparison(By locator, String name) {
        eyes.check(name, Target.region(locator).layout());
    }

    /**
     *  Close applitools session
     *  @return - true / false - whether the test is passed or failed
     */
    public boolean close(){
        TestResults testResults;
        boolean isTestPassed = false;
        try {
            testResults = eyes.close();
            isTestPassed = testResults.isPassed() || testResults.isNew();
        } finally {
            eyes.abortIfNotClosed();
        }
        return isTestPassed;
    }
}
