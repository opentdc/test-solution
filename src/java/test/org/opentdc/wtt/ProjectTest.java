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

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.ProjectModel;
import org.opentdc.wtt.WttService;

import test.org.opentdc.AbstractTestClient;

public class ProjectTest extends AbstractTestClient<WttService> {
		
	public static final String API = "api/company/";	
	public static final String PATH_EL_PROJECT = "project";
	private static CompanyModel company = null;

	@Before
	public void initializeTest() {
		initializeTest(API, WttService.class);
		Response _response = webclient.replacePath("/").post(new CompanyModel());
		company = _response.readEntity(CompanyModel.class);
	}
	
	@After
	public void cleanupTest() {
		webclient.replacePath("/").path(company.getId()).delete();
	}
	
	/********************************** project tests *********************************/			
	@Test
	public void testProjectModelEmptyConstructor() {
		// new() -> _p
		ProjectModel _p = new ProjectModel();
		assertNull("id should not be set by empty constructor", _p.getId());
		assertNull("title should not be set by empty constructor", _p.getTitle());
		assertNull("description should not be set by empty constructor", _p.getDescription());
	}
	
	@Test
	public void testProjectModelConstructor() {		
		// new("MY_TITLE", "MY_DESC") -> _p
		ProjectModel _p = new ProjectModel("MY_TITLE", "MY_DESC");
		assertNull("id should not be set by constructor", _p.getId());
		assertEquals("title should be set by constructor", "MY_TITLE", _p.getTitle());
		assertEquals("description should not set by constructor", "MY_DESC", _p.getDescription());
	}
	
	@Test
	public void testProjectIdAttributeChange() {
		// new() -> _p -> _p.setId()
		ProjectModel _p = new ProjectModel();
		assertNull("id should not be set by constructor", _p.getId());
		_p.setId("MY_ID");
		assertEquals("id should have changed:", "MY_ID", _p.getId());
	}

	@Test
	public void testProjectTitleAttributeChange() {
		// new() -> _p -> _p.setTitle()
		ProjectModel _p = new ProjectModel();
		assertNull("title should not be set by empty constructor", _p.getTitle());
		_p.setTitle("MY_TITLE");
		assertEquals("title should have changed:", "MY_TITLE", _p.getTitle());
	}
	
	@Test
	public void testProjectDescriptionAttributeChange() {
		// new() -> _p -> _p.setDescription()
		ProjectModel _p = new ProjectModel();
		assertNull("description should not be set by empty constructor", _p.getDescription());
		_p.setDescription("MY_DESC");
		assertEquals("description should have changed:", "MY_DESC", _p.getDescription());
	}	
	
	@Test
	public void testProjectCreateReadDeleteWithEmptyConstructor() {
		// new() -> _p1
		ProjectModel _p1 = new ProjectModel();
		assertNull("id should not be set by empty constructor", _p1.getId());
		assertNull("title should not be set by empty constructor", _p1.getTitle());
		assertNull("description should not be set by empty constructor", _p1.getDescription());
		// create(_p1) -> _p2
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_p1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p2 = _response.readEntity(ProjectModel.class);
		assertNull("create() should not change the id of the local object", _p1.getId());
		assertNull("create() should not change the title of the local object", _p1.getTitle());
		assertNull("create() should not change the description of the local object", _p1.getDescription());
		assertNotNull("create() should set a valid id on the remote object returned", _p2.getId());
		assertNull("title of returned object should still be null after remote create", _p2.getTitle());
		assertNull("description of returned object should still be null after remote create", _p2.getDescription());
		// read(_p2) -> _p3
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_p2.getId()).get();
		assertEquals("read(" + _p2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p3 = _response.readEntity(ProjectModel.class);
		assertEquals("id of returned object should be the same", _p2.getId(), _p3.getId());
		assertEquals("title of returned object should be unchanged after remote create", _p2.getTitle(), _p3.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", _p2.getDescription(), _p3.getDescription());
		// delete(_p3)
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_p3.getId()).delete();
		assertEquals("delete(" + _p3.getId() + ") should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testProjectCreateReadDelete() {
		// new("MY_TITLE", "MY_DESC") -> _p1
		ProjectModel _p1 = new ProjectModel("MY_TITLE", "MY_DESC");
		assertNull("id should not be set by constructor", _p1.getId());
		assertEquals("title should be set by constructor", "MY_TITLE", _p1.getTitle());
		assertEquals("description should be set by constructor", "MY_DESC", _p1.getDescription());
		// create(_p1) -> _p2
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_p1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p2 = _response.readEntity(ProjectModel.class);
		assertNull("id should still be null after remote create", _p1.getId());
		assertEquals("title should be unchanged after remote create", "MY_TITLE", _p1.getTitle());
		assertEquals("description should be unchanged after remote create", "MY_DESC", _p1.getDescription());
		assertNotNull("id of returned object should be set", _p2.getId());
		assertEquals("title of returned object should be unchanged after remote create", "MY_TITLE", _p2.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", "MY_DESC", _p2.getDescription());
		// read(_p2)  -> _p3
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_p2.getId()).get();
		assertEquals("read(" + _p2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p3 = _response.readEntity(ProjectModel.class);
		assertEquals("id of returned object should be the same", _p2.getId(), _p3.getId());
		assertEquals("title of returned object should be unchanged after remote create", _p2.getTitle(), _p3.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", _p2.getDescription(), _p3.getDescription());
		// delete(_p3)
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_p3.getId()).delete();
		assertEquals("delete(" + _p3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateProjectWithClientSideId() {
		// new() -> _p1 -> _p1.setId()
		ProjectModel _p1 = new ProjectModel();
		_p1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _p1.getId());
		// create(_p1) -> BAD_REQUEST
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_p1);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateProjectWithDuplicateId() {
		// create(new()) -> _p2
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(new ProjectModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p2 = _response.readEntity(ProjectModel.class);

		// new() -> _p3 -> _p3.setId(_p2.getId())
		ProjectModel _p3 = new ProjectModel();
		_p3.setId(_p2.getId());		// wrongly create a 2nd ProjectModel object with the same ID
		
		// create(_p3) -> CONFLICT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_p3);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testProjectList() {
		ArrayList<ProjectModel> _localList = new ArrayList<ProjectModel>();		
		Response _response = null;
		webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT);
		for (int i = 0; i < LIMIT; i++) {
			// create(new()) -> _localList
			_response = webclient.post(new ProjectModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(ProjectModel.class));
		}
		
		// list(/) -> _remoteList
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).get();
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		List<ProjectModel> _remoteList = new ArrayList<ProjectModel>(webclient.getCollection(ProjectModel.class));

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (ProjectModel _p : _remoteList) {
			_remoteListIds.add(_p.getId());
		}
		
		for (ProjectModel _p : _localList) {
			assertTrue("project <" + _p.getId() + "> should be listed", _remoteListIds.contains(_p.getId()));
		}
		
		for (ProjectModel _p : _localList) {
			_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_p.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(ProjectModel.class);
		}
		
		for (ProjectModel _p : _localList) {
			_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_p.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}

	@Test
	public void testProjectCreate() {	
		// new("MY_TITLE", "MY_DESC") -> _p1
		ProjectModel _p1 = new ProjectModel("MY_TITLE", "MY_DESC");
		// new("MY_TITLE2", "MY_DESC2") -> _p2
		ProjectModel _p2 = new ProjectModel("MY_TITLE2", "MY_DESC2");
		
		// create(_p1)  -> _p3
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_p1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p3 = _response.readEntity(ProjectModel.class);

		// create(_p2) -> _p4
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_p2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p4 = _response.readEntity(ProjectModel.class);		
		assertNotNull("ID should be set", _p3.getId());
		assertNotNull("ID should be set", _p4.getId());
		assertThat(_p4.getId(), not(equalTo(_p3.getId())));
		assertEquals("title1 should be set correctly", "MY_TITLE", _p3.getTitle());
		assertEquals("description1 should be set correctly", "MY_DESC", _p3.getDescription());
		assertEquals("title2 should be set correctly", "MY_TITLE2", _p4.getTitle());
		assertEquals("description2 should be set correctly", "MY_DESC2", _p4.getDescription());

		// delete(_p3) -> NO_CONTENT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_p3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_p4) -> NO_CONTENT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_p4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testProjectCreateDouble() {		
		// create(new()) -> _p
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(new ProjectModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p = _response.readEntity(ProjectModel.class);
		assertNotNull("ID should be set:", _p.getId());		
		
		// create(_p) -> CONFLICT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_p);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_p) -> NO_CONTENT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_p.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testProjectRead() {
		ArrayList<ProjectModel> _localList = new ArrayList<ProjectModel>();
		Response _response = null;
		webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT);
		for (int i = 0; i < LIMIT; i++) {
			_response = webclient.post(new ProjectModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(ProjectModel.class));
		}
	
		// test read on each local element
		for (ProjectModel _p : _localList) {
			_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_p.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(ProjectModel.class);
		}

		// test read on each listed element
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).get();
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		List<ProjectModel> _remoteList = new ArrayList<ProjectModel>(webclient.getCollection(ProjectModel.class));

		ProjectModel _tmpObj = null;
		for (ProjectModel _p : _remoteList) {
			_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_p.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(ProjectModel.class);
			assertEquals("ID should be unchanged when reading a project", _p.getId(), _tmpObj.getId());						
		}

		// TODO: "reading a project with ID = null should fail" -> ValidationException
		// TODO: "reading a non-existing project should fail"

		for (ProjectModel _p : _localList) {
			_response = webclient.replacePath(_p.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}
		
	@Test
	public void testProjectMultiRead() {
		// new() -> _p1
		ProjectModel _p1 = new ProjectModel();
		
		// create(_p1) -> _p2
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_p1);
		ProjectModel _p2 = _response.readEntity(ProjectModel.class);

		// read(_p2) -> _p3
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_p2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p3 = _response.readEntity(ProjectModel.class);
		assertEquals("ID should be unchanged after read:", _p2.getId(), _p3.getId());		

		// read(_p2) -> _p4
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_p2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p4 = _response.readEntity(ProjectModel.class);
		
		// but: the two objects are not equal !
		assertEquals("ID should be the same:", _p3.getId(), _p4.getId());
		assertEquals("title should be the same:", _p3.getTitle(), _p4.getTitle());
		assertEquals("description should be the same:", _p3.getDescription(), _p4.getDescription());
		
		assertEquals("ID should be the same:", _p3.getId(), _p2.getId());
		assertEquals("title should be the same:", _p3.getTitle(), _p2.getTitle());
		assertEquals("description should be the same:", _p3.getDescription(), _p2.getDescription());
		
		// delete(_p2)
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_p2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testProjectUpdate() {
		// new() -> _p1
		ProjectModel _p1 = new ProjectModel();
		
		// create(_p1) -> _p2
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_p1);
		ProjectModel _p2 = _response.readEntity(ProjectModel.class);
		
		// change the attributes
		// update(_p2) -> _p3
		_p2.setTitle("MY_TITLE");
		_p2.setDescription("MY_DESC");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_p2.getId()).put(_p2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p3 = _response.readEntity(ProjectModel.class);

		assertNotNull("ID should be set", _p3.getId());
		assertEquals("ID should be unchanged", _p2.getId(), _p3.getId());	
		assertEquals("title should have changed", "MY_TITLE", _p3.getTitle());
		assertEquals("description should have changed", "MY_DESC", _p3.getDescription());

		// reset the attributes
		// update(_c2) -> _p4
		_p2.setTitle("MY_TITLE2");
		_p2.setDescription("MY_DESC2");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_p2.getId()).put(_p2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p4 = _response.readEntity(ProjectModel.class);

		assertNotNull("ID should be set", _p4.getId());
		assertEquals("ID should be unchanged", _p2.getId(), _p4.getId());	
		assertEquals("title should have changed", "MY_TITLE2", _p4.getTitle());
		assertEquals("description should have changed", "MY_DESC2", _p4.getDescription());
		
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_p2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testProjectDelete(
	) {
		// new() -> _c0
		ProjectModel _c0 = new ProjectModel();
		// create(_c0) -> _c1
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_c0);
		ProjectModel _c1 = _response.readEntity(ProjectModel.class);
		
		// read(_c0) -> _tmpObj
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_c1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _tmpObj = _response.readEntity(ProjectModel.class);
		assertEquals("ID should be unchanged when reading a project (remote):", _c1.getId(), _tmpObj.getId());						

		// read(_c1) -> _tmpObj
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_c1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		_tmpObj = _response.readEntity(ProjectModel.class);
		assertEquals("ID should be unchanged when reading a project (remote):", _c1.getId(), _tmpObj.getId());						
		
		// delete(_c1) -> OK
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_c1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	
		// read the deleted object twice
		// read(_c1) -> NOT_FOUND
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read(_c1) -> NOT_FOUND
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testProjectDoubleDelete() {
		// new() -> _c0
		ProjectModel _c0 = new ProjectModel();
		
		// create(_c0) -> _c1
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_c0);
		ProjectModel _c1 = _response.readEntity(ProjectModel.class);

		// read(_c1) -> OK
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_c1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		// delete(_c1) -> OK
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_c1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		// read(_c1) -> NOT_FOUND
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// delete _c1 -> NOT_FOUND
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_c1.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read _c1 -> NOT_FOUND
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
}
