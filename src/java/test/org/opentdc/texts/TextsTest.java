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
import org.opentdc.texts.TextModel;
import org.opentdc.texts.TextsService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

/*
 * Tests the Texts-Service.
 * @author Bruno Kaiser
 */
public class TextsTest extends AbstractTestClient {
	public static final String API_URL = "api/text/";
	private WebClient textWC = null;

	/**
	 * Initializes the test case.
	 */
	@Before
	public void initializeTest() {
		textWC = initializeTest(ServiceUtil.TEXTS_API_URL, TextsService.class);
	}
	
	/**
	 * Cleanup all resources needed by the test case.
	 */
	@After
	public void cleanupTest() {
		textWC.close();
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

		postText(_model1, Status.BAD_REQUEST);
		_model1.setTitle("testCreateReadDeleteWithEmptyConstructor");
		
		TextModel _model2 = postText(_model1, Status.OK);
		assertNull("create() should not change the id of the local object", _model1.getId());
		assertEquals("create() should not change the title of the local object", "testCreateReadDeleteWithEmptyConstructor", _model1.getTitle());
		assertNull("create() should not change the description of the local object", _model1.getDescription());
		
		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
		assertEquals("create() should not change the title", "testCreateReadDeleteWithEmptyConstructor", _model2.getTitle());
		assertNull("create() should not change the description", _model2.getDescription());
		
		TextModel _model3 = getText(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("title of returned object should be unchanged", _model2.getTitle(), _model3.getTitle());
		assertEquals("description of returned object should be unchanged", _model2.getDescription(), _model3.getDescription());
		deleteText(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateReadDelete() {
		TextModel _model1 = new TextModel("testCreateReadDelete", "testCreateReadDelete");
		assertNull("id should not be set by constructor", _model1.getId());
		assertEquals("title should be set by constructor", "testCreateReadDelete", _model1.getTitle());
		assertEquals("description should be set by constructor", "testCreateReadDelete", _model1.getDescription());

		TextModel _model2 = postText(_model1, Status.OK);
		assertNull("id should be still null after remote create", _model1.getId());
		assertEquals("title should be unchanged after remote create", "testCreateReadDelete", _model1.getTitle());
		assertEquals("description should be unchanged after remote create", "testCreateReadDelete", _model1.getDescription());
		assertNotNull("id of returned object should be set", _model2.getId());
		assertEquals("title of returned object should be unchanged after remote create", "testCreateReadDelete", _model2.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", "testCreateReadDelete", _model2.getDescription());

		TextModel _model3 = getText(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("title of returned object should be unchanged after remote create", _model2.getTitle(), _model3.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", _model2.getDescription(), _model3.getDescription());
		deleteText(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateWithClientSideId() {
		TextModel _model = new TextModel("testCreateWithClientSideId", "testCreateWithClientSideId");
		_model.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _model.getId());
		postText(_model, Status.BAD_REQUEST);
	}
	
	@Test
	public void testCreateWithDuplicateId() {
		TextModel _model1 = postText(new TextModel("testCreateWithDuplicateId1", "testCreateWithDuplicateId1"), Status.OK);
		TextModel _model2 = postText(new TextModel("testCreateWithDuplicateId2", "testCreateWithDuplicateId2"), Status.OK);
		_model2.setId(_model1.getId());		// wrongly create a 2nd TextModel object with the same ID
		postText(_model2, Status.CONFLICT);
		deleteText(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testList() 
	{		
		System.out.println("testList:");
		System.out.println("a) creating " + LIMIT + " TextModels into _localList:");
		ArrayList<TextModel> _localList = new ArrayList<TextModel>();
		TextModel _tm = null;
		for (int i = 0; i < LIMIT; i++) {
			_tm = postText(
					new TextModel("testList" + i, "testList" + i),
					Status.OK);
			_localList.add(_tm);
			System.out.println("\t" + _tm.getId() + ": " + _tm.getTitle());
		}
		List<TextModel> _remoteList = listTexts(textWC, null, 0, 100, Status.OK);

		System.out.println("b) list() -> _remoteList / _remoteListIds:");
		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (TextModel _model : _remoteList) {
			_remoteListIds.add(_model.getId());
			System.out.println("\t" + _model.getId() + ": " + _model.getTitle());
		}
		
		for (TextModel _model : _localList) {
			assertTrue("text <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
		}
		for (TextModel _model : _localList) {
			getText(_model.getId(), Status.OK);
		}
		for (TextModel _model : _localList) {
			deleteText(_model.getId(), Status.NO_CONTENT);
		}
	}
		
	@Test
	public void testCreate() {
		TextModel _model1 = postText(new TextModel("testCreate1", "testCreate2"), Status.OK);
		TextModel _model2 = postText(new TextModel("testCreate3", "testCreate4"), Status.OK);
		assertNotNull("ID should be set", _model1.getId());
		assertNotNull("ID should be set", _model2.getId());
		assertThat(_model1.getId(), not(equalTo(_model2.getId())));

		assertEquals("title should be set correctly", "testCreate1", _model1.getTitle());
		assertEquals("description should be set correctly", "testCreate2", _model1.getDescription());
		assertEquals("title should be set correctly", "testCreate3", _model2.getTitle());
		assertEquals("description should be set correctly", "testCreate4", _model2.getDescription());

		deleteText(_model1.getId(), Status.NO_CONTENT);
		deleteText(_model2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDoubleCreate() {
		TextModel _model = postText(new TextModel("testDoubleCreate1", "testDoubleCreate2"), Status.OK);
		assertNotNull("ID should be set", _model.getId());
		postText(_model, Status.CONFLICT);
		deleteText(_model.getId(), Status.NO_CONTENT);
	}

	@Test
	public void testRead() {
		ArrayList<TextModel> _localList = new ArrayList<TextModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(postText(new TextModel("testRead" + i, "testRead"), Status.OK));
		}
		// test read on each local element
		for (TextModel _model : _localList) {
			getText(_model.getId(), Status.OK);
		}
		// test read on each listed element
		for (TextModel _model : listTexts(null, Status.OK)) {
			assertEquals("ID should be unchanged when reading a text", _model.getId(), getText(_model.getId(), Status.OK).getId());
		}
		for (TextModel _model : _localList) {
			deleteText(_model.getId(), Status.NO_CONTENT);
		}
	}	

	@Test
	public void testMultiRead() {
		TextModel _model1 = postText(new TextModel("testMultiRead", "testMultiRead"), Status.OK);
		TextModel _model2 = getText(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged after read", _model1.getId(), _model2.getId());		
		TextModel _model3 = getText(_model1.getId(), Status.OK);
		
		assertEquals("ID should be the same", _model3.getId(), _model2.getId());
		assertEquals("title should be the same", _model3.getTitle(), _model2.getTitle());
		assertEquals("description should be the same", _model3.getDescription(), _model2.getDescription());
		
		assertEquals("ID should be the same:", _model1.getId(), _model2.getId());
		assertEquals("title should be the same:", _model1.getTitle(), _model2.getTitle());
		assertEquals("description should be the same:", _model1.getDescription(), _model2.getDescription());
		deleteText(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testUpdate(
	) {
		TextModel _model1 = postText(new TextModel("testUpdate1", "testUpdate2"), Status.OK);

		_model1.setTitle("testUpdate3");
		_model1.setDescription("testUpdate4");
		TextModel _model2 = putText(_model1, Status.OK);
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());	
		assertEquals("title should have changed", "testUpdate3", _model2.getTitle());
		assertEquals("description should have changed", "testUpdate4", _model2.getDescription());

		_model1.setTitle("testUpdate5");
		_model1.setDescription("testUpdate6");
		TextModel _model3 = putText(_model1, Status.OK);
		assertNotNull("ID should be set", _model3.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model3.getId());	
		assertEquals("title should have changed", "testUpdate5", _model3.getTitle());
		assertEquals("description should have changed", "testUpdate6", _model3.getDescription());

		deleteText(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDelete() {
		TextModel _model1 = postText(new TextModel("testDelete", "testDelete"), Status.OK);
		TextModel _model2 = getText(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged when reading a text (remote):", _model1.getId(), _model2.getId());						
		deleteText(_model1.getId(), Status.NO_CONTENT);
		getText(_model1.getId(), Status.NOT_FOUND);
		getText(_model1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testDoubleDelete() {
		TextModel _model = postText(new TextModel("testDoubleDelete", "testDoubleDelete"), Status.OK);
		getText(_model.getId(), Status.OK);
		deleteText(_model.getId(), Status.NO_CONTENT);
		getText(_model.getId(), Status.NOT_FOUND);
		deleteText(_model.getId(), Status.NOT_FOUND);
		getText(_model.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testModifications() {
		TextModel _model1 = postText(new TextModel("testModifications", "testModifications"), Status.OK);
		assertNotNull("create() should set createdAt", _model1.getCreatedAt());
		assertNotNull("create() should set createdBy", _model1.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _model1.getCreatedAt(), _model1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());
		_model1.setDescription("updated");
		TextModel _model2 = putText(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
		assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
		// next test fails because of timing issue; but we do not want to introduce a sleep() here
		// assertThat(_model2.getModifiedAt().toString(), not(equalTo(_model2.getCreatedAt().toString())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_model2.getModifiedBy(), not(equalTo(_model2.getCreatedBy())));

		String _createdBy = _model1.getCreatedBy();
		_model1.setCreatedBy("testModifications3");
		TextModel _model3 = putText(_model1, Status.OK);
		assertEquals("update() should not change createdBy", _createdBy, _model3.getCreatedBy());

		Date _createdAt = _model1.getCreatedAt();
		_model1.setCreatedAt(new Date(1000));
		TextModel _model4 = putText(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _createdAt, _model4.getCreatedAt());

		String _modifiedBy = _model1.getModifiedBy();
		_model1.setModifiedBy("testModifications5");
		TextModel _model5 = putText(_model1, Status.OK);
		assertEquals("update() should not change modifiedBy", _modifiedBy, _model5.getModifiedBy());

		Date _modifiedAt = _model1.getModifiedAt();
		Date _modifiedAt2 = new Date(1000);
		_model1.setModifiedAt(_modifiedAt2);
		TextModel _model6 = putText(_model1, Status.OK);
		assertThat(_model6.getModifiedAt(), not(equalTo(_modifiedAt)));
		assertThat(_model6.getModifiedAt(), not(equalTo(_modifiedAt2)));

		deleteText(_model1.getId(), Status.NO_CONTENT);
	}
	
	/********************************* helper methods *********************************/	
	/**
	 * Retrieve a list of TextModel from TextsService by executing a HTTP GET request.
	 * This uses neither position nor size queries.
	 * @param query the URL query to use
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of TextModel object in JSON format
	 */
	public List<TextModel> listTexts(
			String query, 
			Status expectedStatus) {
		return listTexts(textWC, query, -1, -1, expectedStatus);
	}
	
	/**
	 * Retrieve a list of TextModel from TextsService by executing a HTTP GET request.
	 * @param webClient the WebClient for the TextsService
	 * @param query the URL query to use
	 * @param position the position to start a batch with
	 * @param size the size of a batch
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of TextModel objects in JSON format
	 */
	public static List<TextModel> listTexts(
			WebClient webClient, 
			String query, 
			int position,
			int size,
			Status expectedStatus) {
		System.out.println("listTexts(textWC, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + ")");
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
		List<TextModel> _texts = null;
		if (expectedStatus != null) {
			assertEquals("list() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			_texts = new ArrayList<TextModel>(webClient.getCollection(TextModel.class));
			System.out.println("listTexts(textWC, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + ") ->" + _texts.size());
		}
		return _texts;
	}

	/**
	 * Create a new TextModel on the server by executing a HTTP POST request.
	 * @param model the TextModel to post to the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created TextModel
	 */
	public TextModel postText(
			TextModel model, 
			Status expectedStatus) {
		Response _response = textWC.replacePath("/").post(model);
		if (expectedStatus != null) {
			assertEquals("create() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
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
	 * @param model the TextModel data to create on the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created TextModel
	 */
	public static TextModel postText(
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
	public static TextModel createText(
			WebClient webClient, 
			String title, 
			String description,
			Status expectedStatus) 
	{
		return postText(webClient, new TextModel(title, description), expectedStatus);
	}
	
	/**
	 * Read the TextModel with id from TextsService by executing a HTTP GET method.
	 * @param textId the id of the TextModel to retrieve
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the retrieved TextModel object in JSON format
	 */
	public TextModel getText(
			String textId, 
			Status expectedStatus) {
		return getText(textWC, textId, expectedStatus);
	}
	
	/**
	 * Read the TextModel with id from TextsService by executing a HTTP GET method.
	 * @param webClient the web client representing the TextsService
	 * @param textId the id of the TextModel to retrieve
	 * @param expectedStatus  the expected HTTP status to test on
	 * @return the retrieved TextModel object in JSON format
	 */
	public static TextModel getText(
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
	public TextModel putText(
			TextModel model, 
			Status expectedStatus) {
		return putText(textWC, model, expectedStatus);
	}
	
	/**
	 * Update a TextModel on the TextsService by executing a HTTP PUT method.
	 * @param webClient the web client representing the TextsService
	 * @param model the new TextModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated TextModel object in JSON format
	 */
	public static TextModel putText(
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
	public void deleteText(String id, Status expectedStatus) {
		deleteText(textWC, id, expectedStatus);
	}
	
	/**
	 * Delete the TextModel with id on the TextsService by executing a HTTP DELETE method.
	 * @param webClient the WebClient representing the TextsService
	 * @param textId the id of the TextModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public static void deleteText(
			WebClient webClient,
			String textId,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(textId).delete();	
		if (expectedStatus != null) {
			assertEquals("DELETE should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
	}
}
