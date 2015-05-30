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
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.opentdc.resources.ResourceModel;
import org.opentdc.resources.ResourcesService;

import test.org.opentdc.AbstractTestClient;

public class ResourcesTest extends AbstractTestClient<ResourcesService> {

	public static final String API = "api/resource/";

	@Before
	public void initializeTests(
	) {
		initializeTest(API, ResourcesService.class);
	}
	
	/********************************** resource tests *********************************/	
	@Test
	public void testResourceModelEmptyConstructor() {
		// new() -> _c
		ResourceModel _c = new ResourceModel();
		assertNull("id should not be set by empty constructor", _c.getId());
		assertNull("name should not be set by empty constructor", _c.getName());
		assertNull("firstname should not be set by empty constructor", _c.getFirstName());
		assertNull("lastname should not be set by empty constructor", _c.getLastName());
		assertNull("contactId should not be set by empty constructor", _c.getContactId());
	}
	
	@Test
	public void testResourceModelConstructor() {		
		// new("MY_NAME", "MY_FNAME", "MY_LNAME", "MY_ID") -> _c
		ResourceModel _c = new ResourceModel("MY_NAME", "MY_FNAME", "MY_LNAME", "MY_ID");
		assertNull("id should not be set by constructor", _c.getId());
		assertEquals("name should be set by constructor", "MY_NAME", _c.getName());
		assertEquals("firstname should be set by constructor", "MY_FNAME", _c.getFirstName());
		assertEquals("lastname should be set by constructor", "MY_LNAME", _c.getLastName());
		assertEquals("contactId should be set by constructor", "MY_ID", _c.getContactId());
	}
	
	@Test
	public void testResourceIdAttributeChange() {
		// new() -> _c -> _c.setId()
		ResourceModel _c = new ResourceModel();
		assertNull("id should not be set by constructor", _c.getId());
		_c.setId("MY_ID");
		assertEquals("id should have changed:", "MY_ID", _c.getId());
	}

	@Test
	public void testResourceNameAttributeChange() {
		// new() -> _c -> _c.setName()
		ResourceModel _c = new ResourceModel();
		assertNull("name should not be set by empty constructor", _c.getName());
		_c.setName("MY_NAME");
		assertEquals("name should have changed:", "MY_NAME", _c.getName());
	}
	
	@Test
	public void testResourceFirstNameAttributeChange() {
		// new() -> _c -> _c.setFirstName()
		ResourceModel _c = new ResourceModel();
		assertNull("firstname should not be set by empty constructor", _c.getFirstName());
		_c.setFirstName("MY_FNAME");
		assertEquals("firstname should have changed:", "MY_FNAME", _c.getFirstName());
	}
	
	@Test
	public void testResourceLastNameAttributeChange() {
		// new() -> _c -> _c.setLastName()
		ResourceModel _c = new ResourceModel();
		assertNull("lastname should not be set by empty constructor", _c.getLastName());
		_c.setLastName("MY_LNAME");
		assertEquals("lastname should have changed:", "MY_LNAME", _c.getLastName());
	}
	
	@Test
	public void testResourceContactIdAttributeChange() {
		// new() -> _c -> _c.setContactId()
		ResourceModel _c = new ResourceModel();
		assertNull("contactId should not be set by empty constructor", _c.getContactId());
		_c.setContactId("MY_ID");
		assertEquals("contactId should have changed:", "MY_ID", _c.getContactId());
	}
	
	@Test
	public void testResourceCreateReadDeleteWithEmptyConstructor() {
		// new() -> _c1
		ResourceModel _c1 = new ResourceModel();
		assertNull("id should not be set by empty constructor", _c1.getId());
		assertNull("name should not be set by empty constructor", _c1.getName());
		assertNull("firstname should not be set by empty constructor", _c1.getFirstName());
		assertNull("lastname should not be set by empty constructor", _c1.getLastName());
		assertNull("contactId should not be set by empty constructor", _c1.getContactId());
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _c2 = _response.readEntity(ResourceModel.class);
		assertNull("create() should not change the id of the local object", _c1.getId());
		assertNull("create() should not change the name of the local object", _c1.getName());
		assertNull("create() should not change the firstname of the local object", _c1.getFirstName());
		assertNull("create() should not change the lastname of the local object", _c1.getLastName());
		assertNull("create() should not change the contactId of the local object", _c1.getContactId());
		
		assertNotNull("create() should set a valid id on the remote object returned", _c2.getId());
		assertNull("name of returned object should still be null after remote create", _c2.getName());
		assertNull("firstname of returned object should still be null after remote create", _c2.getFirstName());
		assertNull("lastname of returned object should still be null after remote create", _c2.getLastName());
		assertNull("contactId of returned object should still be null after remote create", _c2.getContactId());
		// read(_c2) -> _c3
		_response = webclient.replacePath(_c2.getId()).get();
		assertEquals("read(" + _c2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _c3 = _response.readEntity(ResourceModel.class);
		assertEquals("id of returned object should be the same", _c2.getId(), _c3.getId());
		assertEquals("name of returned object should be unchanged after remote create", _c2.getName(), _c3.getName());
		assertEquals("firstname of returned object should be unchanged after remote create", _c2.getFirstName(), _c3.getFirstName());
		assertEquals("lastname of returned object should be unchanged after remote create", _c2.getLastName(), _c3.getLastName());
		assertEquals("contactId of returned object should be unchanged after remote create", _c2.getContactId(), _c3.getContactId());
		// delete(_c3)
		_response = webclient.replacePath(_c3.getId()).delete();
		assertEquals("delete(" + _c3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testResourceCreateReadDelete() {
		// new("MY_NAME", "MY_FNAME", "MY_LNAME", "MY_ID")  -> _c1
		ResourceModel _c1 = new ResourceModel("MY_NAME", "MY_FNAME", "MY_LNAME", "MY_ID");
		assertNull("id should not be set by constructor", _c1.getId());
		assertEquals("name should be set by constructor", "MY_NAME", _c1.getName());
		assertEquals("firstname should be set by constructor", "MY_FNAME", _c1.getFirstName());
		assertEquals("lastname should be set by constructor", "MY_LNAME", _c1.getLastName());
		assertEquals("contactId should be set by constructor", "MY_ID", _c1.getContactId());
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _c2 = _response.readEntity(ResourceModel.class);
		assertNull("id should be still null after remote create", _c1.getId());
		assertEquals("name should be unchanged after remote create", "MY_NAME", _c1.getName());
		assertEquals("firstname should be unchanged after remote create", "MY_FNAME", _c1.getFirstName());
		assertEquals("lastname should be unchanged after remote create", "MY_LNAME", _c1.getLastName());
		assertEquals("contactId should be unchanged after remote create", "MY_ID", _c1.getContactId());
		
		assertNotNull("id of returned object should be set", _c2.getId());
		assertEquals("name of returned object should be unchanged after remote create", "MY_NAME", _c2.getName());
		assertEquals("firstname of returned object should be unchanged after remote create", "MY_FNAME", _c2.getFirstName());
		assertEquals("lastname of returned object should be unchanged after remote create", "MY_LNAME", _c2.getLastName());
		assertEquals("contactId of returned object should be unchanged after remote create", "MY_ID", _c2.getContactId());
		// read(_c2)  -> _c3
		_response = webclient.replacePath(_c2.getId()).get();
		assertEquals("read(" + _c2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _c3 = _response.readEntity(ResourceModel.class);
		assertEquals("id of returned object should be the same", _c2.getId(), _c3.getId());
		assertEquals("name of returned object should be unchanged after remote create", _c2.getName(), _c3.getName());
		assertEquals("firstname of returned object should be unchanged after remote create", _c2.getFirstName(), _c3.getFirstName());
		assertEquals("lastname of returned object should be unchanged after remote create", _c2.getLastName(), _c3.getLastName());
		assertEquals("contactId of returned object should be unchanged after remote create", _c2.getContactId(), _c3.getContactId());
		// delete(_c3)
		_response = webclient.replacePath(_c3.getId()).delete();
		assertEquals("delete(" + _c3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateResourceWithClientSideId() {
		// new() -> _c1 -> _c1.setId()
		ResourceModel _c1 = new ResourceModel();
		_c1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _c1.getId());
		// create(_c1) -> BAD_REQUEST
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateResourceWithDuplicateId() {
		// create(new()) -> _c2
		Response _response = webclient.replacePath("/").post(new ResourceModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _c2 = _response.readEntity(ResourceModel.class);

		// new() -> _c3 -> _c3.setId(_c2.getId())
		ResourceModel _c3 = new ResourceModel();
		_c3.setId(_c2.getId());		// wrongly create a 2nd ResourceModel object with the same ID
		
		// create(_c3) -> CONFLICT
		_response = webclient.replacePath("/").post(_c3);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testResourceList(
	) {		
		ArrayList<ResourceModel> _localList = new ArrayList<ResourceModel>();
		Response _response = null;
		webclient.replacePath("/");
		for (int i = 0; i < LIMIT; i++) {
			// create(new()) -> _localList
			_response = webclient.post(new ResourceModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(ResourceModel.class));
		}
		
		// list(/) -> _remoteList
		_response = webclient.replacePath("/").get();
		List<ResourceModel> _remoteList = new ArrayList<ResourceModel>(webclient.getCollection(ResourceModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (ResourceModel _c : _remoteList) {
			_remoteListIds.add(_c.getId());
		}
		
		for (ResourceModel _c : _localList) {
			assertTrue("resource <" + _c.getId() + "> should be listed", _remoteListIds.contains(_c.getId()));
		}
		for (ResourceModel _c : _localList) {
			_response = webclient.replacePath(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(ResourceModel.class);
		}
		for (ResourceModel _c : _localList) {
			_response = webclient.replacePath(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}
	
	@Test
	public void testResourceCreate() {
		// new("MY_TITLE", "MY_DESC") -> _c1
		ResourceModel _c1 = new ResourceModel("MY_NAME1", "MY_FNAME1", "MY_LNAME1", "MY_ID1");
		// new("MY_TITLE2", "MY_DESC2") -> _c2
		ResourceModel _c2 = new ResourceModel("MY_NAME2", "MY_FNAME2", "MY_LNAME2", "MY_ID2");
		
		// create(_c1)  -> _c3
		Response _response = webclient.post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _c3 = _response.readEntity(ResourceModel.class);

		// create(_c2) -> _c4
		_response = webclient.post(_c2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _c4 = _response.readEntity(ResourceModel.class);		
		assertNotNull("ID should be set", _c3.getId());
		assertNotNull("ID should be set", _c4.getId());
		assertThat(_c4.getId(), not(equalTo(_c3.getId())));
		assertEquals("name1 should be set correctly", "MY_NAME1", _c3.getName());
		assertEquals("firstname1 should be set correctly", "MY_FNAME1", _c3.getFirstName());
		assertEquals("lastname1 should be set correctly", "MY_LNAME1", _c3.getLastName());
		assertEquals("contactId1 should be set correctly", "MY_ID1", _c3.getContactId());
		assertEquals("name2 should be set correctly", "MY_NAME2", _c4.getName());
		assertEquals("firstname2 should be set correctly", "MY_FNAME2", _c4.getFirstName());
		assertEquals("lastname2 should be set correctly", "MY_LNAME2", _c4.getLastName());
		assertEquals("contactId2 should be set correctly", "MY_ID2", _c4.getContactId());

		// delete(_c1) -> METHOD_NOT_ALLOWED  (_c1.getId() = null)
		_response = webclient.replacePath(_c1.getId()).delete();
		assertEquals("delete() should return with status METHOD_NOT_ALLOWED", Status.METHOD_NOT_ALLOWED.getStatusCode(), _response.getStatus());

		// delete(_c2) -> METHOD_NOT_ALLOWED  (_c2.getId() = null)
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
	public void testResourceDoubleCreate(
	) {
		// create(new()) -> _c
		Response _response = webclient.replacePath("/").post(new ResourceModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _c = _response.readEntity(ResourceModel.class);
		assertNotNull("ID should be set", _c.getId());		
		
		// create(_c) -> CONFLICT
		_response = webclient.replacePath("/").post(_c);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_c) -> NO_CONTENT
		_response = webclient.replacePath(_c.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testResourceRead(
	) {
		ArrayList<ResourceModel> _localList = new ArrayList<ResourceModel>();
		Response _response = null;
		webclient.replacePath("/");
		for (int i = 0; i < LIMIT; i++) {
			_response = webclient.post(new ResourceModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(ResourceModel.class));
		}
	
		// test read on each local element
		for (ResourceModel _c : _localList) {
			_response = webclient.replacePath(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(ResourceModel.class);
		}

		// test read on each listed element
		_response = webclient.replacePath("/").get();
		List<ResourceModel> _remoteList = new ArrayList<ResourceModel>(webclient.getCollection(ResourceModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ResourceModel _tmpObj = null;
		for (ResourceModel _c : _remoteList) {
			_response = webclient.replacePath(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(ResourceModel.class);
			assertEquals("ID should be unchanged when reading a resource", _c.getId(), _tmpObj.getId());						
		}

		// TODO: "reading a resource with ID = null should fail" -> ValidationException
		// TODO: "reading a non-existing resource should fail"

		for (ResourceModel _c : _localList) {
			_response = webclient.replacePath(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}	

	@Test
	public void testResourceMultiRead(
	) {
		// new() -> _c1
		ResourceModel _c1 = new ResourceModel();
		
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		ResourceModel _c2 = _response.readEntity(ResourceModel.class);

		// read(_c2) -> _c3
		_response = webclient.replacePath(_c2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _c3 = _response.readEntity(ResourceModel.class);
		assertEquals("ID should be unchanged after read", _c2.getId(), _c3.getId());		

		// read(_c2) -> _c4
		_response = webclient.replacePath(_c2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _c4 = _response.readEntity(ResourceModel.class);
		
		// but: the two objects are not equal !
		assertEquals("ID should be the same", _c3.getId(), _c4.getId());
		assertEquals("name should be the same", _c3.getName(), _c4.getName());
		assertEquals("firstname should be the same", _c3.getFirstName(), _c4.getFirstName());
		assertEquals("lastname should be the same", _c3.getLastName(), _c4.getLastName());
		assertEquals("contactId should be the same", _c3.getContactId(), _c4.getContactId());
		
		assertEquals("ID should be the same", _c3.getId(), _c2.getId());
		assertEquals("name should be the same", _c3.getName(), _c2.getName());
		assertEquals("firstname should be the same", _c3.getFirstName(), _c2.getFirstName());
		assertEquals("lastname should be the same", _c3.getLastName(), _c2.getLastName());
		assertEquals("contactId should be the same", _c3.getContactId(), _c2.getContactId());
		
		// delete(_c2)
		_response = webclient.replacePath(_c2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testResourceUpdate(
	) {
		// new() -> _c1
		ResourceModel _c1 = new ResourceModel();
		
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").post(_c1);
		ResourceModel _c2 = _response.readEntity(ResourceModel.class);
		
		// change the attributes
		// update(_c2) -> _c3
		_c2.setName("MY_NAME1");
		_c2.setFirstName("MY_FNAME1");
		_c2.setLastName("MY_LNAME1");
		_c2.setContactId("MY_ID1");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_c2.getId()).put(_c2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _c3 = _response.readEntity(ResourceModel.class);

		assertNotNull("ID should be set", _c3.getId());
		assertEquals("ID should be unchanged", _c2.getId(), _c3.getId());	
		assertEquals("name should have changed", "MY_NAME1", _c3.getName());
		assertEquals("firstname should have changed", "MY_FNAME1", _c3.getFirstName());
		assertEquals("lastname should have changed", "MY_LNAME1", _c3.getLastName());
		assertEquals("contactId should have changed", "MY_ID1", _c3.getContactId());

		// reset the attributes
		// update(_c2) -> _c4
		_c2.setName("MY_NAME2");
		_c2.setFirstName("MY_FNAME2");
		_c2.setLastName("MY_LNAME2");
		_c2.setContactId("MY_ID2");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_c2.getId()).put(_c2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _c4 = _response.readEntity(ResourceModel.class);

		assertNotNull("ID should be set", _c4.getId());
		assertEquals("ID should be unchanged", _c2.getId(), _c4.getId());	
		assertEquals("name should have changed", "MY_NAME2", _c4.getName());
		assertEquals("firstname should have changed", "MY_FNAME2", _c4.getFirstName());
		assertEquals("lastname should have changed", "MY_LNAME2", _c4.getLastName());
		assertEquals("contactId should have changed", "MY_ID2", _c4.getContactId());
		
		_response = webclient.replacePath(_c2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testResourceDelete(
	) {
		// new() -> _c0
		ResourceModel _c0 = new ResourceModel();
		// create(_c0) -> _c1
		Response _response = webclient.replacePath("/").post(_c0);
		ResourceModel _c1 = _response.readEntity(ResourceModel.class);
		
		// read(_c0) -> _tmpObj
		_response = webclient.replacePath(_c0.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _tmpObj = _response.readEntity(ResourceModel.class);
		assertEquals("ID should be unchanged when reading a resource (remote):", _c0.getId(), _tmpObj.getId());						

		// read(_c1) -> _tmpObj
		_response = webclient.replacePath(_c1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		_tmpObj = _response.readEntity(ResourceModel.class);
		assertEquals("ID should be unchanged when reading a resource (remote):", _c1.getId(), _tmpObj.getId());						
		
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
	public void testResourceDoubleDelete(
	) {
		// new() -> _c0
		ResourceModel _c0 = new ResourceModel();
		
		// create(_c0) -> _c1
		Response _response = webclient.replacePath("/").post(_c0);
		ResourceModel _c1 = _response.readEntity(ResourceModel.class);

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
