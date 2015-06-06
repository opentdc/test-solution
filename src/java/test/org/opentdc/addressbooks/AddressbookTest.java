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

import org.junit.Before;
import org.junit.Test;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;

import test.org.opentdc.AbstractTestClient;

public class AddressbookTest extends AbstractTestClient<AddressbooksService> {
	
	private static final String API = "api/addressbooks/";

	@Before
	public void initializeTest(
	) {
		initializeTest(API, AddressbooksService.class);
	}
		
	/********************************** addressbook attribute tests *********************************/	
	@Test
	public void testAddressbookModelEmptyConstructor() {
		// new() -> _c
		AddressbookModel _c = new AddressbookModel();
		assertNull("id should not be set by empty constructor", _c.getId());
		assertNull("name should not be set by empty constructor", _c.getName());
	}
	
	@Test
	public void testAddressbookModelConstructor() {		
		// new("MY_NAME") -> _c
		AddressbookModel _c = new AddressbookModel("MY_NAME");
		assertNull("id should not be set by constructor", _c.getId());
		assertEquals("name should be set by constructor", "MY_NAME", _c.getName());
	}
	
	@Test
	public void testAddressbookIdAttributeChange() {
		// new() -> _c -> _c.setId()
		AddressbookModel _c = new AddressbookModel();
		assertNull("id should not be set by constructor", _c.getId());
		_c.setId("MY_ID");
		assertEquals("id should have changed", "MY_ID", _c.getId());
	}

	@Test
	public void testAddressbookNameAttributeChange() {
		// new() -> _c -> _c.setName()
		AddressbookModel _c = new AddressbookModel();
		assertNull("name should not be set by empty constructor", _c.getName());
		_c.setName("MY_NAME");
		assertEquals("name should have changed", "MY_NAME", _c.getName());
	}
	
	@Test
	public void testAddressbookCreateReadDeleteWithEmptyConstructor() {
		// new() -> _c1
		AddressbookModel _c1 = new AddressbookModel();
		assertNull("id should not be set by empty constructor", _c1.getId());
		assertNull("name should not be set by empty constructor", _c1.getName());
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _c2 = _response.readEntity(AddressbookModel.class);
		assertNull("create() should not change the id of the local object", _c1.getId());
		assertNull("create() should not change the name of the local object", _c1.getName());
		assertNotNull("create() should set a valid id on the remote object returned", _c2.getId());
		assertNull("name of returned object should still be null after remote create", _c2.getName());
		
		// read(_c2) -> _c3
		_response = webclient.replacePath("/").path(_c2.getId()).get();
		assertEquals("read(" + _c2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _c3 = _response.readEntity(AddressbookModel.class);
		assertEquals("id of returned object should be the same", _c2.getId(), _c3.getId());
		assertEquals("name of returned object should be unchanged after remote create", _c2.getName(), _c3.getName());
		// delete(_c3)
		_response = webclient.replacePath("/").path(_c3.getId()).delete();
		assertEquals("delete(" + _c3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressbookCreateReadDelete() {
		// new("MY_NAME") -> _c1
		AddressbookModel _c1 = new AddressbookModel("MY_NAME");
		assertNull("id should not be set by constructor", _c1.getId());
		assertEquals("name should be set by constructor", "MY_NAME", _c1.getName());
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _c2 = _response.readEntity(AddressbookModel.class);
		assertNull("id should be still null after remote create", _c1.getId());
		assertEquals("name should be unchanged after remote create", "MY_NAME", _c1.getName());
		
		assertNotNull("id of returned object should be set", _c2.getId());
		assertEquals("name of returned object should be unchanged after remote create", "MY_NAME", _c2.getName());
		// read(_c2)  -> _c3
		_response = webclient.replacePath("/").path(_c2.getId()).get();
		assertEquals("read(" + _c2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _c3 = _response.readEntity(AddressbookModel.class);
		assertEquals("id of returned object should be the same", _c2.getId(), _c3.getId());
		assertEquals("name of returned object should be unchanged after remote create", _c2.getName(), _c3.getName());
		// delete(_c3)
		_response = webclient.replacePath("/").path(_c3.getId()).delete();
		assertEquals("delete(" + _c3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressbookWithClientSideId() {
		// new() -> _c1 -> _c1.setId()
		AddressbookModel _c1 = new AddressbookModel();
		_c1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _c1.getId());
		// create(_c1) -> BAD_REQUEST
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressbookWithDuplicateId() {
		// create(new()) -> _c2
		Response _response = webclient.replacePath("/").post(new AddressbookModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _c2 = _response.readEntity(AddressbookModel.class);

		// new() -> _c3 -> _c3.setId(_c2.getId())
		AddressbookModel _c3 = new AddressbookModel();
		_c3.setId(_c2.getId());		// wrongly create a 2nd AddressbookModel object with the same ID
		
		// create(_c3) -> CONFLICT
		_response = webclient.replacePath("/").post(_c3);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete _c2
		_response = webclient.replacePath("/").path(_c2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressbookList(
	) {		
		ArrayList<AddressbookModel> _localList = new ArrayList<AddressbookModel>();
		Response _response = null;
				
		webclient.replacePath("/");
		for (int i = 0; i < LIMIT; i++) {
			// create(new()) -> _localList
			_response = webclient.post(new AddressbookModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(AddressbookModel.class));
		}
		System.out.println("localList:");
		for (AddressbookModel _am : _localList) {
			System.out.println(_am.getId());
		}
		assertEquals("correct number of addressbooks should be created", LIMIT, _localList.size());
		
		// list(/) -> _remoteList
		_response = webclient.replacePath("/").get();
		List<AddressbookModel> _remoteList = new ArrayList<AddressbookModel>(webclient.getCollection(AddressbookModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		System.out.println("_remoteList:");
		for (AddressbookModel _am : _remoteList) {
			System.out.println(_am.getId());
		}
		assertEquals("list() should return the correct number of addressbooks", LIMIT, _remoteList.size());
		// implicitly proven:  _remoteList.size() == _localList.size()

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (AddressbookModel _c : _remoteList) {
			_remoteListIds.add(_c.getId());
		}
		
		for (AddressbookModel _c : _localList) {
			assertTrue("addressbook <" + _c.getId() + "> should be listed", _remoteListIds.contains(_c.getId()));
		}
		for (AddressbookModel _c : _localList) {
			_response = webclient.replacePath("/").path(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(AddressbookModel.class);
		}
		for (AddressbookModel _c : _localList) {
			_response = webclient.replacePath("/").path(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}

	@Test
	public void testAddressbookCreate() {
		// new("MY_NAME1") -> _c1
		AddressbookModel _c1 = new AddressbookModel("MY_NAME1");
		// new("MY_NAME2") -> _c2
		AddressbookModel _c2 = new AddressbookModel("MY_NAME2");
		
		// create(_c1)  -> _c3
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _c3 = _response.readEntity(AddressbookModel.class);

		// create(_c2) -> _c4
		_response = webclient.replacePath("/").post(_c2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _c4 = _response.readEntity(AddressbookModel.class);		
		assertNotNull("ID should be set", _c3.getId());
		assertNotNull("ID should be set", _c4.getId());
		assertThat(_c4.getId(), not(equalTo(_c3.getId())));
		assertEquals("name1 should be set correctly", "MY_NAME1", _c3.getName());
		assertEquals("name2 should be set correctly", "MY_NAME2", _c4.getName());

		// delete(_c3) -> NO_CONTENT
		_response = webclient.replacePath("/").path(_c3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_c4) -> NO_CONTENT
		_response = webclient.replacePath("/").path(_c4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressbookDoubleCreate(
	) {
		// create(new()) -> _c
		Response _response = webclient.replacePath("/").post(new AddressbookModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _c = _response.readEntity(AddressbookModel.class);
		assertNotNull("ID should be set:", _c.getId());		
		
		// create(_c) -> CONFLICT
		_response = webclient.replacePath("/").post(_c);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_c) -> NO_CONTENT
		_response = webclient.replacePath("/").path(_c.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testAddressbookRead(
	) {
		ArrayList<AddressbookModel> _localList = new ArrayList<AddressbookModel>();
		Response _response = null;
		webclient.replacePath("/");
		for (int i = 0; i < LIMIT; i++) {
			_response = webclient.replacePath("/").post(new AddressbookModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(AddressbookModel.class));
		}
	
		// test read on each local element
		for (AddressbookModel _c : _localList) {
			_response = webclient.replacePath("/").path(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(AddressbookModel.class);
		}

		// test read on each listed element
		_response = webclient.replacePath("/").get();
		List<AddressbookModel> _remoteList = new ArrayList<AddressbookModel>(webclient.getCollection(AddressbookModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		AddressbookModel _tmpObj = null;
		for (AddressbookModel _c : _remoteList) {
			_response = webclient.replacePath("/").path(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(AddressbookModel.class);
			assertEquals("ID should be unchanged when reading an addressbook", _c.getId(), _tmpObj.getId());						
		}

		for (AddressbookModel _c : _localList) {
			_response = webclient.replacePath("/").path(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}	

	@Test
	public void testAddressbookMultiRead(
	) {
		// new() -> _c1
		AddressbookModel _c1 = new AddressbookModel();
		
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		AddressbookModel _c2 = _response.readEntity(AddressbookModel.class);

		// read(_c2) -> _c3
		_response = webclient.replacePath("/").path(_c2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _c3 = _response.readEntity(AddressbookModel.class);
		assertEquals("ID should be unchanged after read", _c2.getId(), _c3.getId());		

		// read(_c2) -> _c4
		_response = webclient.replacePath("/").path(_c2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _c4 = _response.readEntity(AddressbookModel.class);
		
		// but: the two objects are not equal !
		assertEquals("ID should be the same", _c3.getId(), _c4.getId());
		assertEquals("name should be the same", _c3.getName(), _c4.getName());
		
		assertEquals("ID should be the same", _c3.getId(), _c2.getId());
		assertEquals("name should be the same", _c3.getName(), _c2.getName());
		
		// delete(_c2)
		_response = webclient.replacePath("/").path(_c2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressbookUpdate(
	) {
		// new() -> _c1
		AddressbookModel _c1 = new AddressbookModel();
		
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		AddressbookModel _c2 = _response.readEntity(AddressbookModel.class);
		
		// change the attributes
		// update(_c2) -> _c3
		_c2.setName("MY_NAME1");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_c2.getId()).put(_c2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _c3 = _response.readEntity(AddressbookModel.class);

		assertNotNull("ID should be set", _c3.getId());
		assertEquals("ID should be unchanged", _c2.getId(), _c3.getId());	
		assertEquals("name should have changed", "MY_NAME1", _c3.getName());

		// reset the attributes
		// update(_c2) -> _c4
		_c2.setName("MY_NAME2");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_c2.getId()).put(_c2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _c4 = _response.readEntity(AddressbookModel.class);

		assertNotNull("ID should be set", _c4.getId());
		assertEquals("ID should be unchanged", _c2.getId(), _c4.getId());	
		assertEquals("name should have changed", "MY_NAME2", _c4.getName());
		
		_response = webclient.replacePath("/").path(_c2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressbookDelete(
	) {
		// new() -> _c0
		AddressbookModel _c0 = new AddressbookModel();
		// create(_c0) -> _c1
		Response _response = webclient.replacePath("/").post(_c0);
		AddressbookModel _c1 = _response.readEntity(AddressbookModel.class);
		
		// read(_c1) -> _tmpObj
		_response = webclient.replacePath("/").path(_c1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _tmpObj = _response.readEntity(AddressbookModel.class);
		assertEquals("ID should be unchanged when reading an addressbook (remote):", _c1.getId(), _tmpObj.getId());						
		
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
	public void testAddressbookDoubleDelete(
	) {
		// new() -> _c0
		AddressbookModel _c0 = new AddressbookModel();
		
		// create(_c0) -> _c1
		Response _response = webclient.replacePath("/").post(_c0);
		AddressbookModel _c1 = _response.readEntity(AddressbookModel.class);

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
}
