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
package test.org.opentdc.users;

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
import org.opentdc.service.ServiceUtil;
import org.opentdc.users.UserModel;
import org.opentdc.users.UsersService;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.addressbooks.AddressbookTest;
import test.org.opentdc.addressbooks.ContactTest;

/**
 * Testing the UsersService
 * @author Bruno Kaiser
 *
 */
public class UserTest extends AbstractTestClient {
	private static final String CN = "UserTest";
	private WebClient wc = null;
	private static AddressbookModel adb = null;
	private static ContactModel contact = null;
	private WebClient addressbookWC = null;

	@Before
	public void initializeTests() {
		wc = initializeTest(ServiceUtil.USERS_API_URL, UsersService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		adb = AddressbookTest.post(addressbookWC, new AddressbookModel(CN), Status.OK);
		contact = ContactTest.post(addressbookWC, adb.getId(), new ContactModel(CN + "1", CN + "2"), Status.OK);
	}
	
	@After
	public void cleanupTest() {
		AddressbookTest.delete(addressbookWC, adb.getId(), Status.NO_CONTENT);
		addressbookWC.close();
		wc.close();
	}
	
	/********************************** users attributes tests *********************************/	
	@Test
	public void testEmptyConstructor() {
		UserModel _model = new UserModel();
		assertNull("id should not be set", _model.getId());
		assertNull("loginId should not be set", _model.getLoginId());
		assertNull("contactId should not be set", _model.getContactId());
		assertNull("hashedPassword should not be set", _model.getHashedPassword());
		assertNull("salt should not be set", _model.getSalt());
		assertNull("authType should not be set", _model.getAuthType());
	}
	
	@Test
	public void testConstructor() {		
		UserModel _model = new UserModel("LID", "CID");
		assertNull("id should not be set", _model.getId());
		assertEquals("loginId should be set", "LID", _model.getLoginId());
		assertEquals("contactId should be set", "CID", _model.getContactId());
		assertNull("hashedPassword should not be set", _model.getHashedPassword());
		assertNull("salt should not be set", _model.getSalt());
		assertNull("authType should not be set", _model.getAuthType());
	}
	
	@Test
	public void testId() {
		UserModel _model = new UserModel();
		assertNull("id should not be set by constructor", _model.getId());
		_model.setId("testId");
		assertEquals("id should have changed:", "testId", _model.getId());
	}

	@Test
	public void testLoginId() {
		UserModel _model = new UserModel();
		assertNull("loginId should not be set by empty constructor", _model.getLoginId());
		_model.setLoginId("testLoginId");
		assertEquals("loginId should have changed:", "testLoginId", _model.getLoginId());
	}

	@Test
	public void testContactId() {
		UserModel _model = new UserModel();
		assertNull("contactId should not be set by empty constructor", _model.getContactId());
		_model.setContactId("testContactId");
		assertEquals("contactId should have changed:", "testContactId", _model.getContactId());
	}
	
	@Test
	public void testHashedPassword() {
		UserModel _model = new UserModel();
		assertNull("hashedPassword should not be set by empty constructor", _model.getHashedPassword());
		_model.setHashedPassword("testHashedPassword");
		assertEquals("hashedPassword should have changed:", "testHashedPassword", _model.getHashedPassword());
	}
	
	@Test
	public void testSalt() {
		UserModel _model = new UserModel();
		assertNull("salt should not be set by empty constructor", _model.getSalt());
		_model.setSalt("testSalt");
		assertEquals("salt should have changed:", "testSalt", _model.getSalt());
	}
		
	@Test
	public void testCreatedBy() {
		UserModel _model = new UserModel();
		assertNull("createdBy should not be set by empty constructor", _model.getCreatedBy());
		_model.setCreatedBy("testCreatedBy");
		assertEquals("createdBy should have changed", "testCreatedBy", _model.getCreatedBy());	
	}
	
	@Test
	public void testCreatedAt() {
		UserModel _model = new UserModel();
		assertNull("createdAt should not be set by empty constructor", _model.getCreatedAt());
		_model.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _model.getCreatedAt());
	}
		
	@Test
	public void testModifiedBy() {
		UserModel _model = new UserModel();
		assertNull("modifiedBy should not be set by empty constructor", _model.getModifiedBy());
		_model.setModifiedBy("testModifiedBy");
		assertEquals("modifiedBy should have changed", "testModifiedBy", _model.getModifiedBy());	
	}
	
	@Test
	public void testModifiedAt() {
		UserModel _model = new UserModel();
		assertNull("modifiedAt should not be set by empty constructor", _model.getModifiedAt());
		_model.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _model.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testCreateReadDeleteWithEmptyConstructor() {
		UserModel _model1 = create("testUserCreateReadDeleteWithEmptyConstructor", 1);
		assertNull("id should not be set by constructor", _model1.getId());
		assertEquals("loginId should be set by constructor", "testUserCreateReadDeleteWithEmptyConstructor1", _model1.getLoginId());
		assertEquals("contactId should be set by constructor", contact.getId(), _model1.getContactId());
		assertNull("hashedPassword should not be set", _model1.getHashedPassword());
		assertNull("salt should not be set", _model1.getSalt());
		assertNull("authType should not be set", _model1.getAuthType());

		UserModel _model2 = post(_model1, Status.OK);
		
		assertNull("create() should not change the id of the local object", _model1.getId());
		assertEquals("create() should not change the loginId of the local object", "testUserCreateReadDeleteWithEmptyConstructor1", _model1.getLoginId());
		assertEquals("create() should not change the contactId of the local object", contact.getId(), _model1.getContactId());
		assertNull("create() should not change hashedPassword of the local object", _model1.getHashedPassword());
		assertNull("create() should not change the salt of the local object", _model1.getSalt());

		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
		assertEquals("create() should not change the loginId", "testUserCreateReadDeleteWithEmptyConstructor1", _model2.getLoginId());
		assertEquals("create() should not change the contactId", contact.getId(), _model2.getContactId());
		assertNull("create() should not change the hashedPassword", _model2.getHashedPassword());
		assertNull("create() should not change the salt", _model2.getSalt());

		UserModel _model3 = get(_model2.getId(), Status.OK);
		
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("read() should not change the loginId", _model2.getLoginId(), _model3.getLoginId());
		assertEquals("read() should not change the contactId", _model2.getContactId(), _model3.getContactId());
		assertEquals("read() should not change the hashedPassword", _model2.getHashedPassword(), _model3.getHashedPassword());
		assertEquals("read() should not change the salt", _model2.getSalt(), _model3.getSalt());
		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateReadDelete() {
		UserModel _model1 = create("testCreateReadDelete", 1);
		
		// validating _um1 (before create())
		assertNull("id should not be set by constructor", _model1.getId());
		assertEquals("loginId should be set by constructor", "testCreateReadDelete1", _model1.getLoginId());
		assertEquals("contactId should be set by constructor", contact.getId(), _model1.getContactId());
		assertNull("hashedPassword should not be set", _model1.getHashedPassword());
		assertNull("salt should not be set by constructor", _model1.getSalt());
		UserModel _model2 = post(_model1, Status.OK);
		
		assertNull("id should be unchanged", _model1.getId());
		assertEquals("loginId should be unchanged", "testCreateReadDelete1", _model1.getLoginId());
		assertEquals("contactId should be unchanged", contact.getId(), _model1.getContactId());
		assertNull("hashedPassword should be unchanged", _model1.getHashedPassword());
		assertNull("salt should be unchanged", _model1.getSalt());
		
		assertNotNull("id of returned object should be set", _model2.getId());
		assertEquals("loginId should be unchanged", "testCreateReadDelete1", _model2.getLoginId());
		assertEquals("contactId should be unchanged", contact.getId(), _model2.getContactId());
		assertNull("hashedPassword should be unchanged", _model2.getHashedPassword());
		assertNull("salt should be unchanged", _model2.getSalt());
		UserModel _model3 = get(_model2.getId(), Status.OK);		
		
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("loginId should be unchanged", _model2.getLoginId(), _model3.getLoginId());
		assertEquals("contactId should be unchanged", _model2.getContactId(), _model3.getContactId());
		assertEquals("hashedPassword should be unchanged", _model2.getHashedPassword(), _model3.getHashedPassword());
		assertEquals("salt should be unchanged", _model2.getSalt(), _model3.getSalt());
		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testClientSideId() {
		UserModel _model = create("testClientSideId", 1);
		_model.setId("testClientSideId");
		assertEquals("id should have changed", "testClientSideId", _model.getId());
		post(_model, Status.BAD_REQUEST);
	}
	
	@Test
	public void testDuplicateId() {
		UserModel _model1 = post(create("testDuplicateId", 1), Status.OK);
		UserModel _model2 = create("testDuplicateId", 2);
		_model2.setId(_model1.getId());		// wrongly create a 2nd UserModel object with the same ID
		post(_model2, Status.CONFLICT);
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testList() {
		List<UserModel> _listBefore = list(null, Status.OK);
		ArrayList<UserModel> _localList = new ArrayList<UserModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(post(create("testList", i), Status.OK));
		}
		assertEquals("correct number of users should be created", LIMIT, _localList.size());
		
		List<UserModel> _listAfter = list(null, Status.OK);		
		assertEquals("list() should return the correct number of users", _listBefore.size() + LIMIT, _listAfter.size());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (UserModel _model : _listAfter) {
			_remoteListIds.add(_model.getId());
		}
		for (UserModel _model : _localList) {
			assertTrue("user <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
		}
		for (UserModel _model : _localList) {
			get(_model.getId(), Status.OK);
		}
		for (UserModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}
	}

	@Test
	public void testCreate() {
		UserModel _model1 = post(create("testCreate", 1), Status.OK);
		UserModel _model2 = post(create("testCreate", 2), Status.OK);
		assertNotNull("ID should be set", _model1.getId());
		assertNotNull("ID should be set", _model2.getId());
		assertThat(_model2.getId(), not(equalTo(_model1.getId())));
		
		assertNotNull("ID should be set", _model1.getId());
		assertEquals("loginId should be set by constructor", "testCreate1", _model1.getLoginId());
		assertEquals("contactId should be set by constructor", contact.getId(), _model1.getContactId());
		assertNull("hashedPassword should not be set by constructor", _model1.getHashedPassword());
		assertNull("salt should not be set by constructor", _model1.getSalt());
				
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("loginId should be set by constructor", "testCreate2", _model2.getLoginId());
		assertEquals("contactId should be set by constructor", contact.getId(), _model2.getContactId());
		assertNull("hashedPassword should not be set by constructor", _model2.getHashedPassword());
		assertNull("salt should not be set by constructor", _model2.getSalt());
		
		delete(_model1.getId(), Status.NO_CONTENT);
		delete(_model2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDoubleCreate() 
	{
		UserModel _model = post(create("testDoubleCreate", 1), Status.OK);
		assertNotNull("ID should be set:", _model.getId());		
		post(_model, Status.CONFLICT);
		delete(_model.getId(), Status.NO_CONTENT);
	}

	@Test
	public void testUserRead() 
	{
		ArrayList<UserModel> _localList = new ArrayList<UserModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(post(create("testRead", i), Status.OK));
		}
		for (UserModel _model : _localList) {
			get(_model.getId(), Status.OK);
		}
		List<UserModel> _remoteList = list(null, Status.OK);
		for (UserModel _model : _remoteList) {
			assertEquals("ID should be unchanged when reading an user", _model.getId(), get(_model.getId(), Status.OK).getId());						
		}
		for (UserModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}
	}	

	@Test
	public void testUserMultiRead() 
	{
		UserModel _model1 = post(create("testMultiRead", 1), Status.OK);
		UserModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged after read", _model1.getId(), _model2.getId());		
		UserModel _model3 = get(_model1.getId(), Status.OK);		

		assertEquals("ID should be the same", _model2.getId(), _model3.getId());
		assertEquals("loginId should be unchanged", _model2.getLoginId(), _model3.getLoginId());
		assertEquals("contactId should be unchanged", _model2.getContactId(), _model3.getContactId());
		assertEquals("hashedPassword should be unchangede", _model2.getHashedPassword(), _model3.getHashedPassword());
		assertEquals("salt should be unchanged", _model2.getSalt(), _model3.getSalt());

		assertEquals("ID should be the same", _model2.getId(), _model1.getId());
		assertEquals("loginId should be unchanged", _model2.getLoginId(), _model1.getLoginId());
		assertEquals("contactId should be unchanged", _model2.getContactId(), _model1.getContactId());
		assertEquals("hashedPassword should be unchangede", _model2.getHashedPassword(), _model1.getHashedPassword());
		assertEquals("salt should be unchanged", _model2.getSalt(), _model1.getSalt());	
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testUpdate() 
	{
		UserModel _model1 = post(create("testUpdate", 0), Status.OK);
		
		_model1.setLoginId("LID1");
		_model1.setContactId(contact.getId());
		_model1.setHashedPassword("MY_PWD1");
		_model1.setSalt("MY_SALT1");
		UserModel _model2 = put(_model1, Status.OK);
		
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());	
		assertEquals("loginId should be set correctly", "LID1", _model2.getLoginId());
		assertEquals("contactId should be set correctly", contact.getId(), _model2.getContactId());
		assertEquals("v should be set correctly", "MY_PWD1", _model2.getHashedPassword());
		assertEquals("salt should be set correctly", "MY_SALT1", _model2.getSalt());
		
		_model2.setLoginId("LID2");
		ContactModel _cm = ContactTest.post(addressbookWC, adb.getId(), new ContactModel("MY_FNAME2", "MY_LNAME2"), Status.OK);
		_model2.setContactId(_cm.getId());
		_model2.setHashedPassword("MY_PWD2");
		_model2.setSalt("MY_SALT2");	
		UserModel _model3 = put(_model2, Status.OK);

		assertNotNull("ID should be set", _model3.getId());
		assertEquals("ID should be unchanged", _model2.getId(), _model3.getId());	
		assertEquals("loginId should be set by constructor", "LID2", _model3.getLoginId());
		assertEquals("contactId should be set by constructor", _cm.getId(), _model3.getContactId());
		assertEquals("v should be set by constructor", "MY_PWD2", _model3.getHashedPassword());
		assertEquals("salt should be set by constructor", "MY_SALT2", _model3.getSalt());
		
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDelete() 
	{
		UserModel _model1 = post(create("testDelete", 1), Status.OK);
		UserModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged when reading an addressbook", _model1.getId(), _model2.getId());						
		delete(_model1.getId(), Status.NO_CONTENT);
		get(_model1.getId(), Status.NOT_FOUND);
		get(_model1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testDoubleDelete() 
	{
		UserModel _model1 = post(create("testDoubleDelete", 1), Status.OK);
		get(_model1.getId(), Status.OK);
		delete(_model1.getId(), Status.NO_CONTENT);
		get(_model1.getId(), Status.NOT_FOUND);
		delete(_model1.getId(), Status.NOT_FOUND);
		get(_model1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testModifications() 
	{
		UserModel _model1 = post(create("testModifications", 1), Status.OK);
		assertNotNull("create() should set createdAt", _model1.getCreatedAt());
		assertNotNull("create() should set createdBy", _model1.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _model1.getCreatedAt(), _model1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());
		_model1.setLoginId("NEW_LOGINID");
		UserModel _model2 = put(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
		assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
		assertThat(_model2.getModifiedAt(), not(equalTo(_model2.getCreatedAt())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_model2.getModifiedBy(), not(equalTo(_model2.getCreatedBy())));
		_model1.setModifiedBy("MYSELF");
		_model1.setModifiedAt(new Date(1000));
		UserModel _model3 = put(_model1, Status.OK);
		
		// test, that modifiedBy really ignored the client-side value "MYSELF"
		assertThat(_model1.getModifiedBy(), not(equalTo(_model3.getModifiedBy())));
		// check whether the client-side modifiedAt() is ignored
		assertThat(_model1.getModifiedAt(), not(equalTo(_model3.getModifiedAt())));
		
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	/********************************* helper methods *********************************/	
	/**
	 * Retrieve a list of UserModel from UsersService by executing a HTTP GET request.
	 * This uses neither position nor size queries.
	 * @param query the URL query to use
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of UserModel objects in JSON format
	 */
	public List<UserModel> list(
			String query, 
			Status expectedStatus) {
		return list(wc, query, -1, Integer.MAX_VALUE, expectedStatus);
	}
	
	/**
	 * Retrieve a list of UserModel from UsersService by executing a HTTP GET request.
	 * @param webClient the WebClient for the UsersService
	 * @param query the URL query to use
	 * @param position the position to start a batch with
	 * @param size the size of a batch
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of UserModel objects in JSON format
	 */
	public static List<UserModel> list(
			WebClient webClient, 
			String query, 
			int position,
			int size,
			Status expectedStatus) {
		webClient.resetQuery();
		webClient.replacePath("/");
		Response _response = executeListQuery(webClient, query, position, size);
		List<UserModel> _list = null;
		if (expectedStatus != null) {
			assertEquals("list() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			_list = new ArrayList<UserModel>(webClient.getCollection(UserModel.class));
			System.out.println("list(webClient, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + ") ->" + _list.size());
		}
		return _list;
	}
	
	/**
	 * Create a new UserModel on the server by executing a HTTP POST request.
	 * @param model the data to post
	 * @param expectedStatus the expected HTTP status to test on; if this is null, it will not be tested
	 * @return the created data object
	 */
	private UserModel post(
			UserModel model,
			Status expectedStatus) {
		return post(wc, model, expectedStatus);
	}

	/**
	 * Create a new UserModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the service
	 * @param model the data to create on the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created data
	 */
	public static UserModel post(
			WebClient webClient,
			UserModel model,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").post(model);
		if (expectedStatus != null) {
			assertEquals("POST should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(UserModel.class);
		} else {
			return null;
		}
	}
	
	/**
	 * @param loginId
	 * @param suffix
	 * @return
	 */
	private UserModel create(String loginId, int suffix) {
		return new UserModel(loginId + suffix, contact.getId());
	}
	
	/**
	 * Read the UserModel with id from UsersService by executing a HTTP GET method.
	 * @param id the id of the UserModel to retrieve
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the retrieved UserModel object in JSON format
	 */
	private UserModel get(
			String id, 
			Status expectedStatus) {
		return get(wc, id, expectedStatus);
	}
	
	/**
	 * Read the UserModel with id from UsersService by executing a HTTP GET method.
	 * @param webClient the web client representing the UsersService
	 * @param id the id of the UserModel to retrieve
	 * @param expectedStatus  the expected HTTP status to test on
	 * @return the retrieved UserModel object in JSON format
	 */
	public static UserModel get(
			WebClient webClient,
			String id,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(id).get();
		if (expectedStatus != null) {
			assertEquals("GET should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(UserModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Update a UserModel on the UsersService by executing a HTTP PUT method.
	 * @param model the new UserModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated UserModel object in JSON format
	 */
	public UserModel put(
			UserModel model, 
			Status expectedStatus) {
		return put(wc, model, expectedStatus);
	}
	
	/**
	 * Update a UserModel on the UsersService by executing a HTTP PUT method.
	 * @param webClient the web client representing the UsersService
	 * @param model the new UserModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated UserModel object in JSON format
	 */
	public static UserModel put(
			WebClient webClient,
			UserModel model,
			Status expectedStatus) {
		webClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response _response = webClient.replacePath("/").path(model.getId()).put(model);
		if (expectedStatus != null) {
			assertEquals("PUT should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(UserModel.class);
		} else {
			return null;
		}
	}
	
	/**
	 * Delete the UserModel with id on the UsersService by executing a HTTP DELETE method.
	 * @param id the id of the UserModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public void delete(String id, Status expectedStatus) {
		delete(wc, id, expectedStatus);
	}
	
	/**
	 * Delete the UserModel with id on the UsersService by executing a HTTP DELETE method.
	 * @param webClient the WebClient for the UsersService
	 * @param id the id of the UserModel object to delete
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
	

	/* (non-Javadoc)
	 * @see test.org.opentdc.AbstractTestClient#calculateMembers()
	 */
	protected int calculateMembers() {
		return list(wc, null, 0, Integer.MAX_VALUE, Status.OK).size();
	}
}
