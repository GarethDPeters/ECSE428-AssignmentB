package com.garethpeters.cucumber;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.util.List;

public class StepDefinitions {

	private WebDriver driver;
	private final String PATH_TO_CHROME_DRIVER = "/home/gareth/Downloads/chromedriver_linux64/chromedriver";
	private final String OUTLOOK_URL = "https://outlook.live.com/mail/inbox";

	private final String SENDER = "ecse428dummy3@hotmail.com";
	private final String SENDER_PASS = "dummy321";

	private final String SIGN_IN_BTN_FIRST = "officeHeaderMenu";
	private final String SIGN_IN_TB = "loginfmt";
	private final String NEXT_BTN = "idSIButton9";
	private final String PASS_TB = "passwd";
	private final String SIGN_IN_BTN = "idSIButton9";

	private final String NEW_EMAIL_BTN = "id__5";
	private final String SEND_BTN = "Send";
	private final String EMAIL_TB = "input.ms-BasePicker-input.pickerInput_269bfa71";
	private final String SUBJECT_TB = "subjectLine0";
	private final String ATTACH_BTN = "Attach";
	private final String COMPUTER_BTN = "Browse this computer";
	private final String MOST_RECENT_ITEM = "div.n1O1yKKQnfbPMo9fvZ5QH";

	private final String EMAIL_RECIPIENT_TEXT = "div._1GbKnlrcyAfdgFr9WpTgdU";
	private final String SUBJECT_TEXT = "span.dJ4kO5HcLAM4x-GXmxP8n";
	private final String FILE_TEXT = "div._2rAN8ltsKDy-NItWETekFN._1uQGiBv-yESO7W9wcqabjy._2jA_Nob0bnzClVUjNJfDsr";
	private final String ERROR_EMAIL_TEXT = "div._2eDT5LAxGsFuAUVDuX3mz_._3zUVDEaVDhyHmlV2830f9z.F99zb1PyC88tXPxMniJJF";

	@Given("^I am on the Outlook Inbox page$")
	public void givenOnOutlookInbox() throws Throwable {
		setupSeleniumWebDrivers();
		goTo(OUTLOOK_URL);
		signIn();
	}

	@When("^I press on the New Message button$")
	public void PressNewMessagebuton() throws Throwable {
		// Try to find the New Message button and press it
		try {
			System.out.println("Attempting to find new message button... ");
			WebElement btn = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.id(NEW_EMAIL_BTN)));
			System.out.print("Found!\n");
			btn.click();
			System.out.println("Clicking new message button.");
		} catch (Exception e) {
			System.out.println("No new message button present");
		}
	}

	@And("^I enter \"([^\"]*)\" as email recipient address$")
	public void enterValidEmail(String arg1) throws Throwable {
		// Try to find the email recipient text box to input an email address
		try {
			System.out.println("Attempting to find email textbox... ");
			WebElement tb = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.cssSelector(EMAIL_TB)));
			System.out.print("Found!\n");
			tb.sendKeys(arg1);
		} catch (Exception e) {
			System.out.println("No email textbox present");
		}
	}

	@And("^I enter a subject \"([^\"]*)\"$")
	public void enterASubject(String subject) throws Throwable {
		// Try to find the subject line text box to input a subject
		try {
			System.out.println("Attempting to find subject textbox... ");
			WebElement tb = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.id(SUBJECT_TB)));
			System.out.print("Found!\n");
			tb.sendKeys(subject);
		} catch (Exception e) {
			System.out.println("No subject textbox present");
		}
	}

	@When("^I choose \"([^\"]*)\" from my computer using the attachment button$")
	public void chooseFileFromComputer(String arg1) throws Throwable {
		// Try to find the attachment dropdown button and click it
		try {
			System.out.println("Attempting to find attach button... ");
			WebElement btn = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.name(ATTACH_BTN)));
			System.out.print("Found!\n");
			btn.click();
			System.out.println("Clicking attach button.");
		} catch (Exception e) {
			System.out.println("No attach button present");
		}

		// Try to find the browse computer button and upload a file by name
		try {
			System.out.println("Attempting to find browse computer button... ");
			WebElement btn = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.name(COMPUTER_BTN)));
			System.out.print("Found!\n");
			btn.click();
			System.out.println("Clicking browse computer button.");
			getFileFromComputer(arg1);
		} catch (Exception e) {
			System.out.println("No browse computer button present");
		}
	}

	@When("^I press Send$")
	public void pressSend() throws Throwable {
		// Try to find the Send button and click it
		try {
			System.out.println("Attempting to find send button... ");
			WebElement btn = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.name(SEND_BTN)));
			System.out.print("Found!\n");
			btn.click();
			System.out.println("Clicking send button.");
		} catch (Exception e) {
			System.out.println("No send button present");
		}
		Thread.sleep(1000);
	}

	@Then("^the recipient will receive an \"([^\"]*)\" with subject \"([^\"]*)\" and the appropriate \"([^\"]*)\"$")
	public void recipientWillReceiveFile(String email, String subject, String file) throws Throwable {
		navigateToMostRecentMsg();

		// See if the email entry exists, if so, check if it's the same email
		try {
			System.out.println("Attempting to find recipient text... ");
			WebElement emailText = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(EMAIL_RECIPIENT_TEXT)));
			System.out.print("Found!\n");

			Assert.assertTrue(emailText.getText().equals(email));
		} catch (Exception e) {
			System.out.println("Email text not found");
		}

		// See if the subject entry exists, if so, check if it's the same subject
		try {
			System.out.println("Attempting to find subject text... ");
			WebElement subjectText = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(SUBJECT_TEXT)));
			System.out.print("Found!\n");

			Assert.assertTrue(subjectText.getText().equals(subject));
		} catch (Exception e) {
			System.out.println("Subject text not found");
		}

		// See if the file entry exists, if so, check if it's the same filename
		try {
			System.out.println("Attempting to find file text... ");
			WebElement fileText = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(FILE_TEXT)));
			System.out.print("Found!\n");

			Assert.assertTrue(fileText.getText().equals(file));
		} catch (Exception e) {
			System.out.println("File text not found");
		}
	}

	@Then("^the recipient will receive an \"([^\"]*)\" with subject \"([^\"]*)\" and the appropriate \"([^\"]*)\" and \"([^\"]*)\"$")
	public void recipientWillReceiveTwoFile(String email, String subject, String file1, String file2) throws Throwable {
		navigateToMostRecentMsg();

		// See if the email entry exists, if so, check if it's the same email
		try {
			System.out.println("Attempting to find recipient text... ");
			WebElement emailText = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(EMAIL_RECIPIENT_TEXT)));
			System.out.print("Found!\n");

			Assert.assertTrue(emailText.getText().equals(email));
		} catch (Exception e) {
			System.out.println("Email text not found");
		}

		// See if the subject entry exists, if so, check if it's the same subject
		try {
			System.out.println("Attempting to find subject text... ");
			WebElement subjectText = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(SUBJECT_TEXT)));
			System.out.print("Found!\n");

			Assert.assertTrue(subjectText.getText().equals(subject));
		} catch (Exception e) {
			System.out.println("Subject text not found");
		}

		// See if the file entries exists, if so, check if it's the same filenames
		try {
			System.out.println("Attempting to find file texts... ");
			List<WebElement> fileTexts = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(FILE_TEXT)));
			System.out.print("Found!\n");

			Assert.assertTrue(fileTexts.get(0).getText().equals(file1));
			Assert.assertTrue(fileTexts.get(1).getText().equals(file2));
		} catch (Exception e) {
			System.out.println("File texts not found");
		}
	}

	@Then("^I will get an error message stating I have not entered any recipient$")
	public void getErrorMessageNotEnteredEmail() throws Throwable {
		// See if an error message is displayed and compare it with an expected error
		try {
			System.out.println("Attempting to find error message... ");
			WebElement errorMsg = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ERROR_EMAIL_TEXT)));
			System.out.print("Found!\n");

			Assert.assertTrue(errorMsg.getText().equals("This message must have at least one recipient."));
		} catch (Exception e) {
			System.out.println("Error message not found");
		}
	}

	@And("^the email with subject \"([^\\\"]*)\" will not be sent$")
	public void emailNotSent(String subject) throws Throwable {
		navigateToMostRecentMsg();
		// See if the subject entry exists, if so, check if it's a different subject
		try {
			System.out.println("Attempting to find subject text... ");
			WebElement subjectText = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(SUBJECT_TEXT)));
			System.out.print("Found!\n");

			Assert.assertFalse(subjectText.getText().equals(subject));
		} catch (Exception e) {
			System.out.println("Subject text not found");
		}
	}

	private void setupSeleniumWebDrivers() throws MalformedURLException {
		// Set Selenium Web Driver to be Chrome
		if (driver == null) {
			System.out.println("Setting up ChromeDriver... ");
			System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER);
			driver = new ChromeDriver();
			System.out.print("Done!\n");
		}
	}

	private void goTo(String url) {
		if (driver != null) {
			System.out.println("Going to " + url);
			driver.get(url);
		}
	}

	private void signIn() {
		try {
			// Find the initial signin button and click it
			System.out.println("Attempting to find signin button... ");
			WebElement btn = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.className(SIGN_IN_BTN_FIRST)));
			System.out.print("Found!\n");
			btn.click();
			System.out.println("Clicking signin button.");

			// Find the email textbox and enter sender's email address
			System.out.println("Attempting to find signin textbox... ");
			WebElement tb = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.name(SIGN_IN_TB)));
			System.out.print("Found!\n");
			tb.sendKeys(SENDER);

			// Find the Next button and click it
			System.out.println("Attempting to find next button... ");
			btn = (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id(NEXT_BTN)));
			System.out.print("Found!\n");
			btn.click();
			System.out.println("Clicking next button.");

			// Find the password textbox and input correct password
			System.out.println("Attempting to find password textbox... ");
			tb = (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.name(PASS_TB)));
			System.out.print("Found!\n");
			tb.sendKeys(SENDER_PASS);

			// Find 2nd Sign in button and click it
			System.out.println("Attempting to find signin button... ");
			btn = (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id(SIGN_IN_BTN)));
			System.out.print("Found!\n");
			btn.click();
			System.out.println("Clicking signin button.");
		} catch (Exception e) {
			System.out.println("Signin failed");
		}
	}

	private void getFileFromComputer(String filepath) throws Exception {
		Robot r = new Robot();
		Thread.sleep(1000);

		// Press each key one by one to find file in file explorer
		for (int i = 0; i < filepath.length(); i++) {
			int key = KeyEvent.getExtendedKeyCodeForChar(filepath.charAt(i));
			r.keyPress(key);
			Thread.sleep(40);
			r.keyRelease(key);
			Thread.sleep(40);
		}
		r.keyPress(KeyEvent.VK_ENTER); // confirm by pressing Enter in the end
		Thread.sleep(40);
		r.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(40);

		Thread.sleep(2000);
	}

	private void navigateToMostRecentMsg() {
		// Find sent items button and click on it
		try {
			System.out.println("Attempting to find sent items button... ");
			WebElement btn = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Sent Items')]")));
			System.out.print("Found!\n");
			btn.click();
			System.out.println("Clicking sent items button.");
		} catch (Exception e) {
			System.out.println("No sent items button present");
		}

		// Find most recent message and click on it
		try {
			System.out.println("Attempting to find most recent item button... ");
			WebElement btn = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.cssSelector(MOST_RECENT_ITEM)));
			System.out.print("Found!\n");
			btn.click();
			System.out.println("Clicking most recent item button.");
		} catch (Exception e) {
			System.out.println("No most recent item button present");
		}
	}

	// Close browser once finished
	@After
	public void closeBrowser() {
		driver.close();
	}

}
