package test.org.opentdc.wtt;

import static org.junit.Assert.*;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.ContactModel;
import org.opentdc.resources.ResourceModel;
import org.opentdc.resources.ResourcesService;
import org.opentdc.service.ServiceUtil;
import org.opentdc.wtt.*;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.addressbooks.AddressbookTest;
import test.org.opentdc.addressbooks.ContactTest;
import test.org.opentdc.resources.ResourcesTest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ProjectTreeTest extends AbstractTestClient {
	private WebClient wttWC = null;
	private WebClient addressbookWC = null;
	private CompanyModel company = null;
	private AddressbookModel addressbook = null;
	private WebClient resourceWC = null;
	private ResourceModel resource = null;
	private ContactModel contact = null;

	@Before
	public void initializeTests() {
		wttWC = createWebClient(ServiceUtil.WTT_API_URL, WttService.class);
		resourceWC = createWebClient(ServiceUtil.RESOURCES_API_URL, ResourcesService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		
		addressbook = AddressbookTest.createAddressbook(addressbookWC, this.getClass().getName());
		company = CompanyTest.createCompany(wttWC, addressbookWC, addressbook, this.getClass().getName(), "MY_DESC");
		contact = ContactTest.createContact(addressbookWC, addressbook.getId(), "FNAME", "LNAME");
		resource = ResourcesTest.createResource(resourceWC, addressbook, contact, this.getClass().getName(), Status.OK);
	}

	@After
	public void cleanupTest() {
		AddressbookTest.cleanup(addressbookWC, addressbook.getId(), this.getClass().getName());
		ResourcesTest.cleanup(resourceWC, resource.getId(), this.getClass().getName());
		CompanyTest.cleanup(wttWC, company.getId(), this.getClass().getName());
	}
	
	private ProjectModel createProject(String title) {
		return ProjectTest.createProject(wttWC, company.getId(), title, "MY_DESC");
	}

	private ProjectModel createSubProject(String parentProjectId, String title) {
		return SubProjectTest.createSubProject(wttWC, company.getId(), parentProjectId, title, "MY_DESC");
	}
	
	private void createResourceRef(String projectId) {
		ResourceRefTest.createResourceRef(wttWC, company.getId(), projectId, resource.getId());
	}
		
	@Test
	public void testTree() {
		// generate a tree:   1 company - 2 projects with each 3 subprojects and 3 resources
		// generate 2 top level projects -> _p1, _p2,  each with 3 resources
		ProjectModel _p1 = createProject("p1");
		createResourceRef(_p1.getId());
		createResourceRef(_p1.getId());
		createResourceRef(_p1.getId());
		ProjectModel _p2 = createProject("p2");
		createResourceRef(_p2.getId());
		createResourceRef(_p2.getId());
		createResourceRef(_p2.getId());
		
		// generate 3 subprojects on _p1 -> _p1p1, _p1p2, _p1p3, each with 1 resource
		ProjectModel _p1p1 = createSubProject(_p1.getId(), "p1p1");
		createResourceRef(_p1p1.getId());
		createSubProject(_p1p1.getId(), "p1p1p1");
		ProjectModel _p1p2 = createSubProject(_p1.getId(), "p1p2");
		createResourceRef(_p1p2.getId());
		ProjectModel _p1p3 = createSubProject(_p1.getId(), "p1p3");
		createResourceRef(_p1p3.getId());

		// generate 3 subprojects on _p2 -> _p2p1, _p2p2, _p2p3, each with 2 resources
		ProjectModel _p2p1 = createSubProject(_p2.getId(), "p2p1");
		createResourceRef(_p2p1.getId());
		createResourceRef(_p2p1.getId());
		ProjectModel _p2p2 = createSubProject(_p2.getId(), "p2p2");
		createResourceRef(_p2p2.getId());
		createResourceRef(_p2p2.getId());
		ProjectModel _p2p3 = createSubProject(_p2.getId(), "p2p3");
		createResourceRef(_p2p3.getId());
		createResourceRef(_p2p3.getId());
				
		// get the tree
		Response _response = wttWC.replacePath("/").path(company.getId()).path("astree").get();
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
		
		
		// TODO: test resourceRefs
		
	}

}
