package test.org.opentdc.wtt;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
import org.opentdc.service.ServiceUtil;
import org.opentdc.wtt.ProjectModel;

/**
 * Testing subprojects in WttService
 * @author Bruno Kaiser
 *
 */
public class SubProjectTest  extends AbstractProjectTestClient {	
	private static final String CN = "SubProjectTest";
	private ProjectModel parentProject = null;

	/**
	 * Initialize test resources.
	 */
	@Before
	public void initializeTests() {
		super.initializeTests();
		parentProject = ProjectTest.post(wc, company.getId(), 
				new ProjectModel(CN, "MY_DESC"), Status.OK);
	}

	/**
	 * Free all allocated test resources.
	 */
	@After
	public void cleanupTest() {
		super.cleanupTest();
	}
	
	/********************************** subproject attributes tests *********************************/	
	@Test
	public void testEmptyConstructor() {
		super.testEmptyConstructor();
	}
	
	@Test
	public void testConstructor() {	
		super.testConstructor();
	}
	
	@Test
	public void testId() {
		super.testId();
	}

	@Test
	public void testTitle() {
		super.testTitle();
	}
	
	@Test
	public void testDescription() {
		super.testDescription();
	}	
	
	@Test
	public void testCreatedBy() {
		super.testCreatedBy();
	}
	
	@Test
	public void testCreatedAt() {
		super.testCreatedAt();
	}
		
	@Test
	public void testModifiedBy() {
		super.testModifiedBy();
	}
	
	@Test
	public void testModifiedAt() {
		super.testModifiedAt();
	}
	
	/********************************** REST service tests *********************************/		
	// create:  POST p "api/company/{cid}/project/{pid}/project"
	// read:    GET "api/company/{cid}/project/{pid}/project/{spid}"
	// update:  PUT p "api/company/{cid}/project/{pid}/project/{spid}"
	// delete:  DELETE "api/company/{cid}/project/{pid}/project/{spid}"
	@Test
	public void testCreateReadDeleteWithEmptyConstructor() {
		super.testCreateReadDeleteWithEmptyConstructor();
	}

	@Test
	public void testCreateReadDelete() {
		super.testCreateReadDelete();
	}
	
	@Test
	public void testClientSideId() {
		super.testClientSideId();
	}
	
	@Test
	public void testDuplicateId() {
		super.testDuplicateId();
	}
	
	@Test
	public void testList() {
		super.testList();
	}

	@Test
	public void testCreate() {	
		super.testCreate();
	}
	
	@Test
	public void testDoubleCreate() {	
		super.testDoubleCreate();
	}
	
	@Test
	public void testRead() {
		super.testRead();
	}
		
	@Test
	public void testMultiRead() {
		super.testMultiRead();
	}
	
	@Test
	public void testUpdate() {
		super.testUpdate();
	}

	@Test
	public void testDelete() {
		super.testDelete();
	}
	
	@Test
	public void testDoubleDelete() {
		super.testDoubleDelete();
	}

	@Test
	public void testDeepHierarchy() throws Exception {
		ProjectModel _parentProject = post(new ProjectModel("parentProject", "testDeepHierarchy"), Status.OK);
		String _parentProjectId = null;
		TreeMap<String, String> _projectTree = new TreeMap<String, String>();  // parentProject, clientProject
		
		// creating some subprojects in a deep structure
		for (int i = 0; i < LIMIT; i++) {
			_parentProjectId = _parentProject.getId();
			_parentProject = post(_parentProject.getId(), new ProjectModel("testDeepHierarchy" + i, "MY_DESC" + i), Status.OK);
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
			List<ProjectModel> _remoteList = list(_parentId, null, Status.OK);
			assertEquals("list() should contain one single subproject", 1, _remoteList.size());

			get(_parentId, _childId, Status.OK);
		}

		// delete all subprojects recursively  (iterating in reverse order)
		for (String _pId : _projectTree.descendingKeySet()) {
			_childId = _projectTree.get(_pId);
			// System.out.println("deleting <" + _pId + ">/<" + _childId + ">");
			if (! _projectTree.containsKey(_childId)) {
				delete(_pId, _childId, Status.NO_CONTENT);
				_projectTree.remove(_childId);
			}
		}
	}
	
	@Test
	public void testModifications() {
		super.testModifications();
	}
	
	/********************************* helper methods *********************************/	
	/* (non-Javadoc)
	 * @see test.org.opentdc.wtt.AbstractProjectTestClient#list(java.lang.String, javax.ws.rs.core.Response.Status)
	 */
	@Override
	protected List<ProjectModel> list(
			String query, 
			Status expectedStatus) {
		return list(wc, company.getId(), parentProject.getId(), query, -1, Integer.MAX_VALUE, expectedStatus);
	}
	
	private List<ProjectModel> list(
			String parentProjectId,
			String query,
			Status expectedStatus){
		return list(wc, company.getId(), parentProjectId, query, -1, Integer.MAX_VALUE, expectedStatus);
	}
	
	
	/**
	 * Retrieve a list of SubProjects from WttService by executing a HTTP GET request.
	 * @param webClient the WebClient for the WttService
	 * @param companyId the ID of the company
	 * @param parentProjectId the ID of the parentProject to be listed
	 * @param query the URL query to use
	 * @param position the position to start a batch with
	 * @param size the size of a batch
	 * @param expectedStatus the expected HTTP status to test on
	 * @return a List of ProjectModel objects in JSON format
	 */
	public static List<ProjectModel> list(
			WebClient webClient, 
			String companyId,
			String parentProjectId,
			String query, 
			int position,
			int size,
			Status expectedStatus) {
		webClient.resetQuery();
		webClient.replacePath("/").path(companyId).
			path(ServiceUtil.PROJECT_PATH_EL).path(parentProjectId).path(ServiceUtil.PROJECT_PATH_EL);
		Response _response = executeListQuery(webClient, query, position, size);
		List<ProjectModel> _list = null;
		if (expectedStatus != null) {
			assertEquals("list() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			_list = new ArrayList<ProjectModel>(webClient.getCollection(ProjectModel.class));
			System.out.println("list(webClient, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + ") ->" + _list.size());
		}
		return _list;
	}
	
	/* (non-Javadoc)
	 * @see test.org.opentdc.wtt.AbstractProjectTestClient#post(org.opentdc.wtt.ProjectModel, javax.ws.rs.core.Response.Status)
	 */
	@Override
	protected ProjectModel post(
			ProjectModel model,
			Status expectedStatus) {
		return post(wc, company.getId(), parentProject.getId(), model, expectedStatus);
	}
	
	private ProjectModel post(
			String parentProjectId,
			ProjectModel model,
			Status expectedStatus) {
		return post(wc, company.getId(), parentProjectId, model, expectedStatus);
	}

	/**
	 * Create a new SubProject on the server by executing a HTTP POST request.
	 * @param webClient the WebClient representing the WttService
	 * @param companyId the ID of the company
	 * @param parentProjectId the ID of the parentProject
	 * @param model the ProjectModel data to create on the server
	 * @param exceptedStatus the expected HTTP status to test on
	 * @return the created ProjectModel
	 */
	public static ProjectModel post(
			WebClient webClient,
			String companyId,
			String parentProjectId,
			ProjectModel model,
			Status expectedStatus) 
	{
		Response _response = webClient.replacePath("/").path(companyId).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProjectId).path(ServiceUtil.PROJECT_PATH_EL).post(model);
		if (expectedStatus != null) {
			assertEquals("POST should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(ProjectModel.class);
		} else {
			return null;
		}
	}		
	
	/* (non-Javadoc)
	 * @see test.org.opentdc.wtt.AbstractProjectTestClient#get(java.lang.String, javax.ws.rs.core.Response.Status)
	 */
	@Override
	protected ProjectModel get(
			String id, 
			Status expectedStatus) {
		return get(wc, company.getId(), parentProject.getId(), id, expectedStatus);
	}
	
	private ProjectModel get(
			String parentProjectId,
			String id,
			Status expectedStatus) {
		return get(wc, company.getId(), parentProjectId, id, expectedStatus);
	}
	
	/**
	 * Read the SubProject with id from WttService by executing a HTTP GET method.
	 * @param webClient the web client representing the WttService
	 * @param companyId the ID of the company 
	 * @param parentProjectId the ID of the parentProject
	 * @param id the id of the ProjectModel to retrieve
	 * @param expectedStatus  the expected HTTP status to test on
	 * @return the retrieved ProjectModel object in JSON format
	 */
	public static ProjectModel get(
			WebClient webClient,
			String companyId,
			String parentProjectId,
			String id,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(companyId).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProjectId).path(ServiceUtil.PROJECT_PATH_EL).path(id).get();
		if (expectedStatus != null) {
			assertEquals("GET should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(ProjectModel.class);
		} else {
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see test.org.opentdc.wtt.AbstractProjectTestClient#put(org.opentdc.wtt.ProjectModel, javax.ws.rs.core.Response.Status)
	 */
	@Override
	protected ProjectModel put(
			ProjectModel model, 
			Status expectedStatus) {
		return put(wc, company.getId(), parentProject.getId(), model, expectedStatus);
	}
	
	/**
	 * Update a SubProject on the WttService by executing a HTTP PUT method.
	 * @param webClient the web client representing the WttService
	 * @param companyId the ID of the company 
	 * @param parentProjectId the ID of the parentProject
	 * @param model the new ProjectModel data
	 * @param expectedStatus the expected HTTP status to test on
	 * @return the updated ProjectModel object in JSON format
	 */
	public static ProjectModel put(
			WebClient webClient,
			String companyId,
			String parentProjectId,
			ProjectModel model,
			Status expectedStatus) {
		webClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response _response = webClient.replacePath("/").path(companyId).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProjectId).path(ServiceUtil.PROJECT_PATH_EL).path(model.getId()).put(model);
		if (expectedStatus != null) {
			assertEquals("PUT should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
		if (_response.getStatus() == Status.OK.getStatusCode()) {
			return _response.readEntity(ProjectModel.class);
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see test.org.opentdc.wtt.AbstractProjectTestClient#delete(java.lang.String, javax.ws.rs.core.Response.Status)
	 */
	@Override
	protected void delete(
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
	 * @param id the id of the ProjectModel object to delete
	 * @param expectedStatus the expected HTTP status to test on
	 */
	public static void delete(
			WebClient webClient,
			String companyId,
			String parentProjectId,
			String id,
			Status expectedStatus) {
		Response _response = webClient.replacePath("/").path(companyId).
				path(ServiceUtil.PROJECT_PATH_EL).path(parentProjectId).path(ServiceUtil.PROJECT_PATH_EL).path(id).delete();	
		if (expectedStatus != null) {
			assertEquals("DELETE should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
		}
	}		
	
	/* (non-Javadoc)
	 * @see test.org.opentdc.AbstractTestClient#calculateMembers()
	 */
	@Override
	protected int calculateMembers() {
		return list(wc, company.getId(), parentProject.getId(), null, 0, Integer.MAX_VALUE, Status.OK).size();
	}
}
