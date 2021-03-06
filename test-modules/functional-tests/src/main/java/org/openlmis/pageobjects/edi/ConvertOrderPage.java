/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.pageobjects.edi;


import com.thoughtworks.selenium.SeleneseTestNgHelper;
import org.openlmis.UiUtils.TestCaseHelper;
import org.openlmis.UiUtils.TestWebDriver;
import org.openlmis.pageobjects.RequisitionPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.io.IOException;
import java.util.List;

import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;
import static com.thoughtworks.selenium.SeleneseTestCase.assertEquals;
import static org.openqa.selenium.support.How.XPATH;


public class ConvertOrderPage extends RequisitionPage {

  @FindBy(how = How.XPATH, using = "//div[@class='ngCellText ng-scope col1 colt1']/span")
  private static WebElement programOnOrderScreen=null;

  @FindBy(how = How.XPATH, using = "//div[@class='ngCellText ng-scope col2 colt2']/span")
  private static WebElement facilityCodeOnOrderScreen=null;

  @FindBy(how = How.XPATH, using = "//div[@class='ngCellText ng-scope col3 colt3']/span")
  private static WebElement facilityNameOnOrderScreen=null;

  @FindBy(how = How.XPATH, using = "//div[@class='ngCellText ng-scope col4 colt4']/span")
  private static WebElement periodStartDateOnOrderScreen=null;

  @FindBy(how = How.XPATH, using = "//div[@class='ngCellText ng-scope col5 colt5']/span")
  private static WebElement periodEndDateOnOrderScreen=null;

  @FindBy(how = How.XPATH, using = "//div[@class='ngCellText ng-scope col8 colt8']/span")
  private static WebElement supplyDepotOnOrderScreen=null;

  @FindBy(how = How.XPATH, using = "//input[@class='ngSelectionCheckbox']")
  private static WebElement checkboxOnOrderScreen=null;

  @FindBy(how = How.XPATH, using = "//input[@value='Convert To Order']")
  private static WebElement convertToOrderButton=null;

  @FindBy(how = How.XPATH, using = "//div[@id='noRequisitionSelectedMsgDiv']")
  private static WebElement noRequisitonSelectedDiv=null;

  @FindBy(how = How.XPATH, using = "//div[@class='input-append input-prepend']/input")
  private static WebElement searchTextBox=null;

  @FindBy(how = How.XPATH, using = "//button[@ng-click='updateSearchParams()']")
  private static WebElement searchButton=null;

  @FindBy(how = How.XPATH, using = "//div[@class='input-append input-prepend']/div/button")
  private static WebElement searchOptionButton=null;

  @FindBy(how = XPATH, using = "//i[@class='icon-ok']")
  private static WebElement emergencyIcon=null;

  @FindBy(how = XPATH, using = "//span[@openlmis-message='message.no.requisitions.for.conversion']")
  private static WebElement noRequisitionPending=null;


  public ConvertOrderPage(TestWebDriver driver) throws IOException {
    super(driver);
    PageFactory.initElements(new AjaxElementLocatorFactory(TestWebDriver.getDriver(), 10), this);
    testWebDriver.setImplicitWait(2);
  }

  public void verifyOrderListElements(String program, String facilityCode, String facilityName, String periodStartDate, String periodEndDate, String supplyFacilityName) throws IOException {
    testWebDriver.waitForElementToAppear(programOnOrderScreen);
    assertEquals(programOnOrderScreen.getText().trim(), program);
    assertEquals(facilityCodeOnOrderScreen.getText().trim(), facilityCode);
    assertEquals(facilityNameOnOrderScreen.getText().trim(), facilityName);
    assertEquals(periodStartDateOnOrderScreen.getText().trim(), periodStartDate);
    assertEquals(periodEndDateOnOrderScreen.getText().trim(), periodEndDate);
    assertEquals(supplyDepotOnOrderScreen.getText().trim(), supplyFacilityName);
  }

  public void clickConvertToOrderButton() {
    testWebDriver.waitForElementToAppear(convertToOrderButton);
    convertToOrderButton.click();
  }

  public void verifyNoRequisitionPendingMessage()
  {
    testWebDriver.waitForPageToLoad();
    noRequisitionPending.isDisplayed();
  }


  public void clickCheckBoxConvertToOrder() {
    testWebDriver.waitForElementToAppear(checkboxOnOrderScreen);
    checkboxOnOrderScreen.click();
  }

  public void verifyMessageOnOrderScreen(String message) {
    testWebDriver.sleep(500);
    SeleneseTestNgHelper.assertTrue(message, noRequisitonSelectedDiv.isDisplayed());
  }

  public void convertToOrder() throws IOException {
    clickCheckBoxConvertToOrder();
    clickConvertToOrderButton();
    clickOk();
  }

  public void verifyNoRequisitionSelectedMessage() throws IOException {
    clickConvertToOrderButton();
    verifyMessageOnOrderScreen("Message 'Please select at least one Requisition for Converting to Order.' is not displayed");
  }

  public void verifyEmergencyStatus() throws IOException {
    testWebDriver.waitForElementToAppear(emergencyIcon);
    assertTrue("Emergency icon should show up", emergencyIcon.isDisplayed());
  }

  public void searchWithOption(String searchOption, String searchString) {
    testWebDriver.waitForElementToAppear(searchOptionButton);
    searchOptionButton.click();
    testWebDriver.sleep(250);
    testWebDriver.waitForElementToAppear(testWebDriver.getElementByXpath("//a[contains(text(),'" + searchOption + "')]"));
    testWebDriver.getElementByXpath("//a[contains(text(),'" + searchOption + "')]").click();
    sendKeys(searchTextBox, searchString);
    searchButton.click();
    testWebDriver.sleep(1000);
  }

  public void searchWithIndex(int index, String searchString) {
    testWebDriver.waitForElementToAppear(searchOptionButton);
    searchOptionButton.click();
    testWebDriver.sleep(500);
    testWebDriver.waitForElementToAppear(testWebDriver.getElementByXpath("html/body/div[1]/div/div[4]/div/div/div/ul/li["+index+"]/a"));
    testWebDriver.getElementByXpath("html/body/div[1]/div/div[4]/div/div/div/ul/li["+index+"]/a").click();
    sendKeys(searchTextBox, searchString);
    searchButton.click();
    testWebDriver.sleep(1000);
  }
    public String getNoRequisitionPendingMessage() {
        return noRequisitionPending.getText();
    }

  public void selectRequisitionToBeConvertedToOrder(int whichRequisition) {
    testWebDriver.waitForAjax();
    List<WebElement> x = testWebDriver.getElementsByXpath("//div[@id='convertToOrderGrid']//input[@class='ngSelectionCheckbox']");
    testWebDriver.waitForElementToAppear(x.get(whichRequisition - 1));
    x.get(whichRequisition - 1).click();
  }
}