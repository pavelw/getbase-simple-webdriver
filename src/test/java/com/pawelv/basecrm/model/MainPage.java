package com.pawelv.basecrm.model;

import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
	protected static WebDriver driver;

	// navigation
	@FindBy(css = "a#nav-leads")
	private WebElement navLeads;

	// new lead items
	@FindBy(css = "input#lead-first-name")
	private WebElement newLead_FirstName;

	@FindBy(css = "input#lead-last-name")
	private WebElement newLead_LastName;

	@FindBy(css = "input#lead-company-name")
	private WebElement newLead_CompanyName;

	@FindBy(css = "div.tags-field input")
	private WebElement newLead_Tag;

	public static final String newLead_SaveButton_Locator = "div.edit-toolbar a.save";
	//@FindBy(css = newLead_SaveButton_Locator)
	//private WebElement newLead_SaveButton;

	// lead detail items
	@FindBy(css = "h1 span.detail-title")
	private WebElement leadDetail_title;
	
	//settings leads
	public static final String prefsStatus_first_Locator = "button.edit:eq(0)";
	//@FindBy(css = prefsStatus_first_Locator)
	//private WebElement prefsStatus_firstButton;
	
	@FindBy(css = "div#lead-status input#name")
	private WebElement prefsStatus;
	
	public static final String prefsStatusSave_Locator = "div#lead-status button.save:eq(0)";
	//@FindBy(css = prefsStatusSave_Locator)
	//private WebElement prefsStatusSave;
	
	// leads - main list
	public static final String leadsFilter_header="ul.filter-categories li.status-filter a.filter-heading";
	
	@FindBy(css = "span.term-filter input")
	private WebElement LeadsFilterInput;
	
	// assume that it would be only element given random first lead name
	public static final String LeadsFirstResultName_Locator="ul.leads h3 a";
	@FindBy(css = LeadsFirstResultName_Locator)
	private WebElement LeadsFirstResultName;
	
	@FindBy(css = "ul.object-details span.lead-status")
	private WebElement LeadsDetailsStatus;
	
	// static
	public static final String LEADS_URL = "https://app.futuresimple.com/leads";
	public static final String NEW_LEADS_URL = "https://app.futuresimple.com/leads/new";
	public static final String PREFS_LEADS_STATUS_URL = "https://app.futuresimple.com/settings/leads/lead-status";

	public MainPage(WebDriver driver) {
		MainPage.driver = driver;
	}

	private void openPrefsLeadsStatus() {
		driver.navigate().to(PREFS_LEADS_STATUS_URL);
		
		 (new WebDriverWait(driver, 10)).until(new
				  ExpectedCondition<Boolean>() { public Boolean apply(WebDriver d) {
				  return d.getTitle().toLowerCase().contains("customize leads"); } });
	}
	
	private void openLead() {
		driver.navigate().to(LEADS_URL);

		(new WebDriverWait(driver, 10)).until(new
		  ExpectedCondition<Boolean>() { public Boolean apply(WebDriver d) {
		  return d.getTitle().toLowerCase().contains("leads"); } });

	}

	private void openNewLead() {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(navLeads));

		driver.navigate().to(NEW_LEADS_URL);

		(new WebDriverWait(driver, 10)).until(new
		  ExpectedCondition<Boolean>() { public Boolean apply(WebDriver d) {
		  return d.getTitle().toLowerCase().contains("leads"); } });

	}

	public static void selectValue(String valToBeSelected) {
		List<WebElement> options = driver.findElements(By.tagName("option"));
		for (WebElement option : options) {
			if (valToBeSelected.equalsIgnoreCase(option.getText())) {
				option.click();
			}
		}
	}

	// jQuery click()
	private void jClick(String csslocator) {
		((JavascriptExecutor) driver).executeScript("$('" + csslocator
				+ "').click()");
	}

	/*private void mouseOver(WebElement element) {
		String code = "var fireOnThis = arguments[0];"
				+ "var evObj = document.createEvent('MouseEvents');"
				+ "evObj.initEvent( 'mouseover', true, true );"
				+ "fireOnThis.dispatchEvent(evObj);";
		((JavascriptExecutor) driver).executeScript(code, element);
	}*/

	
	// type new sample lead and save it
	public void newSampleLead(final String firstName, String lastName,
			String companyName, String tagName) {
		
		openNewLead();

		newLead_FirstName.sendKeys(firstName);
		newLead_LastName.sendKeys(lastName);
		newLead_CompanyName.sendKeys(companyName);
		newLead_Tag.sendKeys(tagName);

		this.jClick(newLead_SaveButton_Locator);

		
		  (new WebDriverWait(driver, 10)).until(new
		  ExpectedCondition<Boolean>() { public Boolean apply(WebDriver d) {
		  return leadDetail_title.getText().toLowerCase().contains(firstName);
		  } });

	}

	// change first status
	public void setPrefsStatus(String firstStatusText) {

		openPrefsLeadsStatus();
		
		this.jClick(prefsStatus_first_Locator);
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(prefsStatus));

		prefsStatus.clear();
		prefsStatus.sendKeys(firstStatusText);
		
		this.jClick(prefsStatusSave_Locator);
		
	}

	public void verifyStatus(String status, final String firstNameRandom) {
		
		openLead();
		
		this.jClick(leadsFilter_header);
		
		LeadsFilterInput.sendKeys(firstNameRandom);
		
		// wait until results are shown
		(new WebDriverWait(driver, 10)).until(new
				  ExpectedCondition<Boolean>() { public Boolean apply(WebDriver d) {
					  
				  return LeadsFirstResultName.getText().startsWith(firstNameRandom); } });
	
		// go to details of the lead
		this.jClick(LeadsFirstResultName_Locator);
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(LeadsDetailsStatus));
		
		
		Assert.assertEquals(status, LeadsDetailsStatus.getText());
		
	}
}
