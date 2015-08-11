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
package test.org.opentdc.texts;

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
import org.opentdc.texts.SingleLangText;
import org.opentdc.texts.TextModel;
import org.opentdc.texts.TextsService;
import org.opentdc.util.LanguageCode;
import org.opentdc.service.LocalizedTextModel;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.texts.LocalizedTextTest;

/*
 * Tests the Texts-Service.
 * @author Bruno Kaiser
 */
public class TextTest extends AbstractTestClient {
	private WebClient wc = null;

	/**
	 * Initializes the test case.
	 */
	@Before
	public void initializeTest() {
		wc = initializeTest(ServiceUtil.TEXTS_API_URL, TextsService.class);
	}
	
	/**
	 * Cleanup all resources needed by the test case.
	 */
	@After
	public void cleanupTest() {
		wc.close();
	}

	/********************************** texts attributes tests *********************************/	
	/**
	 * Test the empty constructor.
	 */
	@Test
	public void testEmptyConstructor() {
		TextModel _model = new TextModel();
		assertNull("id should not be set by empty constructor", _model.getId());
		assertNull("title should not be set by empty constructor", _model.getTitle());
		assertNull("description should not be set by empty constructor", _model.getDescription());
	}
	
	/**
	 * Test the custom constructor.
	 */
	@Test
	public void testConstructor() {		
		TextModel _model = new TextModel("testConstructor", "testConstructor");
		assertNull("id should not be set by constructor", _model.getId());
		assertEquals("title should be set by constructor", "testConstructor", _model.getTitle());
		assertEquals("description should not set by constructor", "testConstructor", _model.getDescription());
	}
	
	/**
	 * Test id attribute.
	 */
	@Test
	public void testId() {
		TextModel _model = new TextModel();
		assertNull("id should not be set by constructor", _model.getId());
		_model.setId("testId");
		assertEquals("id should have changed", "testId", _model.getId());
	}

	/**
	 * Test title attribute.
	 */
	@Test
	public void testTitle() {
		TextModel _model = new TextModel();
		assertNull("title should not be set by empty constructor", _model.getTitle());
		_model.setTitle("testTitle");
		assertEquals("title should have changed", "testTitle", _model.getTitle());
	}
	
	/**
	 * Test createdBy attribute.
	 */
	@Test
	public void testCreatedBy() {
		TextModel _model = new TextModel();
		assertNull("createdBy should not be set by empty constructor", _model.getCreatedBy());
		_model.setCreatedBy("testCreatedBy");
		assertEquals("createdBy should have changed", "testCreatedBy", _model.getCreatedBy());	
	}
	
	/**
	 * Test createdAt attribute.
	 */
	@Test
	public void testCreatedAt() {
		TextModel _model = new TextModel();
		assertNull("createdAt should not be set by empty constructor", _model.getCreatedAt());
		_model.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _model.getCreatedAt());
	}
		
	/**
	 * Test modifiedBy attribute.
	 */
	@Test
	public void testModifiedBy() {
		TextModel _model = new TextModel();
		assertNull("modifiedBy should not be set by empty constructor", _model.getModifiedBy());
		_model.setModifiedBy("testModifiedBy");
		assertEquals("modifiedBy should have changed", "testModifiedBy", _model.getModifiedBy());	
	}
	
	/**
	 * Test modifiedAt attribute.
	 */
	@Test
	public void testModifiedAt() {
		TextModel _model = new TextModel();
		assertNull("modifiedAt should not be set by empty constructor", _model.getModifiedAt());
		_model.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _model.getModifiedAt());
	}

	/**
	 * Test description attribute.
	 */
	@Test
	public void testDescription() {
		TextModel _model = new TextModel();
		assertNull("description should not be set by empty constructor", _model.getDescription());
		_model.setDescription("testDescription");
		assertEquals("description should have changed", "testDescription", _model.getDescription());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testCreateReadDeleteWithEmptyConstructor() {
		TextModel _model1 = new TextModel();
		assertNull("id should not be set by empty constructor", _model1.getId());
		assertNull("title should not be set by empty constructor", _model1.getTitle());
		assertNull("description should not be set by empty constructor", _model1.getDescription());

		post(_model1, Status.BAD_REQUEST);
		_model1.setTitle("testCreateReadDeleteWithEmptyConstructor");
		
		TextModel _model2 = post(_model1, Status.OK);
		assertNull("create() should not change the id of the local object", _model1.getId());
		assertEquals("create() should not change the title of the local object", "testCreateReadDeleteWithEmptyConstructor", _model1.getTitle());
		assertNull("create() should not change the description of the local object", _model1.getDescription());
		
		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
		assertEquals("create() should not change the title", "testCreateReadDeleteWithEmptyConstructor", _model2.getTitle());
		assertNull("create() should not change the description", _model2.getDescription());
		
		TextModel _model3 = get(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("title of returned object should be unchanged", _model2.getTitle(), _model3.getTitle());
		assertEquals("description of returned object should be unchanged", _model2.getDescription(), _model3.getDescription());
		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateReadDelete() {
		TextModel _model1 = new TextModel("testCreateReadDelete", "testCreateReadDelete");
		assertNull("id should not be set by constructor", _model1.getId());
		assertEquals("title should be set by constructor", "testCreateReadDelete", _model1.getTitle());
		assertEquals("description should be set by constructor", "testCreateReadDelete", _model1.getDescription());

		TextModel _model2 = post(_model1, Status.OK);
		assertNull("id should be still null after remote create", _model1.getId());
		assertEquals("title should be unchanged after remote create", "testCreateReadDelete", _model1.getTitle());
		assertEquals("description should be unchanged after remote create", "testCreateReadDelete", _model1.getDescription());
		assertNotNull("id of returned object should be set", _model2.getId());
		assertEquals("title of returned object should be unchanged after remote create", "testCreateReadDelete", _model2.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", "testCreateReadDelete", _model2.getDescription());

		TextModel _model3 = get(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("title of returned object should be unchanged after remote create", _model2.getTitle(), _model3.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", _model2.getDescription(), _model3.getDescription());
		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateWithClientSideId() {
		TextModel _model = new TextModel("testCreateWithClientSideId", "testCreateWithClientSideId");
		_model.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _model.getId());
		post(_model, Status.BAD_REQUEST);
	}
	
	@Test
	public void testCreateWithDuplicateId() {
		TextModel _model1 = post(new TextModel("testCreateWithDuplicateId1", "testCreateWithDuplicateId1"), Status.OK);
		TextModel _model2 = post(new TextModel("testCreateWithDuplicateId2", "testCreateWithDuplicateId2"), Status.OK);
		String _model2Id = _model2.getId();
		_model2.setId(_model1.getId());		// wrongly create a 2nd TextModel object with the same ID
		post(_model2, Status.CONFLICT);
		delete(_model1.getId(), Status.NO_CONTENT);
		delete(_model2Id, Status.NO_CONTENT);
	}
	
	/**
	 * Test list() operation filtered by LanguageCode.
	 */
	@Test
	public void testListAll()
	{
		TextModel _model1 = post(new TextModel("testListAll1", "MY_DESC"), Status.OK);
		TextModel _model2 = post(new TextModel("testListAll2", "MY_DESC"), Status.OK);
		TextModel _model3 = post(new TextModel("testListAll3", "MY_DESC"), Status.OK);
		LocalizedTextTest.post(wc, _model1, new LocalizedTextModel(LanguageCode.DE, "eins"), Status.OK);
		LocalizedTextTest.post(wc, _model2, new LocalizedTextModel(LanguageCode.DE, "zwei"), Status.OK);
		LocalizedTextTest.post(wc, _model3, new LocalizedTextModel(LanguageCode.DE, "drei"), Status.OK);
		LocalizedTextTest.post(wc, _model1, new LocalizedTextModel(LanguageCode.EN, "one"), Status.OK);
		LocalizedTextTest.post(wc, _model2, new LocalizedTextModel(LanguageCode.EN, "two"), Status.OK);
		LocalizedTextTest.post(wc, _model3, new LocalizedTextModel(LanguageCode.FR, "trois"), Status.OK);

		List<SingleLangText> _tagList = list(null, Status.OK);
		printSingleLangTextList("all tags (no query):", _tagList);
		assertTrue("amount of returned tags must be correct", _tagList.size() >= 6);
		
		_tagList = list("lang=" + LanguageCode.DE, Status.OK);
		printSingleLangTextList("DE texts:", _tagList);
		assertTrue("amount of returned tags must be correct", _tagList.size() >= 3);
		
		_tagList = list("lang=" + LanguageCode.EN, Status.OK);
		printSingleLangTextList("EN texts:", _tagList);
		assertTrue("amount of returned tags must be correct", _tagList.size() >= 2);
		assertEquals("tag text must be correct", "one", _tagList.get(0).getText());
		
		_tagList = list("lang=" + LanguageCode.FR, Status.OK);
		printSingleLangTextList("FR texts:", _tagList);
		assertTrue("amount of returned tags must be correct", _tagList.size() >= 1);
		assertEquals("tag text must be correct", "trois", _tagList.get(0).getText());
		
		_tagList = list("lang=" + LanguageCode.IT, Status.OK);
		printSingleLangTextList("IT texts:", _tagList);
		assertEquals("amount of returned tags must be correct", 0, _tagList.size());
		
		list("language=DE", Status.BAD_REQUEST);
		list("lang=OB", Status.BAD_REQUEST);
		
		delete(_model1.getId(), Status.NO_CONTENT);
		delete(_model2.getId(), Status.NO_CONTENT);
		delete(_model3.getId(), Status.NO_CONTENT);
	}

	/**
	 * Print the result of the list() operation onto stdout.
	 * @param title  the title of the log section
	 * @param list a list of SingleLangText objects
	 */
	public static void printSingleLangTextList(String title, List<SingleLangText> list) {
		System.out.println(title);
		System.out.println("\ttextId\t\t\t\t\tlocalizedTextId\t\t\t\ttext\tlanguageCode");
		for (SingleLangText _model : list) { 
			System.out.println(
					"\t" + _model.getTextId() + 
					"\t" + _model.getLocalizedTextId() + 
					"\t" + _model.getText() + 
					"\t" + _model.getLanguageCode());
		}
		System.out.println("\ttotal:\t" + list.size() + " elements");
	}

	/**
	 * Print the result of the list() operation onto stdout.
	 * @param title  the title of the log section
	 * @param list a list of TextModel objects
	 */
	public static void printModelList(String title, List<TextModel> list) {
		System.out.println("***** " + title);
		System.out.println("\ttextId\t\t\t\t\tlocalizedTextId\t\t\t\ttext\tlanguageCode");
		for (TextModel _model : list) { 
			System.out.println(
					"\t" + _model.getId() + 
					"\t" + _model.getTitle());
		}
		System.out.println("\ttotal:\t" + list.size() + " elements");
	}

	/**
	 * Test list() operation on TextModels not containing LocalizedTextModels.
	 */
	@Test
	public void testPureTextList() 
	{		
		List<SingleLangText> _remoteList1 = list(null, Status.OK);
		ArrayList<TextModel> _localList = new ArrayList<TextModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(post(new TextModel("testPureTextList" + i, "MY_DESC"), Status.OK));
		}
		List<SingleLangText> _remoteList2 = list(null, Status.OK);
		assertEquals("list should be empty (because no LocalizedText is saved", _remoteList1.size(), _remoteList2.size());		
		for (TextModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}
	}
			
	@Test
	public void testCreate() {
		TextModel _model1 = post(new TextModel("testCreate1", "testCreate2"), Status.OK);
		TextModel _model2 = post(new TextModel("testCreate3", "testCreate4"), Status.OK);
		assertNotNull("ID should be set", _model1.getId());
		assertNotNull("ID should be set", _model2.getId());
		assertThat(_model1.getId(), not(equalTo(_model2.getId())));

		assertEquals("title should be set correctly", "testCreate1", _model1.getTitle());
		assertEquals("description should be set correctly", "testCreate2", _model1.getDescription());
		assertEquals("title should be set correctly", "testCreate3", _model2.getTitle());
		assertEquals("description should be set correctly", "testCreate4", _model2.getDescription());

		delete(_model1.getId(), Status.NO_CONTENT);
		delete(_model2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDoubleCreate() {
		TextModel _model = post(new TextModel("testDoubleCreate1", "testDoubleCreate2"), Status.OK);
		assertNotNull("ID should be set", _model.getId());
		post(_model, Status.CONFLICT);
		delete(_model.getId(), Status.NO_CONTENT);
	}

	@Test
	public void testRead() {
		ArrayList<TextModel> _localList = new ArrayList<TextModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(post(new TextModel("testRead" + i, "testRead"), Status.OK));
		}
		// test read on each local element
		for (TextModel _model : _localList) {
			get(_model.getId(), Status.OK);
		}
		List<SingleLangText> _remoteList = list(null, Status.OK);
		
		for (SingleLangText _singleLangText : _remoteList) {
			get(_singleLangText.getTextId(), Status.OK);
		}
		for (TextModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}
	}	

	@Test
	public void testMultiRead() {
		TextModel _model1 = post(new TextModel("testMultiRead", "testMultiRead"), Status.OK);
		TextModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged after read", _model1.getId(), _model2.getId());		
		TextModel _model3 = get(_model1.getId(), Status.OK);
		
		assertEquals("ID should be the same", _model3.getId(), _model2.getId());
		assertEquals("title should be the same", _model3.getTitle(), _model2.getTitle());
		assertEquals("description should be the same", _model3.getDescription(), _model2.getDescription());
		
		assertEquals("ID should be the same:", _model1.getId(), _model2.getId());
		assertEquals("title should be the same:", _model1.getTitle(), _model2.getTitle());
		assertEquals("description should be the same:", _model1.getDescription(), _model2.getDescription());
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testUpdate(
	) {
		TextModel _model1 = post(new TextModel("testUpdate1", "testUpdate2"), Status.OK);

		_model1.setTitle("testUpdate3");
		_model1.setDescription("testUpdate4");
		TextModel _model2 = put(_model1, Status.OK);
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());	
		assertEquals("title should have changed", "testUpdate3", _model2.getTitle());
		assertEquals("description should have changed", "testUpdate4", _model2.getDescription());

		_model1.setTitle("testUpdate5");
		_model1.setDescription("testUpdate6");
		TextModel _model3 = put(_model1, Status.OK);
		assertNotNull("ID should be set", _model3.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model3.getId());	
		assertEquals("title should have changed", "testUpdate5", _model3.getTitle());
		assertEquals("description should have changed", "testUpdate6", _model3.getDescription());

		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDelete() {
		TextModel _model1 = post(new TextModel("testDelete", "testDelete"), Status.OK);
		TextModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged when reading a text (remote):", _model1.getId(), _model2.getId());						
		delete(_model1.getId(), Status.NO_CONTENT);
		get(_model1.getId(), Status.NOT_FOUND);
		get(_model1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testDoubleDelete() {
		TextModel _model = post(new TextModel("testDoubleDelete", "testDoubleDelete"), Status.OK);
		get(_model.getId(), Status.OK);
		delete(_model.getId(), Status.NO_CONTENT);
		get(_model.getId(), Status.NOT_FOUND);
		delete(_model.getId(), Status.NOT_FOUND);
		get(_model.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testModifications() {
		TextModel _model1 = post(new TextModel("testModifications", "testModifications"), Status.OK);
		assertNotNull("create() should set createdAt", _model1.getCreatedAt());
		assertNotNull("create() should set createdBy", _model1.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _model1.getCreatedAt(), _model1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());
		_model1.setDescription("updated");
		TextModel _model2 = put(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
		assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
		// next test fails because of timing issue; but we do not want to introduce a sleep() here
		// assertThat(_model2.getModifiedAt().toString(), not(equalTo(_model2.getCreatedAt().toString())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_model2.getModifiedBy(), not(equalTo(_model2.getCreatedBy())));

		String _createdBy = _model1.getCreatedBy();
		_model1.setCreatedBy("testModifications3");
		TextModel _model3 = put(_model1, Status.OK);
		assertEquals("update() should not change createdBy", _createdBy, _model3.getCreatedBy());

		Date _createdAt = _model1.getCreatedAt();
		_model1.setCreatedAt(new Date(1000));
		TextModel _model4 = put(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _createdAt, _model4.getCreatedAt());

		String _modifiedBy = _model1.getModifiedBy();
		_model1.setModifiedBy("testModifications5");
		TextModel _model5 = put(_model1, Status.OK);
		assertEquals("update() should not change modifiedBy", _modifiedBy, _model5.getModifiedBy());

		Date _modifiedAt = _model1.getModifiedAt();
		Date _modifiedAt2 = new Date(1000);
		_model1.setModifiedAt(_modifiedAt2);
		TextModel _model6 = put(_model1, Status.OK);
		assertThat(_model6.getModifiedAt(), not(equalTo(_modifiedAt)));
		assertThat(_model6.getModifiedAt(), not(equalTo(_modifiedAt2)));

		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	/********************************* helper methods *********************************/	
	/**
	 * Retrieve a list of SingleLangText from TextsService by executing a HTTP GET request.
	 * This uses neither position nor size queries.
	 * @param query the URL query to use
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of SingleLangText object in JSON format
	 */
	public List<SingleLangText> list(
			String query, 
			Status expectedStatus) {
		return list(wc, query, -1, -1, expectedStatus);
	}
	
	/**
	 * Retrieve a list of SingleLangText from TextsService by executing a HTTP GET request.
	 * @param webClient the WebClient for the TextsService
	 * @param query the URL query to use
	 * @param position the position to start a batch with
	 * @param size the size of a batch
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of SingleLangText objects in JSON format
	 */
	public static List<SingleLangText> list(
			WebClient webClient, 
			String query, 
			int position,
			int size,
			Status expectedStatus) {
		Response _response = null;
		webClient.resetQuery();
		if (query == null) {
			if (position >= 0) {
				if (size >= 0) {
					_response = webClient.replacePath("/").query("position", position).query("size", size).get();
				} else {
					_response = webClient.replacePath("/").query("position", position).get();
				}
			} else {
				if (size >= 0) {
					_response = webClient.replacePath("/").query("size", size).get();
				} else {
					_response = webClient.replacePath("/").get();
				}
			}
		} else {
			if (position >= 0) {
				if (size >= 0) {
					_response = webClient.replacePath("/").query("query", query).query("position", position).query("size", size).get();					
				} else {
					_response = webClient.replacePath("/").query("query", query).query("position", position).get();					
				}
			} else {
				if (size >= 0) {
					_response = webClient.replacePath("/").query("query", query).query("size", size).get();					
				} else {
					_response = webClient.replacePath("/").query("query", query).get();					
				}				
			}
		}
		List<SingleLangText> _texts = null;
		if (expectedStatus != null) {
			assertEquals("list() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			_texts = new ArrayList<SingleLangText>(webClient.getCollection(SingleLangText.class));
			System.out.println("list(webClient, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + 
					") ->" + _texts.size() + " objects");
		}
		else {
			System.out.println("list(webClient, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + 
					") -> Status: " + _response.getStatus());
		}
		return _texts;
	}

	/**
	 * Create a new TextModel on the server by executing a HTTP POST request.
	 * @param model the TextModel to post to the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created TextModel
	 */
	public TextModel post(
			TextModel model, 
			Status expectedStatus) {
		return post(wc, model, expectedStatus);
	}

	/**
	 * Create a new TextModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the TextsService
	 * @param model the TextModel data to create on the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created TextModel
	 */
	public static TextModel post(
			WebClient webClient,
			TextModel model,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").post(model);
		if (expectedStatus != null) {
			assertEquals("POST should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(TextModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Create a new TextModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the TextsService
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created TextModel
	 */
	public static TextModel create(
			WebClient webClient, 
			String title, 
			String description,
			Status expectedStatus) 
	{
		return post(webClient, new TextModel(title, description), expectedStatus);
	}
	
	/**
	 * Read the TextModel with id from TextsService by executing a HTTP GET method.
	 * @param textId the id of the TextModel to retrieve
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the retrieved TextModel object in JSON format
	 */
	public TextModel get(
			String textId, 
			Status expectedStatus) {
		return get(wc, textId, expectedStatus);
	}
	
	/**
	 * Read the TextModel with id from TextsService by executing a HTTP GET method.
	 * @param webClient the web client representing the TextsService
	 * @param textId the id of the TextModel to retrieve
	 * @param expectedStatus  the expected HTTP status to test on
	 * @return the retrieved TextModel object in JSON format
	 */
	public static TextModel get(
			WebClient webClient,
			String textId,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(textId).get();
		if (expectedStatus != null) {
			assertEquals("GET should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(TextModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Update a TextModel on the TextsService by executing a HTTP PUT method.
	 * @param tm the new TextModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated TextModel object in JSON format
	 */
	public TextModel put(
			TextModel model, 
			Status expectedStatus) {
		return put(wc, model, expectedStatus);
	}
	
	/**
	 * Update a TextModel on the TextsService by executing a HTTP PUT method.
	 * @param webClient the web client representing the TextsService
	 * @param model the new TextModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated TextModel object in JSON format
	 */
	public static TextModel put(
			WebClient webClient,
			TextModel model,
			Status expectedStatus) {
		webClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response _response = webClient.replacePath("/").path(model.getId()).put(model);
		if (expectedStatus != null) {
			assertEquals("PUT should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(TextModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Delete the TextModel with id on the TextsService by executing a HTTP DELETE method.
	 * @param id the id of the TextModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public void delete(String id, Status expectedStatus) {
		delete(wc, id, expectedStatus);
	}
	
	/**
	 * Delete the TextModel with id on the TextsService by executing a HTTP DELETE method.
	 * @param webClient the WebClient representing the TextsService
	 * @param textId the id of the TextModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public static void delete(
			WebClient webClient,
			String textId,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(textId).delete();	
		if (expectedStatus != null) {
			assertEquals("DELETE should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
	}
	
	protected int calculateMembers() {
		return 0;
	}
}
