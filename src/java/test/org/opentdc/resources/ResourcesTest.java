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

public class ResourcesTest extends AbstractTestClient {
	private static AddressbookModel adb = null;
	private static ContactModel contact = null;
	private WebClient resourceWC = null;
	private WebClient addressbookWC = null;

	@Before
	public void initializeTests() {
		resourceWC = initializeTest(ServiceUtil.RESOURCES_API_URL, ResourcesService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		adb = AddressbookTest.createAddressbook(addressbookWC, this.getClass().getName(), Status.OK);
		contact = ContactTest.createContact(addressbookWC, adb.getId(), "FNAME", "LNAME");
	}
	
	@After
	public void cleanupTest() {
		AddressbookTest.cleanup(addressbookWC, adb.getId(), this.getClass().getName());
		resourceWC.close();
	}
	
	/********************************** resource attributes tests *********************************/	
	@Test
	public void testResourceModelEmptyConstructor() {
		// new() -> _rm
		ResourceModel _rm = new ResourceModel();
		assertNull("id should not be set by empty constructor", _rm.getId());
		assertNull("name should not be set by empty constructor", _rm.getName());
		assertNull("firstname should not be set by empty constructor", _rm.getFirstName());
		assertNull("lastname should not be set by empty constructor", _rm.getLastName());
		assertNull("contactId should not be set by empty constructor", _rm.getContactId());
	}
	
	@Test
	public void testResourceModelConstructor() {		
		// new("MY_NAME", "MY_FNAME", "MY_LNAME", "MY_ID") -> _rm
		ResourceModel _rm = new ResourceModel("MY_NAME", "MY_FNAME", "MY_LNAME", "MY_ID");
		assertNull("id should not be set by constructor", _rm.getId());
		assertEquals("name should be set by constructor", "MY_NAME", _rm.getName());
		assertEquals("firstname should be set by constructor", "MY_FNAME", _rm.getFirstName());
		assertEquals("lastname should be set by constructor", "MY_LNAME", _rm.getLastName());
		assertEquals("contactId should be set by constructor", "MY_ID", _rm.getContactId());
	}
	
	@Test
	public void testResourceIdAttributeChange() {
		// new() -> _rm -> _rm.setId()
		ResourceModel _rm = new ResourceModel();
		assertNull("id should not be set by constructor", _rm.getId());
		_rm.setId("MY_ID");
		assertEquals("id should have changed:", "MY_ID", _rm.getId());
	}

	@Test
	public void testResourceNameAttributeChange() {
		// new() -> _rm -> _rm.setName()
		ResourceModel _rm = new ResourceModel();
		assertNull("name should not be set by empty constructor", _rm.getName());
		_rm.setName("MY_NAME");
		assertEquals("name should have changed:", "MY_NAME", _rm.getName());
	}
	
	@Test
	public void testResourceFirstNameAttributeChange() {
		// new() -> _rm -> _rm.setFirstName()
		ResourceModel _rm = new ResourceModel();
		assertNull("firstname should not be set by empty constructor", _rm.getFirstName());
		_rm.setFirstName("MY_FNAME");
		assertEquals("firstname should have changed:", "MY_FNAME", _rm.getFirstName());
	}
	
	@Test
	public void testResourceLastNameAttributeChange() {
		// new() -> _rm -> _rm.setLastName()
		ResourceModel _rm = new ResourceModel();
		assertNull("lastname should not be set by empty constructor", _rm.getLastName());
		_rm.setLastName("MY_LNAME");
		assertEquals("lastname should have changed:", "MY_LNAME", _rm.getLastName());
	}
	
	@Test
	public void testResourceContactIdAttributeChange() {
		// new() -> _rm -> _rm.setContactId()
		ResourceModel _rm = new ResourceModel();
		assertNull("contactId should not be set by empty constructor", _rm.getContactId());
		_rm.setContactId("MY_ID");
		assertEquals("contactId should have changed:", "MY_ID", _rm.getContactId());
	}
	
	@Test
	public void testResourceCreatedBy() {
		// new() -> _rm -> _rm.setCreatedBy()
		ResourceModel _rm = new ResourceModel();
		assertNull("createdBy should not be set by empty constructor", _rm.getCreatedBy());
		_rm.setCreatedBy("MY_NAME");
		assertEquals("createdBy should have changed", "MY_NAME", _rm.getCreatedBy());	
	}
	
	@Test
	public void testResourceCreatedAt() {
		// new() -> _rm -> _rm.setCreatedAt()
		ResourceModel _rm = new ResourceModel();
		assertNull("createdAt should not be set by empty constructor", _rm.getCreatedAt());
		_rm.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _rm.getCreatedAt());
	}
		
	@Test
	public void testResourceModifiedBy() {
		// new() -> _rm -> _rm.setModifiedBy()
		ResourceModel _rm = new ResourceModel();
		assertNull("modifiedBy should not be set by empty constructor", _rm.getModifiedBy());
		_rm.setModifiedBy("MY_NAME");
		assertEquals("modifiedBy should have changed", "MY_NAME", _rm.getModifiedBy());	
	}
	
	@Test
	public void testResourceModifiedAt() {
		// new() -> _rm -> _rm.setModifiedAt()
		ResourceModel _rm = new ResourceModel();
		assertNull("modifiedAt should not be set by empty constructor", _rm.getModifiedAt());
		_rm.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _rm.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testResourceCreateReadDeleteWithEmptyConstructor() {
		// new() -> _rm1
		ResourceModel _rm1 = new ResourceModel();
		assertNull("id should not be set by empty constructor", _rm1.getId());
		assertNull("name should not be set by empty constructor", _rm1.getName());
		assertNull("firstname should not be set by empty constructor", _rm1.getFirstName());
		assertNull("lastname should not be set by empty constructor", _rm1.getLastName());
		assertNull("contactId should not be set by empty constructor", _rm1.getContactId());
		
		// create(_rm1) -> BAD_REQUEST (because of empty name)
		Response _response = resourceWC.replacePath("/").post(_rm1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_rm1.setName("testResourceCreateReadDeleteWithEmptyConstructor");

		// create(_rm1) -> BAD_REQUEST (because of empty firstName)
		_response = resourceWC.replacePath("/").post(_rm1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_rm1.setFirstName(contact.getFirstName());

		// create(_rm1) -> BAD_REQUEST (because of empty lastName)
		_response = resourceWC.replacePath("/").post(_rm1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_rm1.setLastName(contact.getLastName());

		// create(_rm1) -> BAD_REQUEST (because of empty contactId)
		_response = resourceWC.replacePath("/").post(_rm1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_rm1.setContactId(contact.getId());

		// create(_rm1) -> _rm2
		_response = resourceWC.replacePath("/").post(_rm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _rm2 = _response.readEntity(ResourceModel.class);
		
		// validating _rm1 (local object)
		assertNull("create() should not change the id of the local object", _rm1.getId());
		assertEquals("create() should not change the name of the local object", "testResourceCreateReadDeleteWithEmptyConstructor", _rm1.getName());
		assertEquals("create() should not change the firstname of the local object", contact.getFirstName(), _rm1.getFirstName());
		assertEquals("create() should not change the lastname of the local object", contact.getLastName(), _rm1.getLastName());
		assertEquals("create() should not change the contactId of the local object", contact.getId(), _rm1.getContactId());
		
		// validating _rm2 (returned remote object)
		assertNotNull("create() should set a valid id on the remote object returned", _rm2.getId());
		assertEquals("create() should not change the name of the remote object", "testResourceCreateReadDeleteWithEmptyConstructor", _rm2.getName());
		assertEquals("create() should not change the firstname of the remote object", contact.getFirstName(), _rm2.getFirstName());
		assertEquals("create() should not change the lastname of the remote object", contact.getLastName(), _rm2.getLastName());
		assertEquals("create() should not change the contactId of the remote object", contact.getId(), _rm2.getContactId());

		// read(_rm2) -> _rm3
		_response = resourceWC.replacePath("/").path(_rm2.getId()).get();
		assertEquals("read(" + _rm2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _rm3 = _response.readEntity(ResourceModel.class);
		assertEquals("read() should not change the id", _rm2.getId(), _rm3.getId());
		assertEquals("read() should not change the name", _rm2.getName(), _rm3.getName());
		assertEquals("read() should not change the firstName", _rm2.getFirstName(), _rm3.getFirstName());
		assertEquals("read() should not change the lastName", _rm2.getLastName(), _rm3.getLastName());
		assertEquals("read() should not change the contactId", _rm2.getContactId(), _rm3.getContactId());
		
		// delete(_rm3)
		_response = resourceWC.replacePath("/").path(_rm3.getId()).delete();
		assertEquals("delete(" + _rm3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testResourceCreateReadDelete() {
		// new("testResourceCreateReadDelete", "MY_FNAME", "MY_LNAME", "MY_ID")  -> _rm1
		ResourceModel _rm1 = new ResourceModel("testResourceCreateReadDelete", contact.getFirstName(), contact.getLastName(), contact.getId());
		assertNull("id should not be set by constructor", _rm1.getId());
		assertEquals("name should be set by constructor", "testResourceCreateReadDelete", _rm1.getName());
		assertEquals("firstname should be set by constructor", contact.getFirstName(), _rm1.getFirstName());
		assertEquals("lastname should be set by constructor", contact.getLastName(), _rm1.getLastName());
		assertEquals("contactId should be set by constructor", contact.getId(), _rm1.getContactId());
		// create(_rm1) -> _rm2
		Response _response = resourceWC.replacePath("/").post(_rm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _rm2 = _response.readEntity(ResourceModel.class);
		assertNull("id should be still null after remote create", _rm1.getId());
		assertEquals("name should be unchanged after remote create", "testResourceCreateReadDelete", _rm1.getName());
		assertEquals("firstname should be unchanged after remote create", contact.getFirstName(), _rm1.getFirstName());
		assertEquals("lastname should be unchanged after remote create", contact.getLastName(), _rm1.getLastName());
		assertEquals("contactId should be unchanged after remote create", contact.getId(), _rm1.getContactId());
		
		assertNotNull("id of returned object should be set", _rm2.getId());
		assertEquals("name of returned object should be unchanged after remote create", "testResourceCreateReadDelete", _rm2.getName());
		assertEquals("firstname of returned object should be unchanged after remote create", contact.getFirstName(), _rm2.getFirstName());
		assertEquals("lastname of returned object should be unchanged after remote create", contact.getLastName(), _rm2.getLastName());
		assertEquals("contactId of returned object should be unchanged after remote create", contact.getId(), _rm2.getContactId());
		// read(_rm2)  -> _rm3
		_response = resourceWC.replacePath("/").path(_rm2.getId()).get();
		assertEquals("read(" + _rm2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _rm3 = _response.readEntity(ResourceModel.class);
		assertEquals("id of returned object should be the same", _rm2.getId(), _rm3.getId());
		assertEquals("name of returned object should be unchanged after remote create", _rm2.getName(), _rm3.getName());
		assertEquals("firstname of returned object should be unchanged after remote create", _rm2.getFirstName(), _rm3.getFirstName());
		assertEquals("lastname of returned object should be unchanged after remote create", _rm2.getLastName(), _rm3.getLastName());
		assertEquals("contactId of returned object should be unchanged after remote create", _rm2.getContactId(), _rm3.getContactId());
		// delete(_rm3)
		_response = resourceWC.replacePath("/").path(_rm3.getId()).delete();
		assertEquals("delete(" + _rm3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateResourceWithClientSideId() {
		// new() -> _rm1 -> _rm1.setId("LOCAL_ID") -> create(_rm1) -> BAD_REQUEST
		ResourceModel _rm1 = createResourceModel("testCreateResourceWithClientSideId", 1);
		_rm1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _rm1.getId());
		// create(_c1) -> BAD_REQUEST
		Response _response = resourceWC.replacePath("/").post(_rm1);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateResourceWithDuplicateId() {
		// create(new()) -> _rm1
		Response _response = resourceWC.replacePath("/").post(createResourceModel("testCreateResourceWithDuplicateId", 1));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _rm1 = _response.readEntity(ResourceModel.class);

		// new() -> _rm2 -> _rm2.setId(_rm1.getId())
		ResourceModel _rm2 = createResourceModel("testCreateResourceWithDuplicateId", 2);
		_rm2.setId(_rm1.getId());		// wrongly create a 2nd ResourceModel object with the same ID
		
		// create(_c3) -> CONFLICT
		_response = resourceWC.replacePath("/").post(_rm2);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_rm1)
		_response = resourceWC.replacePath("/").path(_rm1.getId()).delete();
		assertEquals("delete(" + _rm1.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

	}
	
	@Test
	public void testResourceList(
	) {		
		ArrayList<ResourceModel> _localList = new ArrayList<ResourceModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			// create(new()) -> _localList
			_response = resourceWC.replacePath("/").post(createResourceModel("testResourceList", i));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(ResourceModel.class));
		}
		
		// list(/) -> _remoteList
		_response = resourceWC.replacePath("/").query("size", Integer.toString(Integer.MAX_VALUE)).get();
		List<ResourceModel> _remoteList = new ArrayList<ResourceModel>(resourceWC.getCollection(ResourceModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (ResourceModel _rm : _remoteList) {
			_remoteListIds.add(_rm.getId());
		}
		
		for (ResourceModel _rm : _localList) {
			assertTrue("resource <" + _rm.getId() + "> should be listed", _remoteListIds.contains(_rm.getId()));
		}
		for (ResourceModel _rm : _localList) {
			_response = resourceWC.replacePath("/").path(_rm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(ResourceModel.class);
		}
		for (ResourceModel _rm : _localList) {
			_response = resourceWC.replacePath("/").path(_rm.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}


	
	@Test
	public void testResourceCreate() {
		// new("MY_TITLE", "MY_DESC") -> _rm1
		ResourceModel _rm1 = createResourceModel("testResourceCreate", 1);
		// new("MY_TITLE2", "MY_DESC2") -> _rm2
		ResourceModel _rm2 = createResourceModel("testResourceCreate", 2);
		
		// create(_rm1)  -> _rm3
		Response _response = resourceWC.replacePath("/").post(_rm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _rm3 = _response.readEntity(ResourceModel.class);

		// create(_rm2) -> _rm4
		_response = resourceWC.replacePath("/").post(_rm2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _rm4 = _response.readEntity(ResourceModel.class);		
		
		// validate _rm3
		assertNotNull("ID should be set", _rm3.getId());
		assertEquals("name1 should be set correctly", "testResourceCreate1", _rm3.getName());
		assertEquals("firstname1 should be set correctly", "MY_FNAME1", _rm3.getFirstName());
		assertEquals("lastname1 should be set correctly", "MY_LNAME1", _rm3.getLastName());
		assertEquals("contactId1 should be set correctly", _rm1.getContactId(), _rm3.getContactId());
		
		// validate _rm4
		assertNotNull("ID should be set", _rm4.getId());
		assertEquals("name2 should be set correctly", "testResourceCreate2", _rm4.getName());
		assertEquals("firstname2 should be set correctly", "MY_FNAME2", _rm4.getFirstName());
		assertEquals("lastname2 should be set correctly", "MY_LNAME2", _rm4.getLastName());
		assertEquals("contactId2 should be set correctly", _rm2.getContactId(), _rm4.getContactId());

		assertThat(_rm4.getId(), not(equalTo(_rm3.getId())));

		// delete(_rm3) -> NO_CONTENT
		_response = resourceWC.replacePath("/").path(_rm3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_rm4) -> NO_CONTENT
		_response = resourceWC.replacePath("/").path(_rm4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testResourceDoubleCreate(
	) {
		// create(new()) -> _rm
		Response _response = resourceWC.replacePath("/").post(createResourceModel("testResourceDoubleCreate", 1));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _rm = _response.readEntity(ResourceModel.class);
		assertNotNull("ID should be set", _rm.getId());		
		
		// create(_rm) -> CONFLICT
		_response = resourceWC.replacePath("/").post(_rm);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_rm) -> NO_CONTENT
		_response = resourceWC.replacePath("/").path(_rm.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testResourceRead(
	) {
		ArrayList<ResourceModel> _localList = new ArrayList<ResourceModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			_response = resourceWC.replacePath("/").post(createResourceModel("testResourceRead", i));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(ResourceModel.class));
		}
	
		// test read on each local element
		for (ResourceModel _rm : _localList) {
			_response = resourceWC.replacePath("/").path(_rm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(ResourceModel.class);
		}

		// test read on each listed element
		_response = resourceWC.replacePath("/").get();
		List<ResourceModel> _remoteList = new ArrayList<ResourceModel>(resourceWC.getCollection(ResourceModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ResourceModel _tmpObj = null;
		for (ResourceModel _rm : _remoteList) {
			_response = resourceWC.replacePath("/").path(_rm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(ResourceModel.class);
			assertEquals("ID should be unchanged when reading a resource", _rm.getId(), _tmpObj.getId());						
		}

		for (ResourceModel _rm : _localList) {
			_response = resourceWC.replacePath("/").path(_rm.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}	

	@Test
	public void testResourceMultiRead(
	) {
		// new() -> _rm1
		ResourceModel _rm1 = createResourceModel("testResourceMultiRead", 1);
		
		// create(_rm1) -> _rm2
		Response _response = resourceWC.replacePath("/").post(_rm1);
		ResourceModel _rm2 = _response.readEntity(ResourceModel.class);

		// read(_rm2) -> _rm3
		_response = resourceWC.replacePath("/").path(_rm2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _rm3 = _response.readEntity(ResourceModel.class);
		assertEquals("ID should be unchanged after read", _rm2.getId(), _rm3.getId());		

		// read(_rm2) -> _rm4
		_response = resourceWC.replacePath("/").path(_rm2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _rm4 = _response.readEntity(ResourceModel.class);
		
		// the two objects should have same attributes, but not be equal
		assertEquals("ID should be the same", _rm3.getId(), _rm4.getId());
		assertEquals("name should be the same", _rm3.getName(), _rm4.getName());
		assertEquals("firstname should be the same", _rm3.getFirstName(), _rm4.getFirstName());
		assertEquals("lastname should be the same", _rm3.getLastName(), _rm4.getLastName());
		assertEquals("contactId should be the same", _rm3.getContactId(), _rm4.getContactId());
		
		assertEquals("ID should be the same", _rm3.getId(), _rm2.getId());
		assertEquals("name should be the same", _rm3.getName(), _rm2.getName());
		assertEquals("firstname should be the same", _rm3.getFirstName(), _rm2.getFirstName());
		assertEquals("lastname should be the same", _rm3.getLastName(), _rm2.getLastName());
		assertEquals("contactId should be the same", _rm3.getContactId(), _rm2.getContactId());
		
		// delete(_rm2)
		_response = resourceWC.replacePath("/").path(_rm2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testResourceUpdate(
	) {
		// new() -> _rm1
		ResourceModel _rm1 = createResourceModel("testResourceUpdate", 1);
		
		// create(_rm1) -> _rm2
		Response _response = resourceWC.replacePath("/").post(_rm1);
		ResourceModel _rm2 = _response.readEntity(ResourceModel.class);
		
		// change the attributes
		// update(_rm2) -> _rm3
		_rm2.setName("MY_NAME1");
		_rm2.setFirstName("MY_FNAME1");
		_rm2.setLastName("MY_LNAME1");
		ContactModel _cm1 = ContactTest.createContact(addressbookWC, adb.getId(), "MY_FNAME1", "MY_LNAME1");
		_rm2.setContactId(_cm1.getId());
		resourceWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = resourceWC.replacePath("/").path(_rm2.getId()).put(_rm2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _rm3 = _response.readEntity(ResourceModel.class);

		assertNotNull("ID should be set", _rm3.getId());
		assertEquals("ID should be unchanged", _rm2.getId(), _rm3.getId());	
		assertEquals("name should have changed", "MY_NAME1", _rm3.getName());
		assertEquals("firstname should have changed", "MY_FNAME1", _rm3.getFirstName());
		assertEquals("lastname should have changed", "MY_LNAME1", _rm3.getLastName());
		assertEquals("contactId should have changed", _cm1.getId(), _rm3.getContactId());

		// reset the attributes
		// update(_rm2) -> _rm4
		_rm2.setName("MY_NAME2");
		_rm2.setFirstName("MY_FNAME2");
		_rm2.setLastName("MY_LNAME2");
		ContactModel _cm2 = ContactTest.createContact(addressbookWC, adb.getId(), "MY_FNAME2", "MY_LNAME2");
		_rm2.setContactId(_cm2.getId());
		resourceWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = resourceWC.replacePath("/").path(_rm2.getId()).put(_rm2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _rm4 = _response.readEntity(ResourceModel.class);

		assertNotNull("ID should be set", _rm4.getId());
		assertEquals("ID should not change", _rm2.getId(), _rm4.getId());	
		assertEquals("name should have changed", "MY_NAME2", _rm4.getName());
		assertEquals("firstname should have changed", "MY_FNAME2", _rm4.getFirstName());
		assertEquals("lastname should have changed", "MY_LNAME2", _rm4.getLastName());
		assertEquals("contactId should have changed", _cm2.getId(), _rm4.getContactId());
		
		// delete(_rm2)
		_response = resourceWC.replacePath("/").path(_rm2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testResourceDelete(
	) {
		// new() -> _rm1
		ResourceModel _rm1 = createResourceModel("testResourceDelete", 1);
		// create(_rm1) -> _rm2
		Response _response = resourceWC.replacePath("/").post(_rm1);
		ResourceModel _rm2 = _response.readEntity(ResourceModel.class);
		
		// read(_rm1) -> _tmpObj
		_response = resourceWC.replacePath("/").path(_rm2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _tmpObj = _response.readEntity(ResourceModel.class);
		assertEquals("ID should be unchanged when reading a resource (remote):", _rm2.getId(), _tmpObj.getId());						
		
		// delete(_rm2) -> OK
		_response = resourceWC.replacePath("/").path(_rm2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	
		// read the deleted object twice
		// read(_rm2) -> NOT_FOUND
		_response = resourceWC.replacePath("/").path(_rm2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read(_rm2) -> NOT_FOUND
		_response = resourceWC.replacePath("/").path(_rm2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testResourceDoubleDelete(
	) {
		// new() -> _rm1
		ResourceModel _rm1 = createResourceModel("testResourceDoubleDelete", 1);
		
		// create(_rm1) -> _rm2
		Response _response = resourceWC.replacePath("/").post(_rm1);
		ResourceModel _rm2 = _response.readEntity(ResourceModel.class);

		// read(_rm2) -> OK
		_response = resourceWC.replacePath("/").path(_rm2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		// delete(_rm2) -> OK
		_response = resourceWC.replacePath("/").path(_rm2.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		// read(_rm2) -> NOT_FOUND
		_response = resourceWC.replacePath("/").path(_rm2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// delete _rm2 -> NOT_FOUND
		_response = resourceWC.replacePath("/").path(_rm2.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read _rm2 -> NOT_FOUND
		_response = resourceWC.replacePath("/").path(_rm2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testResourceModifications() {
		// create(new ResourceModel()) -> _rm1
		Response _response = resourceWC.replacePath("/").post(createResourceModel("testResourceModifications", 1));
		ResourceModel _rm1 = _response.readEntity(ResourceModel.class);
		
		// test createdAt and createdBy
		assertNotNull("create() should set createdAt", _rm1.getCreatedAt());
		assertNotNull("create() should set createdBy", _rm1.getCreatedBy());
		// test modifiedAt and modifiedBy (= same as createdAt/createdBy)
		assertNotNull("create() should set modifiedAt", _rm1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _rm1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _rm1.getCreatedAt(), _rm1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _rm1.getCreatedBy(), _rm1.getModifiedBy());
		
		// update(_rm1)  -> _rm2
		_rm1.setName("MY_NAME2");
		resourceWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = resourceWC.replacePath("/").path(_rm1.getId()).put(_rm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _rm2 = _response.readEntity(ResourceModel.class);

		// test createdAt and createdBy (unchanged)
		assertEquals("update() should not change createdAt", _rm1.getCreatedAt(), _rm2.getCreatedAt());
		assertEquals("update() should not change createdBy", _rm1.getCreatedBy(), _rm2.getCreatedBy());
		
		// test modifiedAt and modifiedBy (= different from createdAt/createdBy)
		assertThat(_rm2.getModifiedAt(), not(equalTo(_rm2.getCreatedAt())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_rm2.getModifiedBy(), not(equalTo(_rm2.getCreatedBy())));

		// update(_rm1) with modifiedBy/At set on client side -> ignored by server
		_rm1.setModifiedBy("MYSELF");
		_rm1.setModifiedAt(new Date(1000));
		resourceWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = resourceWC.replacePath("/").path(_rm1.getId()).put(_rm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _rm3 = _response.readEntity(ResourceModel.class);
		
		// test, that modifiedBy really ignored the client-side value "MYSELF"
		assertThat(_rm1.getModifiedBy(), not(equalTo(_rm3.getModifiedBy())));
		// check whether the client-side modifiedAt() is ignored
		assertThat(_rm1.getModifiedAt(), not(equalTo(_rm3.getModifiedAt())));
		
		// delete(_rm1) -> NO_CONTENT
		_response = resourceWC.replacePath("/").path(_rm1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	/********************************* helper methods *********************************/	
	public ResourceModel createResourceModel(
			String name, 
			int suffix) {
		ContactModel _cm = ContactTest.createContact(addressbookWC, adb.getId(), "MY_FNAME" + suffix, "MY_LNAME" + suffix);
		return new ResourceModel(name + suffix, _cm.getFirstName(), _cm.getLastName(), _cm.getId());
	}	
	
	/**
	 * Create a new ResourceModel on the server by executing a HTTP POST request.
	 * @param model the ResourceModel to post to the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created ResourceModel
	 */
	public ResourceModel postResource(
			ResourceModel model, 
			Status expectedStatus) {
		return postResource(resourceWC, model, expectedStatus);
	}
	
	/**
	 * Create a new ResourceModel on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the ResourceService
	 * @param model the ResourceModel data to create on the server
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the created ResourceModel
	 */
	public static ResourceModel postResource(
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
	public static ResourceModel createResource(
			WebClient resourceWC, 
			AddressbookModel addressbookModel,
			ContactModel contactModel,
			String resourceName,
			Status expectedStatus) {
		ResourceModel _resourceModel = new ResourceModel();
		_resourceModel.setName(resourceName);
		_resourceModel.setContactId(contactModel.getId());
		_resourceModel.setFirstName(contactModel.getFirstName());
		_resourceModel.setLastName(contactModel.getLastName());
		return postResource(resourceWC, _resourceModel, expectedStatus);
	}
	
	public static void cleanup(
			WebClient resourceWC,
			String resourceId,
			String testName) {
		cleanup(resourceWC, resourceId, testName, true);
	}

	public static void cleanup(
			WebClient resourceWC,
			String resourceId,
			String testName,
			boolean closeWC) {
		resourceWC.replacePath("/").path(resourceId).delete();		
		System.out.println(testName + " deleted resource <" + resourceId + ">.");
		if (closeWC) {
			resourceWC.close();
		}
	}	
}
