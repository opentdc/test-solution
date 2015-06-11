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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.ProjectModel;
import org.opentdc.wtt.ResourceRefModel;
import org.opentdc.wtt.WttService;

import test.org.opentdc.AbstractTestClient;

public class ResourceRefTest extends AbstractTestClient<WttService> {
	
	public static final String API = "api/company/";	
	private static CompanyModel company = null;
	private static ProjectModel parentProject = null;
	public static final String PATH_EL_RESOURCE = "resource";
	public static final String PATH_EL_PROJECT = "project";

	@Before
	public void initializeTest() {
		initializeTest(API, WttService.class);
		Response _response = webclient.replacePath("/")
				.post(new CompanyModel("ResourceRefTest", "MY_DESC"));
		company = _response.readEntity(CompanyModel.class);
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT)
				.post(new ProjectModel("ResourceRefTest", "MY_DESC"));
		parentProject = _response.readEntity(ProjectModel.class);
	}
	
	@After
	public void cleanupTest() {
		webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).delete();
		webclient.replacePath("/").path(company.getId()).delete();
	}

	/********************************** resourceRef attribute tests *********************************/
	@Test
	public void testResourceRefModelEmptyConstructor() {
		// new() -> _rrm
		ResourceRefModel _rrm = new ResourceRefModel();
		assertNull("id should not be set by empty constructor", _rrm.getId());
		assertNull("resourceId should not be set by empty constructor", _rrm.getResourceId());
		assertNull("firstname should not be set by empty constructor", _rrm.getFirstName());
		assertNull("lastname should not be set by empty constructor", _rrm.getLastName());
	}
	
	@Test
	public void testResourceRefModelConstructor() {		
		// new("MY_RESID", "MY_FNAME", "MY_LNAME") -> _rrm
		ResourceRefModel _rrm = new ResourceRefModel("MY_RESID", "MY_FNAME", "MY_LNAME");
		assertNull("id should be null", _rrm.getId());
		assertEquals("resourceId should be set correctly", "MY_RESID", _rrm.getResourceId());
		assertEquals("firstname should be set correctly", "MY_FNAME", _rrm.getFirstName());
		assertEquals("lastname should be set correctly", "MY_LNAME", _rrm.getLastName());
	}
	
	@Test
	public void testResourceRefIdAttributeChange() {
		// new() -> _rrm -> _rrm.setId()
		ResourceRefModel _rrm = new ResourceRefModel();
		assertNull("id should not be set by constructor", _rrm.getId());
		_rrm.setId("MY_ID");
		assertEquals("id should have changed:", "MY_ID", _rrm.getId());
	}
	
	@Test
	public void testResourceRefResourceIdAttributeChange() {
		// new() -> _rrm -> _rrm.setResourceId()
		ResourceRefModel _rrm = new ResourceRefModel();
		assertNull("resourceId should not be set by empty constructor", _rrm.getResourceId());
		_rrm.setResourceId("MY_RESID");
		assertEquals("resourceId should have changed:", "MY_RESID", _rrm.getResourceId());
	}
	
	@Test
	public void testResourceRefFirstNameAttributeChange() {
		// new() -> _rrm -> _rrm.setFirstName()
		ResourceRefModel _rrm = new ResourceRefModel();
		assertNull("firstname should not be set by empty constructor", _rrm.getFirstName());
		_rrm.setFirstName("MY_FNAME");
		assertEquals("firstname should have changed:", "MY_FNAME", _rrm.getFirstName());
	}
	
	@Test
	public void testResourceRefLastNameAttributeChange() {
		// new() -> _rrm -> _rrm.setLastName()
		ResourceRefModel _rrm = new ResourceRefModel();
		assertNull("lastname should not be set by empty constructor", _rrm.getLastName());
		_rrm.setLastName("MY_LNAME");
		assertEquals("lastname should have changed:", "MY_LNAME", _rrm.getLastName());
	}
	
	@Test
	public void testResourceRefCreatedBy() {
		// new() -> _rrm -> _rrm.setCreatedBy()
		ResourceRefModel _rrm = new ResourceRefModel();
		assertNull("createdBy should not be set by empty constructor", _rrm.getCreatedBy());
		_rrm.setCreatedBy("MY_NAME");
		assertEquals("createdBy should have changed", "MY_NAME", _rrm.getCreatedBy());	
	}
	
	@Test
	public void testResourceRefCreatedAt() {
		// new() -> _rrm -> _rrm.setCreatedAt()
		ResourceRefModel _rrm = new ResourceRefModel();
		assertNull("createdAt should not be set by empty constructor", _rrm.getCreatedAt());
		_rrm.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _rrm.getCreatedAt());
	}
		
	@Test
	public void testResourceRefModifiedBy() {
		// new() -> _rrm -> _rrm.setModifiedBy()
		ResourceRefModel _rrm = new ResourceRefModel();
		assertNull("modifiedBy should not be set by empty constructor", _rrm.getModifiedBy());
		_rrm.setModifiedBy("MY_NAME");
		assertEquals("modifiedBy should have changed", "MY_NAME", _rrm.getModifiedBy());	
	}
	
	@Test
	public void testResourceRefModifiedAt() {
		// new() -> _rrm -> _rrm.setModifiedAt()
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
	public void testResourceRefCreateDeleteWithEmptyConstructor() {
		// new(1) -> _rrm1
		ResourceRefModel _rrm1 = new ResourceRefModel();
		assertNull("id should not be set by empty constructor", _rrm1.getId());
		assertNull("resourceId should not be set by empty constructor", _rrm1.getResourceId());
		assertNull("firstname should not be set by empty constructor", _rrm1.getFirstName());
		assertNull("lastname should not be set by empty constructor", _rrm1.getLastName());
		
		// create(_rrm1) -> BAD_REQUEST (because of empty resourceId)
		Response _response = webclient.replacePath("/").post(_rrm1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_rrm1.setResourceId("testResourceRefCreateDeleteWithEmptyConstructor");

		// create(_rrm1) -> BAD_REQUEST (because of empty firstName)
		_response = webclient.replacePath("/").post(_rrm1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_rrm1.setFirstName("MY_FNAME");

		// create(_rrm1) -> BAD_REQUEST (because of empty lastName)
		_response = webclient.replacePath("/").post(_rrm1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_rrm1.setLastName("MY_LNAME");

		// create(_rrm1) -> _rrm2
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).post(_rrm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _rrm2 = _response.readEntity(ResourceRefModel.class);
		
		// validate _rrm1 (local object)
		assertNull("id should still be null", _rrm1.getId());
		assertEquals("resourceId should be set by constructor", "testResourceRefCreateDeleteWithEmptyConstructor", _rrm1.getResourceId());
		assertEquals("firstname should be set by constructor", "MY_FNAME", _rrm1.getFirstName());
		assertEquals("lastname should be set by constructor", "MY_LNAME", _rrm1.getLastName());
		
		// validate _rrm2 (created object)
		assertNotNull("create() should set a valid id on the remote object returned", _rrm2.getId());		
		assertEquals("create() should not change resourceId", "testResourceRefCreateDeleteWithEmptyConstructor", _rrm2.getResourceId());
		assertEquals("create() should not change firstname", "MY_FNAME", _rrm2.getFirstName());
		assertEquals("create() should not change lastname", "MY_LNAME", _rrm2.getLastName());

		// delete(_rrm2)
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).path(_rrm2.getId()).delete();
		assertEquals("delete(" + _rrm2.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testResourceRefCreateDelete() {
		// new("MY_RESID", "MY_FNAME", "MY_LNAME")  -> _rrm1
		ResourceRefModel _rrm1 = createResourceRef("testResourceRefCreateDelete", 1);
		assertNull("id should be null", _rrm1.getId());
		assertEquals("resourceId should be set by constructor", "testResourceRefCreateDelete1", _rrm1.getResourceId());
		assertEquals("firstname should be set by constructor", "MY_FNAME1", _rrm1.getFirstName());
		assertEquals("lastname should be set by constructor", "MY_LNAME1", _rrm1.getLastName());
		// create(_rrm1) -> _rrm2
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).post(_rrm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _rrm2 = _response.readEntity(ResourceRefModel.class);
		
		assertNull("id should still be null", _rrm1.getId());
		assertEquals("resourceId should be unchanged after remote create", "testResourceRefCreateDelete1", _rrm1.getResourceId());
		assertEquals("firstname should be unchanged after remote create", "MY_FNAME1", _rrm1.getFirstName());
		assertEquals("lastname should be unchanged after remote create", "MY_LNAME1", _rrm1.getLastName());
		
		assertNotNull("create() should set a valid id on the remote object returned", _rrm2.getId());		
		assertEquals("resourceId of returned object should be unchanged after remote create", "testResourceRefCreateDelete1", _rrm2.getResourceId());
		assertEquals("firstname of returned object should be unchanged after remote create", "MY_FNAME1", _rrm2.getFirstName());
		assertEquals("lastname of returned object should be unchanged after remote create", "MY_LNAME1", _rrm2.getLastName());
		
		// delete(_rrm2)
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).path(_rrm2.getId()).delete();
		assertEquals("delete(" + _rrm2.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testResourceRefOnSubProject() {
		// create(new ProjectModel()) -> _pm
		Response _response = webclient.replacePath("/").path(company.getId())
				.path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT)
				.post(new ProjectModel("testResourceRefOnSubProject", "MY_DESC"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm = _response.readEntity(ProjectModel.class);
		
		// new ResourceRefModel(1) -> _rrm1
		ResourceRefModel _rrm1 = createResourceRef("testResourceRefOnSubProject", 1);
		assertNull("id should not be set by empty constructor", _rrm1.getId());
		assertEquals("resourceId should be set by constructor", "testResourceRefOnSubProject1", _rrm1.getResourceId());
		assertEquals("firstname should be set by constructor", "MY_FNAME1", _rrm1.getFirstName());
		assertEquals("lastname should be set by constructor", "MY_LNAME1", _rrm1.getLastName());
		
		// createResourceRef(_rrm1) on subproject _pm -> _rrm2
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm.getId()).path(PATH_EL_RESOURCE).post(_rrm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _rrm2 = _response.readEntity(ResourceRefModel.class);
		
		// validate _rrm1 (local object)
		assertNull("create() should not change the id of the local object", _rrm1.getId());
		assertEquals("create() should not change the resourceId", "testResourceRefOnSubProject1", _rrm1.getResourceId());
		assertEquals("create() should not change the firstname", "MY_FNAME1", _rrm1.getFirstName());
		assertEquals("create() should not change the lastname", "MY_LNAME1", _rrm1.getLastName());
		
		// validate _rrm2 (remote object returned)
		assertNotNull("create() should set a valid id on the remote object returned", _rrm2.getId());
		assertEquals("resourceId should not change the resourceId", "testResourceRefOnSubProject1", _rrm2.getResourceId());
		assertEquals("firstname should not change the firstname", "MY_FNAME1", _rrm2.getFirstName());
		assertEquals("lastname should not change the lastname", "MY_LNAME1", _rrm2.getLastName());
		
		// delete(_rrm2)
		 _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm.getId()).path(PATH_EL_RESOURCE).path(_rrm2.getId()).delete();
		assertEquals("delete(" + _rrm2.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());		

		// delete(_pm)
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_pm.getId()).delete();
		assertEquals("delete(" + _pm.getId() + ") should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateResourceRefWithDuplicateId() {
		// new(1) -> _rrm1
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT)
				.path(parentProject.getId()).path(PATH_EL_RESOURCE)
				.post(createResourceRef("testCreateResourceRefWithDuplicateId", 1));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _rrm1 = _response.readEntity(ResourceRefModel.class);

		// new() -> _rrm2 -> _rrm2.setId(_rrm.getId())
		ResourceRefModel _rrm2 = createResourceRef("testCreateResourceRefWithDuplicateId", 2);
		_rrm2.setId(_rrm1.getId());		// wrongly create a 2nd ResourceRefModel object with the same ID
		
		// create(_rrm2) -> CONFLICT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).post(_rrm2);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_rrm)
		 _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).path(_rrm1.getId()).delete();
		assertEquals("delete(" + _rrm1.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());		
	}
	
	@Test
	public void testResourceRefList(
	) {		
		ArrayList<ResourceRefModel> _localList = new ArrayList<ResourceRefModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			// create(new()) -> _localList
			_response = webclient.replacePath("/").path(company.getId())
					.path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE)
					.post(createResourceRef("testResourceRefList", i));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(ResourceRefModel.class));
		}
		
		// list(/) -> _remoteList
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).get();
		List<ResourceRefModel> _remoteList = new ArrayList<ResourceRefModel>(webclient.getCollection(ResourceRefModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (ResourceRefModel _rrm1 : _remoteList) {
			_remoteListIds.add(_rrm1.getId());
		}
		
		for (ResourceRefModel _rrm2 : _localList) {
			assertTrue("resource <" + _rrm2.getId() + "> should be listed", _remoteListIds.contains(_rrm2.getId()));
		}

		for (ResourceRefModel _rrm3 : _localList) {
			_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).path(_rrm3.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}

	@Test
	public void testResourceRefCreate() {
		// new(1) -> _rrm1
		ResourceRefModel _rrm1 = createResourceRef("testResourceRefCreate", 1);
		// new(2) -> _rrm2
		ResourceRefModel _rrm2 = createResourceRef("testResourceRefCreate", 2);
		
		// create(_rrm1)  -> _rrm3
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).post(_rrm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _rrm3 = _response.readEntity(ResourceRefModel.class);

		// create(_rrm2) -> _rrm4
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).post(_rrm2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _rrm4 = _response.readEntity(ResourceRefModel.class);		
		assertNotNull("ID should be set", _rrm3.getId());
		assertNotNull("ID should be set", _rrm4.getId());
		assertThat(_rrm4.getId(), not(equalTo(_rrm3.getId())));
		assertEquals("resourceId should be set correctly", "testResourceRefCreate1", _rrm3.getResourceId());
		assertEquals("firstname should be set correctly", "MY_FNAME1", _rrm3.getFirstName());
		assertEquals("lastname should be set correctly", "MY_LNAME1", _rrm3.getLastName());
		
		assertEquals("resourceId should be set correctly", "testResourceRefCreate2", _rrm4.getResourceId());
		assertEquals("firstname should be set correctly", "MY_FNAME2", _rrm4.getFirstName());
		assertEquals("lastname should be set correctly", "MY_LNAME2", _rrm4.getLastName());

		// delete(_rrm3) -> NO_CONTENT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).path(_rrm3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_rrm4) -> NO_CONTENT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).path(_rrm4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testResourceRefDoubleCreate(
	) {
		// create(1) -> _rrm
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE)
				.post(createResourceRef("testResourceRefDoubleCreate", 1));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _rrm = _response.readEntity(ResourceRefModel.class);
		assertEquals("ID should be unchanged", "testResourceRefDoubleCreate1", _rrm.getResourceId());		
		
		// create(_rrm) -> CONFLICT			// double create
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).post(_rrm);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_rrm) -> NO_CONTENT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).path(_rrm.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testResourceRefDelete(
	) {
		// create(1) -> _rrm
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE)
				.post(createResourceRef("testResourceRefDelete", 1));
		ResourceRefModel _rrm = _response.readEntity(ResourceRefModel.class);
		
		// removeResource(_rrm) -> NO_CONTENT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).path(_rrm.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		// removeResource _rrm -> NOT_FOUND
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).path(_rrm.getId()).delete();
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testResourceRefModifications() {
		// create(new ResourceRefModel()) -> _rrm
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE)
				.post(createResourceRef("testResourceRefModifications", 1));
		ResourceRefModel _rrm = _response.readEntity(ResourceRefModel.class);
		
		// test createdAt and createdBy
		assertNotNull("create() should set createdAt", _rrm.getCreatedAt());
		assertNotNull("create() should set createdBy", _rrm.getCreatedBy());
		// test modifiedAt and modifiedBy (= same as createdAt/createdBy)
		assertNotNull("create() should set modifiedAt", _rrm.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _rrm.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _rrm.getCreatedAt(), _rrm.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _rrm.getCreatedBy(), _rrm.getModifiedBy());
		
		// there is no update method for ResourceRef
		
		// delete(_rrm) -> NO_CONTENT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).path(_rrm.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	/********************************* helper methods *********************************/	
	public static ResourceRefModel createResourceRef(String id, int suffix) {
		return new ResourceRefModel(id + suffix, "MY_FNAME" + suffix, "MY_LNAME" + suffix);
	}
}
