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
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opentdc.service.exception.DuplicateException;
import org.opentdc.service.exception.NotFoundException;
import org.opentdc.wtt.CompanyModel;

import test.org.opentdc.AbstractTestClient;

public class CompanyTest extends AbstractTestClient {
	
	public static final String API = "api/company/";	
	
	private static final String MY_XRI = "MyXri";
	private static final String MY_TITLE = "MyTitle";
	private static final String MY_DESC = "MyDescription";
	
	@BeforeClass
	public static void initializeTests(
	) {
		System.out.println("initializing");
		initializeTests(API);
	}
	
	public static int getStatus() {
		return status;
	}
	
	public static List<CompanyModel> listCompanies(
	) {
		System.out.println("listing all companies");
		return (List<CompanyModel>)webclient.replacePath("/").getCollection(CompanyModel.class);
	}

	public static CompanyModel createCompany(
		CompanyModel c
	) {
		System.out.println("creating a company");
		Response resp = webclient.replacePath("/").post(c);
		status = resp.getStatus();
		if(resp.getStatus() == Status.CONFLICT.getStatusCode()) {
			throw new DuplicateException();
		} else {
			return resp.readEntity(CompanyModel.class);
		}
	}

	public static CompanyModel readCompany(
		String id
	) {
		System.out.println("reading a company");
		Response resp = webclient.replacePath(id).get();
		status = resp.getStatus();
		if(resp.getStatus() == Status.NOT_FOUND.getStatusCode()) {
			throw new NotFoundException();
		} else {
			return resp.readEntity(CompanyModel.class);
		}
	}

	public static CompanyModel updateCompany(
		CompanyModel c
	) throws NotFoundException {
		System.out.println("updating a company");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		Response resp = webclient.replacePath("/").path(c.getId()).put(c);
		status = resp.getStatus();
		if(status == Status.NOT_FOUND.getStatusCode()) {
			throw new NotFoundException();
		} else {
			return resp.readEntity(CompanyModel.class);
		} 
	}

	public static void deleteCompany(
		String id
	) {
		System.out.println("deleting a company");
		Response resp = webclient.replacePath(id).delete();
		status = resp.getStatus();
		if(resp.getStatus() == Status.NOT_FOUND.getStatusCode()) {
			throw new NotFoundException();
		}
	}

	public static int countCompanies(
	) {
		return webclient.replacePath("count").get(Integer.class);
	}
	
	/********************************** company tests *********************************/
	@Test
	public void testNewCompany() {
		// System.out.println("*** testNew:");
		CompanyModel _c = new CompanyModel();		
		assertEquals("default title should be set:", CompanyModel.DEF_TITLE, _c.getTitle());
		assertEquals("default description should be set:", CompanyModel.DEF_DESC, _c.getDescription());
		assertNotNull("project list should not be null:", _c.getProjects());
		assertEquals("zero projects should be listed:", 0, _c.getProjects().size());
	}
	
	@Test
	public void testCompanyAttributeChange() {		
		// System.out.println("*** testCompanyAttributeChange:");
		CompanyModel _c = new CompanyModel();
		_c.setTitle(MY_TITLE);
		_c.setDescription(MY_DESC);
		assertEquals("title should have changed:", MY_TITLE, _c.getTitle());
		assertEquals("description should have changed:", MY_DESC, _c.getDescription());
		// TODO: try to set invalid data attributes
	}
	
	@Test
	public void testCompanyIdAttribute() {
		// System.out.println("*** testCompanyIdAttribute:");
		CompanyModel _c1 = new CompanyModel();
		CompanyModel _c2 = new CompanyModel();
		
		int _existingCompanies = countCompanies();
		
		assertEquals("there should be no company to start with:", 0, countCompanies() - _existingCompanies);
		
		CompanyModel _c3 = createCompany(_c1);
		CompanyModel _c4 = createCompany(_c2);
		
		assertNotNull("ID should be set:", _c3.getId());
		assertNotNull("ID should be set:", _c4.getId());
		assertNotSame("IDs should be different:", _c3.getId(), _c4.getId());
		assertEquals("there should be two companies:", 2, countCompanies() - _existingCompanies);
				
		deleteCompany(_c3.getId());
		deleteCompany(_c4.getId());
		assertEquals("there should be no company left:", 0, countCompanies() - _existingCompanies);
	}

	@Test
	public void testCompanyList(
	) {
		// System.out.println("*** testCompanyList:");
		int LIMIT = 10;
		int _existingCompanies = countCompanies();
		
		ArrayList<CompanyModel> _localList = new ArrayList<CompanyModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(createCompany(new CompanyModel()));
			assertEquals("createCompany() should return with status OK:", Status.OK.getStatusCode(), getStatus());
		}
		assertEquals("there should be " + LIMIT + " additional companies:", LIMIT, countCompanies() - _existingCompanies);
		List<CompanyModel> _remoteList = listCompanies();
		assertEquals("listCompanies() should return with status OK:", Status.OK.getStatusCode(), getStatus());
		assertEquals("there should be still " + LIMIT + " additional companies:", LIMIT, countCompanies() - _existingCompanies);
		assertEquals("there should be " + LIMIT + " companies locally:", LIMIT, _localList.size());
		assertEquals("there should be " + (LIMIT + _existingCompanies) + " companies remotely:",
				(LIMIT + _existingCompanies), _remoteList.size());
		
		for (CompanyModel _company : _localList) {
			deleteCompany(_company.getId());
		}
		assertEquals("there should be no additional companies left:", 0, countCompanies() - _existingCompanies);
	}
	
	@Test
	public void testCompanyRead(
	) {
		// System.out.println("*** testCompanyRead:");
		int LIMIT = 10;
		int _existingCompanies = countCompanies();
		
		ArrayList<CompanyModel> _localList = new ArrayList<CompanyModel>();
		for (int i = 0; i < LIMIT; i++) {
			_localList.add(createCompany(new CompanyModel()));
		}
	
		CompanyModel _tmp = null;
		for (CompanyModel _o : _localList) {
			_tmp = readCompany(_o.getId());
			assertEquals("Read should return with status OK (local):", Status.OK.getStatusCode(), getStatus());
			assertEquals("ID should be unchanged when reading a company (local):", _o.getId(), _tmp.getId());
		}
		
		List<CompanyModel> _remoteList = listCompanies();
		for (CompanyModel _o : _remoteList) {
			_tmp = readCompany(_o.getId());
			assertEquals("Read should return with status OK (remote):", Status.OK.getStatusCode(), getStatus());
			assertEquals("ID should be unchanged when reading a company (remote):", _o.getId(), _tmp.getId());						
		}
		// TODO: "reading a company with ID = null should fail" -> ValidationException
		// TODO: "reading a non-existing company should fail"
			
		for (CompanyModel _company : _localList) {
			deleteCompany(_company.getId());
		}
		assertEquals("there should be no additional companies left:", 0, countCompanies() - _existingCompanies);
	}

	
	@Test
	public void testCompanyCreate(
	) {	
		// System.out.println("*** testCompanyCreate:");
		CompanyModel _c0 = new CompanyModel();	
		int _existingCompanies = countCompanies();
		CompanyModel _c1 = createCompany(_c0);

		assertEquals("createCompany() should return with status OK:", Status.OK.getStatusCode(), getStatus());
		assertEquals("there should be one additional company:", 1, countCompanies() - _existingCompanies);
		assertNotNull("ID should be set:", _c1.getId());
		assertNotNull("ID should be set:", _c1.getId());
		assertNotNull("xri should be set:", _c1.getXri());
		assertEquals("default title should be set:", CompanyModel.DEF_TITLE, _c1.getTitle());
		assertEquals("default description should be set:", CompanyModel.DEF_DESC, _c1.getDescription());
		assertNotNull("project list should not be null:", _c1.getProjects());
		assertEquals("zero projects should be listed:", 0, _c1.getProjects().size());
		
		deleteCompany(_c0.getId());
	}
	
	@Test
	public void testCompanyDoubleCreate(
	) {
		// System.out.println("*** testCompanyDoubleCreate:");		
		CompanyModel _c1 = new CompanyModel();
		int _existingCompanies = countCompanies();
		CompanyModel _c2 = createCompany(_c1);
		assertEquals("createCompany() should return with status OK:", Status.OK.getStatusCode(), getStatus());
		assertEquals("there should be one additional company:", 1, countCompanies() - _existingCompanies);
		assertNotNull("ID should be set:", _c2.getId());		
		try {
			@SuppressWarnings("unused")
			CompanyModel _c3 = createCompany(_c2);
			fail("creating a double object should result in status CONFLICT(409)");
		} catch(DuplicateException e) {}
		deleteCompany(_c2.getId());
		assertEquals("there should be no additional company left:", 0, countCompanies() - _existingCompanies);
	}

	@Test
	public void testCompanyMultiRead(
	) {
		// System.out.println("*** testCompanyMultiRead:");
		CompanyModel _c1 = new CompanyModel();
		int _existingCompanies = countCompanies();
		CompanyModel _c2 = createCompany(_c1);
		CompanyModel _c3 = readCompany(_c2.getId());
		assertEquals("Reading should return with status OK:", Status.OK.getStatusCode(), getStatus());
		assertEquals("ID should be unchanged after read:", _c2.getId(), _c3.getId());		
		CompanyModel _c4 = readCompany(_c2.getId());
		
		// but: the two objects are not equal !
		assertEquals("ID should be the same:", _c3.getId(), _c4.getId());
		assertEquals("xri should be the same:", _c3.getXri(), _c4.getXri());
		assertEquals("title should be the same:", _c3.getTitle(), _c4.getTitle());
		assertEquals("description should be the same:", _c3.getDescription(), _c4.getDescription());
		
		assertEquals("ID should be the same:", _c3.getId(), _c2.getId());
		assertEquals("xri should be the same:", _c3.getXri(), _c2.getXri());
		assertEquals("title should be the same:", _c3.getTitle(), _c2.getTitle());
		assertEquals("description should be the same:", _c3.getDescription(), _c2.getDescription());
		
		deleteCompany(_c2.getId());
		assertEquals("there should be no additional company left:", 0, countCompanies() - _existingCompanies);
	}
	
	@Test
	public void testCompanyUpdate(
	) {
		// System.out.println("*** testCompanyUpdate:");
		int _existingCompanies = countCompanies();
		CompanyModel _c1 = new CompanyModel();
		CompanyModel _c2 = createCompany(_c1);
		
		// change the attributes
		_c2.setTitle(MY_TITLE);
		_c2.setDescription(MY_DESC);
		CompanyModel _c3 = updateCompany(_c2);
		assertEquals("updateCompany() should return with status OK (1):", Status.OK.getStatusCode(), getStatus());
		assertEquals("there should be one additional company (1):", 1, countCompanies() - _existingCompanies);
		assertNotNull("ID should be set (1):", _c3.getId());
		assertEquals("ID should be unchanged (1):", _c2.getId(), _c3.getId());	
		assertEquals("title should have changed (1):", MY_TITLE, _c3.getTitle());
		assertEquals("description should have changed (1):", MY_DESC, _c3.getDescription());

		// reset the attributes
		_c2.setTitle(CompanyModel.DEF_TITLE);
		_c2.setDescription(CompanyModel.DEF_DESC);
		CompanyModel _c4 = updateCompany(_c2);
		assertEquals("updateCompany() should return with status OK (2):", Status.OK.getStatusCode(), getStatus());
		assertEquals("there should be one additional company (2):", 1, countCompanies() - _existingCompanies);
		assertNotNull("ID should be set (2):", _c4.getId());
		assertEquals("ID should be unchanged (2):", _c2.getId(), _c4.getId());	
		assertEquals("title should have changed (2):", CompanyModel.DEF_TITLE, _c4.getTitle());
		assertEquals("description should have changed (2):", CompanyModel.DEF_DESC, _c4.getDescription());

		deleteCompany(_c2.getId());
	}
	
	@Test
	public void testCompanyDelete(
	) {
		// System.out.println("*** testCompanyDelete:");
		CompanyModel _c0 = new CompanyModel();	
		int _existingCompanies = countCompanies();
		CompanyModel _c1 = createCompany(_c0);
		deleteCompany(_c1.getId());		
		assertEquals("there should be no additional company left:", 0, countCompanies() - _existingCompanies);
	}
	
	@Test
	public void testCompanyDoubleDelete(
	) {
		// System.out.println("*** testCompanyDoubleDelete:");
		CompanyModel _c0 = new CompanyModel();
		int _existingCompanies = countCompanies();
		CompanyModel _c1 = createCompany(_c0);
		readCompany(_c1.getId());
		assertEquals("readCompany() should return with status OK:", Status.OK.getStatusCode(), getStatus());
		deleteCompany(_c1.getId());
		assertEquals("there should be no additional company left:", 0, countCompanies() - _existingCompanies);
		try {
			readCompany(_c1.getId());		
			fail("read() of a deleted object should return with status NOT_FOUND");
		} catch(NotFoundException e) {}
		try {
			deleteCompany(_c1.getId());
			fail("2nd deleteCompany() should return with status NOT_FOUND");
		} catch(NotFoundException e) {}
		assertEquals("there should be no additional company left:", 0, countCompanies() - _existingCompanies);
		try {
			readCompany(_c1.getId());
			fail("readCompany() on deleted object should still return with status NOT_FOUND");
		} catch(NotFoundException e) {}
	}

}
