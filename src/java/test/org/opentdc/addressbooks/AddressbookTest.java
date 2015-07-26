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
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

public class AddressbookTest extends AbstractTestClient {
	
	private WebClient addressbookWC = null;

	@Before
	public void initializeTest() {
		addressbookWC = initializeTest(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
	}
		
	@After
	public void cleanupTest() {
		addressbookWC.close();
	}
	
	/********************************** addressbook attribute tests *********************************/	
	@Test
	public void testEmptyConstructor() {
		AddressbookModel _model = new AddressbookModel();
		assertNull("id should not be set by empty constructor", _model.getId());
		assertNull("name should not be set by empty constructor", _model.getName());
	}
	
	@Test
	public void testConstructor() {		
		AddressbookModel _model = new AddressbookModel("testConstructor");
		assertNull("id should not be set by constructor", _model.getId());
		assertEquals("name should be set by constructor", "testConstructor", _model.getName());
	}
	
	@Test
	public void testId() {
		AddressbookModel _model = new AddressbookModel();
		assertNull("id should not be set by constructor", _model.getId());
		_model.setId("testId");
		assertEquals("id should have changed", "testId", _model.getId());
	}

	@Test
	public void testName() {
		AddressbookModel _model = new AddressbookModel();
		assertNull("name should not be set by empty constructor", _model.getName());
		_model.setName("testName");
		assertEquals("name should have changed", "testName", _model.getName());
	}
	
	@Test
	public void testCreatedBy() {
		AddressbookModel _model = new AddressbookModel();
		assertNull("createdBy should not be set by empty constructor", _model.getCreatedBy());
		_model.setCreatedBy("testCreatedBy");
		assertEquals("createdBy should have changed", "testCreatedBy", _model.getCreatedBy());	
	}
	
	@Test
	public void testCreatedAt() {
		AddressbookModel _model = new AddressbookModel();
		assertNull("createdAt should not be set by empty constructor", _model.getCreatedAt());
		_model.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _model.getCreatedAt());
	}
		
	@Test
	public void testModifiedBy() {
		AddressbookModel _model = new AddressbookModel();
		assertNull("modifiedBy should not be set by empty constructor", _model.getModifiedBy());
		_model.setModifiedBy("testModifiedBy");
		assertEquals("modifiedBy should have changed", "testModifiedBy", _model.getModifiedBy());	
	}
	
	@Test
	public void testModifiedAt() {
		AddressbookModel _model = new AddressbookModel();
		assertNull("modifiedAt should not be set by empty constructor", _model.getModifiedAt());
		_model.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _model.getModifiedAt());
	}

	/********************************** addressbook REST service tests *********************************/	
	@Test
	public void testCreateReadDeleteWithEmptyConstructor() {
		AddressbookModel _model1 = new AddressbookModel();
		assertNull("id should not be set by empty constructor", _model1.getId());
		assertNull("name should not be set by empty constructor", _model1.getName());

		postAddressbook(_model1, Status.BAD_REQUEST);
		_model1.setName("testCreateReadDeleteWithEmptyConstructor");
		AddressbookModel _model2 = postAddressbook(_model1, Status.OK);		
		assertNull("create() should not change the id of the local object", _model1.getId());
		assertEquals("create() should not change the name of the local object", "testCreateReadDeleteWithEmptyConstructor", _model1.getName());
		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
		assertEquals("name of returned object should remain unchanged after remote create", _model1.getName(), _model2.getName());
		AddressbookModel _model3 = getAddressbook(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("name of returned object should be unchanged after remote create", _model2.getName(), _model3.getName());
		deleteAddressbook(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateReadDelete() {
		AddressbookModel _model1 = new AddressbookModel("testCreateReadDelete");
		assertNull("id should not be set by constructor", _model1.getId());
		assertEquals("name should be set by constructor", "testCreateReadDelete", _model1.getName());

		AddressbookModel _model2 = postAddressbook(_model1, Status.OK);
		assertNull("id should be still null after remote create", _model1.getId());
		assertEquals("name should be unchanged after remote create", "testCreateReadDelete", _model1.getName());	
		assertNotNull("id of returned object should be set", _model2.getId());
		assertEquals("name of returned object should be unchanged after remote create", "testCreateReadDelete", _model2.getName());
		
		AddressbookModel _model3 = getAddressbook(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("name of returned object should be unchanged after remote create", _model2.getName(), _model3.getName());
		
		deleteAddressbook(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testClientSideId() {
		AddressbookModel _model = new AddressbookModel("testClientSideId");
		_model.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _model.getId());
		postAddressbook(_model, Status.BAD_REQUEST);
	}
	
	@Test
	public void testDuplicateId() {
		AddressbookModel _model1 = postAddressbook(new AddressbookModel("testDuplicateId1"), Status.OK);		
		AddressbookModel _model2 = new AddressbookModel("testDuplicateId2");
		_model2.setId(_model1.getId());		// wrongly create a 2nd AddressbookModel object with the same ID
		postAddressbook(_model2, Status.CONFLICT);
		deleteAddressbook(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testList() {
		List<AddressbookModel> _listBefore = listAddressbooks(null, Status.OK);
		ArrayList<AddressbookModel> _localList = new ArrayList<AddressbookModel>();	
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(postAddressbook(new AddressbookModel("testAddressbookList" + i), Status.OK));
		}
		assertEquals("correct number of addressbooks should be created", LIMIT, _localList.size());
		
		List<AddressbookModel> _listAfter = listAddressbooks(null, Status.OK);		
		assertEquals("list() should return the correct number of addressbooks", _listBefore.size() + LIMIT, _listAfter.size());
		// implicitly proven:  _remoteList.size() == _localList.size()

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (AddressbookModel _model : _listAfter) {
			_remoteListIds.add(_model.getId());
		}
		for (AddressbookModel _model : _localList) {
			assertTrue("addressbook <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
		}
		for (AddressbookModel _model : _localList) {
			getAddressbook(_model.getId(), Status.OK);
		}
		for (AddressbookModel _model : _localList) {
			deleteAddressbook(_model.getId(), Status.NO_CONTENT);
		}
	}

	@Test
	public void testCreate() {
		AddressbookModel _model1 = postAddressbook(new AddressbookModel("testCreate1"), Status.OK);
		AddressbookModel _model2 = postAddressbook(new AddressbookModel("testCreate2"), Status.OK);
		assertNotNull("ID should be set", _model1.getId());
		assertNotNull("ID should be set", _model2.getId());
		assertThat(_model2.getId(), not(equalTo(_model1.getId())));
		assertEquals("name1 should be set correctly", "testCreate1", _model1.getName());
		assertEquals("name2 should be set correctly", "testCreate2", _model2.getName());

		deleteAddressbook(_model1.getId(), Status.NO_CONTENT);
		deleteAddressbook(_model2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDoubleCreate() {
		AddressbookModel _model = postAddressbook(new AddressbookModel("testDoubleCreate"), Status.OK);
		assertNotNull("ID should be set:", _model.getId());		
		postAddressbook(_model, Status.CONFLICT);
		deleteAddressbook(_model.getId(), Status.NO_CONTENT);
	}

	@Test
	public void testRead(
	) {
		ArrayList<AddressbookModel> _localList = new ArrayList<AddressbookModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(postAddressbook(new AddressbookModel("testRead" + i), Status.OK));
		}
		for (AddressbookModel _model : _localList) {
			getAddressbook(_model.getId(), Status.OK);
		}
		List<AddressbookModel> _remoteList = listAddressbooks(null, Status.OK);
		for (AddressbookModel _model : _remoteList) {
			assertEquals("ID should be unchanged when reading an addressbook", _model.getId(), getAddressbook(_model.getId(), Status.OK).getId());						
		}
		for (AddressbookModel _model : _localList) {
			deleteAddressbook(_model.getId(), Status.NO_CONTENT);
		}
	}	

	@Test
	public void testMultiRead(
	) {
		AddressbookModel _model1 = postAddressbook(new AddressbookModel("testMultiRead"), Status.OK);
		AddressbookModel _model2 = getAddressbook(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged after read", _model1.getId(), _model2.getId());		
		AddressbookModel _model3 = getAddressbook(_model1.getId(), Status.OK);		
		assertEquals("ID should be the same", _model2.getId(), _model3.getId());
		assertEquals("name should be the same", _model2.getName(), _model3.getName());
		assertEquals("ID should be the same", _model2.getId(), _model1.getId());
		assertEquals("name should be the same", _model2.getName(), _model1.getName());
		deleteAddressbook(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testUpdate() {
		AddressbookModel _model1 = postAddressbook(new AddressbookModel("testUpdate"), Status.OK);
		_model1.setName("testUpdate1");
		AddressbookModel _model2 = putAddressbook(_model1, Status.OK);
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());	
		assertEquals("name should have changed", "testUpdate1", _model2.getName());
		_model1.setName("testUpdate2");
		AddressbookModel _model3 = putAddressbook(_model1, Status.OK);
		assertNotNull("ID should be set", _model3.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model3.getId());	
		assertEquals("name should have changed", "testUpdate2", _model3.getName());
		deleteAddressbook(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDelete() {
		AddressbookModel _model1 = postAddressbook(new AddressbookModel("testDelete"), Status.OK);
		AddressbookModel _model2 = getAddressbook(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged when reading an addressbook (remote):", _model1.getId(), _model2.getId());						
		deleteAddressbook(_model1.getId(), Status.NO_CONTENT);
		getAddressbook(_model1.getId(), Status.NOT_FOUND);
		getAddressbook(_model1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testDoubleDelete() {
		AddressbookModel _model1 = postAddressbook(new AddressbookModel("testDoubleDelete"), Status.OK);
		getAddressbook(_model1.getId(), Status.OK);
		deleteAddressbook(_model1.getId(), Status.NO_CONTENT);
		getAddressbook(_model1.getId(), Status.NOT_FOUND);
		deleteAddressbook(_model1.getId(), Status.NOT_FOUND);
		getAddressbook(_model1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testModifications() {
		AddressbookModel _model1 = postAddressbook(new AddressbookModel("testModifications"), Status.OK);
		assertNotNull("create() should set createdAt", _model1.getCreatedAt());
		assertNotNull("create() should set createdBy", _model1.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _model1.getCreatedAt(), _model1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());
		_model1.setName("testModifications2");
		AddressbookModel _model2 = putAddressbook(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
		assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
		assertThat(_model2.getModifiedAt(), not(equalTo(_model2.getCreatedAt())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_model2.getModifiedBy(), not(equalTo(_model2.getCreatedBy())));
		_model1.setModifiedBy("MYSELF");
		_model1.setModifiedAt(new Date(1000));
		AddressbookModel _model3 = putAddressbook(_model1, Status.OK);
		
		// test, that modifiedBy really ignored the client-side value "MYSELF"
		assertThat(_model1.getModifiedBy(), not(equalTo(_model3.getModifiedBy())));
		// check whether the client-side modifiedAt() is ignored
		assertThat(_model1.getModifiedAt(), not(equalTo(_model3.getModifiedAt())));
		
		deleteAddressbook(_model1.getId(), Status.NO_CONTENT);
	}
	
	/********************************** helper methods *********************************/	
	/**
	 * Retrieve a list of AddressbookModel from AddressbooksService by executing a HTTP GET request.
	 * This uses neither position nor size queries.
	 * @param query the URL query to use
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of TagTextModel object in JSON format
	 */
	public List<AddressbookModel> listAddressbooks(
			String query, 
			Status expectedStatus) {
		return listAddressbooks(addressbookWC, query, -1, Integer.MAX_VALUE, expectedStatus);
	}
	
	/**
	 * Retrieve a list of AddressbookModel from AddressbooksService by executing a HTTP GET request.
	 * @param webClient the WebClient for the AddressbooksService
	 * @param query the URL query to use
	 * @param position the position to start a batch with
	 * @param size the size of a batch
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of AddressbookModel objects in JSON format
	 */
	public static List<AddressbookModel> listAddressbooks(
			WebClient webClient, 
			String query, 
			int position,
			int size,
			Status expectedStatus) {
		System.out.println("listAddressbooks(tagWC, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + ")");
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
		List<AddressbookModel> _addressbooks = null;
		if (expectedStatus != null) {
			assertEquals("list() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			_addressbooks = new ArrayList<AddressbookModel>(webClient.getCollection(AddressbookModel.class));
			System.out.println("listTags(tagWC, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + ") ->" + _addressbooks.size());
		}
		return _addressbooks;
	}
	
	private AddressbookModel postAddressbook(
			AddressbookModel model,
			Status expectedStatus) {
		return postAddressbook(addressbookWC, model, expectedStatus);
	}

	/**
	 * Create a new AddressbookModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the AddressbooksService
	 * @param model the AddressbookModel data to create on the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created AddressbookModel
	 */
	public static AddressbookModel postAddressbook(
			WebClient webClient,
			AddressbookModel model,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").post(model);
		if (expectedStatus != null) {
			assertEquals("POST should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(AddressbookModel.class);
		} else {
			return null;
		}
	}
	
	/**
	 * Create a new AddressbookModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the AddressbooksService
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created AddressbookModel
	 */
	public static AddressbookModel createAddressbook(
			WebClient webClient,
			String addressbookName,
			Status expectedStatus) 
	{
		return postAddressbook(webClient, new AddressbookModel(addressbookName), expectedStatus);
	}
		
	/**
	 * Read the AddressbookModel with id from AddressbooksService by executing a HTTP GET method.
	 * @param id the id of the AddressbookModel to retrieve
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the retrieved AddressbookModel object in JSON format
	 */
	private AddressbookModel getAddressbook(
			String id, 
			Status expectedStatus) {
		return getAddressbook(addressbookWC, id, expectedStatus);
	}
	
	/**
	 * Read the AddressbookModel with id from AddressbooksService by executing a HTTP GET method.
	 * @param webClient the web client representing the AddressbooksService
	 * @param id the id of the AddressbookModel to retrieve
	 * @param expectedStatus  the expected HTTP status to test on
	 * @return the retrieved AddressbookModel object in JSON format
	 */
	public static AddressbookModel getAddressbook(
			WebClient webClient,
			String id,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(id).get();
		if (expectedStatus != null) {
			assertEquals("GET should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(AddressbookModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Update a AddressbookModel on the AddressbooksService by executing a HTTP PUT method.
	 * @param model the new AddressbookModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated AddressbookModel object in JSON format
	 */
	public AddressbookModel putAddressbook(
			AddressbookModel model, 
			Status expectedStatus) {
		return putAddressbook(addressbookWC, model, expectedStatus);
	}
	
	/**
	 * Update a AddressbookModel on the AddressbooksService by executing a HTTP PUT method.
	 * @param webClient the web client representing the AddressbooksService
	 * @param model the new AddressbookModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated AddressbookModel object in JSON format
	 */
	public static AddressbookModel putAddressbook(
			WebClient webClient,
			AddressbookModel model,
			Status expectedStatus) {
		webClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response _response = webClient.replacePath("/").path(model.getId()).put(model);
		if (expectedStatus != null) {
			assertEquals("PUT should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(AddressbookModel.class);
		} else {
			return null;
		}
	}
	
	/**
	 * Delete the AddressbookModel with id on the AddressbooksService by executing a HTTP DELETE method.
	 * @param id the id of the AddressbookModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public void deleteAddressbook(String id, Status expectedStatus) {
		deleteAddressbook(addressbookWC, id, expectedStatus);
	}
	
	/**
	 * Delete the AddressbookModel with id on the AddressbooksService by executing a HTTP DELETE method.
	 * @param webClient the WebClient for the AddressbooksService
	 * @param tagId the id of the AddressbookModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public static void deleteAddressbook(
			WebClient webClient,
			String id,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(id).delete();	
		if (expectedStatus != null) {
			assertEquals("DELETE should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
	}
	
	public static void cleanup(
		WebClient addressbookWC,
		String addressbookId,
		String testName) {
		addressbookWC.replacePath("/").path(addressbookId).delete();
		System.out.println(testName + " deleted Addressbook <" + addressbookId + ">.");
		addressbookWC.close();
	}	
}
