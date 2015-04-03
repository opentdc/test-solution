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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.ProjectModel;
import org.opentdc.wtt.ResourceModel;

import test.org.opentdc.AbstractTestClient;

public class ResourceTest extends AbstractTestClient {
	
	public static final String APP_URI = "http://localhost:8080/opentdc-services-test/api/company/";	
	
	private static CompanyModel company = null;
	private static final int LIMIT = 10;

	@BeforeClass
	public static void initializeTests(
	) {
		System.out.println("initializing");
		initializeTests(APP_URI);
		company = CompanyTest.createCompany(new CompanyModel());
	}
	
	/********************************** project tests *********************************/
	@Test
	public void testResourceAttribute() {
		// System.out.println("*** testResourceAttribute:");
		ProjectModel _p = new ProjectModel();
		assertNotNull("resource list should not be null:", _p.getResources());
		assertEquals("there should be zero resources (1):", 0, _p.getResources().size());
		
		for (int i = 0; i < LIMIT; i++) {
			_p.addResource(Integer.toString(i));
		}
		assertEquals("there should be " + LIMIT + " resources (1):", LIMIT, _p.getResources().size());
		for (int i = 0; i < LIMIT; i++) {
			_p.removeResource(Integer.toString(i));
		}
		assertEquals("there should be zero resources (2):", 0, _p.getResources().size());
		
		ArrayList<ResourceModel> _resources = new ArrayList<ResourceModel>();
		for (int i = 0; i < LIMIT; i++) {
			_resources.add(new ResourceModel("resource" + i));
		}
		_p.setResources(_resources);
		assertEquals("there should be " + LIMIT + " resources (2):", LIMIT, _p.getResources().size());
		ArrayList<ResourceModel> _resources2 = _p.getResources();
		assertEquals("there should be " + LIMIT + " resources (3):", LIMIT, _resources2.size());
		
		for (int i = 0; i < LIMIT; i++) {
			_p.removeResource("resource" + i);
		}
		assertEquals("there should be zero resources (2):", 0, _p.getResources().size());
		assertNotNull("resources list should not be null:", _p.getResources());	
	}
	
	// public ArrayList<String> listResources(String compId, String projId)
	// public String addResource(String compId, String projId, String resourceId)
	// public void deleteResource(String compId, String projId, String resourceId)
	// public int countResources(String compId, String projId)
	@Test
	public void testResources() throws Exception {
		// System.out.println("*** testResources:");
		ProjectModel _p1 = new ProjectModel();
		ProjectModel _p2 = ProjectTest.createProject(getCompanyId(), _p1);
		
		ArrayList<String> _localList = new ArrayList<String>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(ProjectTest.addResource(getCompanyId(), _p2.getId(), "resource" + i));
			assertEquals("addResource() should return with status OK:", Status.OK.getStatusCode(), ProjectTest.getStatus());
		}
		assertEquals("there should be " + LIMIT + " resources:", LIMIT, ProjectTest.countResources(getCompanyId(), _p2.getId()));
		ArrayList<ResourceModel> _remoteList = ProjectTest.listResources(getCompanyId(), _p2.getId());
		assertEquals("listResources() should return with status OK:", Status.OK.getStatusCode(), ProjectTest.getStatus());
		assertEquals("there should be still " + LIMIT + " resources:", LIMIT, ProjectTest.countResources(getCompanyId(), _p2.getId()));
		assertEquals("there should be " + LIMIT + " resources locally:", LIMIT, _localList.size());
		assertEquals("there should be " + LIMIT + " resources remotely:", LIMIT, _remoteList.size());
		
		for (String _resId : _localList) {
			ProjectTest.deleteResource(getCompanyId(), _p2.getId(), _resId);
		}
		assertEquals("there should be zero resources:", 0, ProjectTest.countResources(getCompanyId(), _p2.getId()));
		_remoteList = ProjectTest.listResources(getCompanyId(), _p2.getId());
		assertEquals("listResources() should return with status OK:", Status.OK.getStatusCode(), ProjectTest.getStatus());
		assertEquals("there should be still zero resources:", 0, ProjectTest.countResources(getCompanyId(), _p2.getId()));
		assertEquals("there should be still " + LIMIT + " resources locally:", LIMIT, _localList.size());
		assertEquals("there should be zero resources remotely:", 0, _remoteList.size());
		
		ProjectTest.deleteProject(getCompanyId(), _p2.getId());
		assertEquals("there should be zero projects:", 0, ProjectTest.countProjects(getCompanyId()));
	}
	
	@Test
	public void testResourceByProject() throws Exception {
		// System.out.println("*** testResourceByProject:");
		ProjectModel _p1 = new ProjectModel();
		for (int i = 0; i < LIMIT; i++) {
			_p1.addResource("resource" + i);
		}
		ProjectModel _p2 = ProjectTest.createProject(getCompanyId(), _p1);
		List<ResourceModel> _list = ProjectTest.listResources(getCompanyId(), _p2.getId());
		assertEquals("listResources() should return with status OK (1):", Status.OK.getStatusCode(), ProjectTest.getStatus());
		assertEquals("there should be " + LIMIT + " resources remotely:", LIMIT, ProjectTest.countResources(getCompanyId(), _p2.getId()));
		assertEquals("there should be " + LIMIT + " resources in local resource (1):", LIMIT, _p2.getResources().size());		
		assertEquals("there should be " + LIMIT + " resources in local list:", LIMIT, _list.size());
		
		_p2.removeResource("resource1");
		assertEquals("there should be " + (LIMIT-1) + " resources in local resource (2):", (LIMIT-1), _p2.getResources().size());
		
		ProjectModel _p3 = ProjectTest.updateProject(getCompanyId(), _p2);
		assertEquals("there should be " + (LIMIT-1) + " resources in local resource (3):", (LIMIT-1), _p3.getResources().size());
		
		_list = ProjectTest.listResources(getCompanyId(), _p3.getId());
		assertEquals("listResources() should return with status OK (2):", Status.OK.getStatusCode(), ProjectTest.getStatus());
		assertEquals("there should be " + (LIMIT-1) + " resources remotely:", (LIMIT-1), ProjectTest.countResources(getCompanyId(), _p2.getId()));
		assertEquals("there should be " + (LIMIT-1) + " resources in local resource (4):", (LIMIT-1), _p2.getResources().size());		
		assertEquals("there should be " + (LIMIT-1) + " resources in local list:", (LIMIT-1), _list.size());
		
		ProjectTest.deleteProject(getCompanyId(), _p3.getId());
		assertEquals("there should be zero projects:", 0, ProjectTest.countProjects(getCompanyId()));
	}
	
	public static String getCompanyId() {
		if (company == null) {
			throw new RuntimeException("*** ERROR: company is not set !");
		} else {
			return company.getId();
		}
	}
	
}
