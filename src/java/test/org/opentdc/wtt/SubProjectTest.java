package test.org.opentdc.wtt;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.service.ServiceUtil;
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.ProjectModel;
import org.opentdc.wtt.WttService;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.addressbooks.AddressbookTest;

public class SubProjectTest  extends AbstractTestClient {		
	private WebClient wttWC = null;
	private WebClient addressbookWC = null;
	private CompanyModel company = null;
	private ProjectModel parentProject = null;
	private AddressbookModel addressbook = null;

	@Before
	public void initializeTest() {
		wttWC = createWebClient(ServiceUtil.WTT_API_URL, WttService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);

		addressbook = AddressbookTest.createAddressbook(addressbookWC, this.getClass().getName(), Status.OK);
		company = CompanyTest.createCompany(wttWC, addressbookWC, addressbook, this.getClass().getName(), "MY_DESC");
		parentProject = ProjectTest.createProject(wttWC, company.getId(), this.getClass().getName(), "MY_DESC");
	}

	@After
	public void cleanupTest() {
		AddressbookTest.delete(addressbookWC, addressbook.getId(), Status.NO_CONTENT);
		System.out.println("deleted 1 addressbook");
		addressbookWC.close();
		CompanyTest.cleanup(wttWC, company.getId(), this.getClass().getName());
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
		// new() -> _pm1
		ProjectModel _pm1 = new ProjectModel();
		
		// create(_pm1) -> BAD_REQUEST (because of empty title)
		Response _response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).post(_pm1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_pm1.setTitle("testSubProjectCreateReadDeleteWithEmptyConstructor");

		// create(_pm1) -> _pm2
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).post(_pm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm2 = _response.readEntity(ProjectModel.class);
		
		// validate _pm1 (local object)
		assertNull("create() should not change the id", _pm1.getId());
		assertEquals("create() should not change the title", "testSubProjectCreateReadDeleteWithEmptyConstructor", _pm1.getTitle());
		assertNull("create() should not change the description", _pm1.getDescription());
		
		// validate _pm2 (remote object returned from create())
		assertNotNull("create() should set a valid id on the remote object returned", _pm2.getId());
		assertEquals("create() should not change the title", "testSubProjectCreateReadDeleteWithEmptyConstructor", _pm2.getTitle());
		assertNull("create() should not change the description", _pm2.getDescription());

		// read(_pm2) -> _pm3
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm2.getId()).get();
		assertEquals("read(" + _pm2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm3 = _response.readEntity(ProjectModel.class);
		
		// validate _pm3 (remote object returned from read())
		assertEquals("read() should not change the id", _pm2.getId(), _pm3.getId());
		assertEquals("read() should not change the title", _pm2.getTitle(), _pm3.getTitle());
		assertEquals("read() should not change the description", _pm2.getDescription(), _pm3.getDescription());

		// delete(_pm3)
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm3.getId()).delete();
		assertEquals("delete(" + _pm3.getId() + ") should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testSubProjectCreateReadDelete() {
		// new() -> _pm1
		ProjectModel _pm1 = new ProjectModel("testSubProjectCreateReadDelete", "MY_DESC");
		assertNull("id should not be set by constructor", _pm1.getId());
		assertEquals("title should be set by constructor", "testSubProjectCreateReadDelete", _pm1.getTitle());
		assertEquals("description should be set by constructor", "MY_DESC", _pm1.getDescription());
		// create(_pm1) -> _pm2
		Response _response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).post(_pm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm2 = _response.readEntity(ProjectModel.class);

		assertNull("id should be null after remote create", _pm1.getId());
		assertEquals("title should be unchanged after remote create", "testSubProjectCreateReadDelete", _pm1.getTitle());
		assertEquals("description should be unchanged after remote create", "MY_DESC", _pm1.getDescription());
		assertNotNull("id of returned object should be set", _pm2.getId());
		assertEquals("title of returned object should be unchanged after remote create", "testSubProjectCreateReadDelete", _pm2.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", "MY_DESC", _pm2.getDescription());

		// read(_pm2)  -> _pm3
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm2.getId()).get();
		assertEquals("read(" + _pm2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm3 = _response.readEntity(ProjectModel.class);
		assertEquals("id of returned object should be the same", _pm2.getId(), _pm3.getId());
		assertEquals("title of returned object should be unchanged after remote create", _pm2.getTitle(), _pm3.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", _pm2.getDescription(), _pm3.getDescription());

		// delete(_pm3)
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm3.getId()).delete();
		assertEquals("delete(" + _pm3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		System.out.println("testSubProjectCreateReadDelete deleted " + _pm3.getId());
	}
	
	@Test
	public void testCreateSubProjectWithClientSideId() {
		// new() -> _pm1 -> _pm1.setId()
		ProjectModel _pm1 = new ProjectModel("testCreateSubProjectWithClientSideId", "MY_DESC");
		_pm1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _pm1.getId());
		// create(_pm1) -> BAD_REQUEST
		Response _response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).post(_pm1);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testCreateSubProjectWithDuplicateId() {
		// create(new()) -> _pm1
		Response _response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).
				post(new ProjectModel("testCreateSubProjectWithDuplicateId", "MY_DESC"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm1 = _response.readEntity(ProjectModel.class);
		System.out.println("testCreateSubProjectWithDuplicateId created " + _pm1.getId());

		// new() -> _pm2 -> _pm2.setId(_pm1.getId())
		ProjectModel _pm2 = new ProjectModel("testCreateSubProjectWithClientSideId2", "MY_DESC2");
		_pm2.setId(_pm1.getId());		// wrongly create a 2nd ProjectModel object with the same ID
		
		// create(_pm2) -> CONFLICT
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).post(_pm2);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_pm1)
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm1.getId()).delete();
		assertEquals("delete(" + _pm1.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		System.out.println("testCreateSubProjectWithDuplicateId deleted " + _pm1.getId());
}
	
	// GET "api/company/{cid}/project/{pid}/project"
	@Test
	public void testSubProjectList() {
		ArrayList<ProjectModel> _localList = new ArrayList<ProjectModel>();		
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			// create(new()) -> _localList
			_response = wttWC.replacePath("/").path(company.getId()).
					path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
					path(ProjectTest.PATH_EL_PROJECT).
					post(new ProjectModel("testSubProjectList" + i, "MY_DESC" + i));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(ProjectModel.class));
		}
		
		// list(/) -> _remoteList
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).get();
		List<ProjectModel> _remoteList = new ArrayList<ProjectModel>(wttWC.getCollection(ProjectModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (ProjectModel _pm : _remoteList) {
			_remoteListIds.add(_pm.getId());
		}
		
		for (ProjectModel _pm : _localList) {
			assertTrue("project <" + _pm.getId() + "> should be listed", _remoteListIds.contains(_pm.getId()));
		}
		
		for (ProjectModel _pm : _localList) {
			_response = wttWC.replacePath("/").path(company.getId()).
					path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
					path(ProjectTest.PATH_EL_PROJECT).path(_pm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(ProjectModel.class);
		}
		
		for (ProjectModel _pm : _localList) {
			_response = wttWC.replacePath("/").path(company.getId()).
					path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
					path(ProjectTest.PATH_EL_PROJECT).path(_pm.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}

	@Test
	public void testSubProjectCreate() {	
		// new(1) -> _pm1
		ProjectModel _pm1 = new ProjectModel("testSubProjectCreate1", "MY_DESC1");
		// new(2) -> _pm2
		ProjectModel _pm2 = new ProjectModel("testSubProjectCreate2", "MY_DESC2");
		
		// create(_pm1)  -> _pm3
		Response _response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).post(_pm1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm3 = _response.readEntity(ProjectModel.class);
		System.out.println("testSubProjectCreate created " + _pm3.getId());

		// create(_pm2) -> _pm4
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).post(_pm2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm4 = _response.readEntity(ProjectModel.class);		
		System.out.println("testSubProjectCreate created " + _pm4.getId());
		assertNotNull("ID should be set", _pm3.getId());
		assertNotNull("ID should be set", _pm4.getId());
		assertThat(_pm4.getId(), not(equalTo(_pm3.getId())));
		assertEquals("title1 should be set correctly", "testSubProjectCreate1", _pm3.getTitle());
		assertEquals("description1 should be set correctly", "MY_DESC1", _pm3.getDescription());
		assertEquals("title2 should be set correctly", "testSubProjectCreate2", _pm4.getTitle());
		assertEquals("description2 should be set correctly", "MY_DESC2", _pm4.getDescription());

		// delete(_p3) -> NO_CONTENT
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		System.out.println("testSubProjectCreate deleted " + _pm3.getId());

		// delete(_p4) -> NO_CONTENT
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		System.out.println("testSubProjectCreate deleted " + _pm4.getId());
	}
	
	@Test
	public void testSubProjectCreateDouble() {		
		// create(new()) -> _pm
		Response _response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).
				post(new ProjectModel("testSubProjectCreateDouble", "MY_DESC"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm = _response.readEntity(ProjectModel.class);
		System.out.println("testSubProjectCreateDouble created " + _pm.getId());
		assertNotNull("ID should be set:", _pm.getId());		
		
		// create(_pm) -> CONFLICT
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).post(_pm);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_pm) -> NO_CONTENT
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		System.out.println("testSubProjectCreateDouble deleted " + _pm.getId());
	}
	
	@Test
	public void testSubProjectRead() {
		ArrayList<ProjectModel> _localList = new ArrayList<ProjectModel>();
		Response _response = null;
		for (int i = 0; i < LIMIT; i++) {
			_response = wttWC.replacePath("/").path(company.getId()).
					path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
					path(ProjectTest.PATH_EL_PROJECT).
					post(new ProjectModel("testSubProjectRead", "MY_DESC"));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(ProjectModel.class));
		}
	
		// test read on each local element
		for (ProjectModel _pm : _localList) {
			_response = wttWC.replacePath("/").path(company.getId()).
					path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
					path(ProjectTest.PATH_EL_PROJECT).path(_pm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(ProjectModel.class);
		}

		// test read on each element within list()
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).get();
		List<ProjectModel> _remoteList = new ArrayList<ProjectModel>(wttWC.getCollection(ProjectModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ProjectModel _tmpObj = null;
		for (ProjectModel _pm : _remoteList) {
			_response = wttWC.replacePath("/").path(company.getId()).
					path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
					path(ProjectTest.PATH_EL_PROJECT).path(_pm.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(ProjectModel.class);
			assertEquals("ID should be unchanged when reading a project", _pm.getId(), _tmpObj.getId());						
		}

		for (ProjectModel _pm : _localList) {
			_response = wttWC.replacePath("/").path(company.getId()).
					path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
					path(ProjectTest.PATH_EL_PROJECT).path(_pm.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
			System.out.println("testSubProjectRead deleted " + _pm.getId());			
		}
	}
		
	@Test
	public void testSubProjectMultiRead() {
		// new() -> _pm1
		ProjectModel _pm1 = new ProjectModel("testSubProjectMultiRead", "MY_DESC");
		
		// create(_pm1) -> _pm2
		Response _response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).post(_pm1);
		ProjectModel _pm2 = _response.readEntity(ProjectModel.class);
		System.out.println("testSubProjectMultiRead created " + _pm2.getId());			

		// read(_pm2) -> _pm3
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm3 = _response.readEntity(ProjectModel.class);
		assertEquals("ID should be unchanged after read:", _pm2.getId(), _pm3.getId());		

		// read(_pm2) -> _pm4
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm4 = _response.readEntity(ProjectModel.class);
		assertEquals("ID should be unchanged after read:", _pm2.getId(), _pm4.getId());		
		
		// but: the two objects are not equal !
		assertEquals("ID should be the same:", _pm3.getId(), _pm4.getId());
		assertEquals("title should be the same:", _pm3.getTitle(), _pm4.getTitle());
		assertEquals("description should be the same:", _pm3.getDescription(), _pm4.getDescription());
		
		assertEquals("ID should be the same:", _pm3.getId(), _pm2.getId());
		assertEquals("title should be the same:", _pm3.getTitle(), _pm2.getTitle());
		assertEquals("description should be the same:", _pm3.getDescription(), _pm2.getDescription());
		
		// delete(_pm2)
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		System.out.println("testSubProjectMultiRead deleted " + _pm2.getId());			
	}
	
	@Test
	public void testSubProjectUpdate() {
		// create() -> _pm1
		Response _response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).post(new ProjectModel("testSubProjectUpdate", "MY_DESC"));
		ProjectModel _pm1 = _response.readEntity(ProjectModel.class);
		
		// change the attributes
		// update(_pm1) -> _pm2
		_pm1.setTitle("MY_TITLE1");
		_pm1.setDescription("MY_DESC1");
		wttWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm1.getId()).put(_pm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm2 = _response.readEntity(ProjectModel.class);

		assertNotNull("ID should be set", _pm2.getId());
		assertEquals("ID should be unchanged", _pm1.getId(), _pm2.getId());	
		assertEquals("title should have changed", "MY_TITLE1", _pm2.getTitle());
		assertEquals("description should have changed", "MY_DESC1", _pm2.getDescription());

		// reset the attributes
		// update(_pm1) -> _pm3
		_pm1.setTitle("MY_TITLE2");
		_pm1.setDescription("MY_DESC2");
		wttWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm1.getId()).put(_pm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm3 = _response.readEntity(ProjectModel.class);

		assertNotNull("ID should be set", _pm3.getId());
		assertEquals("ID should be unchanged", _pm1.getId(), _pm3.getId());	
		assertEquals("title should have changed", "MY_TITLE2", _pm3.getTitle());
		assertEquals("description should have changed", "MY_DESC2", _pm3.getDescription());
		
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testSubProjectDelete(
	) {
		// create() -> _pm1
		Response _response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).post(new ProjectModel("testSubProjectDelete", "MY_DESC"));
		ProjectModel _pm1 = _response.readEntity(ProjectModel.class);
		System.out.println("testSubProjectDelete created " + _pm1.getId());			
		
		// read(_pm1) -> _pm2
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm2 = _response.readEntity(ProjectModel.class);
		assertEquals("ID should be unchanged when reading a project (remote):", _pm1.getId(), _pm2.getId());						
		
		// delete(_pm1) -> OK
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		System.out.println("testSubProjectDelete deleted " + _pm1.getId());			
	
		// read the deleted object twice
		// read(_pm1) -> NOT_FOUND
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read(_pm1) -> NOT_FOUND
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testSubProjectDoubleDelete() {		
		// create() -> _pm1
		Response _response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).post(new ProjectModel("testSubProjectDoubleDelete", "MY_DESC"));
		ProjectModel _pm1 = _response.readEntity(ProjectModel.class);
		System.out.println("testSubProjectDoubleDelete created " + _pm1.getId());			

		// read(_pm1) -> OK
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		// delete(_pm1) -> OK
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		System.out.println("testSubProjectDoubleDelete deleted " + _pm1.getId());			
		
		// read(_pm1) -> NOT_FOUND
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// delete _pm1 -> NOT_FOUND
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm1.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read _pm1 -> NOT_FOUND
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testSubProjectDeep() throws Exception {
			// create() -> _parentProject
		Response _response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).
				post(new ProjectModel("parentProject", "MY_DESC"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _parentProject = _response.readEntity(ProjectModel.class);
		String _parentProjectId = null;
		TreeMap<String, String> _projectTree = new TreeMap<String, String>();  // parentProject, clientProject
		
		// creating some subprojects in a deep structure
		for (int i = 0; i < LIMIT; i++) {
			_parentProjectId = _parentProject.getId();
			_parentProject = wttWC.replacePath("/").path(company.getId()).
					path(ProjectTest.PATH_EL_PROJECT).path(_parentProject.getId()).
					path(ProjectTest.PATH_EL_PROJECT).
					post(new ProjectModel("testSubProjectDeep" + i, "MY_DESC" + i)).readEntity(ProjectModel.class);
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
			_response = wttWC.replacePath("/").path(company.getId()).
					path(ProjectTest.PATH_EL_PROJECT).path(_parentId).
					path(ProjectTest.PATH_EL_PROJECT).get();
			List<ProjectModel> _remoteList = new ArrayList<ProjectModel>(wttWC.getCollection(ProjectModel.class));
			assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			assertEquals("list() should contain one single subproject", 1, _remoteList.size());
			// read(_p2) -> _p3
			_response = wttWC.replacePath("/").path(company.getId()).
					path(ProjectTest.PATH_EL_PROJECT).path(_parentId).
					path(ProjectTest.PATH_EL_PROJECT).path(_childId).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		}

		// delete all subprojects recursively  (iterating in reverse order)
		for (String _pId : _projectTree.descendingKeySet()) {
			_childId = _projectTree.get(_pId);
			// System.out.println("deleting <" + _pId + ">/<" + _childId + ">");
			if (! _projectTree.containsKey(_childId)) {
				_response = wttWC.replacePath("/").path(company.getId()).
						path(ProjectTest.PATH_EL_PROJECT).path(_pId).
						path(ProjectTest.PATH_EL_PROJECT).path(_childId).delete();		
				assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
				_projectTree.remove(_childId);
			}
		}
	}
	
	@Test
	public void testSubProjectModifications() {
		// create(new ProjectModel()) -> _pm1
		Response _response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).post(new ProjectModel("testSubProjectModifications", "MY_DESC"));
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
		wttWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm1.getId()).put(_pm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _pm2 = _response.readEntity(ProjectModel.class);

		// test createdAt and createdBy (unchanged)
		assertEquals("update() should not change createdAt", _pm1.getCreatedAt(), _pm2.getCreatedAt());
		assertEquals("update() should not change createdBy", _pm1.getCreatedBy(), _pm2.getCreatedBy());
		
		// test modifiedAt and modifiedBy (= different from createdAt/createdBy)
		assertThat(_pm2.getModifiedAt(), not(equalTo(_pm2.getCreatedAt())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_pm2.getModifiedBy(), not(equalTo(_pm2.getCreatedBy())));

		// update(_pm1) with modifiedBy/At set on client side -> ignored by server
		_pm1.setModifiedBy("MYSELF");
		_pm1.setModifiedAt(new Date(1000));
		wttWC.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm1.getId()).put(_pm1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectModel _o3 = _response.readEntity(ProjectModel.class);
		
		// test, that modifiedBy really ignored the client-side value "MYSELF"
		assertThat(_pm1.getModifiedBy(), not(equalTo(_o3.getModifiedBy())));
		// check whether the client-side modifiedAt() is ignored
		assertThat(_pm1.getModifiedAt(), not(equalTo(_o3.getModifiedAt())));
		
		// delete(_pm1) -> NO_CONTENT
		_response = wttWC.replacePath("/").path(company.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProject.getId()).
				path(ProjectTest.PATH_EL_PROJECT).path(_pm1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	/********************************* helper methods *********************************/	
	public static ProjectModel createSubProject(
			WebClient wttWC, 
			String companyId,
			String parentProjectId,
			String title, 
			String description) 
	{
		ProjectModel _pm = new ProjectModel();
		_pm.setTitle(title);
		_pm.setDescription(description);
		Response _response = wttWC.replacePath("/").path(companyId).
				path(ProjectTest.PATH_EL_PROJECT).path(parentProjectId).
				path(ProjectTest.PATH_EL_PROJECT).post(_pm);
		assertEquals("post() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		return _response.readEntity(ProjectModel.class);
	}
	
	protected int calculateMembers() {
		return 1;
	}
}
