package test.org.opentdc.wtt;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.wtt.*;

import test.org.opentdc.AbstractTestClient;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ProjectTreeTest extends AbstractTestClient<WttService> {
		
public static final String API = "api/company/";	
	public static final String PATH_EL_PROJECT = "project";
	public static final String PATH_EL_RESOURCE = "resource";
	private static CompanyModel company = null;

	@Before
	public void initializeTest() {
		initializeTest(API, WttService.class);
		Response _response = webclient.replacePath("/").post(new CompanyModel("MY_COMPANY", "MY_DESC"));
		company = _response.readEntity(CompanyModel.class);
	}
		
	@After
	public void cleanupTest() {
		webclient.reset();
//		webclient.replacePath(company.getId()).delete();
	}
	
	private ProjectModel createProject(String title) {
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).post(new ProjectModel(title, "MY_DESC"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		return _response.readEntity(ProjectModel.class);
	}
	
	private ProjectModel createSubProject(String pid, String title) {
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(pid).path(PATH_EL_PROJECT).post(new ProjectModel(title, "MY_DESC"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		return _response.readEntity(ProjectModel.class);	
	}
	
	private ResourceRefModel createResource(String pid) {
		Response _response = webclient.replacePath("/").path(company.getId()).path(PATH_EL_PROJECT).path(pid).path(PATH_EL_RESOURCE).post(new ResourceRefModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		return _response.readEntity(ResourceRefModel.class);	
	}
	
	@Test
	public void testTree() {
		// generate a tree:   1 company - 2 projects with each 3 subprojects and 3 resources
		// generate 2 top level projects -> _p1, _p2,  each with 3 resources
		ProjectModel _p1 = createProject("p1");
		createResource(_p1.getId());
		createResource(_p1.getId());
		createResource(_p1.getId());
		ProjectModel _p2 = createProject("p2");
		createResource(_p2.getId());
		createResource(_p2.getId());
		createResource(_p2.getId());
		
		// generate 3 subprojects on _p1 -> _p1p1, _p1p2, _p1p3, each with 1 resource
		ProjectModel _p1p1 = createSubProject(_p1.getId(), "p1p1");
		createResource(_p1p1.getId());
		createSubProject(_p1p1.getId(), "p1p1p1");
		ProjectModel _p1p2 = createSubProject(_p1.getId(), "p1p2");
		createResource(_p1p2.getId());
		ProjectModel _p1p3 = createSubProject(_p1.getId(), "p1p3");
		createResource(_p1p3.getId());

		// generate 3 subprojects on _p2 -> _p2p1, _p2p2, _p2p3, each with 2 resources
		ProjectModel _p2p1 = createSubProject(_p2.getId(), "p2p1");
		createResource(_p2p1.getId());
		createResource(_p2p1.getId());
		ProjectModel _p2p2 = createSubProject(_p2.getId(), "p2p2");
		createResource(_p2p2.getId());
		createResource(_p2p2.getId());
		ProjectModel _p2p3 = createSubProject(_p2.getId(), "p2p3");
		createResource(_p2p3.getId());
		createResource(_p2p3.getId());
				
		// get the tree
		Response _response = webclient.replacePath("/").path(company.getId()).path("astree").get();
		assertEquals("astree() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		ProjectTreeNodeModel _tree = _response.readEntity(ProjectTreeNodeModel.class);
		
		assertEquals("root node of project tree should be the company", company.getId(), _tree.getId());
		assertEquals("tree should contain two projects", 2, _tree.getProjects().size());
		for (ProjectTreeNodeModel _node : _tree.getProjects()) {
			assertEquals("each project should contain 3 subprojects", 3, _node.getProjects().size());
			assertEquals("each project should contain 3 resources", 3, _node.getResources().size());
			for (ProjectTreeNodeModel _subNode : _node.getProjects()) {
				if (_node.getId().equalsIgnoreCase(_p1.getId())) {
					if (_subNode.getId().equalsIgnoreCase(_p1p1.getId())) {
						assertEquals("subproject should contain 1 subsubproject", 1, _subNode.getProjects().size());
						ProjectTreeNodeModel _subSubNode = _subNode.getProjects().get(0);
						assertEquals("subproject should contain 1 resource", 1, _subNode.getResources().size());
						assertEquals("subsubproject should contain 0 subprojects", 0, _subSubNode.getProjects().size());
						assertEquals("subsubproject should contain 0 resources", 0, _subSubNode.getResources().size());
					}
					else {
						assertEquals("subproject should contain 0 subprojects", 0, _subNode.getProjects().size());
						assertEquals("subproject should contain 1 resource", 1, _subNode.getResources().size());							
					}
				}
				else {				
					assertEquals("each subproject should contain 0 subprojects", 0, _subNode.getProjects().size());
					assertEquals("each subproject should contain 2 resources", 2, _subNode.getResources().size());
				}
			}
		}
		
		
		// TODO: test resources
		
	}

}
