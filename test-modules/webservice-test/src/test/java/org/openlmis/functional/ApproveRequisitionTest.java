/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.functional;

import org.openlmis.UiUtils.HttpClient;
import org.openlmis.UiUtils.ResponseEntity;
import org.openlmis.restapi.domain.Report;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;

import static com.thoughtworks.selenium.SeleneseTestBase.assertEquals;
import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;

public class ApproveRequisitionTest extends JsonUtility {

  public static final String FULL_JSON_APPROVE_TXT_FILE_NAME = "ReportJsonApprove.txt";
  public static final String FULL_JSON_MULTIPLE_PRODUCTS_APPROVE_TXT_FILE_NAME = "ReportJsonMultipleProductsApprove.txt";

  @BeforeMethod(groups = {"webservice","webserviceSmoke"})
  public void setUp() throws Exception {
    super.setup();
    super.setupTestData(false);
    super.setupDataRequisitionApprover();
    dbWrapper.updateRestrictLogin("commTrack", true);
  }

  @AfterMethod(groups = {"webservice","webserviceSmoke"})
  public void tearDown() throws IOException, SQLException {
    dbWrapper.deleteData();
    dbWrapper.closeConnection();
  }

  @Test(groups = {"webserviceSmoke"})
  public void testApproveRequisitionValidRnR() throws Exception {
    HttpClient client = new HttpClient();
    dbWrapper.updateVirtualPropertyOfFacility("F10", "true");
    client.createContext();
    //TODO
    submitRequisition("commTrack1", "HIV");
    String id = String.valueOf(dbWrapper.getMaxRnrID());
    dbWrapper.updateRequisitionStatus("AUTHORIZED", "commTrack", "HIV");

    Report reportFromJson = JsonUtility.readObjectFromFile(FULL_JSON_APPROVE_TXT_FILE_NAME, Report.class);
    reportFromJson.getProducts().get(0).setProductCode("P10");
    reportFromJson.getProducts().get(0).setQuantityApproved(65);
    reportFromJson.setApproverName("some random name");

    ResponseEntity responseEntity =
        client.SendJSON(getJsonStringFor(reportFromJson),
            "http://localhost:9091/rest-api/requisitions/" + id + "/approve",
            "PUT",
            "commTrack",
            "Admin123");

    String response = responseEntity.getResponse();

    assertEquals(200, responseEntity.getStatus());
    assertTrue(response.contains("success\":\"R&R approved successfully!"));
    assertEquals("APPROVED", dbWrapper.getAttributeFromTable("requisitions", "status", "id", id));
    assertEquals("some random name", dbWrapper.getAttributeFromTable("requisition_status_changes", "name", "rnrId", id));
    ResponseEntity responseEntity1 = client.SendJSON("", "http://localhost:9091/feeds/requisition-status/recent", "GET", "", "");

    assertTrue(responseEntity1.getResponse().contains("{\"requisitionId\":" + id + ",\"requisitionStatus\":\"APPROVED\",\"emergency\":false,\"startDate\":"));
    assertTrue(responseEntity1.getResponse().contains(",\"endDate\":"));
  }


  @Test(groups = {"webservice"})
  public void testApproveRequisitionUnauthorizedAccess() throws Exception {
    HttpClient client = new HttpClient();
    client.createContext();
    dbWrapper.updateVirtualPropertyOfFacility("F10", "true");

    submitRequisition("commTrack1", "HIV");
    Long id = (long) dbWrapper.getMaxRnrID();
    dbWrapper.updateRequisitionStatus("AUTHORIZED", "commTrack", "HIV");

    Report reportFromJson = JsonUtility.readObjectFromFile(FULL_JSON_APPROVE_TXT_FILE_NAME, Report.class);

    reportFromJson.getProducts().get(0).setProductCode("P10");
    reportFromJson.getProducts().get(0).setQuantityApproved(65);
    reportFromJson.setApproverName("some random name");

    ResponseEntity responseEntity = client.SendJSON(getJsonStringFor(reportFromJson),
        "http://localhost:9091/rest-api/requisitions/" + id + "/approve", "PUT",
        "commTrack100", "Admin123");
    client.SendJSON("", "http://localhost:9091/", "GET", "", "");

    assertEquals(401, responseEntity.getStatus());
  }

  @Test(groups = {"webservice"})
  public void testApproveRequisitionProductNotAvailableInSystem() throws Exception {
    HttpClient client = new HttpClient();
    client.createContext();
    dbWrapper.updateVirtualPropertyOfFacility("F10", "true");

    submitRequisition("commTrack1", "HIV");
    Long id = (long) dbWrapper.getMaxRnrID();
    dbWrapper.updateRequisitionStatus("AUTHORIZED", "commTrack", "HIV");

    Report reportFromJson = JsonUtility.readObjectFromFile(FULL_JSON_APPROVE_TXT_FILE_NAME, Report.class);

    reportFromJson.getProducts().get(0).setProductCode("P1000");
    reportFromJson.getProducts().get(0).setQuantityApproved(65);
    reportFromJson.setApproverName("some random name");

    ResponseEntity responseEntity =
        client.SendJSON(getJsonStringFor(reportFromJson),
            "http://localhost:9091/rest-api/requisitions/" + id + "/approve",
            "PUT",
            "commTrack",
            "Admin123");

    String response = responseEntity.getResponse();
    assertEquals(400, responseEntity.getStatus());
    assertEquals("{\"error\":\"Invalid product codes [P1000]\"}", response);
  }

  @Test(groups = {"webservice"})
  public void testApproveRequisitionProductNotAvailableInRnR() throws Exception {
    HttpClient client = new HttpClient();
    client.createContext();
    dbWrapper.updateVirtualPropertyOfFacility("F10", "true");

    submitRequisition("commTrack1", "HIV");
    Long id = (long) dbWrapper.getMaxRnrID();
    dbWrapper.updateRequisitionStatus("AUTHORIZED", "commTrack", "HIV");

    Report reportFromJson = JsonUtility.readObjectFromFile(FULL_JSON_APPROVE_TXT_FILE_NAME, Report.class);

    reportFromJson.getProducts().get(0).setProductCode("P11");
    reportFromJson.getProducts().get(0).setQuantityApproved(65);
    reportFromJson.setApproverName("some random name");

    ResponseEntity responseEntity =
        client.SendJSON(getJsonStringFor(reportFromJson),
            "http://localhost:9091/rest-api/requisitions/" + id + "/approve",
            "PUT",
            "commTrack",
            "Admin123");

    String response = responseEntity.getResponse();
    assertEquals(400, responseEntity.getStatus());
    assertEquals("{\"error\":\"Invalid product codes [P11]\"}", response);
  }

  @Test(groups = {"webservice"})
  public void testApproveRequisitionProgramProductsInactive() throws Exception {
    HttpClient client = new HttpClient();
    client.createContext();
    dbWrapper.updateVirtualPropertyOfFacility("F10", "true");

    submitRequisition("commTrack1", "HIV");
    String id = String.valueOf(dbWrapper.getMaxRnrID());
    dbWrapper.updateRequisitionStatus("AUTHORIZED", "commTrack", "HIV");
    dbWrapper.updateActiveStatusOfProgramProduct("P10", "HIV", "False");
    Report reportFromJson = JsonUtility.readObjectFromFile(FULL_JSON_APPROVE_TXT_FILE_NAME, Report.class);

    reportFromJson.getProducts().get(0).setProductCode("P10");
    reportFromJson.getProducts().get(0).setQuantityApproved(65);
    reportFromJson.setApproverName("some random name");

    ResponseEntity responseEntity = client.SendJSON(getJsonStringFor(reportFromJson),
        "http://localhost:9091/rest-api/requisitions/" + id + "/approve",
        "PUT",
        "commTrack",
        "Admin123");

    String response = responseEntity.getResponse();
    assertEquals(200, responseEntity.getStatus());
    assertTrue(response.contains("{\"success\":"));
    assertEquals("APPROVED", dbWrapper.getAttributeFromTable("requisitions", "status", "id", id));
    assertEquals("some random name", dbWrapper.getAttributeFromTable("requisition_status_changes", "name", "rnrId", id));
    dbWrapper.updateActiveStatusOfProgramProduct("P10", "HIV", "True");
  }

  @Test(groups = {"webservice"})
  public void testApproveRequisitionProgramInactive() throws Exception {
    HttpClient client = new HttpClient();
    client.createContext();
    dbWrapper.updateVirtualPropertyOfFacility("F10", "true");

    submitRequisition("commTrack1", "HIV");
    String id = String.valueOf(dbWrapper.getMaxRnrID());

    dbWrapper.updateRequisitionStatus("AUTHORIZED", "commTrack", "HIV");
    dbWrapper.updateActiveStatusOfProgram("HIV", false);
    Report reportFromJson = JsonUtility.readObjectFromFile(FULL_JSON_APPROVE_TXT_FILE_NAME, Report.class);

    reportFromJson.getProducts().get(0).setProductCode("P10");
    reportFromJson.getProducts().get(0).setQuantityApproved(65);
    reportFromJson.setApproverName("some random name");

    ResponseEntity responseEntity = client.SendJSON(getJsonStringFor(reportFromJson),
        "http://localhost:9091/rest-api/requisitions/" + id + "/approve",
        "PUT",
        "commTrack",
        "Admin123");

    String response = responseEntity.getResponse();
    assertEquals(200, responseEntity.getStatus());
    assertTrue(response.contains("{\"success\":"));
    assertEquals("APPROVED", dbWrapper.getAttributeFromTable("requisitions", "status", "id", id));
    assertEquals("some random name", dbWrapper.getAttributeFromTable("requisition_status_changes", "name", "rnrId", id));
    dbWrapper.updateActiveStatusOfProgram("HIV", true);
  }

  @Test(groups = {"webservice"})
  public void testApproveRequisitionProductInactive() throws Exception {
    HttpClient client = new HttpClient();
    client.createContext();
    dbWrapper.updateVirtualPropertyOfFacility("F10", "true");

    submitRequisition("commTrack1", "HIV");
    String id = String.valueOf(dbWrapper.getMaxRnrID());
    dbWrapper.updateRequisitionStatus("AUTHORIZED", "commTrack", "HIV");
    dbWrapper.updateActiveStatusOfProduct("P10", "False");
    Report reportFromJson = JsonUtility.readObjectFromFile(FULL_JSON_APPROVE_TXT_FILE_NAME, Report.class);

    reportFromJson.getProducts().get(0).setProductCode("P10");
    reportFromJson.getProducts().get(0).setQuantityApproved(65);
    reportFromJson.setApproverName("some random name");

    ResponseEntity responseEntity = client.SendJSON(getJsonStringFor(reportFromJson),
        "http://localhost:9091/rest-api/requisitions/" + id + "/approve",
        "PUT",
        "commTrack",
        "Admin123");

    String response = responseEntity.getResponse();
    assertEquals(200, responseEntity.getStatus());
    assertTrue(response.contains("{\"success\":"));
    assertEquals("APPROVED", dbWrapper.getAttributeFromTable("requisitions", "status", "id", id));
    assertEquals("some random name", dbWrapper.getAttributeFromTable("requisition_status_changes", "name", "rnrId", id));
    dbWrapper.updateActiveStatusOfProduct("P10", "True");
  }

  @Test(groups = {"webservice"})
  public void testApproveRequisitionNotVirtualFacility() throws Exception {
    HttpClient client = new HttpClient();
    client.createContext();

    submitRequisition("commTrack1", "HIV");
    Long id = (long) dbWrapper.getMaxRnrID();
    dbWrapper.updateRequisitionStatus("AUTHORIZED", "commTrack", "HIV");
    Report reportFromJson = JsonUtility.readObjectFromFile(FULL_JSON_APPROVE_TXT_FILE_NAME, Report.class);

    reportFromJson.getProducts().get(0).setProductCode("P10");
    reportFromJson.getProducts().get(0).setQuantityApproved(65);
    reportFromJson.setApproverName("some random name");

    ResponseEntity responseEntity = client.SendJSON(getJsonStringFor(reportFromJson),
        "http://localhost:9091/rest-api/requisitions/" + id + "/approve",
        "PUT",
        "commTrack",
        "Admin123");

    String response = responseEntity.getResponse();
    assertEquals(400, responseEntity.getStatus());
    assertEquals("{\"error\":\"Approval not allowed\"}", response);
  }

  @Test(groups = {"webservice"})
  public void testApproveRequisitionInvalidRequisitionId() throws Exception {
    HttpClient client = new HttpClient();
    client.createContext();
    dbWrapper.updateVirtualPropertyOfFacility("F10", "true");

    submitRequisition("commTrack1", "HIV");
    Long requisitionId = 999999L;
    dbWrapper.updateRequisitionStatus("AUTHORIZED", "commTrack", "HIV");

    Report reportFromJson = readObjectFromFile(FULL_JSON_APPROVE_TXT_FILE_NAME, Report.class);
    reportFromJson.getProducts().get(0).setProductCode("P10");
    reportFromJson.getProducts().get(0).setQuantityApproved(65);
    reportFromJson.setApproverName("some random name");

    ResponseEntity responseEntity = client.SendJSON(getJsonStringFor(reportFromJson),
        "http://localhost:9091/rest-api/requisitions/" + requisitionId + "/approve", "PUT",
        "commTrack", "Admin123");
    String response = responseEntity.getResponse();
    client.SendJSON("", "http://localhost:9091/", "GET", "", "");
    assertEquals(400, responseEntity.getStatus());
    assertEquals("{\"error\":\"Requisition Not Found\"}", response);
  }

  @Test(groups = {"webservice"})
  public void testApproveRequisitionBlankQuantityApproved() throws Exception {
    HttpClient client = new HttpClient();
    client.createContext();
    dbWrapper.updateVirtualPropertyOfFacility("F10", "true");

    submitRequisition("commTrack1", "HIV");
    Long id = (long) dbWrapper.getMaxRnrID();
    dbWrapper.updateRequisitionStatus("AUTHORIZED", "commTrack", "HIV");

    Report reportFromJson = readObjectFromFile(FULL_JSON_APPROVE_TXT_FILE_NAME, Report.class);

    reportFromJson.getProducts().get(0).setProductCode("P10");
    reportFromJson.setApproverName("some random name");

    ResponseEntity responseEntity =
        client.SendJSON(
            getJsonStringFor(reportFromJson),
            "http://localhost:9091/rest-api/requisitions/" + id + "/approve",
            "PUT",
            "commTrack",
            "Admin123");

    String response = responseEntity.getResponse();
    client.SendJSON("", "http://localhost:9091/", "GET", "", "");
    assertEquals(400, responseEntity.getStatus());
    assertEquals("{\"error\":\"Missing mandatory fields\"}", response);
  }

  @Test(groups = {"webservice"})
  public void testApproveRequisitionBlankQuantityApproverName() throws Exception {
    HttpClient client = new HttpClient();
    client.createContext();
    dbWrapper.updateVirtualPropertyOfFacility("F10", "true");

    submitRequisition("commTrack1", "HIV");
    Long id = (long) dbWrapper.getMaxRnrID();
    dbWrapper.updateRequisitionStatus("AUTHORIZED", "commTrack", "HIV");

    Report reportFromJson = readObjectFromFile(FULL_JSON_APPROVE_TXT_FILE_NAME, Report.class);

    reportFromJson.getProducts().get(0).setProductCode("P10");

    ResponseEntity responseEntity =
      client.SendJSON(
        getJsonStringFor(reportFromJson),
        "http://localhost:9091/rest-api/requisitions/" + id + "/approve",
        "PUT",
        "commTrack",
        "Admin123");

    String response = responseEntity.getResponse();
    client.SendJSON("", "http://localhost:9091/", "GET", "", "");
    assertEquals(400, responseEntity.getStatus());
    assertEquals("{\"error\":\"Missing mandatory fields\"}", response);
  }

  @Test(groups = {"webservice"})
  public void testApproveRequisitionOnIncorrectRequisitionStatus() throws Exception {
    HttpClient client = new HttpClient();
    dbWrapper.updateVirtualPropertyOfFacility("F10", "true");
    client.createContext();

    submitRequisition("commTrack1", "HIV");
    Long id = (long) dbWrapper.getMaxRnrID();

    Report reportFromJson = JsonUtility.readObjectFromFile(FULL_JSON_APPROVE_TXT_FILE_NAME, Report.class);

    reportFromJson.getProducts().get(0).setProductCode("P10");
    reportFromJson.getProducts().get(0).setQuantityApproved(65);
    reportFromJson.setApproverName("some random name");

    ResponseEntity responseEntity =
        client.SendJSON(getJsonStringFor(reportFromJson),
            "http://localhost:9091/rest-api/requisitions/" + id + "/approve",
            "PUT",
            "commTrack",
            "Admin123");

    String response = responseEntity.getResponse();

    assertEquals(400, responseEntity.getStatus());
    assertTrue(response.contains("{\"error\":\"Approval not allowed\"}"));
  }

  @Test(groups = {"webservice"})
  public void testApproveRequisitionProductCountMismatch() throws Exception {
    HttpClient client = new HttpClient();
    dbWrapper.updateVirtualPropertyOfFacility("F10", "true");
    client.createContext();

    submitRequisition("commTrack1", "HIV");
    String id = String.valueOf(dbWrapper.getMaxRnrID());
    dbWrapper.updateRequisitionStatus("AUTHORIZED", "commTrack", "HIV");

    Report reportFromJson = JsonUtility.readObjectFromFile(FULL_JSON_MULTIPLE_PRODUCTS_APPROVE_TXT_FILE_NAME, Report.class);

    reportFromJson.getProducts().get(0).setProductCode("P10");
    reportFromJson.getProducts().get(0).setQuantityApproved(65);
    reportFromJson.getProducts().get(1).setProductCode("P11");
    reportFromJson.getProducts().get(1).setQuantityApproved(65);
    reportFromJson.setApproverName("some random name");

    ResponseEntity responseEntity =
        client.SendJSON(getJsonStringFor(reportFromJson),
            "http://localhost:9091/rest-api/requisitions/" + id + "/approve",
            "PUT",
            "commTrack",
            "Admin123");

    String response = responseEntity.getResponse();

    assertEquals(400, responseEntity.getStatus());
    assertTrue(response.contains("{\"error\":\"Products do not match with requisition\"}"));
    assertEquals("AUTHORIZED", dbWrapper.getAttributeFromTable("requisitions", "status", "id", id));
  }

  @Test(groups = {"webservice"})
  public void testApproveRequisitionValidRnRWithRemarksAndRnaSubmissionThroughAPI() throws Exception {
    HttpClient client = new HttpClient();
    client.createContext();
    createVirtualFacilityThroughApi("V100","F10");
    dbWrapper.insertProcessingPeriod("Current", "current", "2013-05-01","2016-01-01",1,"M");
    dbWrapper.insertRoleAssignmentForSupervisoryNodeForProgramId1("700", "store in-charge", "N1");
    submitRnRThroughApi("V100","HIV","P10",1,10,1,4,0,2);
    String id = String.valueOf(dbWrapper.getMaxRnrID());

    Report reportFromJson = JsonUtility.readObjectFromFile(FULL_JSON_APPROVE_TXT_FILE_NAME, Report.class);
    reportFromJson.getProducts().get(0).setProductCode("P10");
    reportFromJson.getProducts().get(0).setQuantityApproved(65);
    reportFromJson.setApproverName("some random name");
    reportFromJson.getProducts().get(0).setRemarks("123");

    ResponseEntity responseEntity =
      client.SendJSON(getJsonStringFor(reportFromJson),
        "http://localhost:9091/rest-api/requisitions/" + id + "/approve",
        "PUT",
        "commTrack",
        "Admin123");

    String response = responseEntity.getResponse();

    assertEquals(200, responseEntity.getStatus());
    assertTrue(response.contains("success\":\"R&R approved successfully!"));
    assertEquals("APPROVED", dbWrapper.getAttributeFromTable("requisitions", "status", "id", id));
    assertEquals("123", dbWrapper.getRequisitionLineItemFieldValue(Long.parseLong(id), "remarks", "P10"));
  }

  @Test(groups = {"webservice"})
  public void testShowErrorMessageForUnrecognizedFieldInAPI() throws Exception {
    HttpClient client = new HttpClient();
    client.createContext();
    dbWrapper.updateVirtualPropertyOfFacility("F10", "true");
    submitRequisition("commTrack1", "HIV");
    Long id = (long) dbWrapper.getMaxRnrID();
    dbWrapper.updateRequisitionStatus("AUTHORIZED", "commTrack", "HIV");

    String wrongJson = "{\"approverName\": \"Some Random Name\"," +
      "    \"products\": [" +
      "        {" +
      "            \"productCode\": \"P10\"," +
      "            \"quantityApproved\": \"10\"," +
      "            \"beginningbbbb\": \"10\"," +

      "        }" +
      "    ]" +
      "}";

    ResponseEntity responseEntity =
      client.SendJSON(wrongJson,
        "http://localhost:9091/rest-api/requisitions/"+id+"/approve.json",
        "PUT",
        "commTrack",
        "Admin123");

    assertEquals(400, responseEntity.getStatus());
    assertTrue(responseEntity.getResponse().contains("{\"error\":\"Could not read JSON: Unrecognized field"));

  }
}

