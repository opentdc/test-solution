/**
a * The MIT License (MIT)
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
package test.org.opentdc.wtt;

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
import org.opentdc.addressbooks.OrgModel;
import org.opentdc.addressbooks.OrgType;
import org.opentdc.service.ServiceUtil;
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.WttService;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.addressbooks.AddressbookTest;
import test.org.opentdc.addressbooks.OrgTest;

public class CompanyTest extends AbstractTestClient {
	private WebClient wttWC = null;
	private static AddressbookModel adb = null;
	private static OrgModel org = null;
	private WebClient addressbookWC = null;
	
	@Before
	public void initializeTests() {
		wttWC = initializeTest(ServiceUtil.WTT_API_URL, WttService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		
		adb = AddressbookTest.createAddressbook(addressbookWC, this.getClass().getName(), Status.OK);
		org = OrgTest.createOrg(addressbookWC, adb.getId(), this.getClass().getName(), OrgType.CLUB);
	}

	@After
	public void cleanupTest() {
		AddressbookTest.delete(addressbookWC, adb.getId(), Status.NO_CONTENT);
		System.out.println("deleted 1 addressbook");
		addressbookWC.close();
		wttWC.close();
	}

	/********************************** company attributes tests *********************************/	
	@Test
	public void testCompanyModelEmptyConstructor() {
		// new() -> _c
		CompanyModel _c = new CompanyModel();
		assertNull("id should not be set by empty constructor", _c.getId());
		assertNull("title should not be set by empty constructor", _c.getTitle());
		assertNull("orgId should not be set by empty constructor", _c.getOrgId());
		assertNull("description should not be set by empty constructor", _c.getDescription());
	}
	
	@Test
	public void testCompanyModelConstructor() {		
		// new("MY_TITLE", "MY_DESC") -> _c
		CompanyModel _c = new CompanyModel("MY_TITLE", "MY_DESC", org.getId());
		assertNull("id should not be set by constructor", _c.getId());
		assertEquals("title should be set by constructor", "MY_TITLE", _c.getTitle());
		assertEquals("description should set by constructor", "MY_DESC", _c.getDescription());
		assertEquals("orgId should be set by constructor", org.getId(), _c.getOrgId());
	}
	
	@Test
	public void testCompanyIdAttributeChange() {
		// new() -> _c -> _c.setId()
		CompanyModel _c = new CompanyModel();
		assertNull("id should not be set by constructor", _c.getId());
		_c.setId("MY_ID");
		assertEquals("id should have changed:", "MY_ID", _c.getId());
	}

	@Test
	public void testCompanyTitleAttributeChange() {
		// new() -> _c -> _c.setTitle()
		CompanyModel _c = new CompanyModel();
		assertNull("title should not be set by empty constructor", _c.getTitle());
		_c.setTitle("MY_TITLE");
		assertEquals("title should have changed:", "MY_TITLE", _c.getTitle());
	}
	
	@Test
	public void testCompanyDescriptionAttributeChange() {
		// new() -> _c -> _c.setDescription()
		CompanyModel _c = new CompanyModel();
		assertNull("description should not be set by empty constructor", _c.getDescription());
		_c.setDescription("MY_DESC");
		assertEquals("description should have changed:", "MY_DESC", _c.getDescription());
	}
	
	@Test
	public void testCompanyCreatedBy() {
		// new() -> _o -> _o.setCreatedBy()
		CompanyModel _o = new CompanyModel();
		assertNull("createdBy should not be set by empty constructor", _o.getCreatedBy());
		_o.setCreatedBy("MY_NAME");
		assertEquals("createdBy should have changed", "MY_NAME", _o.getCreatedBy());	
	}
	
	@Test
	public void testCompanyCreatedAt() {
		// new() -> _o -> _o.setCreatedAt()
		CompanyModel _o = new CompanyModel();
		assertNull("createdAt should not be set by empty constructor", _o.getCreatedAt());
		_o.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _o.getCreatedAt());
	}
		
	@Test
	public void testCompanyModifiedBy() {
		// new() -> _o -> _o.setModifiedBy()
		CompanyModel _o = new CompanyModel();
		assertNull("modifiedBy should not be set by empty constructor", _o.getModifiedBy());
		_o.setModifiedBy("MY_NAME");
		assertEquals("modifiedBy should have changed", "MY_NAME", _o.getModifiedBy());	
	}
	
	@Test
	public void testCompanyOrgId() {
		CompanyModel _cm = new CompanyModel();
		assertNull("orgId should not be set by empty constructor", _cm.getOrgId());
		_cm.setOrgId("TEST");
		assertEquals("orgId should have changed", "TEST", _cm.getOrgId());
	}
	
	@Test
	public void testCompanyModifiedAt() {
		// new() -> _o -> _o.setModifiedAt()
		CompanyModel _o = new CompanyModel();
		assertNull("modifiedAt should not be set by empty constructor", _o.getModifiedAt());
		_o.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _o.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testCompanyCreateReadDeleteWithEmptyConstructor() {
		// new() -> _c1
		CompanyModel _c1 = new CompanyModel();
		assertNull("id should not be set by empty constructor", _c1.getId());
		assertNull("title should not be set by empty constructor", _c1.getTitle());
		assertNull("description should not be set by empty constructor", _c1.getDescription());
		assertNull("orgId should not be set by empty constructor", _c1.getOrgId());

		// create(_c1) -> BAD_REQUEST (because of empty title)
		Response _response = wttWC.replacePath("/").post(_c1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_c1.setTitle("testCompanyCreateReadDeleteWithEmptyConstructor");
		
		// create(_c1) -> BAD_REQUEST (because of empty orgId)
		_response = wttWC.replacePath("/").post(_c1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_c1.setOrgId(org.getId());
		
		// create(_c1) -> OK
		_response = wttWC.replacePath("/").post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());		
		
		CompanyModel _c2 = _response.readEntity(CompanyModel.class);
		assertNull("create() should not change the id of the local object", _c1.getId());
		assertEquals("create() should not change the title of the local object", "testCompanyCreateReadDeleteWithEmptyConstructor", _c1.getTitle());
		assertNull("create() should not change the description of the local object", _c1.getDescription());
		assertEquals("create() should not change the orgId of the local object", org.getId(), _c1.getOrgId());
		assertNotNull("create() should set a valid id on the remote object returned", _c2.getId());
		assertEquals("create() should not change the title of the local object", "testCompanyCreateReadDeleteWithEmptyConstructor", _c2.getTitle());
		assertNull("description of returned object should still be null after remote create", _c2.getDescription());
		
		// read(_c2) -> _c3
		_response = wttWC.replacePath("/").path(_c2.getId()).get();
		assertEquals("read(" + _c2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c3 = _response.readEntity(CompanyModel.class);
		assertEquals("id of returned object should be the same", _c2.getId(), _c3.getId());
		assertEquals("title of returned object should be unchanged after remote create", "testCompanyCreateReadDeleteWithEmptyConstructor", _c3.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", _c2.getDescription(), _c3.getDescription());
		assertEquals("orgId of returned object should be unchanged after remote create", _c2.getOrgId(), _c3.getOrgId());

		// delete(_c3)
		_response = wttWC.replacePath("/").path(_c3.getId()).delete();
		assertEquals("delete(" + _c3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCompanyCreateReadDelete() {
		// new("MY_TITLE", "MY_DESC") -> _c1
		CompanyModel _c1 = new CompanyModel("testCompanyCreateReadDelete", "MY_DESC", org.getId());
		assertNull("id should not be set by constructor", _c1.getId());
		assertEquals("title should be set by constructor", "testCompanyCreateReadDelete", _c1.getTitle());
		assertEquals("description should be set by constructor", "MY_DESC", _c1.getDescription());
		assertEquals("orgId should be set by constructor", org.getId(), _c1.getOrgId());
		// create(_c1) -> _c2
		Response _response = wttWC.replacePath("/").post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c2 = _response.readEntity(CompanyModel.class);
		assertNull("id should be still null after remote create", _c1.getId());
		assertEquals("title should be unchanged after remote create", "testCompanyCreateReadDelete", _c1.getTitle());
		assertEquals("description should be unchanged after remote create", "MY_DESC", _c1.getDescription());
		assertEquals("remote create should not change the orgId", org.getId(), _c1.getOrgId());
		
		assertNotNull("id of returned object should be set", _c2.getId());
		assertEquals("title of returned object should be unchanged after remote create", "testCompanyCreateReadDelete", _c2.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", "MY_DESC", _c2.getDescription());
		assertEquals("remote create should not change the orgId", org.getId(), _c2.getOrgId());

		// read(_c2)  -> _c3
		_response = wttWC.replacePath("/").path(_c2.getId()).get();
		assertEquals("read(" + _c2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c3 = _response.readEntity(CompanyModel.class);
		assertEquals("id of returned object should be the same", _c2.getId(), _c3.getId());
		assertEquals("title of returned object should be unchanged after remote create", _c2.getTitle(), _c3.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", _c2.getDescription(), _c3.getDescription());
		assertEquals("remote create should not change the orgId", org.getId(), _c3.getOrgId());

		// delete(_c3)
		_response = wttWC.replacePath("/").path(_c3.getId()).delete();
		assertEquals("delete(" + _c3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateCompanyWithClientSideId() {
		// new() -> _c1 -> _c1.setId()
		CompanyModel _c1 = new CompanyModel();
		_c1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _c1.getId());
		// create(_c1) -> BAD_REQUEST
		Response _response = wttWC.replacePath("/").post(_c1);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateCompanyWithDuplicateId() {
		// create(new()) -> _c2
		Response _response = wttWC.replacePath("/").post(new CompanyModel("testCreateCompanyWithDuplicateId", "MY_DESC", org.getId()));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c2 = _response.readEntity(CompanyModel.class);

		// new() -> _c3 -> _c3.setId(_c2.getId())
		CompanyModel _c3 = new CompanyModel();
		_c3.setId(_c2.getId());		// wrongly create a 2nd CompanyModel object with the same ID
		
		// create(_c3) -> CONFLICT
		_response = wttWC.replacePath("/").post(_c3);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_c2)
		_response = wttWC.replacePath("/").path(_c2.getId()).delete();
		assertEquals("delete(" + _c2.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCompanyList(
	) {		
		ArrayList<CompanyModel> _localList = new ArrayList<CompanyModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			// create(new()) -> _localList
			_response = wttWC.replacePath("/").post(new CompanyModel("testCompanyList" + i, "MY_DESC", org.getId()));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(CompanyModel.class));
		}
		
		// list(/) -> _remoteList
		_response = wttWC.replacePath("/").query("size", Integer.toString(Integer.MAX_VALUE)).get();
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		List<CompanyModel> _remoteList = new ArrayList<CompanyModel>(wttWC.getCollection(CompanyModel.class));

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (CompanyModel _c : _remoteList) {
			_remoteListIds.add(_c.getId());
		}
		
		for (CompanyModel _c : _localList) {
			assertTrue("company <" + _c.getId() + "> should be listed", _remoteListIds.contains(_c.getId()));
		}
		for (CompanyModel _c : _localList) {
			_response = wttWC.replacePath("/").path(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(CompanyModel.class);
		}
		for (CompanyModel _c : _localList) {
			_response = wttWC.replacePath("/").path(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}

	@Test
	public void testCompanyCreate() {
		// new("MY_TITLE", "MY_DESC") -> _c1
		CompanyModel _c1 = new CompanyModel("testCompanyCreate1", "MY_DESC1", org.getId());
		// new("MY_TITLE2", "MY_DESC2") -> _c2
		CompanyModel _c2 = new CompanyModel("testCompanyCreate2", "MY_DESC2", org.getId());
		
		// create(_c1)  -> _c3
		Response _response = wttWC.replacePath("/").post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c3 = _response.readEntity(CompanyModel.class);

		// create(_c2) -> _c4
		_response = wttWC.replacePath("/").post(_c2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c4 = _response.readEntity(CompanyModel.class);		
		assertNotNull("ID should be set", _c3.getId());
		assertNotNull("ID should be set", _c4.getId());
		assertThat(_c4.getId(), not(equalTo(_c3.getId())));
		assertEquals("title1 should be set correctly", "testCompanyCreate1", _c3.getTitle());
		assertEquals("description1 should be set correctly", "MY_DESC1", _c3.getDescription());
		assertEquals("orgId1 should be set correctly", org.getId(), _c3.getOrgId());
		assertEquals("title2 should be set correctly", "testCompanyCreate2", _c4.getTitle());
		assertEquals("description2 should be set correctly", "MY_DESC2", _c4.getDescription());
		assertEquals("orgId2 should be set correctly", org.getId(), _c4.getOrgId());

		// delete(_c3) -> NO_CONTENT
		_response = wttWC.replacePath("/").path(_c3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_c4) -> NO_CONTENT
		_response = wttWC.replacePath("/").path(_c4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCompanyDoubleCreate(
	) {
		// create(new()) -> _c
		Response _response = wttWC.replacePath("/").post(new CompanyModel("testCompanyDoubleCreate", "MY_DESC", org.getId()));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c = _response.readEntity(CompanyModel.class);
		assertNotNull("ID should be set:", _c.getId());		
		
		// create(_c) -> CONFLICT
		_response = wttWC.replacePath("/").post(_c);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_c) -> NO_CONTENT
		_response = wttWC.replacePath("/").path(_c.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testCompanyRead(
	) {
		ArrayList<CompanyModel> _localList = new ArrayList<CompanyModel>();
		Response _response = null;
		OrgModel _om = null;
		for (int i = 0; i < LIMIT; i++) {
			_om = OrgTest.createOrg(addressbookWC, adb.getId(), "ORGNAME" + i, OrgType.LTD);
			_response = wttWC.replacePath("/").post(new CompanyModel("testCompanyRead" + i, "MY_DESC", _om.getId()));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(CompanyModel.class));
		}
	
		// test read on each local element
		for (CompanyModel _c : _localList) {
			_response = wttWC.replacePath("/").path(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(CompanyModel.class);
		}

		// test read on each listed element
		_response = wttWC.replacePath("/").get();
		List<CompanyModel> _remoteList = new ArrayList<CompanyModel>(wttWC.getCollection(CompanyModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		CompanyModel _tmpObj = null;
		for (CompanyModel _c : _remoteList) {
			_response = wttWC.replacePath("/").path(_c.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(CompanyModel.class);
			assertEquals("ID should be unchanged when reading a company", _c.getId(), _tmpObj.getId());						
		}

		for (CompanyModel _c : _localList) {
			_response = wttWC.replacePath("/").path(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}	

	@Test
	public void testCompanyMultiRead(
	) {
		// new() -> _c1
		CompanyModel _c1 = new CompanyModel("testCompanyMultiRead", "MY_DESC", org.getId());
		
		// create(_c1) -> _c2
		Response _response = wttWC.replacePath("/").post(_c1);
		CompanyModel _c2 = _response.readEntity(CompanyModel.class);

		// read(_c2) -> _c3
		_response = wttWC.replacePath("/").path(_c2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c3 = _response.readEntity(CompanyModel.class);
		assertEquals("ID should be unchanged after read", _c2.getId(), _c3.getId());		

		// read(_c2) -> _c4
		_response = wttWC.replacePath("/").path(_c2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c4 = _response.readEntity(CompanyModel.class);
		
		// but: the two objects are not equal !
		assertEquals("ID should be the same", _c3.getId(), _c4.getId());
		assertEquals("title should be the same", _c3.getTitle(), _c4.getTitle());
		assertEquals("description should be the same", _c3.getDescription(), _c4.getDescription());
		
		assertEquals("ID should be the same", _c3.getId(), _c2.getId());
		assertEquals("title should be the same", _c3.getTitle(), _c2.getTitle());
		assertEquals("description should be the same", _c3.getDescription(), _c2.getDescription());
		
		// delete(_c2)
		_response = wttWC.replacePath("/").path(_c2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCompanyUpdate(
	) {
		// new() -> _c1
		CompanyModel _c1 = new CompanyModel("testCompanyUpdate1", "MY_DESC1", org.getId());
		
		// create(_c1) -> _c2
		Response _response = wttWC.replacePath("/").post(_c1);
		CompanyModel _c2 = _response.readEntity(CompanyModel.class);
		
		// change the attributes
		// update(_c2) -> _c3
		_c2.setTitle("testCompanyUpdate2");
		_c2.setDescription("MY_DESC2");
		wttWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = wttWC.replacePath("/").path(_c2.getId()).put(_c2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c3 = _response.readEntity(CompanyModel.class);

		assertNotNull("ID should be set", _c3.getId());
		assertEquals("ID should be unchanged", _c2.getId(), _c3.getId());	
		assertEquals("title should have changed", "testCompanyUpdate2", _c3.getTitle());
		assertEquals("description should have changed", "MY_DESC2", _c3.getDescription());

		// reset the attributes
		// update(_c2) -> _c4
		_c2.setTitle("testCompanyUpdate3");
		_c2.setDescription("MY_DESC3");
		wttWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = wttWC.replacePath("/").path(_c2.getId()).put(_c2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _c4 = _response.readEntity(CompanyModel.class);

		assertNotNull("ID should be set", _c4.getId());
		assertEquals("ID should be unchanged", _c2.getId(), _c4.getId());	
		assertEquals("title should have changed", "testCompanyUpdate3", _c4.getTitle());
		assertEquals("description should have changed", "MY_DESC3", _c4.getDescription());
		
		_response = wttWC.replacePath("/").path(_c2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCompanyDelete(
	) {
		// new() -> _c0
		CompanyModel _c0 = new CompanyModel("testCompanyDelete1", "MY_DESC1", org.getId());
		// create(_c0) -> _c1
		Response _response = wttWC.replacePath("/").post(_c0);
		CompanyModel _c1 = _response.readEntity(CompanyModel.class);
		
		// read(_c1) -> _tmpObj
		_response = wttWC.replacePath("/").path(_c1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _tmpObj = _response.readEntity(CompanyModel.class);
		assertEquals("ID should be unchanged when reading a company (remote):", _c1.getId(), _tmpObj.getId());						
		
		// delete(_c1) -> OK
		_response = wttWC.replacePath("/").path(_c1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	
		// read the deleted object twice
		// read(_c1) -> NOT_FOUND
		_response = wttWC.replacePath("/").path(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read(_c1) -> NOT_FOUND
		_response = wttWC.replacePath("/").path(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCompanyDoubleDelete(
	) {
		// new() -> _c0
		CompanyModel _c0 = new CompanyModel("testCompanyDoubleDelete1", "MY_DESC1", org.getId());
		
		// create(_c0) -> _c1
		Response _response = wttWC.replacePath("/").post(_c0);
		CompanyModel _c1 = _response.readEntity(CompanyModel.class);

		// read(_c1) -> OK
		_response = wttWC.replacePath("/").path(_c1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		// delete(_c1) -> OK
		_response = wttWC.replacePath("/").path(_c1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		// read(_c1) -> NOT_FOUND
		_response = wttWC.replacePath("/").path(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// delete _c1 -> NOT_FOUND
		_response = wttWC.replacePath("/").path(_c1.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read _c1 -> NOT_FOUND
		_response = wttWC.replacePath("/").path(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCompanyModifications() {
		// create(new CompanyModel()) -> _o
		Response _response = wttWC.replacePath("/").post(new CompanyModel("testCompanyModifications1", "MY_DESC1", org.getId()));
		CompanyModel _o = _response.readEntity(CompanyModel.class);
		
		// test createdAt and createdBy
		assertNotNull("create() should set createdAt", _o.getCreatedAt());
		assertNotNull("create() should set createdBy", _o.getCreatedBy());
		// test modifiedAt and modifiedBy (= same as createdAt/createdBy)
		assertNotNull("create() should set modifiedAt", _o.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _o.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _o.getCreatedAt(), _o.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _o.getCreatedBy(), _o.getModifiedBy());
		
		// update(_o)  -> _o2
		_o.setTitle("testCompanyModifications2");
		wttWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = wttWC.replacePath("/").path(_o.getId()).put(_o);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _o2 = _response.readEntity(CompanyModel.class);

		// test createdAt and createdBy (unchanged)
		assertEquals("update() should not change createdAt", _o.getCreatedAt(), _o2.getCreatedAt());
		assertEquals("update() should not change createdBy", _o.getCreatedBy(), _o2.getCreatedBy());
		
		// test modifiedAt and modifiedBy (= different from createdAt/createdBy)
		assertThat(_o2.getModifiedAt(), not(equalTo(_o2.getCreatedAt())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_o2.getModifiedBy(), not(equalTo(_o2.getCreatedBy())));

		// update(o) with modifiedBy/At set on client side -> ignored by server
		_o.setModifiedBy("MYSELF");
		_o.setModifiedAt(new Date(1000));
		wttWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = wttWC.replacePath("/").path(_o.getId()).put(_o);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		CompanyModel _o3 = _response.readEntity(CompanyModel.class);
		
		// test, that modifiedBy really ignored the client-side value "MYSELF"
		assertThat(_o.getModifiedBy(), not(equalTo(_o3.getModifiedBy())));
		// check whether the client-side modifiedAt() is ignored
		assertThat(_o.getModifiedAt(), not(equalTo(_o3.getModifiedAt())));
		
		// delete(_o) -> NO_CONTENT
		_response = wttWC.replacePath("/").path(_o.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	/********************************* helper methods *********************************/	
	public static CompanyModel createCompany(
			WebClient wttWC, 
			WebClient addressbookWC,
			AddressbookModel addressbookModel,
			String title, 
			String description) 
	{
		CompanyModel _cm = new CompanyModel();
		_cm.setTitle(title);
		_cm.setDescription(description);
		_cm.setOrgId(OrgTest.createOrg(addressbookWC, addressbookModel.getId(), "TEST_ORG", OrgType.COOP).getId());
		Response _response = wttWC.replacePath("/").post(_cm);
		return _response.readEntity(CompanyModel.class);
	}
	
	public static void cleanup(
			WebClient wttWC,
			String companyId,
			String testName) {
		cleanup(wttWC, companyId, testName, true);
	}
	
	public static void cleanup(
			WebClient wttWC,
			String companyId,
			String testName,
			boolean closeWC) {
		wttWC.replacePath("/").path(companyId).delete();
		System.out.println(testName + " deleted company <" + companyId + ">.");
		if (closeWC) {
			wttWC.close();
		}
	}
	
	protected int calculateMembers() {
		return 1;
	}
}
