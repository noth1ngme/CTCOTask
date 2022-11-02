package StepDefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;

import java.io.IOException;

import static PageObjects.CTCO.HomePage.*;
import static helper.Helper.*;

public class MyStepdefs {

    @Before
    public void setUp() {
        initializeDriver();
    }

    @Given("Navigate to CTCO Home Page")
    public void navigateToCTCOHomePage() throws IOException {
        driver.get(data("CTCOUrl"));
    }

    @Then("Open Careers Menu")
    public void openCareersMenu() {
        clickBy(careersNavigation);
    }

    @And("Click on the Vacancies from the list")
    public void clickOnTheVacanciesFromTheList() {
        clickBy(vacanciesNavigation);
    }

    @Then("Open Vacancy with title {string}")
    public void openVacancyWithTitle(String linkName) {
        clickBy(getElementByText(linkName));
    }

    @And("Verify that {int} skills are under the paragraph {string}")
    public void verifyThatSkillsAreUnderTheParagraph(int skillsCount, String paragraphName) {
        int skillsCountOnPage = findElements(getListElementsByParagraphName(paragraphName)).size();
        System.out.println("Skills count under " + paragraphName + skillsCountOnPage);
        Assert.assertEquals(skillsCount, skillsCountOnPage);
    }

    @After
    public void teardown() {
        closeSession();
    }
}
