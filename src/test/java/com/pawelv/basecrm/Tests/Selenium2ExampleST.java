package com.pawelv.basecrm.Tests;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.pawelv.basecrm.model.LoginPage;
import com.pawelv.basecrm.model.MainPage;

//@Listeners(ScreenshotListener.class)
//public class Selenium2ExampleST extends SeleniumBase {
public class Selenium2ExampleST {

	public static final String LOGIN_URL = "https://core.futuresimple.com/sales/users/login";
	public static final String SAMPLE_FIRST_NAME = "first";
	public static final String SAMPLE_LAST_NAME = "last";
	public static final String SAMPLE_COMPANY_NAME = "company";
	public static final String TAG_NAME = "testtag";
	public static final String STATUS = "FRESH";
	
	public static final String LOGIN_EMAIL = "your@email.com";
	public static final String LOGIN_PASSWORD = "your_password";
	

	private LoginPage loginpage;
	private MainPage main;

	@Test
	public void getBaseSampleTest() {

		String firstNameRandom = SAMPLE_FIRST_NAME + Math.random();

		loginpage = PageFactory.initElements(new FirefoxDriver(),
				LoginPage.class);

		main = loginpage.doLogin(LOGIN_URL, LOGIN_EMAIL, LOGIN_PASSWORD);

		main.newSampleLead(firstNameRandom, SAMPLE_LAST_NAME,
				SAMPLE_COMPANY_NAME, TAG_NAME);

		main.setPrefsStatus(STATUS);

		main.verifyStatus(STATUS, firstNameRandom);

	}

}
