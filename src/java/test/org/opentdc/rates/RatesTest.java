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
package test.org.opentdc.rates;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
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
import org.opentdc.rates.Currency;
import org.opentdc.rates.RatesModel;
import org.opentdc.rates.RatesService;

import test.org.opentdc.AbstractTestClient;

public class RatesTest extends AbstractTestClient {
	public static final String API_URL = "api/rate/";
	private WebClient rateWC = null;

	@Before
	public void initializeTest() {
		rateWC = initializeTest(API_URL, RatesService.class);
	}
	
	@After
	public void cleanupTest() {
		rateWC.close();
	}

	/********************************** rates attributes tests *********************************/	
	@Test
	public void testRatesModelEmptyConstructor() {
		// new() -> _c
		RatesModel _rm = new RatesModel();
		assertNull("id should not be set by empty constructor", _rm.getId());
		assertNull("title should not be set by empty constructor", _rm.getTitle());
		assertEquals("rate should be initialized to 0 by empty constructor", 0, _rm.getRate());
		assertNull("currency should not be set by empty constructor", _rm.getCurrency());
		assertNull("description should not be set by empty constructor", _rm.getDescription());
	}
	
	@Test
	public void testRatesModelConstructor() {		
		// new("testRatesModelConstructor", 100, "MY_DESC") -> _rm
		RatesModel _rm = new RatesModel("testRatesModelConstructor", 100, "MY_DESC");
		assertNull("id should not be set by constructor", _rm.getId());
		assertEquals("title should be set by constructor", "testRatesModelConstructor", _rm.getTitle());
		assertEquals("rate should be set by constructor", 100, _rm.getRate());
		assertEquals("currency should be set to default value by constructor", 
				Currency.getDefaultCurrency(), _rm.getCurrency());
		assertEquals("description should not set by constructor", "MY_DESC", _rm.getDescription());
	}
	
	@Test
	public void testRatesModelIdAttributeChange() {
		// new() -> _rm -> _rm.setId()
		RatesModel _rm = new RatesModel();
		assertNull("id should not be set by constructor", _rm.getId());
		_rm.setId("MY_ID");
		assertEquals("id should have changed", "MY_ID", _rm.getId());
	}

	@Test
	public void testRatesModelTitleAttributeChange() {
		// new() -> _rm -> _rm.setTitle()
		RatesModel _rm = new RatesModel();
		assertNull("title should not be set by empty constructor", _rm.getTitle());
		_rm.setTitle("MY_TITLE");
		assertEquals("title should have changed", "MY_TITLE", _rm.getTitle());
	}
	
	@Test
	public void testRatesModelRateAttributeChange() {
		// new() -> _rm -> _rm.setRate()
		RatesModel _rm = new RatesModel();
		assertEquals("rate should initialized to 0 by empty constructor", 0, _rm.getRate());
		_rm.setRate(100);
		assertEquals("rate should have changed", 100, _rm.getRate());
	}
	
	@Test
	public void testRatesCreatedBy() {
		// new() -> _rm -> _rm.setCreatedBy()
		RatesModel _rm = new RatesModel();
		assertNull("createdBy should not be set by empty constructor", _rm.getCreatedBy());
		_rm.setCreatedBy("MY_NAME");
		assertEquals("createdBy should have changed", "MY_NAME", _rm.getCreatedBy());	
	}
	
	@Test
	public void testRatesCreatedAt() {
		// new() -> _rm -> _rm.setCreatedAt()
		RatesModel _rm = new RatesModel();
		assertNull("createdAt should not be set by empty constructor", _rm.getCreatedAt());
		_rm.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _rm.getCreatedAt());
	}
		
	@Test
	public void testRatesModifiedBy() {
		// new() -> _rm -> _rm.setModifiedBy()
		RatesModel _rm = new RatesModel();
		assertNull("modifiedBy should not be set by empty constructor", _rm.getModifiedBy());
		_rm.setModifiedBy("MY_NAME");
		assertEquals("modifiedBy should have changed", "MY_NAME", _rm.getModifiedBy());	
	}
	
	@Test
	public void testRatesModifiedAt() {
		// new() -> _rm -> _rm.setModifiedAt()
		RatesModel _rm = new RatesModel();
		assertNull("modifiedAt should not be set by empty constructor", _rm.getModifiedAt());
		_rm.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _rm.getModifiedAt());
	}

	@Test
	public void testRatesModelCurrencyAttributeChange() {
		// new() -> _rm -> _rm.setCurrency()
		RatesModel _rm = new RatesModel();
		assertNull("currency should not be set by empty constructor", _rm.getCurrency());
		_rm.setCurrency(Currency.getDefaultCurrency());
		assertEquals("currency should have changed to default currency", Currency.getDefaultCurrency(), _rm.getCurrency());
		assertEquals("default currency should be correct", Currency.CHF, Currency.getDefaultCurrency());
		_rm.setCurrency(Currency.getCurrency("Euro"));
		assertEquals("currency should be correct", Currency.EUR, _rm.getCurrency());
		assertEquals("label should be correct", Currency.EUR.getLabel(), _rm.getCurrency().getLabel());
		assertEquals("label should be correct", "Euro", Currency.EUR.getLabel());
		assertEquals("stringified value should be correct", Currency.EUR.toString(), _rm.getCurrency().toString());
		_rm.setCurrency(Currency.getCurrency("Swiss Franc"));
		assertEquals("currency should be correct", Currency.CHF, _rm.getCurrency());
		assertEquals("label should be correct", Currency.CHF.getLabel(), _rm.getCurrency().getLabel());
		assertEquals("label should be correct", "Swiss Franc", Currency.CHF.getLabel());
		assertEquals("stringified value should be correct", Currency.CHF.toString(), _rm.getCurrency().toString());
		_rm.setCurrency(Currency.getCurrency("US Dollar"));
		assertEquals("currency should be correct", Currency.USD, _rm.getCurrency());
		assertEquals("label should be correct", Currency.USD.getLabel(), _rm.getCurrency().getLabel());
		assertEquals("label should be correct", "US Dollar", Currency.USD.getLabel());
		assertEquals("stringified value should be correct", Currency.USD.toString(), _rm.getCurrency().toString());
	}

	@Test
	public void testRatesModelDescriptionAttributeChange() {
		// new() -> _rm -> _rm.setDescription()
		RatesModel _rm = new RatesModel();
		assertNull("description should not be set by empty constructor", _rm.getDescription());
		_rm.setDescription("MY_DESC");
		assertEquals("description should have changed", "MY_DESC", _rm.getDescription());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testRateCreateReadDeleteWithEmptyConstructor() {
		// new() -> _rm1
		RatesModel _rm1 = new RatesModel();
		assertNull("id should not be set by empty constructor", _rm1.getId());
		assertNull("title should not be set by empty constructor", _rm1.getTitle());
		assertEquals("rate should initialized to 0 by empty constructor", 0, _rm1.getRate());
		assertNull("currency should not be set by empty constructor", _rm1.getCurrency());
		assertNull("description should not be set by empty constructor", _rm1.getDescription());
		
		// create(_rm1) -> BAD_REQUEST (because of empty title)
		Response _response = rateWC.replacePath("/").post(_rm1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_rm1.setTitle("testRateCreateReadDeleteWithEmptyConstructor");

		// create(_rm1) -> _rm2
		_response = rateWC.replacePath("/").post(_rm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _rm2 = _response.readEntity(RatesModel.class);
		
		// validate _rm1
		assertNull("create() should not change the id of the local object", _rm1.getId());
		assertEquals("create() should not change the title of the local object", "testRateCreateReadDeleteWithEmptyConstructor", _rm1.getTitle());
		assertEquals("create() should not change the rate of the local object", 0, _rm1.getRate());
		assertNull("create() should not change the currency of the local object", _rm1.getCurrency());
		assertNull("create() should not change the description of the local object", _rm1.getDescription());
		
		// validate _rm2
		assertNotNull("create() should set a valid id on the remote object returned", _rm2.getId());
		assertEquals("create() should not change the title", "testRateCreateReadDeleteWithEmptyConstructor", _rm2.getTitle());
		assertEquals("create() should set the default currency", Currency.getDefaultCurrency().getLabel(), _rm2.getCurrency().getLabel());
		assertEquals("create() should set the rate to its default value", 0, _rm2.getRate());
		assertNull("create() should not change the description", _rm2.getDescription());
		
		// read(_rm2) -> _rm3
		_response = rateWC.replacePath("/").path(_rm2.getId()).get();
		assertEquals("read(" + _rm2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _rm3 = _response.readEntity(RatesModel.class);
		assertEquals("id of returned object should be the same", _rm2.getId(), _rm3.getId());
		assertEquals("title of returned object should be unchanged", _rm2.getTitle(), _rm3.getTitle());
		assertEquals("rate of returned object should be unchanged", _rm2.getRate(), _rm3.getRate());
		assertEquals("currency of returned object should be unchanged", _rm2.getCurrency(), _rm3.getCurrency());
		assertEquals("description of returned object should be unchanged", _rm2.getDescription(), _rm3.getDescription());
		// delete(_c3)
		_response = rateWC.replacePath("/").path(_rm3.getId()).delete();
		assertEquals("delete(" + _rm3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testRateCreateReadDelete() {
		// new("testRateCreateReadDelete1", 100, "MY_DESC") -> _rm1
		RatesModel _rm1 = new RatesModel("testRateCreateReadDelete", 100, "MY_DESC");
		assertNull("id should not be set by constructor", _rm1.getId());
		assertEquals("title should be set by constructor", "testRateCreateReadDelete", _rm1.getTitle());
		assertEquals("description should be set by constructor", "MY_DESC", _rm1.getDescription());
		// create(MY_DESC) -> _rm2
		Response _response = rateWC.replacePath("/").post(_rm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _rm2 = _response.readEntity(RatesModel.class);
		assertNull("id should be still null after remote create", _rm1.getId());
		assertEquals("title should be unchanged after remote create", "testRateCreateReadDelete", _rm1.getTitle());
		assertEquals("rate should be unchanged after remote create", 100, _rm1.getRate());
		assertEquals("currency should be unchanged after remote create", Currency.getDefaultCurrency(), _rm1.getCurrency());
		assertEquals("description should be unchanged after remote create", "MY_DESC", _rm1.getDescription());
		assertNotNull("id of returned object should be set", _rm2.getId());
		assertEquals("title of returned object should be unchanged after remote create", "testRateCreateReadDelete", _rm2.getTitle());
		assertEquals("rate should be unchanged after remote create", 100, _rm1.getRate());
		assertEquals("currency should be unchanged after remote create", Currency.getDefaultCurrency(), _rm1.getCurrency());
		assertEquals("description of returned object should be unchanged after remote create", "MY_DESC", _rm2.getDescription());
		// read(_rm2)  -> _rm3
		_response = rateWC.replacePath("/").path(_rm2.getId()).get();
		assertEquals("read(" + _rm2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _rm3 = _response.readEntity(RatesModel.class);
		assertEquals("id of returned object should be the same", _rm2.getId(), _rm3.getId());
		assertEquals("title of returned object should be unchanged after remote create", _rm2.getTitle(), _rm3.getTitle());
		assertEquals("rate should be unchanged after remote create", _rm2.getRate(), _rm3.getRate());
		assertEquals("currency should be unchanged after remote create", _rm2.getCurrency(), _rm3.getCurrency());
		assertEquals("description of returned object should be unchanged after remote create", _rm2.getDescription(), _rm3.getDescription());
		// delete(_c3)
		_response = rateWC.replacePath("/").path(_rm3.getId()).delete();
		assertEquals("delete(" + _rm3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateRateWithClientSideId() {
		// new() -> _rm -> _rm.setId()
		RatesModel _rm = new RatesModel("testCreateRateWithClientSideId", 100, "MY_DESC");
		_rm.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _rm.getId());
		// create(_rm) -> BAD_REQUEST
		Response _response = rateWC.replacePath("/").post(_rm);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateRateWithDuplicateId() {
		// create(new()) -> _rm1
		Response _response = rateWC.replacePath("/").post(new RatesModel("testCreateRateWithDuplicateId", 100, "MY_DESC"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _rm1 = _response.readEntity(RatesModel.class);

		// new() -> _rm2 -> _rm2.setId(_rm1.getId())
		RatesModel _rm2 = new RatesModel("DuplicateRatesModel", 100, "MY_DESC");
		_rm2.setId(_rm1.getId());		// wrongly create a 2nd RatesModel object with the same ID
		
		// create(_rm2) -> CONFLICT
		_response = rateWC.replacePath("/").post(_rm2);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_rm1)
		_response = rateWC.replacePath("/").path(_rm1.getId()).delete();
		assertEquals("delete(" + _rm1.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

	}
	
	@Test
	public void testRateList(
	) {		
		ArrayList<RatesModel> _localList = new ArrayList<RatesModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			// create(new()) -> _localList
			_response = rateWC.replacePath("/").post(new RatesModel("testRateList" + i, 100 + i, "MY_DESC"));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(RatesModel.class));
		}
		
		// list(/) -> _remoteList
		_response = rateWC.replacePath("/").get();
		List<RatesModel> _remoteList = new ArrayList<RatesModel>(rateWC.getCollection(RatesModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (RatesModel _rm : _remoteList) {
			_remoteListIds.add(_rm.getId());
		}
		
		for (RatesModel _rm : _localList) {
			assertTrue("rate <" + _rm.getId() + "> should be listed", _remoteListIds.contains(_rm.getId()));
		}
		for (RatesModel _rm : _localList) {
			_response = rateWC.replacePath("/").path(_rm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(RatesModel.class);
		}
		for (RatesModel _rm : _localList) {
			_response = rateWC.replacePath("/").path(_rm.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}
		
	@Test
	public void testRateCreate() {
		// new("MY_TITLE", "MY_DESC") -> _rm1
		RatesModel _rm1 = new RatesModel("testRateCreate1", 100, "MY_DESC1");
		// new("MY_TITLE2", "MY_DESC2") -> _c2
		RatesModel _rm2 = new RatesModel("testRateCreate2", 200, "MY_DESC2");
		
		// create(_rm1)  -> _rm3
		Response _response = rateWC.replacePath("/").post(_rm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _rm3 = _response.readEntity(RatesModel.class);

		// create(_rm2) -> _rm4
		_response = rateWC.replacePath("/").post(_rm2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _rm4 = _response.readEntity(RatesModel.class);		
		assertNotNull("ID should be set", _rm3.getId());
		assertNotNull("ID should be set", _rm4.getId());
		assertThat(_rm4.getId(), not(equalTo(_rm3.getId())));
		assertEquals("title should be set correctly", "testRateCreate1", _rm3.getTitle());
		assertEquals("description should be set correctly", "MY_DESC1", _rm3.getDescription());
		assertEquals("rate should be set by constructor", 100, _rm3.getRate());
		assertEquals("currency should be set to default value by constructor", 
				Currency.getDefaultCurrency(), _rm3.getCurrency());
		assertEquals("title should be set correctly", "testRateCreate2", _rm4.getTitle());
		assertEquals("description should be set correctly", "MY_DESC2", _rm4.getDescription());
		assertEquals("rate should be set by constructor", 200, _rm4.getRate());
		assertEquals("currency should be set to default value by constructor", 
				Currency.getDefaultCurrency(), _rm4.getCurrency());

		// delete(_rm3) -> NO_CONTENT
		_response = rateWC.replacePath("/").path(_rm3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_rm4) -> NO_CONTENT
		_response = rateWC.replacePath("/").path(_rm4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testRateDoubleCreate(
	) {
		// create() -> _rm
		Response _response = rateWC.replacePath("/").post(new RatesModel("testRateDoubleCreate", 100, "MY_DESC"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _rm = _response.readEntity(RatesModel.class);
		assertNotNull("ID should be set:", _rm.getId());		
		
		// create(_rm) -> CONFLICT
		_response = rateWC.replacePath("/").post(_rm);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_rm) -> NO_CONTENT
		_response = rateWC.replacePath("/").path(_rm.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testRateRead(
	) {
		ArrayList<RatesModel> _localList = new ArrayList<RatesModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			_response = rateWC.replacePath("/").post(new RatesModel("testRateRead" + i, 100 + i, "MY_DESC"));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(RatesModel.class));
		}
	
		// test read on each local element
		for (RatesModel _rm : _localList) {
			_response = rateWC.replacePath("/").path(_rm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(RatesModel.class);
		}

		// test read on each listed element
		_response = rateWC.replacePath("/").get();
		List<RatesModel> _remoteList = new ArrayList<RatesModel>(rateWC.getCollection(RatesModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		RatesModel _tmpObj = null;
		for (RatesModel _rm : _remoteList) {
			_response = rateWC.replacePath("/").path(_rm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(RatesModel.class);
			assertEquals("ID should be unchanged when reading a rate", _rm.getId(), _tmpObj.getId());						
		}

		for (RatesModel _rm : _localList) {
			_response = rateWC.replacePath("/").path(_rm.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}	

	@Test
	public void testRateMultiRead(
	) {
		// create() -> _rm1
		Response _response = rateWC.replacePath("/").post(new RatesModel("testRateMultiRead", 100, "MY_DESC"));
		RatesModel _rm1 = _response.readEntity(RatesModel.class);

		// read(_rm1) -> _rm2
		_response = rateWC.replacePath("/").path(_rm1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _rm2 = _response.readEntity(RatesModel.class);
		assertEquals("ID should be unchanged after read:", _rm1.getId(), _rm2.getId());		

		// read(_rm1) -> _rm4
		_response = rateWC.replacePath("/").path(_rm1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _rm4 = _response.readEntity(RatesModel.class);
		
		// but: the two objects are not equal !
		assertEquals("ID should be the same:", _rm2.getId(), _rm4.getId());
		assertEquals("title should be the same:", _rm2.getTitle(), _rm4.getTitle());
		assertEquals("description should be the same:", _rm2.getDescription(), _rm4.getDescription());
		
		assertEquals("ID should be the same:", _rm2.getId(), _rm1.getId());
		assertEquals("title should be the same:", _rm2.getTitle(), _rm1.getTitle());
		assertEquals("description should be the same:", _rm2.getDescription(), _rm1.getDescription());
		
		// delete(_rm1)
		_response = rateWC.replacePath("/").path(_rm1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testRateUpdate(
	) {
		// create() -> _rm1		
		Response _response = rateWC.replacePath("/").post(new RatesModel("testRateUpdate", 100, "MY_DESC"));
		RatesModel _rm1 = _response.readEntity(RatesModel.class);
		
		// change the attributes
		// update(_rm1) -> _rm2
		_rm1.setTitle("testRateUpdate");
		_rm1.setRate(300);
		_rm1.setCurrency(Currency.USD);
		_rm1.setDescription("testRateUpdate1");
		rateWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = rateWC.replacePath("/").path(_rm1.getId()).put(_rm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _rm2 = _response.readEntity(RatesModel.class);

		assertNotNull("ID should be set", _rm2.getId());
		assertEquals("ID should be unchanged", _rm1.getId(), _rm2.getId());	
		assertEquals("title should have changed", "testRateUpdate", _rm2.getTitle());
		assertEquals("rate should have changed", 300, _rm2.getRate());
		assertEquals("currency should have changed", Currency.USD, _rm2.getCurrency());
		assertEquals("description should have changed", "testRateUpdate1", _rm2.getDescription());

		// reset the attributes
		// update(_rm1) -> _rm3
		_rm1.setTitle("testRateUpdate2");
		_rm1.setRate(400);
		_rm1.setCurrency(Currency.EUR);
		_rm1.setDescription("testRateUpdate3");
		rateWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = rateWC.replacePath("/").path(_rm1.getId()).put(_rm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _rm3 = _response.readEntity(RatesModel.class);

		assertNotNull("ID should be set", _rm3.getId());
		assertEquals("ID should be unchanged", _rm1.getId(), _rm3.getId());	
		assertEquals("title should have changed", "testRateUpdate2", _rm3.getTitle());
		assertEquals("rate should have changed", 400, _rm3.getRate());
		assertEquals("currency should have changed", Currency.EUR, _rm3.getCurrency());
		assertEquals("description should have changed", "testRateUpdate3", _rm3.getDescription());
		
		_response = rateWC.replacePath("/").path(_rm1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testRateDelete(
	) {
		// create() -> _rm1
		Response _response = rateWC.replacePath("/").post(new RatesModel("testRateDelete", 100, "MY_DESC"));
		RatesModel _rm1 = _response.readEntity(RatesModel.class);
		
		// read(_rm1) -> _tmpObj
		_response = rateWC.replacePath("/").path(_rm1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _tmpObj = _response.readEntity(RatesModel.class);
		assertEquals("ID should be unchanged when reading a rate (remote):", _rm1.getId(), _tmpObj.getId());						

		// delete(_rm1) -> OK
		_response = rateWC.replacePath("/").path(_rm1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	
		// read the deleted object twice
		// read(_rm1) -> NOT_FOUND
		_response = rateWC.replacePath("/").path(_rm1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read(_rm1) -> NOT_FOUND
		_response = rateWC.replacePath("/").path(_rm1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testRateDoubleDelete(
	) {
		// new() -> _rm1
		RatesModel _rm1 = new RatesModel("testRateDoubleDelete", 100, "MY_DESC");
		
		// create(_rm1) -> _rm2
		Response _response = rateWC.replacePath("/").post(_rm1);
		RatesModel _rm2 = _response.readEntity(RatesModel.class);

		// read(_rm2) -> OK
		_response = rateWC.replacePath("/").path(_rm2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		// delete(_rm2) -> OK
		_response = rateWC.replacePath("/").path(_rm2.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		// read(_rm2) -> NOT_FOUND
		_response = rateWC.replacePath("/").path(_rm2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// delete _rm2 -> NOT_FOUND
		_response = rateWC.replacePath("/").path(_rm2.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read _rm2 -> NOT_FOUND
		_response = rateWC.replacePath("/").path(_rm2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testRatesModifications() {
		// create(new RatesModel()) -> _rm1
		Response _response = rateWC.replacePath("/").post(new RatesModel("testRatesModifications", 100, "MY_DESC"));
		RatesModel _rm1 = _response.readEntity(RatesModel.class);
		System.out.println("created RatesModel " + _rm1.getId());
		
		// test createdAt and createdBy
		assertNotNull("create() should set createdAt", _rm1.getCreatedAt());
		assertNotNull("create() should set createdBy", _rm1.getCreatedBy());
		// test modifiedAt and modifiedBy (= same as createdAt/createdBy)
		assertNotNull("create() should set modifiedAt", _rm1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _rm1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _rm1.getCreatedAt(), _rm1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _rm1.getCreatedBy(), _rm1.getModifiedBy());
		try {
			System.out.println("sleeping for 3 secs"); // in order to get different modifiedAt Dates
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// update(_rm1)  -> _rm2
		_rm1.setTitle("testRatesModifications1");
		rateWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = rateWC.replacePath("/").path(_rm1.getId()).put(_rm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _rm2 = _response.readEntity(RatesModel.class);
		System.out.println("updated RatesModel " + _rm2.getId());

		// test createdAt and createdBy (unchanged)
		assertEquals("update() should not change createdAt", _rm1.getCreatedAt(), _rm2.getCreatedAt());
		assertEquals("update() should not change createdBy", _rm1.getCreatedBy(), _rm2.getCreatedBy());
		
		// test modifiedAt and modifiedBy (= different from createdAt/createdBy)
		System.out.println("comparing " + _rm2.getModifiedAt().toString() + " with " + _rm2.getCreatedAt().toString());
		assertThat(_rm2.getModifiedAt().toString(), not(equalTo(_rm2.getCreatedAt().toString())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_rm2.getModifiedBy(), not(equalTo(_rm2.getCreatedBy())));

		// update(_rm2) with createdBy set on client side -> ignored
		String _createdBy = _rm1.getCreatedBy();
		_rm1.setCreatedBy("testRatesModifications2");
		rateWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = rateWC.replacePath("/").path(_rm1.getId()).put(_rm1);
		assertEquals("update() should ignore client-side generated createdBy", 
				Status.OK.getStatusCode(), _response.getStatus());
		_rm1.setCreatedBy(_createdBy);

		// update(_rm1) with createdAt set on client side -> ignored
		Date _d = _rm1.getCreatedAt();
		_rm1.setCreatedAt(new Date(1000));
		rateWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = rateWC.replacePath("/").path(_rm1.getId()).put(_rm1);
		assertEquals("update() should ignore client-side generated createdAt", 
				Status.OK.getStatusCode(), _response.getStatus());
		_rm1.setCreatedAt(_d);

		// update(_rm1) with modifiedBy/At set on client side -> ignored by server
		_rm1.setModifiedBy("testRatesModifications3");
		_rm1.setModifiedAt(new Date(1000));
		rateWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = rateWC.replacePath("/").path(_rm1.getId()).put(_rm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _o3 = _response.readEntity(RatesModel.class);
		System.out.println("updated RatesModel " + _o3.getId());
	
		// test, that modifiedBy really ignored the client-side value "MYSELF"
		assertThat(_rm1.getModifiedBy(), not(equalTo(_o3.getModifiedBy())));
		// check whether the client-side modifiedAt() is ignored
		assertThat(_rm1.getModifiedAt(), not(equalTo(_o3.getModifiedAt())));
		
		// delete(_rm1) -> NO_CONTENT
		_response = rateWC.replacePath("/").path(_rm1.getId()).delete();		
		System.out.println("deleted RatesModel " + _rm1.getId());
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	/********************************* helper methods *********************************/	
	public static WebClient createRatesWebClient() {
		return createWebClient(createUrl(DEFAULT_BASE_URL, API_URL), RatesService.class);
	}
	
	public static RatesModel createRate(
			WebClient rateWC, 
			String title, 
			int rate,
			Currency currency,
			String description) 
	{
		RatesModel _rm = new RatesModel();
		_rm.setTitle(title);
		_rm.setRate(rate);
		_rm.setCurrency(currency);
		_rm.setDescription(description);
		Response _response = rateWC.replacePath("/").post(_rm);
		return _response.readEntity(RatesModel.class);
	}
	
	public static void cleanup(
			WebClient rateWC,
			String rateId,
			String testName) {
		cleanup(rateWC, rateId, testName, true);
	}
		
	public static void cleanup(
			WebClient rateWC,
			String rateId,
			String testName,
			boolean closeWC) {
		rateWC.replacePath("/").path(rateId).delete();
		System.out.println(testName + " deleted rate <" + rateId + ">.");
		if (closeWC) {
			rateWC.close();
		}
	}	
}
