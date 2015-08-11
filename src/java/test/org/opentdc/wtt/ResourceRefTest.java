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
package test.org.opentdc.wtt;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.ProjectModel;
import org.opentdc.wtt.ResourceRefModel;
import org.opentdc.wtt.WttService;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.addressbooks.AddressbookTest;
import test.org.opentdc.addressbooks.ContactTest;
import test.org.opentdc.resources.ResourceTest;

public class ResourceRefTest extends AbstractTestClient {
	private WebClient wc = null;
	private WebClient addressbookWC = null;
	private WebClient resourceWC = null;
	private CompanyModel company = null;
	private ProjectModel parentProject = null;
	private AddressbookModel addressbook = null;
	private ResourceModel resource = null;
	private ContactModel contact = null;

	@Before
	public void initializeTest() {
		wc = createWebClient(ServiceUtil.WTT_API_URL, WttService.class);
		resourceWC = createWebClient(ServiceUtil.RESOURCES_API_URL, ResourcesService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);

		addressbook = AddressbookTest.createAddressbook(addressbookWC, this.getClass().getName(), Status.OK);
		company = CompanyTest.create(wc, addressbookWC, addressbook, this.getClass().getName(), "MY_DESC");
		parentProject = ProjectTest.create(wc, company.getId(), this.getClass().getName(), "MY_DESC");
		contact = ContactTest.create(addressbookWC, addressbook.getId(), "FNAME", "LNAME");
		resource = ResourceTest.create(resourceWC, addressbook, contact, this.getClass().getName(), Status.OK);
	}

	@After
	public void cleanupTest() {
		AddressbookTest.delete(addressbookWC, addressbook.getId(), Status.NO_CONTENT);
		System.out.println("deleted 1 addressbook");
		addressbookWC.close();
		ResourceTest.cleanup(resourceWC, resource.getId(), this.getClass().getName());
		CompanyTest.cleanup(wc, company.getId(), this.getClass().getName());
	}
	
	/********************************** resourceRef attribute tests *********************************/
	@Test
	public void testEmptyConstructor() {
		ResourceRefModel _rrm = new ResourceRefModel();
		assertNull("id should not be set by empty constructor", _rrm.getId());
		assertNull("resourceId should not be set by empty constructor", _rrm.getResourceId());
		assertNull("resourceName should not be set by empty constructor", _rrm.getResourceName());
	}
	
	@Test
	public void testConstructor() {		
		ResourceRefModel _rrm = new ResourceRefModel("MY_RESID");
		assertNull("id should be null", _rrm.getId());
		assertEquals("resourceId should be set correctly", "MY_RESID", _rrm.getResourceId());
		assertNull("resourceName should not be set", _rrm.getResourceName());
	}
	
	@Test
	public void testId() {
		ResourceRefModel _rrm = new ResourceRefModel();
		assertNull("id should not be set by constructor", _rrm.getId());
		_rrm.setId("MY_ID");
		assertEquals("id should have changed:", "MY_ID", _rrm.getId());
	}
	
	@Test
	public void testResourceId() {
		// new() -> _rrm -> _rrm.setResourceId()
		ResourceRefModel _rrm = new ResourceRefModel();
		assertNull("resourceId should not be set by empty constructor", _rrm.getResourceId());
		_rrm.setResourceId("MY_RESID");
		assertEquals("resourceId should have changed:", "MY_RESID", _rrm.getResourceId());
	}
	
	@Test
	public void testResourceName() {
		ResourceRefModel _rrm = new ResourceRefModel();
		assertNull("resourceName should not be set by empty constructor", _rrm.getResourceName());
		_rrm.setResourceName("MY_FNAME");
		assertEquals("resourceName should have changed:", "MY_FNAME", _rrm.getResourceName());
	}
		
	@Test
	public void testCreatedBy() {
		ResourceRefModel _rrm = new ResourceRefModel();
		assertNull("createdBy should not be set by empty constructor", _rrm.getCreatedBy());
		_rrm.setCreatedBy("MY_NAME");
		assertEquals("createdBy should have changed", "MY_NAME", _rrm.getCreatedBy());	
	}
	
	@Test
	public void testCreatedAt() {
		ResourceRefModel _rrm = new ResourceRefModel();
		assertNull("createdAt should not be set by empty constructor", _rrm.getCreatedAt());
		_rrm.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _rrm.getCreatedAt());
	}
		
	@Test
	public void testModifiedBy() {
		ResourceRefModel _rrm = new ResourceRefModel();
		assertNull("modifiedBy should not be set by empty constructor", _rrm.getModifiedBy());
		_rrm.setModifiedBy("MY_NAME");
		assertEquals("modifiedBy should have changed", "MY_NAME", _rrm.getModifiedBy());	
	}
	
	@Test
	public void testModifiedAt() {
		ResourceRefModel _rrm = new ResourceRefModel();
		assertNull("modifiedAt should not be set by empty constructor", _rrm.getModifiedAt());
		_rrm.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _rrm.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	// list: GET "api/company/{cid}/project/{pid}/resource"
	// add:  POST "api/company/{cid}/project/{pid}/resource"
	// delete:  DELETE "api/company/{cid}/project/{pid}/resource/{rid}"
	@Test
	public void testCreateDeleteWithEmptyConstructor() {
		ResourceRefModel _rrm1 = new ResourceRefModel();
		assertNull("id should not be set by empty constructor", _rrm1.getId());
		assertNull("resourceId should not be set by empty constructor", _rrm1.getResourceId());
		assertNull("resourceName should not be set by empty constructor", _rrm1.getResourceName());
		
		// create(_rrm1) -> BAD_REQUEST (because of empty resourceId)
		Response _response = wc.replacePath("/").post(_rrm1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_rrm1.setResourceId(resource.getId());

		_response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).post(_rrm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _rrm2 = _response.readEntity(ResourceRefModel.class);
		
		assertNull("id should still be null", _rrm1.getId());
		assertEquals("resourceId should be set by constructor", resource.getId(), _rrm1.getResourceId());
		assertNull("resourceName should not be set", _rrm1.getResourceName());
		
		assertNotNull("create() should set a valid id on the remote object returned", _rrm2.getId());		
		assertEquals("create() should not change resourceId", resource.getId(), _rrm2.getResourceId());
		assertEquals("create() should derive the resourceName", resource.getName(), _rrm2.getResourceName());

		_response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).path(_rrm2.getId()).delete();
		assertEquals("delete(" + _rrm2.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateDelete() {
		ResourceRefModel _rrm1 = new ResourceRefModel(resource.getId());
		assertNull("id should be null", _rrm1.getId());
		assertEquals("resourceId should be set by constructor", resource.getId(), _rrm1.getResourceId());
		assertNull("resourceName should not be set", _rrm1.getResourceName());

		Response _response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).post(_rrm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _rrm2 = _response.readEntity(ResourceRefModel.class);
		
		assertNull("id should still be null", _rrm1.getId());
		assertEquals("resourceId should be unchanged after remote create", resource.getId(), _rrm1.getResourceId());
		assertNull("resourceName should not be set", _rrm1.getResourceName());
		
		assertNotNull("create() should set a valid id on the remote object returned", _rrm2.getId());		
		assertEquals("create() should not change the resourceId", resource.getId(), _rrm2.getResourceId());
		assertEquals("create() should derive the resourceName", resource.getName(), _rrm2.getResourceName());
		
		// delete(_rrm2)
		_response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).path(_rrm2.getId()).delete();
		assertEquals("delete(" + _rrm2.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testOnSubProject() {
		Response _response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.PROJECT_PATH_EL)
				.post(new ProjectModel("testResourceRefOnSubProject", "MY_DESC"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm = _response.readEntity(ProjectModel.class);
		
		ResourceRefModel _rrm1 = new ResourceRefModel(resource.getId());
		assertNull("id should not be set by empty constructor", _rrm1.getId());
		assertEquals("resourceId should be set by constructor", resource.getId(), _rrm1.getResourceId());
		assertNull("resourceName should not be set by constructor", _rrm1.getResourceName());
		
		_response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(_pm.getId()).
				path(ServiceUtil.RESREF_PATH_EL).post(_rrm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _rrm2 = _response.readEntity(ResourceRefModel.class);
		
		assertNull("create() should not change the id of the local object", _rrm1.getId());
		assertEquals("create() should not change the resourceId", resource.getId(), _rrm1.getResourceId());
		assertNull("create() should not change the resourceName", _rrm1.getResourceName());
		
		assertNotNull("create() should set a valid id on the remote object returned", _rrm2.getId());
		assertEquals("resourceId should not change the resourceId", resource.getId(), _rrm2.getResourceId());
		assertEquals("create() should derive the resourceName", resource.getName(), _rrm2.getResourceName());
		
		// delete(_rrm2)
		 _response = wc.replacePath("/").path(company.getId()).
				 path(ServiceUtil.PROJECT_PATH_EL).path(_pm.getId()).
				 path(ServiceUtil.RESREF_PATH_EL).path(_rrm2.getId()).delete();
		assertEquals("delete(" + _rrm2.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());		

		// delete(_pm)
		_response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(_pm.getId()).delete();
		assertEquals("delete(" + _pm.getId() + ") should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateWithDuplicateId() {
		// new(1) -> _rrm1
		Response _response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).post(new ResourceRefModel(resource.getId()));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _rrm1 = _response.readEntity(ResourceRefModel.class);

		// new() -> _rrm2 -> _rrm2.setId(_rrm.getId())
		ResourceRefModel _rrm2 = new ResourceRefModel(resource.getId());
		_rrm2.setId(_rrm1.getId());		// wrongly create a 2nd ResourceRefModel object with the same ID
		
		// create(_rrm2) -> CONFLICT
		_response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).post(_rrm2);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_rrm)
		 _response = wc.replacePath("/").path(company.getId()).
				 path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				 path(ServiceUtil.RESREF_PATH_EL).path(_rrm1.getId()).delete();
		assertEquals("delete(" + _rrm1.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());		
	}
	
	@Test
	public void testList(
	) {		
		ArrayList<ResourceRefModel> _localList = new ArrayList<ResourceRefModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			_response = wc.replacePath("/").path(company.getId()).
					path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
					path(ServiceUtil.RESREF_PATH_EL).post(new ResourceRefModel(resource.getId()));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(ResourceRefModel.class));
		}
		
		_response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).get();
		List<ResourceRefModel> _remoteList = new ArrayList<ResourceRefModel>(wc.getCollection(ResourceRefModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (ResourceRefModel _rrm1 : _remoteList) {
			_remoteListIds.add(_rrm1.getId());
		}
		
		for (ResourceRefModel _rrm2 : _localList) {
			assertTrue("resource <" + _rrm2.getId() + "> should be listed", _remoteListIds.contains(_rrm2.getId()));
		}

		for (ResourceRefModel _rrm3 : _localList) {
			_response = wc.replacePath("/").path(company.getId()).
					path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
					path(ServiceUtil.RESREF_PATH_EL).path(_rrm3.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}

	@Test
	public void testCreate() {
		ResourceRefModel _rrm1 = new ResourceRefModel(resource.getId());
		ResourceRefModel _rrm2 = new ResourceRefModel(resource.getId());
		Response _response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).post(_rrm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _rrm3 = _response.readEntity(ResourceRefModel.class);

		_response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).post(_rrm2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _rrm4 = _response.readEntity(ResourceRefModel.class);		
		assertNotNull("ID should be set", _rrm3.getId());
		assertNotNull("ID should be set", _rrm4.getId());
		assertThat(_rrm4.getId(), not(equalTo(_rrm3.getId())));
		assertEquals("resourceId should be set correctly", resource.getId(), _rrm3.getResourceId());
		assertEquals("resourceName should be derived", resource.getName(), _rrm3.getResourceName());
		
		assertEquals("resourceId should be set correctly", resource.getId(), _rrm4.getResourceId());
		assertEquals("resourceName should be derived", resource.getName(), _rrm4.getResourceName());

		_response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).path(_rrm3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		_response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).path(_rrm4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testDoubleCreate(
	) {
		Response _response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).post(new ResourceRefModel(resource.getId()));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _rrm = _response.readEntity(ResourceRefModel.class);
		assertEquals("ID should be unchanged", resource.getId(), _rrm.getResourceId());		
		
		_response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).post(_rrm);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		_response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).path(_rrm.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testDelete(
	) {
		Response _response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).post(new ResourceRefModel(resource.getId()));
		ResourceRefModel _rrm = _response.readEntity(ResourceRefModel.class);
		
		_response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).path(_rrm.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		_response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).path(_rrm.getId()).delete();
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testResourceRefModifications() {
		Response _response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).post(new ResourceRefModel(resource.getId()));
		ResourceRefModel _rrm = _response.readEntity(ResourceRefModel.class);
		
		assertNotNull("create() should set createdAt", _rrm.getCreatedAt());
		assertNotNull("create() should set createdBy", _rrm.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _rrm.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _rrm.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _rrm.getCreatedAt(), _rrm.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _rrm.getCreatedBy(), _rrm.getModifiedBy());
		
		// there is no update method for ResourceRef
		
		_response = wc.replacePath("/").path(company.getId()).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProject.getId()).
				path(ServiceUtil.RESREF_PATH_EL).path(_rrm.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	/********************************* helper methods *********************************/	
	public static ResourceRefModel create(
				WebClient wttWC,
				String companyId,
				String projectId,
				String resourceId) {
		Response _response = wttWC.replacePath("/").path(companyId).
				path(ServiceUtil.PROJECT_PATH_EL).path(projectId).
				path(ServiceUtil.RESREF_PATH_EL).post(new ResourceRefModel(resourceId));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		return _response.readEntity(ResourceRefModel.class);	
	}
	
	protected int calculateMembers() {
		return 1;
	}
}
