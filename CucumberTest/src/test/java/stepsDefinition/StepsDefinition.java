package stepsDefinition;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class StepsDefinition {

	private WebDriver driver;

	// Metod som genererar slumpm�ssig email
	
	private String RandomEmail() {
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
		StringBuilder email = new StringBuilder();
		Random rnd = new Random();
		while (email.length() < 10) {
			int index = (int) (rnd.nextFloat() * letters.length());
			email.append(letters.charAt(index));
		}
		String RndEmail = email.toString();
		return RndEmail;

	}

	// Metod som genererar slumpm�ssig anv�ndarnamn
	
	private String RandomUser() {
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
		StringBuilder user = new StringBuilder();
		Random rnd = new Random();
		while (user.length() < 10) {
			int index = (int) (rnd.nextFloat() * letters.length());
			user.append(letters.charAt(index));
		}
		String RndUser = user.toString();
		return RndUser;

	}

	// Metod som skapar slumpm�ssiga anv�ndarnamn med 101 bokst�ver eller siffror
	
	private String RandomUser100() {
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
		StringBuilder user101 = new StringBuilder();
		Random rnd = new Random();
		while (user101.length() < 102) {
			int index = (int) (rnd.nextFloat() * letters.length());
			user101.append(letters.charAt(index));
		}
		String RndUser = user101.toString();
		return RndUser;

	}

	// Skapar v�ntemetod som v�ntar tills knappen �r klickbar
	
	private void click(By by) {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(by));
		driver.findElement(by).click();
	}

	// Skapar v�ntemetod som v�ntar tills textrutan hittas
	
	private void sendKeys(By by, String keys) {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(by));
		driver.findElement(by).sendKeys(keys);
	}

	@Given("I have entered Mailchimp site")
	public void i_have_entered_mailchimp_site() {
		System.setProperty("webdriver.chrome.driver", ("C:\\Users\\Administrator\\eclipse-workspace\\Stefan Susnjar\\test\\CucumberTest"));

		driver = new ChromeDriver(); // Startar chrome
		driver.manage().window().maximize();
		driver.get("https://login.mailchimp.com/signup/"); // G�r till webbsidan
		
		// Bl�ddrar ner s� koden fungerar p� mindre sk�rmar
		
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,250)");
	}

	@Given("I have typed in {string}")
	public void i_have_typed_in(String email) {

		// Kontrollerar om korrekt e-postmeddelande k�rs och skriver ett slumpm�ssigt e-post
		
		if (email.equals("TrueEmail")) {
			sendKeys(By.id("email"), (RandomEmail() + "@gmail.com"));

		} else if (email.equals("NoEmail")) { // Om inget e-postmeddelande k�rs skriver den inga symboler i textrutan
			sendKeys(By.id("email"), "");
		}
	}

	@Given("I have also typed in {string}")
	public void i_have_also_typed_in(String username) {

		// Kontrollerar om korrekt anv�ndarnamn k�rs och skriver ett slumpm�ssigt anv�ndarnamn
		
		if (username.equals("TrueUsername")) {
			sendKeys(By.id("new_username"), RandomUser());

		} else if (username.equals("100+UserName")) { // Om anv�ndarnamn med 100+ symboler k�rs, fylls slumpm�ssigt anv�ndarnamn med 101 symboler
			sendKeys(By.id("new_username"), RandomUser100());

		} else if (username.equals("UserAlreadyExist")) { // Om UserAlreadyExist k�rs s� skrivs Ghost92 in
			sendKeys(By.id("new_username"), "Ghost92");
		}

	}

	@Given("I have as well typed in {string}")
	public void i_have_also_typed_inn(String password) {
		
		// Typer i f�rutbest�mt l�senord
		
		sendKeys(By.id("new_password"), password);
	}

	@Then("I press sign up and verify {string} of account")
	public void i_press_sign_up_and_verify_of_account(String message) {
		
		// Klickar Registreringsknapp
		
		click(By.id("create-account"));

		String expected = "";
		String actual = "";

		// Olika p�st�enden som kontrollerar om testfallet har lyckats
		
		if (message.equals("Check your email")) {
			expected = message;
			actual = driver.findElement(By.xpath("//*[@id=\"signup-content\"]/div/div/div/h1"))
					.getAttribute("textContent");
			driver.quit();

		} else if (message.equals("Please enter a value")) {
			expected = message;
			actual = driver.findElement(By.className("invalid-error")).getAttribute("textContent");
			driver.quit();

		} else if (message.equals("Enter a value less than 100 characters long")) {
			expected = message;
			actual = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"))
					.getAttribute("textContent");
			driver.quit();

		} else if (message
				.equals("Another user with this username already exists. Maybe it's your evil twin. Spooky.")) {
			expected = message;
			actual = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"))
					.getAttribute("textContent");
			driver.quit();
		}
		assertEquals(expected, actual);
	}
}
