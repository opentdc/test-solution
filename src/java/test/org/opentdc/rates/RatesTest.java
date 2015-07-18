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
import org.opentdc.rates.RateType;
import org.opentdc.rates.RatesModel;
import org.opentdc.rates.RatesService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

/**
 * Testing RatesService.
 * @author Bruno Kaiser
 *
 */
public class RatesTest extends AbstractTestClient {
	private WebClient rateWC = null;

	@Before
	public void initializeTest() {
		rateWC = initializeTest(ServiceUtil.RATES_API_URL, RatesService.class);
	}
	
	@After
	public void cleanupTest() {
		rateWC.close();
	}

	/********************************** rates attributes tests *********************************/	
	@Test
	public void testEmptyConstructor() {
		RatesModel _model = new RatesModel();
		assertNull("id should not be set by empty constructor", _model.getId());
		assertNull("title should not be set by empty constructor", _model.getTitle());
		assertEquals("rate should be initialized to 0 by empty constructor", 0, _model.getRate());
		assertNull("currency should not be set by empty constructor", _model.getCurrency());
		assertNull("description should not be set by empty constructor", _model.getDescription());
	}
	
	@Test
	public void testConstructor() {		
		RatesModel _model = new RatesModel("testConstructor", 100, "testConstructor");
		assertNull("id should not be set by constructor", _model.getId());
		assertEquals("title should be set by constructor", "testConstructor", _model.getTitle());
		assertEquals("rate should be set by constructor", 100, _model.getRate());
		assertEquals("currency should be set to default value by constructor", 
				Currency.getDefaultCurrency(), _model.getCurrency());
		assertEquals("description should not set by constructor", "testConstructor", _model.getDescription());
	}
	
	@Test
	public void testId() {
		RatesModel _model = new RatesModel();
		assertNull("id should not be set by constructor", _model.getId());
		_model.setId("testId");
		assertEquals("id should have changed", "testId", _model.getId());
	}

	@Test
	public void testTitle() {
		RatesModel _model = new RatesModel();
		assertNull("title should not be set by empty constructor", _model.getTitle());
		_model.setTitle("testTitle");
		assertEquals("title should have changed", "testTitle", _model.getTitle());
	}
	
	@Test
	public void testRate() {
		RatesModel _model = new RatesModel();
		assertEquals("rate should initialized to 0 by empty constructor", 0, _model.getRate());
		_model.setRate(100);
		assertEquals("rate should have changed", 100, _model.getRate());
	}
	
	@Test
	public void testCreatedBy() {
		RatesModel _model = new RatesModel();
		assertNull("createdBy should not be set by empty constructor", _model.getCreatedBy());
		_model.setCreatedBy("testCreatedBy");
		assertEquals("createdBy should have changed", "testCreatedBy", _model.getCreatedBy());	
	}
	
	@Test
	public void testCreatedAt() {
		RatesModel _model = new RatesModel();
		assertNull("createdAt should not be set by empty constructor", _model.getCreatedAt());
		_model.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _model.getCreatedAt());
	}
		
	@Test
	public void testModifiedBy() {
		RatesModel _model = new RatesModel();
		assertNull("modifiedBy should not be set by empty constructor", _model.getModifiedBy());
		_model.setModifiedBy("testModifiedBy");
		assertEquals("modifiedBy should have changed", "testModifiedBy", _model.getModifiedBy());	
	}
	
	@Test
	public void testModifiedAt() {
		RatesModel _model = new RatesModel();
		assertNull("modifiedAt should not be set by empty constructor", _model.getModifiedAt());
		_model.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _model.getModifiedAt());
	}

	@Test
	public void testCurrency() {
		RatesModel _model = new RatesModel();
		assertNull("currency should not be set by empty constructor", _model.getCurrency());
		_model.setCurrency(Currency.getDefaultCurrency());
		assertEquals("currency should have changed to default currency", Currency.getDefaultCurrency(), _model.getCurrency());
		assertEquals("default currency should be correct", Currency.CHF, Currency.getDefaultCurrency());
		_model.setCurrency(Currency.EUR);
		assertEquals("currency should be correct", Currency.EUR, _model.getCurrency());
		assertEquals("label should be correct", Currency.EUR.getLabel(), _model.getCurrency().getLabel());
		assertEquals("label should be correct", "Euro", Currency.EUR.getLabel());
		assertEquals("stringified value should be correct", Currency.EUR.toString(), _model.getCurrency().toString());
		_model.setCurrency(Currency.CHF);
		assertEquals("currency should be correct", Currency.CHF, _model.getCurrency());
		assertEquals("label should be correct", Currency.CHF.getLabel(), _model.getCurrency().getLabel());
		assertEquals("label should be correct", "Swiss Franc", Currency.CHF.getLabel());
		assertEquals("stringified value should be correct", Currency.CHF.toString(), _model.getCurrency().toString());
		_model.setCurrency(Currency.USD);
		assertEquals("currency should be correct", Currency.USD, _model.getCurrency());
		assertEquals("label should be correct", Currency.USD.getLabel(), _model.getCurrency().getLabel());
		assertEquals("label should be correct", "US Dollar", Currency.USD.getLabel());
		assertEquals("stringified value should be correct", Currency.USD.toString(), _model.getCurrency().toString());
	}

	@Test
	public void testDescription() {
		RatesModel _model = new RatesModel();
		assertNull("description should not be set by empty constructor", _model.getDescription());
		_model.setDescription("testDescription");
		assertEquals("description should have changed", "testDescription", _model.getDescription());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testCreateReadDeleteWithEmptyConstructor() {
		RatesModel _model1 = new RatesModel();
		assertNull("id should not be set by empty constructor", _model1.getId());
		assertNull("title should not be set by empty constructor", _model1.getTitle());
		assertEquals("rate should initialized to 0 by empty constructor", 0, _model1.getRate());
		assertNull("currency should not be set by empty constructor", _model1.getCurrency());
		assertNull("description should not be set by empty constructor", _model1.getDescription());
		
		postRate(_model1, Status.BAD_REQUEST);
		_model1.setTitle("testCreateReadDeleteWithEmptyConstructor");
		_model1.setRate(-1);		// negative rates are not allowed
		postRate(_model1, Status.BAD_REQUEST);
		int _rate = 100;
		_model1.setRate(_rate);
		RatesModel _model2 = postRate(_model1, Status.OK);
		
		assertNull("create() should not change the id of the local object", _model1.getId());
		assertEquals("create() should not change the title of the local object", "testCreateReadDeleteWithEmptyConstructor", _model1.getTitle());
		assertEquals("create() should not change the rate of the local object", _rate, _model1.getRate());
		assertNull("create() should not change the currency of the local object", _model1.getCurrency());
		assertNull("create() should not change the description of the local object", _model1.getDescription());
		
		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
		assertEquals("create() should not change the title", "testCreateReadDeleteWithEmptyConstructor", _model2.getTitle());
		assertEquals("create() should not change the rate", _rate, _model2.getRate());
		assertEquals("create() should set the default currency", Currency.getDefaultCurrency().getLabel(), _model2.getCurrency().getLabel());
		assertEquals("create() should set the default type", RateType.getDefaultRateType(), _model2.getType());
		assertNull("create() should not change the description", _model2.getDescription());
		
		RatesModel _model3 = getRate(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("title of returned object should be unchanged", _model2.getTitle(), _model3.getTitle());
		assertEquals("rate of returned object should be unchanged", _model2.getRate(), _model3.getRate());
		assertEquals("currency of returned object should be unchanged", _model2.getCurrency(), _model3.getCurrency());
		assertEquals("description of returned object should be unchanged", _model2.getDescription(), _model3.getDescription());
		
		deleteRate(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateReadDelete() {
		int _rate = 100;
		RatesModel _model1 = new RatesModel("testCreateReadDelete", _rate, "testCreateReadDelete");
		assertNull("id should not be set by constructor", _model1.getId());
		assertEquals("title should be set by constructor", "testCreateReadDelete", _model1.getTitle());
		assertEquals("description should be set by constructor", "testCreateReadDelete", _model1.getDescription());
		assertEquals("type should be set to the default", RateType.getDefaultRateType(), _model1.getType());
		assertEquals("currency should be set to the default", Currency.getDefaultCurrency(), _model1.getCurrency());
		
		RatesModel _model2 = postRate(_model1, Status.OK);
		assertNull("id should be still null after remote create", _model1.getId());
		assertEquals("title should be unchanged after remote create", "testCreateReadDelete", _model1.getTitle());
		assertEquals("rate should be unchanged after remote create", _rate, _model1.getRate());
		assertEquals("currency should be unchanged after remote create", Currency.getDefaultCurrency(), _model1.getCurrency());
		assertEquals("type should be unchanged after remote create", RateType.getDefaultRateType(), _model1.getType());
		assertEquals("description should be unchanged after remote create", "testCreateReadDelete", _model1.getDescription());

		assertNotNull("id of returned object should be set", _model2.getId());
		assertEquals("title of returned object should be unchanged after remote create", "testCreateReadDelete", _model2.getTitle());
		assertEquals("rate should be unchanged after remote create", _rate, _model2.getRate());
		assertEquals("currency should be unchanged after remote create", Currency.getDefaultCurrency(), _model2.getCurrency());
		assertEquals("type should be unchanged after remote create", RateType.getDefaultRateType(), _model2.getType());
		assertEquals("description of returned object should be unchanged after remote create", "testCreateReadDelete", _model2.getDescription());

		RatesModel _model3 = getRate(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("title of returned object should be unchanged after remote create", _model2.getTitle(), _model3.getTitle());
		assertEquals("rate should be unchanged after remote create", _model2.getRate(), _model3.getRate());
		assertEquals("currency should be unchanged after remote create", _model2.getCurrency(), _model3.getCurrency());
		assertEquals("type should be unchanged after remote create", _model2.getType(), _model3.getType());
		assertEquals("description of returned object should be unchanged after remote create", _model2.getDescription(), _model3.getDescription());

		deleteRate(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateWithClientSideId() {
		RatesModel _model = new RatesModel("testCreateWithClientSideId", 100, "testCreateWithClientSideId");
		_model.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _model.getId());
		postRate(_model, Status.BAD_REQUEST);
	}
	
	@Test
	public void testCreateWithDuplicateId() {
		RatesModel _model1 = postRate(new RatesModel("testCreateWithDuplicateId", 100, "MY_DESC"), Status.OK);
		RatesModel _model2 = new RatesModel("DuplicateRatesModel", 100, "MY_DESC");
		_model2.setId(_model1.getId());		// wrongly create a 2nd RatesModel object with the same ID
		postRate(_model2, Status.CONFLICT);
		deleteRate(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testList(
	) {		
		ArrayList<RatesModel> _localList = new ArrayList<RatesModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(postRate(new RatesModel("testList" + i, 100+i, "MY_DESC"), Status.OK));
		}
		List<RatesModel> _remoteList = listRates(Status.OK);

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (RatesModel _model : _remoteList) {
			_remoteListIds.add(_model.getId());
		}
		
		for (RatesModel _model : _localList) {
			assertTrue("rate <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
		}
		for (RatesModel _model : _localList) {
			getRate(_model.getId(), Status.OK);
		}
		for (RatesModel _model : _localList) {
			deleteRate(_model.getId(), Status.NO_CONTENT);
		}
	}
		
	@Test
	public void testCreate() {
		RatesModel _model1 = postRate(new RatesModel("testCreate1", 100, "MY_DESC1"), Status.OK);
		RatesModel _model2 = postRate(new RatesModel("testCreate2", 200, "MY_DESC2"), Status.OK);
		assertNotNull("ID should be set", _model1.getId());
		assertNotNull("ID should be set", _model2.getId());
		assertThat(_model2.getId(), not(equalTo(_model1.getId())));
		assertEquals("title should be set correctly", "testCreate1", _model1.getTitle());
		assertEquals("description should be set correctly", "MY_DESC1", _model1.getDescription());
		assertEquals("rate should be set by constructor", 100, _model1.getRate());
		assertEquals("currency should be set to default value by constructor", Currency.getDefaultCurrency(), _model1.getCurrency());
		assertEquals("type should be set to default value by constructor", RateType.getDefaultRateType(), _model1.getType());
		
		assertEquals("title should be set correctly", "testCreate2", _model2.getTitle());
		assertEquals("description should be set correctly", "MY_DESC2", _model2.getDescription());
		assertEquals("rate should be set by constructor", 200, _model2.getRate());
		assertEquals("currency should be set to default value by constructor", Currency.getDefaultCurrency(), _model2.getCurrency());
		assertEquals("type should be set to default value by constructor", RateType.getDefaultRateType(), _model2.getType());

		deleteRate(_model1.getId(), Status.NO_CONTENT);
		deleteRate(_model2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDoubleCreate() 
	{
		RatesModel _model = postRate(new RatesModel("testDoubleCreate", 100, "MY_DESC"), Status.OK);
		assertNotNull("ID should be set:", _model.getId());	
		postRate(_model, Status.CONFLICT);
		deleteRate(_model.getId(), Status.NO_CONTENT);
	}

	@Test
	public void testRead() {
		ArrayList<RatesModel> _localList = new ArrayList<RatesModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(postRate(new RatesModel("testRead" + i, 100 + i, "MY_DESC"), Status.OK));
		}
	
		// test read on each local element
		for (RatesModel _model : _localList) {
			getRate(_model.getId(), Status.OK);
		}

		// test read on each listed element
		for (RatesModel _model : listRates(Status.OK)) {
			assertEquals("ID should be unchanged when reading a rate", _model.getId(), getRate(_model.getId(), Status.OK).getId());						
		}

		for (RatesModel _model : _localList) {
			deleteRate(_model.getId(), Status.NO_CONTENT);
		}
	}	

	@Test
	public void testMultiRead() 
	{
		RatesModel _model1 = postRate(new RatesModel("testMultiRead", 100, "MY_DESC"), Status.OK);
		RatesModel _model2 = getRate(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged after read:", _model1.getId(), _model2.getId());		
		RatesModel _model3 = getRate(_model1.getId(), Status.OK);
		assertEquals("ID should be the same:", _model2.getId(), _model3.getId());
		assertEquals("title should be the same:", _model2.getTitle(), _model3.getTitle());
		assertEquals("description should be the same:", _model2.getDescription(), _model3.getDescription());
		assertEquals("currency should be the same:", _model2.getCurrency(), _model3.getCurrency());
		assertEquals("type should be the same:", _model2.getType(), _model3.getType());

		assertEquals("ID should be the same:", _model1.getId(), _model3.getId());
		assertEquals("title should be the same:", _model2.getTitle(), _model3.getTitle());
		assertEquals("description should be the same:", _model1.getDescription(), _model3.getDescription());
		assertEquals("currency should be the same:", _model1.getCurrency(), _model3.getCurrency());
		assertEquals("type should be the same:", _model1.getType(), _model3.getType());
		
		deleteRate(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testUpdate() 
	{
		RatesModel _model1 = postRate(new RatesModel("testUpdate", 100, "MY_DESC"), Status.OK);
		_model1.setTitle("testUpdate1");
		_model1.setRate(300);
		_model1.setCurrency(Currency.USD);
		_model1.setType(RateType.STANDARD_EXTERNAL_ON_SITE);
		_model1.setDescription("testUpdate1");
		RatesModel _model2 = putRate(_model1, Status.OK);
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());	
		assertEquals("title should have changed", "testUpdate1", _model2.getTitle());
		assertEquals("rate should have changed", 300, _model2.getRate());
		assertEquals("currency should have changed", Currency.USD, _model2.getCurrency());
		assertEquals("type should have changed", RateType.STANDARD_EXTERNAL_ON_SITE, _model2.getType());
		assertEquals("description should have changed", "testUpdate1", _model2.getDescription());

		_model1.setTitle("testUpdate2");
		_model1.setRate(400);
		_model1.setCurrency(Currency.EUR);
		_model1.setType(RateType.STANDARD_EXTERNAL_OFF_SITE);
		_model1.setDescription("testUpdate2");
		RatesModel _model3 = putRate(_model1, Status.OK);
		assertNotNull("ID should be set", _model3.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model3.getId());	
		assertEquals("title should have changed", "testUpdate2", _model3.getTitle());
		assertEquals("rate should have changed", 400, _model3.getRate());
		assertEquals("currency should have changed", Currency.EUR, _model3.getCurrency());
		assertEquals("type should have changed", RateType.STANDARD_EXTERNAL_OFF_SITE, _model3.getType());
		assertEquals("description should have changed", "testUpdate2", _model3.getDescription());
		
		deleteRate(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDelete() {
		RatesModel _model = postRate(new RatesModel("testDelete", 100, "MY_DESC"), Status.OK);
		RatesModel _tmpObj = getRate(_model.getId(), Status.OK);
		assertEquals("ID should be unchanged when reading a rate (remote):", _model.getId(), _tmpObj.getId());						
		deleteRate(_model.getId(), Status.NO_CONTENT);
		getRate(_model.getId(), Status.NOT_FOUND);
		getRate(_model.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testDoubleDelete() {
		RatesModel _model = postRate(new RatesModel("testDoubleDelete", 100, "MY_DESC"), Status.OK);
		getRate(_model.getId(), Status.OK);
		deleteRate(_model.getId(), Status.NO_CONTENT);
		getRate(_model.getId(), Status.NOT_FOUND);
		deleteRate(_model.getId(), Status.NOT_FOUND);
		getRate(_model.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testModifications() {
		RatesModel _model1 = postRate(new RatesModel("testModifications", 100, "MY_DESC"), Status.OK);
		assertNotNull("create() should set createdAt", _model1.getCreatedAt());
		assertNotNull("create() should set createdBy", _model1.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _model1.getCreatedAt(), _model1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());
		_model1.setTitle("testModifications1");
		RatesModel _model2 = putRate(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
		assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
		// timing issue: in order to test the following, we needed to introduce a sleep, what we do not want to do
		//assertThat(_model2.getModifiedAt().toString(), not(equalTo(_model2.getCreatedAt().toString())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_rm2.getModifiedBy(), not(equalTo(_rm2.getCreatedBy())));
		
		// client-side generated createdBy, createdAt, modifiedBy, modifiedAt should be ignored
		String _createdBy = _model1.getCreatedBy(); 
		Date _createdAt = _model1.getCreatedAt();
		String _modifiedBy = _model1.getModifiedBy();
		Date _modifiedAt = _model1.getModifiedAt();
		
		_model1.setCreatedBy("testModifications2");
		_model1.setCreatedAt(new Date(1000));
		_model1.setModifiedBy("BLA");
		_model1.setModifiedAt(new Date(2000));
		
		// client-side generated createdBy should be ignored -> no Validation errors expected
		RatesModel _model3 = putRate(_model1, Status.OK);
		assertEquals("client-side generated createdBy should be ignored", _createdBy, _model3.getCreatedBy());
		assertEquals("client-side generated createdAt should be ignored", _createdAt, _model3.getCreatedAt());
		// assertThat(_model1.getModifiedAt(), not(equalTo(_model3.getModifiedAt())));   timing issue, too short, don't want to introduce sleeps
		assertThat(_model1.getModifiedBy(), not(equalTo(_model3.getModifiedBy())));
		
		// reset
		_model1.setCreatedBy(_createdBy);
		_model1.setCreatedAt(_createdAt);
		_model1.setModifiedAt(_modifiedAt);
		_model1.setModifiedBy(_modifiedBy);
			
		deleteRate(_model1.getId(), Status.NO_CONTENT);
	}
	
	/********************************* helper methods *********************************/	
	/**
	 * List all rates.
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a list of RatesModel with all rates
	 */
	public List<RatesModel> listRates(
			Status expectedStatus) 
	{
		return listRates(rateWC, expectedStatus);
	}

	/**
	 * List all rates.
	 * @param webClient the WebClient representing the RatesService
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a list of RatesModel with all rates
	 */
	public static List<RatesModel> listRates(
			WebClient webClient,
			Status expectedStatus) 
	{
		Response _response = webClient.replacePath("/").query("size", Integer.MAX_VALUE).get();
		assertEquals("GET should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return new ArrayList<RatesModel>(webClient.getCollection(RatesModel.class));
		} else {
			return null;
		}
	}
	
	/**
	 * Create a new RatesModel on the server by executing a HTTP POST request.
	 * @param model the RatesModel to post to the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created RatesModel
	 */
	public RatesModel postRate(
			RatesModel model, 
			Status expectedStatus) {
		return postRate(rateWC, model, expectedStatus);
	}
	
	/**
	 * Create a new RatesModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the RatesService
	 * @param model the RatesModel data to create on the server
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the created RatesModel
	 */
	public static RatesModel postRate(
			WebClient webClient,
			RatesModel model,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").post(model);
		assertEquals("POST should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(RatesModel.class);
		} else {
			return null;
		}
	}
	
	/**
	 * Create a new RatesModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the RatesService
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the created RatesModel
	 */
	public static RatesModel createRate(
			WebClient webClient,
			Status expectedStatus) 
	{
		return postRate(webClient, new RatesModel("TestRate", 100, "TEST_DESC"), expectedStatus);
	}
	
	/**
	 * Create a new RatesModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the RatesService
	 * @param title the title of the rate
	 * @param rate the rate amount
	 * @param currency the currency of the rate
	 * @param description a description
	 * @return
	 */
	public static RatesModel createRate(
			WebClient webClient, 
			String title, 
			int rate,
			Currency currency,
			String description) 
	{
		RatesModel _model = new RatesModel();
		_model.setTitle(title);
		_model.setRate(rate);
		_model.setCurrency(currency);
		_model.setDescription(description);
		return postRate(webClient, _model, Status.OK);
	}
	
	/**
	 * Read the RatesModel with id from RatesService by executing a HTTP GET method.
	 * @param id the id of the RatesModel to retrieve
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the retrieved RatesModel object in JSON format
	 */
	public RatesModel getRate(String id, Status expectedStatus) {
		return getRate(rateWC, id, expectedStatus);
	}
	
	/**
	 * Read the RatesModel with id from RatesService by executing a HTTP GET method.
	 * @param rateWC the webclient of the RatesService
	 * @param id the id of the RatesModel to retrieve
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the retrieved RatesModel object in JSON format
	 */
	public static RatesModel getRate(WebClient rateWC, String id, Status expectedStatus) {
		Response _response = rateWC.replacePath("/").path(id).get();
		assertEquals("read() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(RatesModel.class);
		} else {
			return null;
		}
	}
	
	/**
	 * Update the RatesModel with id with new values.
	 * @param model the new data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the newly updated RatesModel object in JSON format
	 */
	private RatesModel putRate(
			RatesModel model, 
			Status expectedStatus) {
		return putRate(rateWC, model, expectedStatus);
	}
	
	/**
	 * Update the RatesModel with id with new values.
	 * @param webClient the webclient of the RatesService
	 * @param model the new data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the newly updated RatesModel object in JSON format
	 */
	public static RatesModel putRate(
			WebClient webClient,
			RatesModel model,
			Status expectedStatus) {
		webClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response _response = webClient.replacePath("/").path(model.getId()).put(model);
		assertEquals("update() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(RatesModel.class);
		}
		else {
			return null;
		}
	}
		
	/**
	 * Delete the RatesModel with id on the RatesService by executing a HTTP DELETE method.
	 * @param id the id of the RatesModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public void deleteRate(String rateId, Status expectedStatus) {
		deleteRate(rateWC, rateId, expectedStatus);
	}
	
	/**
	 * Delete the RatesModel on the RatesService by executing a HTTP DELETE method.
	 * @param webClient the WebClient for the RatesService
	 * @param rateId the id of the RatesModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public static void deleteRate(
			WebClient webClient,
			String rateId,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(rateId).delete();		
		assertEquals("DELETE should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
	}
}
