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

import org.junit.Before;
import org.junit.Test;
import org.opentdc.rates.Currency;
import org.opentdc.rates.RatesModel;
import org.opentdc.rates.RatesService;

import test.org.opentdc.AbstractTestClient;

public class RatesTest extends AbstractTestClient<RatesService> {

	private static final String API = "api/rate/";

	@Before
	public void initializeTest(
	) {
		initializeTest(API, RatesService.class);
	}
	
	/********************************** rates attributes tests *********************************/	
	@Test
	public void testRatesModelEmptyConstructor() {
		// new() -> _c
		RatesModel _c = new RatesModel();
		assertNull("id should not be set by empty constructor", _c.getId());
		assertNull("title should not be set by empty constructor", _c.getTitle());
		assertEquals("rate should be initialized to 0 by empty constructor", 0, _c.getRate());
		assertNull("currency should not be set by empty constructor", _c.getCurrency());
		assertNull("description should not be set by empty constructor", _c.getDescription());
	}
	
	@Test
	public void testRatesModelConstructor() {		
		// new("MY_TITLE", "MY_DESC") -> _c
		RatesModel _c = new RatesModel("MY_TITLE", 100, "MY_DESC");
		assertNull("id should not be set by constructor", _c.getId());
		assertEquals("title should be set by constructor", "MY_TITLE", _c.getTitle());
		assertEquals("rate should be set by constructor", 100, _c.getRate());
		assertEquals("currency should be set to default value by constructor", 
				Currency.getDefaultCurrency(), _c.getCurrency());
		assertEquals("description should not set by constructor", "MY_DESC", _c.getDescription());
	}
	
	@Test
	public void testRatesModelIdAttributeChange() {
		// new() -> _c -> _c.setId()
		RatesModel _c = new RatesModel();
		assertNull("id should not be set by constructor", _c.getId());
		_c.setId("MY_ID");
		assertEquals("id should have changed", "MY_ID", _c.getId());
	}

	@Test
	public void testRatesModelTitleAttributeChange() {
		// new() -> _c -> _c.setTitle()
		RatesModel _c = new RatesModel();
		assertNull("title should not be set by empty constructor", _c.getTitle());
		_c.setTitle("MY_TITLE");
		assertEquals("title should have changed", "MY_TITLE", _c.getTitle());
	}
	
	@Test
	public void testRatesModelRateAttributeChange() {
		// new() -> _c -> _c.setRate()
		RatesModel _c = new RatesModel();
		assertEquals("rate should initialized to 0 by empty constructor", 0, _c.getRate());
		_c.setRate(100);
		assertEquals("rate should have changed", 100, _c.getRate());
	}
	
	@Test
	public void testRatesCreatedBy() {
		// new() -> _o -> _o.setCreatedBy()
		RatesModel _o = new RatesModel();
		assertNull("createdBy should not be set by empty constructor", _o.getCreatedBy());
		_o.setCreatedBy("MY_NAME");
		assertEquals("createdBy should have changed", "MY_NAME", _o.getCreatedBy());	
	}
	
	@Test
	public void testRatesCreatedAt() {
		// new() -> _o -> _o.setCreatedAt()
		RatesModel _o = new RatesModel();
		assertNull("createdAt should not be set by empty constructor", _o.getCreatedAt());
		_o.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _o.getCreatedAt());
	}
		
	@Test
	public void testRatesModifiedBy() {
		// new() -> _o -> _o.setModifiedBy()
		RatesModel _o = new RatesModel();
		assertNull("modifiedBy should not be set by empty constructor", _o.getModifiedBy());
		_o.setModifiedBy("MY_NAME");
		assertEquals("modifiedBy should have changed", "MY_NAME", _o.getModifiedBy());	
	}
	
	@Test
	public void testRatesModifiedAt() {
		// new() -> _o -> _o.setModifiedAt()
		RatesModel _o = new RatesModel();
		assertNull("modifiedAt should not be set by empty constructor", _o.getModifiedAt());
		_o.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _o.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testRatesModelCurrencyAttributeChange() {
		// new() -> _c -> _c.setCurrency()
		RatesModel _c = new RatesModel();
		assertNull("currency should not be set by empty constructor", _c.getCurrency());
		_c.setCurrency(Currency.getDefaultCurrency());
		assertEquals("currency should have changed to default currency", Currency.getDefaultCurrency(), _c.getCurrency());
		assertEquals("default currency should be correct", Currency.CHF, Currency.getDefaultCurrency());
		_c.setCurrency(Currency.getCurrency("Euro"));
		assertEquals("currency should be correct", Currency.EUR, _c.getCurrency());
		assertEquals("label should be correct", Currency.EUR.getLabel(), _c.getCurrency().getLabel());
		assertEquals("label should be correct", "Euro", Currency.EUR.getLabel());
		assertEquals("stringified value should be correct", Currency.EUR.toString(), _c.getCurrency().toString());
		_c.setCurrency(Currency.getCurrency("Swiss Franc"));
		assertEquals("currency should be correct", Currency.CHF, _c.getCurrency());
		assertEquals("label should be correct", Currency.CHF.getLabel(), _c.getCurrency().getLabel());
		assertEquals("label should be correct", "Swiss Franc", Currency.CHF.getLabel());
		assertEquals("stringified value should be correct", Currency.CHF.toString(), _c.getCurrency().toString());
		_c.setCurrency(Currency.getCurrency("US Dollar"));
		assertEquals("currency should be correct", Currency.USD, _c.getCurrency());
		assertEquals("label should be correct", Currency.USD.getLabel(), _c.getCurrency().getLabel());
		assertEquals("label should be correct", "US Dollar", Currency.USD.getLabel());
		assertEquals("stringified value should be correct", Currency.USD.toString(), _c.getCurrency().toString());
	}

	@Test
	public void testRatesModelDescriptionAttributeChange() {
		// new() -> _c -> _c.setDescription()
		RatesModel _c = new RatesModel();
		assertNull("description should not be set by empty constructor", _c.getDescription());
		_c.setDescription("MY_DESC");
		assertEquals("description should have changed", "MY_DESC", _c.getDescription());
	}

	@Test
	public void testRateCreateReadDeleteWithEmptyConstructor() {
		// new() -> _c1
		RatesModel _c1 = new RatesModel();
		assertNull("id should not be set by empty constructor", _c1.getId());
		assertNull("title should not be set by empty constructor", _c1.getTitle());
		assertEquals("rate should initialized to 0 by empty constructor", 0, _c1.getRate());
		assertNull("currency should not be set by empty constructor", _c1.getCurrency());
		assertNull("description should not be set by empty constructor", _c1.getDescription());
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _c2 = _response.readEntity(RatesModel.class);
		assertNull("create() should not change the id of the local object", _c1.getId());
		assertNull("create() should not change the title of the local object", _c1.getTitle());
		assertEquals("create() should not change the rate of the local object", 0, _c1.getRate());
		assertNull("create() should not change the currency of the local object", _c1.getCurrency());
		assertNull("create() should not change the description of the local object", _c1.getDescription());
		
		assertNotNull("create() should set a valid id on the remote object returned", _c2.getId());
		assertNull("title of returned object should still be null after remote create", _c2.getTitle());
		assertNull("description of returned object should still be null after remote create", _c2.getDescription());
		// read(_c2) -> _c3
		_response = webclient.replacePath("/").path(_c2.getId()).get();
		assertEquals("read(" + _c2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _c3 = _response.readEntity(RatesModel.class);
		assertEquals("id of returned object should be the same", _c2.getId(), _c3.getId());
		assertEquals("title of returned object should be unchanged after remote create", _c2.getTitle(), _c3.getTitle());
		assertEquals("rate of returned object should be unchanged after remote create", _c2.getRate(), _c3.getRate());
		assertEquals("currency of returned object should be unchanged after remote create", _c2.getCurrency(), _c3.getCurrency());
		assertEquals("description of returned object should be unchanged after remote create", _c2.getDescription(), _c3.getDescription());
		// delete(_c3)
		_response = webclient.replacePath("/").path(_c3.getId()).delete();
		assertEquals("delete(" + _c3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testRateCreateReadDelete() {
		// new("testRateCreateReadDelete1", 100, "MY_DESC") -> _c1
		RatesModel _c1 = new RatesModel("testRateCreateReadDelete1", 100, "testRateCreateReadDelete2");
		assertNull("id should not be set by constructor", _c1.getId());
		assertEquals("title should be set by constructor", "testRateCreateReadDelete1", _c1.getTitle());
		assertEquals("description should be set by constructor", "testRateCreateReadDelete2", _c1.getDescription());
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _c2 = _response.readEntity(RatesModel.class);
		assertNull("id should be still null after remote create", _c1.getId());
		assertEquals("title should be unchanged after remote create", "testRateCreateReadDelete1", _c1.getTitle());
		assertEquals("rate should be unchanged after remote create", 100, _c1.getRate());
		assertEquals("currency should be unchanged after remote create", Currency.getDefaultCurrency(), _c1.getCurrency());
		assertEquals("description should be unchanged after remote create", "testRateCreateReadDelete2", _c1.getDescription());
		assertNotNull("id of returned object should be set", _c2.getId());
		assertEquals("title of returned object should be unchanged after remote create", "testRateCreateReadDelete1", _c2.getTitle());
		assertEquals("rate should be unchanged after remote create", 100, _c1.getRate());
		assertEquals("currency should be unchanged after remote create", Currency.getDefaultCurrency(), _c1.getCurrency());
		assertEquals("description of returned object should be unchanged after remote create", "testRateCreateReadDelete2", _c2.getDescription());
		// read(_c2)  -> _c3
		_response = webclient.replacePath("/").path(_c2.getId()).get();
		assertEquals("read(" + _c2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _c3 = _response.readEntity(RatesModel.class);
		assertEquals("id of returned object should be the same", _c2.getId(), _c3.getId());
		assertEquals("title of returned object should be unchanged after remote create", _c2.getTitle(), _c3.getTitle());
		assertEquals("rate should be unchanged after remote create", _c2.getRate(), _c3.getRate());
		assertEquals("currency should be unchanged after remote create", _c2.getCurrency(), _c3.getCurrency());
		assertEquals("description of returned object should be unchanged after remote create", _c2.getDescription(), _c3.getDescription());
		// delete(_c3)
		_response = webclient.replacePath("/").path(_c3.getId()).delete();
		assertEquals("delete(" + _c3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateRateWithClientSideId() {
		// new() -> _c1 -> _c1.setId()
		RatesModel _c1 = new RatesModel();
		_c1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _c1.getId());
		// create(_c1) -> BAD_REQUEST
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateRateWithDuplicateId() {
		// create(new()) -> _c2
		Response _response = webclient.replacePath("/").post(new RatesModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _c2 = _response.readEntity(RatesModel.class);

		// new() -> _c3 -> _c3.setId(_c2.getId())
		RatesModel _c3 = new RatesModel();
		_c3.setId(_c2.getId());		// wrongly create a 2nd RatesModel object with the same ID
		
		// create(_c3) -> CONFLICT
		_response = webclient.replacePath("/").post(_c3);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_c2)
		_response = webclient.replacePath("/").path(_c2.getId()).delete();
		assertEquals("delete(" + _c2.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

	}
	
	@Test
	public void testRateList(
	) {		
		ArrayList<RatesModel> _localList = new ArrayList<RatesModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			// create(new()) -> _localList
			_response = webclient.replacePath("/").post(new RatesModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(RatesModel.class));
		}
		
		// list(/) -> _remoteList
		_response = webclient.replacePath("/").get();
		List<RatesModel> _remoteList = new ArrayList<RatesModel>(webclient.getCollection(RatesModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (RatesModel _rm : _remoteList) {
			_remoteListIds.add(_rm.getId());
		}
		
		for (RatesModel _rm : _localList) {
			assertTrue("rate <" + _rm.getId() + "> should be listed", _remoteListIds.contains(_rm.getId()));
		}
		for (RatesModel _rm : _localList) {
			_response = webclient.replacePath("/").path(_rm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(RatesModel.class);
		}
		for (RatesModel _rm : _localList) {
			_response = webclient.replacePath("/").path(_rm.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}
		
	@Test
	public void testRateCreate() {
		// new("MY_TITLE", "MY_DESC") -> _c1
		RatesModel _c1 = new RatesModel("testRateCreate1", 100, "testRateCreate2");
		// new("MY_TITLE2", "MY_DESC2") -> _c2
		RatesModel _c2 = new RatesModel("testRateCreate3", 200, "testRateCreate4");
		
		// create(_c1)  -> _c3
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _c3 = _response.readEntity(RatesModel.class);

		// create(_c2) -> _c4
		_response = webclient.replacePath("/").post(_c2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _c4 = _response.readEntity(RatesModel.class);		
		assertNotNull("ID should be set", _c3.getId());
		assertNotNull("ID should be set", _c4.getId());
		assertThat(_c4.getId(), not(equalTo(_c3.getId())));
		assertEquals("title1 should be set correctly", "testRateCreate1", _c3.getTitle());
		assertEquals("description1 should be set correctly", "testRateCreate2", _c3.getDescription());
		assertEquals("rate1 should be set by constructor", 100, _c3.getRate());
		assertEquals("currency1 should be set to default value by constructor", 
				Currency.getDefaultCurrency(), _c3.getCurrency());
		assertEquals("title2 should be set correctly", "testRateCreate3", _c4.getTitle());
		assertEquals("description2 should be set correctly", "testRateCreate4", _c4.getDescription());
		assertEquals("rate2 should be set by constructor", 200, _c4.getRate());
		assertEquals("currency2 should be set to default value by constructor", 
				Currency.getDefaultCurrency(), _c4.getCurrency());

		// delete(_c3) -> NO_CONTENT
		_response = webclient.replacePath("/").path(_c3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_c4) -> NO_CONTENT
		_response = webclient.replacePath("/").path(_c4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testRateDoubleCreate(
	) {
		// create(new()) -> _c
		Response _response = webclient.replacePath("/").post(new RatesModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _c = _response.readEntity(RatesModel.class);
		assertNotNull("ID should be set:", _c.getId());		
		
		// create(_c) -> CONFLICT
		_response = webclient.replacePath("/").post(_c);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_c) -> NO_CONTENT
		_response = webclient.replacePath("/").path(_c.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testRateRead(
	) {
		ArrayList<RatesModel> _localList = new ArrayList<RatesModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			_response = webclient.replacePath("/").post(new RatesModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(RatesModel.class));
		}
	
		// test read on each local element
		for (RatesModel _c : _localList) {
			_response = webclient.replacePath("/").path(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(RatesModel.class);
		}

		// test read on each listed element
		_response = webclient.replacePath("/").get();
		List<RatesModel> _remoteList = new ArrayList<RatesModel>(webclient.getCollection(RatesModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		RatesModel _tmpObj = null;
		for (RatesModel _c : _remoteList) {
			_response = webclient.replacePath("/").path(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(RatesModel.class);
			assertEquals("ID should be unchanged when reading a rate", _c.getId(), _tmpObj.getId());						
		}

		for (RatesModel _c : _localList) {
			_response = webclient.replacePath("/").path(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}	

	@Test
	public void testRateMultiRead(
	) {
		// new() -> _c1
		RatesModel _c1 = new RatesModel();
		
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		RatesModel _c2 = _response.readEntity(RatesModel.class);

		// read(_c2) -> _c3
		_response = webclient.replacePath("/").path(_c2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _c3 = _response.readEntity(RatesModel.class);
		assertEquals("ID should be unchanged after read:", _c2.getId(), _c3.getId());		

		// read(_c2) -> _c4
		_response = webclient.replacePath("/").path(_c2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _c4 = _response.readEntity(RatesModel.class);
		
		// but: the two objects are not equal !
		assertEquals("ID should be the same:", _c3.getId(), _c4.getId());
		assertEquals("title should be the same:", _c3.getTitle(), _c4.getTitle());
		assertEquals("description should be the same:", _c3.getDescription(), _c4.getDescription());
		
		assertEquals("ID should be the same:", _c3.getId(), _c2.getId());
		assertEquals("title should be the same:", _c3.getTitle(), _c2.getTitle());
		assertEquals("description should be the same:", _c3.getDescription(), _c2.getDescription());
		
		// delete(_c2)
		_response = webclient.replacePath("/").path(_c2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testRateUpdate(
	) {
		// new() -> _c1
		RatesModel _c1 = new RatesModel();
		
		// create(_c1) -> _c2		
		Response _response = webclient.replacePath("/").post(_c1);
		RatesModel _c2 = _response.readEntity(RatesModel.class);
		
		// change the attributes
		// update(_c2) -> _c3
		_c2.setTitle("testRateUpdate");
		_c2.setRate(300);
		_c2.setCurrency(Currency.USD);
		_c2.setDescription("testRateUpdate1");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_c2.getId()).put(_c2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _c3 = _response.readEntity(RatesModel.class);

		assertNotNull("ID should be set", _c3.getId());
		assertEquals("ID should be unchanged", _c2.getId(), _c3.getId());	
		assertEquals("title should have changed", "testRateUpdate", _c3.getTitle());
		assertEquals("rate should have changed", 300, _c3.getRate());
		assertEquals("currency should have changed", Currency.USD, _c3.getCurrency());
		assertEquals("description should have changed", "testRateUpdate1", _c3.getDescription());

		// reset the attributes
		// update(_c2) -> _c4
		_c2.setTitle("testRateUpdate2");
		_c2.setRate(400);
		_c2.setCurrency(Currency.EUR);
		_c2.setDescription("testRateUpdate3");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_c2.getId()).put(_c2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _c4 = _response.readEntity(RatesModel.class);

		assertNotNull("ID should be set", _c4.getId());
		assertEquals("ID should be unchanged", _c2.getId(), _c4.getId());	
		assertEquals("title should have changed", "testRateUpdate2", _c4.getTitle());
		assertEquals("rate should have changed", 400, _c4.getRate());
		assertEquals("currency should have changed", Currency.EUR, _c4.getCurrency());
		assertEquals("description should have changed", "testRateUpdate3", _c4.getDescription());
		
		_response = webclient.replacePath("/").path(_c2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testRateDelete(
	) {
		// new() -> _c0
		RatesModel _c0 = new RatesModel();
		// create(_c0) -> _c1
		Response _response = webclient.replacePath("/").post(_c0);
		RatesModel _c1 = _response.readEntity(RatesModel.class);
		
		// read(_c1) -> _tmpObj
		_response = webclient.replacePath("/").path(_c1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _tmpObj = _response.readEntity(RatesModel.class);
		assertEquals("ID should be unchanged when reading a rate (remote):", _c1.getId(), _tmpObj.getId());						

		// delete(_c1) -> OK
		_response = webclient.replacePath("/").path(_c1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	
		// read the deleted object twice
		// read(_c1) -> NOT_FOUND
		_response = webclient.replacePath("/").path(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read(_c1) -> NOT_FOUND
		_response = webclient.replacePath("/").path(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testRateDoubleDelete(
	) {
		// new() -> _c0
		RatesModel _c0 = new RatesModel();
		
		// create(_c0) -> _c1
		Response _response = webclient.replacePath("/").post(_c0);
		RatesModel _c1 = _response.readEntity(RatesModel.class);

		// read(_c1) -> OK
		_response = webclient.replacePath("/").path(_c1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		// delete(_c1) -> OK
		_response = webclient.replacePath("/").path(_c1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		// read(_c1) -> NOT_FOUND
		_response = webclient.replacePath("/").path(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// delete _c1 -> NOT_FOUND
		_response = webclient.replacePath("/").path(_c1.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read _c1 -> NOT_FOUND
		_response = webclient.replacePath("/").path(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testRatesModifications() {
		// create(new RatesModel()) -> _o
		Response _response = webclient.replacePath("/").post(new RatesModel());
		RatesModel _o = _response.readEntity(RatesModel.class);
		System.out.println("created RatesModel " + _o.getId());
		
		// test createdAt and createdBy
		assertNotNull("create() should set createdAt", _o.getCreatedAt());
		assertNotNull("create() should set createdBy", _o.getCreatedBy());
		// test modifiedAt and modifiedBy (= same as createdAt/createdBy)
		assertNotNull("create() should set modifiedAt", _o.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _o.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _o.getCreatedAt(), _o.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _o.getCreatedBy(), _o.getModifiedBy());
		try {
			System.out.println("sleeping for 3 secs"); // in order to get different modifiedAt Dates
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// update(_o)  -> _o2
		_o.setTitle("testRatesModifications1");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_o.getId()).put(_o);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _o2 = _response.readEntity(RatesModel.class);
		System.out.println("updated RatesModel " + _o2.getId());

		// test createdAt and createdBy (unchanged)
		assertEquals("update() should not change createdAt", _o.getCreatedAt(), _o2.getCreatedAt());
		assertEquals("update() should not change createdBy", _o.getCreatedBy(), _o2.getCreatedBy());
		
		// test modifiedAt and modifiedBy (= different from createdAt/createdBy)
		System.out.println("comparing " + _o2.getModifiedAt().toString() + " with " + _o2.getCreatedAt().toString());
		assertThat(_o2.getModifiedAt().toString(), not(equalTo(_o2.getCreatedAt().toString())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_o2.getModifiedBy(), not(equalTo(_o2.getCreatedBy())));

		// update(o2) with createdBy set on client side -> error
		String _createdBy = _o.getCreatedBy();
		_o.setCreatedBy("testRatesModifications2");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_o.getId()).put(_o);
		assertEquals("update() should return with status BAD_REQUEST", 
				Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_o.setCreatedBy(_createdBy);

		// update(o) with createdAt set on client side -> error
		Date _d = _o.getCreatedAt();
		_o.setCreatedAt(new Date(1000));
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_o.getId()).put(_o);
		assertEquals("update() should return with status BAD_REQUEST", 
				Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_o.setCreatedAt(_d);

		// update(o) with modifiedBy/At set on client side -> ignored by server
		_o.setModifiedBy("testRatesModifications3");
		_o.setModifiedAt(new Date(1000));
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_o.getId()).put(_o);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		RatesModel _o3 = _response.readEntity(RatesModel.class);
		System.out.println("updated RatesModel " + _o3.getId());
	
		// test, that modifiedBy really ignored the client-side value "MYSELF"
		assertThat(_o.getModifiedBy(), not(equalTo(_o3.getModifiedBy())));
		// check whether the client-side modifiedAt() is ignored
		assertThat(_o.getModifiedAt(), not(equalTo(_o3.getModifiedAt())));
		
		// delete(_o) -> NO_CONTENT
		_response = webclient.replacePath("/").path(_o.getId()).delete();		
		System.out.println("deleted RatesModel " + _o.getId());
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
}
