package helper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class Helper {
    public static ChromeOptions opt;
    public static ChromeDriver driver;
    public static JavascriptExecutor executor;
    public static WebDriverWait waitFor15Secs;
    public static String url;


    public static void initializeDriver() {
        opt = new ChromeOptions();
        capabilities();
        driver = new ChromeDriver(opt);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        waitFor15Secs = new WebDriverWait(driver, 15);
        driver.manage().window().maximize();
        executor = driver;
    }

    public static void capabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, opt);
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        opt.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        WebDriverManager.chromedriver().setup();
        opt.addArguments("--disable-notifications");
        //opt.addArguments("--headless");
        opt.addArguments("disable-infobars");
        opt.addArguments("--disable-client-side-phishing-detection");
        opt.addArguments("--webview-disable-safebrowsing-support");
        opt.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        opt.setExperimentalOption("useAutomationExtension", false);
        opt.addArguments("--disable-web-security");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        opt.setExperimentalOption("prefs", prefs);
    }

    public static String data(String data) throws IOException { // Extracts values from property file
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("Properties/Properties.properties");
        prop.load(fis);
        return prop.getProperty(data);
    }

    public static void waitAndClickBy(By by) {
        try {
            waitFor15Secs.until(ExpectedConditions.elementToBeClickable(by)).click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            clickBy_JS(by);
        }
    }

    public static void waitForElement(By by) throws InterruptedException {
        int seconds = 0;

        while (driver.findElements(by).size() < 1) {
            Thread.sleep(1000);
            System.out.println("Waiting for element: " + by);
            seconds++;
            if (seconds == 20) {
                System.out.println("Waited Element: " + by + " For " + seconds + " seconds and exiting loop");
                break;
            }
        }

    }

    public static void clickBy(By by) {
        driver.findElement(by).click();
    }

    public static void clickBy_JS(By by) {
        WebElement element = findElement(by);
        executor.executeScript("arguments[0].click();", element);
        System.out.println("Clicked " + by);
    }

    public static boolean isEmpty(By by) {
        return driver.findElements(by).isEmpty();
    }

    public static WebElement findElements(By by, int index) {
        return driver.findElements(by).get(index);
    }

    public static List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    public static WebElement findElement(By by) {
        return driver.findElement(by);
    }

    public static void closeSession() {
        driver.quit();
        System.out.println("ChromeDriver has closed");
    }

    public static void sendSlackMessage(String message) {
        String link = "https://hooks.slack.com/services///";
        driver.executeScript("var zzz = new XMLHttpRequest(); zzz.open(\"POST\", \"" + link + "\"); zzz.send('{\"text\":\"" + message + "\"}');");
    }

    public void takeScreenshot() {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File("headless_screenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
