package PageObjects.CTCO;

import helper.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import static helper.Helper.driver;
import static helper.Helper.findElement;

public class HomePage {
    public static By careersNavigation = By.cssSelector("a[href='https://ctco.lv/careers/']");
    public static By vacanciesNavigation = By.cssSelector("a[href='https://ctco.lv/careers/vacancies/']");

    public static By getElementByText(String menuItem) {
        By by = By.xpath("//*[text()='" + menuItem + "']");
        if (driver.findElements(by).isEmpty())
            return By.xpath("//*[contains(text(),'" + menuItem + "')]");
        else return by;
    }

    public static By getListElementsByParagraphName(String paragraphName){
        By by = By.xpath("//*[text()='" + paragraphName +"']/parent::*/parent::p/following-sibling::ul[1]/li");
        if (driver.findElements(by).isEmpty())
            return By.xpath("//*[contains(text(),'" + paragraphName + "']/parent::*/parent::p/following-sibling::ul[1]/li");
        else return by;
    }
}
