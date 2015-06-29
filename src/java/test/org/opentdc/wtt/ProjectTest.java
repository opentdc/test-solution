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
		Response _response = webclient.replacePath("/").post(new CompanyModel("ProjectTest", "MY_DESC"));
		company = _response.readEntity(CompanyModel.class);
	}
	
	@After
	public void cleanupTest() {
		webclient.replacePath("/").path(company.getId()).delete();
	}
	
	/********************************** project attributes tests *********************************/			
	@Test
	public void testProjectModelEmptyConstructor() {
		// new() -> _pm
		ProjectModel _pm = new ProjectModel();
		assertNull("id should not be set by empty constructor", _pm.getId());
		assertNull("title should not be set by empty constructor", _pm.getTitle());
		assertNull("description should not be set by empty constructor", _pm.getDescription());
	}
	
	@Test
	public void testProjectModelConstructor() {		
		// new("MY_TITLE", "MY_DESC") -> _pm
		ProjectModel _pm = new ProjectModel("MY_TITLE", "MY_DESC");
		assertNull("id should not be set by constructor", _pm.getId());
		assertEquals("title should be set by constructor", "MY_TITLE", _pm.getTitle());
		assertEquals("description should not set by constructor", "MY_DESC", _pm.getDescription());
	}
	
	@Test
	public void testProjectIdAttributeChange() {
		// new() -> _pm -> _pm.setId()
		ProjectModel _pm = new ProjectModel();
		assertNull("id should not be set by constructor", _pm.getId());
		_pm.setId("MY_ID");
		assertEquals("id should have changed:", "MY_ID", _pm.getId());
	}

	@Test
	public void testProjectTitleAttributeChange() {
		// new() -> _pm -> _pm.setTitle()
		ProjectModel _pm = new ProjectModel();
		assertNull("title should not be set by empty constructor", _pm.getTitle());
		_pm.setTitle("MY_TITLE");
		assertEquals("title should have changed:", "MY_TITLE", _pm.getTitle());
	}
	
	@Test
	public void testProjectDescriptionAttributeChange() {
		// new() -> _pm -> _pm.setDescription()
		ProjectModel _pm = new ProjectModel();
		assertNull("description should not be set by empty constructor", _pm.getDescription());
		_pm.setDescription("MY_DESC");
		assertEquals("description should have changed:", "MY_DESC", _pm.getDescription());
	}	
	
	@Test
	public void testProjectCreatedBy() {
		// new() -> _pm -> _pm.setCreatedBy()
		ProjectModel _pm = new ProjectModel();
		assertNull("createdBy should not be set by empty constructor", _pm.getCreatedBy());
		_pm.setCreatedBy("MY_NAME");
		assertEquals("createdBy should have changed", "MY_NAME", _pm.getCreatedBy());	
	}
	
	@Test
	public void testProjectCreatedAt() {
		// new() -> _pm -> _pm.setCreatedAt()
		ProjectModel _pm = new ProjectModel();
		assertNull("createdAt should not be set by empty constructor", _pm.getCreatedAt());
		_pm.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _pm.getCreatedAt());
	}
		
	@Test
	public void testProjectModifiedBy() {
		// new() -> _pm -> _pm.setModifiedBy()
		ProjectModel _pm = new ProjectModel();
		assertNull("modifiedBy should not be set by empty constructor", _pm.getModifiedBy());
		_pm.setModifiedBy("MY_NAME");
		assertEquals("modifiedBy should have changed", "MY_NAME", _pm.getModifiedBy());	
	}
	
	@Test
	public void testProjectModifiedAt() {
		// new() -> _pm -> _pm.setModifiedAt()
		ProjectModel _pm = new ProjectModel();
		assertNull("modifiedAt should not be set by empty constructor", _pm.getModifiedAt());
		_pm.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _pm.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	@Test
	public void testProjectCreateReadDeleteWithEmptyConstructor() {
		// new() -> _pm1
		ProjectModel _pm1 = new ProjectModel();
		assertNull("id should not be set by empty constructor", _pm1.getId());
		assertNull("title should not be set by empty constructor", _pm1.getTitle());
		assertNull("description should not be set by empty constructor", _pm1.getDescription());
		
		// create(_pm1) -> BAD_REQUEST (because of empty title)
		Response _response = webclient.replacePath("/").post(_pm1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_pm1.setTitle("testProjectCreateReadDeleteWithEmptyConstructor");

		// create(_pm1) -> _pm2
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_pm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm2 = _response.readEntity(ProjectModel.class);
		
		// validate _pm1 (local object)
		assertNull("create() should not change the id of the local object", _pm1.getId());
		assertEquals("create() should not change the title of the local object", "testProjectCreateReadDeleteWithEmptyConstructor", _pm1.getTitle());
		assertNull("create() should not change the description of the local object", _pm1.getDescription());
		
		// validate _pm2 (remote object returned from create())
		assertNotNull("create() should set a valid id on the remote object returned", _pm2.getId());
		assertEquals("create() should not change the title", "testProjectCreateReadDeleteWithEmptyConstructor", _pm2.getTitle());
		assertNull("create() should not change the description", _pm2.getDescription());
		
		// read(_pm2) -> _pm3
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm2.getId()).get();
		assertEquals("read(" + _pm2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm3 = _response.readEntity(ProjectModel.class);
		
		// validate _pm3 (remoted objecte returned from read())
		assertEquals("id of returned object should be the same", _pm2.getId(), _pm3.getId());
		assertEquals("title of returned object should be unchanged after remote create", _pm2.getTitle(), _pm3.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", _pm2.getDescription(), _pm3.getDescription());

		// delete(_pm3)
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm3.getId()).delete();
		assertEquals("delete(" + _pm3.getId() + ") should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testProjectCreateReadDelete() {
		// new(1) -> _pm1
		ProjectModel _pm1 = new ProjectModel("testProjectCreateReadDelete", "MY_DESC");
		assertNull("id should not be set by constructor", _pm1.getId());
		assertEquals("title should be set by constructor", "testProjectCreateReadDelete", _pm1.getTitle());
		assertEquals("description should be set by constructor", "MY_DESC", _pm1.getDescription());
		
		// create(_pm1) -> _pm2
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_pm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm2 = _response.readEntity(ProjectModel.class);
		
		// validate _pm1 (local object)
		assertNull("id should still be null after remote create", _pm1.getId());
		assertEquals("create() should not change the title", "testProjectCreateReadDelete", _pm1.getTitle());
		assertEquals("craete() should not change the description", "MY_DESC", _pm1.getDescription());
		
		// validate _pm2 (remote object returned from create())
		assertNotNull("id of returned object should be set", _pm2.getId());
		assertEquals("create() should not change the title", "testProjectCreateReadDelete", _pm2.getTitle());
		assertEquals("create() should not change the description", "MY_DESC", _pm2.getDescription());
		
		// read(_pm2)  -> _pm3
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm2.getId()).get();
		assertEquals("read(" + _pm2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm3 = _response.readEntity(ProjectModel.class);
		
		// validate _pm3 (remote object returned from read())
		assertEquals("read() should not change the id", _pm2.getId(), _pm3.getId());
		assertEquals("read() should not change the title", _pm2.getTitle(), _pm3.getTitle());
		assertEquals("read() should not change the description", _pm2.getDescription(), _pm3.getDescription());
		
		// delete(_pm3)
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm3.getId()).delete();
		assertEquals("delete(" + _pm3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateProjectWithClientSideId() {
		// new() -> _pm1 -> _pm1.setId()
		ProjectModel _pm1 = new ProjectModel("testCreateProjectWithClientSideId", "MY_DESC");
		_pm1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _pm1.getId());
		// create(_pm1) -> BAD_REQUEST
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_pm1);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateProjectWithDuplicateId() {
		// create(new()) -> _pm1
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT)
				.post(new ProjectModel("testCreateProjectWithDuplicateId1", "MY_DESC1"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm1 = _response.readEntity(ProjectModel.class);

		// new() -> _pm2 -> _pm2.setId(_pm1.getId())
		ProjectModel _pm2 = new ProjectModel("testCreateProjectWithDuplicateId2", "MY_DESC2");
		_pm2.setId(_pm1.getId());		// wrongly create a 2nd ProjectModel object with the same ID
		
		// create(_pm2) -> CONFLICT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_pm2);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testProjectList() {
		ArrayList<ProjectModel> _localList = new ArrayList<ProjectModel>();		
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			// create(new()) -> _localList
			_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT)
					.post(new ProjectModel("testProjectList" + i, "MY_DESC" + i));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(ProjectModel.class));
		}
		
		// list(/) -> _remoteList
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).get();
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		List<ProjectModel> _remoteList = new ArrayList<ProjectModel>(webclient.getCollection(ProjectModel.class));

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (ProjectModel _pm : _remoteList) {
			_remoteListIds.add(_pm.getId());
		}
		
		for (ProjectModel _pm : _localList) {
			assertTrue("project <" + _pm.getId() + "> should be listed", _remoteListIds.contains(_pm.getId()));
		}
		
		for (ProjectModel _pm : _localList) {
			_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(ProjectModel.class);
		}
		
		for (ProjectModel _pm : _localList) {
			_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}

	@Test
	public void testProjectCreate() {	
		// new(1) -> _pm1
		ProjectModel _pm1 = new ProjectModel("testProjectCreate1", "MY_DESC1");
		// new(2) -> _pm2
		ProjectModel _pm2 = new ProjectModel("testProjectCreate2", "MY_DESC2");
		
		// create(_pm1)  -> _pm3
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_pm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm3 = _response.readEntity(ProjectModel.class);

		// create(_pm2) -> _pm4
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_pm2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm4 = _response.readEntity(ProjectModel.class);
		
		// validate _pm3
		assertNotNull("ID should be set", _pm3.getId());
		assertEquals("title1 should be set correctly", "testProjectCreate1", _pm3.getTitle());
		assertEquals("description1 should be set correctly", "MY_DESC1", _pm3.getDescription());
		
		// validate _pm4
		assertNotNull("ID should be set", _pm4.getId());
		assertEquals("title2 should be set correctly", "testProjectCreate2", _pm4.getTitle());
		assertEquals("description2 should be set correctly", "MY_DESC2", _pm4.getDescription());

		assertThat(_pm4.getId(), not(equalTo(_pm3.getId())));

		// delete(_pm3) -> NO_CONTENT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_pm4) -> NO_CONTENT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testProjectCreateDouble() {		
		// create(new()) -> _pm
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT)
				.post(new ProjectModel("testProjectCreateDouble", "MY_DESC"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm = _response.readEntity(ProjectModel.class);
		assertNotNull("ID should be set:", _pm.getId());		
		
		// create(_pm) -> CONFLICT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_pm);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_pm) -> NO_CONTENT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testProjectRead() {
		ArrayList<ProjectModel> _localList = new ArrayList<ProjectModel>();
		Response _response = null;
		webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT);
		for (int i = 0; i < LIMIT; i++) {
			_response = webclient.post(new ProjectModel("testProjectRead" + i, "MY_DESC" + i));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(ProjectModel.class));
		}
	
		// test read on each local element
		for (ProjectModel _pm : _localList) {
			_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(ProjectModel.class);
		}

		// test read on each listed element
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).get();
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		List<ProjectModel> _remoteList = new ArrayList<ProjectModel>(webclient.getCollection(ProjectModel.class));

		ProjectModel _tmpObj = null;
		for (ProjectModel _pm : _remoteList) {
			_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(ProjectModel.class);
			assertEquals("ID should be unchanged when reading a project", _pm.getId(), _tmpObj.getId());						
		}

		for (ProjectModel _pm : _localList) {
			_response = webclient.replacePath(_pm.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}
		
	@Test
	public void testProjectMultiRead() {
		// new() -> _pm1
		ProjectModel _pm1 = new ProjectModel("testProjectMultiRead", "MY_DESC");
		
		// create(_pm1) -> _p2
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_pm1);
		ProjectModel _pm2 = _response.readEntity(ProjectModel.class);

		// read(_pm2) -> _pm3
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm3 = _response.readEntity(ProjectModel.class);
		assertEquals("ID should be unchanged after read:", _pm2.getId(), _pm3.getId());		

		// read(_pm2) -> _pm4
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm4 = _response.readEntity(ProjectModel.class);
		
		// but: the two objects are not equal !
		assertEquals("ID should be the same:", _pm3.getId(), _pm4.getId());
		assertEquals("title should be the same:", _pm3.getTitle(), _pm4.getTitle());
		assertEquals("description should be the same:", _pm3.getDescription(), _pm4.getDescription());
		
		assertEquals("ID should be the same:", _pm3.getId(), _pm2.getId());
		assertEquals("title should be the same:", _pm3.getTitle(), _pm2.getTitle());
		assertEquals("description should be the same:", _pm3.getDescription(), _pm2.getDescription());
		
		// delete(_pm2)
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testProjectUpdate() {
		// new() -> _pm1
		ProjectModel _pm1 = new ProjectModel("testProjectUpdate", "MY_DESC");
		
		// create(_pm1) -> _pm2
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_pm1);
		ProjectModel _pm2 = _response.readEntity(ProjectModel.class);
		
		// change the attributes
		// update(_pm2) -> _pm3
		_pm2.setTitle("MY_TITLE");
		_pm2.setDescription("MY_DESC");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm2.getId()).put(_pm2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm3 = _response.readEntity(ProjectModel.class);

		assertNotNull("ID should be set", _pm3.getId());
		assertEquals("ID should be unchanged", _pm2.getId(), _pm3.getId());	
		assertEquals("title should have changed", "MY_TITLE", _pm3.getTitle());
		assertEquals("description should have changed", "MY_DESC", _pm3.getDescription());

		// reset the attributes
		// update(_pm2) -> _pm4
		_pm2.setTitle("MY_TITLE2");
		_pm2.setDescription("MY_DESC2");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm2.getId()).put(_pm2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm4 = _response.readEntity(ProjectModel.class);

		assertNotNull("ID should be set", _pm4.getId());
		assertEquals("ID should be unchanged", _pm2.getId(), _pm4.getId());	
		assertEquals("title should have changed", "MY_TITLE2", _pm4.getTitle());
		assertEquals("description should have changed", "MY_DESC2", _pm4.getDescription());
		
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testProjectDelete(
	) {
		// new() -> _pm1
		ProjectModel _pm1 = new ProjectModel("testProjectDelete", "MY_DESC");
		// create(_pm1) -> _pm2
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_pm1);
		ProjectModel _pm2 = _response.readEntity(ProjectModel.class);
		
		// read(_pm2) -> _pm3
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm3 = _response.readEntity(ProjectModel.class);
		assertEquals("ID should be unchanged when reading a project (remote):", _pm2.getId(), _pm3.getId());						
		
		// delete(_pm2) -> OK
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	
		// read the deleted object twice
		// read(_pm2) -> NOT_FOUND
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read(_pm2) -> NOT_FOUND
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testProjectDoubleDelete() {
		// new() -> _pm1
		ProjectModel _pm1 = new ProjectModel("testProjectDoubleDelete", "MY_DESC");
		
		// create(_pm1) -> _pm2
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_pm1);
		ProjectModel _pm2 = _response.readEntity(ProjectModel.class);

		// read(_pm2) -> OK
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		// delete(_pm2) -> OK
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm2.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		// read(_pm2) -> NOT_FOUND
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// delete _pm2 -> NOT_FOUND
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm2.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read _pm2 -> NOT_FOUND
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm2.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testProjectModifications() {
		// create(new ProjectModel()) -> _pm1
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT)
				.post(new ProjectModel("testProjectModifications", "MY_DESC"));
		ProjectModel _pm1 = _response.readEntity(ProjectModel.class);
		
		// test createdAt and createdBy
		assertNotNull("create() should set createdAt", _pm1.getCreatedAt());
		assertNotNull("create() should set createdBy", _pm1.getCreatedBy());
		// test modifiedAt and modifiedBy (= same as createdAt/createdBy)
		assertNotNull("create() should set modifiedAt", _pm1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _pm1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _pm1.getCreatedAt(), _pm1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _pm1.getCreatedBy(), _pm1.getModifiedBy());
		
		// update(_pm1)  -> _pm2
		_pm1.setTitle("NEW_TITLE");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm1.getId()).put(_pm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm2 = _response.readEntity(ProjectModel.class);

		// test createdAt and createdBy (unchanged)
		assertEquals("update() should not change createdAt", _pm1.getCreatedAt(), _pm2.getCreatedAt());
		assertEquals("update() should not change createdBy", _pm1.getCreatedBy(), _pm2.getCreatedBy());
		
		// test modifiedAt and modifiedBy (= different from createdAt/createdBy)
		assertTrue(_pm2.getModifiedAt().compareTo(_pm2.getCreatedAt()) >= 0);
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_pm2.getModifiedBy(), not(equalTo(_pm2.getCreatedBy())));

		// update(_pm1) with modifiedBy/At set on client side -> ignored by server
		_pm1.setModifiedBy("MYSELF");
		_pm1.setModifiedAt(new Date(1000));
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm1.getId()).put(_pm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm3 = _response.readEntity(ProjectModel.class);
		
		// test, that modifiedBy really ignored the client-side value "MYSELF"
		assertThat(_pm1.getModifiedBy(), not(equalTo(_pm3.getModifiedBy())));
		// check whether the client-side modifiedAt() is ignored
		assertThat(_pm1.getModifiedAt(), not(equalTo(_pm3.getModifiedAt())));
		
		// delete(_o) -> NO_CONTENT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pm1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
}
