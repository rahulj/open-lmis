/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.pageobjects;

import org.openlmis.UiUtils.TestWebDriver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;
import java.util.Map;

import static com.thoughtworks.selenium.SeleneseTestBase.*;
import static java.lang.String.valueOf;
import static org.openqa.selenium.support.How.*;

public class EPIUsePage extends DistributionTab {

  @FindBy(how = XPATH, using = "//div[1]/div/div/ng-include/div/ul/li[4]/a/span[2]")
  private static WebElement epiUseTab=null;

  @FindBy(how = XPATH, using = "//div[@class='left-navigation ng-scope']/ul/li[4]/a/span[1][@class='status-icon']")
  public static WebElement overallEPIUseIcon=null;

  @FindBy(how = How.XPATH, using = "//input[@value='Apply NR to all fields']")
  private static WebElement applyNRToAllFieldsCheckbox=null;

  @FindBy(how = ID, using = "button_OK")
  private static WebElement okButton=null;

  @FindBy(how = ID, using = "button_Cancel")
  private static WebElement cancelButton=null;

  @FindBy(how = ID, using = "stockAtFirstOfMonth0")
  private static WebElement NRForStockFirstOfMonth0 =null;

  @FindBy(how = ID, using = "expirationDate0")
  private static WebElement NRForExpirationDate0=null;

  @FindBy(how = ID, using = "stockAtEndOfMonth0")
  private static WebElement NRForStockEndOfMonth0 =null;

  @FindBy(how = ID, using = "received0")
  private static WebElement NRForReceived0=null;

  @FindBy(how = ID, using = "distributed0")
  private static WebElement NRForDistributed0 =null;

  @FindBy(how = ID, using = "loss0")
  private static WebElement NRForLoss0=null;

  @FindBy(how = NAME, using = "stockAtFirstOfMonth0")
  private static WebElement textBoxStockAtFirstOfMonth0=null;

  @FindBy(how = NAME, using = "received0")
  private static WebElement textBoxReceived0=null;

  @FindBy(how = NAME, using = "distributed0")
  private static WebElement textBoxDistributed0=null;

  @FindBy(how = NAME, using = "loss0")
  private static WebElement textBoxLoss0=null;

  @FindBy(how = NAME, using = "stockAtEndOfMonth0")
  private static WebElement textBoxStockAtEndOfMonth0=null;

  @FindBy(how = NAME, using = "expirationDate0")
  private static WebElement textBoxExpirationDate0=null;

  public EPIUsePage(TestWebDriver driver) {
    super(driver);
  }

  @Override
  public void verifyIndicator(String color) {
    verifyOverallIndicator(overallEPIUseIcon, color);
  }

  @Override
  public void enterValues(List<Map<String, String>> data) {
    Map<String, String> map = data.get(0);
    enterValueInDistributed(map.get("distributed"), 1);
    enterValueInExpirationDate(map.get("expirationDate"), 1);
    enterValueInLoss(map.get("loss"), 1);
    enterValueInReceived(map.get("received"), 1);
    enterValueInStockAtFirstOfMonth(map.get("firstOfMonth"), 1);
    enterValueInStockAtEndOfMonth(map.get("endOfMonth"), 1);
  }

  @Override
  public void navigate() {
    testWebDriver.waitForElementToAppear(epiUseTab);
    epiUseTab.click();
  }

  public void enterData(Integer stockAtFirstOfMonth, Integer receivedValue, Integer distributedValue,
                        Integer loss, Integer stockAtEndOfMonth, String expirationDate, int rowNumber) {
    enterValueInStockAtFirstOfMonth(stockAtFirstOfMonth.toString(), rowNumber);
    enterValueInReceived(receivedValue.toString(), rowNumber);
    enterValueInDistributed(distributedValue.toString(), rowNumber);
    enterValueInLoss(valueOf(loss), rowNumber);
    enterValueInStockAtEndOfMonth(stockAtEndOfMonth.toString(), rowNumber);
    enterValueInExpirationDate(expirationDate, rowNumber);
  }

  public void verifyAllFieldsDisabled() {
    assertFalse("stockAtFirstOfMonth field enabled.", textBoxStockAtFirstOfMonth0.isEnabled());
    assertFalse("received field enabled.", textBoxReceived0.isEnabled());
    assertFalse("distributed field enabled.", textBoxDistributed0.isEnabled());
    assertFalse("loss field enabled.", textBoxLoss0.isEnabled());
    assertFalse("stockAtEndOfMonth Field enabled.", textBoxStockAtEndOfMonth0.isEnabled());
    assertFalse("expirationDate Field enabled.", textBoxExpirationDate0.isEnabled());

    assertFalse("stockAtFirstOfMonth field NR enabled.", NRForStockFirstOfMonth0.isEnabled());
    assertFalse("received field NR enabled.", NRForReceived0.isEnabled());
    assertFalse("distributed field NR enabled.", NRForDistributed0.isEnabled());
    assertFalse("loss field NR enabled.", NRForLoss0.isEnabled());
    assertFalse("stockAtEndOfMonth Field NR enabled.",NRForStockEndOfMonth0 .isEnabled());
    assertFalse("expirationDate Field NR enabled.",NRForExpirationDate0 .isEnabled());

    assertFalse("applyNRToAllFieldsCheckbox NR enabled.", applyNRToAllFieldsCheckbox.isEnabled());
  }

  public void enterValueInStockAtFirstOfMonth(String value, int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement stockAtFirstOfMonth = testWebDriver.getElementByName("stockAtFirstOfMonth" + rowNumber);
    sendKeys(stockAtFirstOfMonth, value);
    stockAtFirstOfMonth.sendKeys(Keys.TAB);
  }

  public void enterValueInReceived(String value, int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement received = testWebDriver.getElementByName("received" + rowNumber);
    sendKeys(received, value);
    received.sendKeys(Keys.TAB);
  }

  public void enterValueInDistributed(String value, int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement distributed = testWebDriver.getElementByName("distributed" + rowNumber);
    sendKeys(distributed, value);
    distributed.sendKeys(Keys.TAB);
  }

  public void enterValueInLoss(String value, int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement loss = testWebDriver.getElementByName("loss" + rowNumber);
    sendKeys(loss, value);
    loss.sendKeys(Keys.TAB);
  }

  public void enterValueInStockAtEndOfMonth(String value, int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement stockAtEndOfMonth = testWebDriver.getElementByName("stockAtEndOfMonth" + rowNumber);
    sendKeys(stockAtEndOfMonth, value);
    stockAtEndOfMonth.sendKeys(Keys.TAB);
  }

  public void enterValueInExpirationDate(String value, int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement expirationDate = testWebDriver.getElementByName("expirationDate" + rowNumber);
    sendKeys(expirationDate, value);
    expirationDate.sendKeys(Keys.TAB);
  }

  public void checkUncheckStockAtFirstOfMonthNotRecorded(int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement stockAtFirstOfMonthNotRecordedCheckBox = testWebDriver.getElementById("stockAtFirstOfMonth" + rowNumber);
    stockAtFirstOfMonthNotRecordedCheckBox.click();
  }

  public void checkUncheckReceivedNotRecorded(int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement receivedNotRecordedCheckBox = testWebDriver.getElementById("received" + rowNumber);
    receivedNotRecordedCheckBox.click();
  }

  public void checkUncheckDistributedNotRecorded(int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement distributedNotRecordedCheckBox = testWebDriver.getElementById("distributed" + rowNumber);
    distributedNotRecordedCheckBox.click();
  }

  public void checkUncheckLossNotRecorded(int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement lossNotRecordedCheckBox = testWebDriver.getElementById("loss" + rowNumber);
    lossNotRecordedCheckBox.click();
  }

  public void checkApplyNRToAllFields(boolean confirm) {
    applyNRToAllFieldsCheckbox.click();
    if (confirm)
      okButton.click();
    else
      cancelButton.click();
  }

  public void checkApplyNRToStockAtFirstOfMonth0() {
    NRForStockFirstOfMonth0.click();
  }

  public void checkApplyNRToDistributed0() {
    NRForDistributed0.click();
  }

  public void checkApplyNRToLoss0() {
    NRForLoss0.click();
  }

  public void checkApplyNRToReceived0() {
    NRForReceived0.click();
  }

  public void checkUncheckStockAtEndOfMonthNotRecorded(int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement stockAtEndOfMonthNotRecordedCheckBox = testWebDriver.getElementById("stockAtEndOfMonth" + rowNumber);
    stockAtEndOfMonthNotRecordedCheckBox.click();
  }

  public void checkUncheckExpirationDateNotRecorded(int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement expirationDateNotRecordedCheckBox = testWebDriver.getElementById("expirationDate" + rowNumber);
    expirationDateNotRecordedCheckBox.click();
  }

  public void verifyProductGroup(String productGroup, int rowNumber) {
    WebElement productGroupLbl = testWebDriver.getElementByXpath(".//*[@id='epiUseTable']/form/table/tbody/tr[" + rowNumber + "]/td[1]/span");
    testWebDriver.waitForElementToAppear(productGroupLbl);
    assertEquals(productGroup, productGroupLbl.getText());
  }

  public void verifyStockAtFirstOfMonth(String stockAtFirstOfMonth, int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement stockAtFirstOfMonthTxt = testWebDriver.getElementByName("stockAtFirstOfMonth" + rowNumber);
    assertEquals(stockAtFirstOfMonth, stockAtFirstOfMonthTxt.getAttribute("value"));
  }

  public void verifyReceived(String received, int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement receivedTxt = testWebDriver.getElementByName("received" + rowNumber);
    assertEquals(received, receivedTxt.getAttribute("value"));
  }

  public void verifyDistributed(String distributed, int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement distributedTxt = testWebDriver.getElementByName("distributed" + rowNumber);
    assertEquals(distributed, distributedTxt.getAttribute("value"));
  }

  public void verifyLoss(String loss, int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement lossTxt = testWebDriver.getElementByName("loss" + rowNumber);
    assertEquals(loss, lossTxt.getAttribute("value"));
  }

  public void verifyStockAtEndOfMonth(String stockAtEndOfMonth, int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement stockAtEndOfMonthTxt = testWebDriver.getElementByName("stockAtEndOfMonth" + rowNumber);
    assertEquals(stockAtEndOfMonth, stockAtEndOfMonthTxt.getAttribute("value"));
  }

  public void verifyExpirationDate(String expirationDate, int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement expirationDateTxt = testWebDriver.getElementByName("expirationDate" + rowNumber);
    assertEquals(expirationDate, expirationDateTxt.getAttribute("value"));
  }

  public void verifyStockAtFirstOfMonthStatus(boolean status, int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement stockAtFirstOfMonthTxt = testWebDriver.getElementByName("stockAtFirstOfMonth" + rowNumber);
    if (status)
      assertTrue("Stock At First Of Month Disabled.", stockAtFirstOfMonthTxt.isEnabled());
    else
      assertFalse("Stock At First Of Month Enabled.", stockAtFirstOfMonthTxt.isEnabled());
  }

  public void verifyReceivedStatus(boolean status, int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement receivedTxt = testWebDriver.getElementByName("received" + rowNumber);
    if (status)
      assertTrue("Received Disabled.", receivedTxt.isEnabled());
    else
      assertFalse("Received Enabled.", receivedTxt.isEnabled());
  }

  public void verifyDistributedStatus(boolean status, int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement distributedTxt = testWebDriver.getElementByName("distributed" + rowNumber);
    if (status)
      assertTrue("Distributed Disabled.", distributedTxt.isEnabled());
    else
      assertFalse("Distributed Enabled.", distributedTxt.isEnabled());
  }

  public void verifyLossStatus(boolean status, int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement lossTxt = testWebDriver.getElementByName("loss" + rowNumber);
    if (status)
      assertTrue("Loss Disabled.", lossTxt.isEnabled());
    else
      assertFalse("Loss Enabled.", lossTxt.isEnabled());
  }

  public void verifyStockAtEndOfMonthStatus(boolean status, int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement stockAtEndOfMonthTxt = testWebDriver.getElementByName("stockAtEndOfMonth" + rowNumber);
    if (status)
      assertTrue("Stock At End Of Month Disabled.", stockAtEndOfMonthTxt.isEnabled());
    else
      assertFalse("Stock At End Of Month Enabled.", stockAtEndOfMonthTxt.isEnabled());
  }

  public void verifyExpirationDateStatus(boolean status, int rowNumber) {
    rowNumber = rowNumber - 1;
    WebElement expirationDateTxt = testWebDriver.getElementByName("expirationDate" + rowNumber);
    if (status)
      assertTrue("Expiration Date Disabled.", expirationDateTxt.isEnabled());
    else
      assertFalse("Expiration Date Enabled.", expirationDateTxt.isEnabled());
  }

  public void verifyTotal(String total, int rowNumber) {
    WebElement totalLbl = testWebDriver.getElementByXpath(".//*[@id='epiUseTable']/form/table/tbody/tr[" + rowNumber + "]/td[4]/span");
    assertEquals(total, totalLbl.getText());
  }

  @Override
  public void verifyData(Map<String, String> epiData) {
    verifyDistributed(epiData.get("distributed"), 1);
    verifyLoss(epiData.get("loss"), 1);
    verifyExpirationDate(epiData.get("expirationDate"), 1);
    verifyReceived(epiData.get("received"), 1);
    verifyStockAtEndOfMonth(epiData.get("endOfMonth"), 1);
    verifyStockAtFirstOfMonth(epiData.get("firstOfMonth"), 1);
    verifyTotal(epiData.get("total"), 1);
  }
}