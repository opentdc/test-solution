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
package test.org.opentdc.resources;

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
import org.opentdc.addressbooks.ContactModel;
import org.opentdc.resources.ResourceModel;
import org.opentdc.resources.ResourcesService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.addressbooks.AddressbookTest;
import test.org.opentdc.addressbooks.ContactTest;

/**
 * Testing resources.
 * @author Bruno Kaiser
 *
 */
public class ResourceTest extends AbstractTestClient {
	private static final String CN = "ResourceTest";
	private static AddressbookModel adb = null;
	private static ContactModel contact = null;
	private WebClient wc = null;
	private WebClient addressbookWC = null;

	@Before
	public void initializeTests() {
		wc = initializeTest(ServiceUtil.RESOURCES_API_URL, ResourcesService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		adb = AddressbookTest.post(addressbookWC, new AddressbookModel(CN), Status.OK);
		contact = ContactTest.post(addressbookWC, adb.getId(), new ContactModel("FNAME", "LNAME"), Status.OK);
	}
	
	@After
	public void cleanupTest() {
		AddressbookTest.delete(addressbookWC, adb.getId(), Status.NO_CONTENT);
		addressbookWC.close();
		wc.close();
	}
	
	/********************************** resource attributes tests *********************************/	
	@Test
	public void testEmptyConstructor() {
		ResourceModel _model = new ResourceModel();
		assertNull("id should not be set by empty constructor", _model.getId());
		assertNull("name should not be set by empty constructor", _model.getName());
		assertNull("firstname should not be set by empty constructor", _model.getFirstName());
		assertNull("lastname should not be set by empty constructor", _model.getLastName());
		assertNull("contactId should not be set by empty constructor", _model.getContactId());
	}
	
	@Test
	public void testConstructor() {		
		ResourceModel _model = new ResourceModel("MY_NAME", "MY_ID");
		assertNull("id should not be set by constructor", _model.getId());
		assertEquals("name should be set by constructor", "MY_NAME", _model.getName());
		assertNull("firstname should be null", _model.getFirstName());
		assertNull("lastname should be null", _model.getLastName());
		assertEquals("contactId should be set by constructor", "MY_ID", _model.getContactId());
	}
	
	@Test
	public void testId() {
		ResourceModel _model = new ResourceModel();
		assertNull("id should not be set by constructor", _model.getId());
		_model.setId("testId");
		assertEquals("id should have changed:", "testId", _model.getId());
	}

	@Test
	public void testName() {
		ResourceModel _model = new ResourceModel();
		assertNull("name should not be set by empty constructor", _model.getName());
		_model.setName("testName");
		assertEquals("name should have changed:", "testName", _model.getName());
	}
	
	@Test
	public void testFirstName() {
		ResourceModel _model = new ResourceModel();
		assertNull("firstname should not be set by empty constructor", _model.getFirstName());
		_model.setFirstName("testFirstName");
		assertEquals("firstname should have changed:", "testFirstName", _model.getFirstName());
	}
	
	@Test
	public void testLastName() {
		ResourceModel _model = new ResourceModel();
		assertNull("lastname should not be set by empty constructor", _model.getLastName());
		_model.setLastName("testLastName");
		assertEquals("lastname should have changed:", "testLastName", _model.getLastName());
	}
	
	@Test
	public void testContactId() {
		ResourceModel _model = new ResourceModel();
		assertNull("contactId should not be set by empty constructor", _model.getContactId());
		_model.setContactId("testContactId");
		assertEquals("contactId should have changed:", "testContactId", _model.getContactId());
	}
	
	@Test
	public void testCreatedBy() {
		ResourceModel _model = new ResourceModel();
		assertNull("createdBy should not be set by empty constructor", _model.getCreatedBy());
		_model.setCreatedBy("testCreatedBy");
		assertEquals("createdBy should have changed", "testCreatedBy", _model.getCreatedBy());	
	}
	
	@Test
	public void testCreatedAt() {
		ResourceModel _model = new ResourceModel();
		assertNull("createdAt should not be set by empty constructor", _model.getCreatedAt());
		_model.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _model.getCreatedAt());
	}
		
	@Test
	public void testModifiedBy() {
		ResourceModel _model = new ResourceModel();
		assertNull("modifiedBy should not be set by empty constructor", _model.getModifiedBy());
		_model.setModifiedBy("testModifiedBy");
		assertEquals("modifiedBy should have changed", "testModifiedBy", _model.getModifiedBy());	
	}
	
	@Test
	public void testModifiedAt() {
		ResourceModel _model = new ResourceModel();
		assertNull("modifiedAt should not be set by empty constructor", _model.getModifiedAt());
		_model.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _model.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testCreateReadDeleteWithEmptyConstructor() {
		ResourceModel _model1 = new ResourceModel();
		assertNull("id should not be set by empty constructor", _model1.getId());
		assertNull("name should not be set by empty constructor", _model1.getName());
		assertNull("firstname should not be set by empty constructor", _model1.getFirstName());
		assertNull("lastname should not be set by empty constructor", _model1.getLastName());
		assertNull("contactId should not be set by empty constructor", _model1.getContactId());
		
		post(_model1, Status.BAD_REQUEST);
		_model1.setName("testCreateReadDeleteWithEmptyConstructor");
		post(_model1, Status.BAD_REQUEST);
		_model1.setContactId(contact.getId());
		ResourceModel _model2 = post(_model1, Status.OK);
		
		assertNull("create() should not change the id of the local object", _model1.getId());
		assertEquals("create() should not change the name of the local object", "testCreateReadDeleteWithEmptyConstructor", _model1.getName());
		assertNull("create() should not change the firstname of the local object", _model1.getFirstName());
		assertNull("create() should not change the lastname of the local object", _model1.getLastName());
		assertEquals("create() should not change the contactId of the local object", contact.getId(), _model1.getContactId());
		
		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
		assertEquals("create() should not change the name of the remote object", "testCreateReadDeleteWithEmptyConstructor", _model2.getName());
		assertEquals("create() should not change the firstname of the remote object", contact.getFirstName(), _model2.getFirstName());
		assertEquals("create() should not change the lastname of the remote object", contact.getLastName(), _model2.getLastName());
		assertEquals("create() should not change the contactId of the remote object", contact.getId(), _model2.getContactId());

		ResourceModel _model3 = get(_model2.getId(), Status.OK);
		assertEquals("read() should not change the id", _model2.getId(), _model3.getId());
		assertEquals("read() should not change the name", _model2.getName(), _model3.getName());
		assertEquals("read() should not change the firstName", _model2.getFirstName(), _model3.getFirstName());
		assertEquals("read() should not change the lastName", _model2.getLastName(), _model3.getLastName());
		assertEquals("read() should not change the contactId", _model2.getContactId(), _model3.getContactId());
		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateReadDelete() {
		ResourceModel _model1 = new ResourceModel("testCreateReadDelete", contact.getId());
		assertNull("id should not be set by constructor", _model1.getId());
		assertEquals("name should be set by constructor", "testCreateReadDelete", _model1.getName());
		assertEquals("contactId should be set by constructor", contact.getId(), _model1.getContactId());
		assertNull("firstName should be null", _model1.getFirstName());
		assertNull("lastName should be null", _model1.getLastName());

		ResourceModel _model2 = post(_model1, Status.OK);
		assertNull("id should be still null after remote create", _model1.getId());
		assertEquals("name should be unchanged after remote create", "testCreateReadDelete", _model1.getName());
		assertEquals("contactId should be unchanged after remote create", contact.getId(), _model1.getContactId());
		assertNull("firstname should be unchanged", _model1.getFirstName());
		assertNull("lastname should be unchanged", _model1.getLastName());
		
		assertNotNull("id of returned object should be set", _model2.getId());
		assertEquals("name of returned object should be unchanged after remote create", "testCreateReadDelete", _model2.getName());
		assertEquals("contactId should be unchanged", contact.getId(), _model2.getContactId());
		assertEquals("firstname should be derived", contact.getFirstName(), _model2.getFirstName());
		assertEquals("lastname should be derived", contact.getLastName(), _model2.getLastName());

		ResourceModel _model3 = get(_model2.getId(), Status.OK);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("name of returned object should be unchanged after remote create", _model2.getName(), _model3.getName());
		assertEquals("firstname of returned object should be unchanged after remote create", _model2.getFirstName(), _model3.getFirstName());
		assertEquals("lastname of returned object should be unchanged after remote create", _model2.getLastName(), _model3.getLastName());
		assertEquals("contactId of returned object should be unchanged after remote create", _model2.getContactId(), _model3.getContactId());
		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testClientSideId() {
		ResourceModel _model = create("testClientSideId", 1);
		_model.setId("testClientSideId");
		assertEquals("id should have changed", "testClientSideId", _model.getId());
		post(_model, Status.BAD_REQUEST);
	}
	
	@Test
	public void testDuplicateId() {
		ResourceModel _model1 = post(create("testDuplicateId", 1), Status.OK);
		ResourceModel _model2 = create("testDuplicateId", 2);
		_model2.setId(_model1.getId());		// wrongly create a 2nd ResourceModel object with the same ID
		post(_model2, Status.CONFLICT);
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testList() {	
		List<ResourceModel> _listBefore = list(null, Status.OK);
		ArrayList<ResourceModel> _localList = new ArrayList<ResourceModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(post(create("testList", i), Status.OK));
		}
		assertEquals("correct number of resources should be created", LIMIT, _localList.size());
		
		List<ResourceModel> _listAfter = list(null, Status.OK);		
		assertEquals("list() should return the correct number of resources", _listBefore.size() + LIMIT, _listAfter.size());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (ResourceModel _model : _listAfter) {
			_remoteListIds.add(_model.getId());
		}
		for (ResourceModel _model : _localList) {
			assertTrue("resource <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
		}
		for (ResourceModel _model : _localList) {
			get(_model.getId(), Status.OK);
		}
		for (ResourceModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}
	}
	
	@Test
	public void testCreate() {
		ResourceModel _model1 = post(create("testCreate", 1), Status.OK);
		ResourceModel _model2 = post(create("testCreate", 2), Status.OK);
				
		assertNotNull("ID should be set", _model1.getId());
		assertEquals("name1 should be set correctly", "testCreate1", _model1.getName());
		assertEquals("firstname1 should be set correctly", "testCreate1", _model1.getFirstName());
		assertEquals("lastname1 should be set correctly", "testCreate1", _model1.getLastName());
		assertEquals("contactId1 should be set correctly", _model1.getContactId(), _model1.getContactId());
		
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("name2 should be set correctly", "testCreate2", _model2.getName());
		assertEquals("firstname2 should be set correctly", "testCreate2", _model2.getFirstName());
		assertEquals("lastname2 should be set correctly", "testCreate2", _model2.getLastName());
		assertEquals("contactId2 should be set correctly", _model2.getContactId(), _model2.getContactId());

		assertThat(_model1.getId(), not(equalTo(_model2.getId())));
		delete(_model1.getId(), Status.NO_CONTENT);
		delete(_model2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDoubleCreate() {
		ResourceModel _model = post(create("testDoubleCreate", 1), Status.OK);
		assertNotNull("ID should be set:", _model.getId());		
		post(_model, Status.CONFLICT);
		delete(_model.getId(), Status.NO_CONTENT);
	}

	@Test
	public void testRead() {
		ArrayList<ResourceModel> _localList = new ArrayList<ResourceModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(post(create("testRead", i), Status.OK));
		}
		for (ResourceModel _model : _localList) {
			get(_model.getId(), Status.OK);
		}
		List<ResourceModel> _remoteList = list(null, Status.OK);
		for (ResourceModel _model : _remoteList) {
			assertEquals("ID should be unchanged when reading an addressbook", _model.getId(), get(_model.getId(), Status.OK).getId());						
		}
		for (ResourceModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}
	}	

	@Test
	public void testMultiRead() {
		ResourceModel _model1 = post(create("testMultiRead", 1), Status.OK);
		ResourceModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged after read", _model1.getId(), _model2.getId());		
		ResourceModel _model3 = get(_model1.getId(), Status.OK);		
		assertEquals("ID should be the same", _model2.getId(), _model3.getId());
		assertEquals("name should be the same", _model2.getName(), _model3.getName());
		assertEquals("firstname should be the same", _model2.getFirstName(), _model3.getFirstName());
		assertEquals("lastname should be the same", _model2.getLastName(), _model3.getLastName());
		assertEquals("contactId should be the same", _model2.getContactId(), _model3.getContactId());
		
		assertEquals("ID should be the same", _model2.getId(), _model1.getId());
		assertEquals("name should be the same", _model2.getName(), _model1.getName());
		assertEquals("firstname should be the same", _model2.getFirstName(), _model1.getFirstName());
		assertEquals("lastname should be the same", _model2.getLastName(), _model1.getLastName());
		assertEquals("contactId should be the same", _model2.getContactId(), _model1.getContactId());
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testUpdate() {
		ResourceModel _model1 = post(create("testUpdate", 1), Status.OK);
		_model1.setName("testUpdate2");
		ContactModel _cm1 = ContactTest.post(addressbookWC, adb.getId(), new ContactModel("testUpdate3", "testUpdate4"), Status.OK);
		_model1.setContactId(_cm1.getId());
		ResourceModel _model2 = put(_model1, Status.OK);

		assertNotNull("ID should be set", _model2.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());	
		assertEquals("name should have changed", "testUpdate2", _model2.getName());
		assertEquals("firstname should have changed", "testUpdate3", _model2.getFirstName());
		assertEquals("lastname should have changed", "testUpdate4", _model2.getLastName());
		assertEquals("contactId should have changed", _cm1.getId(), _model2.getContactId());
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDerivedFields() {
		ResourceModel _model1 = post(create("testDerivedFirstName", 1), Status.OK);
		_model1.setName("testDerivedFirstName2");
		_model1.setFirstName("FIRSTNAME_SHOULD_BE_IGNORED");
		_model1.setLastName("LASTNAME_SHOULD_BE_IGNORED");
		ContactModel _cm1 = ContactTest.post(addressbookWC, adb.getId(), new ContactModel("testDerivedFirstName3", "testDerivedFirstName4"), Status.OK);
		_model1.setContactId(_cm1.getId());
		ResourceModel _model2 = put(_model1, Status.OK);

		assertNotNull("ID should be set", _model2.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());	
		assertEquals("name should have changed", "testDerivedFirstName2", _model2.getName());
		assertEquals("firstname should have changed", "testDerivedFirstName3", _model2.getFirstName());
		assertEquals("lastname should have changed", "testDerivedFirstName4", _model2.getLastName());
		assertEquals("contactId should have changed", _cm1.getId(), _model2.getContactId());
		delete(_model1.getId(), Status.NO_CONTENT);	
	}
	
	@Test
	public void testDelete() {
		ResourceModel _model1 = post(create("testDelete", 1), Status.OK);
		ResourceModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged when reading a resource", _model1.getId(), _model2.getId());						
		delete(_model1.getId(), Status.NO_CONTENT);
		get(_model1.getId(), Status.NOT_FOUND);
		get(_model1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testDoubleDelete() {
		ResourceModel _model1 = post(create("testDoubleDelete", 1), Status.OK);
		get(_model1.getId(), Status.OK);
		delete(_model1.getId(), Status.NO_CONTENT);
		get(_model1.getId(), Status.NOT_FOUND);
		delete(_model1.getId(), Status.NOT_FOUND);
		get(_model1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testModifications() {
		ResourceModel _model1 = post(create("testModifications", 1), Status.OK);
		assertNotNull("create() should set createdAt", _model1.getCreatedAt());
		assertNotNull("create() should set createdBy", _model1.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _model1.getCreatedAt(), _model1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());
		_model1.setName("testModifications2");
		ResourceModel _model2 = put(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
		assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
		assertThat(_model2.getModifiedAt(), not(equalTo(_model2.getCreatedAt())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_model2.getModifiedBy(), not(equalTo(_model2.getCreatedBy())));
		_model1.setModifiedBy("MYSELF");
		_model1.setModifiedAt(new Date(1000));
		ResourceModel _model3 = put(_model1, Status.OK);
		
		// test, that modifiedBy really ignored the client-side value "MYSELF"
		assertThat(_model1.getModifiedBy(), not(equalTo(_model3.getModifiedBy())));
		// check whether the client-side modifiedAt() is ignored
		assertThat(_model1.getModifiedAt(), not(equalTo(_model3.getModifiedAt())));
		
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	/********************************* helper methods *********************************/	
	/**
	 * Retrieve a list of ResourceModel from ResourcesService by executing a HTTP GET request.
	 * This uses neither position nor size queries.
	 * @param query the URL query to use
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of ResourceModel objects in JSON format
	 */
	public List<ResourceModel> list(
			String query, 
			Status expectedStatus) {
		return list(wc, query, -1, Integer.MAX_VALUE, expectedStatus);
	}
	
	/**
	 * Retrieve a list of ResourceModel from ResourcesService by executing a HTTP GET request.
	 * @param webClient the WebClient for the ResourcesService
	 * @param query the URL query to use
	 * @param position the position to start a batch with
	 * @param size the size of a batch
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of ResourceModel objects in JSON format
	 */
	public static List<ResourceModel> list(
			WebClient webClient, 
			String query, 
			int position,
			int size,
			Status expectedStatus) {
		webClient.resetQuery();
		webClient.replacePath("/");
		Response _response = executeListQuery(webClient, query, position, size);
		List<ResourceModel> _list = null;
		if (expectedStatus != null) {
			assertEquals("list() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			_list = new ArrayList<ResourceModel>(webClient.getCollection(ResourceModel.class));
			System.out.println("list(webClient, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + ") ->" + _list.size());
		}
		return _list;
	}
	
	/**
	 * Create a new ResourceModel on the server by executing a HTTP POST request.
	 * @param model the ResourceModel to post to the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created ResourceModel
	 */
	public ResourceModel post(
			ResourceModel model, 
			Status expectedStatus) {
		return post(wc, model, expectedStatus);
	}
	
	/**
	 * Create a new ResourceModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the ResourceService
	 * @param model the ResourceModel data to create on the server
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the created ResourceModel
	 */
	public static ResourceModel post(
			WebClient webClient,
			ResourceModel model,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").post(model);
		assertEquals("POST should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(ResourceModel.class);
		} else {
			return null;
		}
	}
	
	/**
	 * Create a new ResourceModel object (not posted)
	 * @param name the resource name
	 * @param suffix a suffix that is appended to the resource name
	 * @return the allocated (but not posted) ResourceModel object
	 */
	public ResourceModel create(
			String name, 
			int suffix) {
		ContactModel _cm = ContactTest.post(addressbookWC, adb.getId(), new ContactModel(name + suffix, name + suffix), Status.OK);
		return new ResourceModel(name + suffix, _cm.getId());
	}	
	
	/**
	 * Create a new ResourceModel object and post it to the server.
	 * @param resourceWC	the WebClient representing the resource services
	 * @param resourceName the resource name
	 * @param contactId	the id of the contact
	 * @param expectedStatus the expected HTTP status to test on
	 * @return
	 */
	public static ResourceModel create(
			WebClient resourceWC, 
			String resourceName,
			String contactId,
			Status expectedStatus) {
		return post(resourceWC, new ResourceModel(resourceName, contactId), expectedStatus);
	}

	/**
	 * Read the ResourceModel with id from ResourcesService by executing a HTTP GET method.
	 * @param id the id of the ResourceModel to retrieve
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the retrieved ResourceModel object in JSON format
	 */
	private ResourceModel get(
			String id, 
			Status expectedStatus) {
		return get(wc, id, expectedStatus);
	}
	
	/**
	 * Update a ResourceModel on the ResourcesService by executing a HTTP PUT method.
	 * @param model the new ResourceModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated ResourceModel object in JSON format
	 */
	public ResourceModel put(
			ResourceModel model, 
			Status expectedStatus) {
		return put(wc, model, expectedStatus);
	}
	
	/**
	 * Update a ResourceModel on the ResourcesService by executing a HTTP PUT method.
	 * @param webClient the web client representing the ResourcesService
	 * @param model the new ResourceModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated ResourceModel object in JSON format
	 */
	public static ResourceModel put(
			WebClient webClient,
			ResourceModel model,
			Status expectedStatus) {
		webClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response _response = webClient.replacePath("/").path(model.getId()).put(model);
		if (expectedStatus != null) {
			assertEquals("PUT should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(ResourceModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Delete the ResourceModel with id on the ResourcesService by executing a HTTP DELETE method.
	 * @param id the id of the ResourceModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public void delete(String id, Status expectedStatus) {
		delete(wc, id, expectedStatus);
	}
	
	/**
	 * Delete the ResourceModel with id on the ResourcesService by executing a HTTP DELETE method.
	 * @param webClient the WebClient for the ResourcesService
	 * @param id the id of the ResourceModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public static void delete(
			WebClient webClient,
			String id,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(id).delete();	
		if (expectedStatus != null) {
			assertEquals("DELETE should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
	}

	/**
	 * Read the ResourceModel with id from ResourcesService by executing a HTTP GET method.
	 * @param webClient the web client representing the ResourcesService
	 * @param id the id of the ResourceModel to retrieve
	 * @param expectedStatus  the expected HTTP status to test on
	 * @return the retrieved ResourceModel object in JSON format
	 */
	public static ResourceModel get(
			WebClient webClient,
			String id,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(id).get();
		if (expectedStatus != null) {
			assertEquals("GET should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(ResourceModel.class);
		} else {
			return null;
		}
	}
		
	/* (non-Javadoc)
	 * @see test.org.opentdc.AbstractTestClient#calculateMembers()
	 */
	protected int calculateMembers() {
		return 1;
	}
}
