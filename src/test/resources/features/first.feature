Feature: CTCO Verify Count of skills on the Vacancy Page

  @Task
  Scenario: Going to the Careers>Vacancy Page of CTCO and getting exact count of Skills under the paragraph "Professional Skills and Qualification"
    Given Navigate to CTCO Home Page
    Then Open Careers Menu
    And Click on the Vacancies from the list
    Then Open Vacancy with title "TEST AUTOMATION ENGINEER"
    And Verify that 8 skills are under the paragraph "Professional skills and qualification:"