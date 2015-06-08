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
	
	/********************************** resource attributes tests *********************************/	
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
	public void testResourceCreatedBy() {
		// new() -> _o -> _o.setCreatedBy()
		ResourceModel _o = new ResourceModel();
		assertNull("createdBy should not be set by empty constructor", _o.getCreatedBy());
		_o.setCreatedBy("MY_NAME");
		assertEquals("createdBy should have changed", "MY_NAME", _o.getCreatedBy());	
	}
	
	@Test
	public void testResourceCreatedAt() {
		// new() -> _o -> _o.setCreatedAt()
		ResourceModel _o = new ResourceModel();
		assertNull("createdAt should not be set by empty constructor", _o.getCreatedAt());
		_o.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _o.getCreatedAt());
	}
		
	@Test
	public void testResourceModifiedBy() {
		// new() -> _o -> _o.setModifiedBy()
		ResourceModel _o = new ResourceModel();
		assertNull("modifiedBy should not be set by empty constructor", _o.getModifiedBy());
		_o.setModifiedBy("MY_NAME");
		assertEquals("modifiedBy should have changed", "MY_NAME", _o.getModifiedBy());	
	}
	
	@Test
	public void testResourceModifiedAt() {
		// new() -> _o -> _o.setModifiedAt()
		ResourceModel _o = new ResourceModel();
		assertNull("modifiedAt should not be set by empty constructor", _o.getModifiedAt());
		_o.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _o.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
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
		_response = webclient.replacePath("/").path(_c2.getId()).get();
		assertEquals("read(" + _c2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _c3 = _response.readEntity(ResourceModel.class);
		assertEquals("id of returned object should be the same", _c2.getId(), _c3.getId());
		assertEquals("name of returned object should be unchanged after remote create", _c2.getName(), _c3.getName());
		assertEquals("firstname of returned object should be unchanged after remote create", _c2.getFirstName(), _c3.getFirstName());
		assertEquals("lastname of returned object should be unchanged after remote create", _c2.getLastName(), _c3.getLastName());
		assertEquals("contactId of returned object should be unchanged after remote create", _c2.getContactId(), _c3.getContactId());
		// delete(_c3)
		_response = webclient.replacePath("/").path(_c3.getId()).delete();
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
		_response = webclient.replacePath("/").path(_c2.getId()).get();
		assertEquals("read(" + _c2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _c3 = _response.readEntity(ResourceModel.class);
		assertEquals("id of returned object should be the same", _c2.getId(), _c3.getId());
		assertEquals("name of returned object should be unchanged after remote create", _c2.getName(), _c3.getName());
		assertEquals("firstname of returned object should be unchanged after remote create", _c2.getFirstName(), _c3.getFirstName());
		assertEquals("lastname of returned object should be unchanged after remote create", _c2.getLastName(), _c3.getLastName());
		assertEquals("contactId of returned object should be unchanged after remote create", _c2.getContactId(), _c3.getContactId());
		// delete(_c3)
		_response = webclient.replacePath("/").path(_c3.getId()).delete();
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

		_response = webclient.replacePath("/").path(_c2.getId()).delete();
		assertEquals("delete(" + _c2.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

	}
	
	@Test
	public void testResourceList(
	) {		
		ArrayList<ResourceModel> _localList = new ArrayList<ResourceModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			// create(new()) -> _localList
			_response = webclient.replacePath("/").post(new ResourceModel());
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
			_response = webclient.replacePath("/").path(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(ResourceModel.class);
		}
		for (ResourceModel _c : _localList) {
			_response = webclient.replacePath("/").path(_c.getId()).delete();
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
		Response _response = webclient.replacePath("/").post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _c3 = _response.readEntity(ResourceModel.class);

		// create(_c2) -> _c4
		_response = webclient.replacePath("/").post(_c2);
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

		// delete(_c3) -> NO_CONTENT
		_response = webclient.replacePath("/").path(_c3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_c4) -> NO_CONTENT
		_response = webclient.replacePath("/").path(_c4.getId()).delete();
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
		_response = webclient.replacePath("/").path(_c.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testResourceRead(
	) {
		ArrayList<ResourceModel> _localList = new ArrayList<ResourceModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			_response = webclient.replacePath("/").post(new ResourceModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(ResourceModel.class));
		}
	
		// test read on each local element
		for (ResourceModel _c : _localList) {
			_response = webclient.replacePath("/").path(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(ResourceModel.class);
		}

		// test read on each listed element
		_response = webclient.replacePath("/").get();
		List<ResourceModel> _remoteList = new ArrayList<ResourceModel>(webclient.getCollection(ResourceModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ResourceModel _tmpObj = null;
		for (ResourceModel _c : _remoteList) {
			_response = webclient.replacePath("/").path(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(ResourceModel.class);
			assertEquals("ID should be unchanged when reading a resource", _c.getId(), _tmpObj.getId());						
		}

		for (ResourceModel _c : _localList) {
			_response = webclient.replacePath("/").path(_c.getId()).delete();
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
		_response = webclient.replacePath("/").path(_c2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _c3 = _response.readEntity(ResourceModel.class);
		assertEquals("ID should be unchanged after read", _c2.getId(), _c3.getId());		

		// read(_c2) -> _c4
		_response = webclient.replacePath("/").path(_c2.getId()).get();
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
		_response = webclient.replacePath("/").path(_c2.getId()).delete();
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
		
		_response = webclient.replacePath("/").path(_c2.getId()).delete();
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
		_response = webclient.replacePath("/").path(_c1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _tmpObj = _response.readEntity(ResourceModel.class);
		assertEquals("ID should be unchanged when reading a resource (remote):", _c1.getId(), _tmpObj.getId());						
		
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
	public void testResourceDoubleDelete(
	) {
		// new() -> _c0
		ResourceModel _c0 = new ResourceModel();
		
		// create(_c0) -> _c1
		Response _response = webclient.replacePath("/").post(_c0);
		ResourceModel _c1 = _response.readEntity(ResourceModel.class);

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
	
	@Test
	public void testResourceModifications() {
		// create(new ResourceModel()) -> _o
		Response _response = webclient.replacePath("/").post(new ResourceModel());
		ResourceModel _o = _response.readEntity(ResourceModel.class);
		
		// test createdAt and createdBy
		assertNotNull("create() should set createdAt", _o.getCreatedAt());
		assertNotNull("create() should set createdBy", _o.getCreatedBy());
		// test modifiedAt and modifiedBy (= same as createdAt/createdBy)
		assertNotNull("create() should set modifiedAt", _o.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _o.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _o.getCreatedAt(), _o.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _o.getCreatedBy(), _o.getModifiedBy());
		
		// update(_o)  -> _o2
		_o.setName("MY_NAME2");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_o.getId()).put(_o);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _o2 = _response.readEntity(ResourceModel.class);

		// test createdAt and createdBy (unchanged)
		assertEquals("update() should not change createdAt", _o.getCreatedAt(), _o2.getCreatedAt());
		assertEquals("update() should not change createdBy", _o.getCreatedBy(), _o2.getCreatedBy());
		
		// test modifiedAt and modifiedBy (= different from createdAt/createdBy)
		assertThat(_o2.getModifiedAt(), not(equalTo(_o2.getCreatedAt())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_o2.getModifiedBy(), not(equalTo(_o2.getCreatedBy())));

		// update(o2) with createdBy set on client side -> error
		String _createdBy = _o.getCreatedBy();
		_o.setCreatedBy("MYSELF");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_o.getId()).put(_o);
		assertEquals("update() should return with status BAD_REQUEST", 
				Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_o.setCreatedBy(_createdBy);

		// update(o) with createdAt set on client side -> error
		Date _d = _o.getCreatedAt();
		_o.setCreatedAt(new Date(1000));
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_o.getId()).put(_o);
		assertEquals("update() should return with status BAD_REQUEST", 
				Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_o.setCreatedAt(_d);

		// update(o) with modifiedBy/At set on client side -> ignored by server
		_o.setModifiedBy("MYSELF");
		_o.setModifiedAt(new Date(1000));
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(_o.getId()).put(_o);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _o3 = _response.readEntity(ResourceModel.class);
		
		// test, that modifiedBy really ignored the client-side value "MYSELF"
		assertThat(_o.getModifiedBy(), not(equalTo(_o3.getModifiedBy())));
		// check whether the client-side modifiedAt() is ignored
		assertThat(_o.getModifiedAt(), not(equalTo(_o3.getModifiedAt())));
		
		// delete(_o) -> NO_CONTENT
		_response = webclient.replacePath("/").path(_o.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
}
