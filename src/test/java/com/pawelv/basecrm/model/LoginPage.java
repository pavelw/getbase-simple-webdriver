package com.pawelv.basecrm.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	protected WebDriver driver;

	@FindBy(id = "user_email")
	// @CacheLookup
	private WebElement loginInput;

	@FindBy(id = "user_password")
	private WebElement passwordInput;

	@FindBy(css = "button.btn-primary")
	private WebElement loginButton;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public void open(String url) {
		driver.get(url);
	}

	public void close() {
		driver.quit();
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public MainPage doLogin(String loginUrl, String email, String password) {
		this.open(loginUrl);
		
		loginInput.sendKeys(email);
		passwordInput.sendKeys(password);
		loginButton.submit();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id("topbar")));
		
		return PageFactory.initElements(driver, MainPage.class);
	}
}
