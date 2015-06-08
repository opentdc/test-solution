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
		Response _response = webclient.replacePath("/").post(new CompanyModel("ResourceRefTest", "MY_DESC"));
		company = _response.readEntity(CompanyModel.class);
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(new ProjectModel());
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
		// new() -> _c
		ResourceRefModel _c = new ResourceRefModel();
		assertNull("id should not be set by empty constructor", _c.getId());
		assertNull("resourceId should not be set by empty constructor", _c.getResourceId());
		assertNull("firstname should not be set by empty constructor", _c.getFirstName());
		assertNull("lastname should not be set by empty constructor", _c.getLastName());
	}
	
	@Test
	public void testResourceRefModelConstructor() {		
		// new("MY_RESID", "MY_FNAME", "MY_LNAME") -> _c
		ResourceRefModel _c = new ResourceRefModel("MY_RESID", "MY_FNAME", "MY_LNAME");
		assertNull("id should be null", _c.getId());
		assertEquals("resourceId should be set correctly", "MY_RESID", _c.getResourceId());
		assertEquals("firstname should be set correctly", "MY_FNAME", _c.getFirstName());
		assertEquals("lastname should be set correctly", "MY_LNAME", _c.getLastName());
	}
	
	@Test
	public void testResourceRefIdAttributeChange() {
		// new() -> _c -> _c.setId()
		ResourceRefModel _c = new ResourceRefModel();
		assertNull("id should not be set by constructor", _c.getId());
		_c.setId("MY_ID");
		assertEquals("id should have changed:", "MY_ID", _c.getId());
	}
	
	@Test
	public void testResourceRefResourceIdAttributeChange() {
		// new() -> _c -> _c.setResourceId()
		ResourceRefModel _c = new ResourceRefModel();
		assertNull("resourceId should not be set by empty constructor", _c.getResourceId());
		_c.setResourceId("MY_RESID");
		assertEquals("resourceId should have changed:", "MY_RESID", _c.getResourceId());
	}
	
	@Test
	public void testResourceRefFirstNameAttributeChange() {
		// new() -> _c -> _c.setFirstName()
		ResourceRefModel _c = new ResourceRefModel();
		assertNull("firstname should not be set by empty constructor", _c.getFirstName());
		_c.setFirstName("MY_FNAME");
		assertEquals("firstname should have changed:", "MY_FNAME", _c.getFirstName());
	}
	
	@Test
	public void testResourceRefLastNameAttributeChange() {
		// new() -> _c -> _c.setLastName()
		ResourceRefModel _c = new ResourceRefModel();
		assertNull("lastname should not be set by empty constructor", _c.getLastName());
		_c.setLastName("MY_LNAME");
		assertEquals("lastname should have changed:", "MY_LNAME", _c.getLastName());
	}
	
	@Test
	public void testResourceRefCreatedBy() {
		// new() -> _o -> _o.setCreatedBy()
		ResourceRefModel _o = new ResourceRefModel();
		assertNull("createdBy should not be set by empty constructor", _o.getCreatedBy());
		_o.setCreatedBy("MY_NAME");
		assertEquals("createdBy should have changed", "MY_NAME", _o.getCreatedBy());	
	}
	
	@Test
	public void testResourceRefCreatedAt() {
		// new() -> _o -> _o.setCreatedAt()
		ResourceRefModel _o = new ResourceRefModel();
		assertNull("createdAt should not be set by empty constructor", _o.getCreatedAt());
		_o.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _o.getCreatedAt());
	}
		
	@Test
	public void testResourceRefModifiedBy() {
		// new() -> _o -> _o.setModifiedBy()
		ResourceRefModel _o = new ResourceRefModel();
		assertNull("modifiedBy should not be set by empty constructor", _o.getModifiedBy());
		_o.setModifiedBy("MY_NAME");
		assertEquals("modifiedBy should have changed", "MY_NAME", _o.getModifiedBy());	
	}
	
	@Test
	public void testResourceRefModifiedAt() {
		// new() -> _o -> _o.setModifiedAt()
		ResourceRefModel _o = new ResourceRefModel();
		assertNull("modifiedAt should not be set by empty constructor", _o.getModifiedAt());
		_o.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _o.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	// list: GET "api/company/{cid}/project/{pid}/resource"
	// add:  POST "api/company/{cid}/project/{pid}/resource"
	// delete:  DELETE "api/company/{cid}/project/{pid}/resource/{rid}"
	@Test
	public void testResourceRefCreateDeleteWithEmptyConstructor() {
		// new() -> _c1
		ResourceRefModel _c1 = new ResourceRefModel();
		assertNull("id should not be set by empty constructor", _c1.getId());
		assertNull("resourceId should not be set by empty constructor", _c1.getResourceId());
		assertNull("firstname should not be set by empty constructor", _c1.getFirstName());
		assertNull("lastname should not be set by empty constructor", _c1.getLastName());
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _c2 = _response.readEntity(ResourceRefModel.class);
		assertNull("id should still be null", _c1.getId());
		assertNull("resourceId should not be set by empty constructor", _c1.getResourceId());
		assertNull("firstname should not be set by empty constructor", _c1.getFirstName());
		assertNull("lastname should not be set by empty constructor", _c1.getLastName());
		
		assertNotNull("create() should set a valid id on the remote object returned", _c2.getId());		
		assertNull("resourceId should not be set by empty constructor", _c1.getResourceId());
		assertNull("firstname should not be set by empty constructor", _c1.getFirstName());
		assertNull("lastname should not be set by empty constructor", _c1.getLastName());

		// delete(_c2)
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).path(_c2.getId()).delete();
		assertEquals("delete(" + _c2.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testResourceRefCreateDelete() {
		// new("MY_RESID", "MY_FNAME", "MY_LNAME")  -> _c1
		ResourceRefModel _c1 = new ResourceRefModel("MY_RESID", "MY_FNAME", "MY_LNAME");
		assertNull("id should be null", _c1.getId());
		assertEquals("resourceId should be set by constructor", "MY_RESID", _c1.getResourceId());
		assertEquals("firstname should be set by constructor", "MY_FNAME", _c1.getFirstName());
		assertEquals("lastname should be set by constructor", "MY_LNAME", _c1.getLastName());
		// create(_c1) -> _c2
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _c2 = _response.readEntity(ResourceRefModel.class);
		
		assertNull("id should still be null", _c1.getId());
		assertEquals("resourceId should be unchanged after remote create", "MY_RESID", _c1.getResourceId());
		assertEquals("firstname should be unchanged after remote create", "MY_FNAME", _c1.getFirstName());
		assertEquals("lastname should be unchanged after remote create", "MY_LNAME", _c1.getLastName());
		
		assertNotNull("create() should set a valid id on the remote object returned", _c2.getId());		
		assertEquals("resourceId of returned object should be unchanged after remote create", "MY_RESID", _c2.getResourceId());
		assertEquals("firstname of returned object should be unchanged after remote create", "MY_FNAME", _c2.getFirstName());
		assertEquals("lastname of returned object should be unchanged after remote create", "MY_LNAME", _c2.getLastName());
		
		// delete(_c2)
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).path(_c2.getId()).delete();
		assertEquals("delete(" + _c2.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testResourceRefOnSubProject() {
		// create(new ProjectModel()) -> _pm
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).post(new ProjectModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm = _response.readEntity(ProjectModel.class);
		
		// new ResourceRefModel() -> _rrm
		ResourceRefModel _rrm = new ResourceRefModel();
		assertNull("id should not be set by empty constructor", _rrm.getId());
		assertNull("resourceId should not be set by empty constructor", _rrm.getResourceId());
		assertNull("firstname should not be set by empty constructor", _rrm.getFirstName());
		assertNull("lastname should not be set by empty constructor", _rrm.getLastName());
		
		// createResourceRef(_rrm) on subproject _p2 -> _c2
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm.getId()).path(PATH_EL_RESOURCE).post(_rrm);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _rrm2 = _response.readEntity(ResourceRefModel.class);
		
		assertNull("create() should not change the id of the local object", _rrm.getId());
		assertNull("create() should not change the resourceId of the local object", _rrm.getResourceId());
		assertNull("create() should not change the firstname of the local object", _rrm.getFirstName());
		assertNull("create() should not change the lastname of the local object", _rrm.getLastName());
		
		assertNotNull("create() should set a valid id on the remote object returned", _rrm2.getId());
		assertNull("resourceId should not change after remote create", _rrm2.getResourceId());
		assertNull("firstname should not change after remote create", _rrm2.getFirstName());
		assertNull("lastname should not change after remote create", _rrm2.getLastName());
		
		// delete(_c2)
		 _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm.getId()).path(PATH_EL_RESOURCE).path(_rrm2.getId()).delete();
		assertEquals("delete(" + _rrm2.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());		

		// delete(_p2)
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_pm.getId()).delete();
		assertEquals("delete(" + _pm.getId() + ") should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateResourceRefWithDuplicateId() {
		// new("MY_RESID1", "MY_FNAME1", "MY_LNAME1") -> _rrm
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).post(createResRef("1"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _rrm = _response.readEntity(ResourceRefModel.class);

		// new() -> _rrm2 -> _rrm2.setId(_rrm.getId())
		ResourceRefModel _rrm2 = new ResourceRefModel();
		_rrm2.setId(_rrm.getId());		// wrongly create a 2nd ResourceRefModel object with the same ID
		
		// create(_rrm2) -> CONFLICT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).post(_rrm2);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_rrm)
		 _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).path(_rrm.getId()).delete();
		assertEquals("delete(" + _rrm.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());		
	}
	
	@Test
	public void testResourceRefList(
	) {		
		ArrayList<ResourceRefModel> _localList = new ArrayList<ResourceRefModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			// create(new()) -> _localList
			_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).post(createResRef(new Integer(i).toString()));
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

	private ResourceRefModel createResRef(String suffix) {
		return new ResourceRefModel("MY_RESID" + suffix, "MY_FNAME" + suffix, "MY_LNAME" + suffix);
	}
	
	@Test
	public void testResourceRefCreate() {
		// new("MY_RESID1", "MY_FNAME1", "MY_LNAME1") -> _rrm1
		ResourceRefModel _rrm1 = createResRef("1");
		// new("MY_RESID2", "MY_FNAME2", "MY_LNAME2") -> _rrm2
		ResourceRefModel _rrm2 = createResRef("2");
		
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
		assertEquals("resourceId should be set correctly", "MY_RESID1", _rrm3.getResourceId());
		assertEquals("firstname should be set correctly", "MY_FNAME1", _rrm3.getFirstName());
		assertEquals("lastname should be set correctly", "MY_LNAME1", _rrm3.getLastName());
		
		assertEquals("resourceId should be set correctly", "MY_RESID2", _rrm4.getResourceId());
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
		// createResourceRef(new("MY_RESID1", "MY_TITLE", "MY_DESC")) -> _rrm
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).post(createResRef("1"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _rrm = _response.readEntity(ResourceRefModel.class);
		assertEquals("ID should be unchanged", "MY_RESID1", _rrm.getResourceId());		
		
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
		// createResourceRef(new("MY_RESID1", "MY_TITLE", "MY_DESC")) -> _rrm
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).post(createResRef("1"));
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
		// create(new ResourceRefModel()) -> _o
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).post(new ResourceRefModel());
		ResourceRefModel _o = _response.readEntity(ResourceRefModel.class);
		
		// test createdAt and createdBy
		assertNotNull("create() should set createdAt", _o.getCreatedAt());
		assertNotNull("create() should set createdBy", _o.getCreatedBy());
		// test modifiedAt and modifiedBy (= same as createdAt/createdBy)
		assertNotNull("create() should set modifiedAt", _o.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _o.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _o.getCreatedAt(), _o.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _o.getCreatedBy(), _o.getModifiedBy());
		
		// there is no update method for ResourceRef
		
		// delete(_o) -> NO_CONTENT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).path(_o.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
}
