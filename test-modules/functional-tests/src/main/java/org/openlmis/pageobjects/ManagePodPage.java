package org.openlmis.pageobjects;


import com.thoughtworks.selenium.SeleneseTestNgHelper;
import org.openlmis.UiUtils.TestWebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import static org.openqa.selenium.support.How.ID;
import static org.openqa.selenium.support.How.XPATH;

public class ManagePodPage extends Page {

  @FindBy(how = XPATH, using = "//div[@class='ngCellText ng-scope']/span[contains(text(),'F10 - Village Dispensary')]")
  private static WebElement facilityCodeNameOnManagePodScreen = null;

  @FindBy(how = XPATH, using = "//div/span[contains(text(),'Village Dispensary')]")
  private static WebElement supplyingDepotNameOnManagePodScreen  = null;

  @FindBy(how = XPATH, using = "//div/span[contains(text(),'MALARIA')]")
  private static WebElement programCodeNameOnManagePodScreen  = null;

  @FindBy(how = XPATH, using = "//div/span[contains(text(),'PeriodName1 (01/12/2012 - 01/12/2015)')]")
  private static WebElement periodDetailsOnManagePodScreen  = null;

  @FindBy(how = XPATH, using = "//div/span/[contains(text(),'16/12/2013 03:06:04')]")
  private static WebElement orderDateTimeDetailsOnManagePodScreen  = null;

  @FindBy(how = XPATH, using = "//div/span[contains(text(),'Transfer failed')]")
  private static WebElement orderStatusDetailsOnManagePodScreen  = null;

  @FindBy(how = XPATH, using = "//div/a[contains(text(),'Update POD')]")
  private static WebElement UpdatePodLinkOnManagePodScreen  = null;

  @FindBy(how = XPATH, using = "//div[@class='ngHeaderText ng-binding colt0']")
  private static WebElement headerOrderNoOnManagePodScreen  = null;

  @FindBy(how = XPATH, using = "//div[@class='ngHeaderText ng-binding colt1']")
  private static WebElement headerSupplyingDepotOnManagePodScreen  = null;

  @FindBy(how = XPATH, using = "//div[@class='ngHeaderText ng-binding colt2']")
  private static WebElement headerFacilityCodeNameOnManagePodScreen  = null;

  @FindBy(how = XPATH, using = "//div[@class='ngHeaderText ng-binding colt3']")
  private static WebElement headerProgramOnManagePodScreen  = null;

  @FindBy(how = XPATH, using = "//div[@class='ngHeaderText ng-binding colt4']")
  private static WebElement headerPeriodDetailsOnManagePodScreen  = null;

  @FindBy(how = XPATH, using = "//div[@class='ngHeaderText ng-binding colt5']")
  private static WebElement headerOrderDateTimeDetailsOnManagePodScreen  = null;

  @FindBy(how = XPATH, using = "//div[@class='ngHeaderText ng-binding colt6']")
  private static WebElement headerOrderStatusOnManagePodScreen  = null;

  @FindBy(how = XPATH, using = "//div[@class='ngHeaderText ng-binding colt7']")
  private static WebElement headerEmergencyOnManagePodScreen  = null;

  @FindBy(how = ID, using = "noOrdersPresent")
  private static WebElement noOrderPresentMessageOnPodScreen = null;


  @FindBy(how = XPATH, using = " //i[@class='icon-ok']")
  private static WebElement emergencyIconOnPodScreen = null;

  public ManagePodPage(TestWebDriver testWebDriver) {
    super(testWebDriver);
    PageFactory.initElements(new AjaxElementLocatorFactory(TestWebDriver.getDriver(), 10), this);
    testWebDriver.setImplicitWait(10);
  }

  public String getHeaderOrderNo() {
    return testWebDriver.getText(headerOrderNoOnManagePodScreen);
  }

  public String getHeaderFacilityCodeName() {
    return testWebDriver.getText(headerFacilityCodeNameOnManagePodScreen);
  }

  public String getHeaderSupplyingDepotName() {
    return testWebDriver.getText(headerSupplyingDepotOnManagePodScreen);
  }

  public String getHeaderProgramCodeName() {
    return testWebDriver.getText(headerProgramOnManagePodScreen);
  }

  public String getHeaderPeriodDetails() {
    return testWebDriver.getText(headerPeriodDetailsOnManagePodScreen);
  }

  public String getHeaderOrderDateTimeDetails() {
    return testWebDriver.getText(headerOrderDateTimeDetailsOnManagePodScreen);
  }

  public String getHeaderOrderStatusDetails() {
    return testWebDriver.getText(headerOrderStatusOnManagePodScreen);
  }

  public String getHeaderEmergency() {
    return testWebDriver.getText(headerEmergencyOnManagePodScreen);
  }

  public String getFacilityCodeName() {
   return testWebDriver.getText(facilityCodeNameOnManagePodScreen);
  }

  public String getSupplyingDepotName() {
    return testWebDriver.getText(supplyingDepotNameOnManagePodScreen);
  }

  public String getProgramCodeName() {
    return testWebDriver.getText(programCodeNameOnManagePodScreen);
  }

  public String getPeriodDetails() {
    return testWebDriver.getText(periodDetailsOnManagePodScreen);
  }

  public String getOrderDateTimeDetails() {
    return testWebDriver.getText(orderDateTimeDetailsOnManagePodScreen);
  }

  public String getOrderStatusDetails() {
    return testWebDriver.getText(orderStatusDetailsOnManagePodScreen);
  }

  public String getUpdatePodLink() {
    return testWebDriver.getText(UpdatePodLinkOnManagePodScreen);
  }

  public void verifyMessageOnManagePodScreen() {
    testWebDriver.sleep(500);
    testWebDriver.refresh();
    SeleneseTestNgHelper.assertTrue("Message not Displayed on Manage Pod Screen",noOrderPresentMessageOnPodScreen.isDisplayed());
  }
}

