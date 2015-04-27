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

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.ProjectModel;
import org.opentdc.wtt.ResourceRefModel;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.resources.ResourcesTest;

public class ResourceTest extends AbstractTestClient {
	
	public static final String API = "api/company/";	
	
	private static CompanyModel company = null;
	private static final int LIMIT = 3;

	@BeforeClass
	public static void initializeTests(
	) {
		System.out.println("initializing");
		initializeTests(API);
		company = CompanyTest.createCompany(new CompanyModel());
	}
	
	/********************************** project tests *********************************/
	@Test
	public void testResources() throws Exception {
		
		WebClient resourcesWebClient = createWebClient(ResourcesTest.API);
		List<org.opentdc.resources.ResourceModel> resources = new ArrayList<org.opentdc.resources.ResourceModel>();
		for(int i = 0; i < LIMIT; i++) {
			org.opentdc.resources.ResourceModel resource = new org.opentdc.resources.ResourceModel();
			resource.setFirstName("first " + i);
			resource.setLastName("last + i");
			resource.setName("name " + i);
			resources.add(ResourcesTest.create(resource, resourcesWebClient));
		}
		// System.out.println("*** testResources:");
		ProjectModel _p1 = new ProjectModel();
		ProjectModel _p2 = ProjectTest.createProject(getCompanyId(), _p1);
		
		ArrayList<String> _localList = new ArrayList<String>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(ProjectTest.addResource(getCompanyId(), _p2.getId(), resources.get(i).getId()));
			assertEquals("addResource() should return with status OK:", Status.OK.getStatusCode(), ProjectTest.getStatus());
		}
		assertEquals("there should be " + LIMIT + " resources:", LIMIT, ProjectTest.countResources(getCompanyId(), _p2.getId()));
		ArrayList<ResourceRefModel> _remoteList = ProjectTest.listResources(getCompanyId(), _p2.getId());
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
		
		for(int i = 0; i < LIMIT; i++) {
			ResourcesTest.delete(resources.get(i).getId(), resourcesWebClient);
		}
	}
	
	public static String getCompanyId() {
		if (company == null) {
			throw new RuntimeException("*** ERROR: company is not set !");
		} else {
			return company.getId();
		}
	}
	
}
