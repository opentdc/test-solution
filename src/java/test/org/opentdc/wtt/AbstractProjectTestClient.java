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

import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.OrgModel;
import org.opentdc.addressbooks.OrgType;
import org.opentdc.service.ServiceUtil;
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.ProjectModel;
import org.opentdc.wtt.WttService;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.addressbooks.AddressbookTest;
import test.org.opentdc.addressbooks.OrgTest;

/**
 * Abstract class for testing Projects and SubProjects in WttService
 * @author Bruno Kaiser
 *
 */
public abstract class AbstractProjectTestClient extends AbstractTestClient {
	private static final String CN = "AbstractProjectTestClient";
	protected WebClient wc = null;
	protected WebClient addressbookWC = null;
	protected CompanyModel company = null;
	protected AddressbookModel addressbook = null;
	protected OrgModel org = null;

	/**
	 * Initialize test resources.
	 */
	protected void initializeTests() {
		wc = initializeTest(ServiceUtil.WTT_API_URL, WttService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		
		addressbook = AddressbookTest.post(addressbookWC, 
				new AddressbookModel(CN), Status.OK);
		org = OrgTest.post(addressbookWC, addressbook.getId(), 
				new OrgModel(CN, OrgType.COMP), Status.OK);
		company = CompanyTest.post(wc, 
				new CompanyModel(CN, "MY_DESC", org.getId()), Status.OK);
	}

	/**
	 * Free all allocated test resources.
	 */
	protected void cleanupTest() {
		AddressbookTest.delete(addressbookWC, addressbook.getId(), Status.NO_CONTENT);
		addressbookWC.close();
		
		CompanyTest.delete(wc, company.getId(), Status.NO_CONTENT);
		wc.close();
	}
	
	/********************************** project attributes tests *********************************/			
	protected void testEmptyConstructor() {
		ProjectModel _model = new ProjectModel();
		assertNull("id should not be set by empty constructor", _model.getId());
		assertNull("title should not be set by empty constructor", _model.getTitle());
		assertNull("description should not be set by empty constructor", _model.getDescription());
	}
	
	protected void testConstructor() {		
		ProjectModel _model = new ProjectModel("testConstructor", "MY_DESC");
		assertNull("id should not be set by constructor", _model.getId());
		assertEquals("title should be set by constructor", "testConstructor", _model.getTitle());
		assertEquals("description should be set by constructor", "MY_DESC", _model.getDescription());
	}
	
	protected void testId() {
		ProjectModel _model = new ProjectModel();
		assertNull("id should not be set by constructor", _model.getId());
		_model.setId("testId");
		assertEquals("id should have changed:", "testId", _model.getId());
	}

	protected void testTitle() {
		ProjectModel _model = new ProjectModel();
		assertNull("title should not be set by empty constructor", _model.getTitle());
		_model.setTitle("testTitle");
		assertEquals("title should have changed:", "testTitle", _model.getTitle());
	}
	
	protected void testDescription() {
		ProjectModel _model = new ProjectModel();
		assertNull("description should not be set by empty constructor", _model.getDescription());
		_model.setDescription("testDescription");
		assertEquals("description should have changed:", "testDescription", _model.getDescription());
	}	
	
	protected void testCreatedBy() {
		ProjectModel _model = new ProjectModel();
		assertNull("createdBy should not be set by empty constructor", _model.getCreatedBy());
		_model.setCreatedBy("testCreatedBy");
		assertEquals("createdBy should have changed", "testCreatedBy", _model.getCreatedBy());	
	}
	
	protected void testCreatedAt() {
		ProjectModel _model = new ProjectModel();
		assertNull("createdAt should not be set by empty constructor", _model.getCreatedAt());
		_model.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _model.getCreatedAt());
	}
		
	protected void testModifiedBy() {
		ProjectModel _model = new ProjectModel();
		assertNull("modifiedBy should not be set by empty constructor", _model.getModifiedBy());
		_model.setModifiedBy("testModifiedBy");
		assertEquals("modifiedBy should have changed", "testModifiedBy", _model.getModifiedBy());	
	}
	
	protected void testModifiedAt() {
		ProjectModel _model = new ProjectModel();
		assertNull("modifiedAt should not be set by empty constructor", _model.getModifiedAt());
		_model.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _model.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	protected void testCreateReadDeleteWithEmptyConstructor() {
		ProjectModel _model1 = new ProjectModel();
		assertNull("id should not be set by empty constructor", _model1.getId());
		assertNull("title should not be set by empty constructor", _model1.getTitle());
		assertNull("description should not be set by empty constructor", _model1.getDescription());
		
		post(_model1, Status.BAD_REQUEST);
		_model1.setTitle("testCreateReadDeleteWithEmptyConstructor");

		ProjectModel _model2 = post(_model1, Status.OK);		
		assertNull("create() should not change the id of the local object", _model1.getId());
		assertEquals("create() should not change the title of the local object", "testCreateReadDeleteWithEmptyConstructor", _model1.getTitle());
		assertNull("create() should not change the description of the local object", _model1.getDescription());
		
		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
		assertEquals("create() should not change the title", "testCreateReadDeleteWithEmptyConstructor", _model2.getTitle());
		assertNull("create() should not change the description", _model2.getDescription());
		
		ProjectModel _model3 = get(_model2.getId(), Status.OK);		
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("title of returned object should be unchanged after remote create", _model2.getTitle(), _model3.getTitle());
		assertEquals("description of returned object should be unchanged after remote create", _model2.getDescription(), _model3.getDescription());
		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	protected void testCreateReadDelete() {
		ProjectModel _model1 = new ProjectModel("testCreateReadDelete", "MY_DESC");
		assertNull("id should not be set by constructor", _model1.getId());
		assertEquals("title should be set by constructor", "testCreateReadDelete", _model1.getTitle());
		assertEquals("description should be set by constructor", "MY_DESC", _model1.getDescription());
		ProjectModel _model2 = post(_model1, Status.OK);
		
		assertNull("id should still be null after remote create", _model1.getId());
		assertEquals("create() should not change the title", "testCreateReadDelete", _model1.getTitle());
		assertEquals("craete() should not change the description", "MY_DESC", _model1.getDescription());
		
		assertNotNull("id of returned object should be set", _model2.getId());
		assertEquals("create() should not change the title", "testCreateReadDelete", _model2.getTitle());
		assertEquals("create() should not change the description", "MY_DESC", _model2.getDescription());
		
		ProjectModel _model3 = get(_model2.getId(), Status.OK);
		assertEquals("read() should not change the id", _model2.getId(), _model3.getId());
		assertEquals("read() should not change the title", _model2.getTitle(), _model3.getTitle());
		assertEquals("read() should not change the description", _model2.getDescription(), _model3.getDescription());
		delete(_model3.getId(), Status.NO_CONTENT);
	}
	
	protected void testClientSideId() {
		ProjectModel _model = new ProjectModel("testClientSideId", "MY_DESC");
		_model.setId("testClientSideId");
		assertEquals("id should have changed", "testClientSideId", _model.getId());
		post(_model, Status.BAD_REQUEST);
	}
	
	protected void testDuplicateId() {
		ProjectModel _model1 = post(new ProjectModel("testDuplicateId1", "MY_DESC1"), Status.OK);
		ProjectModel _model2 = new ProjectModel("testDuplicateId2", "MY_DESC2");
		_model2.setId(_model1.getId());		// wrongly create a 2nd ProjectModel object with the same ID
		post(_model2, Status.CONFLICT);
	}
	
	protected void testList() {
		List<ProjectModel> _listBefore = list(null, Status.OK);
		ArrayList<ProjectModel> _localList = new ArrayList<ProjectModel>();		
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(post(new ProjectModel("testList" + i, "MY_DESC"), Status.OK));
		}
		assertEquals("correct number of projects should be created", LIMIT, _localList.size());
		List<ProjectModel> _listAfter = list(null, Status.OK);		
		assertEquals("list() should return the correct number of projects", _listBefore.size() + LIMIT, _listAfter.size());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (ProjectModel _model : _listAfter) {
			_remoteListIds.add(_model.getId());
		}
		for (ProjectModel _model : _localList) {
			assertTrue("project <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
		}
		for (ProjectModel _model : _localList) {
			get(_model.getId(), Status.OK);
		}
		for (ProjectModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}
	}

	protected void testCreate() {	
		ProjectModel _model1 = post(new ProjectModel("testCreate1", "MY_DESC1"), Status.OK);
		ProjectModel _model2 = post(new ProjectModel("testCreate2", "MY_DESC2"), Status.OK);
		
		assertNotNull("ID should be set", _model1.getId());
		assertEquals("title1 should be set correctly", "testCreate1", _model1.getTitle());
		assertEquals("description1 should be set correctly", "MY_DESC1", _model1.getDescription());
		
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("title2 should be set correctly", "testCreate2", _model2.getTitle());
		assertEquals("description2 should be set correctly", "MY_DESC2", _model2.getDescription());
		assertThat(_model1.getId(), not(equalTo(_model2.getId())));
		delete(_model1.getId(), Status.NO_CONTENT);
		delete(_model2.getId(), Status.NO_CONTENT);
	}
	
	protected void testDoubleCreate() {
		ProjectModel _model = post(new ProjectModel("testDoubleCreate", "MY_DESC"), Status.OK);
		assertNotNull("ID should be set:", _model.getId());		
		post(_model, Status.CONFLICT);
		delete(_model.getId(), Status.NO_CONTENT);
	}
	
	protected void testRead() {
		ArrayList<ProjectModel> _localList = new ArrayList<ProjectModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(post(new ProjectModel("testRead" + i, "MY_DESC"), Status.OK));
		}
		for (ProjectModel _model : _localList) {
			get(_model.getId(), Status.OK);
		}
		List<ProjectModel> _remoteList = list(null, Status.OK);
		for (ProjectModel _model : _remoteList) {
			assertEquals("ID should be unchanged when reading an project", _model.getId(), get(_model.getId(), Status.OK).getId());						
		}
		for (ProjectModel _model : _localList) {
			delete(_model.getId(), Status.NO_CONTENT);
		}
	}
		
	protected void testMultiRead() {
		ProjectModel _model1 = post(new ProjectModel("testMultiRead", "MY_DESC"), Status.OK);
		ProjectModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged after read", _model1.getId(), _model2.getId());		
		ProjectModel _model3 = get(_model1.getId(), Status.OK);		
		assertEquals("ID should be the same", _model2.getId(), _model3.getId());
		assertEquals("title should be the same", _model2.getTitle(), _model3.getTitle());
		assertEquals("ID should be the same", _model2.getId(), _model1.getId());
		assertEquals("title should be the same", _model2.getTitle(), _model1.getTitle());
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	protected void testUpdate() {
		ProjectModel _model1 = post(new ProjectModel("testUpdate", "MY_DESC"), Status.OK);
		_model1.setTitle("testUpdate1");
		ProjectModel _model2 = put(_model1, Status.OK);
		assertNotNull("ID should be set", _model2.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());	
		assertEquals("title should have changed", "testUpdate1", _model2.getTitle());
		_model1.setTitle("testUpdate2");
		ProjectModel _model3 = put(_model1, Status.OK);
		assertNotNull("ID should be set", _model3.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model3.getId());	
		assertEquals("title should have changed", "testUpdate2", _model3.getTitle());
		delete(_model1.getId(), Status.NO_CONTENT);
	}

	protected void testDelete() {
		ProjectModel _model1 = post(new ProjectModel("testDelete", "MY_DESC"), Status.OK);
		ProjectModel _model2 = get(_model1.getId(), Status.OK);
		assertEquals("ID should be unchanged when reading an project", _model1.getId(), _model2.getId());						
		delete(_model1.getId(), Status.NO_CONTENT);
		get(_model1.getId(), Status.NOT_FOUND);
		get(_model1.getId(), Status.NOT_FOUND);
	}
	
	protected void testDoubleDelete() {
		ProjectModel _model1 = post(new ProjectModel("testDoubleDelete", "MY_DESC"), Status.OK);
		get(_model1.getId(), Status.OK);
		delete(_model1.getId(), Status.NO_CONTENT);
		get(_model1.getId(), Status.NOT_FOUND);
		delete(_model1.getId(), Status.NOT_FOUND);
		get(_model1.getId(), Status.NOT_FOUND);
	}
	
	protected void testModifications() {
		ProjectModel _model1 = post(new ProjectModel("testModifications", "MY_DESC"), Status.OK);
		assertNotNull("create() should set createdAt", _model1.getCreatedAt());
		assertNotNull("create() should set createdBy", _model1.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
		assertEquals("createdAt and modifiedAt should be identical after create()", _model1.getCreatedAt(), _model1.getModifiedAt());
		assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());
		_model1.setTitle("testModifications2");
		ProjectModel _model2 = put(_model1, Status.OK);
		assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
		assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
		assertThat(_model2.getModifiedAt(), not(equalTo(_model2.getCreatedAt())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_model2.getModifiedBy(), not(equalTo(_model2.getCreatedBy())));
		_model1.setModifiedBy("MYSELF");
		_model1.setModifiedAt(new Date(1000));
		ProjectModel _model3 = put(_model1, Status.OK);
		
		// test, that modifiedBy really ignored the client-side value "MYSELF"
		assertThat(_model1.getModifiedBy(), not(equalTo(_model3.getModifiedBy())));
		// check whether the client-side modifiedAt() is ignored
		assertThat(_model1.getModifiedAt(), not(equalTo(_model3.getModifiedAt())));
		
		delete(_model1.getId(), Status.NO_CONTENT);
	}
	
	/********************************* helper methods *********************************/	
	/**
	 * Retrieve a list of projects from WttService by executing a HTTP GET request.
	 * This uses neither position nor size queries.
	 * @param query the URL query to use
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of ProjectModel objects in JSON format
	 */
	abstract protected List<ProjectModel> list(
			String query, 
			Status expectedStatus);
	
	/**
	 * Create a new project on the server by executing a HTTP POST request.
	 * @param model the ProjectModel to post
	 * @param expectedStatus the expected HTTP status to test on; if this is null, it will not be tested
	 * @return the created ProjectModel
	 */
	abstract protected ProjectModel post(
			ProjectModel model,
			Status expectedStatus);
	
	/**
	 * Read a project by id from WttService by executing a HTTP GET method.
	 * @param id the id of the ProjectModel to retrieve
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the retrieved ProjectModel object in JSON format
	 */
	abstract protected ProjectModel get(
			String id, 
			Status expectedStatus);
		
	/**
	 * Update a project on the WttService by executing a HTTP PUT method.
	 * @param model the new ProjectModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated ProjectModel object in JSON format
	 */
	abstract protected ProjectModel put(
			ProjectModel model, 
			Status expectedStatus);
	
	/**
	 * Delete the ProjectModel with id on the WttService by executing a HTTP DELETE method.
	 * @param id the id of the ProjectModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	abstract protected void delete(
			String id, 
			Status expectedStatus);
}
