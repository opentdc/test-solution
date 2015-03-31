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

//@Test public void method() -> identifies a test method
//@Test(expected = Exception.class) -> fails if the method does not throw the named exception
//@Test(timeout=100) -> fails if the method takes longer than 100 milliseconds
//@Before public void method() -> executed before each test
//@After public void method() -> executed after each test
//@BeforeClass public static void method() -> executed once, before the start of all tests
//@AfterClass public static void method() -> executed once, after all tests have been finished,
//			used for cleanup, disconnects
//@Ignore -> ignores a test method. Useful when the underlying code has been changed
//			and the test case has not yet been adapted.

public class CompanyTest {
	private static final String MY_XRI = "MyXri";
	private static final String MY_TITLE = "MyTitle";
	private static final String MY_DESC = "MyDescription";
	private static final int LIMIT = 10;
	
	private CompanyClient client = null;

	@BeforeClass
	public static void initAll() {
		// System.out.println("> initAll()");
	}
	
	@AfterClass
	public static void cleanupAll() {
		// System.out.println("> cleanupAll()");
	}
	
	@Before
	public void initTest() {
		// System.out.println("> initTest()");
		client = new CompanyClient();
	}

	/********************************** company tests *********************************/
	@Test
	public void testNewCompany() {
		// System.out.println("*** testNew:");
		CompanyModel _c = new CompanyModel();
		
		Assert.assertNotNull("ID should be set:", _c.getId());
		Assert.assertEquals("default xri should be set:", CompanyModel.DEF_XRI, _c.getXri());
		Assert.assertEquals("default title should be set:", CompanyModel.DEF_TITLE, _c.getTitle());
		Assert.assertEquals("default description should be set:", CompanyModel.DEF_DESC, _c.getDescription());
		Assert.assertNotNull("project list should not be null:", _c.getProjects());
		Assert.assertEquals("zero projects should be listed:", 0, _c.getProjects().size());
	}
	
	@Test
	public void testCompanyAttributeChange() {		
		// System.out.println("*** testCompanyAttributeChange:");
		CompanyModel _c = new CompanyModel();
		_c.setXri(MY_XRI);
		_c.setTitle(MY_TITLE);
		_c.setDescription(MY_DESC);
		Assert.assertEquals("xri should have changed:", MY_XRI, _c.getXri());
		Assert.assertEquals("title should have changed:", MY_TITLE, _c.getTitle());
		Assert.assertEquals("description should have changed:", MY_DESC, _c.getDescription());
		// TODO: try to set invalid data attributes
	}
	
	@Test
	public void testCompanyProjectAttribute() {
		// System.out.println("*** testCompanyProjectAttribute:");
		CompanyModel _c = new CompanyModel();
		Assert.assertNotNull("project list should not be null:", _c.getProjects());
		Assert.assertEquals("there should be zero projects (1):", 0, _c.getProjects().size());
		
		ArrayList<String> _ids = new ArrayList<String>();
		ProjectModel _p = null;
		for (int i = 0; i < LIMIT; i++) {
			_p = new ProjectModel("title" + i, "description" + i);
			_ids.add(_p.getId());
			_c.addProject(_p);
		}
		Assert.assertEquals("there should be " + LIMIT + " projects:", LIMIT, _c.getProjects().size());
		for (int i = 0; i < LIMIT; i++) {
			Assert.assertEquals("removeProject() should return with true:", true, _c.removeProject(_ids.get(i)));
		}		
		Assert.assertEquals("there should be zero projects (2):", 0, _c.getProjects().size());
		
		ArrayList<ProjectModel> _projects = new ArrayList<ProjectModel>();
		_ids = new ArrayList<String>();
		for (int i = 0; i < LIMIT; i++) {
			_p = new ProjectModel("title" + i, "description" + i);
			_ids.add(_p.getId());
			_projects.add(_p);
		}
		_c.setProjects(_projects);
		Assert.assertEquals("there should be " + LIMIT + " projects (3):", LIMIT, _c.getProjects().size());
		ArrayList<ProjectModel> _projects2 = _c.getProjects();
		Assert.assertEquals("there should be " + LIMIT + " projects (4):", LIMIT, _projects2.size());
		
		for (int i = 0; i < LIMIT; i++) {
			Assert.assertEquals("removeProject() should return with true:", true, _c.removeProject(_ids.get(i)));
		}
		Assert.assertEquals("there should be zero projects (5):", 0, _c.getProjects().size());
		Assert.assertNotNull("project list should not be null:", _c.getProjects());
	}
	
	@Test
	public void testCompanyIdAttribute() {
		// System.out.println("*** testCompanyIdAttribute:");
		CompanyModel _c1 = new CompanyModel();
		CompanyModel _c2 = new CompanyModel();
		
		int _existingCompanies = client.countCompanies();
		
		Assert.assertNotNull("ID should be set:", _c1.getId());
		Assert.assertNotNull("ID should be set:", _c2.getId());
		Assert.assertNotSame("IDs should be different:", _c1.getId(), _c2.getId());
		Assert.assertEquals("there should be no company to start with:", 0, client.countCompanies() - _existingCompanies);
		
		CompanyModel _c3 = client.createCompany(_c1);
		CompanyModel _c4 = client.createCompany(_c2);
		
		Assert.assertNotNull("ID should be set:", _c1.getId());
		Assert.assertNotNull("ID should be set:", _c2.getId());
		Assert.assertNotNull("ID should be set:", _c3.getId());
		Assert.assertNotNull("ID should be set:", _c4.getId());
		Assert.assertNotSame("IDs should be different:", _c1.getId(), _c2.getId());
		Assert.assertEquals("IDs should be equal:", 		_c1.getId(), _c3.getId());
		Assert.assertNotSame("IDs should be different:", _c3.getId(), _c4.getId());
		Assert.assertEquals("IDs should be equal:", 		_c4.getId(), _c2.getId());
		Assert.assertEquals("there should be two companies:", 2, client.countCompanies() - _existingCompanies);
				
		client.deleteCompany(_c1.getId());
		client.deleteCompany(_c2.getId());
		Assert.assertEquals("there should be no company left:", 0, client.countCompanies() - _existingCompanies);
	}
			
	@Test
	public void testCompanyList() throws Exception {
		// System.out.println("*** testCompanyList:");
		int LIMIT = 10;
		int _existingCompanies = client.countCompanies();
		
		ArrayList<CompanyModel> _localList = new ArrayList<CompanyModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(client.createCompany(new CompanyModel()));
			Assert.assertEquals("createCompany() should return with status OK:", Status.OK.getStatusCode(), client.getStatus());
		}
		Assert.assertEquals("there should be " + LIMIT + " additional companies:", LIMIT, client.countCompanies() - _existingCompanies);
		List<CompanyModel> _remoteList = client.listCompanies();
		Assert.assertEquals("listCompanies() should return with status OK:", Status.OK.getStatusCode(), client.getStatus());
		Assert.assertEquals("there should be still " + LIMIT + " additional companies:", LIMIT, client.countCompanies() - _existingCompanies);
		Assert.assertEquals("there should be " + LIMIT + " companies locally:", LIMIT, _localList.size());
		Assert.assertEquals("there should be " + (LIMIT + _existingCompanies) + " companies remotely:",
				(LIMIT + _existingCompanies), _remoteList.size());
		
		for (CompanyModel _company : _localList) {
			client.deleteCompany(_company.getId());
		}
		Assert.assertEquals("there should be no additional companies left:", 0, client.countCompanies() - _existingCompanies);
	}
	
	@Test
	public void testCompanyRead() throws Exception {
		// System.out.println("*** testCompanyRead:");
		int LIMIT = 10;
		int _existingCompanies = client.countCompanies();
		
		ArrayList<CompanyModel> _localList = new ArrayList<CompanyModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(client.createCompany(new CompanyModel()));
		}
	
		CompanyModel _tmp = null;
		for (CompanyModel _o : _localList) {
			_tmp = client.readCompany(_o.getId());
			Assert.assertEquals("Read should return with status OK (local):", Status.OK.getStatusCode(), client.getStatus());
			Assert.assertEquals("ID should be unchanged when reading a company (local):", _o.getId(), _tmp.getId());
		}
		
		List<CompanyModel> _remoteList = client.listCompanies();
		for (CompanyModel _o : _remoteList) {
			_tmp = client.readCompany(_o.getId());
			Assert.assertEquals("Read should return with status OK (remote):", Status.OK.getStatusCode(), client.getStatus());
			Assert.assertEquals("ID should be unchanged when reading a company (remote):", _o.getId(), _tmp.getId());						
		}
		// TODO: "reading a company with ID = null should fail" -> ValidationException
		// TODO: "reading a non-existing company should fail"
			
		for (CompanyModel _company : _localList) {
			client.deleteCompany(_company.getId());
		}
		Assert.assertEquals("there should be no additional companies left:", 0, client.countCompanies() - _existingCompanies);
	}

	
	@Test
	public void testCompanyCreate() {	
		// System.out.println("*** testCompanyCreate:");
		CompanyModel _c0 = new CompanyModel();	
		int _existingCompanies = client.countCompanies();
		CompanyModel _c1 = client.createCompany(_c0);

		Assert.assertEquals("createCompany() should return with status OK:", Status.OK.getStatusCode(), client.getStatus());
		Assert.assertEquals("there should be one additional company:", 1, client.countCompanies() - _existingCompanies);
		Assert.assertNotNull("ID should be set:", _c1.getId());
		Assert.assertEquals("ID should be unchanged:", _c1.getId(), _c0.getId());
		Assert.assertEquals("default xri should be set:", CompanyModel.DEF_XRI, _c1.getXri());
		Assert.assertEquals("default title should be set:", CompanyModel.DEF_TITLE, _c1.getTitle());
		Assert.assertEquals("default description should be set:", CompanyModel.DEF_DESC, _c1.getDescription());
		Assert.assertNotNull("project list should not be null:", _c1.getProjects());
		Assert.assertEquals("zero projects should be listed:", 0, _c1.getProjects().size());
		
		client.deleteCompany(_c0.getId());
	}
	
	@Test
	public void testCompanyDoubleCreate() {
		// System.out.println("*** testCompanyDoubleCreate:");
		
		CompanyModel _c1 = new CompanyModel();
		int _existingCompanies = client.countCompanies();
		CompanyModel _c2 = client.createCompany(_c1);
		Assert.assertEquals("createCompany() should return with status OK:", Status.OK.getStatusCode(), client.getStatus());
		Assert.assertEquals("there should be one additional company:", 1, client.countCompanies() - _existingCompanies);
		Assert.assertNotNull("ID should be set:", _c2.getId());
		Assert.assertEquals("ID should be unchanged:", _c1.getId(), _c2.getId());		
		
		CompanyModel _c3 = client.createCompany(_c1);
		Assert.assertEquals("creating a double object should result in status CONFLICT(409):", 
				Status.CONFLICT.getStatusCode(), client.getStatus()); // DuplicateException
		// TODO: what is the correct value of _c3 ? same as _o or null ?
		
		client.deleteCompany(_c1.getId());
		Assert.assertEquals("deleteCompany() should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), client.getStatus());
		Assert.assertEquals("there should be no additional company left:", 0, client.countCompanies() - _existingCompanies);
		
	}
	
	@Test
	public void testCompanyMultiRead() {
		// System.out.println("*** testCompanyMultiRead:");
		CompanyModel _c1 = new CompanyModel();
		int _existingCompanies = client.countCompanies();
		CompanyModel _c2 = client.createCompany(_c1);
		CompanyModel _c3 = client.readCompany(_c1.getId());
		Assert.assertEquals("Reading should return with status OK:", Status.OK.getStatusCode(), client.getStatus());
		Assert.assertEquals("ID should be unchanged after read:", _c1.getId(), _c3.getId());
		
		CompanyModel _c4 = client.readCompany(_c1.getId());
		Assert.assertEquals("Reading (2) should return with status OK:", Status.OK.getStatusCode(), client.getStatus());
		Assert.assertEquals("ID should be unchanged after read (2):", _c1.getId(), _c4.getId());
		Assert.assertEquals("xri should be unchanged after read (2):", _c1.getXri(), _c4.getXri());
		Assert.assertEquals("title should be unchanged after read (2):", _c1.getTitle(), _c4.getTitle());
		Assert.assertEquals("description should be unchanged after read (2):", _c1.getDescription(), _c4.getDescription());
		// but: the two objects are not equal !
		Assert.assertEquals("ID should be the same:", _c3.getId(), _c4.getId());
		Assert.assertEquals("xri should be the same:", _c3.getXri(), _c4.getXri());
		Assert.assertEquals("title should be the same:", _c3.getTitle(), _c4.getTitle());
		Assert.assertEquals("description should be the same:", _c3.getDescription(), _c4.getDescription());
		
		Assert.assertEquals("ID should be the same:", _c3.getId(), _c2.getId());
		Assert.assertEquals("xri should be the same:", _c3.getXri(), _c2.getXri());
		Assert.assertEquals("title should be the same:", _c3.getTitle(), _c2.getTitle());
		Assert.assertEquals("description should be the same:", _c3.getDescription(), _c2.getDescription());
		
		client.deleteCompany(_c1.getId());
		Assert.assertEquals("deleteCompany() should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), client.getStatus());
		Assert.assertEquals("there should be no additional company left:", 0, client.countCompanies() - _existingCompanies);
	}
	
	@Test
	public void testCompanyUpdate() {
		// System.out.println("*** testCompanyUpdate:");
		int _existingCompanies = client.countCompanies();
		CompanyModel _c1 = new CompanyModel();
		client.createCompany(_c1);
		
		// change the attributes
		_c1.setXri(MY_XRI);
		_c1.setTitle(MY_TITLE);
		_c1.setDescription(MY_DESC);
		CompanyModel _c2 = client.updateCompany(_c1);
		Assert.assertEquals("updateCompany() should return with status OK (1):", Status.OK.getStatusCode(), client.getStatus());
		Assert.assertEquals("there should be one additional company (1):", 1, client.countCompanies() - _existingCompanies);
		Assert.assertNotNull("ID should be set (1):", _c2.getId());
		Assert.assertEquals("ID should be unchanged (1):", _c1.getId(), _c2.getId());	
		Assert.assertEquals("xri should have changed (1):", MY_XRI, _c2.getXri());
		Assert.assertEquals("title should have changed (1):", MY_TITLE, _c2.getTitle());
		Assert.assertEquals("description should have changed (1):", MY_DESC, _c2.getDescription());

		// reset the attributes
		_c1.setXri(CompanyModel.DEF_XRI);
		_c1.setTitle(CompanyModel.DEF_TITLE);
		_c1.setDescription(CompanyModel.DEF_DESC);
		CompanyModel _c3 = client.updateCompany(_c1);
		Assert.assertEquals("updateCompany() should return with status OK (2):", Status.OK.getStatusCode(), client.getStatus());
		Assert.assertEquals("there should be one additional company (2):", 1, client.countCompanies() - _existingCompanies);
		Assert.assertNotNull("ID should be set (2):", _c3.getId());
		Assert.assertEquals("ID should be unchanged (2):", _c1.getId(), _c3.getId());	
		Assert.assertEquals("xri should have changed (2):", CompanyModel.DEF_XRI, _c3.getXri());
		Assert.assertEquals("title should have changed (2):", CompanyModel.DEF_TITLE, _c3.getTitle());
		Assert.assertEquals("description should have changed (2):", CompanyModel.DEF_DESC, _c3.getDescription());

		client.deleteCompany(_c3.getId());
	}
	
	@Test
	public void testCompanyDelete() {
		// System.out.println("*** testCompanyDelete:");
		CompanyModel _c = new CompanyModel();	
		int _existingCompanies = client.countCompanies();
		client.createCompany(_c);
		client.deleteCompany(_c.getId());
		
		Assert.assertEquals("deleteCompany() should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), client.getStatus());
		Assert.assertEquals("there should be no additional company left:", 0, client.countCompanies() - _existingCompanies);
	}
	
	@Test
	public void testCompanyDoubleDelete() {
		// System.out.println("*** testCompanyDoubleDelete:");
		CompanyModel _c = new CompanyModel();
		int _existingCompanies = client.countCompanies();
		client.createCompany(_c);
		client.readCompany(_c.getId());
		Assert.assertEquals("readCompany() should return with status OK:", Status.OK.getStatusCode(), client.getStatus());		
		
		client.deleteCompany(_c.getId());
		Assert.assertEquals("deleteCompany() should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), client.getStatus());
		Assert.assertEquals("there should be no additional company left:", 0, client.countCompanies() - _existingCompanies);
		client.readCompany(_c.getId());
		
		Assert.assertEquals("read() of a deleted object should return with status NOT_FOUND:", Status.NOT_FOUND.getStatusCode(), client.getStatus());
		
		client.deleteCompany(_c.getId());
		Assert.assertEquals("2nd deleteCompany() should return with status NOT_FOUND:", Status.NOT_FOUND.getStatusCode(), client.getStatus());
		Assert.assertEquals("there should be no additional company left:", 0, client.countCompanies() - _existingCompanies);

		client.readCompany(_c.getId());
		Assert.assertEquals("readCompany() on deleted object should still return with status NOT_FOUND:", Status.NOT_FOUND.getStatusCode(), client.getStatus());
	}
		
	/********************************** main *********************************/
	public static void main(String[] args) {
		Result _result = 
				JUnitCore.runClasses(CompanyTest.class);
		System.out.println(_result);
	}
}
