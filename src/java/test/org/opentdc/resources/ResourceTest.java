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

public class ResourceTest extends AbstractTestClient {
	private static AddressbookModel adb = null;
	private static ContactModel contact = null;
	private WebClient wc = null;
	private WebClient addressbookWC = null;

	@Before
	public void initializeTests() {
		wc = initializeTest(ServiceUtil.RESOURCES_API_URL, ResourcesService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		adb = AddressbookTest.createAddressbook(addressbookWC, this.getClass().getName(), Status.OK);
		contact = ContactTest.create(addressbookWC, adb.getId(), "FNAME", "LNAME");
	}
	
	@After
	public void cleanupTest() {
		AddressbookTest.delete(addressbookWC, adb.getId(), Status.NO_CONTENT);
		System.out.println("deleted 1 addressbook");
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
		ResourceModel _model = new ResourceModel("MY_NAME", "MY_FNAME", "MY_LNAME", "MY_ID");
		assertNull("id should not be set by constructor", _model.getId());
		assertEquals("name should be set by constructor", "MY_NAME", _model.getName());
		assertEquals("firstname should be set by constructor", "MY_FNAME", _model.getFirstName());
		assertEquals("lastname should be set by constructor", "MY_LNAME", _model.getLastName());
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
		
		Response _response = wc.replacePath("/").post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setName("testCreateReadDeleteWithEmptyConstructor");

		_response = wc.replacePath("/").post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setFirstName(contact.getFirstName());

		_response = wc.replacePath("/").post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setLastName(contact.getLastName());

		_response = wc.replacePath("/").post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setContactId(contact.getId());

		_response = wc.replacePath("/").post(_model1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _model2 = _response.readEntity(ResourceModel.class);
		
		assertNull("create() should not change the id of the local object", _model1.getId());
		assertEquals("create() should not change the name of the local object", "testCreateReadDeleteWithEmptyConstructor", _model1.getName());
		assertEquals("create() should not change the firstname of the local object", contact.getFirstName(), _model1.getFirstName());
		assertEquals("create() should not change the lastname of the local object", contact.getLastName(), _model1.getLastName());
		assertEquals("create() should not change the contactId of the local object", contact.getId(), _model1.getContactId());
		
		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
		assertEquals("create() should not change the name of the remote object", "testCreateReadDeleteWithEmptyConstructor", _model2.getName());
		assertEquals("create() should not change the firstname of the remote object", contact.getFirstName(), _model2.getFirstName());
		assertEquals("create() should not change the lastname of the remote object", contact.getLastName(), _model2.getLastName());
		assertEquals("create() should not change the contactId of the remote object", contact.getId(), _model2.getContactId());

		_response = wc.replacePath("/").path(_model2.getId()).get();
		assertEquals("read(" + _model2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _model3 = _response.readEntity(ResourceModel.class);
		assertEquals("read() should not change the id", _model2.getId(), _model3.getId());
		assertEquals("read() should not change the name", _model2.getName(), _model3.getName());
		assertEquals("read() should not change the firstName", _model2.getFirstName(), _model3.getFirstName());
		assertEquals("read() should not change the lastName", _model2.getLastName(), _model3.getLastName());
		assertEquals("read() should not change the contactId", _model2.getContactId(), _model3.getContactId());
		
		_response = wc.replacePath("/").path(_model3.getId()).delete();
		assertEquals("delete(" + _model3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateReadDelete() {
		ResourceModel _model1 = new ResourceModel("testCreateReadDelete", contact.getFirstName(), contact.getLastName(), contact.getId());
		assertNull("id should not be set by constructor", _model1.getId());
		assertEquals("name should be set by constructor", "testCreateReadDelete", _model1.getName());
		assertEquals("firstname should be set by constructor", contact.getFirstName(), _model1.getFirstName());
		assertEquals("lastname should be set by constructor", contact.getLastName(), _model1.getLastName());
		assertEquals("contactId should be set by constructor", contact.getId(), _model1.getContactId());

		Response _response = wc.replacePath("/").post(_model1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _model2 = _response.readEntity(ResourceModel.class);
		assertNull("id should be still null after remote create", _model1.getId());
		assertEquals("name should be unchanged after remote create", "testCreateReadDelete", _model1.getName());
		assertEquals("firstname should be unchanged after remote create", contact.getFirstName(), _model1.getFirstName());
		assertEquals("lastname should be unchanged after remote create", contact.getLastName(), _model1.getLastName());
		assertEquals("contactId should be unchanged after remote create", contact.getId(), _model1.getContactId());
		
		assertNotNull("id of returned object should be set", _model2.getId());
		assertEquals("name of returned object should be unchanged after remote create", "testCreateReadDelete", _model2.getName());
		assertEquals("firstname of returned object should be unchanged after remote create", contact.getFirstName(), _model2.getFirstName());
		assertEquals("lastname of returned object should be unchanged after remote create", contact.getLastName(), _model2.getLastName());
		assertEquals("contactId of returned object should be unchanged after remote create", contact.getId(), _model2.getContactId());

		_response = wc.replacePath("/").path(_model2.getId()).get();
		assertEquals("read(" + _model2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _model3 = _response.readEntity(ResourceModel.class);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("name of returned object should be unchanged after remote create", _model2.getName(), _model3.getName());
		assertEquals("firstname of returned object should be unchanged after remote create", _model2.getFirstName(), _model3.getFirstName());
		assertEquals("lastname of returned object should be unchanged after remote create", _model2.getLastName(), _model3.getLastName());
		assertEquals("contactId of returned object should be unchanged after remote create", _model2.getContactId(), _model3.getContactId());

		_response = wc.replacePath("/").path(_model3.getId()).delete();
		assertEquals("delete(" + _model3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testClientSideId() {
		ResourceModel _model = create("testClientSideId", 1);
		_model.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _model.getId());
		Response _response = wc.replacePath("/").post(_model);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testDuplicateId() {
		Response _response = wc.replacePath("/").post(create("testDuplicateId", 1));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _model1 = _response.readEntity(ResourceModel.class);

		ResourceModel _model2 = create("testDuplicateId", 2);
		_model2.setId(_model1.getId());		// wrongly create a 2nd ResourceModel object with the same ID
		
		_response = wc.replacePath("/").post(_model2);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		_response = wc.replacePath("/").path(_model1.getId()).delete();
		assertEquals("delete(" + _model1.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

	}
	
	@Test
	public void testList() {		
		ArrayList<ResourceModel> _localList = new ArrayList<ResourceModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			_response = wc.replacePath("/").post(create("testList", i));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(ResourceModel.class));
		}
		
		_response = wc.replacePath("/").query("size", Integer.toString(Integer.MAX_VALUE)).get();
		List<ResourceModel> _remoteList = new ArrayList<ResourceModel>(wc.getCollection(ResourceModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (ResourceModel _model : _remoteList) {
			_remoteListIds.add(_model.getId());
		}
		
		for (ResourceModel _model : _localList) {
			assertTrue("resource <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
		}
		for (ResourceModel _model : _localList) {
			_response = wc.replacePath("/").path(_model.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(ResourceModel.class);
		}
		for (ResourceModel _model : _localList) {
			_response = wc.replacePath("/").path(_model.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}


	
	@Test
	public void testCreate() {
		ResourceModel _model1 = create("testCreate", 1);
		ResourceModel _model2 = create("testCreate", 2);
		
		Response _response = wc.replacePath("/").post(_model1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _model3 = _response.readEntity(ResourceModel.class);

		_response = wc.replacePath("/").post(_model2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _model4 = _response.readEntity(ResourceModel.class);		
		
		assertNotNull("ID should be set", _model3.getId());
		assertEquals("name1 should be set correctly", "testCreate1", _model3.getName());
		assertEquals("firstname1 should be set correctly", "MY_FNAME1", _model3.getFirstName());
		assertEquals("lastname1 should be set correctly", "MY_LNAME1", _model3.getLastName());
		assertEquals("contactId1 should be set correctly", _model1.getContactId(), _model3.getContactId());
		
		assertNotNull("ID should be set", _model4.getId());
		assertEquals("name2 should be set correctly", "testCreate2", _model4.getName());
		assertEquals("firstname2 should be set correctly", "MY_FNAME2", _model4.getFirstName());
		assertEquals("lastname2 should be set correctly", "MY_LNAME2", _model4.getLastName());
		assertEquals("contactId2 should be set correctly", _model2.getContactId(), _model4.getContactId());

		assertThat(_model4.getId(), not(equalTo(_model3.getId())));

		_response = wc.replacePath("/").path(_model3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		_response = wc.replacePath("/").path(_model4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testDoubleCreate() {
		Response _response = wc.replacePath("/").post(create("testDoubleCreate", 1));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _model = _response.readEntity(ResourceModel.class);
		assertNotNull("ID should be set", _model.getId());		
		
		_response = wc.replacePath("/").post(_model);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		_response = wc.replacePath("/").path(_model.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testRead() {
		ArrayList<ResourceModel> _localList = new ArrayList<ResourceModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			_response = wc.replacePath("/").post(create("testRead", i));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(ResourceModel.class));
		}
	
		for (ResourceModel _model : _localList) {
			_response = wc.replacePath("/").path(_model.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(ResourceModel.class);
		}

		_response = wc.replacePath("/").get();
		List<ResourceModel> _remoteList = new ArrayList<ResourceModel>(wc.getCollection(ResourceModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ResourceModel _model1 = null;
		for (ResourceModel _model : _remoteList) {
			_response = wc.replacePath("/").path(_model.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_model1 = _response.readEntity(ResourceModel.class);
			assertEquals("ID should be unchanged when reading a resource", _model.getId(), _model1.getId());						
		}

		for (ResourceModel _model : _localList) {
			_response = wc.replacePath("/").path(_model.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}	

	@Test
	public void testMultiRead() {
		ResourceModel _model1 = create("testMultiRead", 1);
		
		Response _response = wc.replacePath("/").post(_model1);
		ResourceModel _model2 = _response.readEntity(ResourceModel.class);

		_response = wc.replacePath("/").path(_model2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _model3 = _response.readEntity(ResourceModel.class);
		assertEquals("ID should be unchanged after read", _model2.getId(), _model3.getId());		

		_response = wc.replacePath("/").path(_model2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _model4 = _response.readEntity(ResourceModel.class);
		
		assertEquals("ID should be the same", _model3.getId(), _model4.getId());
		assertEquals("name should be the same", _model3.getName(), _model4.getName());
		assertEquals("firstname should be the same", _model3.getFirstName(), _model4.getFirstName());
		assertEquals("lastname should be the same", _model3.getLastName(), _model4.getLastName());
		assertEquals("contactId should be the same", _model3.getContactId(), _model4.getContactId());
		
		assertEquals("ID should be the same", _model3.getId(), _model2.getId());
		assertEquals("name should be the same", _model3.getName(), _model2.getName());
		assertEquals("firstname should be the same", _model3.getFirstName(), _model2.getFirstName());
		assertEquals("lastname should be the same", _model3.getLastName(), _model2.getLastName());
		assertEquals("contactId should be the same", _model3.getContactId(), _model2.getContactId());
		
		_response = wc.replacePath("/").path(_model2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testUpdate() {
		ResourceModel _model1 = create("testUpdate", 1);
		
		Response _response = wc.replacePath("/").post(_model1);
		ResourceModel _model2 = _response.readEntity(ResourceModel.class);
		
		_model2.setName("MY_NAME1");
		_model2.setFirstName("MY_FNAME1");
		_model2.setLastName("MY_LNAME1");
		ContactModel _cm1 = ContactTest.create(addressbookWC, adb.getId(), "MY_FNAME1", "MY_LNAME1");
		_model2.setContactId(_cm1.getId());
		wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = wc.replacePath("/").path(_model2.getId()).put(_model2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _rm3 = _response.readEntity(ResourceModel.class);

		assertNotNull("ID should be set", _rm3.getId());
		assertEquals("ID should be unchanged", _model2.getId(), _rm3.getId());	
		assertEquals("name should have changed", "MY_NAME1", _rm3.getName());
		assertEquals("firstname should have changed", "MY_FNAME1", _rm3.getFirstName());
		assertEquals("lastname should have changed", "MY_LNAME1", _rm3.getLastName());
		assertEquals("contactId should have changed", _cm1.getId(), _rm3.getContactId());

		_model2.setName("MY_NAME2");
		_model2.setFirstName("MY_FNAME2");
		_model2.setLastName("MY_LNAME2");
		ContactModel _cm2 = ContactTest.create(addressbookWC, adb.getId(), "MY_FNAME2", "MY_LNAME2");
		_model2.setContactId(_cm2.getId());
		wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = wc.replacePath("/").path(_model2.getId()).put(_model2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _model3 = _response.readEntity(ResourceModel.class);

		assertNotNull("ID should be set", _model3.getId());
		assertEquals("ID should not change", _model2.getId(), _model3.getId());	
		assertEquals("name should have changed", "MY_NAME2", _model3.getName());
		assertEquals("firstname should have changed", "MY_FNAME2", _model3.getFirstName());
		assertEquals("lastname should have changed", "MY_LNAME2", _model3.getLastName());
		assertEquals("contactId should have changed", _cm2.getId(), _model3.getContactId());
		
		_response = wc.replacePath("/").path(_model2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testDelete() {
		ResourceModel _model1 = create("testDelete", 1);

		Response _response = wc.replacePath("/").post(_model1);
		ResourceModel _model2 = _response.readEntity(ResourceModel.class);
		
		_response = wc.replacePath("/").path(_model2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _model3 = _response.readEntity(ResourceModel.class);
		assertEquals("ID should be unchanged when reading a resource (remote):", _model2.getId(), _model3.getId());						
		
		_response = wc.replacePath("/").path(_model2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	
		_response = wc.replacePath("/").path(_model2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		_response = wc.replacePath("/").path(_model2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testDoubleDelete() {
		ResourceModel _model1 = create("testDoubleDelete", 1);
		
		Response _response = wc.replacePath("/").post(_model1);
		ResourceModel _model2 = _response.readEntity(ResourceModel.class);

		_response = wc.replacePath("/").path(_model2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		_response = wc.replacePath("/").path(_model2.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		_response = wc.replacePath("/").path(_model2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		_response = wc.replacePath("/").path(_model2.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		_response = wc.replacePath("/").path(_model2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testModifications() {
		Response _response = wc.replacePath("/").post(create("testModifications", 1));
		ResourceModel _model1 = _response.readEntity(ResourceModel.class);
		
		assertNotNull("create() should set createdAt", _model1.getCreatedAt());
		assertNotNull("create() should set createdBy", _model1.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _model1.getCreatedAt(), _model1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());
		
		_model1.setName("MY_NAME2");
		wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = wc.replacePath("/").path(_model1.getId()).put(_model1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _model2 = _response.readEntity(ResourceModel.class);

		assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
		assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
		
		assertThat(_model2.getModifiedAt(), not(equalTo(_model2.getCreatedAt())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?

		_model1.setModifiedBy("MYSELF");
		_model1.setModifiedAt(new Date(1000));
		wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = wc.replacePath("/").path(_model1.getId()).put(_model1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceModel _model3 = _response.readEntity(ResourceModel.class);
		
		assertThat(_model1.getModifiedBy(), not(equalTo(_model3.getModifiedBy())));
		assertThat(_model1.getModifiedAt(), not(equalTo(_model3.getModifiedAt())));
		
		_response = wc.replacePath("/").path(_model1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	/********************************* helper methods *********************************/	
	public ResourceModel create(
			String name, 
			int suffix) {
		ContactModel _cm = ContactTest.create(addressbookWC, adb.getId(), "MY_FNAME" + suffix, "MY_LNAME" + suffix);
		return new ResourceModel(name + suffix, _cm.getFirstName(), _cm.getLastName(), _cm.getId());
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
	public static ResourceModel create(
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
		return post(resourceWC, _resourceModel, expectedStatus);
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
	
	protected int calculateMembers() {
		return 1;
	}
}
