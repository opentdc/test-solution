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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.ProjectModel;
import org.opentdc.wtt.WttService;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.OrgModel;
import org.opentdc.addressbooks.OrgType;
import org.opentdc.service.GenericService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.addressbooks.AddressbookTest;
import test.org.opentdc.addressbooks.OrgTest;

/**
 * Testing lists of subprojects in time tracking service WTT.
 * @author Bruno Kaiser
 *
 */
public class SubProjectListTest extends AbstractTestClient {
	private static final String CN = "SubProjectListTest";
	private static WebClient wc = null;
	private static WebClient addressbookWC = null;
	private static CompanyModel company = null;
	private static ProjectModel parentProject = null;
	private static AddressbookModel addressbook = null;
	private static OrgModel org = null;
	private static ArrayList<ProjectModel> testObjects = null;

	/**
	 * Initialize the test with several subProjects
	 */
	@BeforeClass
	public static void initializeTest() {
		wc = createWebClient(ServiceUtil.WTT_API_URL, WttService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);

		addressbook = AddressbookTest.post(addressbookWC, 
				new AddressbookModel(CN), Status.OK);
		org = OrgTest.post(addressbookWC, addressbook.getId(), 
				new OrgModel(CN, OrgType.ORGUNIT), Status.OK);
		company = CompanyTest.post(wc, 
				new CompanyModel(CN, "MY_DESC", org.getId()), Status.OK);
		parentProject = ProjectTest.post(wc, company.getId(), 
				new ProjectModel(CN, "MY_DESC"), Status.OK);

		System.out.println("***** " + CN);
		testObjects = new ArrayList<ProjectModel>();
		for (int i = 0; i < (2 * GenericService.DEF_SIZE + 5); i++) { // if DEF_SIZE == 25 -> _limit2 = 55
			testObjects.add(SubProjectTest.post(wc, company.getId(), parentProject.getId(), new ProjectModel(CN + i, "MY_DESC"), Status.OK));
		}
		System.out.println("created " + testObjects.size() + " test objects");
		printModelList("testObjects", testObjects);
}

	/**
	 * Remove all allocated test resources.
	 */
	@AfterClass
	public static void cleanupTest() {
		AddressbookTest.delete(addressbookWC, addressbook.getId(), Status.NO_CONTENT);
		addressbookWC.close();
		
		CompanyTest.delete(wc, company.getId(), Status.NO_CONTENT);
		wc.close();
	}
	
	/**
	 * Test whether all allocated test objects are listed.
	 */
	@Test
	public void testAllListed() {
		List<ProjectModel> _list = SubProjectTest.list(wc, company.getId(), parentProject.getId(), null, 0, Integer.MAX_VALUE, Status.OK);
		printModelList("testAllListed", _list);
		ArrayList<String> _ids = new ArrayList<String>();
		for (ProjectModel _model : _list) {
			_ids.add(_model.getId());
		}		
		for (ProjectModel _model : testObjects) {
			assertTrue("SubProject <" + _model.getId() + "> should be listed", _ids.contains(_model.getId()));
		}
	}

	/**
	 * Test whether all listed objects are readable.
	 */
	@Test
	public void testAllReadable() {
		for (ProjectModel _model : testObjects) {
			SubProjectTest.get(wc, company.getId(), parentProject.getId(), _model.getId(), Status.OK);
		}			
	}

	/**
	 * Test batch-wise access to the test data (list with default position and size).
	 */
	@Test
	public void testBatchedList() 
	{
		int _numberOfBatches = 0;
		int _numberOfReturnedObjects = 0;
		int _position = 0;
	
		List<ProjectModel> _batch = null;
		while(true) {
			_numberOfBatches++;
			_batch = SubProjectTest.list(wc, company.getId(), parentProject.getId(), null, _position, -1, Status.OK);
			_numberOfReturnedObjects += _batch.size();
			System.out.println("batch " + _numberOfBatches + ": position=" + _position + ", returnedObjects=" + _numberOfReturnedObjects);
			if (_batch.size() < GenericService.DEF_SIZE) {
				break;
			} else {
				_position += GenericService.DEF_SIZE;					
			}
		}
		validateBatches(_numberOfBatches, _numberOfReturnedObjects, _batch.size());
	}

	/**
	 * Get some elements starting from a specific position
	 */
	@Test
	public void testNextElements() 
	{
		List<ProjectModel> _objs = SubProjectTest.list(wc, company.getId(), parentProject.getId(), null, 5, 5, Status.OK);
		assertEquals("list() should return correct number of elements", 5, _objs.size());		
	}
	
	/**
	 * Get some elements until the end of the list
	 */
	@Test
	public void testLastElements() 
	{
		int _totalMembers = calculateMembers();
		List<ProjectModel> _objs = SubProjectTest.list(wc, company.getId(), parentProject.getId(), null, (_totalMembers - 4), 4, Status.OK);
		assertEquals("list() should return correct number of elements", 4, _objs.size());		
	}
	
	/**
	 * Read some elements until after the list end
	 */
	@Test 
	public void testOverEndOfList() 
	{
		int _totalMembers = calculateMembers();
		List<ProjectModel> _objs = SubProjectTest.list(wc, company.getId(), parentProject.getId(), null, (_totalMembers - 5), 10, Status.OK);
		assertEquals("list() should return correct number of elements", 5, _objs.size());		
	}
	
	/**
	 * Print the result of the list() operation onto stdout.
	 * @param title  the title of the log section
	 * @param list a list of ProjectModel objects
	 */
	public static void printModelList(
			String title, 
			List<ProjectModel> list) 
	{
		System.out.println("***** " + title);
		System.out.println("\tID\ttitle");
		for (ProjectModel _model : list) { 
			System.out.println(
					"\t" + _model.getId() + 
					"\t" + _model.getTitle());
		}
		System.out.println("\ttotal:\t" + list.size() + " elements");
	}
	
	/* (non-Javadoc)
	 * @see test.org.opentdc.AbstractTestClient#calculateMembers()
	 */
	protected int calculateMembers() {
		return SubProjectTest.list(wc, company.getId(), parentProject.getId(), null, 0, Integer.MAX_VALUE, Status.OK).size();
	}
}