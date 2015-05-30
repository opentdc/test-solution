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
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.opentdc.users.UserModel;
import org.opentdc.users.UsersService;

import test.org.opentdc.AbstractTestClient;

public class UserTest extends AbstractTestClient<UsersService> {
	
	private static final String API = "api/users/";

	@Before
	public void initializeTests() {
		initializeTest(API, UsersService.class);
	}
	
	/********************************** users tests *********************************/	
	@Test
	public void testUserModelEmptyConstructor() {
		// new() -> _c
		UserModel _c = new UserModel();
		assertNull("id should not be set by empty constructor", _c.getId());
		assertNull("loginId should not be set by empty constructor", _c.getLoginId());
		assertNull("contactId should not be set by empty constructor", _c.getContactId());
		assertNull("hashedPassword should not be set by empty constructor", _c.getHashedPassword());
		assertNull("salt should not be set by empty constructor", _c.getSalt());
	}
	
	@Test
	public void testUserModelConstructor() {		
		// new("LID", "CID", "MY_PWD", "MY_SALT") -> _c
		UserModel _c = new UserModel("LID", "CID", "MY_PWD", "MY_SALT");
		assertNull("id should not be set by constructor", _c.getId());
		assertEquals("loginId should be set by constructor", "LID", _c.getLoginId());
		assertEquals("contactId should be set by constructor", "CID", _c.getContactId());
		assertEquals("v should be set by constructor", "MY_PWD", _c.getHashedPassword());
		assertEquals("salt should be set by constructor", "MY_SALT", _c.getSalt());
	}
	
	@Test
	public void testUserIdAttributeChange() {
		// new() -> _c -> _c.setId()
		UserModel _c = new UserModel();
		assertNull("id should not be set by constructor", _c.getId());
		_c.setId("MY_ID");
		assertEquals("id should have changed:", "MY_ID", _c.getId());
	}

	@Test
	public void testLoginIdAttributeChange() {
		// new() -> _c -> _c.setLoginId()
		UserModel _c = new UserModel();
		assertNull("loginId should not be set by empty constructor", _c.getLoginId());
		_c.setLoginId("LID");
		assertEquals("loginId should have changed:", "LID", _c.getLoginId());
	}

	@Test
	public void testContactIdAttributeChange() {
		// new() -> _c -> _c.setContactId()
		UserModel _c = new UserModel();
		assertNull("contactId should not be set by empty constructor", _c.getContactId());
		_c.setContactId("CID");
		assertEquals("contactId should have changed:", "CID", _c.getContactId());
	}
	
	@Test
	public void testHashedPasswordAttributeChange() {
		// new() -> _c -> _c.setHashedPassword()
		UserModel _c = new UserModel();
		assertNull("hashedPassword should not be set by empty constructor", _c.getHashedPassword());
		_c.setHashedPassword("MY_PWD");
		assertEquals("hashedPassword should have changed:", "MY_PWD", _c.getHashedPassword());
	}
	
	@Test
	public void testSaltAttributeChange() {
		// new() -> _c -> _c.setSalt()
		UserModel _c = new UserModel();
		assertNull("salt should not be set by empty constructor", _c.getSalt());
		_c.setSalt("MY_SALT");
		assertEquals("salt should have changed:", "MY_SALT", _c.getSalt());
	}
		
	@Test
	public void testUserCreateReadDeleteWithEmptyConstructor() {
		// new() -> _c1
		UserModel _c1 = new UserModel();
		assertNull("id should not be set by empty constructor", _c1.getId());
		assertNull("loginId should not be set by empty constructor", _c1.getLoginId());
		assertNull("contactId should not be set by empty constructor", _c1.getContactId());
		assertNull("hashedPassword should not be set by empty constructor", _c1.getHashedPassword());
		assertNull("salt should not be set by empty constructor", _c1.getSalt());

		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _c2 = _response.readEntity(UserModel.class);
		assertNull("create() should not change the id of the local object", _c1.getId());
		assertNull("create() should not change the loginId of the local object", _c1.getLoginId());
		assertNull("create() should not change the contactId of the local object", _c1.getContactId());
		assertNull("create() should not change hashedPassword of the local object", _c1.getHashedPassword());
		assertNull("create() should not change the salt of the local object", _c1.getSalt());

		assertNotNull("create() should set a valid id on the remote object returned", _c2.getId());
		assertNull("loginId of returned object should still be null after remote create", _c2.getLoginId());
		assertNull("contactId of returned object should still be null after remote create", _c2.getContactId());
		assertNull("hashedPassword of returned object should still be null after remote create", _c2.getHashedPassword());
		assertNull("salt of returned object should still be null after remote create", _c2.getSalt());

		// read(_c2) -> _c3
		_response = webclient.replacePath(_c2.getId()).get();
		assertEquals("read(" + _c2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _c3 = _response.readEntity(UserModel.class);
		assertEquals("id of returned object should be the same", _c2.getId(), _c3.getId());
		assertEquals("loginId of returned object should be unchanged after remote create", _c2.getLoginId(), _c3.getLoginId());
		assertEquals("contactId of returned object should be unchanged after remote create", _c2.getContactId(), _c3.getContactId());
		assertEquals("hashedPassword of returned object should be unchanged after remote create", _c2.getHashedPassword(), _c3.getHashedPassword());
		assertEquals("salt of returned object should be unchanged after remote create", _c2.getSalt(), _c3.getSalt());
		// delete(_c3)
		_response = webclient.replacePath(_c3.getId()).delete();
		assertEquals("delete(" + _c3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testUserCreateReadDelete() {
		// new("MY_TITLE", "MY_DESC") -> _c1
		UserModel _c1 = new UserModel("LID", "CID", "MY_PWD", "MY_SALT");
		assertNull("id should not be set by constructor", _c1.getId());
		assertEquals("loginId should be set by constructor", "LID", _c1.getLoginId());
		assertEquals("contactId should be set by constructor", "CID", _c1.getContactId());
		assertEquals("v should be set by constructor", "MY_PWD", _c1.getHashedPassword());
		assertEquals("salt should be set by constructor", "MY_SALT", _c1.getSalt());
		
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _c2 = _response.readEntity(UserModel.class);
		assertNull("id should be unchanged", _c1.getId());
		assertEquals("loginId should be unchanged", "LID", _c1.getLoginId());
		assertEquals("contactId should be unchanged", "CID", _c1.getContactId());
		assertEquals("hashedPassword should be unchangede", "MY_PWD", _c1.getHashedPassword());
		assertEquals("salt should be unchanged", "MY_SALT", _c1.getSalt());
		
		assertNotNull("id of returned object should be set", _c2.getId());
		assertEquals("loginId should be unchanged", "LID", _c2.getLoginId());
		assertEquals("contactId should be unchanged", "CID", _c2.getContactId());
		assertEquals("hashedPassword should be unchangede", "MY_PWD", _c2.getHashedPassword());
		assertEquals("salt should be unchanged", "MY_SALT", _c2.getSalt());
				
		// read(_c2)  -> _c3
		_response = webclient.replacePath(_c2.getId()).get();
		assertEquals("read(" + _c2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _c3 = _response.readEntity(UserModel.class);
		assertEquals("id of returned object should be the same", _c2.getId(), _c3.getId());
		assertEquals("loginId should be unchanged", _c2.getLoginId(), _c3.getLoginId());
		assertEquals("contactId should be unchanged", _c2.getContactId(), _c3.getContactId());
		assertEquals("hashedPassword should be unchangede", _c2.getHashedPassword(), _c3.getHashedPassword());
		assertEquals("salt should be unchanged", _c2.getSalt(), _c3.getSalt());
		// delete(_c3)
		_response = webclient.replacePath(_c3.getId()).delete();
		assertEquals("delete(" + _c3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateUserWithClientSideId() {
		// new() -> _c1 -> _c1.setId()
		UserModel _c1 = new UserModel();
		_c1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _c1.getId());
		// create(_c1) -> BAD_REQUEST
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateUserWithDuplicateId() {
		// create(new()) -> _c2
		Response _response = webclient.replacePath("/").post(new UserModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _c2 = _response.readEntity(UserModel.class);

		// new() -> _c3 -> _c3.setId(_c2.getId())
		UserModel _c3 = new UserModel();
		_c3.setId(_c2.getId());		// wrongly create a 2nd UserModel object with the same ID
		
		// create(_c3) -> CONFLICT
		_response = webclient.replacePath("/").post(_c3);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testUserList(
	) {		
		ArrayList<UserModel> _localList = new ArrayList<UserModel>();
		Response _response = null;
		webclient.replacePath("/");
		for (int i = 0; i < LIMIT; i++) {
			// create(new()) -> _localList
			_response = webclient.post(new UserModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(UserModel.class));
		}
		
		// list(/) -> _remoteList
		_response = webclient.replacePath("/").get();
		List<UserModel> _remoteList = new ArrayList<UserModel>(webclient.getCollection(UserModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (UserModel _c : _remoteList) {
			_remoteListIds.add(_c.getId());
		}
		
		for (UserModel _c : _localList) {
			assertTrue("user <" + _c.getId() + "> should be listed", _remoteListIds.contains(_c.getId()));
		}
		for (UserModel _c : _localList) {
			_response = webclient.replacePath(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(UserModel.class);
		}
		for (UserModel _c : _localList) {
			_response = webclient.replacePath(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}
	
	@Test
	public void testUserCreate() {
		// new("LID1", "CID1", "MY_PWD1", "MY_SALT1") -> _c1
		UserModel _c1 = new UserModel("LID1", "CID1", "MY_PWD1", "MY_SALT1");
		
		// new("LID2", "CID2", "MY_PWD2", "MY_SALT2") -> _c2
		UserModel _c2 = new UserModel("LID2", "CID2", "MY_PWD2", "MY_SALT2");
		
		// create(_c1)  -> _c3
		Response _response = webclient.post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _c3 = _response.readEntity(UserModel.class);

		// create(_c2) -> _c4
		_response = webclient.post(_c2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _c4 = _response.readEntity(UserModel.class);
		assertThat(_c4.getId(), not(equalTo(_c3.getId())));
		
		// validate _c3
		assertNotNull("ID should be set", _c3.getId());
		assertEquals("loginId should be set by constructor", "LID1", _c3.getLoginId());
		assertEquals("contactId should be set by constructor", "CID1", _c3.getContactId());
		assertEquals("v should be set by constructor", "MY_PWD1", _c3.getHashedPassword());
		assertEquals("salt should be set by constructor", "MY_SALT1", _c3.getSalt());
				
		// validate _c4
		assertNotNull("ID should be set", _c4.getId());
		assertEquals("loginId should be set by constructor", "LID2", _c4.getLoginId());
		assertEquals("contactId should be set by constructor", "CID2", _c4.getContactId());
		assertEquals("v should be set by constructor", "MY_PWD2", _c4.getHashedPassword());
		assertEquals("salt should be set by constructor", "MY_SALT2", _c4.getSalt());
		
		// delete(_c1) -> METHOD_NOT_ALLOWED   (_c1.getId() = null)
		_response = webclient.replacePath(_c1.getId()).delete();
		assertEquals("delete() should return with status METHOD_NOT_ALLOWED", Status.METHOD_NOT_ALLOWED.getStatusCode(), _response.getStatus());

		// delete(_c2) -> METHOD_NOT_ALLOWED   (_c2.getId() = null)
		_response = webclient.replacePath(_c2.getId()).delete();
		assertEquals("delete() should return with status METHOD_NOT_ALLOWED", Status.METHOD_NOT_ALLOWED.getStatusCode(), _response.getStatus());

		// delete(_c3) -> NO_CONTENT
		_response = webclient.replacePath(_c3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_c4) -> NO_CONTENT
		_response = webclient.replacePath(_c4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testUserDoubleCreate(
	) {
		// create(new()) -> _c
		Response _response = webclient.replacePath("/").post(new UserModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _c = _response.readEntity(UserModel.class);
		assertNotNull("ID should be set:", _c.getId());		
		
		// create(_c) -> CONFLICT
		_response = webclient.replacePath("/").post(_c);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_c) -> NO_CONTENT
		_response = webclient.replacePath(_c.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testUserRead(
	) {
		ArrayList<UserModel> _localList = new ArrayList<UserModel>();
		Response _response = null;
		webclient.replacePath("/");
		for (int i = 0; i < LIMIT; i++) {
			_response = webclient.post(new UserModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(UserModel.class));
		}
	
		// test read on each local element
		for (UserModel _c : _localList) {
			_response = webclient.replacePath(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(UserModel.class);
		}

		// test read on each listed element
		_response = webclient.replacePath("/").get();
		List<UserModel> _remoteList = new ArrayList<UserModel>(webclient.getCollection(UserModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		UserModel _tmpObj = null;
		for (UserModel _c : _remoteList) {
			_response = webclient.replacePath(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(UserModel.class);
			assertEquals("ID should be unchanged when reading a user", _c.getId(), _tmpObj.getId());						
		}

		// TODO: "reading a user with ID = null should fail" -> ValidationException
		// TODO: "reading a non-existing user should fail"

		for (UserModel _c : _localList) {
			_response = webclient.replacePath(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}	

	@Test
	public void testUserMultiRead(
	) {
		// new() -> _c1
		UserModel _c1 = new UserModel();
		
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		UserModel _c2 = _response.readEntity(UserModel.class);

		// read(_c2) -> _c3
		_response = webclient.replacePath(_c2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _c3 = _response.readEntity(UserModel.class);
		assertEquals("ID should be unchanged after read", _c2.getId(), _c3.getId());		

		// read(_c2) -> _c4
		_response = webclient.replacePath(_c2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _c4 = _response.readEntity(UserModel.class);
		
		// but: the two objects are not equal !
		assertEquals("ID should be the same", _c3.getId(), _c4.getId());
		assertEquals("loginId should be unchanged", _c3.getLoginId(), _c4.getLoginId());
		assertEquals("contactId should be unchanged", _c3.getContactId(), _c4.getContactId());
		assertEquals("hashedPassword should be unchangede", _c3.getHashedPassword(), _c4.getHashedPassword());
		assertEquals("salt should be unchanged", _c3.getSalt(), _c4.getSalt());

		assertEquals("ID should be the same", _c2.getId(), _c3.getId());
		assertEquals("loginId should be unchanged", _c2.getLoginId(), _c3.getLoginId());
		assertEquals("contactId should be unchanged", _c2.getContactId(), _c3.getContactId());
		assertEquals("hashedPassword should be unchangede", _c2.getHashedPassword(), _c3.getHashedPassword());
		assertEquals("salt should be unchanged", _c2.getSalt(), _c3.getSalt());
		
		// delete(_c2)
		_response = webclient.replacePath(_c2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testUserUpdate(
	) {
		// new() -> _c1
		UserModel _c1 = new UserModel();
		
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		UserModel _c2 = _response.readEntity(UserModel.class);
		
		// change the attributes
		// update(_c2) -> _c3
		_c2.setLoginId("LID1");
		_c2.setContactId("CID1");
		_c2.setHashedPassword("MY_PWD1");
		_c2.setSalt("MY_SALT1");
		
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_c2.getId()).put(_c2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _c3 = _response.readEntity(UserModel.class);

		assertNotNull("ID should be set", _c3.getId());
		assertEquals("ID should be unchanged", _c2.getId(), _c3.getId());
		assertEquals("loginId should be set by constructor", "LID1", _c3.getLoginId());
		assertEquals("contactId should be set by constructor", "CID1", _c3.getContactId());
		assertEquals("v should be set by constructor", "MY_PWD1", _c3.getHashedPassword());
		assertEquals("salt should be set by constructor", "MY_SALT1", _c3.getSalt());
		
		// reset the attributes
		// update(_c2) -> _c4
		_c2.setLoginId("LID2");
		_c2.setContactId("CID2");
		_c2.setHashedPassword("MY_PWD2");
		_c2.setSalt("MY_SALT2");		
		
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_c2.getId()).put(_c2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _c4 = _response.readEntity(UserModel.class);

		assertNotNull("ID should be set", _c4.getId());
		assertEquals("ID should be unchanged", _c2.getId(), _c4.getId());	
		assertEquals("loginId should be set by constructor", "LID2", _c4.getLoginId());
		assertEquals("contactId should be set by constructor", "CID2", _c4.getContactId());
		assertEquals("v should be set by constructor", "MY_PWD2", _c4.getHashedPassword());
		assertEquals("salt should be set by constructor", "MY_SALT2", _c4.getSalt());
		
		_response = webclient.replacePath(_c2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testUserDelete(
	) {
		// new() -> _c0
		UserModel _c0 = new UserModel();
		// create(_c0) -> _c1
		Response _response = webclient.replacePath("/").post(_c0);
		UserModel _c1 = _response.readEntity(UserModel.class);
		
		// read(_c0) -> _tmpObj
		_response = webclient.replacePath(_c0.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		UserModel _tmpObj = _response.readEntity(UserModel.class);
		assertEquals("ID should be unchanged when reading a user (remote):", _c0.getId(), _tmpObj.getId());						

		// read(_c1) -> _tmpObj
		_response = webclient.replacePath(_c1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		_tmpObj = _response.readEntity(UserModel.class);
		assertEquals("ID should be unchanged when reading a user (remote):", _c1.getId(), _tmpObj.getId());						
		
		// delete(_c1) -> OK
		_response = webclient.replacePath(_c1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	
		// read the deleted object twice
		// read(_c1) -> NOT_FOUND
		_response = webclient.replacePath(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read(_c1) -> NOT_FOUND
		_response = webclient.replacePath(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testUserDoubleDelete(
	) {
		// new() -> _c0
		UserModel _c0 = new UserModel();
		
		// create(_c0) -> _c1
		Response _response = webclient.replacePath("/").post(_c0);
		UserModel _c1 = _response.readEntity(UserModel.class);

		// read(_c1) -> OK
		_response = webclient.replacePath(_c1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		// delete(_c1) -> OK
		_response = webclient.replacePath(_c1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		// read(_c1) -> NOT_FOUND
		_response = webclient.replacePath(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// delete _c1 -> NOT_FOUND
		_response = webclient.replacePath(_c1.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read _c1 -> NOT_FOUND
		_response = webclient.replacePath(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}

}
