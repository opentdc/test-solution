/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Arbalo AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package test.org.opentdc.addressbooks;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.OrgModel;
import org.opentdc.addressbooks.OrgType;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

public class OrgTest extends AbstractTestClient {
		private static AddressbookModel adb = null;
		private WebClient wc = null;

		@Before
		public void initializeTests() {
			wc = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
			adb = AddressbookTest.createAddressbook(wc, this.getClass().getName(), Status.OK);
		}
		
		@After
		public void cleanupTest() {
			AddressbookTest.delete(wc, adb.getId(), Status.NO_CONTENT);
			System.out.println("deleted 1 addressbook");
			wc.close();
		}

		/********************************** org attribute tests *********************************/	
		@Test
		public void testEmptyConstructor() {
			OrgModel _model = new OrgModel();
			assertNull("id should not be set by empty constructor", _model.getId());
			assertNull("name should not be set by empty constructor", _model.getName());
			assertNull("description should not be set by empty constructor", _model.getDescription());
			assertNull("costCenter should not be set by empty constructor", _model.getCostCenter());
			assertNull("stockExchange should not be set by empty constructor", _model.getStockExchange());
			assertNull("tickerSymbol should not be set by empty constructor", _model.getTickerSymbol());
			assertNull("orgType should not be set by empty constructor", _model.getOrgType());
			assertNull("logoUrl should not be set by empty constructor", _model.getLogoUrl());
		}
			
		@Test
		public void testId() {
			OrgModel _model = new OrgModel();
			assertNull("id should not be set by constructor", _model.getId());
			_model.setId("testId");
			assertEquals("id should have changed:", "testId", _model.getId());
		}

		@Test
		public void testName() {
			OrgModel _model = new OrgModel();
			assertNull("Name should not be set by constructor", _model.getName());
			_model.setName("testName");
			assertEquals("Name should have changed:", "testName", _model.getName());
		}

		@Test
		public void testDescription() {
			OrgModel _model = new OrgModel();
			assertNull("Description should not be set by constructor", _model.getDescription());
			_model.setDescription("testDescription");
			assertEquals("Description should have changed:", "testDescription", _model.getDescription());
		}

		@Test
		public void testCostCenter() {
			OrgModel _model = new OrgModel();
			assertNull("CostCenter should not be set by constructor", _model.getCostCenter());
			_model.setCostCenter("testCostCenter");
			assertEquals("id should have changed:", "testCostCenter", _model.getCostCenter());
		}

		@Test
		public void testStockExchange() {
			OrgModel _model = new OrgModel();
			assertNull("StockExchange should not be set by constructor", _model.getStockExchange());
			_model.setStockExchange("testStockExchange");
			assertEquals("StockExchange should have changed:", "testStockExchange", _model.getStockExchange());
		}

		@Test
		public void testOrgTickerSymbol() {
			OrgModel _model = new OrgModel();
			assertNull("TickerSymbol should not be set by constructor", _model.getTickerSymbol());
			_model.setTickerSymbol("testOrgTickerSymbol");
			assertEquals("TickerSymbol should have changed:", "testOrgTickerSymbol", _model.getTickerSymbol());
		}

		@Test
		public void testOrgOrgType() {
			OrgModel _model = new OrgModel();
			assertNull("orgType should not be set by constructor", _model.getOrgType());
			_model.setOrgType(OrgType.ASSOC);
			assertEquals("orgType should have changed:", OrgType.ASSOC, _model.getOrgType());
		}
		
		@Test
		public void testLogoUrl() {
			OrgModel _model = new OrgModel();
			assertNull("logoUrl should not be set by constructor", _model.getLogoUrl());
			_model.setLogoUrl("testLogoUrl");
			assertEquals("logoUr should have changed:", "testLogoUrl", _model.getLogoUrl());
		}

		@Test
		public void testCreatedBy() {
			OrgModel _model = new OrgModel();
			assertNull("createdBy should not be set by empty constructor", _model.getCreatedBy());
			_model.setCreatedBy("testCreatedBy");
			assertEquals("createdBy should have changed", "testCreatedBy", _model.getCreatedBy());	
		}

		@Test
		public void testCreatedAt() {
			OrgModel _model = new OrgModel();
			assertNull("createdAt should not be set by empty constructor", _model.getCreatedAt());
			_model.setCreatedAt(new Date());
			assertNotNull("createdAt should have changed", _model.getCreatedAt());
		}

		@Test
		public void testModifiedBy() {
			OrgModel _model = new OrgModel();
			assertNull("modifiedBy should not be set by empty constructor", _model.getModifiedBy());
			_model.setModifiedBy("testModifiedBy");
			assertEquals("modifiedBy should have changed", "testModifiedBy", _model.getModifiedBy());	
		}

		@Test
		public void testOrgModifiedAt() {
			OrgModel _model = new OrgModel();
			assertNull("modifiedAt should not be set by empty constructor", _model.getModifiedAt());
			_model.setModifiedAt(new Date());
			assertNotNull("modifiedAt should have changed", _model.getModifiedAt());
		}

		/********************************* REST service tests *********************************/	
				
		@Test
		public void testCreateReadDeleteWithEmptyConstructor() {
			OrgModel _model1 = new OrgModel();
			assertNull("id should not be set by empty constructor", _model1.getId());
			assertNull("name should not be set by empty constructor", _model1.getName());
			assertNull("description should not be set by empty constructor", _model1.getDescription());
			assertNull("costCenter should not be set by empty constructor", _model1.getCostCenter());
			assertNull("stockExchange should not be set by empty constructor", _model1.getStockExchange());
			assertNull("tickerSymbol should not be set by empty constructor", _model1.getTickerSymbol());
			assertNull("orgType should not be set by empty constructor", _model1.getOrgType());
			assertNull("logoUrl should not be set by empty constructor", _model1.getLogoUrl());
			
			Response _response = wc.replacePath("/").post(_model1);
			assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
			_model1.setName("testOrgCreateReadDeleteWithEmptyConstructor");

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(_model1);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _model2 = _response.readEntity(OrgModel.class);
			
			assertNull("create() should not change the id of the local object", _model1.getId());
			assertEquals("name should not be set by empty constructor", "testOrgCreateReadDeleteWithEmptyConstructor", _model1.getName());
			assertNull("description should not be set by empty constructor", _model1.getDescription());
			assertNull("costCenter should not be set by empty constructor", _model1.getCostCenter());
			assertNull("stockExchange should not be set by empty constructor", _model1.getStockExchange());
			assertNull("tickerSymbol should not be set by empty constructor", _model1.getTickerSymbol());
			assertNull("orgType should not be set by empty constructor", _model1.getOrgType());
			assertNull("logoUrl should not be set by empty constructor", _model1.getLogoUrl());
			
			assertNotNull("create() should set a valid id", _model2.getId());
			assertEquals("create() should not change the name", "testOrgCreateReadDeleteWithEmptyConstructor", _model2.getName());
			assertNull("create() should not change the description", _model2.getDescription());
			assertNull("create() should not change the costCenter", _model2.getCostCenter());
			assertNull("create() should not change the stockExchange", _model2.getStockExchange());
			assertNull("create() should not change the tickerSymbol", _model2.getTickerSymbol());
			assertEquals("create() should set a default orgType", OrgType.getDefaultOrgType(), _model2.getOrgType());
			assertNull("create() should not change the logoUrl", _model2.getLogoUrl());

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model2.getId()).get();
			assertEquals("read(" + _model2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _model3 = _response.readEntity(OrgModel.class);
			
			assertEquals("ids should be equal", _model2.getId(), _model3.getId());
			assertEquals("names should be equal", _model2.getName(), _model3.getName());
			assertEquals("descriptions should be equal", _model2.getDescription(), _model3.getDescription());
			assertEquals("costCenters should be equal", _model2.getCostCenter(), _model3.getCostCenter());
			assertEquals("stockExchanges should be equal", _model2.getStockExchange(), _model3.getStockExchange());
			assertEquals("tickerSymbols should be equal", _model2.getTickerSymbol(), _model3.getTickerSymbol());
			assertEquals("orgTypes should be equal", _model2.getOrgType(), _model3.getOrgType());
			assertEquals("logoUrls should be equal", _model2.getLogoUrl(), _model3.getLogoUrl());
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model3.getId()).delete();
			assertEquals("delete(" + _model3.getId() + ") should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testCreateReadDelete() {
			OrgModel _model1 = create("testCreateReadDelete", OrgType.ASSOC, 1);
			assertNull("id should not be set by constructor", _model1.getId());
			
			assertEquals("constructor should set the name correctly", "testCreateReadDelete1", _model1.getName());
			assertEquals("constructor should set the description correctly", "MY_DESC1", _model1.getDescription());
			assertEquals("constructor should set the costCenter correctly", "MY_COST_CENTER1", _model1.getCostCenter());
			assertEquals("constructor should set the stockExchange correctly", "MY_STOCK_EXCHANGE1", _model1.getStockExchange());
			assertEquals("constructor should set the tickerSymbol correctly", "MY_TICKER_SYMBOL1", _model1.getTickerSymbol());
			assertEquals("constructor should set the orgType correctly", OrgType.ASSOC, _model1.getOrgType());
			assertEquals("constructor should set the logoUrl correctly", "MY_LOGO_URL1", _model1.getLogoUrl());
						
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(_model1);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _model2 = _response.readEntity(OrgModel.class);
			
			assertNull("id should still be null", _model1.getId());
			assertEquals("create() should not change the name", "testCreateReadDelete1", _model1.getName());
			assertEquals("create() should not change the description", "MY_DESC1", _model1.getDescription());
			assertEquals("create() should not change the costCenter", "MY_COST_CENTER1", _model1.getCostCenter());
			assertEquals("create() should not change the stockExchange", "MY_STOCK_EXCHANGE1", _model1.getStockExchange());
			assertEquals("create() should not change the tickerSymbol", "MY_TICKER_SYMBOL1", _model1.getTickerSymbol());
			assertEquals("create() should not change the orgType", OrgType.ASSOC, _model1.getOrgType());
			assertEquals("create() should not change the logoUrl", "MY_LOGO_URL1", _model1.getLogoUrl());
						
			assertNotNull("id of returned object should be set", _model2.getId());
			assertEquals("create() should not change the name", "testCreateReadDelete1", _model2.getName());
			assertEquals("create() should not change the description", "MY_DESC1", _model2.getDescription());
			assertEquals("create() should not change the costCenter", "MY_COST_CENTER1", _model2.getCostCenter());
			assertEquals("create() should not change the stockExchange", "MY_STOCK_EXCHANGE1", _model2.getStockExchange());
			assertEquals("create() should not change the tickerSymbol", "MY_TICKER_SYMBOL1", _model2.getTickerSymbol());
			assertEquals("create() should not change the orgType", OrgType.ASSOC, _model2.getOrgType());
			assertEquals("create() should not change the logoUrl", "MY_LOGO_URL1", _model2.getLogoUrl());
						
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model2.getId()).get();
			assertEquals("read(" + _model2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _model3 = _response.readEntity(OrgModel.class);
			
			assertEquals("ids should be the same", _model2.getId(), _model3.getId());
			assertEquals("names should be the same", _model2.getName(), _model3.getName());
			assertEquals("descriptions should be the same", _model2.getDescription(), _model3.getDescription());
			assertEquals("costCenters should be the same", _model2.getCostCenter(), _model3.getCostCenter());
			assertEquals("stockExchanges should be the same", _model2.getStockExchange(), _model3.getStockExchange());
			assertEquals("tickerSymbols should be the same", _model2.getTickerSymbol(), _model3.getTickerSymbol());
			assertEquals("orgTypes should be the same", _model2.getOrgType(), _model3.getOrgType());
			assertEquals("logoUrls should be the same", _model2.getLogoUrl(), _model3.getLogoUrl());

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model3.getId()).delete();
			assertEquals("delete(" + _model3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testClientSideId() {
			OrgModel _model1 = create("testClientSideId", OrgType.ASSOC, 1);
			_model1.setId("LOCAL_ID");
			assertEquals("id should have changed", "LOCAL_ID", _model1.getId());
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(_model1);
			assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testDuplicateId() {
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(create("testDuplicateId", OrgType.ASSOC, 1));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _model1 = _response.readEntity(OrgModel.class);

			OrgModel _model2 = create("testOrgWithDuplicateId", OrgType.ASSOC, 2);
			_model2.setId(_model1.getId());		// wrongly create a 2nd OrgModel object with the same ID
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(_model2);
			assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testList() {
			ArrayList<OrgModel> _localList = new ArrayList<OrgModel>();		
			Response _response = null;
			wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL);
			for (int i = 0; i < LIMIT; i++) {
				_response = wc.post(create("testList", OrgType.ASSOC, i));
				assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_localList.add(_response.readEntity(OrgModel.class));
			}
			assertEquals("size of lists should be the same", LIMIT, _localList.size());
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).get();
			List<OrgModel> _remoteList = new ArrayList<OrgModel>(wc.getCollection(OrgModel.class));
			assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			assertEquals("size of lists should be the same", LIMIT, _remoteList.size());
			// implicit proof: _localList.size() = _remoteList.size = LIMIT

			ArrayList<String> _remoteListIds = new ArrayList<String>();
			for (OrgModel _model : _remoteList) {
				_remoteListIds.add(_model.getId());
			}
			
			for (OrgModel _model : _localList) {
				assertTrue("project <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
			}
			
			for (OrgModel _model : _localList) {
				_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model.getId()).get();
				assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_response.readEntity(OrgModel.class);
			}
			
			for (OrgModel _model : _localList) {
				_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model.getId()).delete();
				assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
			}
		}

		@Test
		public void testCreate() {				
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(create("testCreate", OrgType.ASSOC, 1));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _model1 = _response.readEntity(OrgModel.class);

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(create("testCreate", OrgType.CLUB, 2));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _model2 = _response.readEntity(OrgModel.class);		
			
			assertNotNull("ID should be set", _model1.getId());
			assertEquals("create() should not change the name", "testCreate1", _model1.getName());
			assertEquals("create() should not change the description", "MY_DESC1", _model1.getDescription());
			assertEquals("create() should not change the costCenter", "MY_COST_CENTER1", _model1.getCostCenter());
			assertEquals("create() should not change the stockExchange", "MY_STOCK_EXCHANGE1", _model1.getStockExchange());
			assertEquals("create() should not change the tickerSymbol", "MY_TICKER_SYMBOL1", _model1.getTickerSymbol());
			assertEquals("create() should not change the orgType", OrgType.ASSOC, _model1.getOrgType());
			assertEquals("create() should not change the logoUrl", "MY_LOGO_URL1", _model1.getLogoUrl());
			
			assertNotNull("ID should be set", _model2.getId());
			assertEquals("create() should not change the name", "testCreate2", _model2.getName());
			assertEquals("create() should not change the description", "MY_DESC2", _model2.getDescription());
			assertEquals("create() should not change the costCenter", "MY_COST_CENTER2", _model2.getCostCenter());
			assertEquals("create() should not change the stockExchange", "MY_STOCK_EXCHANGE2", _model2.getStockExchange());
			assertEquals("create() should not change the tickerSymbol", "MY_TICKER_SYMBOL2", _model2.getTickerSymbol());
			assertEquals("create() should not change the orgType", OrgType.CLUB, _model2.getOrgType());
			assertEquals("create() should not change the logoUrl", "MY_LOGO_URL2", _model2.getLogoUrl());

			assertThat(_model2.getId(), not(equalTo(_model1.getId())));

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model1.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model2.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testCreateDouble() {		
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(create("testCreateDouble", OrgType.ASSOC, 1));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _model = _response.readEntity(OrgModel.class);
			assertNotNull("ID should be set:", _model.getId());		
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(_model);
			assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testRead() {
			ArrayList<OrgModel> _localList = new ArrayList<OrgModel>();
			Response _response = null;
			wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL);
			for (int i = 0; i < LIMIT; i++) {
				_response = wc.post(create("testRead", OrgType.ASSOC, i));
				assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_localList.add(_response.readEntity(OrgModel.class));
			}
		
			for (OrgModel _model : _localList) {
				_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model.getId()).get();
				assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_response.readEntity(OrgModel.class);
			}

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).get();
			List<OrgModel> _remoteList = new ArrayList<OrgModel>(wc.getCollection(OrgModel.class));
			assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

			OrgModel _model1 = null;
			for (OrgModel _model : _remoteList) {
				_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model.getId()).get();
				assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_model1 = _response.readEntity(OrgModel.class);
				assertEquals("ID should be unchanged when reading a project", _model.getId(), _model1.getId());						
			}

			for (OrgModel _model : _localList) {
				_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model.getId()).delete();
				assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
			}
		}
			
		@Test
		public void testMultiRead() {
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(create("testOrgMultiRead", OrgType.ASSOC, 1));
			OrgModel _model1 = _response.readEntity(OrgModel.class);

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model1.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _model2 = _response.readEntity(OrgModel.class);
			assertEquals("ID should be unchanged after read:", _model1.getId(), _model2.getId());		

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model1.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _model3 = _response.readEntity(OrgModel.class);
			
			assertEquals("ID should be the same:", _model2.getId(), _model3.getId());
			assertEquals("names should be the same", _model2.getName(), _model3.getName());
			assertEquals("descriptions should be the same", _model2.getDescription(), _model3.getDescription());
			assertEquals("costCenters should be the same", _model2.getCostCenter(), _model3.getCostCenter());
			assertEquals("stockExchanges should be the same", _model2.getStockExchange(), _model3.getStockExchange());
			assertEquals("tickerSymbols should be the same", _model2.getTickerSymbol(), _model3.getTickerSymbol());
			assertEquals("orgTypes should be the same", _model2.getOrgType(), _model3.getOrgType());
			assertEquals("logoUrls should be the same", _model2.getLogoUrl(), _model3.getLogoUrl());
			
			
			assertEquals("ID should be the same:", _model2.getId(), _model1.getId());
			assertEquals("names should be the same", _model2.getName(), _model1.getName());
			assertEquals("descriptions should be the same", _model2.getDescription(), _model1.getDescription());
			assertEquals("costCenters should be the same", _model2.getCostCenter(), _model1.getCostCenter());
			assertEquals("stockExchanges should be the same", _model2.getStockExchange(), _model1.getStockExchange());
			assertEquals("tickerSymbols should be the same", _model2.getTickerSymbol(), _model1.getTickerSymbol());
			assertEquals("orgTypes should be the same", _model2.getOrgType(), _model1.getOrgType());
			assertEquals("logoUrls should be the same", _model2.getLogoUrl(), _model1.getLogoUrl());
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model1.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testUpdate() {			
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(create("testUpdate", OrgType.ASSOC, 1));
			OrgModel _model1 = _response.readEntity(OrgModel.class);
			
			_model1.setName("testUpdate2");
			_model1.setDescription("MY_DESC2");
			_model1.setCostCenter("MY_COST_CENTER2");
			_model1.setStockExchange("MY_STOCK_EXCHANGE2");
			_model1.setTickerSymbol("MY_TICKER_SYMBOL2");
			_model1.setOrgType(OrgType.CLUB);
			_model1.setLogoUrl("MY_LOGO_URL2");
			wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model1.getId()).put(_model1);
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _model2 = _response.readEntity(OrgModel.class);

			assertNotNull("ID should be set", _model2.getId());
			assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());	
			assertEquals("name should be set correctly", "testUpdate2", _model2.getName());
			assertEquals("description should be set correctly", "MY_DESC2", _model2.getDescription());
			assertEquals("costCenter should be set correctly", "MY_COST_CENTER2", _model2.getCostCenter());
			assertEquals("stockExchange should be set correctly", "MY_STOCK_EXCHANGE2", _model2.getStockExchange());
			assertEquals("tickerSymbol should be set correctly", "MY_TICKER_SYMBOL2", _model2.getTickerSymbol());
			assertEquals("orgType should be set correctly", OrgType.CLUB, _model2.getOrgType());
			assertEquals("logoUrl should be set correctly", "MY_LOGO_URL2", _model2.getLogoUrl());

			_model1.setName("testUpdate3");
			_model1.setDescription("MY_DESC3");
			_model1.setCostCenter("MY_COST_CENTER3");
			_model1.setStockExchange("MY_STOCK_EXCHANGE3");
			_model1.setTickerSymbol("MY_TICKER_SYMBOL3");
			_model1.setOrgType(OrgType.COMP);
			_model1.setLogoUrl("MY_LOGO_URL3");
			wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model1.getId()).put(_model1);
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _model3 = _response.readEntity(OrgModel.class);

			assertNotNull("ID should be set", _model3.getId());
			assertEquals("ID should be unchanged", _model1.getId(), _model3.getId());
			assertEquals("name should be set correctly", "testUpdate3", _model3.getName());
			assertEquals("description should be set correctly", "MY_DESC3", _model3.getDescription());
			assertEquals("costCenter should be set correctly", "MY_COST_CENTER3", _model3.getCostCenter());
			assertEquals("stockExchange should be set correctly", "MY_STOCK_EXCHANGE3", _model3.getStockExchange());
			assertEquals("tickerSymbol should be set correctly", "MY_TICKER_SYMBOL3", _model3.getTickerSymbol());
			assertEquals("orgType should be set correctly", OrgType.COMP, _model3.getOrgType());
			assertEquals("logoUrl should be set correctly", "MY_LOGO_URL3", _model3.getLogoUrl());
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model1.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}

		@Test
		public void testDelete(
		) {
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(create("testDelete", OrgType.ASSOC, 1));
			OrgModel _model1 = _response.readEntity(OrgModel.class);
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model1.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _model2 = _response.readEntity(OrgModel.class);
			assertEquals("ID should be unchanged when reading a project (remote):", _model1.getId(), _model2.getId());						

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model1.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_model2 = _response.readEntity(OrgModel.class);
			assertEquals("ID should be unchanged when reading a project (remote):", _model1.getId(), _model2.getId());						
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model1.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model1.getId()).get();
			assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model1.getId()).get();
			assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testDoubleDelete() {
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(create("testDoubleDelete", OrgType.ASSOC, 1));
			OrgModel _model1 = _response.readEntity(OrgModel.class);

			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model1.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model1.getId()).delete();		
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model1.getId()).get();
			assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model1.getId()).delete();		
			assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model1.getId()).get();
			assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testModifications() {
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(create("testModifications", OrgType.ASSOC, 1));
			OrgModel _model1 = _response.readEntity(OrgModel.class);
			
			assertNotNull("create() should set createdAt", _model1.getCreatedAt());
			assertNotNull("create() should set createdBy", _model1.getCreatedBy());
			assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
			assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
			assertTrue("createdAt should be less than or equal to modifiedAt after create()", _model1.getCreatedAt().compareTo(_model1.getModifiedAt()) <= 0);
			assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());
			
			_model1.setName("MY_NAME2");
			wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model1.getId()).put(_model1);
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _model2 = _response.readEntity(OrgModel.class);

			assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
			assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
			assertThat(_model2.getModifiedAt(), not(equalTo(_model2.getCreatedAt())));
			// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?

			_model1.setModifiedBy("MYSELF");
			_model1.setModifiedAt(new Date(1000));
			wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model1.getId()).put(_model1);
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _model3 = _response.readEntity(OrgModel.class);
			
			assertThat(_model1.getModifiedBy(), not(equalTo(_model3.getModifiedBy())));
			assertThat(_model1.getModifiedAt(), not(equalTo(_model3.getModifiedAt())));
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_model1.getId()).delete();		
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}

		/********************************** helper methods *********************************/			
		private OrgModel create(String name, OrgType orgType, int suffix) {
			OrgModel _model = new OrgModel();
			_model.setName(name + suffix);
			_model.setDescription("MY_DESC" + suffix);
			_model.setCostCenter("MY_COST_CENTER" + suffix);
			_model.setStockExchange("MY_STOCK_EXCHANGE" + suffix);
			_model.setTickerSymbol("MY_TICKER_SYMBOL" + suffix);
			_model.setOrgType(orgType);
			_model.setLogoUrl("MY_LOGO_URL" + suffix);
			return _model;
		}
		
		public static OrgModel create(WebClient abWC, String aid, String name, OrgType orgType) {
			OrgModel _model = new OrgModel();
			_model.setName(name);
			_model.setOrgType(orgType);
			Response _response = abWC.replacePath("/").path(aid).path(ServiceUtil.ORG_PATH_EL).post(_model);
			return _response.readEntity(OrgModel.class);
		}
		
		/**
		 * Delete the OrgModel with id on the AddressbooksService by executing a HTTP DELETE method.
		 * @param webClient the WebClient for the AddressbooksService
		 * @param aid the id of the addressbook
		 * @param orgId the id of the OrgModel object to delete
		 * @param expectedStatus the expected HTTP status to test on
		 */
		public static void delete(
				WebClient webClient,
				String aid,
				String orgId,
				Status expectedStatus) {
			Response _response = webClient.replacePath("/").path(aid).path(ServiceUtil.ORG_PATH_EL).path(orgId).delete();		
			if (expectedStatus != null) {
				assertEquals("DELETE should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
			}
	}
		
		protected int calculateMembers() {
			return 1;
		}
}
