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

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opentdc.service.exception.DuplicateException;
import org.opentdc.service.exception.NotFoundException;
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.ProjectModel;
import org.opentdc.wtt.ResourceRefModel;

import test.org.opentdc.AbstractTestClient;

public class ProjectTest extends AbstractTestClient {
	
	public static final String API = "api/company/";	
	
	private static final String MY_TITLE = "MyTitle";
	private static final String MY_DESC = "MyDescription";
	private static final String MY_TITLE2 = "MyTitle2";
	private static final String MY_DESC2 = "MyDescription2";
	
	public static final String PATH_EL_PROJECT = "project";
	public static final String PATH_EL_RESOURCE = "resource";
	
	private static CompanyModel company = null;
	private static final int LIMIT = 3;
	private int counter = 0;

	@BeforeClass
	public static void initializeTests(
	) {
		System.out.println("initializing");
		initializeTests(API);
		company = CompanyTest.createCompany(new CompanyModel());
	}
	
	public static List<ProjectModel> listProjects(
		String compId
	) {
		// System.out.println("> listProjects()");
		Response _r = webclient.replacePath("/").path(compId).path(PATH_EL_PROJECT).get();
		status = _r.getStatus();
		if (status == Status.OK.getStatusCode()) {
			return new ArrayList<ProjectModel>(webclient.getCollection(ProjectModel.class));
		}
		else {
			return new ArrayList<ProjectModel>();
		}
	}
	
	public static List<ProjectModel> listProjectsHierarchically(String compId, boolean asTree) {
		Response _r = null; 
		if (asTree == true) {
			_r = webclient.replacePath("/").path(compId).path(PATH_EL_PROJECT).path("astree").get();
		}
		else {
			_r = webclient.replacePath("/").path(compId).path(PATH_EL_PROJECT).path("flat").get();				
		}
		status = _r.getStatus();
		if (status == Status.OK.getStatusCode()) {
			return new ArrayList<ProjectModel>(webclient.getCollection(ProjectModel.class));
		}
		else {
			return new ArrayList<ProjectModel>();
		}
	}
	
	public static ProjectModel createProject(
		String compId, 
		ProjectModel p
	) throws DuplicateException, NotFoundException {
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response resp = webclient.replacePath("/").path(compId).path(PATH_EL_PROJECT).post(p);
		status = resp.getStatus();
		if(status == Status.CONFLICT.getStatusCode()) {
			throw new DuplicateException();
		} else if(status == Status.NOT_FOUND.getStatusCode()) {
			throw new NotFoundException();
		} else {
			return resp.readEntity(ProjectModel.class);
		}
	}

	public static ProjectModel createSubproject(
		String compId, 
		String projId, 
		ProjectModel p
	) throws DuplicateException, NotFoundException {
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response resp = webclient.replacePath("/").path(compId).path(PATH_EL_PROJECT).path(projId).post(p);
		status = resp.getStatus();
		if (status == Status.CONFLICT.getStatusCode()) {
			throw new DuplicateException();
		} else if (status == Status.CONFLICT.getStatusCode()) {
			throw new NotFoundException();
		} else {
			return resp.readEntity(ProjectModel.class);
		}
	}

	public static ProjectModel readProject(
		String compId, 
		String projId
	) throws NotFoundException {
		Response resp = webclient.replacePath("/").path(compId).path(PATH_EL_PROJECT).path(projId).get();
		status = resp.getStatus();
		if (status == Status.NOT_FOUND.getStatusCode()) {
			throw new NotFoundException();
		} else {
			return resp.readEntity(ProjectModel.class);
		}
	}

	public static ProjectModel updateProject(
		String compId, 
		ProjectModel p
	) {
		Response resp = webclient.replacePath("/").path(compId).path(PATH_EL_PROJECT).path(p.getId()).put(p);
		status = resp.getStatus();
		if (status == Status.OK.getStatusCode()) {
			return resp.readEntity(ProjectModel.class);
		}
		else {
			return null;
		}
	}

	public static int deleteProject(String compId, String projId) {
		Response resp = webclient.replacePath("/").path(compId).path(PATH_EL_PROJECT).path(projId).delete();
		status = resp.getStatus();
		return status;
	}

	public static int countProjects(
		String compId
	) {
		return webclient.replacePath("/").path(compId).path(PATH_EL_PROJECT).path("count").get(Integer.class);
	}	
	
	public static int countSubprojects(String compId, String projId) {
		ProjectModel _p = readProject(compId, projId);
		return _p.getProjects().size();
		// System.out.println("> countCompanies()");
	}
	
	/***************************** resource **********************************/
	// GET "/{cid}/project/{pid}/resource"
	public static ArrayList<ResourceRefModel> listResources(
		String compId, 
		String projId
	) {
		Response resp = webclient.replacePath("/").path(compId).path(PATH_EL_PROJECT).path(projId).path(PATH_EL_RESOURCE).get();
		status = resp.getStatus();
		if (status == Status.OK.getStatusCode()) {
			return new ArrayList<ResourceRefModel>(webclient.getCollection(ResourceRefModel.class));
		}
		else {
			return new ArrayList<ResourceRefModel>();
		}
	}
	
	// POST "/{cid}/project/{pid}/resource"
	public static String addResource(
		String compId, 
		String projId, 
		String resourceId
	) {
		Response _r = webclient.replacePath("/").path(compId).path(PATH_EL_PROJECT).path(projId).path(PATH_EL_RESOURCE).post(resourceId);
		status = _r.getStatus();
		if (status == Status.OK.getStatusCode()) {
			return resourceId;
		}
		else {
			return null;
		}
	}

	// DELETE "/{cid}/project/{pid}/resource/{rid}"
	public static void deleteResource(
		String compId, 
		String projId, 
		String resourceId
	) {
		Response _r = webclient.replacePath("/").path(compId).path(PATH_EL_PROJECT).path(projId).path(PATH_EL_RESOURCE).path(resourceId).delete();
		status = _r.getStatus();
	}
	
	// GET "/{cid}/project/{pid}/resource/count"
	public static int countResources(
		String compId, 
		String projId
	) {
		Response _r = webclient.replacePath("/").path(compId).path(PATH_EL_PROJECT).path(projId).path(PATH_EL_RESOURCE).path("count").get();
		String _countStr = _r.readEntity(String.class);
		// System.out.println("countProjects (stringified): " + _countStr);
		if (_countStr == null || _countStr.length() == 0) {
			_countStr = "0";
		}
		return new Integer(_countStr).intValue();
	}
	
	/********************************** project tests *********************************/	
	@Test
	public void testProjectAttributeChange() {		
		// System.out.println("*** testProjectAttributeChange:");
		ProjectModel _p = new ProjectModel();
		_p.setTitle(MY_TITLE);
		_p.setDescription(MY_DESC);
		// TODO: try to set invalid data attributes

		Assert.assertEquals("title should have changed:", MY_TITLE, _p.getTitle());
		Assert.assertEquals("description should have changed:", MY_DESC, _p.getDescription());
	}
	
	@Test
	public void testProjectAttributeId() {
		// System.out.println("*** testProjectAttributeId:");
		ProjectModel _p1 = new ProjectModel();
		ProjectModel _p2 = new ProjectModel();
		
		ProjectModel _p3 = createProject(getCompanyId(), _p1);
		ProjectModel _p4 = createProject(getCompanyId(), _p2);
		
		Assert.assertNotNull("ID should be set:", _p3.getId());
		Assert.assertNotNull("ID should be set:", _p4.getId());
		Assert.assertNotSame("IDs should be different:", _p3.getId(), _p4.getId());
		Assert.assertEquals("there should be two projects:", 2, countProjects(getCompanyId()));
				
		deleteProject(getCompanyId(), _p3.getId());
		deleteProject(getCompanyId(), _p4.getId());
		Assert.assertEquals("there should be zero projects:", 0, countProjects(getCompanyId()));
	}

	@Test
	public void testProjectList() throws Exception {
		// System.out.println("*** testProjectList:");		
		ArrayList<ProjectModel> _localList = new ArrayList<ProjectModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(createProject(getCompanyId(), new ProjectModel()));
			Assert.assertEquals("createProject() should return with status OK:", Status.OK.getStatusCode(), getStatus());
		}
		Assert.assertEquals("there should be " + LIMIT + " projects:", LIMIT, countProjects(getCompanyId()));
		List<ProjectModel> _remoteList = listProjects(getCompanyId());
		Assert.assertEquals("listProjects() should return with status OK:", Status.OK.getStatusCode(), getStatus());
		Assert.assertEquals("there should be still " + LIMIT + " projects:", LIMIT, countProjects(getCompanyId()));
		Assert.assertEquals("there should be " + LIMIT + " projects locally:", LIMIT, _localList.size());
		Assert.assertEquals("there should be " + LIMIT + " projects remotely:", LIMIT, _remoteList.size());
		
		for (ProjectModel _p : _localList) {
			deleteProject(getCompanyId(), _p.getId());
		}
		Assert.assertEquals("there should be zero projects:", 0, countProjects(getCompanyId()));
	}

	@Test
	public void testProjectCreate() {	
		// System.out.println("*** testProjectCreate:");
		ProjectModel _p1 = new ProjectModel();
		ProjectModel _p2 = createProject(getCompanyId(), _p1);

		Assert.assertEquals("createProject() should return with status OK:", Status.OK.getStatusCode(), getStatus());
		Assert.assertEquals("there should be one project:", 1, countProjects(getCompanyId()));
		Assert.assertNotNull("ID should be set:", _p2.getId());		
		deleteProject(getCompanyId(), _p2.getId());
	}
	
	@Test
	public void testProjectCreateDouble() {
		// System.out.println("*** testProjectCreateDouble with company <" + getCompanyId() + ">:");
		
		ProjectModel _p1 = new ProjectModel();
		ProjectModel _p2 = createProject(getCompanyId(), _p1);
		Assert.assertEquals("createProject() should return with status OK:", Status.OK.getStatusCode(), getStatus());
		Assert.assertEquals("there should be one project:", 1, countProjects(getCompanyId()));
		Assert.assertNotNull("ID should be set:", _p2.getId());
		try {
			ProjectModel _p3 = createProject(getCompanyId(), _p2);
			fail("creating a double project should result in status CONFLICT(409):");
		} catch(DuplicateException e) {}
		deleteProject(getCompanyId(), _p2.getId());
		Assert.assertEquals("there should be zero projects:", 0, countProjects(getCompanyId()));
	}

	@Test
	public void testProjectCreateSubproject() {	
		// System.out.println("*** testProjectCreateSubproject:");
		ProjectModel _parentProject = createProject(getCompanyId(), new ProjectModel());
		ProjectModel _subProject = createSubproject(getCompanyId(), _parentProject.getId(), new ProjectModel());

		Assert.assertEquals("createSubproject() should return with status OK:", Status.OK.getStatusCode(), getStatus());
		Assert.assertEquals("there should be one parent project:", 1, countProjects(getCompanyId()));
		Assert.assertEquals("there should be one subproject:", 1, countSubprojects(getCompanyId(), _parentProject.getId()));
		Assert.assertNotNull("ID should be set:", _subProject.getId());
		
		deleteProject(getCompanyId(), _subProject.getId());
		deleteProject(getCompanyId(), _parentProject.getId());
	}
	
	@Test
	public void testProjectRead() throws Exception {
		// System.out.println("*** testProjectRead:");
		ArrayList<ProjectModel> _localList = new ArrayList<ProjectModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(createProject(getCompanyId(), new ProjectModel()));
		}
	
		ProjectModel _tmp = null;
		for (ProjectModel _p : _localList) {
			_tmp = readProject(getCompanyId(), _p.getId());
			Assert.assertEquals("Read should return with status OK (local):", Status.OK.getStatusCode(), getStatus());
			Assert.assertEquals("ID should be unchanged after read (local):", _p.getId(), _tmp.getId());
		}
		
		List<ProjectModel> _remoteList = listProjects(getCompanyId());
		for (ProjectModel _p : _remoteList) {
			_tmp = readProject(getCompanyId(), _p.getId());
			Assert.assertEquals("Read should return with status OK (remote):", Status.OK.getStatusCode(), getStatus());
			Assert.assertEquals("ID should be unchanged after read (remote):", _p.getId(), _tmp.getId());						
		}
			
		for (ProjectModel _p : _localList) {
			deleteProject(getCompanyId(), _p.getId());
		}
		Assert.assertEquals("there should be zero projects:", 0, countProjects(getCompanyId()));
		// TODO: "reading a company with ID = null should fail" -> ValidationException
	}
	
	@Test
	public void testProjectReadSubproject() throws Exception {
		// System.out.println("*** testProjectReadSubproject:");
		ProjectModel _topLevelProject = createProject(getCompanyId(), new ProjectModel());
		ProjectModel _parentProject = _topLevelProject;
		ArrayList<ProjectModel> _localList = new ArrayList<ProjectModel>();
		_localList.add(_parentProject);
		
		for (int i = 0; i < LIMIT; i++) {
			_parentProject = createSubproject(getCompanyId(), _parentProject.getId(), new ProjectModel()); 
			_localList.add(_parentProject);
		}
		
		// test list
		List<ProjectModel> _list = listProjects(getCompanyId());
		Assert.assertEquals("there should be one top-level project (count):", 1, countProjects(getCompanyId()));
		Assert.assertEquals("there should be one top-level project (returned from list()):", 1, _list.size());

		ProjectModel _tmp = null;
		for (ProjectModel _p : _localList) {
			_tmp = readProject(getCompanyId(), _p.getId());
			Assert.assertEquals("Read should return with status OK (list):", Status.OK.getStatusCode(), getStatus());
			Assert.assertEquals("ID should be unchanged after read (list):", _p.getId(), _tmp.getId());
		}
		
		// test list/flat
		List<ProjectModel> _listFlat = listProjectsHierarchically(getCompanyId(), false);
		Assert.assertEquals("there should be " + (LIMIT + 1) + " projects (flat):", (LIMIT + 1), _listFlat.size());
		Assert.assertEquals("there should be one top-level project (count):", 1, countProjects(getCompanyId()));

		counter = 0;
		for (ProjectModel _p : _listFlat) {
			_tmp = readProject(getCompanyId(), _p.getId());
			counter++;
			Assert.assertEquals("Read should return with status OK (flat):", Status.OK.getStatusCode(), getStatus());
			Assert.assertEquals("ID should be unchanged after read (flat):", _p.getId(), _tmp.getId());						
		}
		Assert.assertEquals("There should be " + (LIMIT + 1) + " reads (flat):", (LIMIT + 1), counter);						
		
		// test list/astree
		List<ProjectModel> _listAstree = listProjectsHierarchically(getCompanyId(), true);
		Assert.assertEquals("there should be " + 1 + " top-level project (count):", 1, countProjects(getCompanyId()));
		Assert.assertEquals("there should be " + 1 + " top-level project (astree):", 1, _listAstree.size());

		// read all 10 subprojects
		counter = 0;
		for (ProjectModel _p : _listAstree) {
			testReadSubprojects(_p);
		}
		Assert.assertEquals("There should be " + (LIMIT + 1) + " reads (flat):", (LIMIT + 1), counter);						
			
		for (ProjectModel _p : _localList) {
			deleteProject(getCompanyId(), _p.getId());
		}
		Assert.assertEquals("there should be zero projects:", 0, countProjects(getCompanyId()));
	}
	
	private void testReadSubprojects(ProjectModel p) {
		ProjectModel _tmp = readProject(getCompanyId(), p.getId());
		Assert.assertEquals("Read should return with status OK (testReadSubprojects):", Status.OK.getStatusCode(), getStatus());
		Assert.assertEquals("ID should be unchanged after read (testReadSubprojects):", p.getId(), _tmp.getId());						
		counter ++;
		for (ProjectModel _p : p.getProjects()) {
			testReadSubprojects(_p);
		}
	}
	
	@Test
	public void testProjectReadMulti() {
		// System.out.println("*** testProjectReadMulti:");
		ProjectModel _p1 = new ProjectModel();
		ProjectModel _p2 = createProject(getCompanyId(), _p1);
		ProjectModel _p3 = readProject(getCompanyId(), _p2.getId());
		Assert.assertEquals("Reading should return with status OK:", Status.OK.getStatusCode(), getStatus());
		Assert.assertEquals("ID should be unchanged after read:", _p2.getId(), _p3.getId());
		
		ProjectModel _p4 = readProject(getCompanyId(), _p2.getId());
		// but: the two objects are not equal !
		Assert.assertEquals("ID should be the same:", _p3.getId(), _p4.getId());
		Assert.assertEquals("title should be the same:", _p3.getTitle(), _p4.getTitle());
		Assert.assertEquals("description should be the same:", _p3.getDescription(), _p4.getDescription());
		
		Assert.assertEquals("ID should be the same:", _p3.getId(), _p2.getId());
		Assert.assertEquals("title should be the same:", _p3.getTitle(), _p2.getTitle());
		Assert.assertEquals("description should be the same:", _p3.getDescription(), _p2.getDescription());
		
		deleteProject(getCompanyId(), _p2.getId());
		Assert.assertEquals("there should be zero projects:", 0, countProjects(getCompanyId()));
	}
	
	@Test
	public void testProjectUpdate() {
		// System.out.println("*** testProjectUpdate:");
		ProjectModel _p1 = createProject(getCompanyId(), new ProjectModel());
		
		// change the attributes
		_p1.setTitle(MY_TITLE);
		_p1.setDescription(MY_DESC);
		ProjectModel _p2 = updateProject(getCompanyId(), _p1);
		Assert.assertEquals("updateCompany() should return with status OK:", Status.OK.getStatusCode(), getStatus());
		Assert.assertEquals("there should be one additional company:", 1, countProjects(getCompanyId()));
		Assert.assertNotNull("ID should be set:", _p2.getId());
		Assert.assertEquals("ID should be unchanged:", _p1.getId(), _p2.getId());	
		Assert.assertEquals("title should have changed:", MY_TITLE, _p2.getTitle());
		Assert.assertEquals("description should have changed:", MY_DESC, _p2.getDescription());

		// reset the attributes
		_p1.setTitle(MY_TITLE2);
		_p1.setDescription(MY_DESC2);
		ProjectModel _p3 = updateProject(getCompanyId(), _p1);
		Assert.assertEquals("updateCompany() should return with status OK:", Status.OK.getStatusCode(), getStatus());
		Assert.assertEquals("there should be one additional company:", 1, countProjects(getCompanyId()));
		Assert.assertNotNull("ID should be set:", _p3.getId());
		Assert.assertEquals("ID should be unchanged:", _p1.getId(), _p3.getId());	
		Assert.assertEquals("title should have changed:", MY_TITLE2, _p3.getTitle());
		Assert.assertEquals("description should have changed:", MY_DESC2, _p3.getDescription());

		deleteProject(getCompanyId(), _p3.getId());
	}

	@Test
	public void testProjectDelete() {
		// System.out.println("*** testProjectDelete:");
		ProjectModel _p0 = new ProjectModel();
		ProjectModel _p1 = createProject(getCompanyId(), _p0);
		deleteProject(getCompanyId(), _p1.getId());
		Assert.assertEquals("there should be no additional company left:", 0, countProjects(getCompanyId()));
	}
	
	@Test
	public void testProjectDeleteDouble() {
		// System.out.println("*** testProjectDeleteDouble:");
		ProjectModel _p0 = new ProjectModel();
		ProjectModel _p1 = createProject(getCompanyId(), _p0);
		readProject(getCompanyId(), _p1.getId());
		Assert.assertEquals("readProject() should return with status OK:", Status.OK.getStatusCode(), getStatus());		
		
		deleteProject(getCompanyId(), _p1.getId());
		Assert.assertEquals("there should be zero projects:", 0, countProjects(getCompanyId()));
		
		try {
			readProject(getCompanyId(), _p1.getId());	
			fail("readProject() on deleted object should return with status NOT_FOUND");
		} catch(NotFoundException e) {}
		
		deleteProject(getCompanyId(), _p1.getId());
		Assert.assertEquals("2nd deleteProject() should return with status NOT_FOUND:", Status.NOT_FOUND.getStatusCode(), getStatus());
		Assert.assertEquals("there should be zero projects:", 0, countProjects(getCompanyId()));

		try {
			readProject(getCompanyId(), _p1.getId());
			fail("readProject() on deleted object should still return with status NOT_FOUND");
		} catch(NotFoundException e) {}
	}
	
	public static String getCompanyId() {
		if (company == null) {
			throw new RuntimeException("*** ERROR: company is not set !");
		} else {
			return company.getId();
		}
	}

}
