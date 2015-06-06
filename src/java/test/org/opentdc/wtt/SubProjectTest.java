package test.org.opentdc.wtt;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

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

public class SubProjectTest  extends AbstractTestClient<WttService> {
	public static final String API = "api/company/";	
	
	public static final String PATH_EL_PROJECT = "project";
	
	private static CompanyModel company = null;
	private static ProjectModel parentProject = null;

	@Before
	public void initializeTest(
	) {
		initializeTest(API, WttService.class);
		Response _response = webclient.replacePath("/").post(new CompanyModel());
		company = _response.readEntity(CompanyModel.class);
		ProjectModel _pp = new ProjectModel();
		_pp.setTitle("Parent Project");
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(_pp);
		parentProject = _response.readEntity(ProjectModel.class);
	}

	@After
	public void cleanupTest() {
		webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).delete();
		webclient.replacePath("/").path(company.getId()).delete();
	}

	/********************************** subproject attributes tests *********************************/	
	// testing of attributes is not needed here, because subProject use the same Model as Projects
	
	/********************************** REST service tests *********************************/		
	// create:  POST p "api/company/{cid}/project/{pid}/project"
	// read:    GET "api/company/{cid}/project/{pid}/project/{spid}"
	// update:  PUT p "api/company/{cid}/project/{pid}/project/{spid}"
	// delete:  DELETE "api/company/{cid}/project/{pid}/project/{spid}"
	@Test
	public void testSubProjectCreateReadDeleteWithEmptyConstructor() {
		// new() -> _p1
		ProjectModel _p1 = new ProjectModel();
		// create(_p1) -> _p2
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).post(_p1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p2 = _response.readEntity(ProjectModel.class);
		System.out.println("testSubProjectCreateReadDeleteWithEmptyConstructor created " + _p2.getId());
		assertNull("create() should not change the id of the local object", _p1.getId());
		assertNull("create() should not change the title of the local object", _p1.getTitle());
		assertNull("create() should not change the description of the local object", _p1.getDescription());
		assertNotNull("create() should set a valid id on the remote object returned", _p2.getId());
		assertNull("title of returned object should be null after remote create", _p2.getTitle());
		assertNull("description of returned object should be null after remote create", _p2.getDescription());

		// read(_p2) -> _p3
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_p2.getId()).get();
		assertEquals("read(" + _p2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p3 = _response.readEntity(ProjectModel.class);
		assertEquals("id of returned object should be the same", _p2.getId(), _p3.getId());
		assertEquals("title of returned object should be unchanged after remote create", _p2.getTitle(), _p3.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", _p2.getDescription(), _p3.getDescription());

		// delete(_p3)
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_p3.getId()).delete();
		assertEquals("delete(" + _p3.getId() + ") should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		System.out.println("testSubProjectCreateReadDeleteWithEmptyConstructor deleted " + _p3.getId());
	}

	@Test
	public void testSubProjectCreateReadDelete() {
		// new("MY_TITLE", "MY_DESC") -> _p1
		ProjectModel _p1 = new ProjectModel("MY_TITLE", "MY_DESC");
		assertNull("id should not be set by constructor", _p1.getId());
		assertEquals("title should be set by constructor", "MY_TITLE", _p1.getTitle());
		assertEquals("description should be set by constructor", "MY_DESC", _p1.getDescription());
		// create(_p1) -> _p2
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).post(_p1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p2 = _response.readEntity(ProjectModel.class);
		System.out.println("testSubProjectCreateReadDelete created " + _p2.getId());
		assertNull("id should be null after remote create", _p1.getId());
		assertEquals("title should be unchanged after remote create", "MY_TITLE", _p1.getTitle());
		assertEquals("description should be unchanged after remote create", "MY_DESC", _p1.getDescription());
		assertNotNull("id of returned object should be set", _p2.getId());
		assertEquals("title of returned object should be unchanged after remote create", "MY_TITLE", _p2.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", "MY_DESC", _p2.getDescription());

		// read(_p2)  -> _p3
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_p2.getId()).get();
		assertEquals("read(" + _p2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p3 = _response.readEntity(ProjectModel.class);
		assertEquals("id of returned object should be the same", _p2.getId(), _p3.getId());
		assertEquals("title of returned object should be unchanged after remote create", _p2.getTitle(), _p3.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", _p2.getDescription(), _p3.getDescription());

		// delete(_p3)
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_p3.getId()).delete();
		assertEquals("delete(" + _p3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		System.out.println("testSubProjectCreateReadDelete deleted " + _p3.getId());
	}
	
	@Test
	public void testCreateSubProjectWithClientSideId() {
		// new() -> _p1 -> _p1.setId()
		ProjectModel _p1 = new ProjectModel();
		_p1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _p1.getId());
		// create(_p1) -> BAD_REQUEST
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).post(_p1);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateSubProjectWithDuplicateId() {
		// create(new()) -> _p2
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).post(new ProjectModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p2 = _response.readEntity(ProjectModel.class);
		System.out.println("testCreateSubProjectWithDuplicateId created " + _p2.getId());

		// new() -> _p3 -> _p3.setId(_p2.getId())
		ProjectModel _p3 = new ProjectModel();
		_p3.setId(_p2.getId());		// wrongly create a 2nd ProjectModel object with the same ID
		
		// create(_p3) -> CONFLICT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).post(_p3);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_p2)
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_p2.getId()).delete();
		assertEquals("delete(" + _p2.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		System.out.println("testCreateSubProjectWithDuplicateId deleted " + _p2.getId());
}
	
	// GET "api/company/{cid}/project/{pid}/project"
	@Test
	public void testSubProjectList() {
		ArrayList<ProjectModel> _localList = new ArrayList<ProjectModel>();		
		Response _response = null;
		ProjectModel _pm = null;
		for (int i = 0; i < LIMIT; i++) {
			// create(new()) -> _localList
			_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).post(new ProjectModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_pm = _response.readEntity(ProjectModel.class);
			_localList.add(_pm);
			System.out.println("testSubProjectList created " + _pm.getId());
		}
		
		// list(/) -> _remoteList
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).get();
		List<ProjectModel> _remoteList = new ArrayList<ProjectModel>(webclient.getCollection(ProjectModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (ProjectModel _p : _remoteList) {
			_remoteListIds.add(_p.getId());
		}
		
		for (ProjectModel _p : _localList) {
			assertTrue("project <" + _p.getId() + "> should be listed", _remoteListIds.contains(_p.getId()));
		}
		
		for (ProjectModel _p : _localList) {
			_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_p.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(ProjectModel.class);
		}
		
		for (ProjectModel _p : _localList) {
			_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_p.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
			System.out.println("testSubProjectList deleted " + _p.getId());
		}
	}

	@Test
	public void testSubProjectCreate() {	
		// new("MY_TITLE", "MY_DESC") -> _p1
		ProjectModel _p1 = new ProjectModel("MY_TITLE", "MY_DESC");
		// new("MY_TITLE2", "MY_DESC2") -> _p2
		ProjectModel _p2 = new ProjectModel("MY_TITLE2", "MY_DESC2");
		
		// create(_p1)  -> _p3
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).post(_p1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p3 = _response.readEntity(ProjectModel.class);
		System.out.println("testSubProjectCreate created " + _p3.getId());

		// create(_p2) -> _p4
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).post(_p2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p4 = _response.readEntity(ProjectModel.class);		
		System.out.println("testSubProjectCreate created " + _p4.getId());
		assertNotNull("ID should be set", _p3.getId());
		assertNotNull("ID should be set", _p4.getId());
		assertThat(_p4.getId(), not(equalTo(_p3.getId())));
		assertEquals("title1 should be set correctly", "MY_TITLE", _p3.getTitle());
		assertEquals("description1 should be set correctly", "MY_DESC", _p3.getDescription());
		assertEquals("title2 should be set correctly", "MY_TITLE2", _p4.getTitle());
		assertEquals("description2 should be set correctly", "MY_DESC2", _p4.getDescription());

		// delete(_p3) -> NO_CONTENT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_p3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		System.out.println("testSubProjectCreate deleted " + _p3.getId());

		// delete(_p4) -> NO_CONTENT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_p4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		System.out.println("testSubProjectCreate deleted " + _p4.getId());
	}
	
	@Test
	public void testSubProjectCreateDouble() {		
		// create(new()) -> _p
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).post(new ProjectModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p = _response.readEntity(ProjectModel.class);
		System.out.println("testSubProjectCreateDouble created " + _p.getId());
		assertNotNull("ID should be set:", _p.getId());		
		
		// create(_p) -> CONFLICT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).post(_p);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_p) -> NO_CONTENT
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_p.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		System.out.println("testSubProjectCreateDouble deleted " + _p.getId());
	}
	
	@Test
	public void testSubProjectRead() {
		ArrayList<ProjectModel> _localList = new ArrayList<ProjectModel>();
		Response _response = null;
		ProjectModel _pm = null;
		for (int i = 0; i < LIMIT; i++) {
			_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).post(new ProjectModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_pm = _response.readEntity(ProjectModel.class);
			_localList.add(_pm);
			System.out.println("testSubProjectRead created " + _pm.getId());			
		}
	
		// test read on each local element
		for (ProjectModel _p : _localList) {
			_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_p.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(ProjectModel.class);
		}

		// test read on each element within list()
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).get();
		List<ProjectModel> _remoteList = new ArrayList<ProjectModel>(webclient.getCollection(ProjectModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ProjectModel _tmpObj = null;
		for (ProjectModel _p : _remoteList) {
			_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_p.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(ProjectModel.class);
			assertEquals("ID should be unchanged when reading a project", _p.getId(), _tmpObj.getId());						
		}

		for (ProjectModel _p : _localList) {
			_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_p.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
			System.out.println("testSubProjectRead deleted " + _p.getId());			
		}
	}
		
	@Test
	public void testSubProjectMultiRead() {
		// new() -> _p1
		ProjectModel _p1 = new ProjectModel();
		
		// create(_p1) -> _p2
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).post(_p1);
		ProjectModel _p2 = _response.readEntity(ProjectModel.class);
		System.out.println("testSubProjectMultiRead created " + _p2.getId());			

		// read(_p2) -> _p3
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_p2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p3 = _response.readEntity(ProjectModel.class);
		assertEquals("ID should be unchanged after read:", _p2.getId(), _p3.getId());		

		// read(_p2) -> _p4
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_p2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p4 = _response.readEntity(ProjectModel.class);
		assertEquals("ID should be unchanged after read:", _p2.getId(), _p4.getId());		
		
		// but: the two objects are not equal !
		assertEquals("ID should be the same:", _p3.getId(), _p4.getId());
		assertEquals("title should be the same:", _p3.getTitle(), _p4.getTitle());
		assertEquals("description should be the same:", _p3.getDescription(), _p4.getDescription());
		
		assertEquals("ID should be the same:", _p3.getId(), _p2.getId());
		assertEquals("title should be the same:", _p3.getTitle(), _p2.getTitle());
		assertEquals("description should be the same:", _p3.getDescription(), _p2.getDescription());
		
		// delete(_p2)
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_p2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		System.out.println("testSubProjectMultiRead deleted " + _p2.getId());			
	}
	
	@Test
	public void testSubProjectUpdate() {
		// new() -> _p1
		ProjectModel _p1 = new ProjectModel();
		
		// create(_p1) -> _p2
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).post(_p1);
		ProjectModel _p2 = _response.readEntity(ProjectModel.class);
		System.out.println("testSubProjectUpdate created " + _p2.getId());			
		
		// change the attributes
		// update(_p2) -> _p3
		_p2.setTitle("MY_TITLE");
		_p2.setDescription("MY_DESC");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_p2.getId()).put(_p2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p3 = _response.readEntity(ProjectModel.class);

		assertNotNull("ID should be set", _p3.getId());
		assertEquals("ID should be unchanged", _p2.getId(), _p3.getId());	
		assertEquals("title should have changed", "MY_TITLE", _p3.getTitle());
		assertEquals("description should have changed", "MY_DESC", _p3.getDescription());

		// reset the attributes
		// update(_p2) -> _p4
		_p2.setTitle("MY_TITLE2");
		_p2.setDescription("MY_DESC2");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_p2.getId()).put(_p2);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _p4 = _response.readEntity(ProjectModel.class);

		assertNotNull("ID should be set", _p4.getId());
		assertEquals("ID should be unchanged", _p2.getId(), _p4.getId());	
		assertEquals("title should have changed", "MY_TITLE2", _p4.getTitle());
		assertEquals("description should have changed", "MY_DESC2", _p4.getDescription());
		
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_p2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		System.out.println("testSubProjectUpdate deleted " + _p2.getId());			
	}

	@Test
	public void testSubProjectDelete(
	) {
		// new() -> _c0
		ProjectModel _c0 = new ProjectModel();
		// create(_c0) -> _c1
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).post(_c0);
		ProjectModel _c1 = _response.readEntity(ProjectModel.class);
		System.out.println("testSubProjectDelete created " + _c1.getId());			
		
		// read(_c1) -> _tmpObj
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_c1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _tmpObj = _response.readEntity(ProjectModel.class);
		assertEquals("ID should be unchanged when reading a project (remote):", _c1.getId(), _tmpObj.getId());						
		
		// delete(_c1) -> OK
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_c1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		System.out.println("testSubProjectDelete deleted " + _c1.getId());			
	
		// read the deleted object twice
		// read(_c1) -> NOT_FOUND
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read(_c1) -> NOT_FOUND
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testSubProjectDoubleDelete() {
		// new() -> _c0
		ProjectModel _c0 = new ProjectModel();
		
		// create(_c0) -> _c1
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).post(_c0);
		ProjectModel _c1 = _response.readEntity(ProjectModel.class);
		System.out.println("testSubProjectDoubleDelete created " + _c1.getId());			

		// read(_c1) -> OK
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_c1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		// delete(_c1) -> OK
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_c1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		System.out.println("testSubProjectDoubleDelete deleted " + _c1.getId());			
		
		// read(_c1) -> NOT_FOUND
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// delete _c1 -> NOT_FOUND
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_c1.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read _c1 -> NOT_FOUND
		_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).path(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}

	/*
	@Test
	public void testSubProjectDeep() throws Exception {
			// create(_p1) -> _parentProject
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(parentProject.getId()).path(PATH_EL_PROJECT).post(new ProjectModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _parentProject = _response.readEntity(ProjectModel.class);
		String _parentProjectId = null;
		TreeMap<String, String> _projectTree = new TreeMap<String, String>();  // parentProject, clientProject
		
		// creating some subprojects in a deep structure
		for (int i = 0; i < LIMIT; i++) {
			_parentProjectId = _parentProject.getId();
			_parentProject = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_parentProject.getId()).
					path(PATH_EL_PROJECT).post(new ProjectModel()).readEntity(ProjectModel.class);
			// parentProjectId, clientProjectId
			_projectTree.put(_parentProjectId, _parentProject.getId());
			// System.out.println("created <" + _parentProjectId + ">/<" + _parentProject.getId() + ">");
		}
		
		// reading all subprojects recursively
		String _parentId = null;
		String _childId = null;
		
		Iterator<String> _iter = _projectTree.keySet().iterator();
		while (_iter.hasNext()) {
			_parentId = (String) _iter.next();
			_childId = _projectTree.get(_parentId);
			_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_parentId).path(PATH_EL_PROJECT).get();
			List<ProjectModel> _remoteList = new ArrayList<ProjectModel>(webclient.getCollection(ProjectModel.class));
			assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			assertEquals("list() should contain one single subproject", 1, _remoteList.size());
			// read(_p2) -> _p3
			_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_parentId).path(PATH_EL_PROJECT).path(_childId).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		}

		// delete all subprojects recursively  (iterating in reverse order)
		for (String _pId : _projectTree.descendingKeySet()) {
			_childId = _projectTree.get(_pId);
			// System.out.println("deleting <" + _pId + ">/<" + _childId + ">");
			if (! _projectTree.containsKey(_childId)) {
				_response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(_pId).path(PATH_EL_PROJECT).path(_childId).delete();		
				assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
				_projectTree.remove(_childId);
			}
		}
	}
	*/
}
