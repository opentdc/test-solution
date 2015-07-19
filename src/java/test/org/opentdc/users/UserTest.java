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

public class UserTest extends AbstractTestClient {
	private WebClient userWC = null;
	private static AddressbookModel adb = null;
	private static ContactModel contact = null;
	private WebClient addressbookWC = null;

	@Before
	public void initializeTests() {
		userWC = initializeTest(ServiceUtil.USERS_API_URL, UsersService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		adb = AddressbookTest.createAddressbook(addressbookWC, "UserTest", Status.OK);
		contact = ContactTest.createContact(addressbookWC, adb.getId(), "FNAME", "LNAME");
	}
	
	@After
	public void cleanupTest() {
		AddressbookTest.cleanup(addressbookWC, adb.getId(), "UserTest");
		userWC.close();
	}
	
	/********************************** users attributes tests *********************************/	
	@Test
	public void testEmptyConstructor() {
		UserModel _um = new UserModel();
		assertNull("id should not be set", _um.getId());
		assertNull("loginId should not be set", _um.getLoginId());
		assertNull("contactId should not be set", _um.getContactId());
		assertNull("hashedPassword should not be set", _um.getHashedPassword());
		assertNull("salt should not be set", _um.getSalt());
		assertNull("authType should not be set", _um.getAuthType());
	}
	
	@Test
	public void testConstructor() {		
		UserModel _um = new UserModel("LID", "CID");
		assertNull("id should not be set", _um.getId());
		assertEquals("loginId should be set", "LID", _um.getLoginId());
		assertEquals("contactId should be set", "CID", _um.getContactId());
		assertNull("hashedPassword should not be set", _um.getHashedPassword());
		assertNull("salt should not be set", _um.getSalt());
		assertNull("authType should not be set", _um.getAuthType());
	}
	
	@Test
	public void testId() {
		UserModel _um = new UserModel();
		assertNull("id should not be set by constructor", _um.getId());
		_um.setId("MY_ID");
		assertEquals("id should have changed:", "MY_ID", _um.getId());
	}

	@Test
	public void testLoginId() {
		UserModel _um = new UserModel();
		assertNull("loginId should not be set by empty constructor", _um.getLoginId());
		_um.setLoginId("LID");
		assertEquals("loginId should have changed:", "LID", _um.getLoginId());
	}

	@Test
	public void testContactId() {
		UserModel _um = new UserModel();
		assertNull("contactId should not be set by empty constructor", _um.getContactId());
		_um.setContactId("CID");
		assertEquals("contactId should have changed:", "CID", _um.getContactId());
	}
	
	@Test
	public void testHashedPassword() {
		UserModel _um = new UserModel();
		assertNull("hashedPassword should not be set by empty constructor", _um.getHashedPassword());
		_um.setHashedPassword("MY_PWD");
		assertEquals("hashedPassword should have changed:", "MY_PWD", _um.getHashedPassword());
	}
	
	@Test
	public void testSalt() {
		UserModel _um = new UserModel();
		assertNull("salt should not be set by empty constructor", _um.getSalt());
		_um.setSalt("MY_SALT");
		assertEquals("salt should have changed:", "MY_SALT", _um.getSalt());
	}
		
	@Test
	public void testCreatedBy() {
		UserModel _um = new UserModel();
		assertNull("createdBy should not be set by empty constructor", _um.getCreatedBy());
		_um.setCreatedBy("MY_NAME");
		assertEquals("createdBy should have changed", "MY_NAME", _um.getCreatedBy());	
	}
	
	@Test
	public void testCreatedAt() {
		UserModel __um = new UserModel();
		assertNull("createdAt should not be set by empty constructor", __um.getCreatedAt());
		__um.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", __um.getCreatedAt());
	}
		
	@Test
	public void testModifiedBy() {
		UserModel _um = new UserModel();
		assertNull("modifiedBy should not be set by empty constructor", _um.getModifiedBy());
		_um.setModifiedBy("MY_NAME");
		assertEquals("modifiedBy should have changed", "MY_NAME", _um.getModifiedBy());	
	}
	
	@Test
	public void testModifiedAt() {
		UserModel _um = new UserModel();
		assertNull("modifiedAt should not be set by empty constructor", _um.getModifiedAt());
		_um.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _um.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testCreateReadDeleteWithEmptyConstructor() {
		UserModel _um1 = createUser("testUserCreateReadDeleteWithEmptyConstructor", 1);
		assertNull("id should not be set by constructor", _um1.getId());
		assertEquals("loginId should be set by constructor", "testUserCreateReadDeleteWithEmptyConstructor1", _um1.getLoginId());
		assertEquals("contactId should be set by constructor", contact.getId(), _um1.getContactId());
		assertNull("hashedPassword should not be set", _um1.getHashedPassword());
		assertNull("salt should not be set", _um1.getSalt());
		assertNull("authType should not be set", _um1.getAuthType());

		// create(_um1) -> _um2
		Response _response = userWC.replacePath("/").post(_um1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _um2 = _response.readEntity(UserModel.class);
		
		// validate _um1 (local object)
		assertNull("create() should not change the id of the local object", _um1.getId());
		assertEquals("create() should not change the loginId of the local object", "testUserCreateReadDeleteWithEmptyConstructor1", _um1.getLoginId());
		assertEquals("create() should not change the contactId of the local object", contact.getId(), _um1.getContactId());
		assertNull("create() should not change hashedPassword of the local object", _um1.getHashedPassword());
		assertNull("create() should not change the salt of the local object", _um1.getSalt());

		// validate _um2 (remote object)
		assertNotNull("create() should set a valid id on the remote object returned", _um2.getId());
		assertEquals("create() should not change the loginId", "testUserCreateReadDeleteWithEmptyConstructor1", _um2.getLoginId());
		assertEquals("create() should not change the contactId", contact.getId(), _um2.getContactId());
		assertNull("create() should not change the hashedPassword", _um2.getHashedPassword());
		assertNull("create() should not change the salt", _um2.getSalt());

		// read(_um2) -> _um3
		_response = userWC.replacePath("/").path(_um2.getId()).get();
		assertEquals("read(" + _um2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _um3 = _response.readEntity(UserModel.class);
		
		// validate _um3 (read)
		assertEquals("id of returned object should be the same", _um2.getId(), _um3.getId());
		assertEquals("read() should not change the loginId", _um2.getLoginId(), _um3.getLoginId());
		assertEquals("read() should not change the contactId", _um2.getContactId(), _um3.getContactId());
		assertEquals("read() should not change the hashedPassword", _um2.getHashedPassword(), _um3.getHashedPassword());
		assertEquals("read() should not change the salt", _um2.getSalt(), _um3.getSalt());
		
		// delete(_um3)
		_response = userWC.replacePath("/").path(_um3.getId()).delete();
		assertEquals("delete(" + _um3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testUserCreateReadDelete() {
		// new(1) -> _um1
		UserModel _um1 = createUser("testUserCreateReadDelete", 1);
		
		// validating _um1 (before create())
		assertNull("id should not be set by constructor", _um1.getId());
		assertEquals("loginId should be set by constructor", "testUserCreateReadDelete1", _um1.getLoginId());
		assertEquals("contactId should be set by constructor", contact.getId(), _um1.getContactId());
		assertNull("hashedPassword should not be set", _um1.getHashedPassword());
		assertNull("salt should not be set by constructor", _um1.getSalt());
		
		// create(_um1) -> _um2
		Response _response = userWC.replacePath("/").post(_um1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _um2 = _response.readEntity(UserModel.class);
		
		// validating _um1 (after create())
		assertNull("id should be unchanged", _um1.getId());
		assertEquals("loginId should be unchanged", "testUserCreateReadDelete1", _um1.getLoginId());
		assertEquals("contactId should be unchanged", contact.getId(), _um1.getContactId());
		assertNull("hashedPassword should be unchanged", _um1.getHashedPassword());
		assertNull("salt should be unchanged", _um1.getSalt());
		
		// validating _um2 (created object)
		assertNotNull("id of returned object should be set", _um2.getId());
		assertEquals("loginId should be unchanged", "testUserCreateReadDelete1", _um2.getLoginId());
		assertEquals("contactId should be unchanged", contact.getId(), _um2.getContactId());
		assertNull("hashedPassword should be unchanged", _um2.getHashedPassword());
		assertNull("salt should be unchanged", _um2.getSalt());
				
		// read(_um2)  -> _um3
		_response = userWC.replacePath("/").path(_um2.getId()).get();
		assertEquals("read(" + _um2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _um3 = _response.readEntity(UserModel.class);
		
		// both objects should have the same attributes
		assertEquals("id of returned object should be the same", _um2.getId(), _um3.getId());
		assertEquals("loginId should be unchanged", _um2.getLoginId(), _um3.getLoginId());
		assertEquals("contactId should be unchanged", _um2.getContactId(), _um3.getContactId());
		assertEquals("hashedPassword should be unchanged", _um2.getHashedPassword(), _um3.getHashedPassword());
		assertEquals("salt should be unchanged", _um2.getSalt(), _um3.getSalt());
		
		// delete(_um3)
		_response = userWC.replacePath("/").path(_um3.getId()).delete();
		assertEquals("delete(" + _um3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateUserWithClientSideId() {
		// new() -> _um1 -> _um1.setId()
		UserModel _um1 = createUser("testCreateUserWithClientSideId", 1);
		_um1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _um1.getId());
		// create(_um1) -> BAD_REQUEST
		Response _response = userWC.replacePath("/").post(_um1);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateUserWithDuplicateId() {
		// create(new()) -> _um1
		Response _response = userWC.replacePath("/").post(createUser("testCreateUserWithDuplicateId", 1));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _um1 = _response.readEntity(UserModel.class);

		// new() -> _um2 -> _um2.setId(_um1.getId())
		UserModel _um2 = createUser("testCreateUserWithDuplicateId", 2);
		_um2.setId(_um1.getId());		// wrongly create a 2nd UserModel object with the same ID
		
		// create(_um2) -> CONFLICT
		_response = userWC.replacePath("/").post(_um2);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_um1)
		_response = userWC.replacePath("/").path(_um1.getId()).delete();
		assertEquals("delete(" + _um1.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testUserList(
	) {		
		ArrayList<UserModel> _localList = new ArrayList<UserModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			// create(new()) -> _localList
			_response = userWC.replacePath("/").post(createUser("testUserList", i));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(UserModel.class));
		}
		
		// list(/) -> _remoteList
		_response = userWC.replacePath("/").get();
		List<UserModel> _remoteList = new ArrayList<UserModel>(userWC.getCollection(UserModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (UserModel _um : _remoteList) {
			_remoteListIds.add(_um.getId());
		}
		
		for (UserModel _um : _localList) {
			assertTrue("user <" + _um.getId() + "> should be listed", _remoteListIds.contains(_um.getId()));
		}
		for (UserModel _um : _localList) {
			_response = userWC.replacePath("/").path(_um.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(UserModel.class);
		}
		for (UserModel _um : _localList) {
			_response = userWC.replacePath("/").path(_um.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}

	@Test
	public void testUserCreate() {
		Response _response = userWC.replacePath("/").post(createUser("testUserCreate", 1));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _model1 = _response.readEntity(UserModel.class);

		_response = userWC.replacePath("/").post(createUser("testUserCreate", 2));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _model2 = _response.readEntity(UserModel.class);
		assertThat(_model2.getId(), not(equalTo(_model1.getId())));
		
		assertNotNull("ID should be set", _model1.getId());
		assertEquals("loginId should be set by constructor", "testUserCreate1", _model1.getLoginId());
		assertEquals("contactId should be set by constructor", contact.getId(), _model1.getContactId());
		assertNull("hashedPassword should not be set by constructor", _model1.getHashedPassword());
		assertNull("salt should not be set by constructor", _model1.getSalt());
				
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("loginId should be set by constructor", "testUserCreate2", _model2.getLoginId());
		assertEquals("contactId should be set by constructor", contact.getId(), _model2.getContactId());
		assertNull("hashedPassword should not be set by constructor", _model2.getHashedPassword());
		assertNull("salt should not be set by constructor", _model2.getSalt());
		
		// delete(_um3) -> NO_CONTENT
		_response = userWC.replacePath("/").path(_model1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_um4) -> NO_CONTENT
		_response = userWC.replacePath("/").path(_model2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testUserDoubleCreate(
	) {
		// create(new()) -> _um
		Response _response = userWC.replacePath("/").post(createUser("testUserDoubleCreate", 1));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _um = _response.readEntity(UserModel.class);
		assertNotNull("ID should be set:", _um.getId());		
		
		// create(_um) -> CONFLICT
		_response = userWC.replacePath("/").post(_um);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_um) -> NO_CONTENT
		_response = userWC.replacePath("/").path(_um.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testUserRead(
	) {
		ArrayList<UserModel> _localList = new ArrayList<UserModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			_response = userWC.replacePath("/").post(createUser("testUserRead", i));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(UserModel.class));
		}
	
		// test read on each local element
		for (UserModel _um : _localList) {
			_response = userWC.replacePath("/").path(_um.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(UserModel.class);
		}

		// test read on each listed element
		_response = userWC.replacePath("/").get();
		List<UserModel> _remoteList = new ArrayList<UserModel>(userWC.getCollection(UserModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		UserModel _tmpObj = null;
		for (UserModel _um : _remoteList) {
			_response = userWC.replacePath("/").path(_um.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(UserModel.class);
			assertEquals("ID should be unchanged when reading a user", _um.getId(), _tmpObj.getId());						
		}

		for (UserModel _um : _localList) {
			_response = userWC.replacePath("/").path(_um.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}	

	@Test
	public void testUserMultiRead(
	) {
		// new() -> _um1
		UserModel _um1 = createUser("testUserMultiRead", 1);
		
		// create(_um1) -> _um2
		Response _response = userWC.replacePath("/").post(_um1);
		UserModel _um2 = _response.readEntity(UserModel.class);

		// read(_um2) -> _um3
		_response = userWC.replacePath("/").path(_um2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _um3 = _response.readEntity(UserModel.class);
		assertEquals("ID should be unchanged after read", _um2.getId(), _um3.getId());		

		// read(_um2) -> _um4
		_response = userWC.replacePath("/").path(_um2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _um4 = _response.readEntity(UserModel.class);
		
		// both object should have the same attributes, but should be different objects
		assertEquals("ID should be the same", _um3.getId(), _um4.getId());
		assertEquals("loginId should be unchanged", _um3.getLoginId(), _um4.getLoginId());
		assertEquals("contactId should be unchanged", _um3.getContactId(), _um4.getContactId());
		assertEquals("hashedPassword should be unchangede", _um3.getHashedPassword(), _um4.getHashedPassword());
		assertEquals("salt should be unchanged", _um3.getSalt(), _um4.getSalt());

		assertEquals("ID should be the same", _um2.getId(), _um3.getId());
		assertEquals("loginId should be unchanged", _um2.getLoginId(), _um3.getLoginId());
		assertEquals("contactId should be unchanged", _um2.getContactId(), _um3.getContactId());
		assertEquals("hashedPassword should be unchangede", _um2.getHashedPassword(), _um3.getHashedPassword());
		assertEquals("salt should be unchanged", _um2.getSalt(), _um3.getSalt());
		
		// delete(_um2)
		_response = userWC.replacePath("/").path(_um2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testUserUpdate(
	) {
		// new() -> _c1
		UserModel _um1 = createUser("testUserUpdate", 1);
		
		// create(_c1) -> _c2
		Response _response = userWC.replacePath("/").post(_um1);
		UserModel _um2 = _response.readEntity(UserModel.class);
		
		// change the attributes
		// update(_c2) -> _c3
		_um2.setLoginId("LID1");
		_um2.setContactId(contact.getId());
		_um2.setHashedPassword("MY_PWD1");
		_um2.setSalt("MY_SALT1");
		
		userWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = userWC.replacePath("/").path(_um2.getId()).put(_um2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _um3 = _response.readEntity(UserModel.class);

		assertNotNull("ID should be set", _um3.getId());
		assertEquals("ID should be unchanged", _um2.getId(), _um3.getId());
		assertEquals("loginId should be set by constructor", "LID1", _um3.getLoginId());
		assertEquals("contactId should be set by constructor", contact.getId(), _um3.getContactId());
		assertEquals("v should be set by constructor", "MY_PWD1", _um3.getHashedPassword());
		assertEquals("salt should be set by constructor", "MY_SALT1", _um3.getSalt());
		
		// reset the attributes
		// update(_c2) -> _c4
		_um2.setLoginId("LID2");
		ContactModel _cm = ContactTest.createContact(addressbookWC, adb.getId(), "MY_FNAME2", "MY_LNAME2");
		_um2.setContactId(_cm.getId());
		_um2.setHashedPassword("MY_PWD2");
		_um2.setSalt("MY_SALT2");		
		
		userWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = userWC.replacePath("/").path(_um2.getId()).put(_um2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _um4 = _response.readEntity(UserModel.class);

		assertNotNull("ID should be set", _um4.getId());
		assertEquals("ID should be unchanged", _um2.getId(), _um4.getId());	
		assertEquals("loginId should be set by constructor", "LID2", _um4.getLoginId());
		assertEquals("contactId should be set by constructor", _cm.getId(), _um4.getContactId());
		assertEquals("v should be set by constructor", "MY_PWD2", _um4.getHashedPassword());
		assertEquals("salt should be set by constructor", "MY_SALT2", _um4.getSalt());
		
		_response = userWC.replacePath("/").path(_um2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testUserDelete(
	) {
		// new() -> _um1
		UserModel _um1 = createUser("testUserDelete", 1);
		// create(_um1) -> _um2
		Response _response = userWC.replacePath("/").post(_um1);
		UserModel _um2 = _response.readEntity(UserModel.class);
		
		// read(_um2) -> _um3
		_response = userWC.replacePath("/").path(_um2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _um3 = _response.readEntity(UserModel.class);
		assertEquals("ID should be unchanged when reading a user (remote):", _um2.getId(), _um3.getId());						
		
		// delete(_um2) -> OK
		_response = userWC.replacePath("/").path(_um2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	
		// read the deleted object twice
		// read(_um2) -> NOT_FOUND
		_response = userWC.replacePath("/").path(_um2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read(_um2) -> NOT_FOUND
		_response = userWC.replacePath("/").path(_um2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testUserDoubleDelete(
	) {
		// new() -> _um1
		UserModel _um1 = createUser("testUserDoubleDelete", 1);
		
		// create(_um1) -> _um2
		Response _response = userWC.replacePath("/").post(_um1);
		UserModel _um2 = _response.readEntity(UserModel.class);

		// read(_um2) -> OK
		_response = userWC.replacePath("/").path(_um2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		// delete(_um2) -> OK
		_response = userWC.replacePath("/").path(_um2.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		// read(_um2) -> NOT_FOUND
		_response = userWC.replacePath("/").path(_um2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// delete _um2 -> NOT_FOUND
		_response = userWC.replacePath("/").path(_um2.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read _um2 -> NOT_FOUND
		_response = userWC.replacePath("/").path(_um2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testUserModifications() {
		// create(new UserModel()) -> _um1
		Response _response = userWC.replacePath("/").post(createUser("testUserModifications", 1));
		UserModel _um1 = _response.readEntity(UserModel.class);
		
		// test createdAt and createdBy
		assertNotNull("create() should set createdAt", _um1.getCreatedAt());
		assertNotNull("create() should set createdBy", _um1.getCreatedBy());
		// test modifiedAt and modifiedBy (= same as createdAt/createdBy)
		assertNotNull("create() should set modifiedAt", _um1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _um1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _um1.getCreatedAt(), _um1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _um1.getCreatedBy(), _um1.getModifiedBy());
		
		// update(_um1)  -> _um2
		_um1.setLoginId("NEW_LOGINID");
		userWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = userWC.replacePath("/").path(_um1.getId()).put(_um1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _um2 = _response.readEntity(UserModel.class);

		// test createdAt and createdBy (unchanged)
		assertEquals("update() should not change createdAt", _um1.getCreatedAt(), _um2.getCreatedAt());
		assertEquals("update() should not change createdBy", _um1.getCreatedBy(), _um2.getCreatedBy());
		
		// test modifiedAt and modifiedBy (= different from createdAt/createdBy)
		assertThat(_um2.getModifiedAt(), not(equalTo(_um2.getCreatedAt())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_um2.getModifiedBy(), not(equalTo(_um2.getCreatedBy())));

		// update(_um1) with createdBy set on client side -> ignore
		String _createdBy = _um1.getCreatedBy();
		_um1.setCreatedBy("MYSELF");
		userWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = userWC.replacePath("/").path(_um1.getId()).put(_um1);
		assertEquals("update() should ignore client-side generated createdBy", 
				Status.OK.getStatusCode(), _response.getStatus());
		_um1.setCreatedBy(_createdBy);

		// update(_um1) with createdAt set on client side -> ignore
		Date _d = _um1.getCreatedAt();
		_um1.setCreatedAt(new Date(1000));
		userWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = userWC.replacePath("/").path(_um1.getId()).put(_um1);
		assertEquals("update() should ignore client-side generated createdAt", 
				Status.OK.getStatusCode(), _response.getStatus());
		_um1.setCreatedAt(_d);

		// update(_um1) with modifiedBy/At set on client side -> ignored by server
		_um1.setModifiedBy("MYSELF");
		_um1.setModifiedAt(new Date(1000));
		userWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = userWC.replacePath("/").path(_um1.getId()).put(_um1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _o3 = _response.readEntity(UserModel.class);
		
		// test, that modifiedBy really ignored the client-side value "MYSELF"
		assertThat(_um1.getModifiedBy(), not(equalTo(_o3.getModifiedBy())));
		// check whether the client-side modifiedAt() is ignored
		assertThat(_um1.getModifiedAt(), not(equalTo(_o3.getModifiedAt())));
		
		// delete(_um1) -> NO_CONTENT
		_response = userWC.replacePath("/").path(_um1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	/********************************* helper methods *********************************/	
	public UserModel createUser(String loginId, int suffix) {
		return new UserModel(loginId + suffix, contact.getId());
	}
}
