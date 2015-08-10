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
		public void testOrgModelEmptyConstructor() {
			// new() -> _om
			OrgModel _om = new OrgModel();
			assertNull("id should not be set by empty constructor", _om.getId());
			assertNull("name should not be set by empty constructor", _om.getName());
			assertNull("description should not be set by empty constructor", _om.getDescription());
			assertNull("costCenter should not be set by empty constructor", _om.getCostCenter());
			assertNull("stockExchange should not be set by empty constructor", _om.getStockExchange());
			assertNull("tickerSymbol should not be set by empty constructor", _om.getTickerSymbol());
			assertNull("orgType should not be set by empty constructor", _om.getOrgType());
			assertNull("logoUrl should not be set by empty constructor", _om.getLogoUrl());
		}
			
		// ID
		@Test
		public void testOrgId() {
			// new() -> _om -> _om.setId()
			OrgModel _om = new OrgModel();
			assertNull("id should not be set by constructor", _om.getId());
			_om.setId("MY_ID");
			assertEquals("id should have changed:", "MY_ID", _om.getId());
		}

		// Name
		@Test
		public void testOrgName() {
			// new() -> _om -> _om.setName()
			OrgModel _om = new OrgModel();
			assertNull("Name should not be set by constructor", _om.getName());
			_om.setName("MY_Name");
			assertEquals("Name should have changed:", "MY_Name", _om.getName());
		}

		// Description
		@Test
		public void testOrgDescription() {
			// new() -> _om -> _om.setDescription()
			OrgModel _om = new OrgModel();
			assertNull("Description should not be set by constructor", _om.getDescription());
			_om.setDescription("MY_Description");
			assertEquals("Description should have changed:", "MY_Description", _om.getDescription());
		}

		// CostCenter
		@Test
		public void testOrgCostCenter() {
			// new() -> _om -> _om.setCostCenter()
			OrgModel _om = new OrgModel();
			assertNull("CostCenter should not be set by constructor", _om.getCostCenter());
			_om.setCostCenter("MY_CostCenter");
			assertEquals("id should have changed:", "MY_CostCenter", _om.getCostCenter());
		}

		// StockExchange
		@Test
		public void testOrgStockExchange() {
			// new() -> _om -> _om.setStockExchange()
			OrgModel _om = new OrgModel();
			assertNull("StockExchange should not be set by constructor", _om.getStockExchange());
			_om.setStockExchange("MY_StockExchange");
			assertEquals("StockExchange should have changed:", "MY_StockExchange", _om.getStockExchange());
		}

		// TickerSymbol
		@Test
		public void testOrgTickerSymbol() {
			// new() -> _om -> _om.setTickerSymbol()
			OrgModel _om = new OrgModel();
			assertNull("TickerSymbol should not be set by constructor", _om.getTickerSymbol());
			_om.setTickerSymbol("MY_TickerSymbol");
			assertEquals("TickerSymbol should have changed:", "MY_TickerSymbol", _om.getTickerSymbol());
		}

		// orgType
		@Test
		public void testOrgOrgType() {
			// new() -> _om -> _om.setOrgType()
			OrgModel _om = new OrgModel();
			assertNull("orgType should not be set by constructor", _om.getOrgType());
			_om.setOrgType(OrgType.ASSOC);
			assertEquals("orgType should have changed:", OrgType.ASSOC, _om.getOrgType());
		}
		
		// logoUrl
		@Test
		public void testOrgLogoUrl() {
			// new() -> _om -> _om.setLogoUrl()
			OrgModel _om = new OrgModel();
			assertNull("logoUrl should not be set by constructor", _om.getLogoUrl());
			_om.setLogoUrl("MY_LOGO_URL");
			assertEquals("logoUr should have changed:", "MY_LOGO_URL", _om.getLogoUrl());
		}

		// createdBy
		@Test
		public void testOrgCreatedBy() {
			// new() -> _om -> _om.setCreatedBy()
			OrgModel _om = new OrgModel();
			assertNull("createdBy should not be set by empty constructor", _om.getCreatedBy());
			_om.setCreatedBy("MY_NAME");
			assertEquals("createdBy should have changed", "MY_NAME", _om.getCreatedBy());	
		}

		// createdAt
		@Test
		public void testOrgCreatedAt() {
			// new() -> _om -> _om.setCreatedAt()
			OrgModel _om = new OrgModel();
			assertNull("createdAt should not be set by empty constructor", _om.getCreatedAt());
			_om.setCreatedAt(new Date());
			assertNotNull("createdAt should have changed", _om.getCreatedAt());
		}

		// modifiedBy
		@Test
		public void testOrgModifiedBy() {
			// new() -> _om -> _om.setModifiedBy()
			OrgModel _om = new OrgModel();
			assertNull("modifiedBy should not be set by empty constructor", _om.getModifiedBy());
			_om.setModifiedBy("MY_NAME");
			assertEquals("modifiedBy should have changed", "MY_NAME", _om.getModifiedBy());	
		}

		// modifiedAt
		@Test
		public void testOrgModifiedAt() {
			// new() -> _om -> _om.setModifiedAt()
			OrgModel _om = new OrgModel();
			assertNull("modifiedAt should not be set by empty constructor", _om.getModifiedAt());
			_om.setModifiedAt(new Date());
			assertNotNull("modifiedAt should have changed", _om.getModifiedAt());
		}

		/********************************* REST service tests *********************************/	
				
		@Test
		public void testOrgCreateReadDeleteWithEmptyConstructor() {
			// new() -> _om1
			OrgModel _om1 = new OrgModel();
			assertNull("id should not be set by empty constructor", _om1.getId());
			assertNull("name should not be set by empty constructor", _om1.getName());
			assertNull("description should not be set by empty constructor", _om1.getDescription());
			assertNull("costCenter should not be set by empty constructor", _om1.getCostCenter());
			assertNull("stockExchange should not be set by empty constructor", _om1.getStockExchange());
			assertNull("tickerSymbol should not be set by empty constructor", _om1.getTickerSymbol());
			assertNull("orgType should not be set by empty constructor", _om1.getOrgType());
			assertNull("logoUrl should not be set by empty constructor", _om1.getLogoUrl());
			
			// create(_om1) -> BAD_REQUEST (because of empty name)
			Response _response = wc.replacePath("/").post(_om1);
			assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
			_om1.setName("testOrgCreateReadDeleteWithEmptyConstructor");

			// create(_om1) -> _om2
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(_om1);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _om2 = _response.readEntity(OrgModel.class);
			
			// validate _om1
			assertNull("create() should not change the id of the local object", _om1.getId());
			assertEquals("name should not be set by empty constructor", "testOrgCreateReadDeleteWithEmptyConstructor", _om1.getName());
			assertNull("description should not be set by empty constructor", _om1.getDescription());
			assertNull("costCenter should not be set by empty constructor", _om1.getCostCenter());
			assertNull("stockExchange should not be set by empty constructor", _om1.getStockExchange());
			assertNull("tickerSymbol should not be set by empty constructor", _om1.getTickerSymbol());
			assertNull("orgType should not be set by empty constructor", _om1.getOrgType());
			assertNull("logoUrl should not be set by empty constructor", _om1.getLogoUrl());
			
			// validate _om2
			assertNotNull("create() should set a valid id", _om2.getId());
			assertEquals("create() should not change the name", "testOrgCreateReadDeleteWithEmptyConstructor", _om2.getName());
			assertNull("create() should not change the description", _om2.getDescription());
			assertNull("create() should not change the costCenter", _om2.getCostCenter());
			assertNull("create() should not change the stockExchange", _om2.getStockExchange());
			assertNull("create() should not change the tickerSymbol", _om2.getTickerSymbol());
			assertEquals("create() should set a default orgType", OrgType.getDefaultOrgType(), _om2.getOrgType());
			assertNull("create() should not change the logoUrl", _om2.getLogoUrl());

			// read(_om2) -> _om3
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om2.getId()).get();
			assertEquals("read(" + _om2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _om3 = _response.readEntity(OrgModel.class);
			
			// validate that the attributes of _om2 and _om3 are equal
			assertEquals("ids should be equal", _om2.getId(), _om3.getId());
			assertEquals("names should be equal", _om2.getName(), _om3.getName());
			assertEquals("descriptions should be equal", _om2.getDescription(), _om3.getDescription());
			assertEquals("costCenters should be equal", _om2.getCostCenter(), _om3.getCostCenter());
			assertEquals("stockExchanges should be equal", _om2.getStockExchange(), _om3.getStockExchange());
			assertEquals("tickerSymbols should be equal", _om2.getTickerSymbol(), _om3.getTickerSymbol());
			assertEquals("orgTypes should be equal", _om2.getOrgType(), _om3.getOrgType());
			assertEquals("logoUrls should be equal", _om2.getLogoUrl(), _om3.getLogoUrl());
			
			// delete(_p3)
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om3.getId()).delete();
			assertEquals("delete(" + _om3.getId() + ") should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testOrgCreateReadDelete() {
			// new(with custom attributes) -> _om1			
			OrgModel _om1 = createOrg("testOrgCreateReadDelete", OrgType.ASSOC, 1);
			assertNull("id should not be set by constructor", _om1.getId());
			
			// validate _om1 (attributes set by constructor)
			assertEquals("constructor should set the name correctly", "testOrgCreateReadDelete1", _om1.getName());
			assertEquals("constructor should set the description correctly", "MY_DESC1", _om1.getDescription());
			assertEquals("constructor should set the costCenter correctly", "MY_COST_CENTER1", _om1.getCostCenter());
			assertEquals("constructor should set the stockExchange correctly", "MY_STOCK_EXCHANGE1", _om1.getStockExchange());
			assertEquals("constructor should set the tickerSymbol correctly", "MY_TICKER_SYMBOL1", _om1.getTickerSymbol());
			assertEquals("constructor should set the orgType correctly", OrgType.ASSOC, _om1.getOrgType());
			assertEquals("constructor should set the logoUrl correctly", "MY_LOGO_URL1", _om1.getLogoUrl());
						
			// create(_om1) -> _om2
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(_om1);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _om2 = _response.readEntity(OrgModel.class);
			
			// validate _om1 (local object)
			assertNull("id should still be null", _om1.getId());
			assertEquals("create() should not change the name", "testOrgCreateReadDelete1", _om1.getName());
			assertEquals("create() should not change the description", "MY_DESC1", _om1.getDescription());
			assertEquals("create() should not change the costCenter", "MY_COST_CENTER1", _om1.getCostCenter());
			assertEquals("create() should not change the stockExchange", "MY_STOCK_EXCHANGE1", _om1.getStockExchange());
			assertEquals("create() should not change the tickerSymbol", "MY_TICKER_SYMBOL1", _om1.getTickerSymbol());
			assertEquals("create() should not change the orgType", OrgType.ASSOC, _om1.getOrgType());
			assertEquals("create() should not change the logoUrl", "MY_LOGO_URL1", _om1.getLogoUrl());
						
			// validate _om2 (returned remote object)
			assertNotNull("id of returned object should be set", _om2.getId());
			assertEquals("create() should not change the name", "testOrgCreateReadDelete1", _om2.getName());
			assertEquals("create() should not change the description", "MY_DESC1", _om2.getDescription());
			assertEquals("create() should not change the costCenter", "MY_COST_CENTER1", _om2.getCostCenter());
			assertEquals("create() should not change the stockExchange", "MY_STOCK_EXCHANGE1", _om2.getStockExchange());
			assertEquals("create() should not change the tickerSymbol", "MY_TICKER_SYMBOL1", _om2.getTickerSymbol());
			assertEquals("create() should not change the orgType", OrgType.ASSOC, _om2.getOrgType());
			assertEquals("create() should not change the logoUrl", "MY_LOGO_URL1", _om2.getLogoUrl());
						
			// read(_om2)  -> _om3
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om2.getId()).get();
			assertEquals("read(" + _om2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _om3 = _response.readEntity(OrgModel.class);
			
			// validate equality of _om2 and _om3 attributes
			assertEquals("ids should be the same", _om2.getId(), _om3.getId());
			assertEquals("names should be the same", _om2.getName(), _om3.getName());
			assertEquals("descriptions should be the same", _om2.getDescription(), _om3.getDescription());
			assertEquals("costCenters should be the same", _om2.getCostCenter(), _om3.getCostCenter());
			assertEquals("stockExchanges should be the same", _om2.getStockExchange(), _om3.getStockExchange());
			assertEquals("tickerSymbols should be the same", _om2.getTickerSymbol(), _om3.getTickerSymbol());
			assertEquals("orgTypes should be the same", _om2.getOrgType(), _om3.getOrgType());
			assertEquals("logoUrls should be the same", _om2.getLogoUrl(), _om3.getLogoUrl());

			// delete(_om3)
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om3.getId()).delete();
			assertEquals("delete(" + _om3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testOrgWithClientSideId() {
			// new() -> _om1 -> _om1.setId()
			OrgModel _om1 = createOrg("testOrgWithClientSideId", OrgType.ASSOC, 1);
			_om1.setId("LOCAL_ID");
			assertEquals("id should have changed", "LOCAL_ID", _om1.getId());
			// create(_om1) -> BAD_REQUEST
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(_om1);
			assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testOrgWithDuplicateId() {
			// create(new()) -> _om1
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(createOrg("testOrgWithDuplicateId", OrgType.ASSOC, 1));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _om1 = _response.readEntity(OrgModel.class);

			// new() -> _om2 -> _om2.setId(_om1.getId())
			OrgModel _om2 = createOrg("testOrgWithDuplicateId", OrgType.ASSOC, 2);
			_om2.setId(_om1.getId());		// wrongly create a 2nd OrgModel object with the same ID
			
			// create(_om2) -> CONFLICT
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(_om2);
			assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testOrgList() {
			ArrayList<OrgModel> _localList = new ArrayList<OrgModel>();		
			Response _response = null;
			wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL);
			for (int i = 0; i < LIMIT; i++) {
				// create(new()) -> _localList
				_response = wc.post(createOrg("testOrgList", OrgType.ASSOC, i));
				assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_localList.add(_response.readEntity(OrgModel.class));
			}
			assertEquals("size of lists should be the same", LIMIT, _localList.size());
			
			// list(/) -> _remoteList
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).get();
			List<OrgModel> _remoteList = new ArrayList<OrgModel>(wc.getCollection(OrgModel.class));
			assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			assertEquals("size of lists should be the same", LIMIT, _remoteList.size());
			// implicit proof: _localList.size() = _remoteList.size = LIMIT

			ArrayList<String> _remoteListIds = new ArrayList<String>();
			for (OrgModel _om : _remoteList) {
				_remoteListIds.add(_om.getId());
			}
			
			for (OrgModel _om : _localList) {
				assertTrue("project <" + _om.getId() + "> should be listed", _remoteListIds.contains(_om.getId()));
			}
			
			for (OrgModel _om : _localList) {
				_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om.getId()).get();
				assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_response.readEntity(OrgModel.class);
			}
			
			for (OrgModel _om : _localList) {
				_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om.getId()).delete();
				assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
			}
		}

		@Test
		public void testOrgCreate() {				
			// create()  -> _om1
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(createOrg("testOrgCreate", OrgType.ASSOC, 1));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _om1 = _response.readEntity(OrgModel.class);

			// create() -> _om2
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(createOrg("testOrgCreate", OrgType.CLUB, 2));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _om2 = _response.readEntity(OrgModel.class);		
			
			// validate attributes of _om1
			assertNotNull("ID should be set", _om1.getId());
			assertEquals("create() should not change the name", "testOrgCreate1", _om1.getName());
			assertEquals("create() should not change the description", "MY_DESC1", _om1.getDescription());
			assertEquals("create() should not change the costCenter", "MY_COST_CENTER1", _om1.getCostCenter());
			assertEquals("create() should not change the stockExchange", "MY_STOCK_EXCHANGE1", _om1.getStockExchange());
			assertEquals("create() should not change the tickerSymbol", "MY_TICKER_SYMBOL1", _om1.getTickerSymbol());
			assertEquals("create() should not change the orgType", OrgType.ASSOC, _om1.getOrgType());
			assertEquals("create() should not change the logoUrl", "MY_LOGO_URL1", _om1.getLogoUrl());
			
			// validate attributes of _om2
			assertNotNull("ID should be set", _om2.getId());
			assertEquals("create() should not change the name", "testOrgCreate2", _om2.getName());
			assertEquals("create() should not change the description", "MY_DESC2", _om2.getDescription());
			assertEquals("create() should not change the costCenter", "MY_COST_CENTER2", _om2.getCostCenter());
			assertEquals("create() should not change the stockExchange", "MY_STOCK_EXCHANGE2", _om2.getStockExchange());
			assertEquals("create() should not change the tickerSymbol", "MY_TICKER_SYMBOL2", _om2.getTickerSymbol());
			assertEquals("create() should not change the orgType", OrgType.CLUB, _om2.getOrgType());
			assertEquals("create() should not change the logoUrl", "MY_LOGO_URL2", _om2.getLogoUrl());

			assertThat(_om2.getId(), not(equalTo(_om1.getId())));

			// delete(_om1) -> NO_CONTENT
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om1.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

			// delete(_om2) -> NO_CONTENT
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om2.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testOrgCreateDouble() {		
			// create(new()) -> _om
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(createOrg("testOrgCreateDouble", OrgType.ASSOC, 1));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _om = _response.readEntity(OrgModel.class);
			assertNotNull("ID should be set:", _om.getId());		
			
			// create(_om) -> CONFLICT
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(_om);
			assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

			// delete(_om) -> NO_CONTENT
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testOrgRead() {
			ArrayList<OrgModel> _localList = new ArrayList<OrgModel>();
			Response _response = null;
			wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL);
			for (int i = 0; i < LIMIT; i++) {
				_response = wc.post(createOrg("testOrgRead", OrgType.ASSOC, i));
				assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_localList.add(_response.readEntity(OrgModel.class));
			}
		
			// test read on each local element
			for (OrgModel _om : _localList) {
				_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om.getId()).get();
				assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_response.readEntity(OrgModel.class);
			}

			// test read on each listed element
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).get();
			List<OrgModel> _remoteList = new ArrayList<OrgModel>(wc.getCollection(OrgModel.class));
			assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

			OrgModel _tmpObj = null;
			for (OrgModel _om : _remoteList) {
				_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om.getId()).get();
				assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_tmpObj = _response.readEntity(OrgModel.class);
				assertEquals("ID should be unchanged when reading a project", _om.getId(), _tmpObj.getId());						
			}

			for (OrgModel _om : _localList) {
				_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om.getId()).delete();
				assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
			}
		}
			
		@Test
		public void testOrgMultiRead() {
			// create() -> _om1
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(createOrg("testOrgMultiRead", OrgType.ASSOC, 1));
			OrgModel _om1 = _response.readEntity(OrgModel.class);

			// read(_om1) -> _om2
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om1.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _om2 = _response.readEntity(OrgModel.class);
			assertEquals("ID should be unchanged after read:", _om1.getId(), _om2.getId());		

			// read(_om1) -> _om3
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om1.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _om3 = _response.readEntity(OrgModel.class);
			
			// attributes should be the same, but the two objects are not equal
			assertEquals("ID should be the same:", _om2.getId(), _om3.getId());
			assertEquals("names should be the same", _om2.getName(), _om3.getName());
			assertEquals("descriptions should be the same", _om2.getDescription(), _om3.getDescription());
			assertEquals("costCenters should be the same", _om2.getCostCenter(), _om3.getCostCenter());
			assertEquals("stockExchanges should be the same", _om2.getStockExchange(), _om3.getStockExchange());
			assertEquals("tickerSymbols should be the same", _om2.getTickerSymbol(), _om3.getTickerSymbol());
			assertEquals("orgTypes should be the same", _om2.getOrgType(), _om3.getOrgType());
			assertEquals("logoUrls should be the same", _om2.getLogoUrl(), _om3.getLogoUrl());
			
			
			assertEquals("ID should be the same:", _om2.getId(), _om1.getId());
			assertEquals("names should be the same", _om2.getName(), _om1.getName());
			assertEquals("descriptions should be the same", _om2.getDescription(), _om1.getDescription());
			assertEquals("costCenters should be the same", _om2.getCostCenter(), _om1.getCostCenter());
			assertEquals("stockExchanges should be the same", _om2.getStockExchange(), _om1.getStockExchange());
			assertEquals("tickerSymbols should be the same", _om2.getTickerSymbol(), _om1.getTickerSymbol());
			assertEquals("orgTypes should be the same", _om2.getOrgType(), _om1.getOrgType());
			assertEquals("logoUrls should be the same", _om2.getLogoUrl(), _om1.getLogoUrl());
			
			// delete(_p2)
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om1.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testOrgUpdate() {			
			// create() -> _om1
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(createOrg("testOrgUpdate", OrgType.ASSOC, 1));
			OrgModel _om1 = _response.readEntity(OrgModel.class);
			
			// change the attributes
			// update(_om1) -> _om2
			_om1.setName("testOrgUpdate2");
			_om1.setDescription("MY_DESC2");
			_om1.setCostCenter("MY_COST_CENTER2");
			_om1.setStockExchange("MY_STOCK_EXCHANGE2");
			_om1.setTickerSymbol("MY_TICKER_SYMBOL2");
			_om1.setOrgType(OrgType.CLUB);
			_om1.setLogoUrl("MY_LOGO_URL2");
			wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om1.getId()).put(_om1);
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _om2 = _response.readEntity(OrgModel.class);

			assertNotNull("ID should be set", _om2.getId());
			assertEquals("ID should be unchanged", _om1.getId(), _om2.getId());	
			assertEquals("name should be set correctly", "testOrgUpdate2", _om2.getName());
			assertEquals("description should be set correctly", "MY_DESC2", _om2.getDescription());
			assertEquals("costCenter should be set correctly", "MY_COST_CENTER2", _om2.getCostCenter());
			assertEquals("stockExchange should be set correctly", "MY_STOCK_EXCHANGE2", _om2.getStockExchange());
			assertEquals("tickerSymbol should be set correctly", "MY_TICKER_SYMBOL2", _om2.getTickerSymbol());
			assertEquals("orgType should be set correctly", OrgType.CLUB, _om2.getOrgType());
			assertEquals("logoUrl should be set correctly", "MY_LOGO_URL2", _om2.getLogoUrl());

			// reset the attributes
			// update(_om1) -> _om3
			_om1.setName("testOrgUpdate3");
			_om1.setDescription("MY_DESC3");
			_om1.setCostCenter("MY_COST_CENTER3");
			_om1.setStockExchange("MY_STOCK_EXCHANGE3");
			_om1.setTickerSymbol("MY_TICKER_SYMBOL3");
			_om1.setOrgType(OrgType.COMP);
			_om1.setLogoUrl("MY_LOGO_URL3");
			wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om1.getId()).put(_om1);
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _om3 = _response.readEntity(OrgModel.class);

			assertNotNull("ID should be set", _om3.getId());
			assertEquals("ID should be unchanged", _om1.getId(), _om3.getId());
			assertEquals("name should be set correctly", "testOrgUpdate3", _om3.getName());
			assertEquals("description should be set correctly", "MY_DESC3", _om3.getDescription());
			assertEquals("costCenter should be set correctly", "MY_COST_CENTER3", _om3.getCostCenter());
			assertEquals("stockExchange should be set correctly", "MY_STOCK_EXCHANGE3", _om3.getStockExchange());
			assertEquals("tickerSymbol should be set correctly", "MY_TICKER_SYMBOL3", _om3.getTickerSymbol());
			assertEquals("orgType should be set correctly", OrgType.COMP, _om3.getOrgType());
			assertEquals("logoUrl should be set correctly", "MY_LOGO_URL3", _om3.getLogoUrl());
			
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om1.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}

		@Test
		public void testOrgDelete(
		) {
			// create() -> _om1
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(createOrg("testOrgDelete", OrgType.ASSOC, 1));
			OrgModel _om1 = _response.readEntity(OrgModel.class);
			
			// read(_om1) -> _tmpObj
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om1.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _tmpObj = _response.readEntity(OrgModel.class);
			assertEquals("ID should be unchanged when reading a project (remote):", _om1.getId(), _tmpObj.getId());						

			// read(_om1) -> _tmpObj
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om1.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(OrgModel.class);
			assertEquals("ID should be unchanged when reading a project (remote):", _om1.getId(), _tmpObj.getId());						
			
			// delete(_om1) -> OK
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om1.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
			// read the deleted object twice
			// read(_om1) -> NOT_FOUND
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om1.getId()).get();
			assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
			
			// read(_om1) -> NOT_FOUND
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om1.getId()).get();
			assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testOrgDoubleDelete() {
			// create() -> _om1
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(createOrg("testOrgDoubleDelete", OrgType.ASSOC, 1));
			OrgModel _om1 = _response.readEntity(OrgModel.class);

			// read(_om1) -> OK
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om1.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			
			// delete(_om1) -> OK
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om1.getId()).delete();		
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
			
			// read(_om1) -> NOT_FOUND
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om1.getId()).get();
			assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
			
			// delete _om1 -> NOT_FOUND
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om1.getId()).delete();		
			assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
			
			// read _om1 -> NOT_FOUND
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om1.getId()).get();
			assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testOrgModifications() {
			// create() -> _om1
			Response _response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).post(createOrg("testOrgModifications", OrgType.ASSOC, 1));
			OrgModel _om1 = _response.readEntity(OrgModel.class);
			
			// test createdAt and createdBy
			assertNotNull("create() should set createdAt", _om1.getCreatedAt());
			assertNotNull("create() should set createdBy", _om1.getCreatedBy());
			// test modifiedAt and modifiedBy (= same as createdAt/createdBy)
			assertNotNull("create() should set modifiedAt", _om1.getModifiedAt());
			assertNotNull("create() should set modifiedBy", _om1.getModifiedBy());
			assertTrue("createdAt should be less than or equal to modifiedAt after create()", _om1.getCreatedAt().compareTo(_om1.getModifiedAt()) <= 0);
			assertEquals("createdBy and modifiedBy should be identical after create()", _om1.getCreatedBy(), _om1.getModifiedBy());
			
			// update(_om1)  -> _om2
			_om1.setName("MY_NAME2");
			wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om1.getId()).put(_om1);
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _om2 = _response.readEntity(OrgModel.class);

			// test createdAt and createdBy (unchanged)
			assertEquals("update() should not change createdAt", _om1.getCreatedAt(), _om2.getCreatedAt());
			assertEquals("update() should not change createdBy", _om1.getCreatedBy(), _om2.getCreatedBy());
			
			// test modifiedAt and modifiedBy (= different from createdAt/createdBy)
			assertThat(_om2.getModifiedAt(), not(equalTo(_om2.getCreatedAt())));
			// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
			// assertThat(_om2.getModifiedBy(), not(equalTo(_om2.getCreatedBy())));

			// update(_om1) with modifiedBy/At set on client side -> ignored by server
			_om1.setModifiedBy("MYSELF");
			_om1.setModifiedAt(new Date(1000));
			wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om1.getId()).put(_om1);
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			OrgModel _o3 = _response.readEntity(OrgModel.class);
			
			// test, that modifiedBy really ignored the client-side value "MYSELF"
			assertThat(_om1.getModifiedBy(), not(equalTo(_o3.getModifiedBy())));
			// check whether the client-side modifiedAt() is ignored
			assertThat(_om1.getModifiedAt(), not(equalTo(_o3.getModifiedAt())));
			
			// delete(_om1) -> NO_CONTENT
			_response = wc.replacePath("/").path(adb.getId()).path(ServiceUtil.ORG_PATH_EL).path(_om1.getId()).delete();		
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}

		/********************************** helper methods *********************************/			
		private OrgModel createOrg(String name, OrgType orgType, int suffix) {
			OrgModel _om = new OrgModel();
			_om.setName(name + suffix);
			_om.setDescription("MY_DESC" + suffix);
			_om.setCostCenter("MY_COST_CENTER" + suffix);
			_om.setStockExchange("MY_STOCK_EXCHANGE" + suffix);
			_om.setTickerSymbol("MY_TICKER_SYMBOL" + suffix);
			_om.setOrgType(orgType);
			_om.setLogoUrl("MY_LOGO_URL" + suffix);
			return _om;
		}
		
		public static OrgModel createOrg(WebClient abWC, String aid, String name, OrgType orgType) {
			OrgModel _om = new OrgModel();
			_om.setName(name);
			_om.setOrgType(orgType);
			Response _response = abWC.replacePath("/").path(aid).path(ServiceUtil.ORG_PATH_EL).post(_om);
			return _response.readEntity(OrgModel.class);
		}
		
		/**
		 * Delete the OrgModel with id on the AddressbooksService by executing a HTTP DELETE method.
		 * @param webClient the WebClient for the AddressbooksService
		 * @param aid the id of the addressbook
		 * @param orgId the id of the OrgModel object to delete
		 * @param expectedStatus the expected HTTP status to test on
		 */
		public static void deleteOrg(
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
