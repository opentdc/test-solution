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
		// new() -> _abm
		AddressbookModel _abm = new AddressbookModel();
		assertNull("id should not be set by empty constructor", _abm.getId());
		assertNull("name should not be set by empty constructor", _abm.getName());
	}
	
	@Test
	public void testAddressbookModelConstructor() {		
		// new("MY_NAME") -> _abm
		AddressbookModel _abm = new AddressbookModel("MY_NAME");
		assertNull("id should not be set by constructor", _abm.getId());
		assertEquals("name should be set by constructor", "MY_NAME", _abm.getName());
	}
	
	@Test
	public void testAddressbookIdAttributeChange() {
		// new() -> _abm -> _abm.setId()
		AddressbookModel _abm = new AddressbookModel();
		assertNull("id should not be set by constructor", _abm.getId());
		_abm.setId("MY_ID");
		assertEquals("id should have changed", "MY_ID", _abm.getId());
	}

	@Test
	public void testAddressbookNameAttributeChange() {
		// new() -> _abm -> _abm.setName()
		AddressbookModel _abm = new AddressbookModel();
		assertNull("name should not be set by empty constructor", _abm.getName());
		_abm.setName("MY_NAME");
		assertEquals("name should have changed", "MY_NAME", _abm.getName());
	}
	
	@Test
	public void testAddressbookCreatedBy() {
		// new() -> _abm -> _abm.setCreatedBy()
		AddressbookModel _abm = new AddressbookModel();
		assertNull("createdBy should not be set by empty constructor", _abm.getCreatedBy());
		_abm.setCreatedBy("MY_NAME");
		assertEquals("createdBy should have changed", "MY_NAME", _abm.getCreatedBy());	
	}
	
	@Test
	public void testAddressbookCreatedAt() {
		// new() -> _abm -> _abm.setCreatedAt()
		AddressbookModel _abm = new AddressbookModel();
		assertNull("createdAt should not be set by empty constructor", _abm.getCreatedAt());
		_abm.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _abm.getCreatedAt());
	}
		
	@Test
	public void testAddressbookModifiedBy() {
		// new() -> _abm -> _abm.setModifiedBy()
		AddressbookModel _abm = new AddressbookModel();
		assertNull("modifiedBy should not be set by empty constructor", _abm.getModifiedBy());
		_abm.setModifiedBy("MY_NAME");
		assertEquals("modifiedBy should have changed", "MY_NAME", _abm.getModifiedBy());	
	}
	
	@Test
	public void testAddressbookModifiedAt() {
		// new() -> _abm -> _abm.setModifiedAt()
		AddressbookModel _abm = new AddressbookModel();
		assertNull("modifiedAt should not be set by empty constructor", _abm.getModifiedAt());
		_abm.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _abm.getModifiedAt());
	}

	/********************************** addressbook REST service tests *********************************/	
	@Test
	public void testAddressbookCreateReadDeleteWithEmptyConstructor() {
		// new() -> _abm1
		AddressbookModel _abm1 = new AddressbookModel();
		assertNull("id should not be set by empty constructor", _abm1.getId());
		assertNull("name should not be set by empty constructor", _abm1.getName());

		// create(_abm1) -> BAD_REQUEST (because of empty name)
		Response _response = webclient.replacePath("/").post(_abm1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());

		// _abm1.setName() -> create(_abm1) -> _abm2
		_abm1.setName("testAddressbookCreateReadDeleteWithEmptyConstructor");
		_response = webclient.replacePath("/").post(_abm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _abm2 = _response.readEntity(AddressbookModel.class);
		
		// validate _abm1
		assertNull("create() should not change the id of the local object", _abm1.getId());
		assertEquals("create() should not change the name of the local object", "testAddressbookCreateReadDeleteWithEmptyConstructor", _abm1.getName());
		assertNotNull("create() should set a valid id on the remote object returned", _abm2.getId());
		assertEquals("name of returned object should remain unchanged after remote create", _abm1.getName(), _abm2.getName());
		
		// read(_abm2) -> _abm3
		_response = webclient.replacePath("/").path(_abm2.getId()).get();
		assertEquals("read(" + _abm2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _abm3 = _response.readEntity(AddressbookModel.class);
		assertEquals("id of returned object should be the same", _abm2.getId(), _abm3.getId());
		assertEquals("name of returned object should be unchanged after remote create", _abm2.getName(), _abm3.getName());
		// delete(_abm3)
		_response = webclient.replacePath("/").path(_abm3.getId()).delete();
		assertEquals("delete(" + _abm3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressbookCreateReadDelete() {
		// new("MY_NAME") -> _abm1
		AddressbookModel _abm1 = new AddressbookModel("testAddressbookCreateReadDelete");
		assertNull("id should not be set by constructor", _abm1.getId());
		assertEquals("name should be set by constructor", "testAddressbookCreateReadDelete", _abm1.getName());
		// create(_abm1) -> _abm2
		Response _response = webclient.replacePath("/").post(_abm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _abm2 = _response.readEntity(AddressbookModel.class);
		assertNull("id should be still null after remote create", _abm1.getId());
		assertEquals("name should be unchanged after remote create", "testAddressbookCreateReadDelete", _abm1.getName());
		
		assertNotNull("id of returned object should be set", _abm2.getId());
		assertEquals("name of returned object should be unchanged after remote create", "testAddressbookCreateReadDelete", _abm2.getName());
		// read(_abm2)  -> _abm3
		_response = webclient.replacePath("/").path(_abm2.getId()).get();
		assertEquals("read(" + _abm2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _abm3 = _response.readEntity(AddressbookModel.class);
		assertEquals("id of returned object should be the same", _abm2.getId(), _abm3.getId());
		assertEquals("name of returned object should be unchanged after remote create", _abm2.getName(), _abm3.getName());
		// delete(_abm3)
		_response = webclient.replacePath("/").path(_abm3.getId()).delete();
		assertEquals("delete(" + _abm3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressbookWithClientSideId() {
		// new() -> _abm1 -> _abm1.setId()
		AddressbookModel _abm1 = new AddressbookModel("testAddressbookWithClientSideId");
		_abm1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _abm1.getId());
		// create(_abm1) -> BAD_REQUEST
		Response _response = webclient.replacePath("/").post(_abm1);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressbookWithDuplicateId() {
		// create(new()) -> _abm1
		Response _response = webclient.replacePath("/").post(new AddressbookModel("testAddressbookWithDuplicateId"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _abm1 = _response.readEntity(AddressbookModel.class);

		// new() -> _abm2 -> _abm2.setId(_abm1.getId())
		AddressbookModel _abm2 = new AddressbookModel("testAddressbookWithDuplicateId2");
		_abm2.setId(_abm1.getId());		// wrongly create a 2nd AddressbookModel object with the same ID
		
		// create(_abm2) -> CONFLICT
		_response = webclient.replacePath("/").post(_abm2);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete _abm1
		_response = webclient.replacePath("/").path(_abm1.getId()).delete();
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
			_response = webclient.post(new AddressbookModel("testAddressbookList" + i));
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
		for (AddressbookModel _abm : _remoteList) {
			_remoteListIds.add(_abm.getId());
		}
		
		for (AddressbookModel _abm : _localList) {
			assertTrue("addressbook <" + _abm.getId() + "> should be listed", _remoteListIds.contains(_abm.getId()));
		}
		for (AddressbookModel _abm : _localList) {
			_response = webclient.replacePath("/").path(_abm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(AddressbookModel.class);
		}
		for (AddressbookModel _abm : _localList) {
			_response = webclient.replacePath("/").path(_abm.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}

	@Test
	public void testAddressbookCreate() {
		// new("testAddressbookCreate1") -> _abm1
		AddressbookModel _abm1 = new AddressbookModel("testAddressbookCreate1");
		// new("testAddressbookCreate2") -> _abm2
		AddressbookModel _abm2 = new AddressbookModel("testAddressbookCreate2");
		
		// create(_abm1)  -> _abm3
		Response _response = webclient.replacePath("/").post(_abm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _abm3 = _response.readEntity(AddressbookModel.class);

		// create(_abm2) -> _abm4
		_response = webclient.replacePath("/").post(_abm2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _abm4 = _response.readEntity(AddressbookModel.class);		
		assertNotNull("ID should be set", _abm3.getId());
		assertNotNull("ID should be set", _abm4.getId());
		assertThat(_abm4.getId(), not(equalTo(_abm3.getId())));
		assertEquals("name1 should be set correctly", "testAddressbookCreate1", _abm3.getName());
		assertEquals("name2 should be set correctly", "testAddressbookCreate2", _abm4.getName());

		// delete(_abm3) -> NO_CONTENT
		_response = webclient.replacePath("/").path(_abm3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_abm4) -> NO_CONTENT
		_response = webclient.replacePath("/").path(_abm4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressbookDoubleCreate(
	) {
		// create(new("testAddressbookDoubleCreate")) -> _abm
		Response _response = webclient.replacePath("/").post(new AddressbookModel("testAddressbookDoubleCreate"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _abm = _response.readEntity(AddressbookModel.class);
		assertNotNull("ID should be set:", _abm.getId());		
		
		// create(_abm) -> CONFLICT
		_response = webclient.replacePath("/").post(_abm);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_abm) -> NO_CONTENT
		_response = webclient.replacePath("/").path(_abm.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testAddressbookRead(
	) {
		ArrayList<AddressbookModel> _localList = new ArrayList<AddressbookModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			_response = webclient.replacePath("/").post(new AddressbookModel("testAddressbookRead" + i));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(AddressbookModel.class));
		}
	
		// test read on each local element
		for (AddressbookModel _abm : _localList) {
			_response = webclient.replacePath("/").path(_abm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(AddressbookModel.class);
		}

		// test read on each listed element
		_response = webclient.replacePath("/").get();
		List<AddressbookModel> _remoteList = new ArrayList<AddressbookModel>(webclient.getCollection(AddressbookModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		AddressbookModel _tmpObj = null;
		for (AddressbookModel _abm : _remoteList) {
			_response = webclient.replacePath("/").path(_abm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(AddressbookModel.class);
			assertEquals("ID should be unchanged when reading an addressbook", _abm.getId(), _tmpObj.getId());						
		}

		for (AddressbookModel _abm : _localList) {
			_response = webclient.replacePath("/").path(_abm.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}	

	@Test
	public void testAddressbookMultiRead(
	) {
		// new() -> _abm1
		AddressbookModel _abm1 = new AddressbookModel("testAddressbookMultiRead");
		
		// create(_abm1) -> _abm2
		Response _response = webclient.replacePath("/").post(_abm1);
		AddressbookModel _abm2 = _response.readEntity(AddressbookModel.class);

		// read(_abm2) -> _abm3
		_response = webclient.replacePath("/").path(_abm2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _abm3 = _response.readEntity(AddressbookModel.class);
		assertEquals("ID should be unchanged after read", _abm2.getId(), _abm3.getId());		

		// read(_abm2) -> _abm4
		_response = webclient.replacePath("/").path(_abm2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _abm4 = _response.readEntity(AddressbookModel.class);
		
		// but: the two objects are not equal !
		assertEquals("ID should be the same", _abm3.getId(), _abm4.getId());
		assertEquals("name should be the same", _abm3.getName(), _abm4.getName());
		
		assertEquals("ID should be the same", _abm3.getId(), _abm2.getId());
		assertEquals("name should be the same", _abm3.getName(), _abm2.getName());
		
		// delete(_abm2)
		_response = webclient.replacePath("/").path(_abm2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressbookUpdate(
	) {
		// new() -> _abm1
		AddressbookModel _abm1 = new AddressbookModel("testAddressbookUpdate");
		
		// create(_abm1) -> _abm2
		Response _response = webclient.replacePath("/").post(_abm1);
		AddressbookModel _abm2 = _response.readEntity(AddressbookModel.class);
		
		// change the attributes
		// update(_abm2) -> _abm3
		_abm2.setName("testAddressbookUpdate1");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_abm2.getId()).put(_abm2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _abm3 = _response.readEntity(AddressbookModel.class);

		assertNotNull("ID should be set", _abm3.getId());
		assertEquals("ID should be unchanged", _abm2.getId(), _abm3.getId());	
		assertEquals("name should have changed", "testAddressbookUpdate1", _abm3.getName());

		// reset the attributes
		// update(_abm2) -> _abm4
		_abm2.setName("testAddressbookUpdate2");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_abm2.getId()).put(_abm2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _abm4 = _response.readEntity(AddressbookModel.class);

		assertNotNull("ID should be set", _abm4.getId());
		assertEquals("ID should be unchanged", _abm2.getId(), _abm4.getId());	
		assertEquals("name should have changed", "testAddressbookUpdate2", _abm4.getName());
		
		_response = webclient.replacePath("/").path(_abm2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressbookDelete(
	) {
		// create(testAddressbookDelete) -> _abm1
		Response _response = webclient.replacePath("/").post(new AddressbookModel("testAddressbookDelete"));
		AddressbookModel _abm1 = _response.readEntity(AddressbookModel.class);
		
		// read(_abm1) -> _abm2
		_response = webclient.replacePath("/").path(_abm1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _abm2 = _response.readEntity(AddressbookModel.class);
		assertEquals("ID should be unchanged when reading an addressbook (remote):", _abm1.getId(), _abm2.getId());						
		
		// delete(_abm1) -> OK
		_response = webclient.replacePath("/").path(_abm1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	
		// read the deleted object twice
		// read(_abm1) -> NOT_FOUND
		_response = webclient.replacePath("/").path(_abm1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read(_abm1) -> NOT_FOUND
		_response = webclient.replacePath("/").path(_abm1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressbookDoubleDelete(
	) {
		// create(testAddressbookDoubleDelete) -> _abm1
		Response _response = webclient.replacePath("/").post(new AddressbookModel("testAddressbookDoubleDelete"));
		AddressbookModel _abm1 = _response.readEntity(AddressbookModel.class);

		// read(_abm1) -> OK
		_response = webclient.replacePath("/").path(_abm1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		// delete(_abm1) -> NO_CONTENT
		_response = webclient.replacePath("/").path(_abm1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		// read(_abm1) -> NOT_FOUND
		_response = webclient.replacePath("/").path(_abm1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// delete _abm1 -> NOT_FOUND
		_response = webclient.replacePath("/").path(_abm1.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read _abm1 -> NOT_FOUND
		_response = webclient.replacePath("/").path(_abm1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressbookModifications() {
		// create(testAddressbookModifications) -> _abm1
		Response _response = webclient.replacePath("/").post(new AddressbookModel("testAddressbookModifications"));
		AddressbookModel _abm1 = _response.readEntity(AddressbookModel.class);
		
		// test createdAt and createdBy
		assertNotNull("create() should set createdAt", _abm1.getCreatedAt());
		assertNotNull("create() should set createdBy", _abm1.getCreatedBy());
		// test modifiedAt and modifiedBy (= same as createdAt/createdBy)
		assertNotNull("create() should set modifiedAt", _abm1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _abm1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _abm1.getCreatedAt(), _abm1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _abm1.getCreatedBy(), _abm1.getModifiedBy());
		
		// update(_abm1)  -> _abm2
		_abm1.setName("testAddressbookModifications2");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_abm1.getId()).put(_abm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _abm2 = _response.readEntity(AddressbookModel.class);

		// test createdAt and createdBy (unchanged)
		assertEquals("update() should not change createdAt", _abm1.getCreatedAt(), _abm2.getCreatedAt());
		assertEquals("update() should not change createdBy", _abm1.getCreatedBy(), _abm2.getCreatedBy());
		
		// test modifiedAt and modifiedBy (= different from createdAt/createdBy)
		assertThat(_abm2.getModifiedAt(), not(equalTo(_abm2.getCreatedAt())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_abm2.getModifiedBy(), not(equalTo(_abm2.getCreatedBy())));

		// update(o) with modifiedBy/At set on client side -> ignored by server
		_abm1.setModifiedBy("MYSELF");
		_abm1.setModifiedAt(new Date(1000));
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_abm1.getId()).put(_abm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressbookModel _abm3 = _response.readEntity(AddressbookModel.class);
		
		// test, that modifiedBy really ignored the client-side value "MYSELF"
		assertThat(_abm1.getModifiedBy(), not(equalTo(_abm3.getModifiedBy())));
		// check whether the client-side modifiedAt() is ignored
		assertThat(_abm1.getModifiedAt(), not(equalTo(_abm3.getModifiedAt())));
		
		// delete(_abm1) -> NO_CONTENT
		_response = webclient.replacePath("/").path(_abm1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
}
