/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openlmis.core.domain.Program;
import org.openlmis.core.domain.ProgramProduct;
import org.openlmis.core.service.ProgramProductService;
import org.openlmis.web.response.OpenLmisResponse;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.openlmis.web.controller.FacilityProgramProductController.PROGRAM_PRODUCT_LIST;

@RunWith(MockitoJUnitRunner.class)
public class ProgramProductControllerTest {

  @Mock
  ProgramProductService service;

  @InjectMocks
  ProgramProductController controller;

  @Test
  public void shouldGetProgramProductsByProgram() throws Exception {
    List<ProgramProduct> expectedProgramProductList = new ArrayList<>();
    Program program = new Program(1l);
    when(service.getByProgram(program)).thenReturn(expectedProgramProductList);

    ResponseEntity<OpenLmisResponse> responseEntity = controller.getProgramProductsByProgram(1l);

    assertThat((List<ProgramProduct>) responseEntity.getBody().getData().get(PROGRAM_PRODUCT_LIST), is(expectedProgramProductList));
    verify(service).getByProgram(program);
  }

  @Test
  public void shouldGetActiveProgramProductsForAProgram() throws Exception {
    List<ProgramProduct> expectedProgramProductList = new ArrayList<>();
    when(service.getActiveByProgram(1L)).thenReturn(expectedProgramProductList);

    ResponseEntity<OpenLmisResponse> responseEntity = controller.getActiveProgramProductsByProgram(1l);

    assertThat((List<ProgramProduct>) responseEntity.getBody().getData().get(PROGRAM_PRODUCT_LIST), is(expectedProgramProductList));
    verify(service).getActiveByProgram(1L);
  }
}
