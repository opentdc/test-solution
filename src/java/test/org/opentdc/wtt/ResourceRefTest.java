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
import org.opentdc.addressbooks.OrgModel;
import org.opentdc.addressbooks.OrgType;
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
import test.org.opentdc.addressbooks.OrgTest;
import test.org.opentdc.resources.ResourceTest;

/**
 * Testing References to Resources.
 * @author Bruno Kaiser
 *
 */
public class ResourceRefTest extends AbstractTestClient {
	private static final String CN = "ResourceRefTest";
	private WebClient wc = null;
	private WebClient addressbookWC = null;
	private WebClient resourceWC = null;
	private CompanyModel company = null;
	private ProjectModel parentProject = null;
	private AddressbookModel addressbook = null;
	private ResourceModel resource = null;
	private ContactModel contact = null;
	private OrgModel org = null;

	@Before
	public void initializeTest() {
		wc = createWebClient(ServiceUtil.WTT_API_URL, WttService.class);
		resourceWC = createWebClient(ServiceUtil.RESOURCES_API_URL, ResourcesService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);

		addressbook = AddressbookTest.post(addressbookWC, 
				new AddressbookModel(CN), Status.OK);
		contact = ContactTest.post(addressbookWC, addressbook.getId(),
				new ContactModel(CN + "1", CN + "2"), Status.OK);
		org = OrgTest.post(addressbookWC, addressbook.getId(), 
				new OrgModel(CN, OrgType.LTD), Status.OK);
		company = CompanyTest.post(wc, 
				new CompanyModel(CN + "1", "MY_DESC", org.getId()), Status.OK);
		parentProject = ProjectTest.post(wc, company.getId(), 
				new ProjectModel(CN, "MY_DESC"), Status.OK);
		resource = ResourceTest.post(resourceWC, 
				new ResourceModel(CN + "1", contact.getId()), Status.OK);
	}

	@After
	public void cleanupTest() {
		AddressbookTest.delete(addressbookWC, addressbook.getId(), Status.NO_CONTENT);
		addressbookWC.close();
		
		ResourceTest.delete(resourceWC, resource.getId(), Status.NO_CONTENT);
		resourceWC.close();
		
		CompanyTest.delete(wc, company.getId(), Status.NO_CONTENT);
		wc.close();		
	}
	
	/********************************** resourceRef attribute tests *********************************/
	@Test
	public void testEmptyConstructor() {
		ResourceRefModel _model = new ResourceRefModel();
		assertNull("id should not be set by empty constructor", _model.getId());
		assertNull("resourceId should not be set by empty constructor", _model.getResourceId());
		assertNull("resourceName should not be set by empty constructor", _model.getResourceName());
	}
	
	@Test
	public void testConstructor() {		
		ResourceRefModel _model = new ResourceRefModel("testConstructor");
		assertNull("id should be null", _model.getId());
		assertEquals("resourceId should be set correctly", "testConstructor", _model.getResourceId());
		assertNull("resourceName should not be set", _model.getResourceName());
	}
	
	@Test
	public void testId() {
		ResourceRefModel _model = new ResourceRefModel();
		assertNull("id should not be set by constructor", _model.getId());
		_model.setId("testId");
		assertEquals("id should have changed:", "testId", _model.getId());
	}
	
	@Test
	public void testResourceId() {
		ResourceRefModel _model = new ResourceRefModel();
		assertNull("resourceId should not be set by empty constructor", _model.getResourceId());
		_model.setResourceId("testResourceId");
		assertEquals("resourceId should have changed:", "testResourceId", _model.getResourceId());
	}
	
	@Test
	public void testResourceName() {
		ResourceRefModel _model = new ResourceRefModel();
		assertNull("resourceName should not be set by empty constructor", _model.getResourceName());
		_model.setResourceName("testResourceName");
		assertEquals("resourceName should have changed:", "testResourceName", _model.getResourceName());
	}
		
	@Test
	public void testCreatedBy() {
		ResourceRefModel _model = new ResourceRefModel();
		assertNull("createdBy should not be set by empty constructor", _model.getCreatedBy());
		_model.setCreatedBy("testCreatedBy");
		assertEquals("createdBy should have changed", "testCreatedBy", _model.getCreatedBy());	
	}
	
	@Test
	public void testCreatedAt() {
		ResourceRefModel _model = new ResourceRefModel();
		assertNull("createdAt should not be set by empty constructor", _model.getCreatedAt());
		_model.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _model.getCreatedAt());
	}
		
	@Test
	public void testModifiedBy() {
		ResourceRefModel _model = new ResourceRefModel();
		assertNull("modifiedBy should not be set by empty constructor", _model.getModifiedBy());
		_model.setModifiedBy("testModifiedBy");
		assertEquals("modifiedBy should have changed", "testModifiedBy", _model.getModifiedBy());	
	}
	
	@Test
	public void testModifiedAt() {
		ResourceRefModel _model = new ResourceRefModel();
		assertNull("modifiedAt should not be set by empty constructor", _model.getModifiedAt());
		_model.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _model.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	// list: GET "api/company/{cid}/project/{pid}/resource"
	// add:  POST "api/company/{cid}/project/{pid}/resource"
	// delete:  DELETE "api/company/{cid}/project/{pid}/resource/{rid}"
	@Test
	public void testCreateDeleteWithEmptyConstructor() {
		ResourceRefModel _model1 = new ResourceRefModel();
		assertNull("id should not be set by empty constructor", _model1.getId());
		assertNull("resourceId should not be set by empty constructor", _model1.getResourceId());
		assertNull("resourceName should not be set by empty constructor", _model1.getResourceName());
		
		post(_model1, Status.BAD_REQUEST);
		_model1.setResourceId(resource.getId());
		ResourceRefModel _model2 = post(_model1, Status.OK);		
		assertNull("id should still be null", _model1.getId());
		assertEquals("resourceId should be set by constructor", resource.getId(), _model1.getResourceId());
		assertNull("resourceName should not be set", _model1.getResourceName());
		
		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());		
		assertEquals("create() should not change resourceId", resource.getId(), _model2.getResourceId());
		assertEquals("create() should derive the resourceName", resource.getName(), _model2.getResourceName());
		delete(_model2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testCreateDelete() {
		ResourceRefModel _model1 = new ResourceRefModel(resource.getId());
		assertNull("id should be null", _model1.getId());
		assertEquals("resourceId should be set by constructor", resource.getId(), _model1.getResourceId());
		assertNull("resourceName should not be set", _model1.getResourceName());
		ResourceRefModel _model2 = post(_model1, Status.OK);
		
		assertNull("id should still be null", _model1.getId());
		assertEquals("resourceId should be unchanged after remote create", resource.getId(), _model1.getResourceId());
		assertNull("resourceName should not be set", _model1.getResourceName());
		
		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());		
		assertEquals("create() should not change the resourceId", resource.getId(), _model2.getResourceId());
		assertEquals("create() should derive the resourceName", resource.getName(), _model2.getResourceName());
		delete(_model2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testOnSubProject() {
		ProjectModel _subProject = SubProjectTest.post(wc, company.getId(), parentProject.getId(), 
				new ProjectModel("testResourceRefOnSubProject", "MY_DESC"), Status.OK);
		
		ResourceRefModel _model1 = new ResourceRefModel(resource.getId());
		assertNull("id should not be set by empty constructor", _model1.getId());
		assertEquals("resourceId should be set by constructor", resource.getId(), _model1.getResourceId());
		assertNull("resourceName should not be set by constructor", _model1.getResourceName());
		
		ResourceRefModel _model2 = post(_subProject.getId(), _model1, Status.OK);
		assertNull("create() should not change the id of the local object", _model1.getId());
		assertEquals("create() should not change the resourceId", resource.getId(), _model1.getResourceId());
		assertNull("create() should not change the resourceName", _model1.getResourceName());
		
		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
		assertEquals("resourceId should not change the resourceId", resource.getId(), _model2.getResourceId());
		assertEquals("create() should derive the resourceName", resource.getName(), _model2.getResourceName());
		
		delete(_subProject.getId(), _model2.getId(), Status.NO_CONTENT);
		SubProjectTest.delete(wc, company.getId(), parentProject.getId(), _subProject.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testClientSideId() {
		ResourceRefModel _model = new ResourceRefModel(resource.getId());
		_model.setId("testClientSideId");
		assertEquals("id should have changed", "testClientSideId", _model.getId());
		post(_model, Status.BAD_REQUEST);
	}
	
	@Test
	public void testDuplicateId() {
		ResourceRefModel _model1 = post(new ResourceRefModel(resource.getId()), Status.OK);
		ResourceRefModel _model2 = new ResourceRefModel(resource.getId());
		_model2.setId(_model1.getId());		// wrongly create a 2nd ProjectModel object with the same ID
		post(_model2, Status.CONFLICT);
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testList() {		
		List<ResourceRefModel> _listBefore = list(null, Status.OK);
		ArrayList<ResourceRefModel> _localList = new ArrayList<ResourceRefModel>();		
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(post(new ResourceRefModel(resource.getId()), Status.OK));
		}
		assertEquals("correct number of resourceRefs should be created", LIMIT, _localList.size());
		List<ResourceRefModel> _listAfter = list(null, Status.OK);		
		assertEquals("list() should return the correct number of resourceRefs", _listBefore.size() + LIMIT, _listAfter.size());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (ResourceRefModel _model : _listAfter) {
			_remoteListIds.add(_model.getId());
		}
		for (ResourceRefModel _model : _localList) {
			assertTrue("resourceRef <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
		}
		for (ResourceRefModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}
	}

	@Test
	public void testCreate() {
		ResourceRefModel _model1 = post(new ResourceRefModel(resource.getId()), Status.OK);
		ResourceRefModel _model2 = post(new ResourceRefModel(resource.getId()), Status.OK);
		
		assertNotNull("ID should be set", _model1.getId());
		assertEquals("resourceId should be set correctly", resource.getId(), _model1.getResourceId());
		assertEquals("resourceName should be derived", resource.getName(), _model1.getResourceName());
		
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("resourceId should be set correctly", resource.getId(), _model2.getResourceId());
		assertEquals("resourceName should be derived", resource.getName(), _model2.getResourceName());
		assertThat(_model1.getId(), not(equalTo(_model2.getId())));
		delete(_model1.getId(), Status.NO_CONTENT);
		delete(_model2.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDoubleCreate() {
		ResourceRefModel _model = post(new ResourceRefModel(resource.getId()), Status.OK);
		assertNotNull("ID should be set:", _model.getId());		
		assertEquals("ID should be unchanged", resource.getId(), _model.getResourceId());		
		post(_model, Status.CONFLICT);
		delete(_model.getId(), Status.NO_CONTENT);
	}
	
	@Test
	public void testDelete() {
		ResourceRefModel _model1 = post(new ResourceRefModel(resource.getId()), Status.OK);
		delete(_model1.getId(), Status.NO_CONTENT);
		delete(_model1.getId(), Status.NOT_FOUND);
	}
	
	@Test
	public void testResourceRefModifications() {
		ResourceRefModel _model = post(new ResourceRefModel(resource.getId()), Status.OK);
		
		assertNotNull("create() should set createdAt", _model.getCreatedAt());
		assertNotNull("create() should set createdBy", _model.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _model.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _model.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _model.getCreatedAt(), _model.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _model.getCreatedBy(), _model.getModifiedBy());
		
		// there is no update method for ResourceRef
		delete(_model.getId(), Status.NO_CONTENT);
	}

	/********************************* helper methods *********************************/	
	/**
	 * Retrieve a list of ResourceRefModel from WttService by executing a HTTP GET request.
	 * This uses neither position nor size queries.
	 * @param query the URL query to use
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of ResourceRefModel objects in JSON format
	 */
	private List<ResourceRefModel> list(
			String query, 
			Status expectedStatus) {
		return list(wc, company.getId(), parentProject.getId(), query, -1, Integer.MAX_VALUE, expectedStatus);
	}
		
	/**
	 * Retrieve a list of ResourceRefModel from WttService by executing a HTTP GET request.
	 * @param webClient the WebClient for the WttService
	 * @param companyId the ID of the company
	 * @param parentProjectId the ID of the parentProject to be listed
	 * @param query the URL query to use
	 * @param position the position to start a batch with
	 * @param size the size of a batch
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of ResourceRefModel objects in JSON format
	 */
	public static List<ResourceRefModel> list(
			WebClient webClient, 
			String companyId,
			String parentProjectId,
			String query, 
			int position,
			int size,
			Status expectedStatus) {
		webClient.resetQuery();
		webClient.replacePath("/").path(companyId).
			path(ServiceUtil.PROJECT_PATH_EL).path(parentProjectId).path(ServiceUtil.RESREF_PATH_EL);
		Response _response = executeListQuery(webClient, query, position, size);
		List<ResourceRefModel> _list = null;
		if (expectedStatus != null) {
			assertEquals("list() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			_list = new ArrayList<ResourceRefModel>(webClient.getCollection(ResourceRefModel.class));
			System.out.println("list(webClient, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + ") ->" + _list.size());
		}
		return _list;
	}
	
	/**
	 * Create a new SubProject on the server by executing a HTTP POST request.
	 * @param model the ResourceRefModel to post
	 * @param expectedStatus the expected HTTP status to test on; if this is null, it will not be tested
	 * @return the created ResourceRefModel
	 */
	private ResourceRefModel post(
			ResourceRefModel model,
			Status expectedStatus) {
		return post(wc, company.getId(), parentProject.getId(), model, expectedStatus);
	}
	
	private ResourceRefModel post(
			String parentProjectId,
			ResourceRefModel model,
			Status expectedStatus) {
		return post(wc, company.getId(), parentProjectId, model, expectedStatus);
	}

	/**
	 * Create a new SubProject on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the WttService
	 * @param companyId the ID of the company
	 * @param parentProjectId the ID of the parentProject
	 * @param model the ResourceRefModel data to create on the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created ResourceRefModel
	 */
	public static ResourceRefModel post(
			WebClient webClient,
			String companyId,
			String parentProjectId,
			ResourceRefModel model,
			Status expectedStatus) 
	{
		Response _response = webClient.replacePath("/").path(companyId).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProjectId).path(ServiceUtil.RESREF_PATH_EL).post(model);
		if (expectedStatus != null) {
			assertEquals("POST should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(ResourceRefModel.class);
		} else {
			return null;
		}
	}	
	
	// WttService does not support read() (GET) of ResourceRef
	// WttService does not support update() (PUT) of ResourceRef
	
	/**
	 * Delete the SubProject with id on the WttService by executing a HTTP DELETE method.
	 * @param id the id of the ResourceRefModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	private void delete(
			String id, 
			Status expectedStatus) {
		delete(wc, company.getId(), parentProject.getId(), id, expectedStatus);
	}
	
	private void delete(
			String parentProjectId,
			String id,
			Status expectedStatus) {
		delete(wc, company.getId(), parentProjectId, id, expectedStatus);
	}
	
	/**
	 * Delete the SubProject with id on the WttService by executing a HTTP DELETE method.
	 * @param webClient the WebClient for the WttService
	 * @param companyId the ID of the company 
	 * @param parentProjectId the ID of the parentProject
	 * @param id the id of the ResourceRefModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public static void delete(
			WebClient webClient,
			String companyId,
			String parentProjectId,
			String id,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(companyId).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProjectId).path(ServiceUtil.RESREF_PATH_EL).path(id).delete();	
		if (expectedStatus != null) {
			assertEquals("DELETE should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
	}		
	
	/* (non-Javadoc)
	 * @see test.org.opentdc.AbstractTestClient#calculateMembers()
	 */
	protected int calculateMembers() {
		return list(wc, company.getId(), parentProject.getId(), null, 0, Integer.MAX_VALUE, Status.OK).size();
	}}
