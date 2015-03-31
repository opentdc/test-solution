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

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.ProjectModel;
import org.opentdc.wtt.ResourceModel;

public class ResourceTest {
	private static CompanyClient companyClient = null;
	private static CompanyModel company = null;
	private ProjectClient projectClient = null;
	private static final int LIMIT = 10;

	@BeforeClass
	public static void initAll() {
		// System.out.println("> initAll()");
		companyClient = new CompanyClient();
		company = companyClient.createCompany(new CompanyModel());
		// System.out.println("company " + getCompanyId() + " created.");
	}
	
	@AfterClass
	public static void cleanupAll() {
		// System.out.println("> cleanupAll()");
		companyClient.deleteCompany(getCompanyId());
	}
	
	@Before
	public void initTest() {
		// System.out.println("> initTest()");
		projectClient = new ProjectClient();
	}
	
	/********************************** project tests *********************************/
	@Test
	public void testResourceAttribute() {
		// System.out.println("*** testResourceAttribute:");
		ProjectModel _p = new ProjectModel();
		Assert.assertNotNull("resource list should not be null:", _p.getResources());
		Assert.assertEquals("there should be zero resources (1):", 0, _p.getResources().size());
		
		for (int i = 0; i < LIMIT; i++) {
			_p.addResource(Integer.toString(i));
		}
		Assert.assertEquals("there should be " + LIMIT + " resources (1):", LIMIT, _p.getResources().size());
		for (int i = 0; i < LIMIT; i++) {
			_p.removeResource(Integer.toString(i));
		}
		Assert.assertEquals("there should be zero resources (2):", 0, _p.getResources().size());
		
		ArrayList<ResourceModel> _resources = new ArrayList<ResourceModel>();
		for (int i = 0; i < LIMIT; i++) {
			_resources.add(new ResourceModel("resource" + i));
		}
		_p.setResources(_resources);
		Assert.assertEquals("there should be " + LIMIT + " resources (2):", LIMIT, _p.getResources().size());
		ArrayList<ResourceModel> _resources2 = _p.getResources();
		Assert.assertEquals("there should be " + LIMIT + " resources (3):", LIMIT, _resources2.size());
		
		for (int i = 0; i < LIMIT; i++) {
			_p.removeResource("resource" + i);
		}
		Assert.assertEquals("there should be zero resources (2):", 0, _p.getResources().size());
		Assert.assertNotNull("resources list should not be null:", _p.getResources());	
	}
	
	// public ArrayList<String> listResources(String compId, String projId)
	// public String addResource(String compId, String projId, String resourceId)
	// public void deleteResource(String compId, String projId, String resourceId)
	// public int countResources(String compId, String projId)
	@Test
	public void testResources() throws Exception {
		// System.out.println("*** testResources:");
		ProjectModel _p1 = new ProjectModel();
		ProjectModel _p2 = projectClient.createProject(getCompanyId(), _p1);
		
		ArrayList<String> _localList = new ArrayList<String>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(projectClient.addResource(getCompanyId(), _p2.getId(), "resource" + i));
			Assert.assertEquals("addResource() should return with status OK:", Status.OK.getStatusCode(), projectClient.getStatus());
		}
		Assert.assertEquals("there should be " + LIMIT + " resources:", LIMIT, projectClient.countResources(getCompanyId(), _p2.getId()));
		ArrayList<ResourceModel> _remoteList = projectClient.listResources(getCompanyId(), _p2.getId());
		Assert.assertEquals("listResources() should return with status OK:", Status.OK.getStatusCode(), projectClient.getStatus());
		Assert.assertEquals("there should be still " + LIMIT + " resources:", LIMIT, projectClient.countResources(getCompanyId(), _p2.getId()));
		Assert.assertEquals("there should be " + LIMIT + " resources locally:", LIMIT, _localList.size());
		Assert.assertEquals("there should be " + LIMIT + " resources remotely:", LIMIT, _remoteList.size());
		
		for (String _resId : _localList) {
			projectClient.deleteResource(getCompanyId(), _p2.getId(), _resId);
		}
		Assert.assertEquals("there should be zero resources:", 0, projectClient.countResources(getCompanyId(), _p2.getId()));
		_remoteList = projectClient.listResources(getCompanyId(), _p2.getId());
		Assert.assertEquals("listResources() should return with status OK:", Status.OK.getStatusCode(), projectClient.getStatus());
		Assert.assertEquals("there should be still zero resources:", 0, projectClient.countResources(getCompanyId(), _p2.getId()));
		Assert.assertEquals("there should be still " + LIMIT + " resources locally:", LIMIT, _localList.size());
		Assert.assertEquals("there should be zero resources remotely:", 0, _remoteList.size());
		
		projectClient.deleteProject(getCompanyId(), _p2.getId());
		Assert.assertEquals("there should be zero projects:", 0, projectClient.countProjects(getCompanyId()));
	}
	
	@Test
	public void testResourceByProject() throws Exception {
		// System.out.println("*** testResourceByProject:");
		ProjectModel _p1 = new ProjectModel();
		for (int i = 0; i < LIMIT; i++) {
			_p1.addResource("resource" + i);
		}
		ProjectModel _p2 = projectClient.createProject(getCompanyId(), _p1);
		List<ResourceModel> _list = projectClient.listResources(getCompanyId(), _p2.getId());
		Assert.assertEquals("listResources() should return with status OK (1):", Status.OK.getStatusCode(), projectClient.getStatus());
		Assert.assertEquals("there should be " + LIMIT + " resources remotely:", LIMIT, projectClient.countResources(getCompanyId(), _p2.getId()));
		Assert.assertEquals("there should be " + LIMIT + " resources in local resource (1):", LIMIT, _p2.getResources().size());		
		Assert.assertEquals("there should be " + LIMIT + " resources in local list:", LIMIT, _list.size());
		
		_p2.removeResource("resource1");
		Assert.assertEquals("there should be " + (LIMIT-1) + " resources in local resource (2):", (LIMIT-1), _p2.getResources().size());
		
		ProjectModel _p3 = projectClient.updateProject(getCompanyId(), _p2);
		Assert.assertEquals("there should be " + (LIMIT-1) + " resources in local resource (3):", (LIMIT-1), _p3.getResources().size());
		
		_list = projectClient.listResources(getCompanyId(), _p3.getId());
		Assert.assertEquals("listResources() should return with status OK (2):", Status.OK.getStatusCode(), projectClient.getStatus());
		Assert.assertEquals("there should be " + (LIMIT-1) + " resources remotely:", (LIMIT-1), projectClient.countResources(getCompanyId(), _p2.getId()));
		Assert.assertEquals("there should be " + (LIMIT-1) + " resources in local resource (4):", (LIMIT-1), _p2.getResources().size());		
		Assert.assertEquals("there should be " + (LIMIT-1) + " resources in local list:", (LIMIT-1), _list.size());
		
		projectClient.deleteProject(getCompanyId(), _p3.getId());
		Assert.assertEquals("there should be zero projects:", 0, projectClient.countProjects(getCompanyId()));
	}
	
	public static String getCompanyId() {
		if (company == null) {
			System.out.println("*** ERROR: company is not set !");
			System.exit(-1);
		}
		// System.out.println("using company " + company.getId());
		return company.getId();
	}
	
	/********************************** main *********************************/
	public static void main(String[] args) {
		Result _result = 
				JUnitCore.runClasses(ResourceTest.class);
		System.out.println(_result);
	}
}
