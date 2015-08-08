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
package test.org.opentdc.gifts;

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
import org.opentdc.gifts.GiftModel;
import org.opentdc.gifts.GiftsService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

/*
 * Tests the Gifts-Service.
 * @author Bruno Kaiser
 */
public class GiftsTest extends AbstractTestClient {
	public static final String API_URL = "api/gift/";
	private WebClient giftWC = null;

	/**
	 * Initializes the test case.
	 */
	@Before
	public void initializeTest() {
		giftWC = initializeTest(ServiceUtil.GIFTS_API_URL, GiftsService.class);
	}
	
	/**
	 * Cleanup all resources needed by the test case.
	 */
	@After
	public void cleanupTest() {
		giftWC.close();
	}

	/********************************** gift attributes tests *********************************/	
	/**
	 * Test the empty constructor.
	 */
	@Test
	public void testEmptyConstructor() {
		GiftModel _model = new GiftModel();
		assertNull("id should not be set by empty constructor", _model.getId());
		assertNull("title should not be set by empty constructor", _model.getTitle());
		assertNull("description should not be set by empty constructor", _model.getDescription());
	}
	
	/**
	 * Test the custom constructor.
	 */
	@Test
	public void testConstructor() {		
		GiftModel _model = new GiftModel("testConstructor", "testConstructor");
		assertNull("id should not be set by constructor", _model.getId());
		assertEquals("title should be set by constructor", "testConstructor", _model.getTitle());
		assertEquals("description should not set by constructor", "testConstructor", _model.getDescription());
	}
	
	/**
	 * Test id attribute.
	 */
	@Test
	public void testId() {
		GiftModel _model = new GiftModel();
		assertNull("id should not be set by constructor", _model.getId());
		_model.setId("testId");
		assertEquals("id should have changed", "testId", _model.getId());
	}

	/**
	 * Test title attribute.
	 */
	@Test
	public void testTitle() {
		GiftModel _model = new GiftModel();
		assertNull("title should not be set by empty constructor", _model.getTitle());
		_model.setTitle("testTitle");
		assertEquals("title should have changed", "testTitle", _model.getTitle());
	}
	
	/**
	 * Test createdBy attribute.
	 */
	@Test
	public void testCreatedBy() {
		GiftModel _model = new GiftModel();
		assertNull("createdBy should not be set by empty constructor", _model.getCreatedBy());
		_model.setCreatedBy("testCreatedBy");
		assertEquals("createdBy should have changed", "testCreatedBy", _model.getCreatedBy());	
	}
	
	/**
	 * Test createdAt attribute.
	 */
	@Test
	public void testCreatedAt() {
		GiftModel _model = new GiftModel();
		assertNull("createdAt should not be set by empty constructor", _model.getCreatedAt());
		_model.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _model.getCreatedAt());
	}
		
	/**
	 * Test modifiedBy attribute.
	 */
	@Test
	public void testModifiedBy() {
		GiftModel _model = new GiftModel();
		assertNull("modifiedBy should not be set by empty constructor", _model.getModifiedBy());
		_model.setModifiedBy("testModifiedBy");
		assertEquals("modifiedBy should have changed", "testModifiedBy", _model.getModifiedBy());	
	}
	
	/**
	 * Test modifiedAt attribute.
	 */
	@Test
	public void testModifiedAt() {
		GiftModel _model = new GiftModel();
		assertNull("modifiedAt should not be set by empty constructor", _model.getModifiedAt());
		_model.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _model.getModifiedAt());
	}

	/**
	 * Test description attribute.
	 */
	@Test
	public void testDescription() {
		GiftModel _model = new GiftModel();
		assertNull("description should not be set by empty constructor", _model.getDescription());
		_model.setDescription("testDescription");
		assertEquals("description should have changed", "testDescription", _model.getDescription());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testCreateReadDeleteWithEmptyConstructor() {
		GiftModel _model1 = new GiftModel();
		assertNull("id should not be set by empty constructor", _model1.getId());
		assertNull("title should not be set by empty constructor", _model1.getTitle());
		assertNull("description should not be set by empty constructor", _model1.getDescription());

		postGift(_model1, Status.BAD_REQUEST);
		_model1.setTitle("testCreateReadDeleteWithEmptyConstructor");
		
		GiftModel _model2 = postGift(_model1, Status.OK);
		assertNull("create() should not change the id of the local object", _model1.getId());
		assertEquals("create() should not change the title of the local object", "testCreateReadDeleteWithEmptyConstructor", _model1.getTitle());
		assertNull("create() should not change the description of the local object", _model1.getDescription());
		
		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
		assertEquals("create() should not change the title", "testCreateReadDeleteWithEmptyConstructor", _model2.getTitle());
		assertNull("create() should not change the description", _model2.getDescription());
		
		GiftModel _model3 = getGift(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("title of returned object should be unchanged", _model2.getTitle(), _model3.getTitle());
		assertEquals("description of returned object should be unchanged", _model2.getDescription(), _model3.getDescription());
		deleteGift(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateReadDelete() {
		GiftModel _model1 = new GiftModel("testCreateReadDelete", "testCreateReadDelete");
		assertNull("id should not be set by constructor", _model1.getId());
		assertEquals("title should be set by constructor", "testCreateReadDelete", _model1.getTitle());
		assertEquals("description should be set by constructor", "testCreateReadDelete", _model1.getDescription());

		GiftModel _model2 = postGift(_model1, Status.OK);
		assertNull("id should be still null after remote create", _model1.getId());
		assertEquals("title should be unchanged after remote create", "testCreateReadDelete", _model1.getTitle());
		assertEquals("description should be unchanged after remote create", "testCreateReadDelete", _model1.getDescription());
		assertNotNull("id of returned object should be set", _model2.getId());
		assertEquals("title of returned object should be unchanged after remote create", "testCreateReadDelete", _model2.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", "testCreateReadDelete", _model2.getDescription());

		GiftModel _model3 = getGift(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("title of returned object should be unchanged after remote create", _model2.getTitle(), _model3.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", _model2.getDescription(), _model3.getDescription());
		deleteGift(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateWithClientSideId() {
		GiftModel _model = new GiftModel("testCreateWithClientSideId", "testCreateWithClientSideId");
		_model.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _model.getId());
		postGift(_model, Status.BAD_REQUEST);
	}
	
	@Test
	public void testCreateWithDuplicateId() {
		GiftModel _model1 = postGift(new GiftModel("testCreateWithDuplicateId1", "testCreateWithDuplicateId1"), Status.OK);
		GiftModel _model2 = postGift(new GiftModel("testCreateWithDuplicateId2", "testCreateWithDuplicateId2"), Status.OK);
		String _model2Id = _model2.getId();
		_model2.setId(_model1.getId());		// wrongly create a 2nd GiftModel object with the same ID
		postGift(_model2, Status.CONFLICT);
		deleteGift(_model1.getId(), Status.NO_CONTENT);
		deleteGift(_model2Id, Status.NO_CONTENT);
	}
	
	@Test
	public void testList() {		
		ArrayList<GiftModel> _localList = new ArrayList<GiftModel>();
		System.out.println("testList:");
		System.out.println("a) creating " + LIMIT + " gift objects");
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(postGift(
				new GiftModel("testList", "testList"),
				Status.OK));
		}
		List<GiftModel> _remoteList = listGifts(null, Status.OK);

		System.out.println("b) results from list() (_remoteList):");
		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (GiftModel _model : _remoteList) {
			_remoteListIds.add(_model.getId());
			System.out.println("\t" + _model.getId() + "/" + _model.getTitle());
		}
		System.out.println("\ttotal: " + _remoteList.size() + " elements");
		
		System.out.println("c) list of created objects (_localList):");
		for (GiftModel _model : _localList) {
			assertTrue("gift <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
			System.out.println("\t" + _model.getId() + "/" + _model.getTitle());
		}
		System.out.println("\ttotal: " + _localList.size() + " elements");
		System.out.println("d) reading each object in the list");
		for (GiftModel _model : _localList) {
			getGift(_model.getId(), Status.OK);
			System.out.println("\t" + _model.getId() + "/" + _model.getTitle());
		}
		System.out.println("\ttotal: " + _localList.size() + " elements");
		System.out.println("e) deleting each object");
		for (GiftModel _model : _localList) {
			deleteGift(_model.getId(), Status.NO_CONTENT);
			System.out.println("\t" + _model.getId() + "/" + _model.getTitle());
		}
		System.out.println("\ttotal: " + _localList.size() + " elements");
	}
		
	@Test
	public void testCreate() {
		GiftModel _model1 = postGift(new GiftModel("testCreate1", "testCreate2"), Status.OK);
		GiftModel _model2 = postGift(new GiftModel("testCreate3", "testCreate4"), Status.OK);
		assertNotNull("ID should be set", _model1.getId());
		assertNotNull("ID should be set", _model2.getId());
		assertThat(_model1.getId(), not(equalTo(_model2.getId())));

		assertEquals("title should be set correctly", "testCreate1", _model1.getTitle());
		assertEquals("description should be set correctly", "testCreate2", _model1.getDescription());
		assertEquals("title should be set correctly", "testCreate3", _model2.getTitle());
		assertEquals("description should be set correctly", "testCreate4", _model2.getDescription());

		deleteGift(_model1.getId(), Status.NO_CONTENT);
		deleteGift(_model2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDoubleCreate() {
		GiftModel _model = postGift(new GiftModel("testDoubleCreate1", "testDoubleCreate2"), Status.OK);
		assertNotNull("ID should be set", _model.getId());
		postGift(_model, Status.CONFLICT);
		deleteGift(_model.getId(), Status.NO_CONTENT);
	}

	@Test
	public void testRead() {
		ArrayList<GiftModel> _localList = new ArrayList<GiftModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(postGift(new GiftModel("testRead" + i, "testRead"), Status.OK));
		}
		// test read on each local element
		for (GiftModel _model : _localList) {
			getGift(_model.getId(), Status.OK);
		}
		// test read on each listed element
		for (GiftModel _model : listGifts(null, Status.OK)) {
			assertEquals("ID should be unchanged when reading a gift", _model.getId(), getGift(_model.getId(), Status.OK).getId());
		}
		for (GiftModel _model : _localList) {
			deleteGift(_model.getId(), Status.NO_CONTENT);
		}
	}	

	@Test
	public void testMultiRead() {
		GiftModel _model1 = postGift(new GiftModel("testMultiRead", "testMultiRead"), Status.OK);
		GiftModel _model2 = getGift(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged after read", _model1.getId(), _model2.getId());		
		GiftModel _model3 = getGift(_model1.getId(), Status.OK);
		
		assertEquals("ID should be the same", _model3.getId(), _model2.getId());
		assertEquals("title should be the same", _model3.getTitle(), _model2.getTitle());
		assertEquals("description should be the same", _model3.getDescription(), _model2.getDescription());
		
		assertEquals("ID should be the same:", _model1.getId(), _model2.getId());
		assertEquals("title should be the same:", _model1.getTitle(), _model2.getTitle());
		assertEquals("description should be the same:", _model1.getDescription(), _model2.getDescription());
		deleteGift(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testUpdate(
	) {
		GiftModel _model1 = postGift(new GiftModel("testUpdate1", "testUpdate2"), Status.OK);

		_model1.setTitle("testUpdate3");
		_model1.setDescription("testUpdate4");
		GiftModel _model2 = putGift(_model1, Status.OK);
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());	
		assertEquals("title should have changed", "testUpdate3", _model2.getTitle());
		assertEquals("description should have changed", "testUpdate4", _model2.getDescription());

		_model1.setTitle("testUpdate5");
		_model1.setDescription("testUpdate6");
		GiftModel _model3 = putGift(_model1, Status.OK);
		assertNotNull("ID should be set", _model3.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model3.getId());	
		assertEquals("title should have changed", "testUpdate5", _model3.getTitle());
		assertEquals("description should have changed", "testUpdate6", _model3.getDescription());

		deleteGift(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDelete() {
		GiftModel _model1 = postGift(new GiftModel("testDelete", "testDelete"), Status.OK);
		GiftModel _model2 = getGift(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged when reading a gift (remote):", _model1.getId(), _model2.getId());						
		deleteGift(_model1.getId(), Status.NO_CONTENT);
		getGift(_model1.getId(), Status.NOT_FOUND);
		getGift(_model1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testDoubleDelete() {
		GiftModel _model = postGift(new GiftModel("testDoubleDelete", "testDoubleDelete"), Status.OK);
		getGift(_model.getId(), Status.OK);
		deleteGift(_model.getId(), Status.NO_CONTENT);
		getGift(_model.getId(), Status.NOT_FOUND);
		deleteGift(_model.getId(), Status.NOT_FOUND);
		getGift(_model.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testModifications() {
		GiftModel _model1 = postGift(new GiftModel("testModifications", "testModifications"), Status.OK);
		assertNotNull("create() should set createdAt", _model1.getCreatedAt());
		assertNotNull("create() should set createdBy", _model1.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _model1.getCreatedAt(), _model1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());
		_model1.setDescription("updated");
		GiftModel _model2 = putGift(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
		assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
		// next test fails because of timing issue; but we do not want to introduce a sleep() here
		// assertThat(_model2.getModifiedAt().toString(), not(equalTo(_model2.getCreatedAt().toString())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_model2.getModifiedBy(), not(equalTo(_model2.getCreatedBy())));

		String _createdBy = _model1.getCreatedBy();
		_model1.setCreatedBy("testModifications3");
		GiftModel _model3 = putGift(_model1, Status.OK);
		assertEquals("update() should not change createdBy", _createdBy, _model3.getCreatedBy());

		Date _createdAt = _model1.getCreatedAt();
		_model1.setCreatedAt(new Date(1000));
		GiftModel _model4 = putGift(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _createdAt, _model4.getCreatedAt());

		String _modifiedBy = _model1.getModifiedBy();
		_model1.setModifiedBy("testModifications5");
		GiftModel _model5 = putGift(_model1, Status.OK);
		assertEquals("update() should not change modifiedBy", _modifiedBy, _model5.getModifiedBy());

		Date _modifiedAt = _model1.getModifiedAt();
		Date _modifiedAt2 = new Date(1000);
		_model1.setModifiedAt(_modifiedAt2);
		GiftModel _model6 = putGift(_model1, Status.OK);
		assertThat(_model6.getModifiedAt(), not(equalTo(_modifiedAt)));
		assertThat(_model6.getModifiedAt(), not(equalTo(_modifiedAt2)));

		deleteGift(_model1.getId(), Status.NO_CONTENT);
	}
	
	/********************************* helper methods *********************************/	
	/**
	 * Retrieve a list of GiftModel from GiftsService by executing a HTTP GET request.
	 * This uses neither position nor size queries.
	 * @param query the URL query to use
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of GiftModel object in JSON format
	 */
	public List<GiftModel> listGifts(
			String query, 
			Status expectedStatus) {
		return listGifts(giftWC, query, -1, -1, expectedStatus);
	}
	
	/**
	 * Retrieve a list of GiftModel from GiftsService by executing a HTTP GET request.
	 * @param webClient the WebClient for the GiftsService
	 * @param query the URL query to use
	 * @param position the position to start a batch with
	 * @param size the size of a batch
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of GiftModel objects in JSON format
	 */
	public static List<GiftModel> listGifts(
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
		List<GiftModel> _gifts = null;
		if (expectedStatus != null) {
			assertEquals("list() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			_gifts = new ArrayList<GiftModel>(webClient.getCollection(GiftModel.class));
			System.out.println("listGifts(giftWC, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + ") ->" + _gifts.size());
		}
		return _gifts;
	}

	/**
	 * Create a new GiftModel on the server by executing a HTTP POST request.
	 * @param model the GiftModel to post to the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created GiftModel
	 */
	public GiftModel postGift(
			GiftModel model, 
			Status expectedStatus) {
		Response _response = giftWC.replacePath("/").post(model);
		if (expectedStatus != null) {
			assertEquals("create() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(GiftModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Create a new GiftModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the GiftsService
	 * @param model the GiftModel data to create on the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created GiftModel
	 */
	public static GiftModel postGift(
			WebClient webClient,
			GiftModel model,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").post(model);
		if (expectedStatus != null) {
			assertEquals("POST should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(GiftModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Create a new GiftModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the GiftsService
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created GiftModel
	 */
	public static GiftModel createGift(
			WebClient webClient, 
			String title, 
			String description,
			Status expectedStatus) 
	{
		return postGift(webClient, new GiftModel(title, description), expectedStatus);
	}
	
	/**
	 * Read the GiftModel with id from GiftsService by executing a HTTP GET method.
	 * @param giftId the id of the GiftModel to retrieve
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the retrieved GiftModel object in JSON format
	 */
	public GiftModel getGift(
			String giftId, 
			Status expectedStatus) {
		return getGift(giftWC, giftId, expectedStatus);
	}
	
	/**
	 * Read the GiftModel with id from GiftsService by executing a HTTP GET method.
	 * @param webClient the web client representing the GiftsService
	 * @param giftId the id of the GiftModel to retrieve
	 * @param expectedStatus  the expected HTTP status to test on
	 * @return the retrieved GiftModel object in JSON format
	 */
	public static GiftModel getGift(
			WebClient webClient,
			String giftId,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(giftId).get();
		if (expectedStatus != null) {
			assertEquals("GET should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(GiftModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Update a GiftModel on the GiftsService by executing a HTTP PUT method.
	 * @param tm the new GiftModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated GiftModel object in JSON format
	 */
	public GiftModel putGift(
			GiftModel model, 
			Status expectedStatus) {
		return putGift(giftWC, model, expectedStatus);
	}
	
	/**
	 * Update a GiftModel on the GiftsService by executing a HTTP PUT method.
	 * @param webClient the web client representing the GiftsService
	 * @param model the new GiftModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated GiftModel object in JSON format
	 */
	public static GiftModel putGift(
			WebClient webClient,
			GiftModel model,
			Status expectedStatus) {
		webClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response _response = webClient.replacePath("/").path(model.getId()).put(model);
		if (expectedStatus != null) {
			assertEquals("PUT should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(GiftModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Delete the GiftModel with id on the GiftsService by executing a HTTP DELETE method.
	 * @param id the id of the GiftModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public void deleteGift(String id, Status expectedStatus) {
		deleteGift(giftWC, id, expectedStatus);
	}
	
	/**
	 * Delete the GiftModel with id on the GiftsService by executing a HTTP DELETE method.
	 * @param webClient the WebClient representing the GiftsService
	 * @param giftId the id of the GiftModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public static void deleteGift(
			WebClient webClient,
			String giftId,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(giftId).delete();	
		if (expectedStatus != null) {
			assertEquals("DELETE should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
	}
	
	protected int calculateMembers() {
		return 0;
	}
}
