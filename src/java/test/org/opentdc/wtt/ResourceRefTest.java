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
		Response _response = webclient.replacePath("/").post(new CompanyModel());
		company = _response.readEntity(CompanyModel.class);
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(new ProjectModel());
		parentProject = _response.readEntity(ProjectModel.class);
	}
	
	@After
	public void cleanupTest() {
		webclient.replacePath(company.getId()).delete();
		webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).delete();
	}

	/********************************** resourceRef tests *********************************/
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
		// new ProjectModel() -> _p1
		ProjectModel _p1 = new ProjectModel();
		// create(_p1) -> _p2
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).post(_p1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p2 = _response.readEntity(ProjectModel.class);
		// new ResourceRefModel() -> _c1
		ResourceRefModel _c1 = new ResourceRefModel();
		assertNull("id should not be set by empty constructor", _c1.getId());
		assertNull("resourceId should not be set by empty constructor", _c1.getResourceId());
		assertNull("firstname should not be set by empty constructor", _c1.getFirstName());
		assertNull("lastname should not be set by empty constructor", _c1.getLastName());
		// create(_c1) on subproject _p2 -> _c2
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_p2.getId()).path(PATH_EL_RESOURCE).post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _c2 = _response.readEntity(ResourceRefModel.class);
		assertNull("create() should not change the id of the local object", _c1.getId());
		assertNull("create() should not change the resourceId of the local object", _c1.getResourceId());
		assertNull("create() should not change the firstname of the local object", _c1.getFirstName());
		assertNull("create() should not change the lastname of the local object", _c1.getLastName());
		
		assertNotNull("create() should set a valid id on the remote object returned", _c2.getId());
		assertNull("resourceId should not change after remote create", _c2.getResourceId());
		assertNull("firstname should not change after remote create", _c2.getFirstName());
		assertNull("lastname should not change after remote create", _c2.getLastName());
		
		// delete(_c2)
		 _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_p2.getId()).path(PATH_EL_RESOURCE).path(_c2.getId()).delete();
		assertEquals("delete(" + _c2.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());		
		// delete(_p2)
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_p2.getId()).delete();
		assertEquals("delete(" + _p2.getId() + ") should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateResourceRefWithDuplicateId() {
		// new("MY_RESID1", "MY_FNAME1", "MY_LNAME1") -> _c2
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).post(createResRef("1"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _c2 = _response.readEntity(ResourceRefModel.class);

		// new() -> _c3 -> _c3.setId(_c2.getId())
		ResourceRefModel _c3 = new ResourceRefModel();
		_c3.setId(_c2.getId());		// wrongly create a 2nd ResourceRefModel object with the same ID
		
		// create(_c3) -> CONFLICT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).post(_c3);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());
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
		for (ResourceRefModel _c : _remoteList) {
			_remoteListIds.add(_c.getId());
		}
		
		for (ResourceRefModel _c : _localList) {
			assertTrue("resource <" + _c.getId() + "> should be listed", _remoteListIds.contains(_c.getId()));
		}

		for (ResourceRefModel _c : _localList) {
			_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).path(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}

	private ResourceRefModel createResRef(String suffix) {
		return new ResourceRefModel("MY_RESID" + suffix, "MY_FNAME" + suffix, "MY_LNAME" + suffix);
	}
	
	@Test
	public void testResourceRefCreate() {
		// new("MY_RESID1", "MY_FNAME1", "MY_LNAME1") -> _c1
		ResourceRefModel _c1 = createResRef("1");
		// new("MY_RESID2", "MY_FNAME2", "MY_LNAME2") -> _c2
		ResourceRefModel _c2 = createResRef("2");
		
		// create(_c1)  -> _c3
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _c3 = _response.readEntity(ResourceRefModel.class);

		// create(_c2) -> _c4
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).post(_c2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _c4 = _response.readEntity(ResourceRefModel.class);		
		assertNotNull("ID should be set", _c3.getId());
		assertNotNull("ID should be set", _c4.getId());
		assertThat(_c4.getId(), not(equalTo(_c3.getId())));
		assertEquals("resourceId should be set correctly", "MY_RESID1", _c3.getResourceId());
		assertEquals("firstname should be set correctly", "MY_FNAME1", _c3.getFirstName());
		assertEquals("lastname should be set correctly", "MY_LNAME1", _c3.getLastName());
		
		assertEquals("resourceId should be set correctly", "MY_RESID2", _c4.getResourceId());
		assertEquals("firstname should be set correctly", "MY_FNAME2", _c4.getFirstName());
		assertEquals("lastname should be set correctly", "MY_LNAME2", _c4.getLastName());

		// delete(_c3) -> NO_CONTENT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).path(_c3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_c4) -> NO_CONTENT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).path(_c4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testResourceRefDoubleCreate(
	) {
		// new("MY_RESID1", "MY_TITLE", "MY_DESC") -> _c1
		ResourceRefModel _c1 = createResRef("1");
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).post(_c1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ResourceRefModel _c2 = _response.readEntity(ResourceRefModel.class);
		assertEquals("ID should be unchanged", "MY_RESID1", _c2.getResourceId());		
		
		// create(_c2) -> CONFLICT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).post(_c2);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_c2) -> NO_CONTENT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).path(_c2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testResourceRefDelete(
	) {
		// new("MY_RESID1", "MY_TITLE", "MY_DESC") -> _c1
		ResourceRefModel _c1 = createResRef("1");
		
		// addResource(_c1) -> _c2
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).post(_c1);
		ResourceRefModel _c2 = _response.readEntity(ResourceRefModel.class);
		
		// removeResource(_c2) -> NO_CONTENT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).path(_c2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		// removeResource _c2 -> NOT_FOUND
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_RESOURCE).path(_c2.getId()).delete();
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
}
