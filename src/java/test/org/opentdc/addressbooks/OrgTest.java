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

/**
 * Testing organizations.
 * @author Bruno Kaiser
 *
 */
public class OrgTest extends AbstractTestClient {
	private static final String CN = "OrgTest";
		private static AddressbookModel adb = null;
		private WebClient wc = null;

		@Before
		public void initializeTests() {
			wc = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
			adb = AddressbookTest.post(wc, new AddressbookModel(CN), Status.OK);
		}
		
		@After
		public void cleanupTest() {
			AddressbookTest.delete(wc, adb.getId(), Status.NO_CONTENT);
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
			
			post(_model1, Status.BAD_REQUEST);
			_model1.setName("testOrgCreateReadDeleteWithEmptyConstructor");
			OrgModel _model2 = post(_model1, Status.OK);
			
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

			OrgModel _model3 = get(_model2.getId(), Status.OK);			
			assertEquals("ids should be equal", _model2.getId(), _model3.getId());
			assertEquals("names should be equal", _model2.getName(), _model3.getName());
			assertEquals("descriptions should be equal", _model2.getDescription(), _model3.getDescription());
			assertEquals("costCenters should be equal", _model2.getCostCenter(), _model3.getCostCenter());
			assertEquals("stockExchanges should be equal", _model2.getStockExchange(), _model3.getStockExchange());
			assertEquals("tickerSymbols should be equal", _model2.getTickerSymbol(), _model3.getTickerSymbol());
			assertEquals("orgTypes should be equal", _model2.getOrgType(), _model3.getOrgType());
			assertEquals("logoUrls should be equal", _model2.getLogoUrl(), _model3.getLogoUrl());
			
			delete(_model3.getId(), Status.NO_CONTENT);
		}
		
		@Test
		public void testCreateReadDelete() {
			OrgModel _model1 = setDefaultValues(new OrgModel(), "testCreateReadDelete", OrgType.ASSOC, 1);			
			assertNull("id should not be set by constructor", _model1.getId());
			assertEquals("constructor should set the name correctly", "testCreateReadDelete1", _model1.getName());
			assertEquals("constructor should set the description correctly", "MY_DESC1", _model1.getDescription());
			assertEquals("constructor should set the costCenter correctly", "MY_COST_CENTER1", _model1.getCostCenter());
			assertEquals("constructor should set the stockExchange correctly", "MY_STOCK_EXCHANGE1", _model1.getStockExchange());
			assertEquals("constructor should set the tickerSymbol correctly", "MY_TICKER_SYMBOL1", _model1.getTickerSymbol());
			assertEquals("constructor should set the orgType correctly", OrgType.ASSOC, _model1.getOrgType());
			assertEquals("constructor should set the logoUrl correctly", "MY_LOGO_URL1", _model1.getLogoUrl());
					
			OrgModel _model2 = post(_model1, Status.OK);
			
			assertNull("id should still be null", _model1.getId());
			assertEquals("name should not change", "testCreateReadDelete1", _model1.getName());
			assertEquals("description should not change", "MY_DESC1", _model1.getDescription());
			assertEquals("costCenter should not change", "MY_COST_CENTER1", _model1.getCostCenter());
			assertEquals("stockExchange should not change", "MY_STOCK_EXCHANGE1", _model1.getStockExchange());
			assertEquals("tickerSymbol should not change", "MY_TICKER_SYMBOL1", _model1.getTickerSymbol());
			assertEquals("orgType should not change", OrgType.ASSOC, _model1.getOrgType());
			assertEquals("logoUrl should not change", "MY_LOGO_URL1", _model1.getLogoUrl());
						
			assertNotNull("id of returned object should be set", _model2.getId());
			assertEquals("name should not change", "testCreateReadDelete1", _model2.getName());
			assertEquals("description should not change", "MY_DESC1", _model2.getDescription());
			assertEquals("costCenter should not change", "MY_COST_CENTER1", _model2.getCostCenter());
			assertEquals("stockExchange should not change", "MY_STOCK_EXCHANGE1", _model2.getStockExchange());
			assertEquals("tickerSymbol should not change", "MY_TICKER_SYMBOL1", _model2.getTickerSymbol());
			assertEquals("orgType should not change", OrgType.ASSOC, _model2.getOrgType());
			assertEquals("logoUrl should not change", "MY_LOGO_URL1", _model2.getLogoUrl());
	
			OrgModel _model3 = get(_model2.getId(), Status.OK);			
			assertEquals("ids should be the same", _model2.getId(), _model3.getId());
			assertEquals("names should be the same", _model2.getName(), _model3.getName());
			assertEquals("descriptions should be the same", _model2.getDescription(), _model3.getDescription());
			assertEquals("costCenters should be the same", _model2.getCostCenter(), _model3.getCostCenter());
			assertEquals("stockExchanges should be the same", _model2.getStockExchange(), _model3.getStockExchange());
			assertEquals("tickerSymbols should be the same", _model2.getTickerSymbol(), _model3.getTickerSymbol());
			assertEquals("orgTypes should be the same", _model2.getOrgType(), _model3.getOrgType());
			assertEquals("logoUrls should be the same", _model2.getLogoUrl(), _model3.getLogoUrl());

			delete(_model3.getId(), Status.NO_CONTENT);
		}
		
		@Test
		public void testClientSideId() {
			OrgModel _model = new OrgModel("testClientSideId", OrgType.ASSOC);
			_model.setId("testClientSideId");
			assertEquals("id should have changed", "testClientSideId", _model.getId());
			post(_model, Status.BAD_REQUEST);
		}
		
		@Test
		public void testDuplicateId() {
			OrgModel _model1 = create("testDuplicateId1", OrgType.ASSOC, 1, Status.OK);
			OrgModel _model2 = new OrgModel("testDuplicateId2", OrgType.CLUB);
			_model2.setId(_model1.getId());		// wrongly create a 2nd OrgModel object with the same ID
			post(_model2, Status.CONFLICT);
			delete(_model1.getId(), Status.NO_CONTENT);
		}
		
		@Test
		public void testList() {
			List<OrgModel> _listBefore = list(null, Status.OK);
			ArrayList<OrgModel> _localList = new ArrayList<OrgModel>();
			for (int i = 0; i < LIMIT; i++) {
				_localList.add(create("testList", OrgType.COMP, i, Status.OK));
			}
			assertEquals("correct number of orgs should be created", LIMIT, _localList.size());
			
			List<OrgModel> _listAfter = list(null, Status.OK);		
			assertEquals("list() should return the correct number of orgs", _listBefore.size() + LIMIT, _listAfter.size());
			
			ArrayList<String> _remoteListIds = new ArrayList<String>();
			for (OrgModel _model : _listAfter) {
				_remoteListIds.add(_model.getId());
			}
			
			for (OrgModel _model : _localList) {
				assertTrue("org <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
			}
			
			for (OrgModel _model : _localList) {
				get(_model.getId(), Status.OK);
			}
			
			for (OrgModel _model : _localList) {
				delete(_model.getId(), Status.NO_CONTENT);
			}
		}

		@Test
		public void testCreate() {	
			OrgModel _model1 = create("testCreate", OrgType.COOP, 1, Status.OK);
			OrgModel _model2 = create("testCreate", OrgType.LTD, 2, Status.OK);
					
			assertNotNull("ID should be set", _model1.getId());
			assertEquals("create() should not change the name", "testCreate1", _model1.getName());
			assertEquals("create() should not change the description", "MY_DESC1", _model1.getDescription());
			assertEquals("create() should not change the costCenter", "MY_COST_CENTER1", _model1.getCostCenter());
			assertEquals("create() should not change the stockExchange", "MY_STOCK_EXCHANGE1", _model1.getStockExchange());
			assertEquals("create() should not change the tickerSymbol", "MY_TICKER_SYMBOL1", _model1.getTickerSymbol());
			assertEquals("create() should not change the orgType", OrgType.COOP, _model1.getOrgType());
			assertEquals("create() should not change the logoUrl", "MY_LOGO_URL1", _model1.getLogoUrl());
			
			assertNotNull("ID should be set", _model2.getId());
			assertEquals("create() should not change the name", "testCreate2", _model2.getName());
			assertEquals("create() should not change the description", "MY_DESC2", _model2.getDescription());
			assertEquals("create() should not change the costCenter", "MY_COST_CENTER2", _model2.getCostCenter());
			assertEquals("create() should not change the stockExchange", "MY_STOCK_EXCHANGE2", _model2.getStockExchange());
			assertEquals("create() should not change the tickerSymbol", "MY_TICKER_SYMBOL2", _model2.getTickerSymbol());
			assertEquals("create() should not change the orgType", OrgType.LTD, _model2.getOrgType());
			assertEquals("create() should not change the logoUrl", "MY_LOGO_URL2", _model2.getLogoUrl());

			assertThat(_model2.getId(), not(equalTo(_model1.getId())));

			delete(_model1.getId(), Status.NO_CONTENT);
			delete(_model2.getId(), Status.NO_CONTENT);
		}
		
		@Test
		public void testDoubleCreate() {		
			OrgModel _model = create("testDoubleCreate", OrgType.ORGUNIT, 1, Status.OK);
			assertNotNull("ID should be set:", _model.getId());		
			post(_model, Status.CONFLICT);
			delete(_model.getId(), Status.NO_CONTENT);
		}
		
		@Test
		public void testRead() 
		{
			ArrayList<OrgModel> _localList = new ArrayList<OrgModel>();
			for (int i = 0; i < LIMIT; i++) {
				_localList.add(create("testRead", OrgType.SOLE, i, Status.OK));
			}
			for (OrgModel _model : _localList) {
				get(_model.getId(), Status.OK);
			}
			List<OrgModel> _remoteList = list(null, Status.OK);
			for (OrgModel _model : _remoteList) {
				assertEquals("ID should be unchanged when reading an org", _model.getId(), get(_model.getId(), Status.OK).getId());						
			}
			for (OrgModel _model : _localList) {
				delete(_model.getId(), Status.NO_CONTENT);
			}
		}
			
		@Test
		public void testMultiRead() 
		{
			OrgModel _model1 = create("testMultiRead", OrgType.TEAM, 1, Status.OK);
			OrgModel _model2 = get(_model1.getId(), Status.OK);
			assertEquals("ID should be unchanged after read:", _model1.getId(), _model2.getId());	
			OrgModel _model3 = get(_model1.getId(), Status.OK);
						
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
			
			delete(_model1.getId(), Status.NO_CONTENT);
		}
		
		@Test
		public void testUpdate() {	
			OrgModel _model1 = create("testUpdate", OrgType.ASSOC, 1, Status.OK);
			OrgModel _model2 = put(setDefaultValues(_model1, "testUpdate", OrgType.CLUB, 2), Status.OK);

			assertNotNull("ID should be set", _model2.getId());
			assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());	
			assertEquals("name should be set correctly", "testUpdate2", _model2.getName());
			assertEquals("description should be set correctly", "MY_DESC2", _model2.getDescription());
			assertEquals("costCenter should be set correctly", "MY_COST_CENTER2", _model2.getCostCenter());
			assertEquals("stockExchange should be set correctly", "MY_STOCK_EXCHANGE2", _model2.getStockExchange());
			assertEquals("tickerSymbol should be set correctly", "MY_TICKER_SYMBOL2", _model2.getTickerSymbol());
			assertEquals("orgType should be set correctly", OrgType.CLUB, _model2.getOrgType());
			assertEquals("logoUrl should be set correctly", "MY_LOGO_URL2", _model2.getLogoUrl());
			
			OrgModel _model3 = put(setDefaultValues(_model1, "testUpdate", OrgType.COMP, 3), Status.OK);

			assertNotNull("ID should be set", _model3.getId());
			assertEquals("ID should be unchanged", _model1.getId(), _model3.getId());
			assertEquals("name should be set correctly", "testUpdate3", _model3.getName());
			assertEquals("description should be set correctly", "MY_DESC3", _model3.getDescription());
			assertEquals("costCenter should be set correctly", "MY_COST_CENTER3", _model3.getCostCenter());
			assertEquals("stockExchange should be set correctly", "MY_STOCK_EXCHANGE3", _model3.getStockExchange());
			assertEquals("tickerSymbol should be set correctly", "MY_TICKER_SYMBOL3", _model3.getTickerSymbol());
			assertEquals("orgType should be set correctly", OrgType.COMP, _model3.getOrgType());
			assertEquals("logoUrl should be set correctly", "MY_LOGO_URL3", _model3.getLogoUrl());

			delete(_model1.getId(), Status.NO_CONTENT);
		}

		@Test
		public void testDelete() 
		{
			OrgModel _model1 = create("testDelete", OrgType.ASSOC, 1, Status.OK);
			OrgModel _model2 = get(_model1.getId(), Status.OK);
			assertEquals("ID should be unchanged when reading an org", _model1.getId(), _model2.getId());						
			delete(_model1.getId(), Status.NO_CONTENT);
			get(_model1.getId(), Status.NOT_FOUND);
			get(_model1.getId(), Status.NOT_FOUND);
		}
		
		@Test
		public void testDoubleDelete() {
			OrgModel _model1 = create("testDoubleDelete", OrgType.COOP, 1, Status.OK);
			get(_model1.getId(), Status.OK);
			delete(_model1.getId(), Status.NO_CONTENT);
			get(_model1.getId(), Status.NOT_FOUND);
			delete(_model1.getId(), Status.NOT_FOUND);
			get(_model1.getId(), Status.NOT_FOUND);
		}
		
		@Test
		public void testModifications() {
			OrgModel _model1 = create("testModifications", OrgType.LTD, 1, Status.OK);
			assertNotNull("create() should set createdAt", _model1.getCreatedAt());
			assertNotNull("create() should set createdBy", _model1.getCreatedBy());
			assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
			assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
			assertEquals("createdAt and modifiedAt should be identical after create()", _model1.getCreatedAt(), _model1.getModifiedAt());
			assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());	
			
			_model1.setName("MY_NAME2");
			OrgModel _model2 = put(_model1, Status.OK);
			
			assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
			assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
			assertThat(_model2.getModifiedAt(), not(equalTo(_model2.getCreatedAt())));
			// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
			// assertThat(_model2.getModifiedBy(), not(equalTo(_model2.getCreatedBy())));
			_model1.setModifiedBy("MYSELF");
			_model1.setModifiedAt(new Date(1000));
			OrgModel _model3 = put(_model1, Status.OK);

			// test, that modifiedBy really ignored the client-side value "MYSELF"
			assertThat(_model1.getModifiedBy(), not(equalTo(_model3.getModifiedBy())));
			// check whether the client-side modifiedAt() is ignored
			assertThat(_model1.getModifiedAt(), not(equalTo(_model3.getModifiedAt())));

			delete(_model1.getId(), Status.NO_CONTENT);
		}

		/********************************** helper methods *********************************/			
		/**
		 * Retrieve a list of OrgModel from AddressbooksService by executing a HTTP GET request.
		 * This uses neither position nor size queries.
		 * @param query the URL query to use
		 * @param expectedStatus the expected HTTP status to test on
		 * @return a List of OrgModel objects in JSON format
		 */
		public List<OrgModel> list(
				String query, 
				Status expectedStatus) {
			return list(wc, adb.getId(), query, -1, Integer.MAX_VALUE, expectedStatus, false);
		}
		
		/**
		 * Retrieve a list of OrgModel from AddressbooksService by executing a HTTP GET request.
		 * @param webClient the WebClient for the AddressbooksService
		 * @param aid the ID of the addressbook to be listed
		 * @param query the URL query to use
		 * @param position the position to start a batch with
		 * @param size the size of a batch
		 * @param expectedStatus the expected HTTP status to test on
		 * @param listAllOrgs if true, allOrgs is called, else a normal list GET
		 * @return a List of OrgModel objects in JSON format
		 */
		public static List<OrgModel> list(
				WebClient webClient, 
				String aid,
				String query, 
				int position,
				int size,
				Status expectedStatus,
				boolean listAllOrgs) {
			webClient.resetQuery();
			if (listAllOrgs == true) {
				webClient.replacePath("/").path("allOrgs");
			} else {
				webClient.replacePath("/").path(aid).path(ServiceUtil.ORG_PATH_EL);
			}
			Response _response = executeListQuery(webClient, query, position, size);
			List<OrgModel> _list = null;
			if (expectedStatus != null) {
				assertEquals("list() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
			}
			if (_response.getStatus() == Status.OK.getStatusCode()) {
				_list = new ArrayList<OrgModel>(webClient.getCollection(OrgModel.class));
				System.out.println("list(webClient, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + ", " + listAllOrgs + ") ->" + _list.size());
			}
			return _list;
		}
		
		/**
		 * Create a new OrgModel on the server by executing a HTTP POST request.
		 * @param model the OrgModel to post
		 * @param expectedStatus the expected HTTP status to test on; if this is null, it will not be tested
		 * @return the created OrgModel
		 */
		private OrgModel post(
				OrgModel model,
				Status expectedStatus) {
			return post(wc, adb.getId(), model, expectedStatus);
		}

		/**
		 * Create a new OrgModel on the server by executing a HTTP POST request.
		 * @param webClient the WebClient representing the AddressbooksService
		 * @param aid the ID of the addressbook to be listed
		 * @param model the OrgModel data to create on the server
		 * @param exceptedStatus the expected HTTP status to test on
		 * @return the created OrgModel
		 */
		public static OrgModel post(
				WebClient webClient,
				String aid,
				OrgModel model,
				Status expectedStatus) 
		{
			Response _response = webClient.replacePath("/").path(aid).path(ServiceUtil.ORG_PATH_EL).post(model);
			if (expectedStatus != null) {
				assertEquals("POST should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
			}
			if (_response.getStatus() == Status.OK.getStatusCode()) {
				return _response.readEntity(OrgModel.class);
			} else {
				return null;
			}
		}
		
		private OrgModel setDefaultValues(
				OrgModel model,
				String name,
				OrgType orgType,
				int suffix) 
		{
			model.setName(name + suffix);
			model.setDescription("MY_DESC" + suffix);
			model.setCostCenter("MY_COST_CENTER" + suffix);
			model.setStockExchange("MY_STOCK_EXCHANGE" + suffix);
			model.setTickerSymbol("MY_TICKER_SYMBOL" + suffix);
			model.setOrgType(orgType);
			model.setLogoUrl("MY_LOGO_URL" + suffix);
			return model;
		}
		
		/**
		 * @param name
		 * @param orgType
		 * @param suffix
		 * @param expectedStatus the expected HTTP status to test on 
		 * @return
		 */
		private OrgModel create(
				String name, 
				OrgType orgType, 
				int suffix,
				Status expectedStatus) 
		{
			OrgModel _model = setDefaultValues(new OrgModel(), name, orgType, suffix);
			return post(wc, adb.getId(), _model, expectedStatus);
		}
		
		/**
		 * @param webClient
		 * @param aid
		 * @param name
		 * @param orgType
		 * @param expectedStatus  the expected HTTP status to test on
		 * @return
		 */
		public static OrgModel create(
				WebClient webClient, 
				String aid, 
				String name, 
				OrgType orgType,
				Status expectedStatus) 
		{
			OrgModel _model = new OrgModel();
			_model.setName(name);
			_model.setOrgType(orgType);
			Response _response = webClient.replacePath("/").path(aid).path(ServiceUtil.ORG_PATH_EL).post(_model);
			if (expectedStatus != null) {
				assertEquals("GET should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
			}
			if (_response.getStatus() == Status.OK.getStatusCode()) {
				return _response.readEntity(OrgModel.class);
			} else {
				return null;
			}
		}
		
		/**
		 * Read the OrgModel with id from AddressbooksService by executing a HTTP GET method.
		 * @param id the id of the OrgModel to retrieve
		 * @param expectedStatus the expected HTTP status to test on
		 * @return the retrieved OrgModel object in JSON format
		 */
		private OrgModel get(
				String id, 
				Status expectedStatus) {
			return get(wc, adb.getId(), id, expectedStatus);
		}
		
		/**
		 * Read the OrgModel with id from AddressbooksService by executing a HTTP GET method.
		 * @param webClient the web client representing the AddressbooksService
		 * @param aid the ID of the addressbook 
		 * @param id the id of the OrgModel to retrieve
		 * @param expectedStatus  the expected HTTP status to test on
		 * @return the retrieved OrgModel object in JSON format
		 */
		public static OrgModel get(
				WebClient webClient,
				String aid,
				String id,
				Status expectedStatus) {
			Response _response = webClient.replacePath("/").path(aid).path(ServiceUtil.ORG_PATH_EL).path(id).get();
			if (expectedStatus != null) {
				assertEquals("GET should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
			}
			if (_response.getStatus() == Status.OK.getStatusCode()) {
				return _response.readEntity(OrgModel.class);
			} else {
				return null;
			}
		}
		
		/**
		 * Update a OrgModel on the AddressbooksService by executing a HTTP PUT method.
		 * @param model the new OrgModel data
		 * @param expectedStatus the expected HTTP status to test on
		 * @return the updated OrgModel object in JSON format
		 */
		public OrgModel put(
				OrgModel model, 
				Status expectedStatus) {
			return put(wc, adb.getId(), model, expectedStatus);
		}
		
		/**
		 * Update a OrgModel on the AddressbooksService by executing a HTTP PUT method.
		 * @param webClient the web client representing the AddressbooksService
		 * @param aid the ID of the addressbook 
		 * @param model the new OrgModel data
		 * @param expectedStatus the expected HTTP status to test on
		 * @return the updated OrgModel object in JSON format
		 */
		public static OrgModel put(
				WebClient webClient,
				String aid,
				OrgModel model,
				Status expectedStatus) {
			webClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			Response _response = webClient.replacePath("/").path(aid).path(ServiceUtil.ORG_PATH_EL).path(model.getId()).put(model);
			if (expectedStatus != null) {
				assertEquals("PUT should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
			}
			if (_response.getStatus() == Status.OK.getStatusCode()) {
				return _response.readEntity(OrgModel.class);
			} else {
				return null;
			}
		}
		
		/**
		 * Delete the OrgModel with id on the AddressbooksService by executing a HTTP DELETE method.
		 * @param id the id of the OrgModel object to delete
		 * @param expectedStatus the expected HTTP status to test on
		 */
		public void delete(
				String id, 
				Status expectedStatus) {
			delete(wc, adb.getId(), id, expectedStatus);
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
		
		/* (non-Javadoc)
		 * @see test.org.opentdc.AbstractTestClient#calculateMembers()
		 */
		protected int calculateMembers() {
			return AddressbookTest.list(wc, null, 0, Integer.MAX_VALUE, Status.OK).size();
		}
}
